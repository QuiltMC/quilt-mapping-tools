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

import java.util.function.BiFunction;

import org.quiltmc.mapping.api.serialization.ReadableParser;
import org.quiltmc.mapping.api.serialization.Serializer;
import org.quiltmc.mapping.api.serialization.WritableParser;

public class PrimitiveSerializer<T> implements Serializer<T> {
	private final String name;
	BiFunction<ReadableParser<?>, Object, T> reader;
	BiFunction<WritableParser<?>, T, Object> writer;

	public <I, O> PrimitiveSerializer(String name, BiFunction<ReadableParser<I>, I, T> reader, BiFunction<WritableParser<O>, T, O> writer) {
		this.reader = ((BiFunction) reader);
		this.writer = ((BiFunction) writer);
		this.name = name;
	}

	@Override
	public <I> T read(ReadableParser<I> reader, I input) {
		return this.reader.apply(reader, input);
	}

	@Override
	public <O> O write(T value, WritableParser<O> writer) {
		return (O) this.writer.apply(writer, value);
	}

	@Override
	public String toString() {
		return "PrimitiveSerializer{'" + name + "'}";
	}
}
