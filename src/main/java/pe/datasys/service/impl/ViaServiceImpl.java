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
import pe.datasys.dto.ViaDTO;
import pe.datasys.model.ViaEntity;
import pe.datasys.repo.IGenericRepo;
import pe.datasys.repo.IViaRepo;
import pe.datasys.service.IViaService;

@Service
@RequiredArgsConstructor
public class ViaServiceImpl extends CRUDImpl<ViaEntity, Integer> implements IViaService{

    private final IViaRepo repo;
    private final EntityManager entityManager;

    @Override
    protected IGenericRepo<ViaEntity, Integer> getRepo() {
       return repo;
    }

    @Override
    public Page<ViaDTO> paginate(Integer page, Integer rowPage, List<Filter> filters, List<SortModel> sorts) {
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
        List<ViaDTO> lista = querySelect.getResultList();

        PageRequest pageable = PageRequest.of(page, rowPage);

        Page<ViaDTO> rtnPage = new PageImpl<>(lista, pageable, total);
        return rtnPage;
    }

    public StringBuilder getSelect() {
       StringBuilder sql = new StringBuilder("SELECT a ");
        return sql;
    }

    public StringBuilder getFrom() {
        StringBuilder sql = new StringBuilder(" FROM ViaEntity a ");
        return sql;
    }

    public StringBuilder getFilters(List<Filter> filters) {
        StringBuilder sql = new StringBuilder("where 1=1 ");

        for (Filter filtro : filters) {

            if (filtro.getField().equals("idVia")) {
                sql.append(getFiltersColumns("a.idVia", filtro.getMatchMode(), ":idVia"));
            }
            
            if (filtro.getField().equals("nombreVia")) {
                sql.append(getFiltersColumns("a.nombreVia", filtro.getMatchMode(), ":nombreVia"));
            }
            if (filtro.getField().equals("nombreTipoVia")) {
                sql.append(getFiltersColumns("a.tipoVia.nombreTipoVia", filtro.getMatchMode(), ":nombreTipoVia"));
            }
        }

        return sql;
    }

    public Query setParams(List<Filter> filters, Query query) {
        for (Filter filtro : filters) {
            if (filtro.getField().equals("idVia")) {
                query.setParameter("idVia", filtro.getValue());
            }
            if (filtro.getField().equals("nombreVia")) {
                query.setParameter("nombreVia", filtro.getValue());
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
            if (sort.getField().equals("idVia")) {
                preSql = " ORDER BY a.idVia " + sort.getSort();
            }
            if (sort.getField().equals("nombreVia")) {
                preSql = " ORDER BY a.nombreVia " + sort.getSort();
            }
            if (sort.getField().equals("nombreTipoVia")) {
                preSql = " ORDER BY a.tipoVia.nombreTipoVia " + sort.getSort();
            }
        }

        StringBuilder sql = new StringBuilder(preSql);

        return sql;
    }

        
}
