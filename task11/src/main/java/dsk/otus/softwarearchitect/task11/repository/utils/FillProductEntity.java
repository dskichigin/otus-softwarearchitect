package dsk.otus.softwarearchitect.task11.repository.utils;

import dsk.otus.softwarearchitect.task11.entity.ProductEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FillProductEntity {
    public static ProductEntity getEntity(ResultSet rs) throws SQLException {
        ProductEntity pe = new ProductEntity();
        pe.setGuid(rs.getString("guid"));
        pe.setCategory(rs.getString("category"));
        pe.setColor(rs.getString("color"));
        pe.setDescription(rs.getString("description"));
        pe.setName(rs.getString("name"));
        pe.setPrice(rs.getDouble("price"));
        return pe;
    }
}
