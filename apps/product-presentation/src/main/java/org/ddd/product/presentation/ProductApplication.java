package org.ddd.product.presentation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = "org.ddd.product.infrastructure")
@EnableJpaRepositories(basePackages = "org.ddd.product.infrastructure")
@SpringBootApplication(scanBasePackages = { "org.ddd.product", "org.ddd.shared" })
public class ProductApplication {
    public static void main(String[] args){
        SpringApplication.run(ProductApplication.class, args);
    }
}
