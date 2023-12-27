package pe.datasys.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import pe.datasys.commons.Filter;
import pe.datasys.commons.SortModel;
import pe.datasys.dto.OrdenAsignacionTablaDTO;
import pe.datasys.model.OrdenAsignacionEntity;
import pe.datasys.repo.IGenericRepo;
import pe.datasys.repo.IOrdenAsignacionRepo;
import pe.datasys.service.IOrdenAsignacionService;

@Service
@RequiredArgsConstructor
public class OrdenAsignacionServiceImpl extends CRUDImpl<OrdenAsignacionEntity, Long> implements IOrdenAsignacionService {

    private final IOrdenAsignacionRepo repo;
    private final EntityManager entityManager;

    @Override
    protected IGenericRepo<OrdenAsignacionEntity, Long> getRepo() {
       return repo;
    }

    @Override
    public OrdenAsignacionEntity findOrdenAsignacionActiva(Long idOrden) {
        return repo.findOrdenAsignacionActiva(idOrden);
    }

    @Override
    public Page<OrdenAsignacionTablaDTO> paginate(Integer page, Integer rowPage, List<Filter> filters, List<SortModel> sorts) {
        String sqlCount = "SELECT count(o) " + getFrom().toString() + getFilters(filters).toString();

        String sqlSelect = getSelect().toString() + getFrom().toString() + getFilters(filters).toString()
                + getOrder(sorts).toString();
        
        Query queryCount = entityManager.createQuery(sqlCount);
        Query querySelect = entityManager.createQuery(sqlSelect);

        queryCount = this.setParams(filters, queryCount);

        querySelect = this.setParams(filters, querySelect);

        Long total = (long) queryCount.getSingleResult();

        querySelect.setFirstResult((page) * rowPage);
        querySelect.setMaxResults(rowPage);

        @SuppressWarnings("unchecked")
        List<OrdenAsignacionTablaDTO> lista = querySelect.getResultList();

        PageRequest pageable = PageRequest.of(page, rowPage);

        Page<OrdenAsignacionTablaDTO> rtnPage = new PageImpl<>(lista, pageable, total);
        return rtnPage;
    }

    public StringBuilder getSelect() {
        StringBuilder sql = new StringBuilder("SELECT new pe.datasys.dto.OrdenAsignacionTablaDTO(o.idOrdenAsignacion, o.orden.idOrden, o.orden.fechaRegistro, o.fechaAsignacion, o.fechaAtencion, o.orden.abonado.tercero.nombreTercero, o.orden.tipoOrden.nombreTipoOrden, o.user.name, o.orden.abonado.sector.nombreSector, concat(o.orden.abonado.via.tipoVia.idTipoVia, '. ', o.orden.abonado.via.nombreVia, ' ', o.orden.abonado.numero), o.orden.estado, o.reporte) ");
         return sql;
     }
 
     public StringBuilder getFrom() {
         StringBuilder sql = new StringBuilder(" FROM OrdenAsignacionEntity o ");
         return sql;
     }
 
     public StringBuilder getFilters(List<Filter> filters) {
         StringBuilder sql = new StringBuilder("where 1=1 ");
 
         for (Filter filtro : filters) {
 
             if (filtro.getField().equals("estado")) {
                 sql.append(getFiltersColumns("o.orden.estado", filtro.getMatchMode(), ":estado"));
             }

         }
 
         return sql;
     }
 
     public Query setParams(List<Filter> filters, Query query) {
         for (Filter filtro : filters) {
             if (filtro.getField().equals("estado")) {
                 query.setParameter("estado", filtro.getValue());
             }
             
         }
 
         return query;
     }
 
     public StringBuilder getOrder(List<SortModel> sorts) {
 
         String preSql = "";
 
         for (SortModel sort : sorts) {
             if (sort.getField().equals("fechaAsignacion")) {
                 preSql = " ORDER BY o.fechaAsignacion " + sort.getSort();
             }
         }
 
         StringBuilder sql = new StringBuilder(preSql);
 
         return sql;
     }
}
