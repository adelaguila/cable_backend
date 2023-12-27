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
import pe.datasys.dto.SectorDTO;
import pe.datasys.model.SectorEntity;
import pe.datasys.repo.IGenericRepo;
import pe.datasys.repo.ISectorRepo;
import pe.datasys.service.ISectorService;

@Service
@RequiredArgsConstructor
public class SectorServiceImpl extends CRUDImpl<SectorEntity, Integer> implements ISectorService{

    private final ISectorRepo repo;
    private final EntityManager entityManager;

    @Override
    protected IGenericRepo<SectorEntity, Integer> getRepo() {
       return repo;
    }

    @Override
    public Page<SectorDTO> paginate(Integer page, Integer rowPage, List<Filter> filters, List<SortModel> sorts) {
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
        List<SectorDTO> lista = querySelect.getResultList();

        PageRequest pageable = PageRequest.of(page, rowPage);

        Page<SectorDTO> rtnPage = new PageImpl<>(lista, pageable, total);
        return rtnPage;
    }

    public StringBuilder getSelect() {
        StringBuilder sql = new StringBuilder("SELECT a ");
         return sql;
     }
 
     public StringBuilder getFrom() {
         StringBuilder sql = new StringBuilder(" FROM SectorEntity a ");
         return sql;
     }
 
     public StringBuilder getFilters(List<Filter> filters) {
         StringBuilder sql = new StringBuilder("where 1=1 ");
 
         for (Filter filtro : filters) {
 
             if (filtro.getField().equals("idSector")) {
                 sql.append(getFiltersColumns("a.idSector", filtro.getMatchMode(), ":idSector"));
             }
             
             if (filtro.getField().equals("nombreSector")) {
                 sql.append(getFiltersColumns("a.nombreSector", filtro.getMatchMode(), ":nombreSector"));
             }
         }
 
         return sql;
     }
 
     public Query setParams(List<Filter> filters, Query query) {
         for (Filter filtro : filters) {
             if (filtro.getField().equals("idSector")) {
                 query.setParameter("idSector", filtro.getValue());
             }
             if (filtro.getField().equals("nombreSector")) {
                 query.setParameter("nombreSector", filtro.getValue());
             }
         }
 
         return query;
     }
 
     public StringBuilder getOrder(List<SortModel> sorts) {
 
         String preSql = "";
 
         for (SortModel sort : sorts) {
             if (sort.getField().equals("idSector")) {
                 preSql = " ORDER BY a.idSector " + sort.getSort();
             }
             if (sort.getField().equals("nombreSector")) {
                 preSql = " ORDER BY a.nombreSector " + sort.getSort();
             }
         }
 
         StringBuilder sql = new StringBuilder(preSql);
 
         return sql;
     }
        
}
