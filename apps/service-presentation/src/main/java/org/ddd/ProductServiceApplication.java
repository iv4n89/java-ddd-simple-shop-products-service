package org.ddd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableDiscoveryClient
@EnableJpaRepositories(basePackages = { "org.ddd.product", "org.ddd.category" })
@EntityScan(basePackages = { "org.ddd.product", "org.ddd.category" })
@SpringBootApplication(scanBasePackages = { "org.ddd.product", "org.ddd.category", "org.ddd.shared" })
public class ProductServiceApplication {
    public static void main(String[] args){
        SpringApplication.run(ProductServiceApplication.class, args);
    }
}
