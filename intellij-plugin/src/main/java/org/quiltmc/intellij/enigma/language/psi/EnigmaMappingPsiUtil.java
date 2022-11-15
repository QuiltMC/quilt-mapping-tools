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

import com.intellij.icons.AllIcons;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;
import java.util.List;

public final class EnigmaMappingPsiUtil {
	@Nullable
	public static Icon getIcon(@NotNull EnigmaMappingEntry entry) {
		if (entry instanceof EnigmaMappingClazz) {
			return AllIcons.Nodes.Class;
		} else if (entry instanceof EnigmaMappingField) {
			return AllIcons.Nodes.Field;
		} else if (entry instanceof EnigmaMappingMethod) {
			return AllIcons.Nodes.Method;
		} else if (entry instanceof EnigmaMappingArg) {
			return AllIcons.Nodes.Parameter;
		}

		return null;
	}

	@Nullable
	public static String getName(@NotNull ASTNode node) {
		IElementType type = node.getElementType();
		List<? extends PsiElement> names;
		if (type == EnigmaMappingTypes.CLAZZ) {
			names = node.getPsi(EnigmaMappingClazz.class).getBinaryNameList();
		} else if (type == EnigmaMappingTypes.FIELD) {
			names = node.getPsi(EnigmaMappingField.class).getIdentifierNameList();
		} else if (type == EnigmaMappingTypes.METHOD) {
			names = node.getPsi(EnigmaMappingMethod.class).getIdentifierNameList();
		} else if (type == EnigmaMappingTypes.ARG) {
			EnigmaMappingIdentifierName name = node.getPsi(EnigmaMappingArg.class).getIdentifierName();
			return name != null ? name.getText() : null;
		} else {
			return null;
		}

		return !names.isEmpty() ? names.get(names.size() - 1).getText() : null;
	}

	@Nullable
	public static String getDescriptor(@NotNull ASTNode node) {
		IElementType type = node.getElementType();
		PsiElement element;
		if (type == EnigmaMappingTypes.FIELD) {
			element = node.getPsi(EnigmaMappingField.class).getFieldDescriptor();
		} else if (type == EnigmaMappingTypes.METHOD) {
			element = node.getPsi(EnigmaMappingMethod.class).getMethodDescriptor();
		} else {
			return null;
		}

		return element != null ? element.getText() : null;
	}

	public static String toString(@NotNull ASTNode node) {
		IElementType type = node.getElementType();
		String name = getName(node);
		String descriptor = getDescriptor(node);

		return toString(type, name, descriptor);
	}

	private static String toString(@NotNull IElementType type, @Nullable String name, @Nullable String descriptor) {
		if (type == EnigmaMappingTypes.CLAZZ) {
			if (name == null) return "CLASS";
			return "CLASS " + name;
		} else if (type == EnigmaMappingTypes.FIELD) {
			if (name == null) return "FIELD";
			if (descriptor == null) return "FIELD " + name;
			return "FIELD " + name + " " + descriptor;
		} else if (type == EnigmaMappingTypes.METHOD) {
			if (name == null) return "METHOD";
			if (descriptor == null) return "METHOD " + name;
			return "METHOD " + name + " " + descriptor;
		} else if (type == EnigmaMappingTypes.ARG) {
			if (name == null) return "ARG";
			return "ARG " + name;
		} else if (type == EnigmaMappingTypes.COMMENT) {
			return "COMMENT";
		}

		return "";
	}

	@Nullable
	public static String getName(@NotNull EnigmaMappingEntry entry) {
		List<? extends PsiElement> names;
		if (entry instanceof EnigmaMappingClazz) {
			names = ((EnigmaMappingClazz) entry).getBinaryNameList();
		} else if (entry instanceof EnigmaMappingField) {
			names = ((EnigmaMappingField) entry).getIdentifierNameList();
		} else if (entry instanceof EnigmaMappingMethod) {
			names = ((EnigmaMappingMethod) entry).getIdentifierNameList();
		} else if (entry instanceof EnigmaMappingArg) {
			EnigmaMappingIdentifierName name = ((EnigmaMappingArg) entry).getIdentifierName();
			return name != null ? name.getText() : null;
		} else {
			return null;
		}

		return !names.isEmpty() ? names.get(names.size() - 1).getText() : null;
	}

	@Nullable
	public static String getDescriptor(@NotNull EnigmaMappingEntry entry) {
		PsiElement element;
		if (entry instanceof EnigmaMappingField) {
			element = ((EnigmaMappingField) entry).getFieldDescriptor();
		} else if (entry instanceof EnigmaMappingMethod) {
			element = ((EnigmaMappingMethod) entry).getMethodDescriptor();
		} else {
			return null;
		}

		return element != null ? element.getText() : null;
	}

	public static String toString(@NotNull EnigmaMappingEntry entry) {
		return toString(entry.getNode().getElementType(), getName(entry), getDescriptor(entry));
	}
}
