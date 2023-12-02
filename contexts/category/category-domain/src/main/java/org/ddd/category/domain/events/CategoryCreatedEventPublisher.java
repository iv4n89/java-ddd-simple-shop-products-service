package org.ddd.category.domain.events;

import org.ddd.shared.domain.ports.out.message.publisher.DomainEventPublisher;

public interface CategoryCreatedEventPublisher extends DomainEventPublisher<CategoryCreatedEvent> {}
