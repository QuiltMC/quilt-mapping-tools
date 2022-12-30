package org.quiltmc.annotation_replacement.api.entry.value;

import org.quiltmc.annotation_replacement.api.entry.AnnotationAdditionEntry;
import org.quiltmc.mapping.api.entry.MappingEntry;
import org.quiltmc.mapping.api.entry.MappingType;

public interface EnumAnnotationValue extends AnnotationValue<String, EnumAnnotationValue> {
	MappingType<EnumAnnotationValue> ENUM_MAPPING_TYPE = new MappingType<>("enum_annotation_value", EnumAnnotationValue.class, mappingType -> mappingType.equals(AnnotationAdditionEntry.ANNOTATION_ADDITION_MAPPING_TYPE));

	@Override
	default MappingType<EnumAnnotationValue> getType() {
		return ENUM_MAPPING_TYPE;
	}

	@Override
	default boolean shouldMerge(MappingEntry<?> other) {
		boolean sameType = AnnotationValue.super.shouldMerge(other);
		if (sameType) {
			EnumAnnotationValue eav = ((EnumAnnotationValue) other);
			return this.name().equals(eav.name());
		}
		return false;
	}

	@Override
	default EnumAnnotationValue merge(MappingEntry<?> other) {
		// TODO: Always prefer this overriding the other entry? maybe adding a strategy system for this?
		return this;
	}
}
