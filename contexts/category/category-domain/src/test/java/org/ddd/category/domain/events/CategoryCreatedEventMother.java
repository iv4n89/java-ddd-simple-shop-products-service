package org.ddd.category.domain.events;

import org.ddd.shared.domain.valueobject.UUIDMother;

public class CategoryCreatedEventMother {

    public static CategoryCreatedEvent create(String id) {
        return new CategoryCreatedEvent(id);
    }

    public static CategoryCreatedEvent random() {
        return create(UUIDMother.random());
    }

}
