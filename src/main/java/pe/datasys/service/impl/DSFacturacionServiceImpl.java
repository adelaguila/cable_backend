package pe.datasys.service.impl;

import java.util.List;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.BigDecimal;

import lombok.RequiredArgsConstructor;
import pe.datasys.dto.DSFacturacion.DataClienteReceptorDTO;
import pe.datasys.dto.DSFacturacion.DataComprobanteDTO;
import pe.datasys.dto.DSFacturacion.DataCuotaDTO;
import pe.datasys.dto.DSFacturacion.DataDocumentoAfectadoDTO;
import pe.datasys.dto.DSFacturacion.DataItemDTO;
import pe.datasys.dto.DSFacturacion.DataTotalesDTO;
import pe.datasys.dto.DSFacturacion.RespDSFacturacionDTO;
import pe.datasys.model.ConfiguracionEntity;
import pe.datasys.model.FacturacionEntity;
import pe.datasys.model.FacturacionItemEntity;
import pe.datasys.model.TerceroDireccionEntity;
import pe.datasys.repo.IConfiguracionRepo;
import pe.datasys.repo.IFacturacionItemRepo;
import pe.datasys.repo.IFacturacionRepo;
import pe.datasys.repo.ITerceroDireccionRepo;
import pe.datasys.service.IDSFacturacionService;

@Service
@RequiredArgsConstructor
public class DSFacturacionServiceImpl implements IDSFacturacionService {

    private final IFacturacionRepo facturacionRepo;
    private final IFacturacionItemRepo facturacionItemRepo;
    private final ITerceroDireccionRepo terceroDireccionRepo;
    private final IConfiguracionRepo configuracionRepo;

    private final RestTemplate restTemplate;

    private final ModelMapper mapper;

    @Override
    public Object generarComprobante(Long idFacturacion) throws IOException {

        ConfiguracionEntity conf = configuracionRepo.findById(1).orElse(null);
        String url = conf.getUrlPse() + "documents";
        String token = conf.getTokenPse();

        FacturacionEntity facturacion = facturacionRepo.findById(idFacturacion).orElse(null);
        List<FacturacionItemEntity> facturacionItems = facturacionItemRepo.itemsIdFacturacion(idFacturacion);

        TerceroDireccionEntity terceroDireccion = terceroDireccionRepo.findById(facturacion.getIdTerceroDireccion())
                .orElse(null);

        DataDocumentoAfectadoDTO documentoAfectado = new DataDocumentoAfectadoDTO();
        documentoAfectado.setExternal_id(facturacion.getDocumentoAfectado());

        DataClienteReceptorDTO clienteReceptor = new DataClienteReceptorDTO();
        if (facturacion.getTercero().getDniruc().length() == 8) {
            clienteReceptor.setCodigo_tipo_documento_identidad("1");
        } else if (facturacion.getTercero().getDniruc().length() == 11) {
            clienteReceptor.setCodigo_tipo_documento_identidad("6");
        } else {
            clienteReceptor.setCodigo_tipo_documento_identidad("0");
        }
        clienteReceptor.setNumero_documento(facturacion.getTercero().getDniruc());
        clienteReceptor.setApellidos_y_nombres_o_razon_social(facturacion.getTercero().getNombreTercero());
        clienteReceptor.setCodigo_pais("PE");
        clienteReceptor.setUbigeo(terceroDireccion.getUbigeo().getCodigo());
        clienteReceptor.setDireccion(terceroDireccion.getDireccion());
        clienteReceptor.setCorreo_electronico(facturacion.getTercero().getCorreoElectronico());
        clienteReceptor.setTelefono(facturacion.getTercero().getTelefono1());

        String tipoAfecIgv = "";

        DataTotalesDTO totales = new DataTotalesDTO();
        totales.setTotal_exportacion(new BigDecimal(0));
        if (facturacion.getPigv().compareTo(new BigDecimal(0)) == 1) {
            totales.setTotal_operaciones_exoneradas(new BigDecimal(0));
            totales.setTotal_operaciones_inafectas(new BigDecimal(0));
            totales.setTotal_operaciones_gravadas(facturacion.getSubtotal());
            tipoAfecIgv = "10";
        }

        if (facturacion.getPigv().compareTo(new BigDecimal(0)) == 0) {
            totales.setTotal_operaciones_exoneradas(facturacion.getSubtotal());
            totales.setTotal_operaciones_inafectas(new BigDecimal(0));
            totales.setTotal_operaciones_gravadas(new BigDecimal(0));
            tipoAfecIgv = "20";
        }
        totales.setTotal_operaciones_gratuitas(new BigDecimal(0));
        totales.setTotal_igv(facturacion.getIgv());
        totales.setTotal_impuestos(facturacion.getIgv());
        totales.setTotal_valor(facturacion.getSubtotal());
        totales.setTotal_venta(facturacion.getTotal());

        List<DataItemDTO> items = new ArrayList<DataItemDTO>();

        for (FacturacionItemEntity fi : facturacionItems) {
            DataItemDTO item = new DataItemDTO();
            item.setCodigo_interno("001");
            item.setDescripcion(fi.getGlosa());
            item.setCodigo_producto_sunat("");
            item.setCodigo_producto_gsl("");
            item.setUnidad_de_medida("NIU");
            item.setCantidad(fi.getCantidad());
            item.setValor_unitario(fi.getSubtotal().divide(new BigDecimal(fi.getCantidad())));
            item.setCodigo_tipo_precio("01");
            item.setPrecio_unitario(fi.getPrecio());
            item.setCodigo_tipo_afectacion_igv(tipoAfecIgv);
            item.setTotal_base_igv(fi.getSubtotal());
            item.setPorcentaje_igv(facturacion.getPigv());
            item.setTotal_igv(facturacion.getIgv());
            item.setTotal_impuestos(facturacion.getIgv());
            item.setTotal_valor_item(facturacion.getSubtotal());
            item.setTotal_item(facturacion.getTotal());

            items.add(item);
        }

        DataComprobanteDTO dataJson = new DataComprobanteDTO();
        dataJson.setSerie_documento(facturacion.getSerie());
        dataJson.setNumero_documento(facturacion.getNumero());
        dataJson.setFecha_de_emision(facturacion.getFecha().toString());
        dataJson.setHora_de_emision("10:11:11");
        dataJson.setCodigo_tipo_operacion("0101");
        dataJson.setCodigo_tipo_documento(facturacion.getTipoDocumento());
        dataJson.setCodigo_tipo_moneda(facturacion.getTipoMoneda());
        dataJson.setFecha_de_vencimiento(facturacion.getFechaVencimiento().toString());
        dataJson.setNumero_orden_de_compra("");
        if (facturacion.getTipoDocumento() == "07" || facturacion.getTipoDocumento() == "08") {
            dataJson.setDocumento_afectado(documentoAfectado);
            dataJson.setCodigo_tipo_nota(facturacion.getNotaCodigo());
            dataJson.setMotivo_o_sustento_de_nota(facturacion.getNotaMotivo());
        }
        dataJson.setDatos_del_cliente_o_receptor(clienteReceptor);

        if (facturacion.getTipoPago() == "CREDITO") {
            dataJson.setCodigo_condicion_de_pago("02");

            List<DataCuotaDTO> cuotas = new ArrayList<DataCuotaDTO>();
            DataCuotaDTO cuota = new DataCuotaDTO();
            cuota.setFecha(facturacion.getFechaVencimiento().toString());
            cuota.setCodigo_tipo_moneda(facturacion.getTipoMoneda());
            cuota.setMonto(facturacion.getTotal());

            cuotas.add(cuota);

            dataJson.setCuotas(cuotas);
        }

        dataJson.setTotales(totales);
        dataJson.setItems(items);

        ObjectMapper objectMapper = new ObjectMapper();
        String datosJson = objectMapper.writeValueAsString(dataJson);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);

        HttpEntity<String> entity = new HttpEntity<>(datosJson.toString(), headers);

        Object res = restTemplate.exchange(url, HttpMethod.POST, entity, Object.class).getBody();

        RespDSFacturacionDTO respuesta = mapper.map(res, RespDSFacturacionDTO.class);


        if (respuesta.getSuccess()) {
            String respuestaCdr = "";
            if(respuesta.getResponse() != null){
                respuestaCdr = respuesta.getResponse().getDescription();
                
            }else{
                respuestaCdr = respuesta.getData().getExternal_id(); 
            }
            facturacionRepo.actualizarDatosFE(
                    respuesta.getData().getExternal_id(),
                    respuesta.getData().getHash(),
                    respuesta.getLinks().getCdr(),
                    respuesta.getLinks().getPdf(),
                    respuesta.getLinks().getXml(),
                    respuestaCdr, idFacturacion);
        }

        return dataJson;
    }

}
