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
import pe.datasys.dto.RoleDTO;
import pe.datasys.model.Role;
import pe.datasys.repo.IGenericRepo;
import pe.datasys.repo.IRoleRepo;
import pe.datasys.service.IRoleService;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl extends CRUDImpl<pe.datasys.model.Role, Integer> implements IRoleService{

    private final IRoleRepo repo;
    private final EntityManager entityManager;

    @Override
    protected IGenericRepo<Role, Integer> getRepo() {
       return repo;
    }

    @Override
    public Page<RoleDTO> paginate(Integer page, Integer rowPage, List<Filter> filters, List<SortModel> sorts) {
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
        List<RoleDTO> lista = querySelect.getResultList();

        PageRequest pageable = PageRequest.of(page, rowPage);

        Page<RoleDTO> rtnPage = new PageImpl<>(lista, pageable, total);
        return rtnPage;
    }

    public StringBuilder getSelect() {
        StringBuilder sql = new StringBuilder("SELECT a ");
         return sql;
     }
 
     public StringBuilder getFrom() {
         StringBuilder sql = new StringBuilder(" FROM Role a ");
         return sql;
     }
 
     public StringBuilder getFilters(List<Filter> filters) {
         StringBuilder sql = new StringBuilder("where 1=1 ");
 
         for (Filter filtro : filters) {
 
             if (filtro.getField().equals("idRole")) {
                 sql.append(getFiltersColumns("a.idRole", filtro.getMatchMode(), ":idRole"));
             }
             
             if (filtro.getField().equals("name")) {
                 sql.append(getFiltersColumns("a.name", filtro.getMatchMode(), ":name"));
             }
         }
 
         return sql;
     }
 
     public Query setParams(List<Filter> filters, Query query) {
         for (Filter filtro : filters) {
             if (filtro.getField().equals("idRole")) {
                 query.setParameter("idRole", filtro.getValue());
             }
             if (filtro.getField().equals("name")) {
                 query.setParameter("name", filtro.getValue());
             }
         }
 
         return query;
     }
 
     public StringBuilder getOrder(List<SortModel> sorts) {
 
         String preSql = "";
 
         for (SortModel sort : sorts) {
             if (sort.getField().equals("idRole")) {
                 preSql = " ORDER BY a.idRole " + sort.getSort();
             }
             if (sort.getField().equals("name")) {
                 preSql = " ORDER BY a.name " + sort.getSort();
             }
         }
 
         StringBuilder sql = new StringBuilder(preSql);
 
         return sql;
     }
        
}
