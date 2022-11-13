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
