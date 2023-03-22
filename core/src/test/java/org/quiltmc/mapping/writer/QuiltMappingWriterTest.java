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

package org.quiltmc.mapping.writer;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
import org.quiltmc.mapping.api.serialization.ReadableParser;
import org.quiltmc.mapping.api.serialization.Serializer;
import org.quiltmc.mapping.api.serialization.WritableParser;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QuiltMappingWriterTest {
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
		String input = getInput("org/quiltmc/mapping/parser/TestMapping.quiltmapping");
		JsonObject json = JsonParser.parseString(input).getAsJsonObject();

		Serializer<ClassEntry> serializer = ClassEntry.CLASS_MAPPING_TYPE.serializer();
		ClassEntry clazz = serializer.read(ReadableParser.JSON, json.get("classes").getAsJsonArray().get(0));


		JsonElement written = serializer.write(clazz, WritableParser.JSON);
		System.out.println(written);
		assertEquals(clazz, serializer.read(ReadableParser.JSON, written), "Parses correctly");
	}


	private String getInput(String name) throws IOException {
		InputStream testMappingResource = QuiltMappingWriterTest.class.getClassLoader().getResourceAsStream(name);
		assert testMappingResource != null;
		return new String(testMappingResource.readAllBytes());
	}
}
