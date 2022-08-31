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

package org.quiltmc.mapping;

import org.quiltmc.mapping.entry.MappingEntry;
import org.quiltmc.mapping.parser.MappingEntryParser;
import org.quiltmc.mapping.writer.MappingEntryWriter;

public record MappingType<T extends MappingEntry<T>>(String key, TokenType tokenType, Class<T> targetEntry, MappingEntryParser<T> parser, MappingEntryWriter<T> writer) {
	public enum TokenType {
		OBJECT(true, false), LITERAL(false, false), OBJECT_ARRAY(true, true), LITERAL_ARRAY(false, true);
		private final boolean isObject, isArray;

		TokenType(boolean isObject, boolean isArray) {
			this.isObject = isObject;
			this.isArray = isArray;
		}

		public boolean isObject() {
			return isObject;
		}

		public boolean isArray() {
			return isArray;
		}
	}
}
