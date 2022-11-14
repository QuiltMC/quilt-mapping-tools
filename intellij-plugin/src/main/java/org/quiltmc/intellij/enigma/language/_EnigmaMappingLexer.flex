package org.quiltmc.intellij.enigma.language;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import it.unimi.dsi.fastutil.ints.IntArrayList;

import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import static org.quiltmc.intellij.enigma.language.psi.EnigmaMappingTypes.*;

%%

%{
  private final IntArrayList states = new IntArrayList(10);

  public _EnigmaMappingLexer() {
    this((java.io.Reader)null);
  }

  private void yypushstate(int state) {
     states.add(yystate());
     yybegin(state);
  }

  private void yypopstate() {
      int state = states.removeInt(states.size() - 1);
      yybegin(state);
  }
%}

%public
%class _EnigmaMappingLexer
%implements FlexLexer
%function advance
%type IElementType
%state CLASS_DEF
%state ENTRY_DEF
%state ARG_DEF
%state IN_COMMENT
%unicode

EOL=\R
WHITE_SPACE=\s+

NEW_LINE=\r?\n
WHITE_SPACE=[ ]+
TAB=\t
IDENTIFIER=[^\r\n\t /]+
NUMBER=\d+
COMMENT_TEXT=[^\r\n\t]+

%%
<YYINITIAL> {
  {WHITE_SPACE}      { return WHITE_SPACE; }

  "CLASS"            { yypushstate(CLASS_DEF); return CLASS_KEYWORD; }
  "FIELD"            { yypushstate(ENTRY_DEF); return FIELD_KEYWORD; }
  "METHOD"           { yypushstate(ENTRY_DEF); return METHOD_KEYWORD; }
  "ARG"              { yypushstate(ARG_DEF); return ARG_KEYWORD; }
  "COMMENT"          { yypushstate(IN_COMMENT); return COMMENT_KEYWORD; }

  {NEW_LINE}         { return NEW_LINE; }
  {WHITE_SPACE}      { return WHITE_SPACE; }
  {TAB}              { return TAB; }
}

<CLASS_DEF> {
  {WHITE_SPACE}      { return WHITE_SPACE; }

  "/"                { return PACKAGE_SEPARATOR; }

  {NEW_LINE}         { yypopstate(); return NEW_LINE; }
  {IDENTIFIER}       { return IDENTIFIER; }
}

<ENTRY_DEF> {
  {WHITE_SPACE}          { return WHITE_SPACE; }

  [^ ]/[^\r\n\t ]*\r?\n  { return DESCRIPTOR; }
  {NEW_LINE}             { yypopstate(); return NEW_LINE; }
  {IDENTIFIER}           { return IDENTIFIER; }
}

<ARG_DEF> {
  {WHITE_SPACE}      { return WHITE_SPACE; }

  {NEW_LINE}         { yypopstate(); return NEW_LINE; }
  {NUMBER}           { return NUMBER; }
  {IDENTIFIER}       { return IDENTIFIER; }
}

<IN_COMMENT> {
  {NEW_LINE}          { yypopstate(); return NEW_LINE; }
  {COMMENT_TEXT}      { return COMMENT_TEXT; }
}

[^] { return BAD_CHARACTER; }
