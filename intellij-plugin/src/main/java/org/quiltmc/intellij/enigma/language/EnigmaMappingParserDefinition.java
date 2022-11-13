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

import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;
import org.quiltmc.intellij.enigma.language.parser.EnigmaMappingParser;
import org.quiltmc.intellij.enigma.language.psi.EnigmaMappingFile;
import org.quiltmc.intellij.enigma.language.psi.EnigmaMappingTypes;

public class EnigmaMappingParserDefinition implements ParserDefinition {
	public static final IFileElementType FILE = new IFileElementType(EnigmaMappingLanguage.INSTANCE);

	@Override
	public @NotNull Lexer createLexer(Project project) {
		return new EnigmaMappingLexerAdapter();
	}

	@Override
	public @NotNull PsiParser createParser(Project project) {
		return new EnigmaMappingParser();
	}

	@Override
	public @NotNull IFileElementType getFileNodeType() {
		return FILE;
	}

	@Override
	public @NotNull TokenSet getCommentTokens() {
		return TokenSet.EMPTY;
	}

	@Override
	public @NotNull TokenSet getStringLiteralElements() {
		return TokenSet.EMPTY;
	}

	@Override
	public @NotNull PsiElement createElement(ASTNode node) {
		return EnigmaMappingTypes.Factory.createElement(node);
	}

	@Override
	public @NotNull PsiFile createFile(@NotNull FileViewProvider viewProvider) {
		return new EnigmaMappingFile(viewProvider);
	}
}
