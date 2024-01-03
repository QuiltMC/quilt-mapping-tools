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

package org.quiltmc.mapping.api.entry.info;

import org.quiltmc.mapping.api.entry.MappingEntry;
import org.quiltmc.mapping.api.entry.MappingType;
import org.quiltmc.mapping.api.entry.MappingTypes;
import org.quiltmc.mapping.api.entry.naming.ClassEntry;
import org.quiltmc.mapping.api.entry.naming.FieldEntry;
import org.quiltmc.mapping.api.entry.naming.MethodEntry;
import org.quiltmc.mapping.api.parse.Parsers;
import org.quiltmc.mapping.impl.entry.info.CommentEntryImpl;

/**
 * Represents a comment on other entries. Comments can be added to Classes, Methods, Fields, Arguments, and the Return entries.
 */
public interface CommentEntry extends MappingEntry<CommentEntry> {
	/**
	 * The Mapping Type for Comments.
	 */
	MappingType<CommentEntry> COMMENT_MAPPING_TYPE = MappingTypes.register(
		new MappingType<>(
			"comment",
			CommentEntry.class,
			type -> type.equals(ClassEntry.CLASS_MAPPING_TYPE) ||
					type.equals(MethodEntry.METHOD_MAPPING_TYPE) ||
					type.equals(FieldEntry.FIELD_MAPPING_TYPE) ||
					type.equals(ArgEntry.ARG_MAPPING_TYPE) ||
					type.equals(ReturnEntry.RETURN_MAPPING_TYPE),
			Parsers
				.create(
					Parsers.STRING.field("comment", CommentEntry::comment),
					() -> CommentEntry.COMMENT_MAPPING_TYPE,
					CommentEntry::comment
				)));

	/**
	 * @return the comment
	 */
	String comment();

	/**
	 * Creates a new comment.
	 *
	 * @param comment the content
	 * @return a new comment entry
	 */
	static CommentEntry comment(String comment) {
		return new CommentEntryImpl(comment);
	}

	@Override
	default MappingType<CommentEntry> getType() {
		return COMMENT_MAPPING_TYPE;
	}
}
