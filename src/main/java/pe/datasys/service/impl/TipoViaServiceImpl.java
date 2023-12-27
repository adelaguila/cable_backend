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
import pe.datasys.dto.TipoViaDTO;
import pe.datasys.model.TipoViaEntity;
import pe.datasys.repo.IGenericRepo;
import pe.datasys.repo.ITipoViaRepo;
import pe.datasys.service.ITipoViaService;

@Service
@RequiredArgsConstructor
public class TipoViaServiceImpl extends CRUDImpl<TipoViaEntity, String> implements ITipoViaService {

    private final ITipoViaRepo repo;
    private final EntityManager entityManager;

    @Override
    protected IGenericRepo<TipoViaEntity, String> getRepo() {
        return repo;
    }

    @Override
    public Page<TipoViaDTO> paginate(Integer page, Integer rowPage, List<Filter> filters, List<SortModel> sorts) {
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
        List<TipoViaDTO> lista = querySelect.getResultList();

        PageRequest pageable = PageRequest.of(page, rowPage);

        Page<TipoViaDTO> rtnPage = new PageImpl<>(lista, pageable, total);
        return rtnPage;
    }

    public StringBuilder getSelect() {
        StringBuilder sql = new StringBuilder(
                "SELECT a ");
        return sql;
    }

    public StringBuilder getFrom() {
        StringBuilder sql = new StringBuilder(" FROM TipoViaEntity a  ");
        return sql;
    }

    public StringBuilder getFilters(List<Filter> filters) {
        StringBuilder sql = new StringBuilder("where 1=1 ");

        for (Filter filtro : filters) {

            if (filtro.getField().equals("idTipoVia")) {
                sql.append(getFiltersColumns("a.idTipoVia", filtro.getMatchMode(), ":idTipoVia"));
            }
            if (filtro.getField().equals("nombreTipoVia")) {
                sql.append(getFiltersColumns("a.nombreTipoVia", filtro.getMatchMode(), ":nombreTipoVia"));
            }
        }

        return sql;
    }

    public Query setParams(List<Filter> filters, Query query) {
        for (Filter filtro : filters) {
            if (filtro.getField().equals("idTipoVia")) {
                query.setParameter("idTipoVia", filtro.getValue());
            }
            if (filtro.getField().equals("nombreTipoVia")) {
                query.setParameter("nombreTipoVia", filtro.getValue());
            }
        }

        return query;
    }

    public StringBuilder getOrder(List<SortModel> sorts) {

        String preSql = "";

        for (SortModel sort : sorts) {
            if (sort.getField().equals("idTipoVia")) {
                preSql = " ORDER BY a.idTipoVia " + sort.getSort();
            }
            if (sort.getField().equals("nombreTipoVia")) {
                preSql = " ORDER BY a.nombreTipoVia " + sort.getSort();
            }
        }

        StringBuilder sql = new StringBuilder(preSql);

        return sql;
    }

}
