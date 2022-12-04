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

import com.intellij.lang.ASTNode;
import com.intellij.psi.util.PsiTreeUtil;
import org.quiltmc.intellij.enigma.language.psi.EnigmaMappingClazz;
import org.quiltmc.intellij.enigma.language.psi.IEnigmaMappingClass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EnigmaMappingClazzImplMixin extends EnigmaMappingEntryImpl implements IEnigmaMappingClass {
	private List<EnigmaMappingClazz> parents;
	private List<EnigmaMappingClazz> childrenClasses;

	public EnigmaMappingClazzImplMixin(ASTNode node) {
		super(node);
	}

	private List<EnigmaMappingClazz> createParentsList() {
		EnigmaMappingClazz parent = PsiTreeUtil.getParentOfType(this, EnigmaMappingClazz.class);
		if (parent != null) {
			List<EnigmaMappingClazz> parents = new ArrayList<>(parent.getParentClasses());
			parents.add(parent);
			return parents;
		} else {
			return Collections.emptyList();
		}
	}

	private List<EnigmaMappingClazz> findChildrenClasses() {
		return new ArrayList<>(PsiTreeUtil.findChildrenOfType(this, EnigmaMappingClazz.class));
	}

	@Override
	public boolean usesClassName() {
		return true;
	}

	@Override
	public void subtreeChanged() {
		super.subtreeChanged();
		childrenClasses = null;
		parents = null; // invalidate just in case
	}

	@Override
	public List<? extends EnigmaMappingClazz> getParentClasses() {
		if (parents == null) {
			parents = createParentsList();
		}

		return parents;
	}

	@Override
	public List<? extends EnigmaMappingClazz> getChildrenClasses() {
		if (childrenClasses == null) {
			childrenClasses = findChildrenClasses();
		}

		return childrenClasses;
	}
}
