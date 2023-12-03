package org.ddd.category.application.usecases;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.ddd.category.application.exceptions.CategoryNotFoundException;
import org.ddd.category.domain.repository.CategoryRepository;
import org.ddd.shared.domain.valueobject.CategoryId;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryNumOfProductsChanger {

    private final CategoryRepository categoryRepository;

    public void changeNumOfProducts(UUID categoryId, int numOfProducts) {
    categoryRepository
        .findById(new CategoryId(categoryId))
        .ifPresentOrElse(
            category -> {
              category.changeNumOfProducts(numOfProducts);
              categoryRepository.save(category);
            },
            () -> {
              throw new CategoryNotFoundException("Category with id " + categoryId + " not found");
            });
    }

}
