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

package org.quiltmc.intellij.enigma.language.reference;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReferenceBase;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.intellij.enigma.language.EnigmaMappingUtil;

public class EnigmaMappingClassReference extends PsiReferenceBase<PsiElement> {
	private final String text;

	public EnigmaMappingClassReference(@NotNull PsiElement element, TextRange rangeInElement) {
		super(element, rangeInElement);
		this.text = element.getText().substring(rangeInElement.getStartOffset(), rangeInElement.getEndOffset());
	}

	@Override
	public @Nullable PsiElement resolve() {
		Project project = myElement.getProject();
		return EnigmaMappingUtil.findClass(project, text);
	}
}
