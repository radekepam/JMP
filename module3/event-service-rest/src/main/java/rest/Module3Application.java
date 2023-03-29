package rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "dto", "api", "implementation", "rest"
})

public class Module3Application {
    public static void main(String[] args) {
        SpringApplication.run(Module3Application.class, args);
    }
}
