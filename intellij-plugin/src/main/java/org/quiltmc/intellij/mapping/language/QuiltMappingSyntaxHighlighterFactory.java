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

import com.intellij.json.highlighting.JsonSyntaxHighlighterFactory;
import com.intellij.json.json5.Json5Lexer;
import com.intellij.lexer.Lexer;
import org.jetbrains.annotations.NotNull;

public class QuiltMappingSyntaxHighlighterFactory extends JsonSyntaxHighlighterFactory {
	@Override
	protected @NotNull Lexer getLexer() {
		return new Json5Lexer();
	}

	@Override
	protected boolean isCanEscapeEol() {
		return true;
	}
}
