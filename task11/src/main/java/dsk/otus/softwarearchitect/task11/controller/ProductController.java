package dsk.otus.softwarearchitect.task11.controller;

import dsk.otus.softwarearchitect.task11.cache.ProductProxy;
import dsk.otus.softwarearchitect.task11.entity.ProductEntity;
import dsk.otus.softwarearchitect.task11.entity.ShardInfoEntity;
import dsk.otus.softwarearchitect.task11.entity.VersionEntity;
import dsk.otus.softwarearchitect.task11.repository.ProductRepository;
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
        try {
            return ResponseEntity.ok(productProxy.createProduct(product));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
//            e.printStackTrace();
        }
    }

    @GetMapping(path = "/products/{guid}", produces = "application/json")
    @Counted
    public @ResponseBody
    ResponseEntity<ProductEntity> getProduct(@PathVariable("guid") String guid, HttpServletRequest request) throws Exception {
        ProductEntity product = productProxy.getProduct(guid);
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
        try {
            return productProxy.findProductsByFilter(q);
        } catch (Exception e) {
            return null;
//            e.printStackTrace();
        }
    }


    @PutMapping(path="/products/{guid}", consumes = "application/json", produces = "application/json")
    @Counted
    public @ResponseBody ResponseEntity<ProductEntity> updateProduct(@PathVariable("guid") String guid, @RequestBody ProductEntity product, HttpServletRequest request) {
        product.setGuid(guid);
        try {
            return ResponseEntity.ok(productProxy.updateProduct(product));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
            //e.printStackTrace();
        }
    }

    @DeleteMapping(path="/products/{guid}")
    @Counted
    public ResponseEntity deleteProduct(@PathVariable("guid") String guid, HttpServletRequest request) throws Exception {
        if (guid.equals("all")) {
            productProxy.deleteProductAll();
        } else {
            productProxy.deleteProduct(guid);
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/products/fill", produces = "application/json")
//    @Counted
    public @ResponseBody
    ResponseEntity fillProducts(@PathParam("count") String count, HttpServletRequest request) {
        // заполняем базу
        try {
            productProxy.fillProducts(Long.valueOf(count));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
//            e.printStackTrace();
        }
        return ResponseEntity.ok().build();
    }
    @GetMapping(path = "/products/count", produces = "application/json")
//    @Counted
    public @ResponseBody
    ResponseEntity<List<ShardInfoEntity>> fillProductsCount(HttpServletRequest request) {
        return ResponseEntity.ok(productRepository.shardCountRows());
    }

    /**
     * расчетный номер шарда
     * @param guid
     * @param request
     * @return
     * @throws Exception
     */
    @GetMapping(path = "/products/shard/{guid}", produces = "application/json")
    @Counted
    public @ResponseBody
    ResponseEntity<ShardInfoEntity> getShardProduct(@PathVariable("guid") String guid, HttpServletRequest request) throws Exception {
        int shard = productRepository.getShard(guid);
        return ResponseEntity.ok(new ShardInfoEntity(shard));
    }

    @GetMapping(path = "/version", produces = "application/json")
    @Counted
    public VersionEntity getVersion() {
        VersionEntity version = new VersionEntity();
        version.setVersion("1.0");
        return version;
    }
}
