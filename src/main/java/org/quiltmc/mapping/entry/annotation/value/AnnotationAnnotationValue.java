package org.quiltmc.mapping.entry.annotation.value;

import java.util.List;
import java.util.Objects;

import org.quiltmc.mapping.entry.annotation.AnnotationInformation;

public record AnnotationAnnotationValue(String name, List<AnnotationValue> values, String descriptor) implements AnnotationValue, AnnotationInformation {
    @Override
    public AnnotationValue remap() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnnotationAnnotationValue that = (AnnotationAnnotationValue) o;
        return Objects.equals(name, that.name) && this.values.containsAll(that.values) && that.values.containsAll(this.values) && Objects.equals(descriptor, that.descriptor);
    }
}
