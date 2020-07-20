package dsk.otus.softwarearchitect.task11.repository;

import dsk.otus.softwarearchitect.task11.core.TemplateManager;
import dsk.otus.softwarearchitect.task11.entity.ProductEntity;
import dsk.otus.softwarearchitect.task11.entity.ShardInfoEntity;
import dsk.otus.softwarearchitect.task11.repository.utils.FillProductEntity;
import dsk.otus.softwarearchitect.task11.repository.utils.ProductRowMapper;
import dsk.otus.softwarearchitect.task11.repository.utils.StringRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProductRepository {
    @Autowired
    TemplateManager templateManager;

    public List<ProductEntity> findAll() {
        String sql ="select guid, name, category, color, price, description from products";
        List<ProductEntity> products = new ArrayList<>();

        for (int shard = 0; shard <= templateManager.getCountShard()-1; shard++) {
            JdbcTemplate template = templateManager.getJdbcTemplate(shard);
            List<ProductEntity> prs = template.query(sql, (resultSet, i) -> { return FillProductEntity.getEntity(resultSet); });
            for (ProductEntity product : prs) {
                product.setShard(shard);
            }
            products.addAll(prs);
        }
        return products;
    }

    public List<String> findByFilter(String filter) {
        String sql ="select guid from products where lower(name) like ? or lower(color) like ? or lower(category) like ? or lower(description) like ? limit 100";
        List<String> products = new ArrayList<>();

        for (int shard = 0; shard <= templateManager.getCountShard()-1; shard++) {
            JdbcTemplate template = templateManager.getJdbcTemplate(shard);
            products.addAll(template.query(sql,
                    new StringRowMapper(), filter, filter, filter, filter));
        }
        return products;
    }
    public ProductEntity insert(ProductEntity product) throws Exception {
        JdbcTemplate template = templateManager.getJdbcTemplate(getShard(product.getGuid()));

        String sql = "INSERT INTO products (guid, name, category, color, price, description) values (?, ?, ?, ?, ?, ?)";
        template.update(sql, product.getGuid(), product.getName(), product.getCategory(), product.getColor(),
                product.getPrice(), product.getDescription());
        product.setShard(getShard(product.getGuid()));
        return product;
    }
    public ProductEntity update(ProductEntity product) throws Exception {
        JdbcTemplate template = templateManager.getJdbcTemplate(getShard(product.getGuid()));

        String sql = "UPDATE products set name = ?, category = ?, color = ?, price = ?, description = ? where guid = ?";
        template.update(sql, product.getName(), product.getCategory(), product.getColor(),
                product.getPrice(), product.getDescription(), product.getGuid());

        product.setShard(getShard(product.getGuid()));
        return product;
    }
    public ProductEntity findById(String guid) throws Exception {
        JdbcTemplate template = templateManager.getJdbcTemplate(getShard(guid));
        ProductEntity product = template.queryForObject("select * from products where guid = ?", new Object[]{guid}, new ProductRowMapper());
        if (product != null)
            product.setShard(getShard(product.getGuid()));
        return product;
    }
    public void deleteById(String guid) throws Exception {
        JdbcTemplate template = templateManager.getJdbcTemplate(getShard(guid));

        template.update("DELETE FROM products where guid = ?", guid);
    }

    public void deleteAll() {
        for (int shard = 0; shard <= templateManager.getCountShard()-1; shard++) {
            JdbcTemplate template = templateManager.getJdbcTemplate(shard);
            template.update("DELETE FROM products");
        }
    }
    public List<ShardInfoEntity> shardCountRows() {
        List<ShardInfoEntity> shards = new ArrayList<>();
        for (int shard = 0; shard <= templateManager.getCountShard()-1; shard++) {
            JdbcTemplate template = templateManager.getJdbcTemplate(shard);

            Long count = (long) template.queryForObject(
                    "select count(guid) from products", null, Long.class);
            shards.add(new ShardInfoEntity(shard, count));
        }
        return shards;
    }

//    public int getShard(String guid) {
//        return 0;
//    }
    public int getShard(String key) throws Exception {
        int shardsCount = templateManager.getCountShard();
        if (shardsCount<1) throw new Exception("invalid shards count");
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(key.getBytes("UTF-8"));
        byte[] bb = md.digest();
        int n = Math.abs((int) bb[0]);
        if (n>=10) n = n%10;
        double delimiter = (double)10/shardsCount;
        return (int) (n/delimiter);
    }
}