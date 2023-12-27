package pe.datasys.repo;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pe.datasys.model.FacturacionEntity;

public interface IFacturacionRepo extends IGenericRepo<FacturacionEntity, Long> {
    
    @Modifying
    @Query(value = "UPDATE facturacion SET external_id = :externalId, hash = :hash, link_cdr = :linkCdr, link_pdf = :linkPdf, link_xml = :linkXml, respuesta_cdr = :respuestaCdr WHERE id_facturacion = :idFacturacion", nativeQuery = true)
    Integer actualizarDatosFE(
        @Param("externalId") String externalId, 
        @Param("hash") String hash,
        @Param("linkCdr") String linkCdr,
        @Param("linkPdf") String linkPdf,
        @Param("linkXml") String linkXml,
        @Param("respuestaCdr") String respuestaCdr,
        @Param("idFacturacion") Long idFacturacion
        );
}
