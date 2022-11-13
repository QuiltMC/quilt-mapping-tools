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

import com.intellij.icons.AllIcons;
import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.util.NlsContexts;
import com.intellij.openapi.util.NlsSafe;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;

public class EnigmaMappingFileType extends LanguageFileType {
	public static final EnigmaMappingFileType INSTANCE = new EnigmaMappingFileType();

	protected EnigmaMappingFileType() {
		super(EnigmaMappingLanguage.INSTANCE);
	}

	@Override
	public @NonNls @NotNull String getName() {
		return "Enigma Mappings";
	}

	@Override
	public @NlsContexts.Label @NotNull String getDescription() {
		return "Mappings file (Enigma format)";
	}

	@Override
	public @NlsSafe @NotNull String getDefaultExtension() {
		return "mapping";
	}

	@Override
	public @Nullable Icon getIcon() {
		return AllIcons.FileTypes.Text;
	}
}
