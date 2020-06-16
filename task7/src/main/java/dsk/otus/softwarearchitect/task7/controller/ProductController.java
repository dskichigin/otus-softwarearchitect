package dsk.otus.softwarearchitect.task7.controller;

import dsk.otus.softwarearchitect.task7.cache.ProductProxy;
import dsk.otus.softwarearchitect.task7.entity.ProductEntity;
import dsk.otus.softwarearchitect.task7.entity.VersionEntity;
import dsk.otus.softwarearchitect.task7.repository.ProductRepository;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@Timed(percentiles = {0.5, 0.95, 0.99, 1}, histogram = true)
public class ProductController {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductProxy productProxy;

    public ProductController() { }

    @PostMapping(path = "/products", produces = "application/json")
    @Counted
    public @ResponseBody
    ResponseEntity<ProductEntity> createProduct(@RequestBody ProductEntity product) {
        return ResponseEntity.ok(productProxy.createProduct(product));
    }

    @GetMapping(path = "/products/{id}", produces = "application/json")
    @Counted
    public @ResponseBody
    ResponseEntity<ProductEntity> getProduct(@PathVariable("id") String id, HttpServletRequest request) throws Exception {
        ProductEntity product = productProxy.getProduct(Long.valueOf(id));
        if (product == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.ok(product);
    }

    @GetMapping(value = "/products", produces = "application/json")
    @Counted
    public ResponseEntity<List<ProductEntity>> getProducts(HttpServletRequest request) {
        return ResponseEntity.ok(productProxy.getProducts());
    }

    @GetMapping(value = "/products/search", produces = "application/json")
    @Counted
    public List<ProductEntity> findProducts(@PathParam("q") String q, HttpServletRequest request) {
        return productProxy.findProductsByFilter(q);
    }


    @PutMapping(path="/products/{id}", consumes = "application/json", produces = "application/json")
    @Counted
    public @ResponseBody ResponseEntity<ProductEntity> updateProduct(@PathVariable("id") String id, @RequestBody ProductEntity product, HttpServletRequest request) {
        product.setId(Long.valueOf(id));
        return ResponseEntity.ok(productProxy.updateProduct(product));
    }

    @DeleteMapping(path="/products/{id}")
    @Counted
    public ResponseEntity deleteProduct(@PathVariable("id") String id, HttpServletRequest request) throws Exception {
        if (id.equals("all")) {
            productProxy.deleteProductAll();
        } else {
            productProxy.deleteProduct(Long.valueOf(id));
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/products/fill", produces = "application/json")
//    @Counted
    public @ResponseBody
    ResponseEntity fillProducts(@PathParam("count") String count, HttpServletRequest request) {
        // заполняем базу
        productProxy.fillProducts(Long.valueOf(count));
        return ResponseEntity.ok().build();
    }
    @GetMapping(path = "/products/count", produces = "application/json")
//    @Counted
    public @ResponseBody
    ResponseEntity<Long> fillProductsCount(HttpServletRequest request) {
        return ResponseEntity.ok(productRepository.countRows());
    }


    @GetMapping(path = "/version", produces = "application/json")
    @Counted
    public VersionEntity getVersion() {
        VersionEntity version = new VersionEntity();
        version.setVersion("1.0");
        return version;
    }
}
