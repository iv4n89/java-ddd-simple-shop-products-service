package org.ddd.shared.domain.valueobject;

public class PasswordMother {

    public static String random() {
        return MotherCreator.random().internet().password();
    }
}
