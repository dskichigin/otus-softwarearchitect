package dsk.otus.softwarearchitect.task11.common;

import org.springframework.jdbc.core.JdbcTemplate;

public class ShardTemplate {
    private JdbcTemplate jdbcTemplate;
    private int shard = 0;

    public ShardTemplate(int shard, JdbcTemplate jdbcTemplate) {
        this.shard = shard;
        this.jdbcTemplate = jdbcTemplate;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public int getShard() {
        return shard;
    }
}
