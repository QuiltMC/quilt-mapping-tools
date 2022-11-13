/*
 * Copyright 2022 QuiltMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
