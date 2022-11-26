package org.quiltmc.intellij.enigma.language.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.quiltmc.intellij.enigma.language.psi.EnigmaMappingComment;
import org.quiltmc.intellij.enigma.language.psi.EnigmaMappingTypes;
import org.quiltmc.intellij.enigma.language.psi.IEnigmaMappingComment;

public class EnigmaMappingCommentImplMixin extends ASTWrapperPsiElement implements EnigmaMappingComment, IEnigmaMappingComment {
	public EnigmaMappingCommentImplMixin(@NotNull ASTNode node) {
		super(node);
	}

	@Override
	public String getContent() {
		PsiElement text = findChildByType(EnigmaMappingTypes.COMMENT_TEXT);
		if (text != null) {
			return text.getText().trim();
		}

		return "";
	}
}
