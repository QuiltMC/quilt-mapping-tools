package org.quiltmc.annotation_replacement.test;

public @interface TestAnnotation {
    int value();

    String extra();

    boolean[] array();

    TestEnum enumValue();

    Class<?> clazz();

    NestedAnnotation nestedAnnotation();
}
