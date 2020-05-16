package dsk.otus.softwarearchitect.task5.repository;

import dsk.otus.softwarearchitect.task5.entity.UserEntity;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends CrudRepository<UserEntity, String> {
    @Query("select * from users where username = :username")
    List<UserEntity> findByUserName(@Param("username") String username);
}