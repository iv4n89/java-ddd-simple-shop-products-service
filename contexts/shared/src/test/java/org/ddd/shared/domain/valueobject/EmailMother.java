package org.ddd.shared.domain.valueobject;

public class EmailMother {

    public static String random() {
        return MotherCreator.random().internet().emailAddress();
    }
}
