package org.quiltmc.mapping.intellij.language;

import com.intellij.lang.Language;

public class QuiltMappingLanguage extends Language {
	public static final QuiltMappingLanguage INSTANCE = new QuiltMappingLanguage();

	private QuiltMappingLanguage() {
		super("QuiltMapping");
	}
}
