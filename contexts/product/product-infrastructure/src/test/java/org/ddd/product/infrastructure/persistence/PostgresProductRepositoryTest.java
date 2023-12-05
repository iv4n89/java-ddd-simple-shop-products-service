package org.ddd.product.infrastructure.persistence;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.ddd.product.domain.ProductMother;
import org.ddd.product.domain.ProductNameMother;
import org.ddd.product.domain.model.Product;
import org.ddd.product.domain.valueobject.ProductName;
import org.ddd.product.infrastructure.ProductInfrastructureTestConfiguration;
import org.ddd.product.infrastructure.persistence.mapper.ProductDataMapper;
import org.ddd.shared.domain.valueobject.*;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@SpringBootTest(classes = ProductInfrastructureTestConfiguration.class)
@EmbeddedKafka(partitions = 1, topics = {"product-created-event"})
@TestPropertySource(properties = {"kafka-config.bootstrap-servers=${spring.embedded.kafka.brokers}", "kafka-config.product-created-topic=product-created-topic"})
class PostgresProductRepositoryTest {

    @Autowired
    @Qualifier("productJpaRepositoryTest")
    private ProductJpaRepository productJpaRepositoryTest;

    @Autowired
    @Qualifier("productRepositoryInfraTest")
    private PostgresProductRepository postgresProductRepositoryTest;

    @Test
    @Order(6)
    void testFindAll() {
        // Given
        List<Product> expected = List.of(ProductMother.randomActive(), ProductMother.randomActive());
        when(productJpaRepositoryTest.findAll()).thenReturn(expected.stream().map(ProductDataMapper::toEntity).toList());

        // When
        List<Product> actual = postgresProductRepositoryTest.findAll();

        // Then
        verify(productJpaRepositoryTest, times(1)).findAll();
        verifyNoMoreInteractions(productJpaRepositoryTest);

        assertEquals(expected.get(0).getId(), actual.get(0).getId());
        assertEquals(expected.size(), actual.size());
    }

    @Test
    @Order(1)
    void testFindAllEmptyList() {
        // Given
        List<Product> expected = Collections.emptyList();
        when(productJpaRepositoryTest.findAll()).thenReturn(Collections.emptyList());

        // When
        List<Product> actual = postgresProductRepositoryTest.findAll();

        // Then
        verify(productJpaRepositoryTest, times(1)).findAll();
        verifyNoMoreInteractions(productJpaRepositoryTest);

        assertEquals(0, actual.size());
    }

    @Test
    @Order(5)
    void testFindAnExistingProductById() {
        // Given
        Product expected = ProductMother.randomActive();
        when(productJpaRepositoryTest.findById(expected.getId().value())).thenReturn(Optional.of(ProductDataMapper.toEntity(expected)));

        // When
        Product actual = postgresProductRepositoryTest.findById(expected.getId()).orElseThrow();

        // Then
        verify(productJpaRepositoryTest, times(1)).findById(expected.getId().value());
        verifyNoMoreInteractions(productJpaRepositoryTest);

        assertEquals(expected.getId(), actual.getId());
    }

    @Test
    @Order(2)
    void testFindANonExistingProductById() {
        // Given
        Product expected = ProductMother.randomActive();
        when(productJpaRepositoryTest.findById(expected.getId().value())).thenReturn(Optional.empty());

        // When
        Optional<Product> actual = postgresProductRepositoryTest.findById(expected.getId());

        // Then
        verify(productJpaRepositoryTest, times(1)).findById(expected.getId().value());
        verifyNoMoreInteractions(productJpaRepositoryTest);

        assertTrue(actual.isEmpty());
    }

    @Test
    @Order(4)
    void testFindAnExistingProductByName() {
        // Given
        Product expected = ProductMother.randomActive();
        when(productJpaRepositoryTest.findByName(expected.getName().value())).thenReturn(Optional.of(ProductDataMapper.toEntity(expected)));

        // When
        Product actual = postgresProductRepositoryTest.findByName(expected.getName()).orElseThrow();

        // Then
        verify(productJpaRepositoryTest, times(1)).findByName(expected.getName().value());
        verifyNoMoreInteractions(productJpaRepositoryTest);

        assertEquals(expected.getName(), actual.getName());
    }

    @Test
    @Order(3)
    void testFindANonExistingProductByName() {
        // Given
        Product expected = ProductMother.randomActive();
        when(productJpaRepositoryTest.findByName(expected.getName().value())).thenReturn(Optional.empty());

        // When
        Optional<Product> actual = postgresProductRepositoryTest.findByName(expected.getName());

        // Then
        verify(productJpaRepositoryTest, times(1)).findByName(expected.getName().value());
        verifyNoMoreInteractions(productJpaRepositoryTest);

        assertTrue(actual.isEmpty());
    }

    @Test
    @Order(7)
    void testSaveANewProduct() {
        // Given
        Product expected = ProductMother.randomActive();
        when(productJpaRepositoryTest.save(any())).thenReturn(ProductDataMapper.toEntity(expected));

        // When
        Product actual = postgresProductRepositoryTest.save(expected);

        // Then
        verify(productJpaRepositoryTest, times(1)).save(any());
        verifyNoMoreInteractions(productJpaRepositoryTest);

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getPrice(), actual.getPrice());
        assertEquals(expected.getCategoryId(), actual.getCategoryId());
    }

    @Test
    @Order(8)
    void testSaveAnExistingProduct() {
        // Given
        ProductId id = ProductIdMother.random();
        ProductName name = ProductNameMother.random();
        Money price = MoneyMother.random();
        CategoryId categoryId = CategoryIdMother.random();
        Product expected = ProductMother.from(id, ProductNameMother.random(), CategoryIdMother.random(), MoneyMother.random(), true);
        when(productJpaRepositoryTest.save(any())).thenReturn(ProductDataMapper.toEntity(expected));

        // When
        Product actual = postgresProductRepositoryTest.save(expected);

        // Then
        verify(productJpaRepositoryTest, atLeastOnce()).save(any());
        verifyNoMoreInteractions(productJpaRepositoryTest);

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getPrice(), actual.getPrice());
        assertEquals(expected.getCategoryId(), actual.getCategoryId());
    }

    @Test
    @Order(9)
    void testErrorAtSavingANonValidProduct() {
        // Given
        Product expected = ProductMother.randomActive();
        when(productJpaRepositoryTest.save(any())).thenThrow(new RuntimeException());

        // When
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> postgresProductRepositoryTest.save(expected));

        // Then
        verify(productJpaRepositoryTest, times(1)).save(any());
        verifyNoMoreInteractions(productJpaRepositoryTest);

        assertEquals("java.lang.RuntimeException", runtimeException.getClass().getName());
    }
}
