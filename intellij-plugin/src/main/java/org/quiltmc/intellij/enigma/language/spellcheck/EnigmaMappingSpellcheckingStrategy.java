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

package org.quiltmc.intellij.enigma.language.spellcheck;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.spellchecker.inspections.CommentSplitter;
import com.intellij.spellchecker.inspections.IdentifierSplitter;
import com.intellij.spellchecker.tokenizer.SpellcheckingStrategy;
import com.intellij.spellchecker.tokenizer.TokenConsumer;
import com.intellij.spellchecker.tokenizer.Tokenizer;
import org.jetbrains.annotations.NotNull;
import org.quiltmc.intellij.enigma.language.psi.*;

public class EnigmaMappingSpellcheckingStrategy extends SpellcheckingStrategy {
	private final Tokenizer<EnigmaMappingComment> commentTokenizer = new Tokenizer<>() {
		@Override
		public void tokenize(@NotNull EnigmaMappingComment element, TokenConsumer consumer) {
			ASTNode textNode = element.getNode().findChildByType(EnigmaMappingTypes.COMMENT_TEXT);
			PsiElement textElement = textNode == null ? null : textNode.getPsi();

			if (textElement != null) {
				consumer.consumeToken(textElement, textElement.getText(), false, 0,
						TextRange.create(1, textElement.getTextLength()), CommentSplitter.getInstance());
			}
		}
	};

	private final Tokenizer<EnigmaMappingIdentifierName> identifierTokenizer = new Tokenizer<>() {
		@Override
		public void tokenize(@NotNull EnigmaMappingIdentifierName element, TokenConsumer consumer) {
			consumer.consumeToken(element, IdentifierSplitter.getInstance());
		}
	};

	private final Tokenizer<EnigmaMappingClazz> classTokenizer = new Tokenizer<>() {
		@Override
		public void tokenize(@NotNull EnigmaMappingClazz element, TokenConsumer consumer) {
			if (element.getBinaryNameList().size() == 2) {
				EnigmaMappingBinaryName name = element.getBinaryNameList().get(1);
				name.getIdentifierNameList().forEach(n -> identifierTokenizer.tokenize(n, consumer));
			}
		}
	};

	private final Tokenizer<EnigmaMappingField> fieldTokenizer = new Tokenizer<>() {
		@Override
		public void tokenize(@NotNull EnigmaMappingField element, TokenConsumer consumer) {
			if (element.getIdentifierNameList().size() == 2) {
				EnigmaMappingIdentifierName name = element.getIdentifierNameList().get(1);
				identifierTokenizer.tokenize(name, consumer);
			}
		}
	};

	private final Tokenizer<EnigmaMappingMethod> methodTokenizer = new Tokenizer<>() {
		@Override
		public void tokenize(@NotNull EnigmaMappingMethod element, TokenConsumer consumer) {
			if (element.getIdentifierNameList().size() == 2) {
				EnigmaMappingIdentifierName name = element.getIdentifierNameList().get(1);
				identifierTokenizer.tokenize(name, consumer);
			}
		}
	};

	private final Tokenizer<EnigmaMappingArg> argTokenizer = new Tokenizer<>() {
		@Override
		public void tokenize(@NotNull EnigmaMappingArg element, TokenConsumer consumer) {
			if (element.getIdentifierName() != null) {
				identifierTokenizer.tokenize(element.getIdentifierName(), consumer);
			}
		}
	};

	@Override
	public @NotNull Tokenizer<?> getTokenizer(PsiElement element) {
		if (element instanceof EnigmaMappingComment) {
			return commentTokenizer;
		}

		if (element instanceof EnigmaMappingClazz) {
			return classTokenizer;
		}

		if (element instanceof EnigmaMappingField) {
			return fieldTokenizer;
		}

		if (element instanceof EnigmaMappingMethod) {
			return methodTokenizer;
		}

		if (element instanceof EnigmaMappingArg) {
			return argTokenizer;
		}

		return super.getTokenizer(element);
	}
}
