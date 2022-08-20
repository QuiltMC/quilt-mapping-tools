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

import java.util.List;
import java.util.Objects;

import org.quiltmc.mapping.entry.annotation.AnnotationInformation;

public record AnnotationAnnotationValue(String name, List<AnnotationValue> values, String descriptor) implements AnnotationValue, AnnotationInformation {
	@Override
	public AnnotationValue remap() {
		return null;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		AnnotationAnnotationValue that = (AnnotationAnnotationValue) o;
		return Objects.equals(name, that.name) && this.values.containsAll(that.values) && that.values.containsAll(this.values) && Objects.equals(descriptor, that.descriptor);
	}
}
