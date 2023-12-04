package org.ddd.category.infrastructure.persistence;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.ddd.category.domain.model.Category;
import org.ddd.category.domain.repository.CategoryRepository;
import org.ddd.category.domain.valueobject.CategoryName;
import org.ddd.category.domain.valueobject.CategorySlug;
import org.ddd.category.infrastructure.persistence.entity.CategoryEntity;
import org.ddd.category.infrastructure.persistence.mapper.CategoryDataMapper;
import org.ddd.shared.domain.valueobject.CategoryId;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@RequiredArgsConstructor
@Component("PostgresCategoryRepository")
public class PostgresCategoryRepository implements CategoryRepository {

  private final CategoryJpaRepository categoryJpaRepository;

  @Override
  public Category save(Category category) {
    CategoryEntity entity = CategoryDataMapper.categoryToCategoryEntity(category);
    return CategoryDataMapper.categoryEntityToCategory(categoryJpaRepository.save(entity));
  }

  @Override
  public List<Category> findAll() {
    return categoryJpaRepository.findAll().stream()
        .map(CategoryDataMapper::categoryEntityToCategory)
        .toList();
  }

  @Override
  public Optional<Category> findById(CategoryId id) {
    return categoryJpaRepository
        .findById(id.value())
        .map(CategoryDataMapper::categoryEntityToCategory);
  }

  @Override
  public Optional<Category> findByName(CategoryName name) {
    return categoryJpaRepository
        .findByName(name.value())
        .map(CategoryDataMapper::categoryEntityToCategory);
  }

  @Override
  public Optional<Category> findBySlug(CategorySlug slug) {
    return categoryJpaRepository
        .findBySlug(slug.value())
        .map(CategoryDataMapper::categoryEntityToCategory);
  }

  @Override
  public boolean existsById(CategoryId id) {
    return categoryJpaRepository.existsById(id.value());
  }
}
