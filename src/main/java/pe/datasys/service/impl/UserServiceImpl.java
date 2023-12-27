package pe.datasys.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import pe.datasys.commons.Filter;
import pe.datasys.commons.SortModel;
import pe.datasys.dto.UserDTO;
import pe.datasys.model.User;
import pe.datasys.repo.IGenericRepo;
import pe.datasys.repo.IUserRepo;
import pe.datasys.service.IUserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends CRUDImpl<User, Integer> implements IUserService {

    private final IUserRepo repo;
    private final EntityManager entityManager;
    private final PasswordEncoder bcrypt;

    @Override
    protected IGenericRepo<User, Integer> getRepo() {
        return repo;
    }

    @Override
    public Page<UserDTO> paginate(Integer page, Integer rowPage, List<Filter> filters, List<SortModel> sorts) {
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
        List<UserDTO> lista = querySelect.getResultList();

        PageRequest pageable = PageRequest.of(page, rowPage);

        Page<UserDTO> rtnPage = new PageImpl<>(lista, pageable, total);
        return rtnPage;
    }

    public StringBuilder getSelect() {
        StringBuilder sql = new StringBuilder("SELECT a ");
        return sql;
    }

    public StringBuilder getFrom() {
        StringBuilder sql = new StringBuilder(" FROM User a ");
        return sql;
    }

    public StringBuilder getFilters(List<Filter> filters) {
        StringBuilder sql = new StringBuilder("where 1=1 ");

        for (Filter filtro : filters) {

            if (filtro.getField().equals("idUser")) {
                sql.append(getFiltersColumns("a.idUser", filtro.getMatchMode(), ":idUser"));
            }

            if (filtro.getField().equals("username")) {
                sql.append(getFiltersColumns("a.username", filtro.getMatchMode(), ":username"));
            }

            if (filtro.getField().equals("name")) {
                sql.append(getFiltersColumns("a.name", filtro.getMatchMode(), ":name"));
            }
        }

        return sql;
    }

    public Query setParams(List<Filter> filters, Query query) {
        for (Filter filtro : filters) {
            if (filtro.getField().equals("idUser")) {
                query.setParameter("idUser", filtro.getValue());
            }
            if (filtro.getField().equals("username")) {
                query.setParameter("username", filtro.getValue());
            }
            if (filtro.getField().equals("name")) {
                query.setParameter("name", filtro.getValue());
            }
        }

        return query;
    }

    public StringBuilder getOrder(List<SortModel> sorts) {

        String preSql = "";

        for (SortModel sort : sorts) {
            if (sort.getField().equals("idUser")) {
                preSql = " ORDER BY a.idUser " + sort.getSort();
            }
            if (sort.getField().equals("username")) {
                preSql = " ORDER BY a.username " + sort.getSort();
            }
            if (sort.getField().equals("name")) {
                preSql = " ORDER BY a.name " + sort.getSort();
            }
        }

        StringBuilder sql = new StringBuilder(preSql);

        return sql;
    }

    @Override
    public User findOneByUsername(String username) {
        return repo.findOneByUsername(username);
    }

    @Override
    public void changePassword(String password, String username) {
        repo.changePassword(bcrypt.encode(password), username);
    }

}
