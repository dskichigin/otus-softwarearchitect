package dsk.otus.softwarearchitect.task6.repository;

import dsk.otus.softwarearchitect.task6.entity.ProductEntity;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends CrudRepository<ProductEntity, String> {
    @Query("select id from products where lower(name) like :filter or lower(color) like :filter or lower(category) like :filter or lower(description) like :filter limit 100")
    List<String> findByFilter(@Param("filter") String filter);
}