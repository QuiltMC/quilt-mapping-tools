package org.quiltmc.intellij.mapping.language.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.Couple;
import com.intellij.psi.PsiElement;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.quiltmc.intellij.mapping.language.psi.QuiltMappingTypes;

import java.util.List;

public class QuiltMappingPsiUtil {
	private static final List<IElementType> COMMENT_TYPES = List.of(QuiltMappingTypes.COMMENT, QuiltMappingTypes.BLOCK_COMMENT);

	public static PsiElement getLastSiblingOfType(@NotNull PsiElement start, IElementType expectedType, boolean forwards) {
		ASTNode node = start.getNode();
		ASTNode current = node;

		while (node != null) {
			IElementType elementType = node.getElementType();
			if (elementType == expectedType) {
				current = node;
			} else if (elementType != TokenType.WHITE_SPACE && (!COMMENT_TYPES.contains(elementType) || COMMENT_TYPES.contains(expectedType))) {
				break;
			}

			node = forwards ? node.getTreeNext() : node.getTreePrev();
		}

		return current.getPsi();
	}

	public static Couple<PsiElement> expandElement(PsiElement element) {
		IElementType type = element.getNode().getElementType();
		return Couple.of(getLastSiblingOfType(element, type, false), getLastSiblingOfType(element, type, true));
	}
}
