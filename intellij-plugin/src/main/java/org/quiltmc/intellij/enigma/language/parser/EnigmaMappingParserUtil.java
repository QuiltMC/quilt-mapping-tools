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

package org.quiltmc.intellij.enigma.language.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.parser.GeneratedParserUtilBase;

import static org.quiltmc.intellij.enigma.language.psi.EnigmaMappingTypes.*;

public class EnigmaMappingParserUtil extends GeneratedParserUtilBase {
	private static int getIndent(PsiBuilder builder) {
		ErrorState state = ErrorState.get(builder);
		int indent = 0;
		Frame frame = state.currentFrame;
		while (frame != null && frame.elementType != null) {
			indent++;
			frame = frame.parentFrame;
		}
		return indent;
	}

	/**
	 * Parses a specific number of indentation tabs given by the number of parent elements for the current state.
	 */
	public static boolean parseIndent(PsiBuilder builder, int level) {
		if (!recursion_guard_(builder, level, "parseIndent")) return false;
		if (!nextTokenIs(builder, TAB)) return false;
		boolean result = false;
		PsiBuilder.Marker marker = enter_section_(builder);
		int indent = getIndent(builder);
		for (int i = 0; i < indent; i++) {
			result = consumeToken(builder, TAB);
			if (!result) {
				break;
			}
		}
		exit_section_(builder, marker, null, result);
		return result;
	}
}
