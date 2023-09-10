/*
 * Copyright 2023 QuiltMC
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

import java.util.Collection;
import java.util.Objects;

import org.quiltmc.annotation_replacement.api.entry.WriteableAnnotationInformation;
import org.quiltmc.annotation_replacement.api.entry.value.AnnotationValue;
import org.quiltmc.annotation_replacement.api.entry.value.NestedAnnotationValue;
import org.quiltmc.mapping.api.entry.mutable.MutableMappingEntry;

@SuppressWarnings("SuspiciousMethodCalls")
public record NestedAnnotationValueImpl(String name, Collection<? extends AnnotationValue<?, ?>> value, String descriptor) implements NestedAnnotationValue, WriteableAnnotationInformation {
	@Override
	public NestedAnnotationValue remap() {
		return null;
	}

	@Override
	public MutableMappingEntry<NestedAnnotationValue> makeMutable() {
		return null;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		NestedAnnotationValueImpl that = (NestedAnnotationValueImpl) o;
		return Objects.equals(name, that.name) && this.value.containsAll(that.value) && that.value.containsAll(this.value) && Objects.equals(descriptor, that.descriptor);
	}

	@Override
	public Collection<? extends AnnotationValue<?, ?>> values() {
		return this.value;
	}

	@Override
	public String toString() {
		return "NestedAnnotationValue[" +
			   "name='" + name + '\'' +
			   ", value=" + value +
			   ", descriptor='" + descriptor + '\'' +
			   ']';
	}
}
