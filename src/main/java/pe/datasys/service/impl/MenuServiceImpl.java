package pe.datasys.service.impl;

import pe.datasys.repo.IGenericRepo;
import pe.datasys.repo.IMenuRepo;
import pe.datasys.service.IMenuService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import pe.datasys.commons.Filter;
import pe.datasys.commons.SortModel;
import pe.datasys.dto.MenuDTO;
import pe.datasys.model.Menu;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl extends CRUDImpl<Menu, Integer> implements IMenuService {

    private final IMenuRepo repo;
    private final EntityManager entityManager;

    @Override
    protected IGenericRepo<Menu, Integer> getRepo() {
        return repo;
    }

    @Override
    public Page<MenuDTO> paginate(Integer page, Integer rowPage, List<Filter> filters, List<SortModel> sorts) {
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
        List<MenuDTO> lista = querySelect.getResultList();

        PageRequest pageable = PageRequest.of(page, rowPage);

        Page<MenuDTO> rtnPage = new PageImpl<>(lista, pageable, total);
        return rtnPage;
    }

    public StringBuilder getSelect() {
        StringBuilder sql = new StringBuilder("SELECT a ");
         return sql;
     }
 
     public StringBuilder getFrom() {
         StringBuilder sql = new StringBuilder(" FROM Menu a ");
         return sql;
     }
 
     public StringBuilder getFilters(List<Filter> filters) {
         StringBuilder sql = new StringBuilder("where 1=1 ");
 
         for (Filter filtro : filters) {
 
             if (filtro.getField().equals("idMenu")) {
                 sql.append(getFiltersColumns("a.idMenu", filtro.getMatchMode(), ":idMenu"));
             }
             
             if (filtro.getField().equals("name")) {
                 sql.append(getFiltersColumns("a.name", filtro.getMatchMode(), ":name"));
             }

             if (filtro.getField().equals("grupo")) {
                 sql.append(getFiltersColumns("a.grupo", filtro.getMatchMode(), ":grupo"));
             }

             if (filtro.getField().equals("nivel")) {
                 sql.append(getFiltersColumns("a.nivel", filtro.getMatchMode(), ":nivel"));
             }
         }
 
         return sql;
     }
 
     public Query setParams(List<Filter> filters, Query query) {
         for (Filter filtro : filters) {
             if (filtro.getField().equals("idMenu")) {
                 query.setParameter("idMenu", filtro.getValue());
             }
             if (filtro.getField().equals("name")) {
                 query.setParameter("name", filtro.getValue());
             }
             if (filtro.getField().equals("grupo")) {
                 query.setParameter("grupo", filtro.getValue());
             }
             if (filtro.getField().equals("nivel")) {
                 query.setParameter("nivel", filtro.getValue());
             }
         }
 
         return query;
     }
 
     public StringBuilder getOrder(List<SortModel> sorts) {
 
         String preSql = "";
 
         for (SortModel sort : sorts) {
             if (sort.getField().equals("idMenu")) {
                 preSql = " ORDER BY a.idMenu " + sort.getSort();
             }
             if (sort.getField().equals("name")) {
                 preSql = " ORDER BY a.name " + sort.getSort();
             }
             if (sort.getField().equals("grupo")) {
                 preSql = " ORDER BY a.grupo " + sort.getSort();
             }
             if (sort.getField().equals("nivel")) {
                 preSql = " ORDER BY a.nivel " + sort.getSort();
             }
         }
 
         StringBuilder sql = new StringBuilder(preSql);
 
         return sql;
     }

    @Override
    public List<Menu> getMenusByUsername(String username) {
        // String contextSessionUser = SecurityContextHolder.getContext().getAuthentication().getName();
        // return repo.getMenusByUsername(contextSessionUser);
        return repo.getMenusByUsername(username);
    }
}
