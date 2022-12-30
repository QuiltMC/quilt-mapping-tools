/*
 * Copyright 2022 QuiltMC
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

import java.util.Arrays;
import java.util.Objects;

import org.quiltmc.annotation_replacement.api.entry.value.AnnotationValue;
import org.quiltmc.annotation_replacement.api.entry.value.LiteralAnnotationValue;
import org.quiltmc.mapping.api.entry.mutable.MutableMappingEntry;

public record LiteralAnnotationValueImpl(String name, Object value, String descriptor) implements LiteralAnnotationValue {
	@Override
	public LiteralAnnotationValue remap() {
		return null;
	}

	@Override
	public MutableMappingEntry<LiteralAnnotationValue> makeMutable() {
		return new MutableLiteralAnnotationValueImpl(this.name, deepCopyValue(value), this.descriptor);
	}

	private Object deepCopyValue(Object value) {
		if (value.getClass().isArray()) {
			Object[] objects = ((Object[]) value);
			Object[] ret = new Object[objects.length];

			for (int i = 0; i < objects.length; i++) {
				ret[i] = deepCopyValue(objects[i]);
			}

			return ret;
		}
		return value;
	}

	@Override
	public String toString() {
		// Weird hack to make primitive arrays not crash
		String value = Arrays.deepToString(new Object[]{this.value});
		return "LiteralAnnotationValue[" +
			   "name='" + name + '\'' +
			   ", value=" + value.substring(1, value.length() - 1) +
			   ", descriptor='" + descriptor + '\'' +
			   ']';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		LiteralAnnotationValueImpl that = (LiteralAnnotationValueImpl) o;
		boolean valuesEqual = this.value.getClass().isArray() && that.value.getClass().isArray() ? Arrays.deepEquals((Object[]) this.value, (Object[]) that.value) : Objects.equals(this.value, that.value);
		return Objects.equals(name, that.name) && valuesEqual && Objects.equals(descriptor, that.descriptor);
	}
}
