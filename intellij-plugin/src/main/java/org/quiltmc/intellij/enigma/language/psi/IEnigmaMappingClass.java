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

import java.util.List;

public interface IEnigmaMappingClass {
	/**
	 * Gets all the parent classes of this class, using cache if available.
	 * @see #getParentClass()
	 */
	List<? extends EnigmaMappingClazz> getParentClasses();

	/**
	 * Gets the parent of this class, using cache if available.
	 * @see #getParentClasses()
	 */
	default EnigmaMappingClazz getParentClass() {
		List<? extends EnigmaMappingClazz> classes = getParentClasses();
		return classes.size() >= 1 ? classes.get(classes.size() - 1) : null;
	}

	/**
	 * Gets all the children classes within this class hierarchy, using cache if available.
	 */
	List<? extends EnigmaMappingClazz> getChildrenClasses();
}
