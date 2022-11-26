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
import org.quiltmc.mapping.parser.exception.InvalidSyntaxException;

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
		InputStream testMappingResource = QuiltMappingParserTest.class.getClassLoader().getResourceAsStream("org/quiltmc/mapping/parser/TestMapping.quiltmapping");
		assert testMappingResource != null;
		String testMappingInput = new String(testMappingResource.readAllBytes());

		System.out.println(testMappingInput);
		System.out.println(new QuiltMappingParser(testMappingInput, TYPES).parse());

		// test: test mapping file with negative argument index
		// this test should throw an exception
		InputStream negativeArgIndexTestResource = QuiltMappingParserTest.class.getClassLoader().getResourceAsStream("org/quiltmc/mapping/parser/fail_cases/NegativeArgumentIndexTestMapping.quiltmapping");
		assert negativeArgIndexTestResource != null;
		String negativeArgIndexTest = new String(negativeArgIndexTestResource.readAllBytes());
		try {
			new QuiltMappingParser(negativeArgIndexTest, TYPES).parse();
			Assertions.fail("expected a parsing exception in negative arg index test");
		} catch (Exception ignored) {
			// this is what we want!
		}

		// test: test mapping file with negative argument index
		// this test should throw an exception
		InputStream nonexistentExtensionTestResource = QuiltMappingParserTest.class.getClassLoader().getResourceAsStream("org/quiltmc/mapping/parser/fail_cases/NonexistentExtensionTestMapping.quiltmapping");
		assert nonexistentExtensionTestResource != null;
		String nonexistentExtensionInput = new String(nonexistentExtensionTestResource.readAllBytes());
		try {
			new QuiltMappingParser(nonexistentExtensionInput, TYPES).parse();
			Assertions.fail("expected a parsing exception in nonexistent extension test");
		} catch (Exception ignored) {
			// this is what we want!
		}

		// test: test mapping file with extra nonsense in it
		// this test should pass and ignore the all the nonsense
		// it should only print warnings
		InputStream badTestMappingResource = QuiltMappingParserTest.class.getClassLoader().getResourceAsStream("org/quiltmc/mapping/parser/BadlyFormattedTestMapping.quiltmapping");
		assert badTestMappingResource != null;
		String badTestMappingInput = new String(badTestMappingResource.readAllBytes());

		System.out.println(badTestMappingInput);
		System.out.println(new QuiltMappingParser(badTestMappingInput, TYPES).parse());
	}
}
