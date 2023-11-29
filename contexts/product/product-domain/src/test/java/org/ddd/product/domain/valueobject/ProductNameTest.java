package org.ddd.product.domain.valueobject;

import static org.junit.jupiter.api.Assertions.*;

import org.ddd.product.domain.ProductNameMother;
import org.ddd.product.domain.ProductDomainTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = ProductDomainTestConfig.class)
class ProductNameTest {

  @Test
  void productNameShouldNotBeEmpty() {
    // Given
    ProductName productName = ProductNameMother.random();
    // Then
    assertFalse(productName.value().isEmpty());
  }

  @Test
  void productNameShouldNotBeBuiltWithAnEmptyString() {
    // Given
    String expected = "";
    // Then
    assertThrows(IllegalArgumentException.class, () -> ProductNameMother.create(expected));
  }

  @Test
  void productNameShouldNotBeBuiltWithNull() {
    // Given
    String expected = null;
    // Then
    assertThrows(IllegalArgumentException.class, () -> ProductNameMother.create(expected));
  }

  @Test
  void productNameShouldNotBeBuiltWithLessThanThreeCharacters() {
    // Given
    String expected = "ab";
    // Then
    assertThrows(IllegalArgumentException.class, () -> ProductNameMother.create(expected));
  }

  @Test
  void productNameShouldNotBeBuiltWithMoreThanFiftyCharacters() {
    // Given
    String expected =
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. "
            + "Sed non risus. Suspendisse lectus tortor, dignissim sit amet, "
            + "adipiscing nec, ultricies sed, dolor. Cras elementum ultrices diam. "
            + "Maecenas ligula massa, varius a, semper congue, euismod non, mi. "
            + "Proin porttitor, orci nec nonummy molestie, enim est eleifend mi, "
            + "non fermentum diam nisl sit amet erat. Duis semper. Duis arcu massa, "
            + "scelerisque vitae, consequat in, pretium a, enim. Pellentesque congue. "
            + "Ut in risus volutpat libero pharetra tempor. Cras vestibulum bibendum augue. "
            + "Praesent egestas leo in pede. Praesent blandit odio eu enim. Pellentesque sed "
            + "dui ut augue blandit sodales. Vestibulum ante ipsum primis in faucibus orci luctus "
            + "et ultrices posuere cubilia Curae; Aliquam nibh. Mauris ac mauris sed pede pellentesque "
            + "feugiat. Integer imperdiet lectus quis justo. Integer tempor. Vivamus ac urna vel leo "
            + "pretium faucibus. Mauris elementum mauris vitae tortor. In dapibus augue non sapien. "
            + "Integer sed tellus. Ut ultricies vestibulum nisi. Pellentesque habitant morbi tristique "
            + "senectus et netus et malesuada fames ac turpis egestas. "
            + "Proin pharetra nonummy pede. Mauris et orci.";
    // Then
    assertThrows(IllegalArgumentException.class, () -> ProductNameMother.create(expected));
  }
}
