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

BINARY_NAME=[^ \t\n\x0B\f\r]+

%%
<YYINITIAL> {
  {WHITE_SPACE}        { return WHITE_SPACE; }

  "ACC:"               { return ACC_PREFIX; }
  "ACC:PRIVATE"        { return ACC_PRIVATE; }
  "ACC:PROTECTED"      { return ACC_PROTECTED; }
  "ACC:PUBLIC"         { return ACC_PUBLIC; }
  "CLASS"              { return CLASS_KEYWORD; }

  {BINARY_NAME}        { return BINARY_NAME; }

}

[^] { return BAD_CHARACTER; }
