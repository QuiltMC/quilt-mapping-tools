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

package org.quiltmc.mapping.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.jupiter.api.Assertions;
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
import org.quiltmc.mapping.parser.exception.ParsingException;

class QuiltMappingParserTest {
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
	void main() throws IOException {
		// test: test mapping file
		// this test should parse as normal with no issues
		String testMapping = getInput("org/quiltmc/mapping/parser/TestMapping.quiltmapping");
		System.out.println("TestMapping.quiltmapping parsed output:");
		System.out.println(new QuiltMappingParser(testMapping, TYPES).parse());

		// test: test mapping file with negative argument index
		String negativeArgIndexTest = getInput("org/quiltmc/mapping/parser/fail_cases/NegativeArgumentIndexTestMapping.quiltmapping");
		QuiltMappingParser negativeArgParser = new QuiltMappingParser(negativeArgIndexTest, TYPES);
		Assertions.assertThrows(ParsingException.class, negativeArgParser::parse);

		// test: test mapping file with negative argument index
		String nonexistentExtensionTest = getInput("org/quiltmc/mapping/parser/fail_cases/NonexistentExtensionTestMapping.quiltmapping");
		QuiltMappingParser nonexistentExtensionParser = new QuiltMappingParser(nonexistentExtensionTest, TYPES);
		Assertions.assertThrows(ParsingException.class, nonexistentExtensionParser::parse);

		// test: test mapping file with an incorrect value type
		String incorrectValueTypeTest = getInput("org/quiltmc/mapping/parser/fail_cases/ExpectedIntegerTestMapping.quiltmapping");
		QuiltMappingParser incorrectValueTypeParser = new QuiltMappingParser(incorrectValueTypeTest, TYPES);
		Assertions.assertThrows(ParsingException.class, incorrectValueTypeParser::parse);

		// test: test mapping file with extra nonsense in it
		// this test should pass and ignore the all the nonsense
		// it should only print warnings
		String badFormattingTestMapping = getInput("org/quiltmc/mapping/parser/BadlyFormattedTestMapping.quiltmapping");
		System.out.println("BadlyFormattedTestMapping.quiltmapping parsed output:");
		System.out.println(new QuiltMappingParser(badFormattingTestMapping, TYPES).parse());
	}

	private String getInput(String name) throws IOException {
		InputStream testMappingResource = QuiltMappingParserTest.class.getClassLoader().getResourceAsStream(name);
		assert testMappingResource != null;
		return new String(testMappingResource.readAllBytes());
	}
}
