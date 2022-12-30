package org.quiltmc.annotation_replacement.impl.entry;

import java.util.Collection;
import java.util.List;

import org.quiltmc.annotation_replacement.api.entry.AnnotationAdditionEntry;
import org.quiltmc.annotation_replacement.api.entry.value.AnnotationValue;
import org.quiltmc.annotation_replacement.api.entry.value.MutableAnnotationValue;
import org.quiltmc.mapping.api.entry.mutable.MutableMappingEntry;

public record AnnotationAdditionEntryImpl(String descriptor, Collection<? extends AnnotationValue<?, ?>> values) implements AnnotationAdditionEntry {
	@Override
	public AnnotationAdditionEntry remap() {
		return null;
	}

	@Override
	public MutableMappingEntry<AnnotationAdditionEntry> makeMutable() {
		return new MutableAnnotationAdditionEntryImpl(this.descriptor, this.values.stream().map(value -> ((MutableAnnotationValue<?, ?>) value.makeMutable())).toList());
	}
}
