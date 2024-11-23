package urdego.content_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
@EntityScan(basePackages = "domain.entity")
public class ContentServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContentServiceApplication.class, args);
    }
}
