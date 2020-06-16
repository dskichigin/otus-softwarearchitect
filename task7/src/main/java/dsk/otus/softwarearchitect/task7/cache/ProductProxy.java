package dsk.otus.softwarearchitect.task7.cache;

import com.github.javafaker.Faker;
import dsk.otus.softwarearchitect.task7.entity.ProductEntity;
import dsk.otus.softwarearchitect.task7.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Component
@CacheConfig(cacheNames={"products"})
public class ProductProxy {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ConfigurableApplicationContext applicationContex;

//    @CachePut(value = "products", key = "#product.id")
    public ProductEntity createProduct(ProductEntity product) {
        product.setId(productRepository.generateId());
        product.setNew(true);
        productRepository.save(product);
        return product;
    }

    @Cacheable(value = "products", key = "#id")
    public ProductEntity getProduct(Long id) {
        Optional<ProductEntity> result = productRepository.findById(id);
        if (result.isPresent()) return result.get();

        return null;
    }

//    @Cacheable("products")
    public List<ProductEntity> getProducts() {
        Iterable<ProductEntity> result = productRepository.findAll();
        List<ProductEntity> products = new ArrayList();
        result.forEach(products::add);

        return products;
    }
    public List<ProductEntity> findProductsByFilter(String filter) {
        List<Long> ids = productRepository.findByFilter("%"+filter.toLowerCase()+"%");

        ProductProxy productProxy = applicationContex.getBean(ProductProxy.class);
        List<ProductEntity> products = new ArrayList<>();
        for (Long id : ids) {
            products.add(productProxy.getProduct(id));
        }
        return products;
    }

    @CachePut(value = "products", key = "#product.id")
    public ProductEntity updateProduct(ProductEntity product) {
        Optional<ProductEntity> result = productRepository.findById(product.getId());
        if (result.isPresent()) {
            product.setNew(false);
            productRepository.save(product);
        }
        return product;
    }

    @CacheEvict(value = "products", key = "#id")
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
    @CacheEvict(value = "products", allEntries=true)
    public void deleteProductAll() {
        productRepository.deleteAll();
    }

//    @Cacheable("products")
    public List<ProductEntity> fillProducts(Long count) {
        Faker faker = new Faker();
        Random random = new Random();
        ArrayList<ProductEntity> products = new ArrayList<>();

        ProductProxy productProxy = applicationContex.getBean(ProductProxy.class);
        productProxy.deleteProductAll();
        productRepository.clearId();

        for (Long i=0L; i<count; i++) {
            ProductEntity product = new ProductEntity();
            product.setId(i);
            product.setName(faker.commerce().productName());
            product.setPrice(Double.valueOf(faker.commerce().price().replace(',','.')));
            product.setColor(faker.commerce().color());
            product.setDescription(faker.lorem().sentence(random.nextInt(5)));
            productRepository.save(product);
            products.add(product);
        }
        return products;
    }
}
