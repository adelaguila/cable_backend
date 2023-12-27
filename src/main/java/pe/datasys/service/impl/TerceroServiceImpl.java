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
import pe.datasys.dto.TerceroDTO;
import pe.datasys.model.TerceroEntity;
import pe.datasys.repo.IGenericRepo;
import pe.datasys.repo.ITerceroRepo;
import pe.datasys.service.ITerceroService;

@Service
@RequiredArgsConstructor
public class TerceroServiceImpl extends CRUDImpl<TerceroEntity, Long> implements ITerceroService{

    private final ITerceroRepo repo;
    private final EntityManager entityManager;

    @Override
    protected IGenericRepo<TerceroEntity, Long> getRepo() {
       return repo;
    }

    @Override
    public Page<TerceroDTO> paginate(Integer page, Integer rowPage, List<Filter> filters, List<SortModel> sorts) {
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
        List<TerceroDTO> lista = querySelect.getResultList();

        PageRequest pageable = PageRequest.of(page, rowPage);

        Page<TerceroDTO> rtnPage = new PageImpl<>(lista, pageable, total);
        return rtnPage;
    }

    public StringBuilder getSelect() {
        StringBuilder sql = new StringBuilder(
                "SELECT new pe.datasys.dto.TerceroDTO(a.idTercero, a.dniruc, a.nombreTercero, a.telefono1, a.telefono2, a.correoElectronico) ");
        return sql;
    }

    public StringBuilder getFrom() {
        StringBuilder sql = new StringBuilder(" FROM TerceroEntity a  ");
        return sql;
    }

    public StringBuilder getFilters(List<Filter> filters) {
        StringBuilder sql = new StringBuilder("where 1=1 ");

        for (Filter filtro : filters) {

            if (filtro.getField().equals("idTercero")) {
                sql.append(getFiltersColumns("a.idTercero", filtro.getMatchMode(), ":idTercero"));
            }
            if (filtro.getField().equals("dniruc")) {
                sql.append(getFiltersColumns("a.dniruc", filtro.getMatchMode(), ":dniruc"));
            }
            if (filtro.getField().equals("nombreTercero")) {
                sql.append(getFiltersColumns("a.nombreTercero", filtro.getMatchMode(), ":nombreTercero"));
            }
        }

        return sql;
    }

    public Query setParams(List<Filter> filters, Query query) {
        for (Filter filtro : filters) {
            if (filtro.getField().equals("idTercero")) {
                query.setParameter("idTercero", filtro.getValue());
            }
            if (filtro.getField().equals("dniruc")) {
                query.setParameter("dniruc", filtro.getValue());
            }
            if (filtro.getField().equals("nombreTercero")) {
                query.setParameter("nombreTercero", filtro.getValue());
            }
        }

        return query;
    }

    public StringBuilder getOrder(List<SortModel> sorts) {

        String preSql = "";

        for (SortModel sort : sorts) {
            if (sort.getField().equals("idTercero")) {
                preSql = " ORDER BY a.idTercero " + sort.getSort();
            }
            if (sort.getField().equals("dniruc")) {
                preSql = " ORDER BY a.dniruc " + sort.getSort();
            }
            if (sort.getField().equals("nombreTercero")) {
                preSql = " ORDER BY a.nombreTercero " + sort.getSort();
            }
        }

        StringBuilder sql = new StringBuilder(preSql);

        return sql;
    }

    @Override
    public TerceroEntity checkDniruc(String dniruc) {
        return repo.checkDniruc(dniruc);
    }

    @Override
    public List<TerceroEntity> autocomplete(String term) {
        return repo.autocomplete(term);
    }

        
}
