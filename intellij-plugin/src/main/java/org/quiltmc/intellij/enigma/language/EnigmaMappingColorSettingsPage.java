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
			new AttributesDescriptor("Keywords", EnigmaMappingSyntaxHighlighter.KEYWORD)
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
		return "CLASS\n" +
				"\tCOMMENT lorem ipsum dolor sit amet consectetur adipiscing elit,\n" +
				"\tCOMMENT sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n" +
				"\tMETHOD\n" +
				"\tCLASS\n" +
				"\t\tCOMMENT foo\n" +
				"\t\tMETHOD\n" +
				"\t\t\tCOMMENT bar\n" +
				"\t\tFIELD\n" +
				"\tFIELD\n";
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
