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

package org.quiltmc.mapping.api.entry;

import java.util.Collection;
import java.util.stream.Stream;

public interface ParentMappingEntry<T extends ParentMappingEntry<T>> extends MappingEntry<T> {
	default <C extends MappingEntry<C>> Collection<C> getChildrenOfType(MappingType<C> type) {
		return children().stream().filter(mapping -> mapping.getType().equals(type)).map(type.targetEntry()::cast).toList();
	}

	default <C extends MappingEntry<C>> Stream<C> streamChildrenOfType(MappingType<C> type) {
		return children().stream().filter(mapping -> mapping.getType().equals(type)).map(type.targetEntry()::cast);
	}

	Collection<MappingEntry<?>> children();
}
