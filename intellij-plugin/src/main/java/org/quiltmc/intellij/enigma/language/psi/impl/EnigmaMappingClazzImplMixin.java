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
import com.intellij.psi.util.CachedValue;
import com.intellij.psi.util.CachedValueProvider;
import com.intellij.psi.util.CachedValuesManager;
import com.intellij.psi.util.PsiModificationTracker;
import com.intellij.psi.util.PsiTreeUtil;
import org.quiltmc.intellij.enigma.language.psi.EnigmaMappingClazz;
import org.quiltmc.intellij.enigma.language.psi.IEnigmaMappingClass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EnigmaMappingClazzImplMixin extends EnigmaMappingEntryImpl implements IEnigmaMappingClass {
	private final CachedValue<List<EnigmaMappingClazz>> parentCache;
	private final CachedValue<List<EnigmaMappingClazz>> childrenCache;

	public EnigmaMappingClazzImplMixin(ASTNode node) {
		super(node);

		parentCache = CachedValuesManager.getManager(getProject()).createCachedValue(() -> {
			List<EnigmaMappingClazz> parents = createParentsList();
			List<Object> dependencies = new ArrayList<>(parents);
			dependencies.add(PsiModificationTracker.MODIFICATION_COUNT);
			dependencies.add(this);
			return CachedValueProvider.Result.create(parents, dependencies);
		});
		childrenCache = CachedValuesManager.getManager(getProject()).createCachedValue(() ->
				CachedValueProvider.Result.create(findChildrenClasses(), PsiModificationTracker.MODIFICATION_COUNT, this));
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
	public List<? extends EnigmaMappingClazz> getParentClasses() {
		List<EnigmaMappingClazz> cached = parentCache.getValue();

		return parentCache.hasUpToDateValue() ? cached : createParentsList();
	}

	@Override
	public List<? extends EnigmaMappingClazz> getChildrenClasses() {
		List<EnigmaMappingClazz> cached = childrenCache.getValue();

		return childrenCache.hasUpToDateValue() ? cached : findChildrenClasses();
	}
}
