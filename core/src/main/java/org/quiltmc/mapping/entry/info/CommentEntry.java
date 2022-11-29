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

package org.quiltmc.mapping.entry.info;

import java.util.List;

import org.quiltmc.mapping.MappingType;
import org.quiltmc.mapping.entry.MappingEntry;
import org.quiltmc.mapping.entry.naming.ClassEntry;
import org.quiltmc.mapping.entry.naming.FieldEntry;
import org.quiltmc.mapping.entry.naming.MethodEntry;
import org.quiltmc.mapping.parser.QuiltMappingParser;
import org.quiltmc.mapping.writer.QuiltMappingWriter;

public record CommentEntry(String comment) implements MappingEntry<CommentEntry> {
	public static final MappingType<CommentEntry> COMMENT_MAPPING_TYPE = new MappingType<>("comment", MappingType.TokenType.LITERAL, CommentEntry.class, CommentEntry::parse, CommentEntry::write);

	public static CommentEntry parse(QuiltMappingParser parser) {
		return new CommentEntry(parser.string());
	}

	public void write(QuiltMappingWriter writer) {
		writer.writeString(this.comment);
	}

	@Override
	public CommentEntry remap() {
		return this;
	}

	@Override
	public CommentEntry merge(MappingEntry<?> other) {
		return new CommentEntry(this.comment + "\n" + ((CommentEntry) other).comment);
	}

	@Override
	public MappingType<CommentEntry> getType() {
		return COMMENT_MAPPING_TYPE;
	}

	@Override
	public List<MappingType<?>> getTargetTypes() {
		return List.of(ClassEntry.CLASS_MAPPING_TYPE, MethodEntry.METHOD_MAPPING_TYPE, FieldEntry.FIELD_MAPPING_TYPE, ArgEntry.ARG_MAPPING_TYPE, ReturnEntry.RETURN_MAPPING_TYPE);
	}
}
