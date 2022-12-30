package org.quiltmc.annotation_replacement.impl.entry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.quiltmc.annotation_replacement.api.entry.AnnotationAdditionEntry;
import org.quiltmc.annotation_replacement.api.entry.MutableAnnotationAdditionEntry;
import org.quiltmc.annotation_replacement.api.entry.value.AnnotationValue;
import org.quiltmc.annotation_replacement.api.entry.value.MutableAnnotationValue;
import org.quiltmc.mapping.api.entry.mutable.MutableMappingEntry;

public class MutableAnnotationAdditionEntryImpl implements MutableAnnotationAdditionEntry {
	private String descriptor;
	private final Collection<MutableAnnotationValue<?,?>> values;

	public MutableAnnotationAdditionEntryImpl(String descriptor, Collection<? extends MutableAnnotationValue<?, ?>> values) {
		this.descriptor = descriptor;
		this.values = new ArrayList<>(values);
	}

	@Override
	public Collection<? extends MutableAnnotationValue<?, ?>> values() {
		return this.values;
	}

	@Override
	public void addValue(MutableAnnotationValue<?, ?> value) {
		this.values.add(value);
	}

	@Override
	public String descriptor() {
		return this.descriptor;
	}

	@Override
	public AnnotationAdditionEntry remap() {
		return null;
	}

	@Override
	public MutableMappingEntry<AnnotationAdditionEntry> makeMutable() {
		return this;
	}

	@Override
	public void setDescriptor(String descriptor) {
		this.descriptor = descriptor;
	}

	@Override
	public AnnotationAdditionEntry makeFinal() {
		return new AnnotationAdditionEntryImpl(this.descriptor, this.values.stream().map(value -> ((AnnotationValue<?, ?>) value.makeFinal())).toList());
	}
}
