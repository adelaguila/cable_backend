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
import pe.datasys.dto.TipoCargoDTO;
import pe.datasys.model.TipoCargoEntity;
import pe.datasys.repo.IGenericRepo;
import pe.datasys.repo.ITipoCargoRepo;
import pe.datasys.service.ITipoCargoService;


@Service
@RequiredArgsConstructor
public class TipoCargoServiceImpl extends CRUDImpl<TipoCargoEntity, Integer> implements ITipoCargoService{

    private final ITipoCargoRepo repo;
    private final EntityManager entityManager;

    @Override
    protected IGenericRepo<TipoCargoEntity, Integer> getRepo() {
       return repo;
    }

    @Override
    public Page<TipoCargoDTO> paginate(Integer page, Integer rowPage, List<Filter> filters, List<SortModel> sorts) {
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
        List<TipoCargoDTO> lista = querySelect.getResultList();

        PageRequest pageable = PageRequest.of(page, rowPage);

        Page<TipoCargoDTO> rtnPage = new PageImpl<>(lista, pageable, total);
        return rtnPage;
    }
    
    public StringBuilder getSelect() {
        StringBuilder sql = new StringBuilder("SELECT a ");
        return sql;
    }

    public StringBuilder getFrom() {
        StringBuilder sql = new StringBuilder(" FROM TipoCargoEntity a ");
        return sql;
    }

    public StringBuilder getFilters(List<Filter> filters) {
        StringBuilder sql = new StringBuilder("where 1=1 ");

        for (Filter filtro : filters) {

            if (filtro.getField().equals("idTipoCargo")) {
                sql.append(getFiltersColumns("a.idTipoCargo", filtro.getMatchMode(), ":idTipoCargo"));
            }
            
            if (filtro.getField().equals("nombreTipoCargo")) {
                sql.append(getFiltersColumns("a.nombreTipoCargo", filtro.getMatchMode(), ":nombreTipoCargo"));
            }
        }

        return sql;
    }

    public Query setParams(List<Filter> filters, Query query) {
        for (Filter filtro : filters) {
            if (filtro.getField().equals("idTipoCargo")) {
                query.setParameter("idTipoCargo", filtro.getValue());
            }
            if (filtro.getField().equals("nombreTipoCargo")) {
                query.setParameter("nombreTipoCargo", filtro.getValue());
            }
        }

        return query;
    }

    public StringBuilder getOrder(List<SortModel> sorts) {

        String preSql = "";

        for (SortModel sort : sorts) {
            if (sort.getField().equals("idTipoCargo")) {
                preSql = " ORDER BY a.idTipoCargo " + sort.getSort();
            }
            if (sort.getField().equals("nombreTipoCargo")) {
                preSql = " ORDER BY a.nombreTipoCargo " + sort.getSort();
            }
        }

        StringBuilder sql = new StringBuilder(preSql);

        return sql;
    }
}
