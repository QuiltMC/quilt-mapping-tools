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

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.quiltmc.mapping.api.entry.info.ArgEntry;
import org.quiltmc.mapping.api.entry.info.CommentEntry;
import org.quiltmc.mapping.api.entry.unpick.UnpickEntry;
import org.quiltmc.mapping.impl.serialization.TabSeparatedContent;

public class SerializationTest {
	@Test
	public void test() {
		ArgEntry arg = ArgEntry.arg(0, List.of("name"), List.of(
			CommentEntry.comment("This is a comment"),
			UnpickEntry.unpick("group", null)
		));

		TabSeparatedContent written = ArgEntry.ARG_MAPPING_TYPE.parser().serialize(arg);
		ArgEntry read = ArgEntry.ARG_MAPPING_TYPE.parser().deserialize(written);

		Assertions.assertEquals(arg, read, "Serialized and Deserialized match");
	}
}
