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

package org.quiltmc.intellij.enigma.language.structureview;

import com.intellij.ide.projectView.PresentationData;
import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.util.treeView.smartTree.SortableTreeElement;
import com.intellij.ide.util.treeView.smartTree.TreeElement;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.NavigatablePsiElement;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.quiltmc.intellij.enigma.language.psi.EnigmaMappingArg;
import org.quiltmc.intellij.enigma.language.psi.EnigmaMappingClazz;
import org.quiltmc.intellij.enigma.language.psi.EnigmaMappingEntry;
import org.quiltmc.intellij.enigma.language.psi.EnigmaMappingField;
import org.quiltmc.intellij.enigma.language.psi.EnigmaMappingFile;
import org.quiltmc.intellij.enigma.language.psi.EnigmaMappingMethod;
import org.quiltmc.intellij.enigma.language.psi.EnigmaMappingPsiUtil;
import org.quiltmc.intellij.enigma.language.psi.impl.EnigmaMappingArgImpl;
import org.quiltmc.intellij.enigma.language.psi.impl.EnigmaMappingClazzImpl;
import org.quiltmc.intellij.enigma.language.psi.impl.EnigmaMappingEntryImpl;

import java.util.ArrayList;
import java.util.List;

public class EnigmaMappingStructureViewElement implements StructureViewTreeElement, SortableTreeElement {
	private final NavigatablePsiElement element;

	public EnigmaMappingStructureViewElement(@NotNull NavigatablePsiElement element) {
		this.element = element;
	}

	@Override
	public Object getValue() {
		return element;
	}

	@Override
	public @NotNull String getAlphaSortKey() {
		if (element instanceof EnigmaMappingEntry) {
			String name = EnigmaMappingPsiUtil.getName((EnigmaMappingEntry) element);

			if (name != null) {
				return name;
			}

			if (element instanceof EnigmaMappingClazz) {
				return "class";
			} else if (element instanceof EnigmaMappingField) {
				return "field";
			} else if (element instanceof EnigmaMappingMethod) {
				return "method";
			} else if (element instanceof EnigmaMappingArg) {
				EnigmaMappingArg arg = (EnigmaMappingArg) element;
				PsiElement index = arg.getNumber();
				return index != null ? index.getText() : "arg";
			}
		}

		return "";
	}

	@Override
	public @NotNull ItemPresentation getPresentation() {
		ItemPresentation presentation = element.getPresentation();
		return presentation != null ? presentation : new PresentationData();
	}

	@Override
	public TreeElement @NotNull [] getChildren() {
		if (element instanceof EnigmaMappingFile) {
			EnigmaMappingClazz clazz = ((EnigmaMappingFile) element).getTopLevelClass();
			if (clazz != null) {
				return new TreeElement[] {new EnigmaMappingStructureViewElement((EnigmaMappingClazzImpl) clazz)};
			}
		} else if (element instanceof EnigmaMappingClazz) {
			EnigmaMappingClazz clazz = (EnigmaMappingClazz) element;

			List<TreeElement> elements = new ArrayList<>();
			for (EnigmaMappingEntry entry : clazz.getEntryList()) {
				elements.add(new EnigmaMappingStructureViewElement((EnigmaMappingEntryImpl) entry));
			}

			return elements.toArray(TreeElement.EMPTY_ARRAY);
		} else if (element instanceof EnigmaMappingMethod) {
			EnigmaMappingMethod method = (EnigmaMappingMethod) element;

			List<TreeElement> elements = new ArrayList<>();
			for (EnigmaMappingArg arg : method.getArgList()) {
				elements.add(new EnigmaMappingStructureViewElement((EnigmaMappingArgImpl) arg));
			}

			return elements.toArray(TreeElement.EMPTY_ARRAY);
		}

		return EMPTY_ARRAY;
	}

	@Override
	public void navigate(boolean requestFocus) {
		element.navigate(requestFocus);
	}

	@Override
	public boolean canNavigate() {
		return element.canNavigate();
	}

	@Override
	public boolean canNavigateToSource() {
		return element.canNavigateToSource();
	}
}
