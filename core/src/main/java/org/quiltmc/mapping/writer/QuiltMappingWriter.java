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

package org.quiltmc.mapping.writer;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.quiltmc.json5.JsonWriter;
import org.quiltmc.mapping.MappingType;
import org.quiltmc.mapping.entry.MappingEntry;
import org.quiltmc.mapping.file.QuiltMappingFile;

public class QuiltMappingWriter {
	public final Map<String, MappingType<?>> types;
	private final ThreadLocal<JsonWriter> writer = new ThreadLocal<>();

	public QuiltMappingWriter(Collection<MappingType<?>> types) {
		this.types = types.stream().collect(Collectors.toMap(
				MappingType::key,
				Function.identity()
		));
	}

	public void write(QuiltMappingFile file, Writer writer) throws IOException {
		JsonWriter jsonWriter = JsonWriter.json5(writer);
		this.writer.set(jsonWriter);
		this.write(file);
		this.writer.remove();
	}

	public void write(QuiltMappingFile file, Path output) throws IOException {
		JsonWriter jsonWriter = JsonWriter.json5(output);
		this.writer.set(jsonWriter);
		this.write(file);
		this.writer.remove();
	}

	private void write(QuiltMappingFile file) throws IOException {
		this.wrapSyntaxError(this.writer.get()::beginObject);
		this.writeString("from", file.header().fromNamespace());
		this.writeString("to", file.header().toNamespace());
		this.name("extensions");
		this.array(file.header().extensions(), this::writeString);
		this.writeChildren(file.entries());
		this.wrapSyntaxError(this.writer.get()::endObject);

		this.writer.get().flush();
	}

	public void writeString(String value) {
		wrapSyntaxError(() -> this.writer.get().value(value));
	}

	public void writeString(String name, String value) {
		wrapSyntaxError(() -> this.writer.get().name(name).value(value));
	}

	public void writeNumber(String name, Number value) {
		wrapSyntaxError(() -> this.writer.get().name(name).value(value));
	}

	public void number(Number value) {
		wrapSyntaxError(() -> this.writer.get().value(value));
	}

	public void bool(Boolean value) {
		wrapSyntaxError(() -> this.writer.get().value(value));
	}

	public <T extends Enum<?>> void writeEnum(String name, T value) {
		wrapSyntaxError(() -> this.writer.get().name(name).value(value.toString().toLowerCase()));
	}

	public void writeInt(String name, int value) {
		wrapSyntaxError(() -> this.writer.get().name(name).value(value));
	}

	public void comment(String comment) {
		wrapSyntaxError(() -> this.writer.get().comment(comment));
	}

	public void writeChildren(List<MappingEntry<?>> children) {
		Map<MappingType<?>, List<MappingEntry<?>>> types = children.stream().collect(Collectors.groupingBy(MappingEntry::getType));
		for (Map.Entry<MappingType<?>, List<MappingEntry<?>>> entry : types.entrySet()) {
			writeChildTypeAnon(entry.getKey(), entry.getValue());
		}
	}

	@SuppressWarnings("unchecked")
	private <T extends MappingEntry<T>> void writeChildTypeAnon(MappingType<?> key, List<MappingEntry<?>> value) {
		writeChildType((MappingType<T>) key, (List<MappingEntry<T>>) (List<?>) value);
	}

	public void name(String name) {
		this.wrapSyntaxError(() -> this.writer.get().name(name));
	}

	public <T> void array(Collection<T> values, Consumer<T> writer) {
		this.wrapSyntaxError(this.writer.get()::beginArray);
		values.forEach(writer);
		this.wrapSyntaxError(this.writer.get()::endArray);
	}

	public void object(Runnable writer) {
		this.wrapSyntaxError(this.writer.get()::beginObject);
		writer.run();
		this.wrapSyntaxError(this.writer.get()::endObject);
	}

	@SuppressWarnings("unchecked")
	public <T extends MappingEntry<T>> void writeChildType(MappingType<T> type, List<MappingEntry<T>> values) {
		if (!type.tokenType().isArray() && values.size() > 1) {
			throw new RuntimeException("Too many values for " + type.key() + " mapping type");
		}

		this.wrapSyntaxError(() -> this.writer.get().name(type.key()));

		if (type.tokenType().isArray()) {
			this.wrapSyntaxError(this.writer.get()::beginArray);
		}

		for (MappingEntry<T> value : values) {
			if (type.tokenType().isObject()) {
				this.wrapSyntaxError(this.writer.get()::beginObject);
			}
			type.writer().write((T) value, this);
			if (type.tokenType().isObject()) {
				this.wrapSyntaxError(this.writer.get()::endObject);
			}
		}

		if (type.tokenType().isArray()) {
			this.wrapSyntaxError(this.writer.get()::endArray);
		}
	}

	private void wrapSyntaxError(ThrowableRunnable<IOException> toRun) {
		try {
			toRun.run();
		} catch (Exception ex) {
			throw new RuntimeException(ex); // TODO: change this exception?
		}
	}

	private interface ThrowableRunnable<T extends Throwable> {
		void run() throws T;
	}
}
