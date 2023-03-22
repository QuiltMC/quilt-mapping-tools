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

package org.quiltmc.mapping.impl.serialization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.quiltmc.mapping.api.entry.MappingEntry;
import org.quiltmc.mapping.api.entry.MappingType;
import org.quiltmc.mapping.api.entry.MappingTypes;
import org.quiltmc.mapping.api.entry.ParentMappingEntry;
import org.quiltmc.mapping.api.serialization.exception.MissingValueException;
import org.quiltmc.mapping.api.serialization.Builder;
import org.quiltmc.mapping.api.serialization.ReadableParser;
import org.quiltmc.mapping.api.serialization.Serializer;
import org.quiltmc.mapping.api.serialization.WritableParser;

public class EntryBuilderImpl<T extends MappingEntry<T>> extends BuilderImpl<T, Builder.EntryBuilder<T>> implements Builder.EntryBuilder<T> {
	private Supplier<MappingType<T>> type;

	@Override
	public EntryBuilder<T> withChildren(Supplier<MappingType<T>> type) {
		this.type = type;
		return this;
	}

	@Override
	public Serializer<T> build(Function<BuilderArgs, T> builder) {
		if (this.type == null) {
			return super.build(builder);
		}

		return new Serializer<>() {
			@Override
			public <I> T read(ReadableParser<I> reader, I input) {
				BuilderArgsImpl args = new BuilderArgsImpl();
				for (Field<T, ?> f : fields) {
					if (reader.hasField(f.name(), input)) {
						args.values.put(
								f.name(), f.serializer().read(reader, reader.field(f.name(), input))
						);
					}
				}

				Collection<MappingEntry<?>> children = new ArrayList<>();

				for (MappingType<?> targeting : MappingTypes.getAllTargeting(EntryBuilderImpl.this.type.get())) {
					if (reader.hasField(targeting.key(), input)) {
						if (reader.isFieldList(targeting.key(), input)) {
							if (targeting.isSingle().test(EntryBuilderImpl.this.type.get())) {
								throw new IllegalArgumentException("Single type is list");
							}

							children.addAll(
									((Serializer<MappingEntry<?>>) targeting.serializer())
											.list()
											.read(reader, reader.field(targeting.key(), input))
							);
						} else {
							if (!targeting.isSingle().test(EntryBuilderImpl.this.type.get())) {
								throw new IllegalArgumentException("Plural type is single");
							}

							children.add(
									((Serializer<MappingEntry<?>>) targeting.serializer())
											.read(reader, reader.field(targeting.key(), input))
							);
						}
					}
				}

				args.values.put("children", children);

				return builder.apply(args);
			}

			@Override
			public <O> O write(T value, WritableParser<O> writer) {
				O object = writer.object();
				for (Field<T, ?> f : fields) {
					Object val = f.getter().apply(value);
					if (val == null) {
						if (!f.nullable()) {
							throw new MissingValueException();
						}
						continue;
					}
					object = writer.field(object, f.name(),
							((Serializer<Object>) f.serializer()).write(val, writer));
				}


				if (value instanceof ParentMappingEntry<?> parentEntry) {
					for (MappingType<?> targeting : MappingTypes.getAllTargeting(EntryBuilderImpl.this.type.get())) {
						List<MappingEntry<?>> children = (List<MappingEntry<?>>) parentEntry.getChildrenOfType(targeting).stream().toList();
						if (!children.isEmpty()) {
							if (targeting.isSingle().test(EntryBuilderImpl.this.type.get())) {
								if (children.size() > 1) {
									throw new IllegalArgumentException("Single type is list");
								}

								O writtenChildren = ((Serializer<MappingEntry<?>>) targeting.serializer())
										.write(children.get(0), writer);

								object = writer.field(object, targeting.key(), writtenChildren);
							} else {
								O writtenChildren = ((Serializer<MappingEntry<?>>) targeting.serializer())
										.list()
										.write(children, writer);

								object = writer.field(object, targeting.key(), writtenChildren);
							}
						}
					}
				}

				return object;
			}

			@Override
			public String toString() {
				return "Entry{fields = [" + EntryBuilderImpl.this.fields.stream().map(Objects::toString).collect(Collectors.joining(", ")) + "]"
					   + "type = '" + EntryBuilderImpl.this.type.get().key() + "'}";
			}
		};
	}
}
