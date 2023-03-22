/*
 * Copyright 2022-2023 QuiltMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

	@Override
	public String toString() {
		return "EnumAnnotationValue[" +
			   "name='" + name + '\'' +
			   ", value='" + value + '\'' +
			   ", descriptor='" + descriptor + '\'' +
			   ']';
	}
}
