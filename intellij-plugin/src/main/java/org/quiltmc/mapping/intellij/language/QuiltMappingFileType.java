package org.quiltmc.mapping.intellij.language;

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.mapping.intellij.QuiltMappingIcons;

import javax.swing.Icon;

public class QuiltMappingFileType extends LanguageFileType {
	public static final QuiltMappingFileType INSTANCE = new QuiltMappingFileType();

	private QuiltMappingFileType() {
		super(QuiltMappingLanguage.INSTANCE);
	}

	@NotNull
	@Override
	public String getName() {
		return "Quilt Mapping";
	}

	@NotNull
	@Override
	public String getDescription() {
		return "Quilt Mapping file";
	}

	@NotNull
	@Override
	public String getDefaultExtension() {
		return "quiltmapping";
	}

	@Nullable
	@Override
	public Icon getIcon() {
		return QuiltMappingIcons.FILE;
	}
}
