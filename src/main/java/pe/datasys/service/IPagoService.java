package pe.datasys.service;
import pe.datasys.dto.PagoGeneraComprobanteDTO;
import pe.datasys.model.PagoEntity;

import java.util.List;

public interface IPagoService extends ICRUD<PagoEntity, Long> {

     List<PagoEntity> findByAbonadoIdAbonado(Long idAbonado);
    
     PagoEntity savePagoComprobanteTransactional(PagoEntity pago, PagoGeneraComprobanteDTO pagoGenerarComprobante);
}
