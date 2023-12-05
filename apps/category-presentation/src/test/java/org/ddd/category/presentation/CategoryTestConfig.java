package org.ddd.category.presentation;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = {"org.ddd.category", "org.ddd.shared"})
@EntityScan(basePackages = {"org.ddd.category", "org.ddd.shared"})
@SpringBootApplication(scanBasePackages = {"org.ddd.category", "org.ddd.shared"})
public class CategoryTestConfig {}
