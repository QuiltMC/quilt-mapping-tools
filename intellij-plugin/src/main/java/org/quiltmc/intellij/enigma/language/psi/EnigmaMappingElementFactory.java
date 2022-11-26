package org.quiltmc.intellij.enigma.language.psi;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.util.PsiTreeUtil;
import org.quiltmc.intellij.enigma.language.EnigmaMappingLanguage;

public class EnigmaMappingElementFactory {
	private static PsiFile createFile(Project project, String text) {
		return PsiFileFactory.getInstance(project).createFileFromText("dummy.mapping", EnigmaMappingLanguage.INSTANCE, text, false, false);
	}

	public static EnigmaMappingClassName createClassName(Project project, String text) {
		return PsiTreeUtil.getChildOfType(createFile(project, "CLASS " + text), EnigmaMappingClassName.class);
	}

	private static EnigmaMappingClazz createDummyClazz(Project project, String text) {
		return PsiTreeUtil.getChildOfType(createFile(project, "CLASS dummy\n" + text), EnigmaMappingClazz.class);
	}

	public static EnigmaMappingIdentifierName createIdentifierName(Project project, String name) {
		return PsiTreeUtil.findChildOfType(createDummyClazz(project, "\tFIELD " + name), EnigmaMappingIdentifierName.class);
	}
}
