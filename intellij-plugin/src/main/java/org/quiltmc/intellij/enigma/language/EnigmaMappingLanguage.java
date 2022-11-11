package org.quiltmc.intellij.enigma.language;

import com.intellij.lang.Language;

public class EnigmaMappingLanguage extends Language {
	public static final EnigmaMappingLanguage INSTANCE = new EnigmaMappingLanguage();

	protected EnigmaMappingLanguage() {
		super("EnigmaMapping");
	}
}
