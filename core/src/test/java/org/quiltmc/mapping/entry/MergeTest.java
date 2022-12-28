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

package org.quiltmc.mapping.entry;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.quiltmc.mapping.MappingType;
import org.quiltmc.mapping.api.entry.info.ArgEntry;
import org.quiltmc.mapping.api.entry.info.CommentEntry;
import org.quiltmc.mapping.api.entry.info.ReturnEntry;
import org.quiltmc.mapping.api.entry.naming.ClassEntry;
import org.quiltmc.mapping.api.entry.naming.FieldEntry;
import org.quiltmc.mapping.api.entry.naming.MethodEntry;
import org.quiltmc.mapping.impl.entry.transitive.TransitiveEntryImpl;
import org.quiltmc.mapping.impl.entry.unpick.UnpickEntryImpl;
import org.quiltmc.mapping.file.QuiltMappingFile;
import org.quiltmc.mapping.parser.QuiltMappingParser;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MergeTest {
	public static final List<MappingType<?>> TYPES = List.of(
			ClassEntry.CLASS_MAPPING_TYPE,
			MethodEntry.METHOD_MAPPING_TYPE,
			FieldEntry.FIELD_MAPPING_TYPE,
			CommentEntry.COMMENT_MAPPING_TYPE,
			ArgEntry.ARG_MAPPING_TYPE,
			ReturnEntry.RETURN_MAPPING_TYPE,
			UnpickEntryImpl.UNPICK_MAPPING_TYPE,
			TransitiveEntryImpl.TRANSITIVE_MAPPING_TYPE);

	@Test
	void testMergeFromFile() throws IOException {
		QuiltMappingParser parser = new QuiltMappingParser(TYPES);

		String testMapping = getInput("org/quiltmc/mapping/parser/TestMapping.quiltmapping");
		QuiltMappingFile testMappingFile = parser.parse(testMapping);

		String testMappingSmall = getInput("org/quiltmc/mapping/parser/TestMappingSmall.quiltmapping");
		QuiltMappingFile testMappingSmallFile = parser.parse(testMappingSmall);

		String testMappingMerged = getInput("org/quiltmc/mapping/parser/TestMappingMerged.quiltmapping");
		QuiltMappingFile testMappingMergedFile = parser.parse(testMappingMerged);

		assertEquals(testMappingMergedFile, testMappingFile.merge(testMappingSmallFile), "Merged correctly");
	}

	private String getInput(String name) throws IOException {
		InputStream testMappingResource = MergeTest.class.getClassLoader().getResourceAsStream(name);
		assert testMappingResource != null;
		return new String(testMappingResource.readAllBytes());
	}
}
