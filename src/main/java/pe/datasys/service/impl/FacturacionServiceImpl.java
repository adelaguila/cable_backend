package pe.datasys.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pe.datasys.model.ComprobanteEntity;
import pe.datasys.model.ConfiguracionEntity;
import pe.datasys.model.FacturacionEntity;
import pe.datasys.model.FacturacionItemEntity;
import pe.datasys.model.PagoEntity;
import pe.datasys.model.TerceroDireccionEntity;
import pe.datasys.model.TerceroEntity;
import pe.datasys.repo.IComprobanteRepo;
import pe.datasys.repo.IConfiguracionRepo;
import pe.datasys.repo.IFacturacionItemRepo;
import pe.datasys.repo.IFacturacionRepo;
import pe.datasys.repo.IGenericRepo;
import pe.datasys.repo.ITerceroDireccionRepo;
import pe.datasys.service.IFacturacionService;

@Service
@RequiredArgsConstructor
public class FacturacionServiceImpl extends CRUDImpl<FacturacionEntity, Long> implements IFacturacionService {

    private final IFacturacionRepo facturacionRepo;
    private final IFacturacionItemRepo facturacionItemRepo;
    private final ITerceroDireccionRepo terceroDireccionRepo;
    private final IConfiguracionRepo configuracionRepo;
    private final IComprobanteRepo comprobanteRepo;

    @Override
    protected IGenericRepo<FacturacionEntity, Long> getRepo() {
        return facturacionRepo;
    }

    @Override
    public FacturacionEntity envioJsonDsFacturacion(Long idFacturacion) {
        return null;
    }

    @Override
    public FacturacionEntity saveFromPago(PagoEntity pago, ComprobanteEntity comprobante, TerceroEntity tercero, String glosa) {
        Integer numero = comprobante.getNumero() + 1;
        LocalDate fechaActual = LocalDate.now();
        ConfiguracionEntity configuracion = configuracionRepo.findById(1).orElse(null);
        BigDecimal pigv = new BigDecimal(0);
        BigDecimal subtotal = new BigDecimal(0);
        BigDecimal igv = new BigDecimal(0);
        TerceroDireccionEntity terceroDireccionDefault = terceroDireccionRepo
                .direccionDefaultIdTercero(tercero.getIdTercero());

        if (configuracion.getPigv().compareTo(new BigDecimal(0)) == 1) {
            pigv = (configuracion.getPigv().divide(new BigDecimal(100))).add(new BigDecimal(1));
            subtotal = pago.getTotal().divide(pigv).setScale(1, RoundingMode.HALF_UP);
            igv = pago.getTotal().subtract(subtotal).setScale(1, RoundingMode.HALF_UP);
        } else {
            subtotal = pago.getTotal();
        }

        FacturacionEntity facturacion = new FacturacionEntity();
        facturacion.setTercero(tercero);
        facturacion.setFecha(fechaActual);
        facturacion.setTipoDocumento(comprobante.getTipo());
        facturacion.setSerie(comprobante.getSerie());
        facturacion.setNumero(numero);
        facturacion.setPigv(configuracion.getPigv());
        facturacion.setSubtotal(subtotal);
        facturacion.setIgv(igv);
        facturacion.setTotal(pago.getTotal());
        facturacion.setFechaPago(fechaActual);
        facturacion.setDirecto(0);
        facturacion.setIdPago(pago.getIdPago());
        facturacion.setTipoPago(pago.getTipoPago());
        facturacion.setFechaVencimiento(fechaActual);
        facturacion.setTipoMoneda("PEN");
        facturacion.setIdTerceroDireccion(terceroDireccionDefault.getIdTerceroDireccion());
        facturacion.setIdAbonado(pago.getAbonado().getIdAbonado());
        facturacion.setEstado("CANCELADO");

        facturacionRepo.save(facturacion);
        
        FacturacionItemEntity facturacionItem = new FacturacionItemEntity();
        facturacionItem.setFacturacion(facturacion);
        facturacionItem.setGlosa(glosa);
        facturacionItem.setCantidad(1);
        facturacionItem.setPrecio(subtotal);
        facturacionItem.setSubtotal(subtotal);
        facturacionItem.setIgv(igv);
        facturacionItem.setTotal(pago.getTotal());

        facturacionItemRepo.save(facturacionItem);

        comprobanteRepo.updateCorrelativo(numero, comprobante.getSerie());

        return facturacion;
    }

}
