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


%%
<YYINITIAL> {
  {WHITE_SPACE}      { return WHITE_SPACE; }

  "CLASS"            { return CLASS_KEYWORD; }


}

[^] { return BAD_CHARACTER; }
