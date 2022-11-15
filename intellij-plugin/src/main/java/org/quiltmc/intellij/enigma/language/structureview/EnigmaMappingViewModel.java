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

import com.intellij.ide.structureView.StructureViewModel;
import com.intellij.ide.structureView.StructureViewModelBase;
import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.util.treeView.smartTree.Sorter;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.intellij.enigma.language.psi.*;

public class EnigmaMappingViewModel extends StructureViewModelBase implements StructureViewModel.ElementInfoProvider {
	public EnigmaMappingViewModel(@NotNull PsiFile psiFile, @Nullable Editor editor) {
		super(psiFile, editor, new EnigmaMappingStructureViewElement(psiFile));
		withSuitableClasses(EnigmaMappingFile.class, EnigmaMappingClazz.class, EnigmaMappingField.class, EnigmaMappingMethod.class, EnigmaMappingArg.class);
		withSorters(Sorter.ALPHA_SORTER);
	}

	@Override
	public boolean isAlwaysShowsPlus(StructureViewTreeElement element) {
		return false;
	}

	@Override
	public boolean isAlwaysLeaf(StructureViewTreeElement element) {
		return false;
	}
}
