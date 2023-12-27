package pe.datasys.repo;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;
import pe.datasys.model.User;

public interface IUserRepo extends IGenericRepo<User, Integer>  {

    //@Query("FROM User u WHERE u.username = ?")
    //Derived Query
    User findOneByUsername(String username);

    @Transactional
    @Modifying
    @Query("UPDATE User us SET us.password =:password WHERE us.username =:username")
    void changePassword(@Param("password") String password, @Param("username") String username);
}
