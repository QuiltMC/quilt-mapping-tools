package org.quiltmc.annotation_replacement.api.entry;

import org.quiltmc.mapping.api.entry.mutable.MutableMappingEntry;

import java.util.Collection;
import java.util.List;

public interface MutableAnnotationModificationEntry extends AnnotationModificationEntry, MutableMappingEntry<AnnotationModificationEntry> {
	@Override
	Collection<? extends MutableAnnotationAdditionEntry> additions();

	void addRemoval(String removal);

	<T extends MutableAnnotationAdditionEntry> void addAddition(T addition);
}
