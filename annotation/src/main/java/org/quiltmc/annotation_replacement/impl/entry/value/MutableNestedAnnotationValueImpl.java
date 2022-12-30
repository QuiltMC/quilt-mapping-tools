package org.quiltmc.annotation_replacement.impl.entry.value;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.quiltmc.annotation_replacement.api.entry.value.AnnotationValue;
import org.quiltmc.annotation_replacement.api.entry.value.LiteralAnnotationValue;
import org.quiltmc.annotation_replacement.api.entry.value.MutableAnnotationValue;
import org.quiltmc.annotation_replacement.api.entry.value.MutableLiteralAnnotationValue;
import org.quiltmc.annotation_replacement.api.entry.value.MutableNestedAnnotationValue;
import org.quiltmc.annotation_replacement.api.entry.value.NestedAnnotationValue;
import org.quiltmc.mapping.api.entry.MappingEntry;
import org.quiltmc.mapping.api.entry.mutable.MutableMappingEntry;

public class MutableNestedAnnotationValueImpl implements MutableNestedAnnotationValue {
	private String name;
	private Collection<? extends MutableAnnotationValue<?, ?>> value;
	private String descriptor;

	public MutableNestedAnnotationValueImpl(String name, Collection<? extends AnnotationValue<?, ?>> values, String descriptor) {
		this.name = name;
		this.value = values.stream().map(value -> ((MutableAnnotationValue<?, ?>) value.makeMutable()))
				.collect(Collectors.toList());
		this.descriptor = descriptor;
	}

	@Override
	public String name() {
		return this.name;
	}

	@Override
	public Collection<? extends MutableAnnotationValue<?, ?>> value() {
		return this.value;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void setValue(Collection<? extends AnnotationValue<?, ?>> values) {
		this.value = values.stream().map(value -> ((MutableAnnotationValue<?, ?>) value.makeMutable()))
				.collect(Collectors.toList());
	}

	@Override
	public String descriptor() {
		return this.descriptor;
	}

	@Override
	public Collection<? extends AnnotationValue<?, ?>> values() {
		return this.value;
	}

	@Override
	public MutableNestedAnnotationValue remap() {
		return null;
	}

	@Override
	public MutableNestedAnnotationValue makeMutable() {
		return this;
	}

	@Override
	public void setDescriptor(String descriptor) {
		this.descriptor = descriptor;
	}

	@Override
	public NestedAnnotationValue makeFinal() {
		return new NestedAnnotationValueImpl(this.name, this.value.stream().map(value -> ((AnnotationValue<?, ?>) value.makeFinal())).toList(), this.descriptor);
	}
}
