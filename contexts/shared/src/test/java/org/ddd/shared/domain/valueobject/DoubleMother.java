package org.ddd.shared.domain.valueobject;

public class DoubleMother {

        public static Double random() {
            return MotherCreator.random().number().randomDouble(2, 0, 100);
        }
}
