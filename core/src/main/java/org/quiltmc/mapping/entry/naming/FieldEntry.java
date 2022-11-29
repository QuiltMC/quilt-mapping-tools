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

package org.quiltmc.mapping.entry.naming;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.Nullable;
import org.quiltmc.mapping.MappingType;
import org.quiltmc.mapping.entry.AbstractParentDescriptorMappingEntry;
import org.quiltmc.mapping.entry.MappingEntry;
import org.quiltmc.mapping.parser.QuiltMappingParser;
import org.quiltmc.mapping.writer.QuiltMappingWriter;

public class FieldEntry extends AbstractParentDescriptorMappingEntry<FieldEntry> {
	public static final MappingType<FieldEntry> FIELD_MAPPING_TYPE = new MappingType<>("fields", MappingType.TokenType.OBJECT_ARRAY, FieldEntry.class, FieldEntry::parse, FieldEntry::write);

	protected FieldEntry(String fromName, @Nullable String toName, String descriptor, List<MappingEntry<?>> children) {
		super(fromName, toName, descriptor, children, FIELD_MAPPING_TYPE);
	}

	public static FieldEntry parse(QuiltMappingParser parser) {
		String fromName = null;
		String toName = null;
		String descriptor = null;
		List<MappingEntry<?>> children = new ArrayList<>();

		while (parser.hasValues()) {
			String name = parser.valueName();
			switch (name) {
				case "from_name" -> fromName = parser.string();
				case "descriptor" -> descriptor = parser.string();
				case "to_name" -> toName = parser.string();
				default -> parser.parseChildToken(children, name);
			}
		}

		parser.checkValuePresent("from_name", fromName);
		parser.checkValuePresent("descriptor", descriptor);
		parser.checkValue("to_name", toName, String::isEmpty);

		return new FieldEntry(fromName, toName, descriptor, children);
	}

	public void write(QuiltMappingWriter writer) {
		writer.writeString("from_name", fromName);
		if (this.toName != null && !this.toName.isEmpty()) {
			writer.writeString("to_name", this.toName);
		}
		writer.writeString("descriptor", this.descriptor);

		writer.writeChildren(this.children);
	}

	@Override
	public FieldEntry remap() {
		return null;
	}

	@Override
	public List<MappingType<?>> getTargetTypes() {
		return List.of(ClassEntry.CLASS_MAPPING_TYPE);
	}


	@Override
	public FieldEntry merge(MappingEntry<?> other) {
		FieldEntry field = ((FieldEntry) other);
		List<MappingEntry<?>> children = mergeChildren(this.children, field.children);
		return new FieldEntry(this.fromName, this.toName != null ? this.toName : field.toName, this.descriptor, children);
	}

	@Override
	public String toString() {
		return "FieldEntry[" +
			   "fromName='" + fromName + '\'' +
			   ", toName='" + toName + '\'' +
			   ", children=" + children +
			   ", descriptor='" + descriptor + '\'' +
			   ']';
	}
}
