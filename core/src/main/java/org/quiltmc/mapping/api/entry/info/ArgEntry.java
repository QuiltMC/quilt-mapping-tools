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

package org.quiltmc.mapping.api.entry.info;

import java.util.Collection;

import org.jetbrains.annotations.Nullable;
import org.quiltmc.mapping.api.entry.MappingEntry;
import org.quiltmc.mapping.api.entry.MappingType;
import org.quiltmc.mapping.api.entry.MappingTypes;
import org.quiltmc.mapping.api.entry.ParentMappingEntry;
import org.quiltmc.mapping.api.entry.naming.MethodEntry;
import org.quiltmc.mapping.impl.entry.info.ArgEntryImpl;
import org.quiltmc.mapping.api.serialization.Builder;

public interface ArgEntry extends ParentMappingEntry<ArgEntry> {
	MappingType<ArgEntry> ARG_MAPPING_TYPE = MappingTypes.register(
			new MappingType<>(
					"arg",
					ArgEntry.class,
					mappingType -> mappingType.equals(MethodEntry.METHOD_MAPPING_TYPE),
					Builder.EntryBuilder.<ArgEntry>entry()
							.integer("index", ArgEntry::index)
							.nullableString("name", ArgEntry::name)
							.withChildren(() -> ArgEntry.ARG_MAPPING_TYPE)
							.build(inputs -> arg(inputs.get("index"), inputs.getNullable("name"), inputs.get("children")))
			));


	static ArgEntry arg(int index, @Nullable String name, Collection<MappingEntry<?>> children) {
		return new ArgEntryImpl(index, name, children);
	}

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
