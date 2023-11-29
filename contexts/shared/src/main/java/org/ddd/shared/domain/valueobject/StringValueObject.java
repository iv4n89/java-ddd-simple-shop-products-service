package org.ddd.shared.domain.valueobject;

public abstract class StringValueObject extends ValueObject<String> {
    protected StringValueObject(String value) {
        super(value);
    }

    @Override
    protected void isValid() {
        isNotNull();
        isNotEmpty();
    }

    protected void isNotNull() {
        if (value == null) {
            throw new IllegalArgumentException("The value cannot be null");
        }
    }

    protected void isNotEmpty() {
        if (value.isEmpty()) {
            throw new IllegalArgumentException("The value cannot be empty");
        }
    }
}
