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
import pe.datasys.dto.PlanDTO;
import pe.datasys.model.PlanEntity;
import pe.datasys.repo.IGenericRepo;
import pe.datasys.repo.IPlanRepo;
import pe.datasys.service.IPlanService;

@Service
@RequiredArgsConstructor
public class PlanServiceImpl extends CRUDImpl<PlanEntity, Integer> implements IPlanService{

    private final IPlanRepo repo;
    private final EntityManager entityManager;

    @Override
    protected IGenericRepo<PlanEntity, Integer> getRepo() {
       return repo;
    }

    @Override
    public Page<PlanDTO> paginate(Integer page, Integer rowPage, List<Filter> filters, List<SortModel> sorts) {
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
        List<PlanDTO> lista = querySelect.getResultList();

        PageRequest pageable = PageRequest.of(page, rowPage);

        Page<PlanDTO> rtnPage = new PageImpl<>(lista, pageable, total);
        return rtnPage;
    }
    
    public StringBuilder getSelect() {
        StringBuilder sql = new StringBuilder("SELECT a ");
         return sql;
     }
 
     public StringBuilder getFrom() {
         StringBuilder sql = new StringBuilder(" FROM PlanEntity a ");
         return sql;
     }
 
     public StringBuilder getFilters(List<Filter> filters) {
         StringBuilder sql = new StringBuilder("where 1=1 ");
 
         for (Filter filtro : filters) {
 
             if (filtro.getField().equals("idPlan")) {
                 sql.append(getFiltersColumns("a.idPlan", filtro.getMatchMode(), ":idPlan"));
             }
             
             if (filtro.getField().equals("nombrePlan")) {
                 sql.append(getFiltersColumns("a.nombrePlan", filtro.getMatchMode(), ":nombrePlan"));
             }
         }
 
         return sql;
     }
 
     public Query setParams(List<Filter> filters, Query query) {
         for (Filter filtro : filters) {
             if (filtro.getField().equals("idPlan")) {
                 query.setParameter("idPlan", filtro.getValue());
             }
             if (filtro.getField().equals("nombrePlan")) {
                 query.setParameter("nombrePlan", filtro.getValue());
             }
         }
 
         return query;
     }
 
     public StringBuilder getOrder(List<SortModel> sorts) {
 
         String preSql = "";
 
         for (SortModel sort : sorts) {
             if (sort.getField().equals("idPlan")) {
                 preSql = " ORDER BY a.idPlan " + sort.getSort();
             }
             if (sort.getField().equals("nombrePlan")) {
                 preSql = " ORDER BY a.nombrePlan " + sort.getSort();
             }
         }
 
         StringBuilder sql = new StringBuilder(preSql);
 
         return sql;
     }
 
}
