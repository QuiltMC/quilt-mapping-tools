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

import java.util.function.Predicate;

import org.quiltmc.mapping.api.parse.Parser;
import org.quiltmc.mapping.impl.serialization.TabSeparatedContent;

/**
 * A specification for the type of mapping entry.
 *
 * @param key         the name of the entry type
 * @param targetEntry the target entry class
 * @param targets     the mapping types this type can target
 * @param parser      the parser for the type
 * @param <T>         the mapping entry type
 */
public record MappingType<T extends MappingEntry<T>>(String key, Class<T> targetEntry, Predicate<MappingType<?>> targets, Parser<T, TabSeparatedContent> parser) {
}
