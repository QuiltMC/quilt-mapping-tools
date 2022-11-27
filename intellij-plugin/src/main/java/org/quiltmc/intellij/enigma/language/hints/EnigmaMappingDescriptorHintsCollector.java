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

package org.quiltmc.intellij.enigma.language.hints;

import com.intellij.codeInsight.hints.FactoryInlayHintsCollector;
import com.intellij.codeInsight.hints.InlayHintsSink;
import com.intellij.codeInsight.hints.presentation.InlayPresentation;
import com.intellij.codeInsight.hints.presentation.PresentationFactory;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.intellij.enigma.language.EnigmaMappingUtil;
import org.quiltmc.intellij.enigma.language.psi.EnigmaMappingClassName;
import org.quiltmc.intellij.enigma.language.psi.EnigmaMappingClazz;
import org.quiltmc.intellij.enigma.language.psi.EnigmaMappingDescriptor;
import org.quiltmc.intellij.enigma.language.psi.EnigmaMappingMethodDescriptor;
import org.quiltmc.intellij.enigma.language.psi.EnigmaMappingPsiUtil;
import org.quiltmc.intellij.enigma.language.psi.EnigmaMappingReturnDescriptor;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("UnstableApiUsage")
public class EnigmaMappingDescriptorHintsCollector extends FactoryInlayHintsCollector {
	public EnigmaMappingDescriptorHintsCollector(@NotNull Editor editor) {
		super(editor);
	}

	private InlayPresentation createPresentation(String text) {
		PresentationFactory factory = getFactory();
		return factory.roundWithBackground(factory.smallText(text));
	}

	@Override
	public boolean collect(@NotNull PsiElement element, @NotNull Editor editor, @NotNull InlayHintsSink inlayHintsSink) {
		collect(element, inlayHintsSink);
		return true;
	}

	private void collect(@Nullable PsiElement element, @NotNull InlayHintsSink inlayHintsSink) {
		if (shouldShowHints(element)) {
			String text;
			if (element instanceof EnigmaMappingMethodDescriptor) {
				EnigmaMappingMethodDescriptor methodDescriptor = (EnigmaMappingMethodDescriptor) element;
				List<String> paramHints = new ArrayList<>();

				for (EnigmaMappingDescriptor descriptor : methodDescriptor.getDescriptorList()) {
					paramHints.add(getHintText(descriptor));
				}

				String returnText = getHintText(methodDescriptor.getReturnDescriptor());
				text = returnText + " (" + String.join(", ", paramHints) + ")";
			} else {
				text = getHintText(element);
			}

			inlayHintsSink.addInlineElement(element.getTextOffset() + element.getTextLength(), true, createPresentation(text), false);
		}
	}

	private static boolean shouldShowHints(@Nullable PsiElement element) {
		if (element == null) {
			return false;
		}

		if (element instanceof EnigmaMappingMethodDescriptor) {
			return true;
		} else if (element instanceof EnigmaMappingDescriptor) {
			return !(element.getParent() instanceof EnigmaMappingMethodDescriptor);
		}

		return false;
	}

	private static String getHintText(@Nullable PsiElement element) {
		if (element == null) {
			return "<unknown>";
		} else if (!(element instanceof EnigmaMappingDescriptor) && !(element instanceof EnigmaMappingReturnDescriptor)) {
			return "";
		}

		String descriptor = element.getText();
		int arrayDim = 0;
		while (descriptor.startsWith("[")) {
			arrayDim++;
			descriptor = descriptor.substring(1);
		}
		String name = "";

		EnigmaMappingClassName className = element instanceof EnigmaMappingDescriptor
				? ((EnigmaMappingDescriptor) element).getClassName()
				: ((EnigmaMappingReturnDescriptor) element).getClassName();
		if (className != null) {
			EnigmaMappingClazz clazz = EnigmaMappingUtil.findClass(element.getProject(), className.getText());
			if (clazz != null) {
				name = EnigmaMappingPsiUtil.getReadableClassName(clazz);
			}

			if (name == null || name.isEmpty()) {
				name = className.getText().replace('/', '.').replace('$', '.');
			}
		}

		if (name.isEmpty()) {
			switch (descriptor.charAt(0)) {
				case 'B':
					name = "byte";
					break;
				case 'C':
					name = "char";
					break;
				case 'D':
					name = "double";
					break;
				case 'F':
					name = "float";
					break;
				case 'I':
					name = "int";
					break;
				case 'J':
					name = "long";
					break;
				case 'S':
					name = "short";
					break;
				case 'Z':
					name = "boolean";
					break;
				case 'V':
					name = "void";
					break;
				case 'L':
					name = "<class>";
					break;
				default:
					name = "<unknown>";
			}
		}

		return name + "[]".repeat(arrayDim);
	}
}
