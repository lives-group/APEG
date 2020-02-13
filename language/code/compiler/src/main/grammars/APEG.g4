grammar APEG;

@parser::header
{
    package apeg.parse;
    
    import apeg.ast.*;
    import apeg.ast.expr.*;
    import apeg.ast.expr.operators.*;
    import apeg.ast.rules.*;
    import apeg.ast.types.*;
    
    import apeg.util.SymInfo;
    import apeg.util.Pair;
    
    import java.util.List;
    import java.util.ArrayList;
}

@parser::members
{
	ASTFactory factory;
	
	public APEGParser(ASTFactory factory, TokenStream input) {
		this(input);
		this.factory = factory;
	}	
}

@lexer::header
{
    package apeg.parse;
}

/***
 * The preamble of the grammar
 ***/

grammarDef returns[Grammar gram]:
  t='apeg' ID ';' option /*header*/ rules
  {$gram = factory.newGrammar(new SymInfo($t.line, $t.pos), $ID.text, $option.opt, $rules.list);
  }
;

/***
 *  Option Section
 ***/

option returns[Grammar.GrammarOption opt]:
   {$opt = new Grammar.GrammarOption();}
   'options' '{' (r=grammar_opt[$opt] {$opt = $r.result;} ';')* '}'
  |
   {$opt = new Grammar.GrammarOption();}
;

grammar_opt[Grammar.GrammarOption opt] returns[Grammar.GrammarOption result]:
  'isAdaptable' {$opt.adaptable = true; $result = $opt;}
 |
  'memoize' {$opt.memoize = true; $result = $opt;}
 |
  'noUsualChoiceSemantics' {$opt.usual_semantics = false; $result = $opt;}
;

/***
 * Code to be included at the begin of the generated code
 * It is ignored by the tool 
 */

header returns[String h]:
    'header' '{' r=h_text {$h = $r.text;} '}'
   |
    {$h = "";}
;

h_text:
  (.*?);

/***
 * Grammar Rule Section
 ***/

rules returns[List<RulePEG> list]:
   {$list = new ArrayList<RulePEG>();}
   (r=production {$list.add($r.rule);})+
;

// A definition of an APEG rule
production returns[RulePEG rule]:
   annotation ID optDecls optReturn ':' peg_expr ';'
   {$rule = factory.newRule(new SymInfo($ID.line, $ID.pos) ,$ID.text, $annotation.anno, $optDecls.list,
   	                        $optReturn.list, $peg_expr.peg);
   }
  |
   ID optDecls optReturn ':' peg_expr ';'
   {$rule = factory.newRule(new SymInfo($ID.line, $ID.pos), $ID.text, RulePEG.Annotation.NONE,
   	                        $optDecls.list, $optReturn.list, $peg_expr.peg);
   }
;

annotation returns[RulePEG.Annotation anno]:
   // to use together with the option memoize disabled
   '@memoize' {$anno = RulePEG.Annotation.MEMOIZE;}
  |
   // to use together with the option memoize
   '@transient' {$anno = RulePEG.Annotation.TRANSIENT;}
  ;

/***
 * Attributes Definition Section
 ***/

// This rule defines the list of inherited attributes
optDecls returns[List<Pair<Type, String>> list]:
   decls {$list = $decls.list;}
  |
   {$list = new ArrayList<Pair<Type, String>>();}
;

// This rule defines the list of synthesized attributes
optReturn returns[List<Expr> list]:
   'returns' exprs {$list = $exprs.list;}
  |
   {$list = new ArrayList<Expr>();}
;

// This rule defines the lists of all attributes
decls returns[List<Pair<Type, String>> list]:
  '[' {$list = new ArrayList<Pair<Type, String>>();}
  v1=varDecl {$list.add($v1.var);} (',' v2=varDecl {$list.add($v2.var);})* ']'
;

exprs returns[List<Expr> list] :
  '[' {$list = new ArrayList<Expr>();}
   e1=expr {$list.add($e1.exp);} (',' e2=expr {$list.add($e2.exp);})* ']'
;

varDecl returns[Pair<Type, String> var]:
  type ID {$var = new Pair<Type, String>($type.tp, $ID.text);}
;

type returns[Type tp]:
  INT_TYPE {$tp = factory.newIntType(new SymInfo($INT_TYPE.line, $INT_TYPE.pos));}
 |
  FLOAT_TYPE {$tp = factory.newFloatType(new SymInfo($FLOAT_TYPE.line, $FLOAT_TYPE.pos));}
 |
  BOOLEAN_TYPE {$tp = factory.newBooleanType(new SymInfo($BOOLEAN_TYPE.line, $BOOLEAN_TYPE.pos));}
 |
  STRING_TYPE {$tp = factory.newStringType(new SymInfo($STRING_TYPE.line, $STRING_TYPE.pos));}
 |
  CHAR_TYPE {$tp = factory.newCharType(new SymInfo($CHAR_TYPE.line, $CHAR_TYPE.pos));}
 |
  GRAMMAR_TYPE {$tp = factory.newGrammarType(new SymInfo($GRAMMAR_TYPE.line, $GRAMMAR_TYPE.pos));}
 |
  LANGUAGE_TYPE {$tp = factory.newLangType(new SymInfo($LANGUAGE_TYPE.line, $LANGUAGE_TYPE.pos));}
 |
  MAP_TYPE '(' type ')' {$tp = factory.newMapType(new SymInfo($MAP_TYPE.line, $MAP_TYPE.pos) , $type.tp);}
 |
  META_TYPE {$tp = factory.newMetaType(new SymInfo($META_TYPE.line, $META_TYPE.pos));}
;

/***
 * APEG Expressions
 ***/

// Definition of the right side of a APEG
// This rule defines that the CHOICE operator has the lowest precedence 
// The precedence of CHOICE operator is 1
// CHOICE is an associative operator. We decided for right association because it may be faster to interpret
peg_expr returns[APEG peg]:
  peg_seq '/' e=peg_expr {$peg = factory.newChoicePEG($peg_seq.peg.getSymInfo(), $peg_seq.peg, $e.peg);}
 |
  peg_seq {$peg = $peg_seq.peg;}
;

// This rule defines a sequence operator: e1 e2 
// The precedence of sequence operator is 2
peg_seq returns[APEG peg]: 
   p1=peg_capturetext {List<APEG> l = new ArrayList<APEG>(); l.add($p1.peg);}
   (p2=peg_capturetext {l.add($p2.peg);})+ {$peg = factory.newSequencePEG($p1.peg.getSymInfo(), (APEG[]) l.toArray());}
  |
   peg_capturetext {$peg = $peg_capturetext.peg;}
  |
   {$peg = factory.newLambdaPEG(new SymInfo(_ctx.start.getLine(), _ctx.start.getCharPositionInLine()));}/ // LAMBDA parsing expression
;

peg_capturetext returns[APEG peg]:
   peg_unary_op {$peg = $peg_unary_op.peg;}
  |
   attribute_ref '=' peg_unary_op {$peg = factory.newBindPEG($attribute_ref.exp.getSymInfo(), $attribute_ref.exp, $peg_unary_op.peg);}
;


// This rule defines the operators with precedence 4 and 3  
// e? (Optional with precedence 4
// e* (Zero-or-more with precedence 4)
// e+ (One-or-more with precedence 4)
// &e (And-predicate with precedence 3)
// !e (Not-predicate with precedence 3)
peg_unary_op returns[APEG peg]:
   peg_factor '?' {$peg = factory.newOptionalPEG($peg_factor.peg.getSymInfo(), $peg_factor.peg);}
  | 
   peg_factor '*' {$peg = factory.newStarPEG($peg_factor.peg.getSymInfo(), $peg_factor.peg);}
  | 
   peg_factor '+' {$peg = factory.newPositiveKleenePEG($peg_factor.peg.getSymInfo(), $peg_factor.peg);}
  |
   peg_factor {$peg = $peg_factor.peg;}
  |
   t='&' peg_factor {$peg = factory.newAndPEG(new SymInfo($t.line, $t.pos), $peg_factor.peg);}
  |
   t='!' peg_factor {$peg = factory.newNotPEG(new SymInfo($t.line, $t.pos), $peg_factor.peg);}
  |
   t='{?' condExpr '}' {$peg = factory.newConstraintPEG(new SymInfo($t.line, $t.pos), $condExpr.exp);}
  |
   t='{' st1=assign
     {List<Pair<Attribute, Expr>> l = new ArrayList<Pair<Attribute, Expr>>();
      l.add($st1.stm);
     } (st2=assign {l.add($st2.stm);})*
   '}' {$peg = factory.newUpdatePEG(new SymInfo($t.line, $t.pos), l);}
;

// This rule defines the other operators and basic expressions
// ' ' (Character with precedence 5)
// " " (Literal String with precedence 5)
// [ ] (Character class with precedence 5)
// . (Any character with precedence 5)
// (e) (Grouping with precedence 5)
// A<...> (non-terminal basic expression)
// \lambda (empty basic expression)
peg_factor returns[APEG peg]:
   STRING_LITERAL { 
     String s = $STRING_LITERAL.text;
     s = s.substring(1, s.length()-1);
     $peg = factory.newLiteralPEG(new SymInfo($STRING_LITERAL.line, $STRING_LITERAL.pos), s);
   }
  |
   ntcall {$peg = $ntcall.peg;}
  |
   '[' range_pair ']' /*{$peg = factory.newGroupPeg($range_pair.text);}*/
  |
   t='.' {$peg = factory.newAnyPEG(new SymInfo($t.line, $t.pos));}
  |
   '(' peg_expr ')' {$peg = $peg_expr.peg;}
;

ntcall returns[APEG peg]:
   ID '<' actPars '>' {$peg = factory.newNonterminalPEG(new SymInfo($ID.line, $ID.pos), $ID.text, $actPars.list);}
  |
   ID {$peg = factory.newNonterminalPEG(new SymInfo($ID.line, $ID.pos), $ID.text, new ArrayList<Expr>());}
;

range_pair:
  single_pair
 |
  single_pair ('-' single_pair)+
;

/*range_pair:
   t1=LETTER '-' t2=LETTER -> ^(DOUBLE_PAIR $t1 $t2)
  |
   t1=DIGIT '-' t2=DIGIT -> ^(DOUBLE_PAIR $t1 $t2)
  |
   t1=ESC '-' t2=ESC -> ^(DOUBLE_PAIR $t1 $t2)
  |
   LETTER | DIGIT | ESC
  ;*/

single_pair: ID | INT_NUMBER | ESC;

/***
 * Constraint and Update Expressions
 ***/

assign returns[Pair<Attribute, Expr> stm]:
  attribute_ref '=' expr ';'
     /*{$stm = factory.newAssignment($attribute_ref.exp, $expr.exp);}*/
;

expr returns[Expr exp]: condExpr {$exp = $condExpr.exp;};

condExpr returns[Expr exp]:
   or_cond {$exp = $or_cond.exp;}
 |
   e1=or_cond (equalityOp e2=or_cond
   	 /*{$exp = factory.newEqualityExpr($e1.exp, $e2.exp, $equalityOp.op);}*/
   )+
;

or_cond returns[Expr exp]:
   and_cond {$exp = $and_cond.exp;}
  |
   e1=and_cond (OP_OR e2=and_cond /*{$exp = factory.newOrExpr($e1.exp, $e2.exp);}*/)+
;

and_cond returns[Expr exp]:
  bool_expr {$exp = $bool_expr.exp;}    
 |
  e1=bool_expr (OP_AND e2=bool_expr /*{$exp = factory.newAndExpr($e1.exp, $e2.exp);}*/)+
;

bool_expr returns[Expr exp]:  
   aexpr {$exp = $aexpr.exp;}
 |
  e1=aexpr relOp e2=aexpr
   /*{$exp = factory.newBinaryExpr($e1.exp, $e2.exp, $relOp.op);}*/
;

aexpr returns[Expr exp]:
    termOptUnary {$exp = $termOptUnary.exp;}
  |
    termOptUnary (addOp term)+
     /*{$exp = factory.newBinaryExpr($termOptUnary.exp, $term.exp, $addOp.op);}*/
;

termOptUnary returns[Expr exp]:
   OP_SUB term /*{$exp = factory.newMinusExpr($term.exp);}*/
  |
   term {$exp = $term.exp;}
;
 
term returns[Expr exp]:
   factor {$exp = $factor.exp;}
  |
   e1=factor (mulOp e2=factor)+
    /*{$exp = factory.newBinaryExpr($e1.exp, $e2.exp, $mulOp.op);}*/
;

factor returns[Expr exp]:
   attribute_ref {$exp = $attribute_ref.exp;}
  |
   number /*{$exp = $number.exp;}*/
  |
   STRING_LITERAL
    { 
      String s = $STRING_LITERAL.text;
      s = s.substring(1, s.length()-1);
      /*$exp = factory.newStringExpr(s);*/
    }
  |
   '(' expr ')' {$exp = $expr.exp;}
  |
   '!' f=factor /*{$exp = factory.newNotExpr($f.exp);}*/
  |
   TRUE /*{$exp = factory.newBooleanExpr(true);}*/
  |
   FALSE /*{$exp = factory.newBooleanExpr(false);}*/
  |
   meta_peg /*{$exp = $meta_peg.exp;}*/
;

attribute_ref returns[Attribute exp]:
  ID /*{$exp = factory.newAttributeExpr($ID.text);}*/
 |
  '$g' /*{$exp = factory.newAttributeGrammarExpr();}*/
;

number returns[Expr exp]:
   INT_NUMBER /*{$exp = factory.newIntExpr(Integer.parseInt($INT_NUMBER.text));}*/
  |
   REAL_NUMBER /*{$exp = factory.newFloatExpr(Double.parseDouble($REAL_NUMBER.text));}*/
;

actPars returns[List<Expr> list]:
   /*{$list = new ArrayList<Expr>();}*/ e1=aexpr /*{$list.add($e1.exp);}*/
    (',' e2=aexpr /*{$list.add($e2.exp);}*/)*
  |
    /*{$list = new ArrayList<Expr>();}*/
; 

equalityOp returns[int op]:
  OP_NE /*{$op = EqualityExprNode.EqualityOperator.NE;}*/
 |
  OP_EQ /*{$op = EqualityExprNode.EqualityOperator.EQ;}*/
;

relOp returns[int op]:
 
   OP_LT /*{$op = BinaryExprNode.Operator.LT;}*/
  |
   OP_GT /*{$op = BinaryExprNode.Operator.GT;}*/
  |
   OP_LE /*{$op = BinaryExprNode.Operator.LE;}*/
  |
   OP_GE /*{$op = BinaryExprNode.Operator.GE;}*/
;

addOp returns[boolean op]:
   
   OP_SUB /*{$op = BinaryExprNode.Operator.SUB;}*/
   |
   OP_ADD /*{$op = BinaryExprNode.Operator.ADD;}*/
 
;

mulOp returns[int op]:
   OP_MUL /*{$op = BinaryExprNode.Operator.MUL;}*/
  |
   OP_DIV /*{$op = BinaryExprNode.Operator.DIV;}*/
  |
   OP_MOD /*{$op = BinaryExprNode.Operator.MOD;}*/
;

/***
 * Rules for metaprogramming
 */

meta_peg returns[MetaASTNode exp]:
'@[' expr ']'
  /*{$exp = factory.newMetaPeg($expr.exp); }*/
;

/***
 * Lexical
 */

//APEG types
INT_TYPE: 'int';
FLOAT_TYPE: 'float';
BOOLEAN_TYPE: 'boolean';
STRING_TYPE: 'string';
CHAR_TYPE: 'char';
GRAMMAR_TYPE: 'grammar';
LANGUAGE_TYPE: 'language';
MAP_TYPE: 'map';
META_TYPE: 'meta';

// Operators
OP_AND : '&&';
OP_OR : '||';
OP_NOT : '!';
OP_EQ : '==';
OP_LT : '<';
OP_GT : '>';
OP_LE : '<=';
OP_GE : '>=';
OP_NE : '!=';
OP_ADD : '+';
OP_SUB : '-';
OP_MUL : '*';
OP_DIV : '/';
OP_MOD : '%';
STRING_LITERAL: '\'' LITERAL_CHAR* '\''
 /*{
    String s = $text;
    s = s.substring(1, s.length()-1);
    s = formatString(s);
    setText(s);
 }*/
;
fragment LITERAL_CHAR
  : ESC
  | ~('\''|'\\')
  ;
ESC : '\\'
    ( 'n'
    | 'r'
    | 't'
    | 'b'
    | 'f'
    | '"'
    | '\''
    | '\\'
    | 'u' XDIGIT XDIGIT XDIGIT XDIGIT
    | . // unknown, leave as it is
    )
  ;
fragment XDIGIT :
    '0' .. '9'
  | 'a' .. 'f'
  | 'A' .. 'F'
  ;
fragment LETTER : 'a'..'z' | 'A'..'Z';
fragment DIGIT : '0'..'9';
TRUE : 'true';
FALSE : 'false';
ID : LETTER (LETTER | DIGIT | '_')*;
INT_NUMBER : DIGIT+;
REAL_NUMBER :
  DIGIT+ ('.' DIGIT*)? EXPONENT?
  |
  '.' DIGIT+ EXPONENT?
  ;
fragment EXPONENT : ('e'|'E') ('+'|'-')? DIGIT+ ;
WS : (' ' | '\t' | '\r' | '\n') { skip(); } ;
COMMENT : '/*' .* '*/' { skip(); } ;
LINE_COMMENT : '//' ~('\n'|'\r')* '\r'? '\n' { skip(); } ;

