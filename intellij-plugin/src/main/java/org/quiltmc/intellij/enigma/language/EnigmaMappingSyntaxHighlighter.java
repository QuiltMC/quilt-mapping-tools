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

import java.util.HashMap;
import java.util.Map;

public class EnigmaMappingSyntaxHighlighter extends SyntaxHighlighterBase {
	public static final TextAttributesKey KEYWORD =
			TextAttributesKey.createTextAttributesKey("ENIGMA.KEYWORD", DefaultLanguageHighlighterColors.KEYWORD);

	private static final Map<IElementType, TextAttributesKey> ATTRIBUTES = new HashMap<>();

	static {
		fillMap(ATTRIBUTES, EnigmaMappingTokenSets.KEYWORDS, KEYWORD);
		fillMap(ATTRIBUTES, HighlighterColors.BAD_CHARACTER, TokenType.BAD_CHARACTER);
	}

	@Override
	public @NotNull Lexer getHighlightingLexer() {
		return new EnigmaMappingLexerAdapter();
	}

	@Override
	public TextAttributesKey @NotNull [] getTokenHighlights(IElementType tokenType) {
		return pack(ATTRIBUTES.get(tokenType));
	}
}
