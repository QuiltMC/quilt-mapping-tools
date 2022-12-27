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

package org.quiltmc.intellij.mapping.language;

import com.intellij.json.psi.impl.JsonFileImpl;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import org.jetbrains.annotations.NotNull;

public class QuiltMappingFile extends JsonFileImpl {
	protected QuiltMappingFile(@NotNull FileViewProvider viewProvider) {
		super(viewProvider, QuiltMappingLanguage.INSTANCE);
	}

	@Override
	public @NotNull FileType getFileType() {
		return QuiltMappingFileType.INSTANCE;
	}

	@Override
	public String toString() {
		return "Quilt Mapping File: " + getName();
	}
}
