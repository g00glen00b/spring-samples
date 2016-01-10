package be.g00glen00b;

import be.g00glen00b.service.impl.AwesomeWebsiteServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@SpringBootApplication
public class SpringBootI18nWebappApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootI18nWebappApplication.class, args);
    }

    @Bean
    public Random randomGenerator() {
        return new Random();
    }
}
