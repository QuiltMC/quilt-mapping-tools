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

package org.quiltmc.mapping.api.entry.info;

import org.jetbrains.annotations.Nullable;
import org.quiltmc.mapping.MappingType;
import org.quiltmc.mapping.api.entry.MappingEntry;
import org.quiltmc.mapping.api.entry.ParentMappingEntry;
import org.quiltmc.mapping.api.entry.naming.MethodEntry;

public interface ArgEntry extends ParentMappingEntry<ArgEntry> {
	MappingType<ArgEntry> ARG_MAPPING_TYPE = new MappingType<>("args", ArgEntry.class, mappingType -> mappingType.equals(MethodEntry.METHOD_MAPPING_TYPE));

	@Override
	default boolean shouldMerge(MappingEntry<?> other) {
		return ParentMappingEntry.super.shouldMerge(other) && this.index() == ((ArgEntry) other).index();
	}

	int index();
	@Nullable String name();

	@Override
	default MappingType<ArgEntry> getType() {
		return ARG_MAPPING_TYPE;
	}
}
