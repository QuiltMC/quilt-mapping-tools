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

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.navigation.ItemPresentation;
import com.intellij.openapi.util.NlsSafe;
import com.intellij.psi.PsiElement;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.intellij.enigma.language.psi.EnigmaMappingClazz;
import org.quiltmc.intellij.enigma.language.psi.EnigmaMappingEntry;
import org.quiltmc.intellij.enigma.language.psi.EnigmaMappingPsiUtil;

public abstract class EnigmaMappingEntryImplMixin extends ASTWrapperPsiElement implements EnigmaMappingEntry {
	public EnigmaMappingEntryImplMixin(@NotNull ASTNode node) {
		super(node);
	}

	@Override
	public ItemPresentation getPresentation() {
		return EnigmaMappingPsiImplUtil.getPresentation(this);
	}

	@Override
	public @Nullable PsiElement getNameIdentifier() {
		return EnigmaMappingPsiImplUtil.getNameIdentifier(this);
	}

	@Override
	public String getName() {
		if (this instanceof EnigmaMappingClazz) {
			return EnigmaMappingPsiUtil.getFullClassName((EnigmaMappingClazz) this, false);
		}

		return getNameIdentifier() != null ? getNameIdentifier().getText() : super.getName();
	}

	@Override
	public PsiElement setName(@NlsSafe @NotNull String name) throws IncorrectOperationException {
		return EnigmaMappingPsiImplUtil.setName(this, name);
	}

	@Override
	public @NotNull PsiElement getNavigationElement() {
		PsiElement e = getNameIdentifier();
		return e != null ? e : super.getNavigationElement();
	}

	@Override
	public boolean usesClassName() {
		return false;
	}

	@Override
	public boolean hasName() {
		if (usesClassName()) return getNamedCls() != null;
		return getNamed() != null;
	}

	@Override
	public boolean isNamed() {
		if (usesClassName()) return hasName() && getNamedCls() != getObfCls();
		return hasName() && getNamed() != getObf();
	}
}
