package org.quiltmc.annotation_replacement.api.entry.value;

import java.util.Collection;

import org.quiltmc.annotation_replacement.api.entry.AnnotationAdditionEntry;
import org.quiltmc.annotation_replacement.api.entry.WriteableAnnotationInformation;
import org.quiltmc.mapping.api.entry.MappingEntry;
import org.quiltmc.mapping.api.entry.MappingType;

public interface NestedAnnotationValue extends AnnotationValue<Collection<? extends AnnotationValue<?, ?>>, NestedAnnotationValue>, WriteableAnnotationInformation {
	MappingType<NestedAnnotationValue> NESTED_MAPPING_TYPE = new MappingType<>("nested_annotation_value", NestedAnnotationValue.class, mappingType -> mappingType.equals(AnnotationAdditionEntry.ANNOTATION_ADDITION_MAPPING_TYPE));

	@Override
	default MappingType<NestedAnnotationValue> getType() {
		return NESTED_MAPPING_TYPE;
	}

	@Override
	default boolean shouldMerge(MappingEntry<?> other) {
		boolean sameType = AnnotationValue.super.shouldMerge(other);
		if (sameType) {
			NestedAnnotationValue eav = ((NestedAnnotationValue) other);
			return this.name().equals(eav.name());
		}
		return false;
	}
}
