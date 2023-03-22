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

package org.quiltmc.mapping.api.serialization;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.quiltmc.mapping.impl.serialization.PrimitiveSerializer;

public interface Serializer<V> {
	<I> V read(ReadableParser<I> reader, I input);

	<O> O write(V value, WritableParser<O> writer);

	default Serializer<List<V>> list() {
		return new Serializer<>() {
			@Override
			public <I> List<V> read(ReadableParser<I> reader, I input) {
				Iterator<I> list = reader.list(input);
				List<V> values = new ArrayList<>();
				while (list.hasNext()) {
					I element = list.next();
					values.add(Serializer.this.read(reader, element));
				}

				return values;
			}

			@Override
			public <O> O write(List<V> value, WritableParser<O> writer) {
				return writer.list(value.stream().map(v -> Serializer.this.write(v, writer)).toList());
			}

			@Override
			public String toString() {
				return "List{" + Serializer.this + "}";
			}
		};
	}

	default <O> Serializer<O> map(Function<V, O> into, Function<O, V> from) {
		return new Serializer<>() {
			@Override
			public <I> O read(ReadableParser<I> reader, I input) {
				return into.apply(Serializer.this.read(reader, input));
			}

			@Override
			public <O1> O1 write(O value, WritableParser<O1> writer) {
				return Serializer.this.write(from.apply(value), writer);
			}

			@Override
			public String toString() {
				return "Mapped{" + Serializer.this + "}";
			}
		};
	}

	static <V, K> Serializer<V> dispatch(Serializer<K> key, Function<V, K> type, Function<K, Serializer<? extends V>> serializers) {
		return dispatch("type", key, type, serializers);
	}

	static <V, K> Serializer<V> dispatch(String keyName, Serializer<K> key, Function<V, K> type, Function<K, Serializer<? extends V>> serializers) {
		return new Serializer<>() {
			@Override
			public <I> V read(ReadableParser<I> reader, I input) {
				if (!reader.hasField(keyName, input)) {
					throw new IllegalArgumentException("no type key");
				}

				K readType = key.read(reader, reader.field(keyName, input));
				Serializer<? extends V> serializer = serializers.apply(readType);

				return serializer.read(reader, input);
			}

			private static <T> T unbox(Object o) {
				return (T) o;
			}

			@Override
			public <O> O write(V value, WritableParser<O> writer) {
				K writeType = type.apply(value);
				Serializer<? extends V> serializer = serializers.apply(writeType);
				O written = serializer.write(unbox(value), writer);
				return writer.field(written, keyName, key.write(writeType, writer));
			}

			@Override
			public String toString() {
				return "Dispatched{keyName = '" + keyName + "', key = " + key + "}";
			}
		};
	}

	static <V> Serializer<V> lazy(Supplier<Serializer<V>> supplier) {
		return new Serializer<V>() {
			@Override
			public <I> V read(ReadableParser<I> reader, I input) {
				return supplier.get().read(reader, input);
			}

			@Override
			public <O> O write(V value, WritableParser<O> writer) {
				return supplier.get().write(value, writer);
			}

			@Override
			public String toString() {
				return "Lazy{" + supplier.get() + "}";
			}
		};
	}

	Serializer<Boolean> BOOLEAN = new PrimitiveSerializer<>("boolean", ReadableParser::readBoolean, WritableParser::writeBoolean);
	Serializer<Byte> BYTE = new PrimitiveSerializer<>("byte", ReadableParser::readByte, WritableParser::writeByte);
	Serializer<Short> SHORT = new PrimitiveSerializer<>("short", ReadableParser::readShort, WritableParser::writeShort);
	Serializer<Integer> INTEGER = new PrimitiveSerializer<>("integer", ReadableParser::readInteger, WritableParser::writeInteger);
	Serializer<Long> LONG = new PrimitiveSerializer<>("long", ReadableParser::readLong, WritableParser::writeLong);
	Serializer<Float> FLOAT = new PrimitiveSerializer<>("float", ReadableParser::readFloat, WritableParser::writeFloat);
	Serializer<Double> DOUBLE = new PrimitiveSerializer<>("double", ReadableParser::readDouble, WritableParser::writeDouble);
	Serializer<String> STRING = new PrimitiveSerializer<>("string", ReadableParser::readString, WritableParser::writeString);

	static <E extends Enum<E>> Serializer<E> enumSerializer(Class<E> enumClass) {
		return enumSerializer(enumClass, e -> e.name().toLowerCase());
	}

	static <E extends Enum<E>> Serializer<E> enumSerializer(Class<E> enumClass, Function<E, String> toString) {
		return new Serializer<>() {
			final Map<String, E> constants = Arrays.stream(enumClass.getEnumConstants()).collect(Collectors.toUnmodifiableMap(toString, Function.identity()));

			@Override
			public <I> E read(ReadableParser<I> reader, I input) {
				return constants.get(reader.readString(input));
			}

			@Override
			public <O> O write(E value, WritableParser<O> writer) {
				return writer.writeString(toString.apply(value));
			}

			@Override
			public String toString() {
				return "EnumSerializer{'" + enumClass.getSimpleName() + "'}";
			}
		};
	}
}
