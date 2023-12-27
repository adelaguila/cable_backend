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
import pe.datasys.dto.BancoDTO;
import pe.datasys.model.BancoEntity;
import pe.datasys.repo.IBancoRepo;
import pe.datasys.repo.IGenericRepo;
import pe.datasys.service.IBancoService;

@Service
@RequiredArgsConstructor
public class BancoServiceImpl extends CRUDImpl<BancoEntity, Integer> implements IBancoService{

    private final IBancoRepo repo;
    private final EntityManager entityManager;

    @Override
    protected IGenericRepo<BancoEntity, Integer> getRepo() {
       return repo;
    }

    @Override
    public Page<BancoDTO> paginate(Integer page, Integer rowPage, List<Filter> filters, List<SortModel> sorts) {
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
        List<BancoDTO> lista = querySelect.getResultList();

        PageRequest pageable = PageRequest.of(page, rowPage);

        Page<BancoDTO> rtnPage = new PageImpl<>(lista, pageable, total);
        return rtnPage;
    }

    public StringBuilder getSelect() {
        StringBuilder sql = new StringBuilder("SELECT a ");
         return sql;
     }
 
     public StringBuilder getFrom() {
         StringBuilder sql = new StringBuilder(" FROM BancoEntity a ");
         return sql;
     }
 
     public StringBuilder getFilters(List<Filter> filters) {
         StringBuilder sql = new StringBuilder("where 1=1 ");
 
         for (Filter filtro : filters) {
 
             if (filtro.getField().equals("idBanco")) {
                 sql.append(getFiltersColumns("a.idBanco", filtro.getMatchMode(), ":idBanco"));
             }
             
             if (filtro.getField().equals("nombreBanco")) {
                 sql.append(getFiltersColumns("a.nombreBanco", filtro.getMatchMode(), ":nombreBanco"));
             }
         }
 
         return sql;
     }
 
     public Query setParams(List<Filter> filters, Query query) {
         for (Filter filtro : filters) {
             if (filtro.getField().equals("idBanco")) {
                 query.setParameter("idBanco", filtro.getValue());
             }
             if (filtro.getField().equals("nombreBanco")) {
                 query.setParameter("nombreBanco", filtro.getValue());
             }
         }
 
         return query;
     }
 
     public StringBuilder getOrder(List<SortModel> sorts) {
 
         String preSql = "";
 
         for (SortModel sort : sorts) {
             if (sort.getField().equals("idBanco")) {
                 preSql = " ORDER BY a.idBanco " + sort.getSort();
             }
             if (sort.getField().equals("nombreBanco")) {
                 preSql = " ORDER BY a.nombreBanco " + sort.getSort();
             }
         }
 
         StringBuilder sql = new StringBuilder(preSql);
 
         return sql;
     }
        
}
