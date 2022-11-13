package org.quiltmc.intellij.enigma.language;

import org.quiltmc.intellij.test.language.AbstractParsingTestCase;

public class EnigmaMappingParsingTest extends AbstractParsingTestCase {
	public EnigmaMappingParsingTest() {
		super("enigma", "mapping", new EnigmaMappingParserDefinition());
	}
}
