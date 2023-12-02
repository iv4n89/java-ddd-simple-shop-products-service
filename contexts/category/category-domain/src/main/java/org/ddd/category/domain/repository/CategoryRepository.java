package org.ddd.category.domain.repository;

import org.ddd.category.domain.model.Category;

import java.util.Optional;

public interface CategoryRepository {

    Category save(Category category);

    Optional<Category> findById(String id);

    Optional<Category> findByName(String name);

    Optional<Category> findBySlug(String slug);

}
