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

package org.quiltmc.mapping.api.entry.unpick;

import org.jetbrains.annotations.Nullable;

import org.quiltmc.mapping.api.entry.MappingEntry;
import org.quiltmc.mapping.api.entry.MappingType;
import org.quiltmc.mapping.api.entry.MappingTypes;
import org.quiltmc.mapping.api.entry.info.ArgEntry;
import org.quiltmc.mapping.api.entry.info.ReturnEntry;
import org.quiltmc.mapping.api.entry.naming.FieldEntry;
import org.quiltmc.mapping.api.parse.Parsers;
import org.quiltmc.mapping.impl.entry.unpick.UnpickEntryImpl;

/**
 * Represents an unpick entry on a type. Unpick entries can apply to Fields, Arguments, and the Return Type. Any of these can specify the group, but it should only be serialized on the field.
 */
public interface UnpickEntry extends MappingEntry<UnpickEntry> {
	/**
	 * The Mapping Type for an Unpick Entry
	 */
	MappingType<UnpickEntry> UNPICK_MAPPING_TYPE = MappingTypes.register(new MappingType<>(
		"unpick",
		UnpickEntry.class,
		type -> type.equals(FieldEntry.FIELD_MAPPING_TYPE) || type.equals(ArgEntry.ARG_MAPPING_TYPE) || type.equals(ReturnEntry.RETURN_MAPPING_TYPE),
		Parsers.create(
			Parsers.STRING.field("group", UnpickEntry::group),
			Parsers.enumParser(UnpickType.class).nullableField("type", UnpickEntry::type),
			() -> UnpickEntry.UNPICK_MAPPING_TYPE,
			UnpickEntry::unpick
		)));

	/**
	 * @param group the unpick group
	 * @param type  the unpick type
	 * @return the new unpick entry
	 */
	static UnpickEntry unpick(String group, @Nullable UnpickType type) {
		return new UnpickEntryImpl(group, type);
	}

	/**
	 * @return the group
	 */
	String group();

	/**
	 * @return the unpick type
	 */
	@Nullable
	UnpickType type();

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

	enum UnpickType {
		/**
		 * Represents a single value
		 */
		CONSTANT,
		/**
		 * Represents a bit flag for the value
		 */
		FLAG
	}
}
