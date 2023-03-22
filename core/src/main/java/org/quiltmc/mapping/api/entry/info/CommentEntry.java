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

import org.quiltmc.mapping.api.entry.MappingEntry;
import org.quiltmc.mapping.api.entry.MappingType;
import org.quiltmc.mapping.api.entry.MappingTypes;
import org.quiltmc.mapping.api.entry.naming.ClassEntry;
import org.quiltmc.mapping.api.entry.naming.FieldEntry;
import org.quiltmc.mapping.api.entry.naming.MethodEntry;
import org.quiltmc.mapping.impl.entry.info.CommentEntryImpl;
import org.quiltmc.mapping.api.serialization.Serializer;

public interface CommentEntry extends MappingEntry<CommentEntry> {
	MappingType<CommentEntry> COMMENT_MAPPING_TYPE = MappingTypes.register(
			new MappingType<>(
					"comment",
					CommentEntry.class,
					type -> type.equals(ClassEntry.CLASS_MAPPING_TYPE) ||
							type.equals(MethodEntry.METHOD_MAPPING_TYPE) ||
							type.equals(FieldEntry.FIELD_MAPPING_TYPE) ||
							type.equals(ArgEntry.ARG_MAPPING_TYPE) ||
							type.equals(ReturnEntry.RETURN_MAPPING_TYPE),
					type -> true,
					Serializer.STRING.map(CommentEntry::comment, CommentEntry::comment)));

	String comment();

	static CommentEntry comment(String comment) {
		return new CommentEntryImpl(comment);
	}

	@Override
	default MappingType<CommentEntry> getType() {
		return COMMENT_MAPPING_TYPE;
	}
}
