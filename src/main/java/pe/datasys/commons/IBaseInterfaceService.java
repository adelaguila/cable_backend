package pe.datasys.commons;

import java.util.List;

import org.springframework.data.domain.Page;

public interface IBaseInterfaceService<T,ID> {

	public Page<?> pagination(Integer pagenumber, Integer rows, List<SortModel> sortModel, Filter filter );
	
	public T create(T entidad);
	
    public T save(T entidad);
	
	public T readById(ID id);

	public T findById(ID id);
	
	public T update(T entidad, ID id);
	
	public void delete(T entidad);
	
	public void deleteById(ID id);
	
	public Boolean exists(ID id);
	
	public Long count();
	
	public List<T> getAll();

	public List<T> findAll();
	

    
}
