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
