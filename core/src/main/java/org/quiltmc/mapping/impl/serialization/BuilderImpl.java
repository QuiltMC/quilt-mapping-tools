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
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.quiltmc.mapping.api.serialization.exception.MissingValueException;
import org.quiltmc.mapping.api.serialization.Builder;
import org.quiltmc.mapping.api.serialization.ReadableParser;
import org.quiltmc.mapping.api.serialization.Serializer;
import org.quiltmc.mapping.api.serialization.WritableParser;

public abstract class BuilderImpl<T, S extends Builder<T, S>> implements Builder<T, S> {
	List<Field<T, ?>> fields = new ArrayList<>();

	protected record Field<T, V>(String name, Serializer<V> serializer, Function<T, V> getter, boolean nullable) {
		@Override
		public String toString() {
			return "{" +
				   "name='" + name + '\'' +
				   ", serializer=" + serializer +
				   ", nullable=" + nullable +
				   '}';
		}
	}

	@Override
	public S integer(String name, Function<T, Integer> getter) {
		fields.add(new Field<>(name, Serializer.INTEGER, getter, false));
		return (S) this;
	}

	@Override
	public S nullableInteger(String name, Function<T, Integer> getter) {
		fields.add(new Field<>(name, Serializer.INTEGER, getter, true));
		return (S) this;
	}

	@Override
	public S string(String name, Function<T, String> getter) {
		fields.add(new Field<>(name, Serializer.STRING, getter, false));
		return (S) this;
	}

	@Override
	public S nullableString(String name, Function<T, String> getter) {
		fields.add(new Field<>(name, Serializer.STRING, getter, true));
		return (S) this;
	}

	@Override
	public <E extends Enum<E>> S enumValue(String name, Function<T, E> getter, Class<E> enumClass) {
		fields.add(new Field<>(name, Serializer.enumSerializer(enumClass), getter, false));
		return (S) this;
	}

	@Override
	public <E extends Enum<E>> S nullableEnum(String name, Function<T, E> getter, Class<E> enumClass) {
		fields.add(new Field<>(name, Serializer.enumSerializer(enumClass), getter, true));
		return (S) this;
	}

	@Override
	public <V> S field(String name, Serializer<V> serializer, Function<T, V> getter) {
		fields.add(new Field<>(name, serializer, getter, false));
		return (S) this;
	}

	@Override
	public Serializer<T> build(Function<BuilderArgs, T> builder) {
		return new Serializer<>() {
			@Override
			public <I> T read(ReadableParser<I> reader, I input) {
				BuilderArgsImpl args = new BuilderArgsImpl();
				for (Field<T, ?> f : fields) {
					if (reader.hasField(f.name, input)) {
						args.values.put(
								f.name, f.serializer().read(reader, reader.field(f.name, input))
						);
					}
				}
				return builder.apply(args);
			}

			@Override
			public <O> O write(T value, WritableParser<O> writer) {
				O object = writer.object();
				for (Field<T, ?> f : fields) {
					Object val = f.getter.apply(value);
					if (val == null) {
						if (!f.nullable()) {
							throw new MissingValueException();
						}
						continue;
					}
					object = writer.field(object, f.name,
							((Serializer<Object>) f.serializer()).write(val, writer));
				}
				return object;
			}

			@Override
			public String toString() {
				return "Group{fields = [" + BuilderImpl.this.fields.stream().map(Objects::toString).collect(Collectors.joining(", ")) + "]}";
			}
		};
	}
}
