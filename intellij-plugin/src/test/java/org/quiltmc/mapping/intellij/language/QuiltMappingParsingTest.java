package org.quiltmc.mapping.intellij.language;

import com.intellij.testFramework.ParsingTestCase;

public class QuiltMappingParsingTest extends ParsingTestCase {
	public QuiltMappingParsingTest() {
		super("", "quiltmapping", new QuiltMappingParserDefinition());
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
