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

package org.quiltmc.intellij.enigma.language;

import com.intellij.lang.ASTNode;
import com.intellij.lang.folding.FoldingBuilderEx;
import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.intellij.enigma.language.psi.EnigmaMappingArg;
import org.quiltmc.intellij.enigma.language.psi.EnigmaMappingClazz;
import org.quiltmc.intellij.enigma.language.psi.EnigmaMappingComment;
import org.quiltmc.intellij.enigma.language.psi.EnigmaMappingEntry;
import org.quiltmc.intellij.enigma.language.psi.EnigmaMappingField;
import org.quiltmc.intellij.enigma.language.psi.EnigmaMappingMethod;
import org.quiltmc.intellij.enigma.language.psi.EnigmaMappingPsiUtil;

import java.util.ArrayList;
import java.util.List;

public class EnigmaMappingFoldingBuilder extends FoldingBuilderEx implements DumbAware {
	@Override
	public FoldingDescriptor @NotNull [] buildFoldRegions(@NotNull PsiElement root, @NotNull Document document, boolean quick) {
		List<FoldingDescriptor> descriptors = new ArrayList<>();

		EnigmaMappingClazz rootClazz = PsiTreeUtil.getChildOfType(root, EnigmaMappingClazz.class);
		recursiveDescriptors(rootClazz, document, descriptors);

		return descriptors.toArray(FoldingDescriptor.EMPTY);
	}

	private void recursiveDescriptors(@Nullable EnigmaMappingClazz clazz, @NotNull Document document, List<FoldingDescriptor> descriptors) {
		if (clazz == null) return;

		descriptors.add(new FoldingDescriptor(clazz, clazz.getNode().getTextRange()));
		addCommentsDescriptor(clazz.getCommentList(), document, descriptors);

		for (EnigmaMappingEntry entry : clazz.getEntryList()) {
			if (entry instanceof EnigmaMappingField) {
				EnigmaMappingField field = (EnigmaMappingField) entry;
				addCommentsDescriptor(field.getCommentList(), document, descriptors);
			} else if (entry instanceof EnigmaMappingMethod) {
				EnigmaMappingMethod method = (EnigmaMappingMethod) entry;
				addCommentsDescriptor(method.getCommentList(), document, descriptors);

				List<EnigmaMappingArg> args = method.getArgList();
				if (args.size() > 0) {
					addDescriptor(method, args.get(args.size() - 1), document, descriptors);
					addDescriptor(args.get(0), args.get(args.size() - 1), document, descriptors);
					for (EnigmaMappingArg arg : args) {
						addCommentsDescriptor(arg.getCommentList(), document, descriptors);
					}
				}
			} else if (entry instanceof EnigmaMappingClazz) {
				recursiveDescriptors((EnigmaMappingClazz) entry, document, descriptors);
			}
		}
	}

	private void addCommentsDescriptor(List<EnigmaMappingComment> comments, @NotNull Document document, List<FoldingDescriptor> descriptors) {
		if (comments.size() > 1) {
			addDescriptor(comments.get(0), comments.get(comments.size() - 1), document, descriptors);
		}
	}

	private void addDescriptor(PsiElement first, PsiElement last, @NotNull Document document, List<FoldingDescriptor> descriptors) {
		int start = first.getTextRange().getStartOffset();
		int end = last.getTextRange().getEndOffset();
		if (document.getLineNumber(start) != document.getLineNumber(end)) {
			descriptors.add(new FoldingDescriptor(first, new TextRange(start, end)));
		}
	}

	@Override
	public @Nullable String getPlaceholderText(@NotNull ASTNode node) {
		return EnigmaMappingPsiUtil.toString(node) + " ...";
	}

	@Override
	public boolean isCollapsedByDefault(@NotNull ASTNode node) {
		return false;
	}
}
