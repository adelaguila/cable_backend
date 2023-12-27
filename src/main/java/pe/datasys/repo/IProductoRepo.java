package pe.datasys.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import pe.datasys.model.ProductoEntity;

public interface IProductoRepo extends IGenericRepo<ProductoEntity, Integer> {
    @Query("FROM ProductoEntity u WHERE u.nombreProducto LIKE %:term% ORDER BY u.nombreProducto")
    List<ProductoEntity> autocomplete(String term);
}
