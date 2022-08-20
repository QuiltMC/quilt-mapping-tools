package org.quiltmc.mapping.entry.annotation.value;

import java.util.Arrays;
import java.util.Objects;

public record LiteralAnnotationValue(String name, Object value, String descriptor) implements AnnotationValue {
    @Override
    public AnnotationValue remap() {
        return null;
    }

    @Override
    public String toString() {
        // Weird hack to make primitive arrays not crash
        String value = Arrays.deepToString(new Object[]{this.value});
        return "LiteralAnnotationValue[" +
               "name='" + name + '\'' +
               ", value=" + value.substring(1, value.length() - 1) +
               ", descriptor='" + descriptor + '\'' +
               ']';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LiteralAnnotationValue that = (LiteralAnnotationValue) o;
        boolean valuesEqual = this.value.getClass().isArray() && that.value.getClass().isArray() ? Arrays.deepEquals((Object[]) this.value, (Object[]) that.value) : Objects.equals(this.value, that.value);
        return Objects.equals(name, that.name) && valuesEqual && Objects.equals(descriptor, that.descriptor);
    }
}
