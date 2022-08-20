package org.quiltmc.annotation_replacement.test;


public class TestClass {
    @Deprecated
    @NestedAnnotation(2.0f)
    public void nothing() {

    }

    public static <T extends Number> double add(T one, T two) {
        return one.doubleValue() + two.doubleValue();
    }
}
