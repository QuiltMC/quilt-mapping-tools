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

package org.quiltmc.mapping.entry.annotation.value;

import java.util.Arrays;
import java.util.Objects;

public record LiteralAnnotationValue(String name, Object value, String descriptor) implements AnnotationValue {
	@Override
	public AnnotationValue remap() {
		return null;
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
		LiteralAnnotationValue that = (LiteralAnnotationValue) o;
		boolean valuesEqual = this.value.getClass().isArray() && that.value.getClass().isArray() ? Arrays.deepEquals((Object[]) this.value, (Object[]) that.value) : Objects.equals(this.value, that.value);
		return Objects.equals(name, that.name) && valuesEqual && Objects.equals(descriptor, that.descriptor);
	}
}
