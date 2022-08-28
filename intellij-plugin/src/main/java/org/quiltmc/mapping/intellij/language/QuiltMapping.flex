package org.quiltmc.mapping.intellij.language;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import static org.quiltmc.mapping.intellij.language.psi.QuiltMappingTypes.*;

%%

%{
  public QuiltMappingLexer() {
    this((java.io.Reader)null);
  }
%}

%public
%class QuiltMappingLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

EOL=\R
WHITE_SPACE=\s+

COMMENT="//".*
BLOCK_COMMENT="/"\*([^*]|\*+[^*/])*(\*+"/")?
DOUBLE_QUOTED_STRING=\"([^\"\n]|\\\n)*\"?
SINGLE_QUOTED_STRING='([^'\n]|\\\n)*'?
NUMBER=[-+]?0[xX]([A-Fa-f0-9]+)|[-+]?([0-9]+\.?[0-9]*|\.[0-9]+)|[-+]?[0-9]+[Ee][-+]?[0-9]+
IDENTIFIER=[A-z][A-z0-9_$]*
SPACE=[ \t\n\x0B\f\r]

%%
<YYINITIAL> {
  {WHITE_SPACE}               { return WHITE_SPACE; }

  "{"                         { return LCURLY; }
  "}"                         { return RCURLY; }
  "["                         { return LBRACKET; }
  "]"                         { return RBRACKET; }
  ","                         { return COMMA; }
  ":"                         { return COLON; }
  "true"                      { return TRUE; }
  "false"                     { return FALSE; }
  "null"                      { return NULL; }

  {COMMENT}                   { return COMMENT; }
  {BLOCK_COMMENT}             { return BLOCK_COMMENT; }
  {DOUBLE_QUOTED_STRING}      { return DOUBLE_QUOTED_STRING; }
  {SINGLE_QUOTED_STRING}      { return SINGLE_QUOTED_STRING; }
  {NUMBER}                    { return NUMBER; }
  {IDENTIFIER}                { return IDENTIFIER; }
  {SPACE}                     { return SPACE; }

}

[^] { return BAD_CHARACTER; }
