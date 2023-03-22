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
import org.quiltmc.mapping.api.entry.mutable.MutableMappingEntry;

public record EnumAnnotationValueImpl(String name, String value, String descriptor) implements EnumAnnotationValue {
	@Override
	public EnumAnnotationValue remap() {
		return null;
	}

	@Override
	public MutableMappingEntry<EnumAnnotationValue> makeMutable() {
		return new MutableEnumAnnotationValueImpl(this.name, this.value, this.descriptor);
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
