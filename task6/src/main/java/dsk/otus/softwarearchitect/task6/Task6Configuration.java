package dsk.otus.softwarearchitect.task6;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan(basePackages = "dsk.otus.softwarearchitect.task6")
@EnableJdbcRepositories
@EnableWebMvc
@EnableCaching
//@EnablePrometheusMetrics
//@EnablePrometheusScraping
public class Task6Configuration {
}