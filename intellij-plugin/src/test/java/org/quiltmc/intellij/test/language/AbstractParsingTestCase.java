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

package org.quiltmc.intellij.test.language;

import com.intellij.lang.ParserDefinition;
import com.intellij.testFramework.ParsingTestCase;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractParsingTestCase extends ParsingTestCase {
	protected AbstractParsingTestCase(@NotNull String dataPath, @NotNull String fileExt, ParserDefinition @NotNull ... definitions) {
		super(dataPath, fileExt, definitions);
	}

	public void testParsingTestData() {
		doTest(true);
	}

	@Override
	protected String getTestDataPath() {
		return "src/test/testData";
	}

	@Override
	protected boolean skipSpaces() {
		return false;
	}

	@Override
	protected boolean includeRanges() {
		return true;
	}
}
