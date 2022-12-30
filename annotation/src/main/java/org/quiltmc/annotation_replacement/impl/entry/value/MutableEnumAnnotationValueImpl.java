package org.quiltmc.annotation_replacement.impl.entry.value;

import org.quiltmc.annotation_replacement.api.entry.value.EnumAnnotationValue;
import org.quiltmc.annotation_replacement.api.entry.value.MutableEnumAnnotationValue;
import org.quiltmc.mapping.api.entry.mutable.MutableMappingEntry;

public class MutableEnumAnnotationValueImpl implements MutableEnumAnnotationValue {
	private String name;
	private String value;
	private String descriptor;

	public MutableEnumAnnotationValueImpl(String name, String value, String descriptor) {
		this.name = name;
		this.value = value;
		this.descriptor = descriptor;
	}

	@Override
	public String name() {
		return this.name;
	}

	@Override
	public String value() {
		return this.value;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String descriptor() {
		return this.descriptor;
	}

	@Override
	public EnumAnnotationValue remap() {
		return null;
	}

	@Override
	public MutableMappingEntry<EnumAnnotationValue> makeMutable() {
		return this;
	}

	@Override
	public void setDescriptor(String descriptor) {
		this.descriptor = descriptor;
	}

	@Override
	public EnumAnnotationValue makeFinal() {
		return new EnumAnnotationValueImpl(this.name, this.value, this.descriptor);
	}
}
