package org.ddd.category.infrastructure.persistence;

import org.ddd.category.infrastructure.persistence.entity.CategoryEntity;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Primary
@Repository
public interface CategoryJpaRepository extends JpaRepository<CategoryEntity, UUID> {

    Optional<CategoryEntity> findByName(String name);
    Optional<CategoryEntity> findBySlug(String slug);

}
