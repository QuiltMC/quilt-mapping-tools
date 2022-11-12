package org.quiltmc.intellij.enigma.language.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import org.jetbrains.annotations.NotNull;
import org.quiltmc.intellij.enigma.language.EnigmaMappingFileType;
import org.quiltmc.intellij.enigma.language.EnigmaMappingLanguage;

public class EnigmaMappingFile extends PsiFileBase {
	public EnigmaMappingFile(@NotNull FileViewProvider viewProvider) {
		super(viewProvider, EnigmaMappingLanguage.INSTANCE);
	}

	@Override
	public @NotNull FileType getFileType() {
		return EnigmaMappingFileType.INSTANCE;
	}

	@Override
	public String toString() {
		return "Enigma Mapping";
	}
}
