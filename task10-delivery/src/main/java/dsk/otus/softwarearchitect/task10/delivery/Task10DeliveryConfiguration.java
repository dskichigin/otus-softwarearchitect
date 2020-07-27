package dsk.otus.softwarearchitect.task10.delivery;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan(basePackages = "dsk.otus.softwarearchitect.task10.delivery")
@EnableWebMvc
//@EnableCaching
@PropertySource("file:jdbc.properties")
//@EnablePrometheusMetrics
//@EnablePrometheusScraping
public class Task10DeliveryConfiguration {
}