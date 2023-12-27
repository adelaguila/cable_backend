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
import pe.datasys.dto.CajaNapDTO;
import pe.datasys.model.CajaNapEntity;
import pe.datasys.repo.ICajaNapRepo;
import pe.datasys.repo.IGenericRepo;
import pe.datasys.service.ICajaNapService;

@Service
@RequiredArgsConstructor
public class CajaNapServiceImpl extends CRUDImpl<CajaNapEntity, Integer> implements ICajaNapService{

    private final ICajaNapRepo repo;
    private final EntityManager entityManager;

    @Override
    protected IGenericRepo<CajaNapEntity, Integer> getRepo() {
       return repo;
    }

    @Override
    public Page<CajaNapDTO> paginate(Integer page, Integer rowPage, List<Filter> filters, List<SortModel> sorts) {
        String sqlCount = "SELECT count(a) " + getFrom().toString() + getFilters(filters).toString();

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
        List<CajaNapDTO> lista = querySelect.getResultList();

        PageRequest pageable = PageRequest.of(page, rowPage);

        Page<CajaNapDTO> rtnPage = new PageImpl<>(lista, pageable, total);
        return rtnPage;
    }
       
    public StringBuilder getSelect() {
        StringBuilder sql = new StringBuilder("SELECT a ");
         return sql;
     }
 
     public StringBuilder getFrom() {
         StringBuilder sql = new StringBuilder(" FROM CajaNapEntity a ");
         return sql;
     }
 
     public StringBuilder getFilters(List<Filter> filters) {
         StringBuilder sql = new StringBuilder("where 1=1 ");
 
         for (Filter filtro : filters) {
 
             if (filtro.getField().equals("idCajaNap")) {
                 sql.append(getFiltersColumns("a.idCajaNap", filtro.getMatchMode(), ":idCajaNap"));
             }
             
             if (filtro.getField().equals("nombreCajaNap")) {
                 sql.append(getFiltersColumns("a.nombreCajaNap", filtro.getMatchMode(), ":nombreCajaNap"));
             }
             if (filtro.getField().equals("puertos")) {
                 sql.append(getFiltersColumns("a.puertos", filtro.getMatchMode(), ":puertos"));
             }
         }
 
         return sql;
     }
 
     public Query setParams(List<Filter> filters, Query query) {
         for (Filter filtro : filters) {
             if (filtro.getField().equals("idCajaNap")) {
                 query.setParameter("idCajaNap", filtro.getValue());
             }
             if (filtro.getField().equals("nombreCajaNap")) {
                 query.setParameter("nombreCajaNap", filtro.getValue());
             }
             if (filtro.getField().equals("puertos")) {
                 query.setParameter("puertos", filtro.getValue());
             }
         }
 
         return query;
     }
 
     public StringBuilder getOrder(List<SortModel> sorts) {
 
         String preSql = "";
 
         for (SortModel sort : sorts) {
             if (sort.getField().equals("idCajaNap")) {
                 preSql = " ORDER BY a.idCajaNap " + sort.getSort();
             }
             if (sort.getField().equals("nombreCajaNap")) {
                 preSql = " ORDER BY a.nombreCajaNap " + sort.getSort();
             }
             if (sort.getField().equals("puertos")) {
                 preSql = " ORDER BY a.puertos " + sort.getSort();
             }
         }
 
         StringBuilder sql = new StringBuilder(preSql);
 
         return sql;
     }
}
