package pe.datasys.service;

import java.io.IOException;

public interface IDSFacturacionService {

    Object generarComprobante(Long idFacturacion) throws IOException ;
    
}
