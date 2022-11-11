package org.quiltmc.intellij.enigma.language;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.util.NlsContexts;
import com.intellij.openapi.util.NlsSafe;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;

public class EnigmaMappingFileType extends LanguageFileType {
	public static final EnigmaMappingFileType INSTANCE = new EnigmaMappingFileType();

	protected EnigmaMappingFileType() {
		super(EnigmaMappingLanguage.INSTANCE);
	}

	@Override
	public @NonNls @NotNull String getName() {
		return "Enigma Mappings";
	}

	@Override
	public @NlsContexts.Label @NotNull String getDescription() {
		return "Mappings file (Enigma format)";
	}

	@Override
	public @NlsSafe @NotNull String getDefaultExtension() {
		return "mapping";
	}

	@Override
	public @Nullable Icon getIcon() {
		return AllIcons.FileTypes.Text;
	}
}
