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
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import org.quiltmc.mapping.api.parse.Field;
import org.quiltmc.mapping.api.parse.Parser;
import org.quiltmc.mapping.impl.serialization.TabSeparatedContent;

public class EntryParser<I> implements Parser<I, TabSeparatedContent> {
	protected final List<Field<I, ?>> fields;
	protected final Supplier<String> key;
	protected final Function<List<Object>, I> constructor;

	public EntryParser(List<Field<I, ?>> fields, Supplier<String> key, Function<List<Object>, I> constructor) {
		if (fields.stream().filter(Field::greedy).count() > 1) {
			throw new IllegalArgumentException("Cannot have more than two greedy fields");
		}

		this.fields = fields;
		this.key = key;
		this.constructor = constructor;
	}

	@SuppressWarnings("unchecked")
	protected int parseFields(TabSeparatedContent input, List<Object> values) {
		List<String> content = new ArrayList<>(input.getContent());
		content.remove(0); // Remove the key

		// I can't remember how this worked but it does
		int nullableFields = (int) this.fields.stream().filter(f -> (f.inline() && !f.greedy()) && f.nullable()).count() - (this.fields.size() - content.size());

		// First, we read the inline fields
		int contentIndex = 0;
		for (Field<I, ?> f : fields) {
			if (!f.inline() || f.greedy()) continue;

			if (f.nullable()) {
				if (nullableFields > 0) {
					nullableFields--;
				} else {
					values.add(null);
					continue;
				}
			}

			if (contentIndex == content.size()) {
				throw new RuntimeException("Not enough values provided in line");
			}

			String value = content.get(contentIndex++);
			Parser<Object, String> parser = (Parser<Object, String>) f.parser();
			Object val = parser.deserialize(value);
			values.add(val);
		}

		Optional<Field<I, ?>> greedy = this.fields.stream().filter(Field::greedy).findFirst();

		if (greedy.isPresent()) {
			Field<I, ?> f = greedy.get();
			if (f.nullable() && nullableFields == 0) {
				values.add(null);
			} else {
				List<String> value = content.subList(contentIndex, content.size());
				Parser<Object, List<String>> parser = (Parser<Object, List<String>>) f.parser();
				Object val = parser.deserialize(value);
				values.add(val);
			}
		}


		// Then we read the sub-fields
		int consumedSubcontent = 0;
		for (Field<I, ?> f : fields) {
			if (f.inline()) continue;

			List<String> value = new ArrayList<>(input.getSubcontent().get(consumedSubcontent++).getContent());
			String key = value.remove(0);
			if (f.nullable() && !f.name().equals(key.toLowerCase())) {
				consumedSubcontent--;
				continue;
			}


			Parser<Object, List<String>> parser = (Parser<Object, List<String>>) f.parser();
			Object val = parser.deserialize(value);
			values.add(val);
		}

		return consumedSubcontent;
	}

	@Override
	public I deserialize(TabSeparatedContent input) {
		List<Object> values = new ArrayList<>();

		parseFields(input, values);

		return this.constructor.apply(values);
	}

	@SuppressWarnings("unchecked")
	protected void serializeFields(I input, TabSeparatedContent content) {
		for (Field<I, ?> f : fields) {
			Object val = f.getter().apply(input);
			if (val == null && f.nullable()) {
				continue;
			}

			if (f.inline()) {
				if (f.greedy()) {
					Parser<Object, List<String>> parser = (Parser<Object, List<String>>) f.parser();
					List<String> serialized = parser.serialize(val);
					serialized.forEach(content::pushContent);
				} else {
					Parser<Object, String> parser = (Parser<Object, String>) f.parser();
					String serialized = parser.serialize(val);
					content.pushContent(serialized);
				}
			} else {
				Parser<Object, List<String>> parser = (Parser<Object, List<String>>) f.parser();
				List<String> serialized = new ArrayList<>(parser.serialize(val));
				serialized.add(0, f.name().toUpperCase());
				content.push(new TabSeparatedContent(serialized));
			}
		}
	}

	@Override
	public TabSeparatedContent serialize(I input) {
		TabSeparatedContent content = new TabSeparatedContent(new ArrayList<>());
		content.pushContent(key.get().toUpperCase());

		serializeFields(input, content);

		return content;
	}
}
