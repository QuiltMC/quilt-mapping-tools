package org.quiltmc.intellij.mapping.language;

import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.options.colors.AttributesDescriptor;
import com.intellij.openapi.options.colors.ColorDescriptor;
import com.intellij.openapi.options.colors.ColorSettingsPage;
import com.intellij.openapi.util.NlsContexts;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.intellij.mapping.QuiltMappingIcons;

import javax.swing.Icon;
import java.util.Map;

public class QuiltMappingColorSettingsPage implements ColorSettingsPage {
	private static final AttributesDescriptor[] DESCRIPTORS = new AttributesDescriptor[] {
			new AttributesDescriptor("Braces", QuiltMappingSyntaxHighlighter.BRACES),
			new AttributesDescriptor("Brackets", QuiltMappingSyntaxHighlighter.BRACKETS),
			new AttributesDescriptor("Comment//Block comment", QuiltMappingSyntaxHighlighter.BLOCK_COMMENT),
			new AttributesDescriptor("Comment//Line comment", QuiltMappingSyntaxHighlighter.LINE_COMMENT),
			new AttributesDescriptor("Colon", QuiltMappingSyntaxHighlighter.COLON),
			new AttributesDescriptor("Comma", QuiltMappingSyntaxHighlighter.COMMA),
			new AttributesDescriptor("Keyword", QuiltMappingSyntaxHighlighter.KEYWORD),
			new AttributesDescriptor("Number", QuiltMappingSyntaxHighlighter.NUMBER),
			new AttributesDescriptor("String", QuiltMappingSyntaxHighlighter.STRING),
			new AttributesDescriptor("Property name", QuiltMappingSyntaxHighlighter.PROPERTY_NAME),
			new AttributesDescriptor("Valid escape sequence", QuiltMappingSyntaxHighlighter.VALID_ESCAPE),
			new AttributesDescriptor("Invalid escape sequence", QuiltMappingSyntaxHighlighter.INVALID_ESCAPE),
	};

	@Override
	public @Nullable Icon getIcon() {
		return QuiltMappingIcons.FILE;
	}

	@Override
	public @NotNull SyntaxHighlighter getHighlighter() {
		return new QuiltMappingSyntaxHighlighter();
	}

	@Override
	public @NonNls @NotNull String getDemoText() {
		return "{\n" +
				"  // Its format is json5 based\n" +
				"  keywords: [true, false, null],\n" +
				"  numbers: [\n" +
				"    /*\n" +
				"     * Leading and trailing decimal points,\n" +
				"     * hexadecimal, and exponents are supported\n" +
				"     */\n" +
				"    1.23,\n" +
				"    .456,\n" +
				"    -789.,\n" +
				"    0xC0FFEE,\n" +
				"    1.6605e-24, // Trailing commas are also allowed\n" +
				"  ],\n" +
				"  \"foo\": 'Bar',\n" +
				"  bad_value: [ property: 'value'; ],\n" +
				"  'multiline': \"No need for \\n \\\n" +
				"    Just end the line with a single \\\n" +
				"    backslash\",\n" +
				"}\n";
	}

	@Override
	public @Nullable Map<String, TextAttributesKey> getAdditionalHighlightingTagToDescriptorMap() {
		return null;
	}

	@Override
	public AttributesDescriptor @NotNull [] getAttributeDescriptors() {
		return DESCRIPTORS;
	}

	@Override
	public ColorDescriptor @NotNull [] getColorDescriptors() {
		return ColorDescriptor.EMPTY_ARRAY;
	}

	@Override
	public @NotNull @NlsContexts.ConfigurableName String getDisplayName() {
		return "Quilt Mapping";
	}
}
