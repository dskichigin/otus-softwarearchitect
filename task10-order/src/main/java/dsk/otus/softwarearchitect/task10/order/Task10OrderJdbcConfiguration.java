package dsk.otus.softwarearchitect.task10.order;

import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jdbc.core.convert.JdbcConverter;
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions;
import org.springframework.data.jdbc.core.convert.RelationResolver;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.relational.core.mapping.RelationalMappingContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class Task10OrderJdbcConfiguration extends AbstractJdbcConfiguration {
    private static String POSTGRES_URI = "POSTGRES_URI";
    private static String POSTGRES_USER = "POSTGRES_USER";
    private static String POSTGRES_PASSWORD = "POSTGRES_PASSWORD";

    @Value("${POSTGRES_URI}")
    private String jdbcUrl; //jdbc:postgresql://host:port/database
    @Value("${POSTGRES_USER}")
    private String jdbcUser;
    @Value("${POSTGRES_PASSWORD}")
    private String jdbcPassword;

    @Bean
    NamedParameterJdbcOperations operations() {
        return new NamedParameterJdbcTemplate(postgresDataSource());
    }

    @Bean
    PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(postgresDataSource());
    }

    @Bean
    DataSource postgresDataSource() {
        if (System.getenv(POSTGRES_URI) != null)
            jdbcUrl = System.getenv(POSTGRES_URI);
        if (System.getenv(POSTGRES_USER) != null)
            jdbcUser = System.getenv(POSTGRES_USER);
        if (System.getenv(POSTGRES_PASSWORD) != null)
            jdbcPassword = System.getenv(POSTGRES_PASSWORD);

        if (!jdbcUrl.startsWith("jdbc:"))
            jdbcUrl = "jdbc:" +jdbcUrl;

        PGSimpleDataSource ds = new PGSimpleDataSource();ds.setURL(jdbcUrl);
        ds.setUser(jdbcUser);
        ds.setPassword(jdbcPassword);
        return ds;
    }

    @Bean
    JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }


    @Bean
    public JdbcConverter jdbcConverter(final RelationalMappingContext mappingContext,
                                       final NamedParameterJdbcOperations operations,
                                       final @Lazy RelationResolver relationResolver,
                                       final JdbcCustomConversions conversions) {
        return new AbstractJdbcConfiguration().jdbcConverter(mappingContext, operations, relationResolver, conversions);
    }
}