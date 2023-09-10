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

package org.quiltmc.mapping.impl.parse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import org.quiltmc.mapping.api.entry.MappingEntry;
import org.quiltmc.mapping.api.entry.MappingType;
import org.quiltmc.mapping.api.entry.MappingTypes;
import org.quiltmc.mapping.api.entry.ParentMappingEntry;
import org.quiltmc.mapping.api.parse.Field;
import org.quiltmc.mapping.api.parse.Parser;
import org.quiltmc.mapping.api.parse.Parsers;
import org.quiltmc.mapping.impl.serialization.TabSeparatedContent;

public class ParentEntryParser<I extends ParentMappingEntry<I>> extends EntryParser<I> {
	final Supplier<MappingType<I>> type;

	public ParentEntryParser(List<Field<I, ?>> fields, Supplier<MappingType<I>> type, Function<List<Object>, I> constructor) {
		super(fields, () -> type.get().key(), constructor);
		this.type = type;
	}

	@Override
	public I parse(TabSeparatedContent input) {
		List<Object> values = new ArrayList<>();
		int parsedSubcontents = parseFields(input, values);

		MappingType<I> type = this.type.get();
		Collection<MappingType<?>> childTypes = MappingTypes.getAllTargeting(type);
		List<TabSeparatedContent> subcontents = input.getSubcontent();
		subcontents = subcontents.subList(parsedSubcontents, subcontents.size());
		Collection<MappingEntry<?>> children = Parsers.parseChildren(childTypes, subcontents);

		values.add(children);

		return this.constructor.apply(values);
	}

	@SuppressWarnings("unchecked")
	@Override
	public TabSeparatedContent serialize(I input) {
		TabSeparatedContent content = new TabSeparatedContent(new ArrayList<>());
		content.pushContent(this.key.get().toUpperCase());

		serializeFields(input, content);

		for (MappingEntry<?> child : input.children()) {
			Parser<Object, TabSeparatedContent> typeParser = (Parser<Object, TabSeparatedContent>) child.getType().parser();
			content.push(typeParser.serialize(child));
		}

		return content;
	}
}
