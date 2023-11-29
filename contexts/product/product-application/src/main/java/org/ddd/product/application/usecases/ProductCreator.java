package org.ddd.product.application.usecases;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.ddd.product.domain.events.ProductCreatedEvent;
import org.ddd.product.domain.events.ProductCreatedEventPublisher;
import org.ddd.product.domain.model.Product;
import org.ddd.product.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductCreator {

    private final ProductRepository productRepository;
    private final ProductCreatedEventPublisher productCreatedEventPublisher;

    public Product save(UUID id, String name, UUID categoryId, String price, boolean isActive) {
        Product product = Product.fromPrimitives(id.toString(), name, categoryId.toString(), price, isActive);
        Product productSaved = productRepository.save(product);
        ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent(productSaved.getId().toString());
        productCreatedEventPublisher.publish(productCreatedEvent);
        return productSaved;
    }

}
