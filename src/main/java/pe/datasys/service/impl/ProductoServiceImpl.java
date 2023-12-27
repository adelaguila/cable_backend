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
import pe.datasys.dto.ProductoDTO;
import pe.datasys.model.ProductoEntity;
import pe.datasys.repo.IGenericRepo;
import pe.datasys.repo.IProductoRepo;
import pe.datasys.service.IProductoService;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl extends CRUDImpl<ProductoEntity, Integer> implements IProductoService{

    private final IProductoRepo repo;
    private final EntityManager entityManager;

    @Override
    protected IGenericRepo<ProductoEntity, Integer> getRepo() {
       return repo;
    }

    @Override
    public Page<ProductoDTO> paginate(Integer page, Integer rowPage, List<Filter> filters, List<SortModel> sorts) {
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
        List<ProductoDTO> lista = querySelect.getResultList();

        PageRequest pageable = PageRequest.of(page, rowPage);

        Page<ProductoDTO> rtnPage = new PageImpl<>(lista, pageable, total);
        return rtnPage;
    }
    
    public StringBuilder getSelect() {
        StringBuilder sql = new StringBuilder("SELECT a ");
         return sql;
     }
 
     public StringBuilder getFrom() {
         StringBuilder sql = new StringBuilder(" FROM ProductoEntity a ");
         return sql;
     }
 
     public StringBuilder getFilters(List<Filter> filters) {
         StringBuilder sql = new StringBuilder("where 1=1 ");
 
         for (Filter filtro : filters) {
 
             if (filtro.getField().equals("idProducto")) {
                 sql.append(getFiltersColumns("a.idProducto", filtro.getMatchMode(), ":idProducto"));
             }
             
             if (filtro.getField().equals("nombreProducto")) {
                 sql.append(getFiltersColumns("a.nombreProducto", filtro.getMatchMode(), ":nombreProducto"));
             }
         }
 
         return sql;
     }
 
     public Query setParams(List<Filter> filters, Query query) {
         for (Filter filtro : filters) {
             if (filtro.getField().equals("idProducto")) {
                 query.setParameter("idProducto", filtro.getValue());
             }
             if (filtro.getField().equals("nombreProducto")) {
                 query.setParameter("nombreProducto", filtro.getValue());
             }
         }
 
         return query;
     }
 
     public StringBuilder getOrder(List<SortModel> sorts) {
 
         String preSql = "";
 
         for (SortModel sort : sorts) {
             if (sort.getField().equals("idProducto")) {
                 preSql = " ORDER BY a.idProducto " + sort.getSort();
             }
             if (sort.getField().equals("nombreProducto")) {
                 preSql = " ORDER BY a.nombreProducto " + sort.getSort();
             }
         }
 
         StringBuilder sql = new StringBuilder(preSql);
 
         return sql;
     }

    @Override
    public List<ProductoEntity> autocomplete(String term) {
        return repo.autocomplete(term);
    }
 
}
