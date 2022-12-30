package org.quiltmc.annotation_replacement.api.entry.value;

import java.util.Collection;

public interface MutableNestedAnnotationValue extends NestedAnnotationValue, MutableAnnotationValue<Collection<? extends AnnotationValue<?, ?>>, NestedAnnotationValue> {
	@Override
	Collection<? extends MutableAnnotationValue<?, ?>> value();
}
