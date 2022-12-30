package org.quiltmc.annotation_replacement.api.entry.value;

import org.quiltmc.annotation_replacement.api.entry.AnnotationAdditionEntry;
import org.quiltmc.mapping.api.entry.MappingEntry;
import org.quiltmc.mapping.api.entry.MappingType;

public interface LiteralAnnotationValue extends AnnotationValue<Object, LiteralAnnotationValue> {
	MappingType<LiteralAnnotationValue> LITERAL_MAPPING_TYPE = new MappingType<>("literal_annotation_value", LiteralAnnotationValue.class, mappingType -> mappingType.equals(AnnotationAdditionEntry.ANNOTATION_ADDITION_MAPPING_TYPE));

	@Override
	default MappingType<LiteralAnnotationValue> getType() {
		return LITERAL_MAPPING_TYPE;
	}

	@Override
	default boolean shouldMerge(MappingEntry<?> other) {
		boolean sameType = AnnotationValue.super.shouldMerge(other);
		if (sameType) {
			LiteralAnnotationValue lav = ((LiteralAnnotationValue) other);
			return this.name().equals(lav.name());
		}
		return false;
	}

	@Override
	default LiteralAnnotationValue merge(MappingEntry<?> other) {
		// TODO: Always prefer this overriding the other entry? maybe adding a strategy system for this?
		return this;
	}
}
