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

package org.quiltmc.mapping.api.entry;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * A holder for all the available mapping types. This allows for different types to be available at runtime, especially for plugins.
 */
public final class MappingTypes {
	private static final Map<String, MappingType<?>> TYPES = new HashMap<>();

	/**
	 * Registers a mapping entry to be used.
	 *
	 * @param type the mapping type
	 * @param <T>  the mapping entry type
	 * @return the type
	 */
	public static <T extends MappingEntry<T>> MappingType<T> register(MappingType<T> type) {
		var old = TYPES.put(type.key(), type);

		if (old != null) {
			throw new IllegalArgumentException(String.format("Registered two types for key: %s!\n\told: %s\n\tnew: %s", type.key(), old, type));
		}

		if (type.targets().test(type)) {
			System.out.println("Targets self before construction somehow");
		}

		return type;
	}

	/**
	 * @param type the type
	 * @return all the types that can target the type
	 */
	public static Collection<MappingType<?>> getAllTargeting(MappingType<?> type) {
		return TYPES.values().stream().filter(mappingType -> mappingType.targets().test(type)).toList();
	}

	/**
	 * @return all the types
	 */
	public static Map<String, MappingType<?>> types() {
		return Map.copyOf(TYPES);
	}
}
