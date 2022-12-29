package org.quiltmc.annotation_replacement.api.entry;

import org.quiltmc.annotation_replacement.entry.AnnotationInformation;
import org.quiltmc.mapping.api.entry.DescriptorMappingEntry;
import org.quiltmc.mapping.api.entry.MappingType;
import org.quiltmc.mapping.api.entry.ParentMappingEntry;

public interface AnnotationAdditionEntry extends ParentMappingEntry<AnnotationAdditionEntry>, DescriptorMappingEntry<AnnotationAdditionEntry>, AnnotationInformation {
	MappingType<AnnotationAdditionEntry> ANNOTATION_ADDITION_MAPPING_TYPE = new MappingType<>("additions",AnnotationAdditionEntry.class, mappingType -> mappingType.equals(AnnotationModificationEntry.ANNOTATION_MODIFICATION_MAPPING_TYPE));

	@Override
	default MappingType<AnnotationAdditionEntry> getType() {
		return ANNOTATION_ADDITION_MAPPING_TYPE;
	}

	enum ValueType {
		LITERAL, CLASS, ENUM, ANNOTATION
	}
}
