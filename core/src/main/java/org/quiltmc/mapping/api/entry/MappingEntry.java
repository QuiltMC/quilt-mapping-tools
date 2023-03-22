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

package org.quiltmc.mapping.api.entry;

import org.quiltmc.mapping.api.entry.mutable.MutableMappingEntry;

public interface MappingEntry<T extends MappingEntry<T>> {
	T remap();

	MappingType<T> getType();

	default boolean shouldMerge(MappingEntry<?> other) {
		return this.getType().equals(other.getType());
	}

	@SuppressWarnings("unchecked")
	default T merge(MappingEntry<?> other) {
		return (T) this;
	}

	MutableMappingEntry<T> makeMutable();
}
