package org.quiltmc.annotation_replacement.api.entry;

import org.quiltmc.mapping.api.entry.MappingEntry;
import org.quiltmc.mapping.api.entry.MappingType;

import java.util.List;

public interface AnnotationModificationEntry extends MappingEntry<AnnotationModificationEntry> {
	MappingType<AnnotationModificationEntry> ANNOTATION_MODIFICATION_MAPPING_TYPE = new MappingType<>("annotation_modifications", AnnotationModificationEntry.class, mappingType -> true);

	List<? extends AnnotationRemovalEntry> removals();

	List<? extends AnnotationAdditionEntry> additions();

	@Override
	default MappingType<AnnotationModificationEntry> getType() {
		return ANNOTATION_MODIFICATION_MAPPING_TYPE;
	}

	@Override
	default boolean shouldMerge(MappingEntry<?> other) {
		return MappingEntry.super.shouldMerge(other);
	}

	@Override
	default AnnotationModificationEntry merge(MappingEntry<?> other) {
		return null; // TODO Implement me
	}
}
