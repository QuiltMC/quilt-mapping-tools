package org.quiltmc.annotation_replacement.test;

public class TestClassExpected {
    @TestAnnotation(
            value = 1,
            extra = "test string",
            array = {true, false, true},
            enumValue = TestEnum.VALUE,
            clazz = TestClass.class,
            nestedAnnotation = @NestedAnnotation(1.0f)
    )
    public void nothing() {

    }
}
