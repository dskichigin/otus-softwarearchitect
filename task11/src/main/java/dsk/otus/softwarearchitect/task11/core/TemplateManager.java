package dsk.otus.softwarearchitect.task11.core;

import dsk.otus.softwarearchitect.task11.common.ShardTemplate;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class TemplateManager {
    private static String POSTGRES_URI = "POSTGRES_URI";
    private static String POSTGRES_USER = "POSTGRES_USER";
    private static String POSTGRES_PASSWORD = "POSTGRES_PASSWORD";

    private static String POSTGRES_URI_1 = "POSTGRES_URI_1";
    private static String POSTGRES_USER_1 = "POSTGRES_USER_1";
    private static String POSTGRES_PASSWORD_1 = "POSTGRES_PASSWORD_1";

    @Value("${POSTGRES_URI}")
    private String jdbcUrl; //jdbc:postgresql://host:port/database
    @Value("${POSTGRES_USER}")
    private String jdbcUser;
    @Value("${POSTGRES_PASSWORD}")
    private String jdbcPassword;

    @Value("${POSTGRES_URI_1}")
    private String jdbcUrl1; //jdbc:postgresql://host:port/database
    @Value("${POSTGRES_USER_1}")
    private String jdbcUser1;
    @Value("${POSTGRES_PASSWORD_1}")
    private String jdbcPassword1;

    private int count_shard = 2;

    private List<ShardTemplate> shardTemplates = Collections.synchronizedList(new ArrayList());

    public int getCountShard() {
        return count_shard;
    }
    public JdbcTemplate getJdbcTemplate(int shard) {
        if (shard > count_shard-1) return null;

        for (ShardTemplate shardTemplate : shardTemplates) {
            if (shardTemplate.getShard() == shard) return shardTemplate.getJdbcTemplate();
        }
        ShardTemplate shardTemplate = null;
        if (shard == 0) {
            shardTemplate = new ShardTemplate(shard, jdbcTemplate(postgresDataSource(shard)));
        }
        if (shard == 1) {
            shardTemplate = new ShardTemplate(shard, jdbcTemplate(postgresDataSource(shard)));
        }
        shardTemplates.add(shardTemplate);
        return shardTemplate.getJdbcTemplate();
    }

    private DataSource postgresDataSource(int shard) {
        PGSimpleDataSource ds = null;
        if (shard == 0) {
            System.out.println(shard);
            System.out.println(System.getenv(POSTGRES_URI));
            System.out.println(System.getenv(POSTGRES_USER));
            System.out.println(System.getenv(POSTGRES_PASSWORD));

            if (System.getenv(POSTGRES_URI) != null)
                jdbcUrl = System.getenv(POSTGRES_URI);
            if (System.getenv(POSTGRES_USER) != null)
                jdbcUser = System.getenv(POSTGRES_USER);
            if (System.getenv(POSTGRES_PASSWORD) != null)
                jdbcPassword = System.getenv(POSTGRES_PASSWORD);

            if (!jdbcUrl.startsWith("jdbc:"))
                jdbcUrl = "jdbc:" + jdbcUrl;

            ds = new PGSimpleDataSource();
            ds.setURL(jdbcUrl);
            ds.setUser(jdbcUser);
            ds.setPassword(jdbcPassword);
        }
        if (shard == 1) {
            System.out.println(shard);
            System.out.println(System.getenv(POSTGRES_URI_1));
            System.out.println(System.getenv(POSTGRES_USER_1));
            System.out.println(System.getenv(POSTGRES_PASSWORD_1));

            if (System.getenv(POSTGRES_URI_1) != null)
                jdbcUrl1 = System.getenv(POSTGRES_URI_1);
            if (System.getenv(POSTGRES_USER_1) != null)
                jdbcUser1 = System.getenv(POSTGRES_USER_1);
            if (System.getenv(POSTGRES_PASSWORD_1) != null)
                jdbcPassword1 = System.getenv(POSTGRES_PASSWORD_1);

            if (!jdbcUrl1.startsWith("jdbc:"))
                jdbcUrl1 = "jdbc:" + jdbcUrl1;

            ds = new PGSimpleDataSource();
            ds.setURL(jdbcUrl1);
            ds.setUser(jdbcUser1);
            ds.setPassword(jdbcPassword1);
        }
        return ds;
    }

    private JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
