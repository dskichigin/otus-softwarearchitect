package dsk.otus.softwarearchitect.task11.repository.utils;

import dsk.otus.softwarearchitect.task11.entity.ProductEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ProductEntityRM implements RowMapper<Map<ProductEntity, Integer>> {

    private int code;

    @Override
    public Map<ProductEntity, Integer> mapRow(ResultSet rs, int rowNum) throws SQLException {
        Map<ProductEntity, Integer> map = new HashMap<>();
        map.put(FillProductEntity.getEntity(rs), code);
        return map;
    }

    public ProductEntityRM(int code) {
        this.code = code;
    }
}
