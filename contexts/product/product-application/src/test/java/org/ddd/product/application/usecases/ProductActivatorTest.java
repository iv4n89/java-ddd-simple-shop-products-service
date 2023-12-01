package org.ddd.product.application.usecases;

import org.ddd.product.application.ProductApplicationTestConfig;
import org.ddd.product.domain.ProductMother;
import org.ddd.product.domain.model.Product;
import org.ddd.product.domain.repository.ProductRepository;
import org.ddd.shared.domain.valueobject.ProductId;
import org.ddd.shared.domain.valueobject.ProductIdMother;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(classes = ProductApplicationTestConfig.class)
class ProductActivatorTest {

    @Qualifier("productActivatorTest")
    @Autowired
    private ProductActivator productActivator;

    @Qualifier("productRepositoryTest")
    @Autowired
    private ProductRepository productRepository;

    @Test
    void productActivatorShouldActivateAProduct() {
        // Given
        Product product = ProductMother.randomInactive();
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        // When
        productActivator.activate(product.getId().value());
        // Then
        assertTrue(product.isActive());
        verify(productRepository, times(1)).findById(product.getId());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void productActivatorShouldDeactivateAProduct() {
        // Given
        Product product = ProductMother.randomActive();
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        // When
        productActivator.deactivate(product.getId().value());
        // Then
        assertFalse(product.isActive());
        verify(productRepository, times(1)).findById(product.getId());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void productActivatorShouldFailWithNonExistentProduct() {
        // Given
        ProductId id = ProductIdMother.random();
        when(productRepository.findById(id)).thenReturn(Optional.empty());
        // When
        // Then
        UUID idValue = id.value();
        assertThrows(RuntimeException.class, () -> productActivator.activate(idValue));
        verify(productRepository, times(1)).findById(id);
        verify(productRepository, times(0)).save(any(Product.class));
    }

}
