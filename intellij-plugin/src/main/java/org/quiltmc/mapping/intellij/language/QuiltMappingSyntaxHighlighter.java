package org.quiltmc.mapping.intellij.language;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.quiltmc.mapping.intellij.language.psi.QuiltMappingTypes;

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

	private static final TextAttributesKey[] BRACE_KEYS = new TextAttributesKey[]{BRACES};
	private static final TextAttributesKey[] BRACKET_KEYS = new TextAttributesKey[]{BRACKETS};
	private static final TextAttributesKey[] BLOCK_COMMENT_KEYS = new TextAttributesKey[]{BLOCK_COMMENT};
	private static final TextAttributesKey[] LINE_COMMENT_KEYS = new TextAttributesKey[]{LINE_COMMENT};
	private static final TextAttributesKey[] COLON_KEYS = new TextAttributesKey[]{COLON};
	private static final TextAttributesKey[] COMMA_KEYS = new TextAttributesKey[]{COMMA};
	private static final TextAttributesKey[] KEYWORD_KEYS = new TextAttributesKey[]{KEYWORD};
	private static final TextAttributesKey[] NUMBER_KEYS = new TextAttributesKey[]{NUMBER};
	private static final TextAttributesKey[] STRING_KEYS = new TextAttributesKey[]{STRING};
	private static final TextAttributesKey[] PROPERTY_NAME_KEYS = new TextAttributesKey[]{PROPERTY_NAME};

	@Override
	public @NotNull Lexer getHighlightingLexer() {
		return new QuiltMappingLexerAdapter();
	}

	@Override
	public TextAttributesKey @NotNull [] getTokenHighlights(IElementType tokenType) {
		if (tokenType.equals(QuiltMappingTypes.LBRACE) || tokenType.equals(QuiltMappingTypes.RBRACE)) {
			return BRACE_KEYS;
		} else if (tokenType.equals(QuiltMappingTypes.LBRACKET) || tokenType.equals(QuiltMappingTypes.RBRACKET)) {
			return BRACKET_KEYS;
		} else if (tokenType.equals(QuiltMappingTypes.BLOCK_COMMENT)) {
			return BLOCK_COMMENT_KEYS;
		} else if (tokenType.equals(QuiltMappingTypes.COMMENT)) {
			return LINE_COMMENT_KEYS;
		} else if (tokenType.equals(QuiltMappingTypes.COLON)) {
			return COLON_KEYS;
		} else if (tokenType.equals(QuiltMappingTypes.COMMA)) {
			return COMMA_KEYS;
		} else if (tokenType.equals(QuiltMappingTypes.TRUE) || tokenType.equals(QuiltMappingTypes.FALSE) || tokenType.equals(QuiltMappingTypes.NULL)) {
			return KEYWORD_KEYS;
		} else if (tokenType.equals(QuiltMappingTypes.NUMBER)) {
			return NUMBER_KEYS;
		} else if (tokenType.equals(QuiltMappingTypes.SINGLE_QUOTED_STRING) || tokenType.equals(QuiltMappingTypes.DOUBLE_QUOTED_STRING)) {
			return STRING_KEYS;
		} else if (tokenType.equals(QuiltMappingTypes.IDENTIFIER)) {
			return PROPERTY_NAME_KEYS;
		}

		return TextAttributesKey.EMPTY_ARRAY;
	}
}
