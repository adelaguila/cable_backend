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
import pe.datasys.dto.UbigeoDTO;
import pe.datasys.model.UbigeoEntity;
import pe.datasys.repo.IGenericRepo;
import pe.datasys.repo.IUbigeoRepo;
import pe.datasys.service.IUbigeoService;

@Service
@RequiredArgsConstructor
public class UbigeoServiceImpl extends CRUDImpl<UbigeoEntity, String> implements IUbigeoService {

    private final IUbigeoRepo repo;
    private final EntityManager entityManager;

    @Override
    protected IGenericRepo<UbigeoEntity, String> getRepo() {
        return repo;
    }

    @Override
    public Page<UbigeoDTO> paginate(Integer page, Integer rowPage, List<Filter> filters, List<SortModel> sorts) {
        String sqlCount = "SELECT count(a) " + getFrom().toString() + getFilters(filters).toString();

        String sqlSelect = getSelect().toString() + getFrom().toString() + getFilters(filters).toString()
                + getOrder(sorts).toString();
        // log.info("AQUI " + sqlSelect );
        Query queryCount = entityManager.createQuery(sqlCount);
        Query querySelect = entityManager.createQuery(sqlSelect);

        queryCount = this.setParams(filters, queryCount);

        querySelect = this.setParams(filters, querySelect);

        Long total = (long) queryCount.getSingleResult();

        querySelect.setFirstResult((page) * rowPage);
        querySelect.setMaxResults(rowPage);

        @SuppressWarnings("unchecked")
        List<UbigeoDTO> lista = querySelect.getResultList();

        PageRequest pageable = PageRequest.of(page, rowPage);

        Page<UbigeoDTO> rtnPage = new PageImpl<>(lista, pageable, total);
        return rtnPage;
    }

    public StringBuilder getSelect() {
        StringBuilder sql = new StringBuilder(
                "SELECT new pe.datasys.dto.UbigeoDTO(a.codigo, a.departamento, a.provincia, a.distrito, a.capital) ");
        return sql;
    }

    public StringBuilder getFrom() {
        StringBuilder sql = new StringBuilder(" FROM UbigeoEntity a  ");
        return sql;
    }

    public StringBuilder getFilters(List<Filter> filters) {
        StringBuilder sql = new StringBuilder("where 1=1 ");

        for (Filter filtro : filters) {

            if (filtro.getField().equals("codigo")) {
                sql.append(getFiltersColumns("a.codigo", filtro.getMatchMode(), ":codigo"));
            }
            if (filtro.getField().equals("departamento")) {
                sql.append(getFiltersColumns("a.departamento", filtro.getMatchMode(), ":departamento"));
            }
            if (filtro.getField().equals("provincia")) {
                sql.append(getFiltersColumns("a.provincia", filtro.getMatchMode(), ":provincia"));
            }
            if (filtro.getField().equals("distrito")) {
                sql.append(getFiltersColumns("a.distrito", filtro.getMatchMode(), ":distrito"));
            }
            if (filtro.getField().equals("capital")) {
                 sql.append(getFiltersColumns("a.capital", filtro.getMatchMode(), ":capital"));
            }
        }

        return sql;
    }

    public Query setParams(List<Filter> filters, Query query) {
        for (Filter filtro : filters) {
            if (filtro.getField().equals("codigo")) {
                query.setParameter("codigo", filtro.getValue());
            }
            if (filtro.getField().equals("departamento")) {
                query.setParameter("departamento", filtro.getValue());
            }
            if (filtro.getField().equals("provincia")) {
                query.setParameter("provincia", filtro.getValue());
            }
            if (filtro.getField().equals("distrito")) {
                query.setParameter("distrito", filtro.getValue());
            }
            if (filtro.getField().equals("capital")) {
                query.setParameter("capital", filtro.getValue());
            }
        }

        return query;
    }

    // public StringBuilder getOrder() {
    // StringBuilder sql = new StringBuilder("");
    // return sql;
    // }

    public StringBuilder getOrder(List<SortModel> sorts) {

        String preSql = "";

        for (SortModel sort : sorts) {
            if (sort.getField().equals("codigo")) {
                preSql = " ORDER BY a.codigo " + sort.getSort();
            }
            if (sort.getField().equals("departamento")) {
                preSql = " ORDER BY a.departamento " + sort.getSort();
            }
            if (sort.getField().equals("provincia")) {
                preSql = " ORDER BY a.provincia " + sort.getSort();
            }
            if (sort.getField().equals("distrito")) {
                preSql = " ORDER BY a.distrito " + sort.getSort();
            }
            if (sort.getField().equals("capital")) {
                preSql = " ORDER BY a.capital " + sort.getSort();
            }
        }

        StringBuilder sql = new StringBuilder(preSql);

        return sql;
    }

    @Override
    public List<UbigeoEntity> autocomplete(String term) {
        return repo.autocomplete(term);
    }

}
