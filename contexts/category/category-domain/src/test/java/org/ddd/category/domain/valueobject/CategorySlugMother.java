package org.ddd.category.domain.valueobject;

import org.ddd.shared.domain.valueobject.WordMother;

public class CategorySlugMother {

    public static CategorySlug random() {
        return create(WordMother.random(5));
    }

    public static CategorySlug create(String value) {
        return new CategorySlug(value);
    }

}
