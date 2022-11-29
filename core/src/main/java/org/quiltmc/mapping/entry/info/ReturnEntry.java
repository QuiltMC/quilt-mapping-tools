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

import org.quiltmc.mapping.MappingType;
import org.quiltmc.mapping.entry.AbstractParentMappingEntry;
import org.quiltmc.mapping.entry.MappingEntry;
import org.quiltmc.mapping.entry.naming.MethodEntry;
import org.quiltmc.mapping.parser.QuiltMappingParser;
import org.quiltmc.mapping.writer.QuiltMappingWriter;

public record ReturnEntry(List<MappingEntry<?>> children) implements MappingEntry<ReturnEntry> {
	public static final MappingType<ReturnEntry> RETURN_MAPPING_TYPE = new MappingType<>("return", MappingType.TokenType.OBJECT, ReturnEntry.class, ReturnEntry::parse, ReturnEntry::write);

	public static ReturnEntry parse(QuiltMappingParser parser) {
		List<MappingEntry<?>> children = new ArrayList<>();

		while (parser.hasValues()) {
			String name = parser.valueName();
			parser.parseChildToken(children, name);
		}

		return new ReturnEntry(List.copyOf(children));
	}

	public void write(QuiltMappingWriter writer) {
		writer.writeChildren(this.children);
	}

	@Override
	public ReturnEntry merge(MappingEntry<?> other) {
		ReturnEntry ret = (ReturnEntry) other;
		return new ReturnEntry(AbstractParentMappingEntry.mergeChildren(this.children, ret.children));
	}

	@Override
	public ReturnEntry remap() {
		return this;
	}

	@Override
	public MappingType<ReturnEntry> getType() {
		return RETURN_MAPPING_TYPE;
	}

	@Override
	public List<MappingType<?>> getTargetTypes() {
		return List.of(MethodEntry.METHOD_MAPPING_TYPE);
	}
}
