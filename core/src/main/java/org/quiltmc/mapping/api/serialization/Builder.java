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

import java.util.function.Function;
import java.util.function.Supplier;

import org.quiltmc.mapping.api.entry.MappingEntry;
import org.quiltmc.mapping.api.entry.MappingType;
import org.quiltmc.mapping.impl.serialization.EntryBuilderImpl;
import org.quiltmc.mapping.impl.serialization.GroupBuilderImpl;

public interface Builder<T, S extends Builder<T, S>> {
	S integer(String name, Function<T, Integer> getter);

	S nullableInteger(String name, Function<T, Integer> getter);

	S string(String name, Function<T, String> getter);

	S nullableString(String name, Function<T, String> getter);

	<E extends Enum<E>> S enumValue(String name, Function<T, E> getter, Class<E> enumClass);

	<E extends Enum<E>> S nullableEnum(String name, Function<T, E> getter, Class<E> enumClass);

	<V> S field(String name, Serializer<V> serializer, Function<T, V> getter);

	Serializer<T> build(Function<BuilderArgs, T> builder);

	interface BuilderArgs {
		<T> T get(String name);

		<T> T getOr(String name, T or);

		<T> T getNullable(String name);

	}

	interface GroupBuilder<T> extends Builder<T, GroupBuilder<T>> {
		static <T> GroupBuilder<T> group() {
			return new GroupBuilderImpl<>();
		}
	}

	interface EntryBuilder<T extends MappingEntry<T>> extends Builder<T, EntryBuilder<T>> {
		static <T extends MappingEntry<T>> EntryBuilder<T> entry() {
			return new EntryBuilderImpl<>();
		}

		EntryBuilder<T> withChildren(Supplier<MappingType<T>> type);
	}
}
