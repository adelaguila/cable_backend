package pe.datasys.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import pe.datasys.model.UbigeoEntity;

public interface IUbigeoRepo extends IGenericRepo<UbigeoEntity, String> {
    @Query("FROM UbigeoEntity u WHERE u.distrito LIKE %:term% ORDER BY u.distrito, u.provincia")
    List<UbigeoEntity> autocomplete(String term);
}
