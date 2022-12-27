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
