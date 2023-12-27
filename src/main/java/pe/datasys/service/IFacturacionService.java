package pe.datasys.service;

import pe.datasys.model.ComprobanteEntity;
import pe.datasys.model.FacturacionEntity;
import pe.datasys.model.PagoEntity;
import pe.datasys.model.TerceroEntity;


public interface IFacturacionService extends ICRUD<FacturacionEntity, Long> {
    
    FacturacionEntity envioJsonDsFacturacion(Long idFacturacion);   
    
    FacturacionEntity saveFromPago(PagoEntity pago, ComprobanteEntity comprobante, TerceroEntity tercero, String glosa);   

     
}
