package org.quiltmc.intellij.mapping.language;

import org.quiltmc.intellij.test.language.AbstractParsingTestCase;

public class QuiltMappingParsingTest extends AbstractParsingTestCase {
	public QuiltMappingParsingTest() {
		super("quiltmapping", "quiltmapping", new QuiltMappingParserDefinition());
	}
}
