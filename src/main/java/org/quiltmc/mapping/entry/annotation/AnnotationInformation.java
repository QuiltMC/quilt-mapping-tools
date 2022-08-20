package org.quiltmc.mapping.entry.annotation;

import java.util.List;

import org.objectweb.asm.AnnotationVisitor;
import org.quiltmc.mapping.entry.annotation.value.AnnotationAnnotationValue;
import org.quiltmc.mapping.entry.annotation.value.AnnotationValue;
import org.quiltmc.mapping.entry.annotation.value.EnumAnnotationValue;
import org.quiltmc.mapping.entry.annotation.value.LiteralAnnotationValue;

public interface AnnotationInformation {
    String descriptor();

    List<AnnotationValue> values();

    default void visit(AnnotationVisitor visitor) {
        for (AnnotationValue value : values()) {
            // TODO: Support classes
            if (value instanceof LiteralAnnotationValue literal) {
                if (literal.descriptor().startsWith("[")) {
                    if (literal.value().getClass().componentType().isPrimitive()) {
                        visitor.visit(literal.name(), literal.value());
                    } else {
                        AnnotationVisitor arrayVisitor = visitor.visitArray(literal.name());
                        for (Object arrayValue : (Object[]) literal.value()) {
                            arrayVisitor.visit(null, arrayValue);
                        }
                    }
                } else {
                    visitor.visit(literal.name(), literal.value());
                }
            } else if (value instanceof AnnotationAnnotationValue annotation) {
                AnnotationVisitor annotationVisitor = visitor.visitAnnotation(annotation.name(), annotation.descriptor());
                annotation.visit(annotationVisitor);
                annotationVisitor.visitEnd();
            } else if (value instanceof EnumAnnotationValue enumValue) {
                visitor.visitEnum(enumValue.name(), enumValue.descriptor(), enumValue.value());
            }
        }
    }
}
