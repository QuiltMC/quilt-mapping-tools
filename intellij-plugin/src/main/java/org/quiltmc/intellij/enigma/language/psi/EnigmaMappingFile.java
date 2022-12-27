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

package org.quiltmc.intellij.enigma.language.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.intellij.enigma.language.EnigmaMappingFileType;
import org.quiltmc.intellij.enigma.language.EnigmaMappingLanguage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EnigmaMappingFile extends PsiFileBase {
	public EnigmaMappingFile(@NotNull FileViewProvider viewProvider) {
		super(viewProvider, EnigmaMappingLanguage.INSTANCE);
	}

	@Nullable
	public EnigmaMappingClazz getTopLevelClass() {
		return PsiTreeUtil.getChildOfType(this, EnigmaMappingClazz.class);
	}

	public List<EnigmaMappingClazz> getClasses() {
		EnigmaMappingClazz top = getTopLevelClass();
		if (top != null) {
			List<EnigmaMappingClazz> classes = new ArrayList<>(top.getChildrenClasses());
			classes.add(0, top);
			return classes;
		}

		return Collections.emptyList();
	}

	@Override
	public @NotNull FileType getFileType() {
		return EnigmaMappingFileType.INSTANCE;
	}

	@Override
	public String toString() {
		return "EnigmaMappingFile: " + getName();
	}
}
