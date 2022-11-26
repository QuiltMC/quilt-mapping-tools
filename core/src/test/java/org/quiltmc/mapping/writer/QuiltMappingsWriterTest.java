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

package org.quiltmc.mapping.writer;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.quiltmc.mapping.MappingType;
import org.quiltmc.mapping.entry.info.ArgEntry;
import org.quiltmc.mapping.entry.info.CommentEntry;
import org.quiltmc.mapping.entry.info.ReturnEntry;
import org.quiltmc.mapping.entry.naming.ClassEntry;
import org.quiltmc.mapping.entry.naming.FieldEntry;
import org.quiltmc.mapping.entry.naming.MethodEntry;
import org.quiltmc.mapping.entry.transitive.TransitiveEntry;
import org.quiltmc.mapping.entry.unpick.UnpickEntry;
import org.quiltmc.mapping.file.QuiltMappingFile;
import org.quiltmc.mapping.parser.QuiltMappingParser;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QuiltMappingsWriterTest {
	public static final List<MappingType<?>> TYPES = List.of(
			ClassEntry.CLASS_MAPPING_TYPE,
			MethodEntry.METHOD_MAPPING_TYPE,
			FieldEntry.FIELD_MAPPING_TYPE,
			CommentEntry.COMMENT_MAPPING_TYPE,
			ArgEntry.ARG_MAPPING_TYPE,
			ReturnEntry.RETURN_MAPPING_TYPE,
			UnpickEntry.UNPICK_MAPPING_TYPE,
			TransitiveEntry.TRANSITIVE_MAPPING_TYPE);


	@Test
	void write() throws IOException {
		String input = new String(QuiltMappingsWriterTest.class.getClassLoader().getResourceAsStream("org/quiltmc/mapping/parser/TestMapping.quiltmapping").readAllBytes());
		QuiltMappingFile parsed = new QuiltMappingParser(input, TYPES).parse();
		QuiltMappingsWriter writer = new QuiltMappingsWriter(parsed, TYPES);
		StringWriter stringWriter = new StringWriter();
		writer.write(stringWriter);

		QuiltMappingFile actual = new QuiltMappingParser(stringWriter.toString(), TYPES).parse();
		assertEquals(parsed, actual, "Parses correctly");
	}
}
