package pe.datasys.service.impl;

import java.util.List;

import pe.datasys.exception.ModelNotFoundException;
import pe.datasys.repo.IGenericRepo;
import pe.datasys.service.ICRUD;

public abstract class CRUDImpl<T, ID> implements ICRUD<T, ID> {

    protected abstract IGenericRepo<T, ID> getRepo();

    @Override
    public T save(T t) {
        return getRepo().save(t);
    }

    @Override
    public T update(T t, ID id) {
        getRepo().findById(id).orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND " + id));
        return getRepo().save(t);
    }

    @Override
    public List<T> findAll() {
        return getRepo().findAll();
    }

    @Override
    public T findById(ID id) {
        return getRepo().findById(id).orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND: " + id));
    }

    @Override
    public void delete(ID id) {
        getRepo().findById(id).orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND " + id));
        getRepo().deleteById(id);
    }

    public String getFiltersColumns(String column, String operator, String parametro) {
        String sql = "";
        if (operator.equals("=")) {
            sql = " AND ".concat(column).concat(" = ").concat(parametro);
        }
        if (operator.equals(">")) {
            sql = " AND ".concat(column).concat(" > ").concat(parametro);
        }
        if (operator.equals(">=")) {
            sql = " AND ".concat(column).concat(" >= ").concat(parametro);
        }
        if (operator.equals("<")) {
            sql = " AND ".concat(column).concat(" < ").concat(parametro);
        }
        if (operator.equals("<")) {
            sql = " AND ".concat(column).concat(" <= ").concat(parametro);
        }
        if (operator.equals("LIKE")) {
            sql = " AND ".concat(column).concat(" LIKE concat(").concat("'%', ").concat(parametro).concat(", '%')");
        }
        return sql;
    }

}
