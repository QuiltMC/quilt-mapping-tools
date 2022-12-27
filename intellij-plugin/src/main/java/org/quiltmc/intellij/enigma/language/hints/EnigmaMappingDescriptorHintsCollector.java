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
import com.intellij.openapi.project.DumbService;
import com.intellij.openapi.util.TextRange;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("UnstableApiUsage")
public class EnigmaMappingDescriptorHintsCollector extends FactoryInlayHintsCollector {
	private static final String DEFAULT_VALUE = "<unknown>";
	private static final Map<Character, String> DESCRIPTORS = Map.of(
			'B', "byte",
			'C', "char",
			'D', "double",
			'F', "float",
			'I', "int",
			'J', "long",
			'S', "short",
			'Z', "boolean",

			'V', "void",
			'L', "<class>"
	);

	private final Map<String, String> classNameCache = new HashMap<>();

	public EnigmaMappingDescriptorHintsCollector(@NotNull Editor editor) {
		super(editor);
	}

	private InlayPresentation createPresentation(String text) {
		PresentationFactory factory = getFactory();
		return factory.roundWithBackground(factory.smallText(text));
	}

	@Override
	public boolean collect(@NotNull PsiElement element, @NotNull Editor editor, @NotNull InlayHintsSink inlayHintsSink) {
		if (DumbService.isDumb(element.getProject()) || element.getProject().isDefault()) {
			return false;
		}

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

	private String getHintText(@Nullable PsiElement element) {
		if (element == null) {
			return DEFAULT_VALUE;
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
			TextRange range = className.getTextRangeInParent();
			String text = descriptor.substring(range.getStartOffset(), range.getEndOffset());

			if (classNameCache.containsKey(text)) {
				name = classNameCache.get(text);
			} else {
				EnigmaMappingClazz clazz = EnigmaMappingUtil.findClass(element.getProject(), text);
				if (clazz != null) {
					name = EnigmaMappingPsiUtil.getReadableClassName(clazz);
				}

				if (name == null || name.isEmpty()) {
					name = binaryToReadableName(text);
				}

				classNameCache.put(text, name);
			}
		}

		if (name.isEmpty()) {
			name = DESCRIPTORS.getOrDefault(descriptor.charAt(0), DEFAULT_VALUE);
		}

		return name + "[]".repeat(arrayDim);
	}

	private static String binaryToReadableName(String name) {
		String pkg = name.substring(0, name.lastIndexOf('/'));
		if (pkg.equals("java/lang")) {
			return name.substring(pkg.length() + 1).replace('$', '.');
		}

		return name.replace('/', '.').replace('$', '.');
	}
}
