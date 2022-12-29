package org.quiltmc.annotation_replacement.api.entry;

import org.quiltmc.mapping.api.entry.DescriptorMappingEntry;
import org.quiltmc.mapping.api.entry.MappingEntry;
import org.quiltmc.mapping.api.entry.MappingType;

public interface AnnotationRemovalEntry extends DescriptorMappingEntry<AnnotationRemovalEntry> {
	MappingType<AnnotationRemovalEntry> ANNOTATION_REMOVAL_MAPPING_TYPE = new MappingType<>("removals", AnnotationRemovalEntry.class, mappingType -> mappingType.equals(AnnotationModificationEntry.ANNOTATION_MODIFICATION_MAPPING_TYPE));

	@Override
	default MappingType<AnnotationRemovalEntry> getType() {
		return ANNOTATION_REMOVAL_MAPPING_TYPE;
	}

	@Override
	default boolean shouldMerge(MappingEntry<?> other) {
		return DescriptorMappingEntry.super.shouldMerge(other) && this.getDescriptor().equals(((AnnotationRemovalEntry) other).getDescriptor());
	}

	@Override
	default AnnotationRemovalEntry merge(MappingEntry<?> other) {
		return this;
	}
}
