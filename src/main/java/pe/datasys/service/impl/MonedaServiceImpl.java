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
import pe.datasys.dto.MonedaDTO;
import pe.datasys.model.MonedaEntity;
import pe.datasys.repo.IGenericRepo;
import pe.datasys.repo.IMonedaRepo;
import pe.datasys.service.IMonedaService;

@Service
@RequiredArgsConstructor
public class MonedaServiceImpl extends CRUDImpl<MonedaEntity, String> implements IMonedaService {

    private final IMonedaRepo repo;
    private final EntityManager entityManager;

    @Override
    protected IGenericRepo<MonedaEntity, String> getRepo() {
        return repo;
    }

    @Override
    public Page<MonedaDTO> paginate(Integer page, Integer rowPage, List<Filter> filters, List<SortModel> sorts) {
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
        List<MonedaDTO> lista = querySelect.getResultList();

        PageRequest pageable = PageRequest.of(page, rowPage);

        Page<MonedaDTO> rtnPage = new PageImpl<>(lista, pageable, total);
        return rtnPage;
    }

    public StringBuilder getSelect() {
        StringBuilder sql = new StringBuilder(
                "SELECT a ");
        return sql;
    }

    public StringBuilder getFrom() {
        StringBuilder sql = new StringBuilder(" FROM MonedaEntity a  ");
        return sql;
    }

    public StringBuilder getFilters(List<Filter> filters) {
        StringBuilder sql = new StringBuilder("where 1=1 ");

        for (Filter filtro : filters) {

            if (filtro.getField().equals("codigo")) {
                sql.append(getFiltersColumns("a.codigo", filtro.getMatchMode(), ":codigo"));
            }
            if (filtro.getField().equals("nombreMoneda")) {
                sql.append(getFiltersColumns("a.nombreMoneda", filtro.getMatchMode(), ":nombreMoneda"));
            }
        }

        return sql;
    }

    public Query setParams(List<Filter> filters, Query query) {
        for (Filter filtro : filters) {
            if (filtro.getField().equals("codigo")) {
                query.setParameter("codigo", filtro.getValue());
            }
            if (filtro.getField().equals("nombreMoneda")) {
                query.setParameter("nombreMoneda", filtro.getValue());
            }
        }

        return query;
    }

    public StringBuilder getOrder(List<SortModel> sorts) {

        String preSql = "";

        for (SortModel sort : sorts) {
            if (sort.getField().equals("codigo")) {
                preSql = " ORDER BY a.codigo " + sort.getSort();
            }
            if (sort.getField().equals("nombreMoneda")) {
                preSql = " ORDER BY a.nombreMoneda " + sort.getSort();
            }
        }

        StringBuilder sql = new StringBuilder(preSql);

        return sql;
    }

}
