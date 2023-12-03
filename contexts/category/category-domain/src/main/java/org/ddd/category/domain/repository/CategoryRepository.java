package org.ddd.category.domain.repository;

import org.ddd.category.domain.model.Category;
import org.ddd.category.domain.valueobject.CategoryName;
import org.ddd.category.domain.valueobject.CategorySlug;
import org.ddd.shared.domain.valueobject.CategoryId;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {

  Category save(Category category);

  List<Category> findAll();

  Optional<Category> findById(CategoryId id);

  Optional<Category> findByName(CategoryName name);

  Optional<Category> findBySlug(CategorySlug slug);

  boolean existsById(CategoryId id);
}
