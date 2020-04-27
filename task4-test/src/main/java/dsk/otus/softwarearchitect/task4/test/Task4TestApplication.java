package dsk.otus.softwarearchitect.task4.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
//@ComponentScan(basePackages = {"dsk.otus.softwarearchitect.test.task4"})
public class Task4TestApplication {
    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(Task4TestApplication.class, args);
        TestController testController = context.getBean(TestController.class);
        testController.start();
    }
}
