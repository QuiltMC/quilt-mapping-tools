package org.quiltmc.mapping.intellij.language;

import com.intellij.lexer.LayeredLexer;
import com.intellij.lexer.Lexer;
import com.intellij.lexer.StringLiteralLexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.StringEscapesTokenTypes;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.quiltmc.mapping.intellij.language.psi.QuiltMappingTypes;

import java.util.HashMap;
import java.util.Map;

public class QuiltMappingSyntaxHighlighter extends SyntaxHighlighterBase {
	public static final TextAttributesKey BRACES =
			TextAttributesKey.createTextAttributesKey("QUILT_MAPPING_BRACES", DefaultLanguageHighlighterColors.BRACES);
	public static final TextAttributesKey BRACKETS =
			TextAttributesKey.createTextAttributesKey("QUILT_MAPPING_BRACKETS", DefaultLanguageHighlighterColors.BRACKETS);

	public static final TextAttributesKey BLOCK_COMMENT =
			TextAttributesKey.createTextAttributesKey("QUILT_MAPPING_BLOCK_COMMENT", DefaultLanguageHighlighterColors.BLOCK_COMMENT);
	public static final TextAttributesKey LINE_COMMENT =
			TextAttributesKey.createTextAttributesKey("QUILT_MAPPING_LINE_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT);

	public static final TextAttributesKey COLON =
			TextAttributesKey.createTextAttributesKey("QUILT_MAPPING_COLON", DefaultLanguageHighlighterColors.SEMICOLON);
	public static final TextAttributesKey COMMA =
			TextAttributesKey.createTextAttributesKey("QUILT_MAPPING_COMMA", DefaultLanguageHighlighterColors.COMMA);
	public static final TextAttributesKey KEYWORD =
			TextAttributesKey.createTextAttributesKey("QUILT_MAPPING_KEYWORD", DefaultLanguageHighlighterColors.KEYWORD);
	public static final TextAttributesKey NUMBER =
			TextAttributesKey.createTextAttributesKey("QUILT_MAPPING_NUMBER", DefaultLanguageHighlighterColors.NUMBER);
	public static final TextAttributesKey STRING =
			TextAttributesKey.createTextAttributesKey("QUILT_MAPPING_STRING", DefaultLanguageHighlighterColors.STRING);

	public static final TextAttributesKey PROPERTY_NAME =
			TextAttributesKey.createTextAttributesKey("QUILT_MAPPING_PROPERTY_NAME", DefaultLanguageHighlighterColors.INSTANCE_FIELD);

	public static final TextAttributesKey VALID_ESCAPE =
			TextAttributesKey.createTextAttributesKey("QUILT_MAPPING_VALID_ESCAPE", DefaultLanguageHighlighterColors.VALID_STRING_ESCAPE);
	public static final TextAttributesKey INVALID_ESCAPE =
			TextAttributesKey.createTextAttributesKey("QUILT_MAPPING_INVALID_ESCAPE", DefaultLanguageHighlighterColors.INVALID_STRING_ESCAPE);

	private static final Map<IElementType, TextAttributesKey> ATTRIBUTES = new HashMap<>();

	static {
		fillMap(ATTRIBUTES, BRACES, QuiltMappingTypes.LBRACE, QuiltMappingTypes.RBRACE);
		fillMap(ATTRIBUTES, BRACKETS, QuiltMappingTypes.LBRACKET, QuiltMappingTypes.RBRACKET);
		fillMap(ATTRIBUTES, BLOCK_COMMENT, QuiltMappingTypes.BLOCK_COMMENT);
		fillMap(ATTRIBUTES, LINE_COMMENT, QuiltMappingTypes.COMMENT);
		fillMap(ATTRIBUTES, COLON, QuiltMappingTypes.COLON);
		fillMap(ATTRIBUTES, COMMA, QuiltMappingTypes.COMMA);
		fillMap(ATTRIBUTES, KEYWORD, QuiltMappingTypes.TRUE, QuiltMappingTypes.FALSE, QuiltMappingTypes.NULL);
		fillMap(ATTRIBUTES, NUMBER, QuiltMappingTypes.NUMBER);
		fillMap(ATTRIBUTES, STRING, QuiltMappingTypes.SINGLE_QUOTED_STRING, QuiltMappingTypes.DOUBLE_QUOTED_STRING);
		fillMap(ATTRIBUTES, PROPERTY_NAME, QuiltMappingTypes.IDENTIFIER);

		fillMap(ATTRIBUTES, VALID_ESCAPE, StringEscapesTokenTypes.VALID_STRING_ESCAPE_TOKEN);
		fillMap(ATTRIBUTES, INVALID_ESCAPE, StringEscapesTokenTypes.INVALID_CHARACTER_ESCAPE_TOKEN, StringEscapesTokenTypes.INVALID_UNICODE_ESCAPE_TOKEN);
	}

	@Override
	public @NotNull Lexer getHighlightingLexer() {
		LayeredLexer lexer = new LayeredLexer(new QuiltMappingLexerAdapter());
		lexer.registerSelfStoppingLayer(new StringEscapesLexer('\"', QuiltMappingTypes.DOUBLE_QUOTED_STRING), new IElementType[]{QuiltMappingTypes.DOUBLE_QUOTED_STRING}, IElementType.EMPTY_ARRAY);
		lexer.registerSelfStoppingLayer(new StringEscapesLexer('\'', QuiltMappingTypes.SINGLE_QUOTED_STRING), new IElementType[]{QuiltMappingTypes.SINGLE_QUOTED_STRING}, IElementType.EMPTY_ARRAY);
		return lexer;
	}

	@Override
	public TextAttributesKey @NotNull [] getTokenHighlights(IElementType tokenType) {
		return pack(ATTRIBUTES.get(tokenType));
	}

	private static class StringEscapesLexer extends StringLiteralLexer {
		private static final String ESCAPES;

		public StringEscapesLexer(char quoteChar, IElementType originalLiteralToken) {
			super(quoteChar, originalLiteralToken, true, ESCAPES, false, true);
		}

		@Override
		protected @NotNull IElementType handleSingleSlashEscapeSequence() {
			return myOriginalLiteralToken;
		}

		@Override
		protected boolean shouldAllowSlashZero() {
			return true;
		}

		static {
			StringBuilder builder = new StringBuilder("/");

			for (char c = '\1'; c < '\255'; c++) {
				if (!Character.isDigit(c) && c != 'x' && c != 'u' && c != '\n' && c != '\r') {
					builder.append(c);
				}
			}

			ESCAPES = builder.toString();
		}
	}
}
