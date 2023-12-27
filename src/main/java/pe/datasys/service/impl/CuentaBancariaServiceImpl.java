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
import pe.datasys.dto.CuentaBancariaDTO;
import pe.datasys.model.CuentaBancariaEntity;
import pe.datasys.repo.ICuentaBancariaRepo;
import pe.datasys.repo.IGenericRepo;

import pe.datasys.service.ICuentaBancariaService;


@Service
@RequiredArgsConstructor
public class CuentaBancariaServiceImpl extends CRUDImpl<CuentaBancariaEntity, String> implements ICuentaBancariaService {

    private final ICuentaBancariaRepo repo;
    private final EntityManager entityManager;

    @Override
    protected IGenericRepo<CuentaBancariaEntity, String> getRepo() {
        return repo;
    }

    @Override
    public Page<CuentaBancariaDTO> paginate(Integer page, Integer rowPage, List<Filter> filters, List<SortModel> sorts) {
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
        List<CuentaBancariaDTO> lista = querySelect.getResultList();

        PageRequest pageable = PageRequest.of(page, rowPage);

        Page<CuentaBancariaDTO> rtnPage = new PageImpl<>(lista, pageable, total);
        return rtnPage;
    }

    public StringBuilder getSelect() {
        StringBuilder sql = new StringBuilder(
                "SELECT a ");
        return sql;
    }

    public StringBuilder getFrom() {
        StringBuilder sql = new StringBuilder(" FROM CuentaBancariaEntity a  ");
        return sql;
    }

    public StringBuilder getFilters(List<Filter> filters) {
        StringBuilder sql = new StringBuilder("where 1=1 ");

        for (Filter filtro : filters) {

            if (filtro.getField().equals("numeroCuenta")) {
                sql.append(getFiltersColumns("a.numeroCuenta", filtro.getMatchMode(), ":numeroCuenta"));
            }
            // if (filtro.getField().equals("nombreCuentaBancaria")) {
            //     sql.append(getFiltersColumns("a.nombreCuentaBancaria", filtro.getMatchMode(), ":nombreCuentaBancaria"));
            // }
        }

        return sql;
    }

    public Query setParams(List<Filter> filters, Query query) {
        for (Filter filtro : filters) {
            if (filtro.getField().equals("numeroCuenta")) {
                query.setParameter("numeroCuenta", filtro.getValue());
            }
            // if (filtro.getField().equals("nombreCuentaBancaria")) {
            //     query.setParameter("nombreCuentaBancaria", filtro.getValue());
            // }
        }

        return query;
    }

    public StringBuilder getOrder(List<SortModel> sorts) {

        String preSql = "";

        for (SortModel sort : sorts) {
            if (sort.getField().equals("numeroCuenta")) {
                preSql = " ORDER BY a.numeroCuenta " + sort.getSort();
            }
            // if (sort.getField().equals("nombreCuentaBancaria")) {
            //     preSql = " ORDER BY a.nombreCuentaBancaria " + sort.getSort();
            // }
        }

        StringBuilder sql = new StringBuilder(preSql);

        return sql;
    }

}
