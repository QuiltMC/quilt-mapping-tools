package org.quiltmc.intellij.enigma.language;

import com.intellij.lang.documentation.AbstractDocumentationProvider;
import com.intellij.lang.documentation.DocumentationMarkup;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.intellij.enigma.language.psi.EnigmaMappingEntry;
import org.quiltmc.intellij.enigma.language.psi.EnigmaMappingPsiUtil;

public class EnigmaMappingDocumentationProvider extends AbstractDocumentationProvider {
	@Override
	public @Nullable @Nls String generateDoc(PsiElement element, @Nullable PsiElement originalElement) {
		if (element instanceof EnigmaMappingEntry) {
			EnigmaMappingEntry entry = (EnigmaMappingEntry) element;
			return renderDoc(entry);
		}

		return null;
	}

	@NotNull
	private static String renderDoc(EnigmaMappingEntry entry) {
		return DocumentationMarkup.DEFINITION_START +
				EnigmaMappingPsiUtil.toString(entry) +
				DocumentationMarkup.DEFINITION_END +
				DocumentationMarkup.CONTENT_START +
				EnigmaMappingUtil.getComment(entry) +
				DocumentationMarkup.CONTENT_END;
	}
}
