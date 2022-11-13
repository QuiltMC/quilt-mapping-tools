package org.quiltmc.intellij.enigma.language;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import static org.quiltmc.intellij.enigma.language.psi.EnigmaMappingTypes.*;

%%

%{
  public _EnigmaMappingLexer() {
    this((java.io.Reader)null);
  }
%}

%public
%class _EnigmaMappingLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

EOL=\R
WHITE_SPACE=\s+

NEW_LINE=\r?\n
WHITE_SPACE=[ \t]+

%%
<YYINITIAL> {
  {WHITE_SPACE}      { return WHITE_SPACE; }

  "CLASS"            { return CLASS_KEYWORD; }
  "FIELD"            { return FIELD_KEYWORD; }
  "METHOD"           { return METHOD_KEYWORD; }
  "ARG"              { return ARG_KEYWORD; }
  "COMMENT"          { return COMMENT_KEYWORD; }

  {NEW_LINE}         { return NEW_LINE; }
  {WHITE_SPACE}      { return WHITE_SPACE; }

}

[^] { return BAD_CHARACTER; }
