package org.quiltmc.intellij.enigma.language;

import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.options.colors.AttributesDescriptor;
import com.intellij.openapi.options.colors.ColorDescriptor;
import com.intellij.openapi.options.colors.ColorSettingsPage;
import com.intellij.openapi.util.NlsContexts;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Map;

public class EnigmaMappingColorSettingsPage implements ColorSettingsPage {
	private static final AttributesDescriptor[] DESCRIPTORS = new AttributesDescriptor[]{
			new AttributesDescriptor("Keywords", EnigmaMappingSyntaxHighlighter.KEYWORD),
			// new AttributesDescriptor("Class names", EnigmaMappingSyntaxHighlighter.CLASS_NAME),
			// new AttributesDescriptor("Class references", EnigmaMappingSyntaxHighlighter.CLASS_REFERENCE),
			new AttributesDescriptor("Identifiers", EnigmaMappingSyntaxHighlighter.IDENTIFIER),
			new AttributesDescriptor("Numbers", EnigmaMappingSyntaxHighlighter.NUMBER),
			new AttributesDescriptor("Comments", EnigmaMappingSyntaxHighlighter.DOC_COMMENT)
	};

	@Override
	public @Nullable Icon getIcon() {
		return EnigmaMappingFileType.ICON;
	}

	@Override
	public @NotNull SyntaxHighlighter getHighlighter() {
		return new EnigmaMappingSyntaxHighlighter();
	}

	@Override
	public @NonNls @NotNull String getDemoText() {
		return "CLASS a/a com/example/Foo\n" +
				"\tCOMMENT lorem ipsum dolor sit amet consectetur adipiscing elit,\n" +
				"\tCOMMENT sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n" +
				"\tFIELD a x I\n" +
				"\tFIELD b INSTANCE La/a;\n" +
				"\tMETHOD a foo ()V\n" +
				"\tCLASS a/a$b com/example/Foo$Bar\n" +
				"\t\tCOMMENT foo\n" +
				"\t\tFIELD a i [[I\n" +
				"\t\tMETHOD b bar (I)V\n" +
				"\t\t\tCOMMENT bar\n" +
				"\t\t\tARG 1 count\n" +
				"\t\t\t\tCOMMENT the count\n";
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
		return EnigmaMappingFileType.NAME;
	}
}
