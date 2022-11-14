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
import com.intellij.openapi.util.Comparing;

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

	// field-type
	public static boolean parseFieldDescriptor(PsiBuilder builder, int level) {
		if (!recursion_guard_(builder, level, "parseFieldDescriptor")) return false;
		if (!nextTokenIs(builder, ANY)) return false;

		TokenChecker checker = new TokenChecker(builder.getTokenText());
		boolean result;
		PsiBuilder.Marker marker = enter_section_(builder, level, _NONE_, "<field descriptor>");
		result = parseFieldType(builder, level + 1, checker);
		if (result) consumeToken(builder, ANY);
		exit_section_(builder, level, marker, result, checker.pinned, null);

		return result;
	}

	// base-type | object-type | array-type
	public static boolean parseFieldType(PsiBuilder builder, int level, TokenChecker checker) {
		if (!recursion_guard_(builder, level, "parseFieldType")) return false;

		boolean result;
		result = parseBaseType(builder, level + 1, checker);
		if (!result) result = parseObjectType(builder, level + 1, checker);
		if (!result && !checker.pinned) result = parseArrayType(builder, level + 1, checker);

		return result || checker.pinned;
	}

	// 'B' | 'C' | 'D' | 'F' | 'I' | 'J' | 'S' | 'Z'
	public static boolean parseBaseType(PsiBuilder builder, int level, TokenChecker checker) {
		boolean result;
		switch (checker.substring(1)) {
			case "B":
			case "C":
			case "D":
			case "F":
			case "I":
			case "J":
			case "S":
			case "Z":
				result = true;
				checker.skip(1);
				break;
			default:
				result = false;
		}

		return result;
	}

	// 'L' binary-name ';'
	public static boolean parseObjectType(PsiBuilder builder, int level, TokenChecker checker) {
		boolean result;
		result = checker.nextMatches("L", true);
		checker.pin(result);
		result = checker.find(";");
		return result;
	}

	// '[' field-type
	public static boolean parseArrayType(PsiBuilder builder, int level, TokenChecker checker) {
		boolean result;
		result = checker.nextMatches("[", false);
		checker.pin(result);

		if (result) result = parseFieldType(builder, level + 1, checker);

		return result;
	}

	// '(' parameter-descriptor* ')' return-descriptor
	public static boolean parseMethodDescriptor(PsiBuilder builder, int level) {
		if (!recursion_guard_(builder, level, "parseMethodDescriptor")) return false;
		if (!nextTokenIs(builder, ANY)) return false;

		TokenChecker checker = new TokenChecker(builder.getTokenText());
		boolean result;
		PsiBuilder.Marker marker = enter_section_(builder, level, _NONE_, null, "<method_descriptor>");
		result = checker.nextMatches("(", false);
		checker.pin(result);

		while (result && !checker.substring(1).equals(")") && !checker.reachedEnd()) {
			result = parseFieldType(builder, level + 1, checker);
		}
		result = result && checker.nextMatches(")", false);
		result = result && parseReturnDescriptor(builder, level + 1, checker);
		if (result) consumeToken(builder, ANY);
		exit_section_(builder, level, marker, result, checker.pinned, null);

		return result || checker.pinned;
	}

	// field-type | void-descriptor
	public static boolean parseReturnDescriptor(PsiBuilder builder, int level, TokenChecker checker) {
		if (!recursion_guard_(builder, level, "parseReturnDescriptor")) return false;

		boolean result;
		result = parseFieldType(builder, level + 1, checker);
		if (!result) result = checker.nextMatches("V", true);

		return result;
	}

	public static class TokenChecker {
		final String text;
		int offset = 0;
		boolean pinned = false;

		public TokenChecker(String text) {
			if (text == null) this.text = "";
			else this.text = text;
		}

		public void pin(boolean pinned) {
			this.pinned |= pinned;
		}

		public String substring(int length) {
			return text.substring(offset, Math.min(offset + length, text.length()));
		}

		public boolean nextMatches(String text, boolean caseSensitive) {
			if (reachedEnd()) return false;
			String substring = substring(text.length());

			if (!Comparing.equal(substring, text, caseSensitive)) return false;

			offset += text.length();
			return true;
		}

		public void skip(int i) {
			int newOffset = offset + i;
			offset = Math.min(newOffset, text.length());
		}

		public boolean reachedEnd() {
			return offset >= text.length();
		}

		public boolean find(String text) {
			if (reachedEnd()) return false;

			int l = text.length();
			while (!substring(l).equals(text) && !reachedEnd()) {
				skip(l);
			}

			return nextMatches(text, true);
		}
	}
}
