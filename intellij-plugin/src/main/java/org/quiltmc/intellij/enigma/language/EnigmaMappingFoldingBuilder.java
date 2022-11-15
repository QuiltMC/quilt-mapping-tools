package org.quiltmc.intellij.enigma.language;

import com.intellij.lang.ASTNode;
import com.intellij.lang.folding.FoldingBuilderEx;
import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.intellij.enigma.language.psi.*;

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
		IElementType type = node.getElementType();
		if (type == EnigmaMappingTypes.CLAZZ) {
			EnigmaMappingClazz clazz = node.getPsi(EnigmaMappingClazz.class);
			List<EnigmaMappingBinaryName> names = clazz.getBinaryNameList();
			if (!names.isEmpty()) {
				EnigmaMappingBinaryName name = names.get(names.size() - 1);
				return "CLASS " + name.getText() + " ...";
			}

			return "CLASS ...";
		} else if (type == EnigmaMappingTypes.FIELD) {
			EnigmaMappingField field = node.getPsi(EnigmaMappingField.class);
			List<EnigmaMappingIdentifierName> names = field.getIdentifierNameList();
			if (!names.isEmpty()) {
				EnigmaMappingIdentifierName name = names.get(names.size() - 1);
				EnigmaMappingFieldDescriptor desc = field.getFieldDescriptor();
				return "FIELD " + name.getText() + " " + (desc != null ? desc.getText() + " ..." : "...");
			}

			return "FIELD ...";
		} else if (type == EnigmaMappingTypes.METHOD) {
			EnigmaMappingMethod method = node.getPsi(EnigmaMappingMethod.class);
			List<EnigmaMappingIdentifierName> names = method.getIdentifierNameList();
			if (!names.isEmpty()) {
				EnigmaMappingIdentifierName name = names.get(names.size() - 1);
				EnigmaMappingMethodDescriptor desc = method.getMethodDescriptor();
				return "METHOD " + name.getText() + " " + (desc != null ? desc.getText() + " ..." : "...");
			}

			return "METHOD ...";
		} else if (type == EnigmaMappingTypes.ARG) {
			return "ARG ...";
		} else if (type == EnigmaMappingTypes.COMMENT) {
			return "COMMENT ...";
		}

		return "...";
	}

	@Override
	public boolean isCollapsedByDefault(@NotNull ASTNode node) {
		return false;
	}
}
