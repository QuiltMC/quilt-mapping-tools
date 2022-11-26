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

import com.intellij.navigation.ChooseByNameContributor;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.quiltmc.intellij.enigma.language.psi.EnigmaMappingClazz;
import org.quiltmc.intellij.enigma.language.psi.EnigmaMappingPsiUtil;
import org.quiltmc.intellij.enigma.language.psi.impl.EnigmaMappingClazzImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EnigmaMappingChooseByNameContributor implements ChooseByNameContributor {
	@Override
	public String @NotNull [] getNames(Project project, boolean includeNonProjectItems) {
		// TODO: Methods and fields
		List<EnigmaMappingClazz> classes = EnigmaMappingUtil.findClasses(project);
		List<String> names = new ArrayList<>(classes.size());

		for (EnigmaMappingClazz clazz : classes) {
			String name = EnigmaMappingPsiUtil.getFullClassName(clazz, false);
			if (name != null) {
				names.add(name);
			}
		}

		return names.toArray(new String[names.size()]);
	}

	@Override
	public NavigationItem @NotNull [] getItemsByName(String name, String pattern, Project project, boolean includeNonProjectItems) {
		List<EnigmaMappingClazz> classes = EnigmaMappingUtil.findClasses(project, name, false);
		List<NavigationItem> items = classes.stream().map(c -> (EnigmaMappingClazzImpl) c).collect(Collectors.toList());
		return items.toArray(new NavigationItem[0]);
	}
}
