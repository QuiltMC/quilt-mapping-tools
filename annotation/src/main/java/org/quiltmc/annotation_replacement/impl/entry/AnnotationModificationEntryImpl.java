package org.quiltmc.annotation_replacement.impl.entry;

import java.util.Collection;
import java.util.Set;

import org.quiltmc.annotation_replacement.api.entry.AnnotationAdditionEntry;
import org.quiltmc.annotation_replacement.api.entry.AnnotationModificationEntry;
import org.quiltmc.annotation_replacement.api.entry.MutableAnnotationAdditionEntry;
import org.quiltmc.mapping.api.entry.MappingEntry;
import org.quiltmc.mapping.api.entry.mutable.MutableMappingEntry;

public record AnnotationModificationEntryImpl(Set<String> removals, Collection<? extends AnnotationAdditionEntry> additions) implements AnnotationModificationEntry {
	@Override
	public AnnotationModificationEntry remap() {
		return null;
	}

	@Override
	public MutableMappingEntry<AnnotationModificationEntry> makeMutable() {
		return new MutableAnnotationModificationEntryImpl(this.removals, this.additions.stream().map(MappingEntry::makeMutable).map(MutableAnnotationAdditionEntry.class::cast).toList());
	}
}
