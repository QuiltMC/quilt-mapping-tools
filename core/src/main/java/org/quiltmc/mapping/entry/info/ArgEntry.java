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

package org.quiltmc.mapping.entry.info;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.Nullable;
import org.quiltmc.mapping.MappingType;
import org.quiltmc.mapping.entry.AbstractParentMappingEntry;
import org.quiltmc.mapping.entry.MappingEntry;
import org.quiltmc.mapping.entry.naming.MethodEntry;
import org.quiltmc.mapping.parser.QuiltMappingParser;
import org.quiltmc.mapping.writer.QuiltMappingsWriter;

public record ArgEntry(int index, @Nullable String name, List<MappingEntry<?>> children) implements MappingEntry<ArgEntry> {
	public static final MappingType<ArgEntry> ARG_MAPPING_TYPE = new MappingType<>("args", MappingType.TokenType.OBJECT_ARRAY, ArgEntry.class, ArgEntry::parse, ArgEntry::write);

	public static ArgEntry parse(QuiltMappingParser parser) {
		int index = -1;
		String argName = null;
		List<MappingEntry<?>> children = new ArrayList<>();

		while (parser.hasValues()) {
			String name = parser.valueName();
			switch (name) {
				case "index" -> index = parser.integerValue();
				case "name" -> argName = parser.string();
				default -> parser.parseChildToken(children, name);
			}
		}

		parser.checkValue("index", index, i -> i > 0);

		return new ArgEntry(index, argName, List.copyOf(children));
	}

	public void write(QuiltMappingsWriter writer) {
		writer.writeInt("index", this.index);
		if (this.name != null && !this.name.isEmpty()) {
			writer.writeString("name", this.name);
		}
		writer.writeChildren(this.children);
	}

	@Override
	public boolean shouldMerge(MappingEntry<?> other) {
		return MappingEntry.super.shouldMerge(other) && this.index == ((ArgEntry) other).index;
	}

	@Override
	public ArgEntry merge(MappingEntry<?> other) {
		ArgEntry arg = (ArgEntry) other;
		return new ArgEntry(this.index, this.name != null ? this.name : arg.name, AbstractParentMappingEntry.mergeChildren(this.children, arg.children));
	}

	@Override
	public ArgEntry remap() {
		return this;
	}

	@Override
	public MappingType<ArgEntry> getType() {
		return ARG_MAPPING_TYPE;
	}

	@Override
	public List<MappingType<?>> getTargetTypes() {
		return List.of(MethodEntry.METHOD_MAPPING_TYPE);
	}
}
