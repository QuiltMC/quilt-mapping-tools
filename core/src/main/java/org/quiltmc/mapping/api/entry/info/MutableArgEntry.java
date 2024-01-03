/*
 * Copyright 2022-2023 QuiltMC
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

package org.quiltmc.mapping.api.entry.info;

import org.jetbrains.annotations.Nullable;

import org.quiltmc.mapping.api.entry.mutable.MutableParentMappingEntry;

/**
 * A mutable representation of an Argument Entry
 */
public interface MutableArgEntry extends ArgEntry, MutableParentMappingEntry<ArgEntry> {
	/**
	 * @param index the new index for the argument
	 */
	void setIndex(int index);

	/**
	 * @param namespace the namespace to set
	 * @param name      the name to set
	 */
	void setName(int namespace, @Nullable String name);
}
