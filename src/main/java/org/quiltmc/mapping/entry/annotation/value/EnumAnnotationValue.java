package org.quiltmc.mapping.entry.annotation.value;

public record EnumAnnotationValue(String name, String value, String descriptor) implements AnnotationValue {
    @Override
    public AnnotationValue remap() {
        return null;
    }
}
