package org.quiltmc.mapping.entry.annotation.value;

public interface AnnotationValue {
    String descriptor();

    String name();

    AnnotationValue remap();
}
