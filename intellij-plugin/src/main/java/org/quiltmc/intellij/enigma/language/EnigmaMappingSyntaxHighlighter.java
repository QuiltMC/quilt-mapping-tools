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

package org.quiltmc.intellij.enigma.language;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.quiltmc.intellij.enigma.language.psi.EnigmaMappingTokenSets;
import org.quiltmc.intellij.enigma.language.psi.EnigmaMappingTypes;

import java.util.HashMap;
import java.util.Map;

public class EnigmaMappingSyntaxHighlighter extends SyntaxHighlighterBase {
	public static final TextAttributesKey KEYWORD =
			TextAttributesKey.createTextAttributesKey("ENIGMA.KEYWORD", DefaultLanguageHighlighterColors.KEYWORD);
	public static final TextAttributesKey CLASS_NAME =
			TextAttributesKey.createTextAttributesKey("ENIGMA.CLASS_NAME", DefaultLanguageHighlighterColors.CLASS_NAME);
	public static final TextAttributesKey IDENTIFIER =
			TextAttributesKey.createTextAttributesKey("ENIGMA.IDENTIFIER", DefaultLanguageHighlighterColors.IDENTIFIER);
	public static final TextAttributesKey NUMBER =
			TextAttributesKey.createTextAttributesKey("ENIGMA.NUMBER", DefaultLanguageHighlighterColors.NUMBER);
	public static final TextAttributesKey DOC_COMMENT =
			TextAttributesKey.createTextAttributesKey("ENIGMA.DOC_COMMENT", DefaultLanguageHighlighterColors.DOC_COMMENT);

	private static final Map<IElementType, TextAttributesKey> ATTRIBUTES = new HashMap<>();

	static {
		fillMap(ATTRIBUTES, EnigmaMappingTokenSets.KEYWORDS, KEYWORD);
		fillMap(ATTRIBUTES, CLASS_NAME, EnigmaMappingTypes.CLASS_NAME);
		fillMap(ATTRIBUTES, IDENTIFIER, EnigmaMappingTypes.IDENTIFIER);
		fillMap(ATTRIBUTES, NUMBER, EnigmaMappingTypes.NUMBER);
		fillMap(ATTRIBUTES, DOC_COMMENT, EnigmaMappingTypes.COMMENT_TEXT);
		fillMap(ATTRIBUTES, HighlighterColors.BAD_CHARACTER, TokenType.BAD_CHARACTER);
	}

	@Override
	public @NotNull Lexer getHighlightingLexer() {
		// TODO: Add a layer to convert class name/reference tokens to elements
		return new EnigmaMappingLexerAdapter();
	}

	@Override
	public TextAttributesKey @NotNull [] getTokenHighlights(IElementType tokenType) {
		return pack(ATTRIBUTES.get(tokenType));
	}
}
