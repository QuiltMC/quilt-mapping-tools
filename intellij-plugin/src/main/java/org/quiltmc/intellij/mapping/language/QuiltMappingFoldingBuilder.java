package org.quiltmc.intellij.mapping.language;

import com.intellij.lang.ASTNode;
import com.intellij.lang.folding.FoldingBuilderEx;
import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.util.Couple;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.intellij.mapping.language.psi.QuiltMappingTypes;
import org.quiltmc.intellij.mapping.language.psi.impl.QuiltMappingPsiUtil;

import java.util.ArrayList;
import java.util.List;

public class QuiltMappingFoldingBuilder extends FoldingBuilderEx implements DumbAware {
	@Override
	public FoldingDescriptor @NotNull [] buildFoldRegions(@NotNull PsiElement root, @NotNull Document document, boolean quick) {
		List<FoldingDescriptor> descriptors = new ArrayList<>();
		collectDescriptors(root.getNode(), document, descriptors);
		return descriptors.toArray(FoldingDescriptor.EMPTY);
	}

	private static void collectDescriptors(ASTNode node, Document document, List<FoldingDescriptor> descriptors) {
		IElementType type = node.getElementType();

		if (type == QuiltMappingTypes.COMMENT) {
			Couple<PsiElement> expanded = QuiltMappingPsiUtil.expandElement(node.getPsi());
			int start = expanded.getFirst().getTextRange().getStartOffset();
			int end = expanded.getSecond().getTextRange().getEndOffset();

			if (document.getLineNumber(start) != document.getLineNumber(end)) {
				descriptors.add(new FoldingDescriptor(node, new TextRange(start, end)));
			}
		} else if (type == QuiltMappingTypes.BLOCK_COMMENT) {
			descriptors.add(new FoldingDescriptor(node, node.getTextRange()));
		} else if (type == QuiltMappingTypes.OBJECT || type == QuiltMappingTypes.ARRAY || type == QuiltMappingTypes.DOUBLE_QUOTED_STRING || type == QuiltMappingTypes.SINGLE_QUOTED_STRING) {
			TextRange range = node.getTextRange();
			if (document.getLineNumber(range.getStartOffset()) < document.getLineNumber(range.getEndOffset())) {
				descriptors.add(new FoldingDescriptor(node, range));
			}
		}

		for (ASTNode child : node.getChildren(null)) {
			collectDescriptors(child, document, descriptors);
		}
	}

	@Override
	public @Nullable String getPlaceholderText(@NotNull ASTNode node) {
		IElementType type = node.getElementType();

		if (type == QuiltMappingTypes.BLOCK_COMMENT) {
			return "/*...*/";
		} else if (type == QuiltMappingTypes.COMMENT) {
			return "//...";
		} else if (type == QuiltMappingTypes.OBJECT) {
			return "{...}";
		} else if (type == QuiltMappingTypes.ARRAY) {
			return "[...]";
		} else if (type == QuiltMappingTypes.DOUBLE_QUOTED_STRING) {
			return "\"...\"";
		} else if (type == QuiltMappingTypes.SINGLE_QUOTED_STRING) {
			return "'...'";
		}

		return "...";
	}

	@Override
	public boolean isCollapsedByDefault(@NotNull ASTNode node) {
		return false;
	}
}
