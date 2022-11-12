package org.quiltmc.intellij.enigma.language;

import com.intellij.lexer.FlexAdapter;

public class EnigmaMappingLexerAdapter extends FlexAdapter {
	public EnigmaMappingLexerAdapter() {
		super(new _EnigmaMappingLexer());
	}
}
