package org.quiltmc.intellij.enigma.language;

import com.intellij.ide.navigationToolbar.StructureAwareNavBarModelExtension;
import com.intellij.lang.Language;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.intellij.enigma.language.psi.EnigmaMappingEntry;
import org.quiltmc.intellij.enigma.language.psi.EnigmaMappingPsiUtil;

import javax.swing.Icon;

public class EnigmaMappingStructureAwareNavbar extends StructureAwareNavBarModelExtension {
	@NotNull
	@Override
	protected Language getLanguage() {
		return EnigmaMappingLanguage.INSTANCE;
	}

	@Override
	public @Nullable String getPresentableText(Object object) {
		if (object instanceof EnigmaMappingEntry) {
			EnigmaMappingEntry entry = (EnigmaMappingEntry) object;
			return EnigmaMappingPsiUtil.toString(entry);
		}

		return null;
	}

	@Override
	public @Nullable Icon getIcon(Object object) {
		if (object instanceof EnigmaMappingEntry) {
			EnigmaMappingEntry entry = (EnigmaMappingEntry) object;
			return EnigmaMappingPsiUtil.getIcon(entry);
		}

		return null;
	}
}
