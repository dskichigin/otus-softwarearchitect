package dsk.otus.softwarearchitect.task7.repository;

import dsk.otus.softwarearchitect.task7.entity.ProductEntity;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends CrudRepository<ProductEntity, Long> {
    @Query("select id from products where lower(name) like :filter or lower(color) like :filter or lower(category) like :filter or lower(description) like :filter limit 100")
    List<Long> findByFilter(@Param("filter") String filter);

    @Query("select nextval('seq_products')")
    Long generateId();

    @Query("SELECT setval('seq_products', 1, true)")
    Long clearId();

    @Query("SELECT count(id) from products")
    Long countRows();
}