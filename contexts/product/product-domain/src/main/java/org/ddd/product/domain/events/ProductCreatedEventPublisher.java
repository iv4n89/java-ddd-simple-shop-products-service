package org.ddd.product.domain.events;


import org.ddd.shared.domain.publisher.DomainEventPublisher;

public interface ProductCreatedEventPublisher extends DomainEventPublisher<ProductCreatedEvent> {}
