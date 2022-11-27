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

package org.quiltmc.intellij.enigma.language.hints;

import com.intellij.codeInsight.hints.ImmediateConfigurable;
import com.intellij.codeInsight.hints.InlayHintsCollector;
import com.intellij.codeInsight.hints.InlayHintsProvider;
import com.intellij.codeInsight.hints.InlayHintsSink;
import com.intellij.codeInsight.hints.NoSettings;
import com.intellij.codeInsight.hints.SettingsKey;
import com.intellij.lang.Language;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.intellij.enigma.language.EnigmaMappingLanguage;

import javax.swing.JPanel;

// TODO: Convert to kotlin?
@SuppressWarnings("UnstableApiUsage")
public class EnigmaMappingDescriptorHintsProvider implements InlayHintsProvider<NoSettings> {
	@Nullable
	@Override
	public InlayHintsCollector getCollectorFor(@NotNull PsiFile psiFile, @NotNull Editor editor, @NotNull NoSettings noSettings, @NotNull InlayHintsSink inlayHintsSink) {
		return new EnigmaMappingDescriptorHintsCollector(editor);
	}

	@NotNull
	@Override
	public NoSettings createSettings() {
		return new NoSettings();
	}

	@Override
	public boolean isVisibleInSettings() {
		return false;
	}

	@NotNull
	@Override
	public SettingsKey<NoSettings> getKey() {
		return new SettingsKey<>("enigma.hints.descriptor");
	}

	@Nls(capitalization = Nls.Capitalization.Sentence)
	@NotNull
	@Override
	public String getName() {
		return "Descriptor hints";
	}

	@Nullable
	@Override
	public String getPreviewText() {
		return "CLASS a/a com/example/Foo\n" +
				"\tFIELD a INSTANCE La/a;\n";
	}

	@NotNull
	@Override
	public ImmediateConfigurable createConfigurable(@NotNull NoSettings noSettings) {
		return changeListener -> new JPanel();
	}

	@Override
	public boolean isLanguageSupported(@NotNull Language language) {
		return language.is(EnigmaMappingLanguage.INSTANCE);
	}
}
