package pe.datasys.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pe.datasys.dto.PagoGeneraComprobanteDTO;

import pe.datasys.model.CargoEntity;
import pe.datasys.model.CargoPagoEntity;
import pe.datasys.model.ComprobanteEntity;
import pe.datasys.model.FacturacionEntity;
import pe.datasys.model.PagoEntity;
import pe.datasys.model.TerceroEntity;
import pe.datasys.repo.IAbonadoRepo;
import pe.datasys.repo.ICargoPagoRepo;
import pe.datasys.repo.ICargoRepo;
import pe.datasys.repo.IComprobanteRepo;
import pe.datasys.repo.IGenericRepo;
import pe.datasys.repo.IPagoRepo;
import pe.datasys.service.IDSFacturacionService;
import pe.datasys.service.IFacturacionService;
import pe.datasys.service.IPagoService;
import pe.datasys.service.ITerceroService;

@Service
@RequiredArgsConstructor
public class PagoServiceImpl extends CRUDImpl<PagoEntity, Long> implements IPagoService {

    private final IPagoRepo pagoRepo;
    private final ICargoRepo cargoRepo;
    private final ICargoPagoRepo cargoPagoRepo;
    private final IComprobanteRepo comprobanteRepo;
    private final IAbonadoRepo abonadoRepo;
    
    private final IFacturacionService facturacionService;
    private final ITerceroService terceroService;
    private final IDSFacturacionService dsFacturacionService;

    @Override
    protected IGenericRepo<PagoEntity, Long> getRepo() {
        return pagoRepo;
    }

    @Override
    public List<PagoEntity> findByAbonadoIdAbonado(Long idAbonado) {
        return pagoRepo.findByAbonadoIdAbonado(idAbonado);
    }

    @Override
    public PagoEntity savePagoComprobanteTransactional(PagoEntity pago,
            PagoGeneraComprobanteDTO pagoGenerarComprobante) {

        List<CargoEntity> cargosPendientes = cargoRepo.cargosPendientesIdAbonado(pago.getAbonado().getIdAbonado());

        BigDecimal totalPago = pago.getTotal();
        String glosa = pago.getReferencia();    

        PagoEntity pagoNew = pagoRepo.save(pago);

        for (CargoEntity cargo : cargosPendientes) {

            BigDecimal pagado = new BigDecimal(0);
            BigDecimal pendiente = cargo.getTotal().subtract(cargo.getPagado());

            if (pendiente.compareTo(totalPago) == -1 || pendiente.compareTo(totalPago) == 0) {
                pagado = pendiente;
            }else{
                pagado = totalPago;
            }

            CargoPagoEntity cargoPago = new CargoPagoEntity();
            cargoPago.setCargo(cargo);
            cargoPago.setPago(pagoNew);
            cargoPago.setPagado(pagado);
            cargoPagoRepo.save(cargoPago);

            cargoRepo.actualizarPago(pagado, cargo.getIdCargo());

            totalPago = totalPago.subtract(pagado);

            glosa = glosa + " " + cargo.getGlosa();

            if (totalPago.compareTo(new BigDecimal(0)) == 0) {
                break;
            }
        }

        ComprobanteEntity comprobante = comprobanteRepo.findById(pagoGenerarComprobante.getComprobante().getSerie()).orElse(null);

        if(comprobante != null) {

            TerceroEntity tercero = terceroService.findById(pagoGenerarComprobante.getTercero().getIdTercero());

            FacturacionEntity facturacionNew = facturacionService.saveFromPago(pagoNew, comprobante, tercero, glosa);

            try {
                dsFacturacionService.generarComprobante(facturacionNew.getIdFacturacion());
            } catch (IOException e) {
  
                e.printStackTrace();
            }
        }

        abonadoRepo.actualizarDeudaSaldoFavorIdAbonado(pagoNew.getAbonado().getIdAbonado());

        return pagoNew;
    }

}
