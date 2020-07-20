package dsk.otus.softwarearchitect.task11.repository.utils;

import dsk.otus.softwarearchitect.task11.entity.ProductEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRowMapper implements RowMapper<ProductEntity> {

    @Override
    public ProductEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return FillProductEntity.getEntity(rs);
    }
}
