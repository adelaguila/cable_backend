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
import pe.datasys.dto.ComprobanteDTO;
import pe.datasys.model.ComprobanteEntity;
import pe.datasys.repo.IComprobanteRepo;
import pe.datasys.repo.IGenericRepo;
import pe.datasys.service.IComprobanteService;

@Service
@RequiredArgsConstructor
public class ComprobanteServiceImpl extends CRUDImpl<ComprobanteEntity, String> implements IComprobanteService{

    private final IComprobanteRepo repo;
    private final EntityManager entityManager;

    @Override
    protected IGenericRepo<ComprobanteEntity, String> getRepo() {
       return repo;
    }

    @Override
    public Page<ComprobanteDTO> paginate(Integer page, Integer rowPage, List<Filter> filters, List<SortModel> sorts) {
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
        List<ComprobanteDTO> lista = querySelect.getResultList();

        PageRequest pageable = PageRequest.of(page, rowPage);

        Page<ComprobanteDTO> rtnPage = new PageImpl<>(lista, pageable, total);
        return rtnPage;
    }
    
    public StringBuilder getSelect() {
        StringBuilder sql = new StringBuilder("SELECT a ");
         return sql;
     }
 
     public StringBuilder getFrom() {
         StringBuilder sql = new StringBuilder(" FROM ComprobanteEntity a ");
         return sql;
     }
 
     public StringBuilder getFilters(List<Filter> filters) {
         StringBuilder sql = new StringBuilder("where 1=1 ");
 
         for (Filter filtro : filters) {
 
             if (filtro.getField().equals("serie")) {
                 sql.append(getFiltersColumns("a.serie", filtro.getMatchMode(), ":serie"));
             }
         }
 
         return sql;
     }
 
     public Query setParams(List<Filter> filters, Query query) {
         for (Filter filtro : filters) {
             if (filtro.getField().equals("serie")) {
                 query.setParameter("serie", filtro.getValue());
             }
         }
 
         return query;
     }
 
     public StringBuilder getOrder(List<SortModel> sorts) {
 
         String preSql = "";
 
         for (SortModel sort : sorts) {
             if (sort.getField().equals("serie")) {
                 preSql = " ORDER BY a.serie " + sort.getSort();
             }
        
         }
 
         StringBuilder sql = new StringBuilder(preSql);
 
         return sql;
     }

}
