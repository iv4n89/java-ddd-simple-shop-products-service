package org.ddd.category.domain.events;

public class CategoryCreatedEvent extends CategoryEvent {
  public CategoryCreatedEvent(String categoryId) {
    super(categoryId);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof CategoryCreatedEvent)) return false;
    CategoryCreatedEvent that = (CategoryCreatedEvent) obj;
    return getCategoryId().equals(that.getCategoryId());
  }

  @Override
  public int hashCode() {
    return getCategoryId().hashCode();
  }
}
