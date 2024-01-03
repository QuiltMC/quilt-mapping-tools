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

package org.quiltmc.mapping.api.parse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.quiltmc.mapping.api.entry.MappingEntry;
import org.quiltmc.mapping.api.entry.MappingType;
import org.quiltmc.mapping.api.entry.ParentMappingEntry;
import org.quiltmc.mapping.impl.parse.EntryParser;
import org.quiltmc.mapping.impl.parse.ParentEntryParser;
import org.quiltmc.mapping.impl.parse.PrimativeParser;
import org.quiltmc.mapping.impl.serialization.TabSeparatedContent;

/**
 * Common parsers that are used.
 */
@SuppressWarnings("unchecked")
public class Parsers {
	/**
	 * A parser for Integers
	 */
	public static final PrimativeParser<Integer> INTEGER = new PrimativeParser<>(Integer::valueOf);

	/**
	 * A parser for Bytes
	 */
	public static final PrimativeParser<Byte> BYTE = new PrimativeParser<>(Byte::valueOf);

	/**
	 * A parser for Shorts
	 */
	public static final PrimativeParser<Short> SHORT = new PrimativeParser<>(Short::valueOf);

	/**
	 * A parser for Longs
	 */
	public static final PrimativeParser<Long> LONG = new PrimativeParser<>(Long::valueOf);

	/**
	 * A parser for Floats
	 */
	public static final PrimativeParser<Float> FLOAT = new PrimativeParser<>(Float::valueOf);

	/**
	 * A parser for Doubles
	 */
	public static final PrimativeParser<Double> DOUBLE = new PrimativeParser<>(Double::valueOf);

	/**
	 * A parser for Booleans
	 */
	public static final PrimativeParser<Boolean> BOOLEAN = new PrimativeParser<>(Boolean::valueOf);

	/**
	 * A parser for Strings
	 */
	public static final PrimativeParser<String> STRING = new PrimativeParser<>(Function.identity());

	/**
	 * Creates an entry parser with one field.
	 *
	 * @param field1      the field
	 * @param key         the name for the entry
	 * @param constructor the constructor for the entry
	 * @param <I>         the type of the entry
	 * @param <T1>        the type of the field
	 * @return the entry parser
	 */
	public static <I, T1> EntryParser<I> create(Field<I, T1> field1, String key, App1<I, T1> constructor) {
		return new EntryParser<>(List.of(field1), () -> key, l -> constructor.apply((T1) l.get(0)));
	}

	/**
	 * Creates an entry parser with one field.
	 *
	 * @param field1      the field
	 * @param type        the entry type for the entry
	 * @param constructor the constructor for the entry
	 * @param <I>         the type of the entry
	 * @param <T1>        the type of the field
	 * @return the entry parser
	 */
	public static <I, T1> EntryParser<I> create(Field<I, T1> field1, Supplier<MappingType<?>> type, App1<I, T1> constructor) {
		return new EntryParser<>(List.of(field1), () -> type.get().key(), l -> constructor.apply((T1) l.get(0)));
	}

	/**
	 * Creates an entry parser with two fields.
	 *
	 * @param field1      the first field
	 * @param field2      the second field
	 * @param key         the name for the entry
	 * @param constructor the constructor for the entry
	 * @param <I>         the type of the entry
	 * @param <T1>        the type of the first field
	 * @param <T2>        the type of the second field
	 * @return the entry parser
	 */
	public static <I, T1, T2> EntryParser<I> create(Field<I, T1> field1, Field<I, T2> field2, String key, App2<I, T1, T2> constructor) {
		return new EntryParser<>(List.of(field1, field2), () -> key, l -> constructor.apply((T1) l.get(0), (T2) l.get(1)));
	}

	/**
	 * Creates an entry parser with two fields.
	 *
	 * @param field1      the first field
	 * @param field2      the second field
	 * @param type        the entry type for the entry
	 * @param constructor the constructor for the entry
	 * @param <I>         the type of the entry
	 * @param <T1>        the type of the first field
	 * @param <T2>        the type of the second field
	 * @return the entry parser
	 */
	public static <I, T1, T2> EntryParser<I> create(Field<I, T1> field1, Field<I, T2> field2, Supplier<MappingType<?>> type, App2<I, T1, T2> constructor) {
		return new EntryParser<>(List.of(field1, field2), () -> type.get().key(), l -> constructor.apply((T1) l.get(0), (T2) l.get(1)));
	}

	/**
	 * Creates an entry parser with three fields.
	 *
	 * @param field1      the first field
	 * @param field2      the second field
	 * @param field3      the third field
	 * @param key         the name for the entry
	 * @param constructor the constructor for the entry
	 * @param <I>         the type of the entry
	 * @param <T1>        the type of the first field
	 * @param <T2>        the type of the second field
	 * @param <T3>        the type of the third field
	 * @return the entry parser
	 */
	public static <I, T1, T2, T3> EntryParser<I> create(Field<I, T1> field1, Field<I, T2> field2, Field<I, T3> field3, String key, App3<I, T1, T2, T3> constructor) {
		return new EntryParser<>(List.of(field1, field2, field3), () -> key, l -> constructor.apply((T1) l.get(0), (T2) l.get(1), (T3) l.get(2)));
	}

	/**
	 * Creates an entry parser with three fields.
	 *
	 * @param field1      the first field
	 * @param field2      the second field
	 * @param field3      the third field
	 * @param type        the entry type for the entry
	 * @param constructor the constructor for the entry
	 * @param <I>         the type of the entry
	 * @param <T1>        the type of the first field
	 * @param <T2>        the type of the second field
	 * @param <T3>        the type of the third field
	 * @return the entry parser
	 */
	public static <I, T1, T2, T3> EntryParser<I> create(Field<I, T1> field1, Field<I, T2> field2, Field<I, T3> field3, Supplier<MappingType<?>> type, App3<I, T1, T2, T3> constructor) {
		return new EntryParser<>(List.of(field1, field2, field3), () -> type.get().key(), l -> constructor.apply((T1) l.get(0), (T2) l.get(1), (T3) l.get(2)));
	}

	/**
	 * Creates an entry parser with four fields.
	 *
	 * @param field1      the first field
	 * @param field2      the second field
	 * @param field3      the third field
	 * @param field4      the fourth field
	 * @param key         the name for the entry
	 * @param constructor the constructor for the entry
	 * @param <I>         the type of the entry
	 * @param <T1>        the type of the first field
	 * @param <T2>        the type of the second field
	 * @param <T3>        the type of the third field
	 * @param <T4>        the type of the fourth field
	 * @return the entry parser
	 */
	public static <I, T1, T2, T3, T4> EntryParser<I> create(Field<I, T1> field1, Field<I, T2> field2, Field<I, T3> field3, Field<I, T4> field4, String key, App4<I, T1, T2, T3, T4> constructor) {
		return new EntryParser<>(List.of(field1, field2, field3, field4), () -> key, l -> constructor.apply((T1) l.get(0), (T2) l.get(1), (T3) l.get(2), (T4) l.get(3)));
	}

	/**
	 * Creates an entry parser with four fields.
	 *
	 * @param field1      the first field
	 * @param field2      the second field
	 * @param field3      the third field
	 * @param field4      the fourth field
	 * @param type        the entry type for the entry
	 * @param constructor the constructor for the entry
	 * @param <I>         the type of the entry
	 * @param <T1>        the type of the first field
	 * @param <T2>        the type of the second field
	 * @param <T3>        the type of the third field
	 * @param <T4>        the type of the fourth field
	 * @return the entry parser
	 */
	public static <I, T1, T2, T3, T4> EntryParser<I> create(Field<I, T1> field1, Field<I, T2> field2, Field<I, T3> field3, Field<I, T4> field4, Supplier<MappingType<?>> type, App4<I, T1, T2, T3, T4> constructor) {
		return new EntryParser<>(List.of(field1, field2, field3, field4), () -> type.get().key(), l -> constructor.apply((T1) l.get(0), (T2) l.get(1), (T3) l.get(2), (T4) l.get(3)));
	}

	public static <I extends ParentMappingEntry<I>> ParentEntryParser<I> createParent(Supplier<MappingType<I>> type, App1<I, Collection<MappingEntry<?>>> constructor) {
		return new ParentEntryParser<>(List.of(), type, l -> constructor.apply((Collection<MappingEntry<?>>) l.get(0)));
	}

	public static <I extends ParentMappingEntry<I>, T1> ParentEntryParser<I> createParent(Field<I, T1> field1, Supplier<MappingType<I>> type, App2<I, T1, Collection<MappingEntry<?>>> constructor) {
		return new ParentEntryParser<>(List.of(field1), type, l -> constructor.apply((T1) l.get(0), (Collection<MappingEntry<?>>) l.get(1)));
	}

	public static <I extends ParentMappingEntry<I>, T1, T2> ParentEntryParser<I> createParent(Field<I, T1> field1, Field<I, T2> field2, Supplier<MappingType<I>> type, App3<I, T1, T2, Collection<MappingEntry<?>>> constructor) {
		return new ParentEntryParser<>(List.of(field1, field2), type, l -> constructor.apply((T1) l.get(0), (T2) l.get(1), (Collection<MappingEntry<?>>) l.get(2)));
	}

	public static <I extends ParentMappingEntry<I>, T1, T2, T3> ParentEntryParser<I> createParent(Field<I, T1> field1, Field<I, T2> field2, Field<I, T3> field3, Supplier<MappingType<I>> type, App4<I, T1, T2, T3, Collection<MappingEntry<?>>> constructor) {
		return new ParentEntryParser<>(List.of(field1, field2, field3), type, l -> constructor.apply((T1) l.get(0), (T2) l.get(1), (T3) l.get(2), (Collection<MappingEntry<?>>) l.get(3)));
	}

	/**
	 * Creates a parser for an enum using the lowercase name by default.
	 *
	 * @param enumClass the class for the enum
	 * @param <I>       the enum class
	 * @return the parser
	 */
	public static <I extends Enum<I>> Parser<I, String> enumParser(Class<I> enumClass) {
		return enumParser(enumClass, e -> e.name().toLowerCase());
	}

	/**
	 * Creates a parser for an enum.
	 *
	 * @param enumClass the class for the enum
	 * @param toString  the method to convert the enum to a string representation
	 * @param <I>       the enum class
	 * @return the parser
	 */
	public static <I extends Enum<I>> Parser<I, String> enumParser(Class<I> enumClass, Function<I, String> toString) {
		return new Parser<>() {
			final Map<String, I> constants = Arrays.stream(enumClass.getEnumConstants()).collect(Collectors.toUnmodifiableMap(toString, Function.identity()));

			@Override
			public I deserialize(String input) {
				return constants.get(input);
			}

			@Override
			public String serialize(I input) {
				return toString.apply(input);
			}
		};
	}

	/**
	 * A functional interface that takes in one parameter.
	 *
	 * @param <I>  the return type
	 * @param <T1> the parameter type
	 */
	public interface App1<I, T1> {
		I apply(T1 arg1);
	}

	/**
	 * A functional interface that takes in two parameters.
	 *
	 * @param <I>  the return type
	 * @param <T1> the first parameter type
	 * @param <T2> the second parameter type
	 */
	public interface App2<I, T1, T2> {
		I apply(T1 arg1, T2 arg2);
	}

	/**
	 * A functional interface that takes in three parameters.
	 *
	 * @param <I>  the return type
	 * @param <T1> the first parameter type
	 * @param <T2> the second parameter type
	 * @param <T3> the third parameter type
	 */
	public interface App3<I, T1, T2, T3> {
		I apply(T1 arg1, T2 arg2, T3 arg3);
	}

	/**
	 * A functional interface that takes in four parameters.
	 *
	 * @param <I>  the return type
	 * @param <T1> the first parameter type
	 * @param <T2> the second parameter type
	 * @param <T3> the third parameter type
	 * @param <T4> the fourth parameter type
	 */
	public interface App4<I, T1, T2, T3, T4> {
		I apply(T1 arg1, T2 arg2, T3 arg3, T4 arg4);
	}

	/**
	 * Parses the children for the given mapping type, returning them in a collection
	 *
	 * @param childTypes  the types to parse
	 * @param subcontents the content to parse
	 * @return the parsed children
	 */
	public static Collection<MappingEntry<?>> parseChildren(Collection<MappingType<?>> childTypes, List<TabSeparatedContent> subcontents) {
		Collection<MappingEntry<?>> children = new ArrayList<>();
		for (TabSeparatedContent subcontent : subcontents) {
			String key = subcontent.getContent().get(0);
			Optional<MappingType<?>> childType = childTypes.stream().filter(t -> t.key().equals(key.toLowerCase())).findAny();
			if (childType.isEmpty()) {
				throw new RuntimeException("Unknown key: " + key);
			}

			MappingType<?> mappingType = childType.get();
			Parser<?, TabSeparatedContent> parser = mappingType.parser();

			children.add((MappingEntry<?>) parser.deserialize(subcontent));
		}

		return children;
	}
}
