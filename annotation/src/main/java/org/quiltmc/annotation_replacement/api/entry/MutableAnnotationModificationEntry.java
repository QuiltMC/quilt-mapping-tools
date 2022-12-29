package org.quiltmc.annotation_replacement.api.entry;

import org.quiltmc.mapping.api.entry.mutable.MutableMappingEntry;

import java.util.List;

public interface MutableAnnotationModificationEntry extends AnnotationModificationEntry, MutableMappingEntry<AnnotationModificationEntry> {
	@Override
	List<? extends MutableAnnotationRemovalEntry> removals();

	@Override
	List<? extends MutableAnnotationAdditionEntry> additions();

	void addRemoval(MutableAnnotationRemovalEntry mapping);

	void addAddition(MutableAnnotationAdditionEntry mapping);
}
