package dsk.otus.softwarearchitect.task6.controller;

import dsk.otus.softwarearchitect.task6.cache.ProductProxy;
import dsk.otus.softwarearchitect.task6.entity.ProductEntity;
import dsk.otus.softwarearchitect.task6.entity.VersionEntity;
import dsk.otus.softwarearchitect.task6.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.util.List;

@RestController
public class ProductController {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductProxy productProxy;

    public ProductController() { }

    @PostMapping(path = "/products", produces = "application/json")
    public @ResponseBody
    ResponseEntity<ProductEntity> createProduct(@RequestBody ProductEntity product) {
        return ResponseEntity.ok(productProxy.createProduct(product));
    }

    @GetMapping(path = "/products/{id}", produces = "application/json")
    public @ResponseBody
    ResponseEntity<ProductEntity> getProduct(@PathVariable("id") String id, HttpServletRequest request) throws Exception {
        ProductEntity product = productProxy.getProduct(id);
        if (product == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return ResponseEntity.ok(product);
    }

    @GetMapping(value = "/products", produces = "application/json")
    public ResponseEntity<List<ProductEntity>> getProducts(HttpServletRequest request) {
        return ResponseEntity.ok(productProxy.getProducts());
    }

    @GetMapping(value = "/products/filter", produces = "application/json")
    public List<ProductEntity> findProducts(@PathParam("filter") String filter, HttpServletRequest request) {
        return productProxy.findProductsByFilter(filter);
    }


    @PutMapping(path="/products/{id}", consumes = "application/json", produces = "application/json")
    public @ResponseBody ResponseEntity<ProductEntity> updateProduct(@PathVariable("id") String id, @RequestBody ProductEntity product, HttpServletRequest request) {
        product.setId(id);
        return ResponseEntity.ok(productProxy.updateProduct(product));
    }

    @DeleteMapping(path="/products/{id}")
    public ResponseEntity deleteProduct(@PathVariable("id") String id, HttpServletRequest request) throws Exception {
        if (id.equals("all")) {
            productProxy.deleteProductAll();
        } else {
            productProxy.deleteProduct(id);
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/products/fill/{count}", produces = "application/json")
    public @ResponseBody
    ResponseEntity fillProducts(@PathVariable("count") String str_count, HttpServletRequest request) {
        // заполняем базу
        productProxy.fillProducts(Integer.valueOf(str_count));
        return ResponseEntity.ok().build();
    }


    @GetMapping(path = "/version", produces = "application/json")
    public VersionEntity getVersion() {
        VersionEntity version = new VersionEntity();
        version.setVersion("1.0");
        return version;
    }
}
