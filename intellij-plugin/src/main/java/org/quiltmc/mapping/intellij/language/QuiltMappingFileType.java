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

package org.quiltmc.mapping.intellij.language;

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.mapping.intellij.QuiltMappingIcons;

import javax.swing.Icon;

public class QuiltMappingFileType extends LanguageFileType {
	public static final QuiltMappingFileType INSTANCE = new QuiltMappingFileType();

	private QuiltMappingFileType() {
		super(QuiltMappingLanguage.INSTANCE);
	}

	@NotNull
	@Override
	public String getName() {
		return "Quilt Mapping";
	}

	@NotNull
	@Override
	public String getDescription() {
		return "Quilt Mapping file";
	}

	@NotNull
	@Override
	public String getDefaultExtension() {
		return "quiltmapping";
	}

	@Nullable
	@Override
	public Icon getIcon() {
		return QuiltMappingIcons.FILE;
	}
}
