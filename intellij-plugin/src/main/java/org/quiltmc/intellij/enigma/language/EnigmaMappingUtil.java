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

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.intellij.enigma.language.psi.EnigmaMappingClazz;
import org.quiltmc.intellij.enigma.language.psi.EnigmaMappingComment;
import org.quiltmc.intellij.enigma.language.psi.EnigmaMappingEntry;
import org.quiltmc.intellij.enigma.language.psi.EnigmaMappingFile;
import org.quiltmc.intellij.enigma.language.psi.EnigmaMappingPsiUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

public final class EnigmaMappingUtil {
	private EnigmaMappingUtil() {}

	// TODO: Cache?
	@Nullable
	public static EnigmaMappingClazz findClass(Project project, String name) {
		return findClass(project, name, true);
	}

	@Nullable
	public static EnigmaMappingClazz findClass(Project project, String name, boolean obfName) {
		Collection<VirtualFile> virtualFiles = FileTypeIndex.getFiles(EnigmaMappingFileType.INSTANCE, GlobalSearchScope.allScope(project));

		for (VirtualFile virtualFile : virtualFiles) {
			EnigmaMappingFile file = (EnigmaMappingFile) PsiManager.getInstance(project).findFile(virtualFile);

			if (file != null) {
				Collection<EnigmaMappingClazz> classes = file.getClasses();

				for (EnigmaMappingClazz clazz : classes) {
					if (!clazz.hasName() || !obfName && !clazz.isNamed()) {
						continue;
					}

					String className = EnigmaMappingPsiUtil.getFullClassName(clazz, obfName);
					if (className != null && className.equals(name)) {
						return clazz;
					}
				}
			}
		}

		return null;
	}

	public static List<EnigmaMappingClazz> findClasses(Project project) {
		return findClasses(project, c -> true);
	}

	public static List<EnigmaMappingClazz> findClasses(Project project, String name, boolean obfName) {
		return findClasses(project, clazz -> {
			if (!clazz.hasName() || !obfName && !clazz.isNamed()) {
				return false;
			}

			String className = EnigmaMappingPsiUtil.getFullClassName(clazz, obfName);
			return name.equals(className);
		});
	}

	private static List<EnigmaMappingClazz> findClasses(Project project, Predicate<EnigmaMappingClazz> predicate) {
		List<EnigmaMappingClazz> result = new ArrayList<>();
		Collection<VirtualFile> virtualFiles = FileTypeIndex.getFiles(EnigmaMappingFileType.INSTANCE, GlobalSearchScope.allScope(project));

		for (VirtualFile virtualFile : virtualFiles) {
			EnigmaMappingFile file = (EnigmaMappingFile) PsiManager.getInstance(project).findFile(virtualFile);

			if (file != null) {
				for (EnigmaMappingClazz clazz : file.getClasses()) {
					if (predicate.test(clazz)) {
						result.add(clazz);
					}
				}
			}
		}

		return result;
	}

	public static String getComment(EnigmaMappingEntry entry) {
		List<EnigmaMappingComment> comments = entry.getCommentList();
		List<String> lines = new ArrayList<>();
		for (EnigmaMappingComment comment : comments) {
			lines.add(comment.getContent());
		}

		return String.join("\n", lines);
	}
}
