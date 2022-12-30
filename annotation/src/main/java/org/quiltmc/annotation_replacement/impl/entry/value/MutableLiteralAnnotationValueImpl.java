package org.quiltmc.annotation_replacement.impl.entry.value;

import org.quiltmc.annotation_replacement.api.entry.value.LiteralAnnotationValue;
import org.quiltmc.annotation_replacement.api.entry.value.MutableLiteralAnnotationValue;

public class MutableLiteralAnnotationValueImpl implements MutableLiteralAnnotationValue {
	private String name;
	private Object value;
	private String descriptor;

	public MutableLiteralAnnotationValueImpl(String name, Object value, String descriptor) {
		this.name = name;
		this.value = value;
		this.descriptor = descriptor;
	}

	@Override
	public String name() {
		return this.name;
	}

	@Override
	public Object value() {
		return this.value;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	public String descriptor() {
		return this.descriptor;
	}

	@Override
	public MutableLiteralAnnotationValue remap() {
		return null;
	}

	@Override
	public MutableLiteralAnnotationValue makeMutable() {
		return this;
	}

	@Override
	public void setDescriptor(String descriptor) {
		this.descriptor = descriptor;
	}

	@Override
	public LiteralAnnotationValue makeFinal() {
		return new LiteralAnnotationValueImpl(this.name, this.value, this.descriptor);
	}
}
