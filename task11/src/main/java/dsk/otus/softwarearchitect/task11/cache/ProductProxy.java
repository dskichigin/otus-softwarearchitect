package dsk.otus.softwarearchitect.task11.cache;

import com.github.javafaker.Faker;
import dsk.otus.softwarearchitect.task11.entity.ProductEntity;
import dsk.otus.softwarearchitect.task11.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@CacheConfig(cacheNames={"products"})
public class ProductProxy {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ConfigurableApplicationContext applicationContext;

//    @CachePut(value = "products", key = "#product.id")
    public ProductEntity createProduct(ProductEntity product) throws Exception {
        productRepository.insert(product);
        return product;
    }

    @Cacheable(value = "products", key = "#guid")
    public ProductEntity getProduct(String guid) throws Exception {
        return productRepository.findById(guid);
    }

//    @Cacheable("products")
    public List<ProductEntity> getProducts() {
        return productRepository.findAll();
    }
    public List<ProductEntity> findProductsByFilter(String filter) throws Exception {
        List<String> ids = productRepository.findByFilter("%"+filter.toLowerCase()+"%");

        ProductProxy productProxy = applicationContext.getBean(ProductProxy.class);
        List<ProductEntity> products = new ArrayList<>();
        for (String id : ids) {
            products.add(productProxy.getProduct(id));
        }
        return products;
    }

    @CachePut(value = "products", key = "#product.id")
    public ProductEntity updateProduct(ProductEntity product) throws Exception {
        ProductEntity result = productRepository.findById(product.getGuid());
        if (result != null)
            productRepository.update(product);
        return product;
    }

    @CacheEvict(value = "products", key = "#id")
    public void deleteProduct(String guid) throws Exception {
        productRepository.deleteById(guid);
    }
    @CacheEvict(value = "products", allEntries=true)
    public void deleteProductAll() {
        productRepository.deleteAll();
    }

//    @Cacheable("products")
    public List<ProductEntity> fillProducts(Long count) throws Exception {
        Faker faker = new Faker();
        Random random = new Random();
        List<ProductEntity> products = new ArrayList<>();

        ProductProxy productProxy = applicationContext.getBean(ProductProxy.class);
        productProxy.deleteProductAll();

        for (Long i=0L; i<count; i++) {
            ProductEntity product = new ProductEntity();
            product.setGuid(java.util.UUID.randomUUID().toString());
            product.setName(faker.commerce().productName());
            product.setPrice(Double.valueOf(faker.commerce().price().replace(',','.')));
            product.setColor(faker.commerce().color());
            product.setDescription(faker.lorem().sentence(random.nextInt(5)));
            productRepository.insert(product);
            products.add(product);
        }
        return products;
    }
}
