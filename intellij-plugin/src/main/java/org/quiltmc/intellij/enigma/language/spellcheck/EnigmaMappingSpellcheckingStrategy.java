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

	private final Tokenizer<EnigmaMappingEntry> entryTokenizer = new Tokenizer<>() {
		@Override
		public void tokenize(@NotNull EnigmaMappingEntry element, TokenConsumer consumer) {
			if (element.isNamed()) {
				if (element.usesClassName()) {
					if (element.getNamedCls() != null)
						element.getNamedCls().getIdentifierNameList().forEach(n -> identifierTokenizer.tokenize(n, consumer));
				} else if (element.getNamed() != null) {
					identifierTokenizer.tokenize(element.getNamed(), consumer);
				}
			}
		}
	};

	@Override
	public @NotNull Tokenizer<?> getTokenizer(PsiElement element) {
		if (element instanceof EnigmaMappingComment) {
			return commentTokenizer;
		}

		if (element instanceof EnigmaMappingEntry) {
			return entryTokenizer;
		}

		return super.getTokenizer(element);
	}
}
