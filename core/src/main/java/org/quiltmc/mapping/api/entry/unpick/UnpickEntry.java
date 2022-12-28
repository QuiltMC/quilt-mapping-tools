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

package org.quiltmc.mapping.api.entry.unpick;

import org.jetbrains.annotations.Nullable;
import org.quiltmc.mapping.MappingType;
import org.quiltmc.mapping.api.entry.MappingEntry;
import org.quiltmc.mapping.api.entry.info.ArgEntry;
import org.quiltmc.mapping.api.entry.info.ReturnEntry;
import org.quiltmc.mapping.api.entry.naming.FieldEntry;

public interface UnpickEntry extends MappingEntry<UnpickEntry> {
	MappingType<UnpickEntry> UNPICK_MAPPING_TYPE = new MappingType<>("unpick", UnpickEntry.class, mappingType -> mappingType.equals(FieldEntry.FIELD_MAPPING_TYPE) || mappingType.equals(ArgEntry.ARG_MAPPING_TYPE) || mappingType.equals(ReturnEntry.RETURN_MAPPING_TYPE));

	String group();
	@Nullable UnpickType type();

	@Override
	default MappingType<UnpickEntry> getType() {
		return UNPICK_MAPPING_TYPE;
	}

	@Override
	default UnpickEntry remap() {
		return this;
	}

	@Override
	default boolean shouldMerge(MappingEntry<?> other) {
		return MappingEntry.super.shouldMerge(other) && this.group().equals(((UnpickEntry) other).group());
	}

	@Override
	default UnpickEntry merge(MappingEntry<?> other) {
		return this;
	}
}
