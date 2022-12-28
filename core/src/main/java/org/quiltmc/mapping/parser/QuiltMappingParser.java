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

package org.quiltmc.mapping.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.jetbrains.annotations.Contract;
import org.quiltmc.json5.JsonReader;
import org.quiltmc.mapping.MappingType;
import org.quiltmc.mapping.api.entry.MappingEntry;
import org.quiltmc.mapping.file.MappingHeader;
import org.quiltmc.mapping.file.QuiltMappingFile;
import org.quiltmc.mapping.parser.exception.InvalidSyntaxException;
import org.quiltmc.mapping.parser.exception.InvalidValueException;
import org.quiltmc.mapping.parser.exception.MissingValueException;


public class QuiltMappingParser {
	private static final Logger LOGGER = Logger.getLogger("quilt mapping parser");
	private final Map<String, MappingType<?>> types;

	private final ThreadLocal<JsonReader> reader = new ThreadLocal<>();

	public QuiltMappingParser(Collection<MappingType<?>> types) {
		this.types = types.stream().collect(Collectors.toMap(
				MappingType::key,
				Function.identity()
		));
	}

	public QuiltMappingFile parse(String input) {
		JsonReader jsonReader = JsonReader.json5(input);
		this.reader.set(jsonReader);

		try {
			return this.object(() -> {
				String from = null;
				String to = "";
				Set<String> extensions = Set.of();

				List<MappingEntry<?>> entries = new ArrayList<>();

				while (this.hasValues()) {
					String name = this.valueName();
					switch (name) {
						case "from" -> from = this.string();
						case "to" -> to = this.string();
						case "extensions" -> extensions = Set.copyOf(this.array(this::string));
						default -> parseChildToken(entries, name);
					}
				}

				// validation: extensions must be defined
				this.checkValue("extensions", extensions, extensionSet -> {
					for (String extension : extensionSet) {
						if (!this.types.containsKey(extension)) {
							return true;
						}
					}

					return false;
				});
				// validation: from value must exist
				this.checkValuePresent("from", from);

				return new QuiltMappingFile(new MappingHeader(from, to, extensions), entries);
			});
		} finally {
			this.reader.remove();
		}
	}

	public void parseChildToken(List<MappingEntry<?>> children, String name) {
//		if (types.containsKey(name)) {
//			MappingType<?> type = types.get(name);
//			if (type.tokenType().isArray()) {
//				children.addAll(parseMappingEntryArray(type));
//			} else {
//				children.add(parseMappingEntry(type));
//			}
//		} else {
//			LOGGER.warning("unknown token: " + name);
//			this.skip();
//		}
	}

	public <T extends MappingEntry<T>> T parseMappingEntry(MappingType<T> mappingType) {
//		if (mappingType.tokenType().isObject()) {
//			return this.object(() -> mappingType.parser().parse(this));
//		}
//
//		return mappingType.parser().parse(this);
		return null;
	}

	public <T extends MappingEntry<T>> List<T> parseMappingEntryArray(MappingType<T> mappingType) {
		return this.array(() -> parseMappingEntry(mappingType));
	}

	@Contract("_, null -> fail")
	public <T> void checkValuePresent(String name, T value) {
		checkValue(name, value, v -> v == null || v instanceof String s && s.isEmpty());
	}

	@Contract("_, null, _ -> fail")
	public <T> void checkValue(String name, T value, Predicate<T> tester) {
		if (tester.test(value)) {
			throw new MissingValueException(this, "Missing or invalid value for mapping field " + name);
		}
	}

	private <T> T wrapSyntaxError(ThrowableSupplier<T, IOException> toRun) {
		try {
			return toRun.get();
		} catch (Exception ex) {
			throw new InvalidSyntaxException(this, ex);
		}
	}

	private void wrapSyntaxError(ThrowableRunnable<IOException> toRun) {
		try {
			toRun.run();
		} catch (Exception ex) {
			throw new InvalidSyntaxException(this, ex);
		}
	}

	public boolean hasValues() {
		return wrapSyntaxError(this.reader.get()::hasNext);
	}

	public String valueName() {
		return wrapSyntaxError(() -> {
			try {
				return this.reader.get().nextName();
			} catch (IllegalStateException e) {
				throw new InvalidValueException(this, "Expected a string but found: " + this.reader.get().peek(), e);
			}
		});
	}

	public String string() {
		return wrapSyntaxError(() -> {
			try {
				return this.reader.get().nextString();
			} catch (IllegalStateException e) {
				throw new InvalidValueException(this, "Expected a string but found: " + this.reader.get().peek(), e);
			}
		});
	}

	public int integerValue() {
		return wrapSyntaxError(() -> {
			try {
				return this.reader.get().nextInt();
			} catch (IllegalStateException e) {
				throw new InvalidValueException(this, "Expected an int but found: " + this.reader.get().peek(), e);
			}
		});
	}

	public long longValue() {
		return wrapSyntaxError(() -> {
			try {
				return this.reader.get().nextLong();
			} catch (IllegalStateException e) {
				throw new InvalidValueException(this, "Expected a long but found: " + this.reader.get().peek(), e);
			}
		});
	}

	public double doubleValue() {
		return wrapSyntaxError(() -> {
			try {
				return this.reader.get().nextDouble();
			} catch (IllegalStateException e) {
				throw new InvalidValueException(this, "Expected a double but found: " + this.reader.get().peek(), e);
			}
		});
	}

	public float floatValue() {
		return wrapSyntaxError(() -> {
			try {
				return this.reader.get().nextNumber().floatValue();
			} catch (IllegalStateException e) {
				throw new InvalidValueException(this, "Expected a float but found: " + this.reader.get().peek(), e);
			}
		});
	}

	public boolean booleanValue() {
		return wrapSyntaxError(() -> {
			try {
				return this.reader.get().nextBoolean();
			} catch (IllegalStateException e) {
				throw new InvalidValueException(this, "Expected a boolean but found: " + this.reader.get().peek(), e);
			}
		});
	}

	public <T extends Enum<?>> T enumValue(Class<T> clazz) {
		T[] enums = clazz.getEnumConstants();

		String name = wrapSyntaxError(() -> {
			try {
				return this.reader.get().nextString();
			} catch (IllegalStateException e) {
				throw new InvalidValueException(this, "Expected a string but found: " + this.reader.get().peek(), e);
			}
		}).toUpperCase();

		for (T e : enums) {
			if (e.name().equals(name)) {
				return e;
			}
		}

		throw new InvalidValueException(this, "Could not find enum constant `" + name + "` in `" + clazz.getSimpleName() + "`");
	}

	public String location() {
		return this.reader.get().locationString();
	}

	public <T> List<T> array(Supplier<T> generator) {
		JsonReader jsonReader = this.reader.get();
		List<T> list = new ArrayList<>();
		this.wrapSyntaxError(jsonReader::beginArray);
		while (this.hasValues()) {
			list.add(generator.get());
		}
		this.wrapSyntaxError(jsonReader::endArray);
		return List.copyOf(list);
	}

	public <T> T object(Supplier<T> generator) {
		JsonReader jsonReader = this.reader.get();
		this.wrapSyntaxError(jsonReader::beginObject);
		T t = generator.get();
		this.wrapSyntaxError(jsonReader::endObject);
		return t;
	}

	public void skip() {
		wrapSyntaxError(this.reader.get()::skipValue);
	}

	private interface ThrowableSupplier<V, T extends Throwable> {
		V get() throws T;
	}

	private interface ThrowableRunnable<T extends Throwable> {
		void run() throws T;
	}
}
