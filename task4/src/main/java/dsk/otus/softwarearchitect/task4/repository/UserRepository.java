package dsk.otus.softwarearchitect.task4.repository;

import dsk.otus.softwarearchitect.task4.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, String> { }