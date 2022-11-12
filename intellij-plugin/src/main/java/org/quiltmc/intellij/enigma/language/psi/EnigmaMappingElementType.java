package org.quiltmc.intellij.enigma.language.psi;

import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.quiltmc.intellij.enigma.language.EnigmaMappingLanguage;

public class EnigmaMappingElementType extends IElementType {
	public EnigmaMappingElementType(@NonNls @NotNull String debugName) {
		super(debugName, EnigmaMappingLanguage.INSTANCE);
	}
}
