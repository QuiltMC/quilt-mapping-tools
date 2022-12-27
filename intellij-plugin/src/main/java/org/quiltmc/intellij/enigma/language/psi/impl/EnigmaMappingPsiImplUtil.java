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

package org.quiltmc.intellij.enigma.language.psi.impl;

import com.intellij.navigation.ItemPresentation;
import com.intellij.openapi.util.NlsSafe;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiNameIdentifierOwner;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.intellij.enigma.language.psi.EnigmaMappingElementFactory;
import org.quiltmc.intellij.enigma.language.psi.EnigmaMappingEntry;
import org.quiltmc.intellij.enigma.language.psi.EnigmaMappingPsiUtil;

import javax.swing.Icon;

public final class EnigmaMappingPsiImplUtil {
	private EnigmaMappingPsiImplUtil() {}

	public static ItemPresentation getPresentation(@NotNull EnigmaMappingEntry entry) {
		return new ItemPresentation() {
			@Override
			public @NlsSafe @Nullable String getPresentableText() {
				return EnigmaMappingPsiUtil.toString(entry);
			}

			@Override
			public @NlsSafe @Nullable String getLocationString() {
				PsiFile file = entry.getContainingFile();
				return file != null ? file.getName() : null;
			}

			@Override
			public @Nullable Icon getIcon(boolean unused) {
				return EnigmaMappingPsiUtil.getIcon(entry);
			}
		};
	}

	/**
	 * @see PsiNameIdentifierOwner#getNameIdentifier()
	 */
	@Nullable
	public static PsiElement getNameIdentifier(@NotNull EnigmaMappingEntry entry) {
		return entry.usesClassName() ? entry.getNamedCls() : entry.getNamed();
	}

	/**
	 * @see PsiNameIdentifierOwner#setName(String)
	 */
	public static PsiElement setName(@NotNull EnigmaMappingEntry entry, String name) {
		if (entry.usesClassName()) {
			return EnigmaMappingElementFactory.createClassName(entry.getProject(), name);
		} else {
			return EnigmaMappingElementFactory.createIdentifierName(entry.getProject(), name);
		}
	}
}
