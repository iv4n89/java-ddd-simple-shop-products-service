package org.ddd.product.application.dto;

import org.ddd.product.application.ProductApplicationTestConfig;
import org.ddd.product.domain.ProductMother;
import org.ddd.product.domain.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = ProductApplicationTestConfig.class)
class ProductResponseDtoTest {

    @Test
    void productResponseDtoShouldBeCreated() {
        // Given
        Product product = ProductMother.randomActive();
        // When
        ProductResponseDto productResponseDto = ProductResponseDto.builder()
                .id(product.getId().value().toString())
                .name(product.getName().value())
                .price(product.getPrice().value().toPlainString())
                .categoryId(product.getCategoryId().value().toString())
                .isActive(product.isActive())
                .build();
        // Then
        assertNotNull(productResponseDto);
        assertEquals(product.getId().value().toString(), productResponseDto.getId());
        assertEquals(product.getName().value(), productResponseDto.getName());
        assertEquals(product.getPrice().value().toPlainString(), productResponseDto.getPrice());
        assertEquals(product.getCategoryId().value().toString(), productResponseDto.getCategoryId());
        assertEquals(product.isActive(), productResponseDto.isActive());
    }

}
