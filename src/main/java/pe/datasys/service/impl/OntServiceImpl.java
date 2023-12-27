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
import pe.datasys.dto.OntDTO;
import pe.datasys.model.OntEntity;
import pe.datasys.repo.IGenericRepo;
import pe.datasys.repo.IOntRepo;
import pe.datasys.service.IOntService;

@Service
@RequiredArgsConstructor
public class OntServiceImpl extends CRUDImpl<OntEntity, String> implements IOntService{

    private final IOntRepo repo;
    private final EntityManager entityManager;

    @Override
    protected IGenericRepo<OntEntity, String> getRepo() {
       return repo;
    }

    @Override
    public Page<OntDTO> paginate(Integer page, Integer rowPage, List<Filter> filters, List<SortModel> sorts) {
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
        List<OntDTO> lista = querySelect.getResultList();

        PageRequest pageable = PageRequest.of(page, rowPage);

        Page<OntDTO> rtnPage = new PageImpl<>(lista, pageable, total);
        return rtnPage;
    }
    
    public StringBuilder getSelect() {
        StringBuilder sql = new StringBuilder("SELECT a ");
         return sql;
     }
 
     public StringBuilder getFrom() {
         StringBuilder sql = new StringBuilder(" FROM OntEntity a ");
         return sql;
     }
 
     public StringBuilder getFilters(List<Filter> filters) {
         StringBuilder sql = new StringBuilder("where 1=1 ");
 
         for (Filter filtro : filters) {
 
             if (filtro.getField().equals("serie")) {
                 sql.append(getFiltersColumns("a.serie", filtro.getMatchMode(), ":serie"));
             }
             
             if (filtro.getField().equals("nombreMarca")) {
                 sql.append(getFiltersColumns("a.marca.nombreMarca", filtro.getMatchMode(), ":nombreMarca"));
             }
             if (filtro.getField().equals("modelo")) {
                 sql.append(getFiltersColumns("a.modelo", filtro.getMatchMode(), ":modelo"));
             }
             if (filtro.getField().equals("estado")) {
                 sql.append(getFiltersColumns("a.estado", filtro.getMatchMode(), ":estado"));
             }
         }
 
         return sql;
     }
 
     public Query setParams(List<Filter> filters, Query query) {
         for (Filter filtro : filters) {
             if (filtro.getField().equals("serie")) {
                 query.setParameter("serie", filtro.getValue());
             }
             if (filtro.getField().equals("nombreMarca")) {
                 query.setParameter("nombreMarca", filtro.getValue());
             }
             if (filtro.getField().equals("modelo")) {
                 query.setParameter("modelo", filtro.getValue());
             }
             if (filtro.getField().equals("estado")) {
                 query.setParameter("estado", filtro.getValue());
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
             if (sort.getField().equals("nombreMarca")) {
                 preSql = " ORDER BY a.marca.nombreMarca " + sort.getSort();
             }
             if (sort.getField().equals("modelo")) {
                 preSql = " ORDER BY a.modelo " + sort.getSort();
             }
             if (sort.getField().equals("estado")) {
                 preSql = " ORDER BY a.estado " + sort.getSort();
             }
         }
 
         StringBuilder sql = new StringBuilder(preSql);
 
         return sql;
     }

    @Override
    public List<OntEntity> findByEstado(String estado) {
        return repo.findByEstado(estado);
    }
}
