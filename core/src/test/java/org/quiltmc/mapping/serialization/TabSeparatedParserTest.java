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

package org.quiltmc.mapping.serialization;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.quiltmc.mapping.api.entry.MappingType;
import org.quiltmc.mapping.api.entry.info.ArgEntry;
import org.quiltmc.mapping.api.entry.info.CommentEntry;
import org.quiltmc.mapping.api.entry.info.ReturnEntry;
import org.quiltmc.mapping.api.entry.naming.ClassEntry;
import org.quiltmc.mapping.api.entry.naming.FieldEntry;
import org.quiltmc.mapping.api.entry.naming.MethodEntry;
import org.quiltmc.mapping.api.entry.transitive.TransitiveEntry;
import org.quiltmc.mapping.api.entry.unpick.UnpickEntry;
import org.quiltmc.mapping.api.file.QuiltMappingFile;
import org.quiltmc.mapping.api.parse.Parser;
import org.quiltmc.mapping.impl.serialization.TabSeparatedContent;

public class TabSeparatedParserTest {

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
	public void basicParseTabsOnlyAndRemake() throws IOException {
		String input = getInput("org/quiltmc/mapping/parser/TestMappingTabs.qm");

		TabSeparatedContent content = TabSeparatedContent.parse(input);

		Assertions.assertEquals(input.stripTrailing(), content.toString(-1).stripTrailing(), "Input matches parsed output");
	}

	@Test
	public void parseToFileAndRemake() throws IOException {
		String input = getInput("org/quiltmc/mapping/parser/TestMappingTabs.qm");

		QuiltMappingFile file = QuiltMappingFile.PARSER.deserialize(TabSeparatedContent.parse(input));
		TabSeparatedContent content = QuiltMappingFile.PARSER.serialize(file);

		Assertions.assertEquals(input.stripTrailing(), content.toString(-1).stripTrailing(), "Input matches parsed output");
	}

	@Test
	public void create() {
		Parser<ArgEntry, TabSeparatedContent> argParser = ArgEntry.ARG_MAPPING_TYPE.parser();

		ArgEntry arg = ArgEntry.arg(0, List.of("name"), List.of(CommentEntry.comment("Test comment!")));
		TabSeparatedContent serialized = argParser.serialize(arg);
		ArgEntry parsed = argParser.deserialize(serialized);
		Assertions.assertEquals(arg, parsed, "Serialized and reparsed correctly");

		arg = ArgEntry.arg(1, List.of(), List.of(CommentEntry.comment("Test comment2!")));
		serialized = argParser.serialize(arg);
		parsed = argParser.deserialize(serialized);
		Assertions.assertEquals(arg, parsed, "Serialized and reparsed correctly part 2");
	}

	private String getInput(String name) throws IOException {
		InputStream testMappingResource = TabSeparatedParserTest.class.getClassLoader().getResourceAsStream(name);
		assert testMappingResource != null;
		return new String(testMappingResource.readAllBytes());
	}
}
