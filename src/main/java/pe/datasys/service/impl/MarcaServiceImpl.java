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
import pe.datasys.dto.MarcaDTO;
import pe.datasys.model.MarcaEntity;
import pe.datasys.repo.IGenericRepo;
import pe.datasys.repo.IMarcaRepo;
import pe.datasys.service.IMarcaService;

@Service
@RequiredArgsConstructor
public class MarcaServiceImpl extends CRUDImpl<MarcaEntity, Integer> implements IMarcaService{

    private final IMarcaRepo repo;
    private final EntityManager entityManager;

    @Override
    protected IGenericRepo<MarcaEntity, Integer> getRepo() {
       return repo;
    }

    @Override
    public Page<MarcaDTO> paginate(Integer page, Integer rowPage, List<Filter> filters, List<SortModel> sorts) {
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
        List<MarcaDTO> lista = querySelect.getResultList();

        PageRequest pageable = PageRequest.of(page, rowPage);

        Page<MarcaDTO> rtnPage = new PageImpl<>(lista, pageable, total);
        return rtnPage;
    }
    
    public StringBuilder getSelect() {
        StringBuilder sql = new StringBuilder("SELECT a ");
         return sql;
     }
 
     public StringBuilder getFrom() {
         StringBuilder sql = new StringBuilder(" FROM MarcaEntity a ");
         return sql;
     }
 
     public StringBuilder getFilters(List<Filter> filters) {
         StringBuilder sql = new StringBuilder("where 1=1 ");
 
         for (Filter filtro : filters) {
 
             if (filtro.getField().equals("idMarca")) {
                 sql.append(getFiltersColumns("a.idMarca", filtro.getMatchMode(), ":idMarca"));
             }
             
             if (filtro.getField().equals("nombreMarca")) {
                 sql.append(getFiltersColumns("a.nombreMarca", filtro.getMatchMode(), ":nombreMarca"));
             }
         }
 
         return sql;
     }
 
     public Query setParams(List<Filter> filters, Query query) {
         for (Filter filtro : filters) {
             if (filtro.getField().equals("idMarca")) {
                 query.setParameter("idMarca", filtro.getValue());
             }
             if (filtro.getField().equals("nombreMarca")) {
                 query.setParameter("nombreMarca", filtro.getValue());
             }
         }
 
         return query;
     }
 
     public StringBuilder getOrder(List<SortModel> sorts) {
 
         String preSql = "";
 
         for (SortModel sort : sorts) {
             if (sort.getField().equals("idMarca")) {
                 preSql = " ORDER BY a.idMarca " + sort.getSort();
             }
             if (sort.getField().equals("nombreMarca")) {
                 preSql = " ORDER BY a.nombreMarca " + sort.getSort();
             }
         }
 
         StringBuilder sql = new StringBuilder(preSql);
 
         return sql;
     }
}
