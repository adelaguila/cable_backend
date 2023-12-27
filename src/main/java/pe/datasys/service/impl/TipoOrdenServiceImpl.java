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
import pe.datasys.dto.TipoOrdenDTO;
import pe.datasys.model.TipoOrdenEntity;
import pe.datasys.repo.IGenericRepo;
import pe.datasys.repo.ITipoOrdenRepo;
import pe.datasys.service.ITipoOrdenService;

@Service
@RequiredArgsConstructor
public class TipoOrdenServiceImpl extends CRUDImpl<TipoOrdenEntity, Integer> implements ITipoOrdenService{

    private final ITipoOrdenRepo repo;
    private final EntityManager entityManager;

    @Override
    protected IGenericRepo<TipoOrdenEntity, Integer> getRepo() {
       return repo;
    }

    @Override
    public Page<TipoOrdenDTO> paginate(Integer page, Integer rowPage, List<Filter> filters, List<SortModel> sorts) {
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
        List<TipoOrdenDTO> lista = querySelect.getResultList();

        PageRequest pageable = PageRequest.of(page, rowPage);

        Page<TipoOrdenDTO> rtnPage = new PageImpl<>(lista, pageable, total);
        return rtnPage;
    }
    
    public StringBuilder getSelect() {
        StringBuilder sql = new StringBuilder("SELECT a ");
        return sql;
    }

    public StringBuilder getFrom() {
        StringBuilder sql = new StringBuilder(" FROM TipoOrdenEntity a ");
        return sql;
    }

    public StringBuilder getFilters(List<Filter> filters) {
        StringBuilder sql = new StringBuilder("where 1=1 ");

        for (Filter filtro : filters) {

            if (filtro.getField().equals("idTipoOrden")) {
                sql.append(getFiltersColumns("a.idTipoOrden", filtro.getMatchMode(), ":idTipoOrden"));
            }
            
            if (filtro.getField().equals("nombreTipoOrden")) {
                sql.append(getFiltersColumns("a.nombreTipoOrden", filtro.getMatchMode(), ":nombreTipoOrden"));
            }
        }

        return sql;
    }

    public Query setParams(List<Filter> filters, Query query) {
        for (Filter filtro : filters) {
            if (filtro.getField().equals("idTipoOrden")) {
                query.setParameter("idTipoOrden", filtro.getValue());
            }
            if (filtro.getField().equals("nombreTipoOrden")) {
                query.setParameter("nombreTipoOrden", filtro.getValue());
            }
        }

        return query;
    }

    public StringBuilder getOrder(List<SortModel> sorts) {

        String preSql = "";

        for (SortModel sort : sorts) {
            if (sort.getField().equals("idTipoOrden")) {
                preSql = " ORDER BY a.idTipoOrden " + sort.getSort();
            }
            if (sort.getField().equals("nombreTipoOrden")) {
                preSql = " ORDER BY a.nombreTipoOrden " + sort.getSort();
            }
        }

        StringBuilder sql = new StringBuilder(preSql);

        return sql;
    }

    @Override
    public List<TipoOrdenEntity> findUsoEstadoAbonado(String usoEstadoAbonado) {
        return repo.findByUsoEstadoAbonado(usoEstadoAbonado);
    }
}
