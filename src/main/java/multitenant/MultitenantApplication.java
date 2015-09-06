package multitenant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

@SpringBootApplication
@EnableAutoConfiguration
public class MultitenantApplication {

    public static void main(String[] args) {
        SpringApplication.run(MultitenantApplication.class, args);
    }
    }
