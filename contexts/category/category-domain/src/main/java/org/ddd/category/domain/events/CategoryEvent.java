package org.ddd.category.domain.events;

import org.ddd.category.domain.model.Category;
import org.ddd.shared.domain.events.DomainEvent;

import java.time.LocalDateTime;

public abstract class CategoryEvent implements DomainEvent<Category> {

    private final String categoryId;
    private final LocalDateTime issedAt;

    protected CategoryEvent(String categoryId) {
        this.categoryId = categoryId;
        this.issedAt = LocalDateTime.now();
    }

    public String getCategoryId() {
        return categoryId;
    }

    public LocalDateTime getIssedAt() {
        return issedAt;
    }
}
