package org.ddd.product.domain.events;

import org.ddd.shared.domain.ports.out.message.publisher.DomainEventPublisher;

public interface ProductCreatedEventPublisher extends DomainEventPublisher<ProductCreatedEvent> {}
