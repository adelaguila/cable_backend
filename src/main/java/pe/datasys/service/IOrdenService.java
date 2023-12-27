package pe.datasys.service;

import java.util.List;

import org.springframework.data.domain.Page;

import pe.datasys.commons.Filter;
import pe.datasys.commons.SortModel;
import pe.datasys.dto.ImprimirOrdenDTO;
import pe.datasys.dto.OrdenAsignacionDTO;
import pe.datasys.dto.OrdenDTO;
import pe.datasys.model.OrdenAsignacionEntity;
import pe.datasys.model.OrdenEntity;

public interface IOrdenService extends ICRUD<OrdenEntity, Long> {
    Page<OrdenDTO> paginate(Integer page, Integer rowPage, List<Filter> filters, List<SortModel> sorts);

    List<ImprimirOrdenDTO> findOrdenImprimir(Long id);

    List<OrdenEntity> findFirst2ByIdOrden(Long id);

    List<OrdenEntity> findFirst1ByIdOrden(Long id);

    List<OrdenEntity> findByAbonadoIdAbonado(Long idAbonado);

    byte[] generateOrdenPDF(Long id) throws Exception;

    OrdenAsignacionEntity registrarOrdenAsignacionTransactional(OrdenAsignacionDTO dto);

    Integer atenderOrdenAsignacionTransactional(OrdenAsignacionDTO dto);
}
