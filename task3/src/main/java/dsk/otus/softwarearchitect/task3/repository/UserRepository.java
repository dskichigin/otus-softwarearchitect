package dsk.otus.softwarearchitect.task3.repository;

import dsk.otus.softwarearchitect.task3.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, String> { }