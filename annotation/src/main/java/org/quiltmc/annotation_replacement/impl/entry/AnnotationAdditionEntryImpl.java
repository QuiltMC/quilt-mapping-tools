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

package org.quiltmc.annotation_replacement.impl.entry;

import java.util.Collection;

import org.quiltmc.annotation_replacement.api.entry.AnnotationAdditionEntry;
import org.quiltmc.annotation_replacement.api.entry.value.AnnotationValue;
import org.quiltmc.annotation_replacement.api.entry.value.MutableAnnotationValue;
import org.quiltmc.mapping.api.entry.mutable.MutableMappingEntry;

public record AnnotationAdditionEntryImpl(String descriptor, Collection<? extends AnnotationValue<?, ?>> values) implements AnnotationAdditionEntry {
	@Override
	public AnnotationAdditionEntry remap() {
		return null;
	}

	@Override
	public MutableMappingEntry<AnnotationAdditionEntry> makeMutable() {
		return new MutableAnnotationAdditionEntryImpl(this.descriptor, this.values.stream().map(value -> ((MutableAnnotationValue<?, ?>) value.makeMutable())).toList());
	}

	@Override
	public String toString() {
		return "AnnotationAdditionEntry[" +
			   "descriptor='" + descriptor + '\'' +
			   ", values=" + values +
			   ']';
	}
}
