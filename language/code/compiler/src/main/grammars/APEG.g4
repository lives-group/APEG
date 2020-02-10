grammar APEG;

@parser::header
{
    package apeg.parse;
    
    import apeg.ast.*;
    
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

grammarDef returns[apeg.ast.Grammar gram]:
  'apeg' ID ';' option header rules
  /*{$gram = factory.newGrammar($ID.text, $option.list, $header.h,
  	                  $rules.list, $functions.func, $functions.func_sources);
  }*/
;

/***
 *  Option Section
 ***/

option returns[apeg.ast.Grammar.GrammarOption opt]:
   {$opt = new apeg.ast.Grammar.GrammarOption();}
   'options' '{' (r=grammar_opt[$opt] {$opt = $r.result;} ';')* '}'
  |
   {$opt = new apeg.ast.Grammar.GrammarOption();}
;

grammar_opt[apeg.ast.Grammar.GrammarNode opt] returns[apeg.ast.Grammar.GrammarNode result]:
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

rules returns[List<apeg.ast.RulePEG> list]:
   /*{$list = new ArrayList<apeg.ast.RulePEG>();}*/
   (r=production /*{$list.add($r.rule);}*/)+
;

// A definition of an APEG rule
production returns[apeg.ast.RulePEG rule]:
   annotation ID optDecls optReturn ':' peg_expr ';'
   /*{$rule = factory.newRule($ID.text, $annotation.anno, $optDecls.list,
   	                        $optReturn.list, $peg_expr.peg);
   }*/
  |
   ID optDecls optReturn ':' peg_expr ';'
   /*{$rule = factory.newRule($ID.text, apeg.ast.RulePEG.Annotation.NONE,
   	                        $optDecls.list, $optReturn.list, $peg_expr.peg);
   }*/
;

annotation returns[apeg.ast.RulePEG.Annotation anno]:
   // to use together with the option memoize disabled
   '@memoize' /*{$anno = apeg.ast.RulePEG.Annotation.MEMOIZE;}*/
  |
   // to use together with the option memoize
   '@transient' /*{$anno = apeg.parse.ast.RulePEG.Annotation.TRANSIENT;}*/
  ;

/***
 * Attributes Definition Section
 ***/

// This rule defines the list of inherited attributes
optDecls returns[List<Pair<apeg.ast.types.Type, String>> list]:
   decls /*{$list = $decls.list;}*/
  |
   /*{$list = new ArrayList<Pair<apeg.ast.types.Type, String>>();}*/
;

// This rule defines the list of synthesized attributes
optReturn returns[List<apeg.ast.expr.Expr> list]:
   'returns' decls /*{$list = $decls.list;}*/
  |
   /*{$list = new ArrayList<apeg.ast.expr.Expr>();}*/
;

// This rule defines the lists of all attributes
decls returns[List<Pair<apeg.ast.types.Type, String>> list]:
  '[' /*{$list = new ArrayList<Pair<apeg.ast.types.Type, String>>();} */
  v1=varDecl /*{$list.add($v1.var);}*/ (',' v2=varDecl /*{$list.add($v2.var);}*/)* ']'
;

exprs returns[List<apeg.ast.expr.Expr> list] :
  '[' /*{$list = new ArrayListExpr>();} */
   e1=expr /*{$list.add($e1.expr);}*/ (',' e2=expr /*{$list.add($e2.expr);}*/)* ']'
;

varDecl returns[Pair<apeg.ast.types.Type, String> var]:
  type ID /*{$var = factory.newVarDeclaration($ID.text, $type.tp);}*/
;

type returns[apeg.ast.types.Type tp]:
  INT_TYPE /*{$tp = factory.newIntType();}*/
 |
  FLOAT_TYPE /*{$tp = factory.newFloatType();}*/
 |
  BOOLEAN_TYPE /*{$tp = factory.newBooleanType();}*/
 |
  STRING_TYPE /*{$tp = factory.newStringType();}*/
 |
  CHAR_TYPE /*{$tp = factory.newStringType();}*/
 |
  GRAMMAR_TYPE /*{$tp = factory.newGrammarType();}*/
 |
  LANGUAGE_TYPE /*{$tp = factory.newRuleType();}*/
 |
  MAP_TYPE /*{$tp = factory.newUserType($ID.text);}*/
 |
  META_TYPE /*{$tp = factory.newUserType($ID.text);}*/
;

/***
 * APEG Expressions
 ***/

// Definition of the right side of a APEG
// This rule defines that the CHOICE operator has the lowest precedence 
// The precedence of CHOICE operator is 1
// CHOICE is an associative operator. We decided for right association because it may be faster to interpret
peg_expr returns[PegNode peg]:
  peg_seq '/' e=peg_expr {$peg = factory.newChoicePeg($peg_seq.peg, $e.peg);}
 |
  peg_seq {$peg = $peg_seq.peg;}
;

// This rule defines a sequence operator: e1 e2 
// The precedence of sequence operator is 2
peg_seq returns[PegNode peg]: 
   p1=peg_capturetext {List<PegNode> l = new ArrayList<PegNode>(); l.add($p1.peg);}
   (p2=peg_capturetext {l.add($p2.peg);})+ {$peg = factory.newSequencePeg(l);}
  |
   peg_capturetext {$peg = $peg_capturetext.peg;}
  |
   {$peg = factory.newLambdaPeg();}// LAMBDA parsing expression
;

peg_capturetext returns[PegNode peg]:
   peg_unary_op {$peg = $peg_unary_op.peg;}
  |
   attribute_ref '=' peg_unary_op
    {$peg = factory.newBindPeg($attribute_ref.exp, $peg_unary_op.peg);}
;


// This rule defines the operators with precedence 4 and 3  
// e? (Optional with precedence 4
// e* (Zero-or-more with precedence 4)
// e+ (One-or-more with precedence 4)
// &e (And-predicate with precedence 3)
// !e (Not-predicate with precedence 3)
peg_unary_op returns[PegNode peg]:
   peg_factor '?' {$peg = factory.newOptionalPeg($peg_factor.peg);}
  | 
   peg_factor '*' {$peg = factory.newStarPeg($peg_factor.peg);}
  | 
   peg_factor '+' {$peg = factory.newPlusPeg($peg_factor.peg);}
  |
   peg_factor {$peg = $peg_factor.peg;}
  |
   '&' peg_factor {$peg = factory.newAndPeg($peg_factor.peg);}
  |
   '!' peg_factor {$peg = factory.newNotPeg($peg_factor.peg);}
  |
   '{?' condExpr '}' {$peg = factory.newConstraintPeg($condExpr.exp);}
  |
   '{' st1=assign
     {List<AssignmentNode> l = new ArrayList<AssignmentNode>();
      l.add($st1.stm);
     } (st2=assign {l.add($st2.stm);})*
   '}' {$peg = factory.newUpdatePeg(l);}
;

// This rule defines the other operators and basic expressions
// ' ' (Character with precedence 5)
// " " (Literal String with precedence 5)
// [ ] (Character class with precedence 5)
// . (Any character with precedence 5)
// (e) (Grouping with precedence 5)
// A<...> (non-terminal basic expression)
// \lambda (empty basic expression)
peg_factor returns[PegNode peg]:
   STRING_LITERAL { 
     String s = $STRING_LITERAL.text;
     s = s.substring(1, s.length()-1);
     $peg = factory.newLiteralPeg(s);
   }
  |
   ntcall {$peg = $ntcall.peg;}
  |
   '[' range_pair ']' {$peg = factory.newGroupPeg($range_pair.text);}
  |
   '.' {$peg = factory.newAnyPeg();}
  |
   '(' peg_expr ')' {$peg = $peg_expr.peg;}
;

ntcall returns[PegNode peg]:
   ID '<' actPars '>' {$peg = factory.newNonterminalPeg($ID.text, $actPars.list);}
  |
   ID {$peg = factory.newNonterminalPeg($ID.text, new ArrayList<ExprNode>());}
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

assign returns[AssignmentNode stm]:
  attribute_ref '=' expr ';'
     {$stm = factory.newAssignment($attribute_ref.exp, $expr.exp);}
;

expr returns[ExprNode exp]: condExpr {$exp = $condExpr.exp;};

condExpr returns[ExprNode exp]:
   or_cond {$exp = $or_cond.exp;}
 |
   e1=or_cond (equalityOp e2=or_cond
   	 {$exp = factory.newEqualityExpr($e1.exp, $e2.exp, $equalityOp.op);}
   )+
;

or_cond returns[ExprNode exp]:
   and_cond {$exp = $and_cond.exp;}
  |
   e1=and_cond (OP_OR e2=and_cond {$exp = factory.newOrExpr($e1.exp, $e2.exp);})+
;

and_cond returns[ExprNode exp]:
  bool_expr {$exp = $bool_expr.exp;}    
 |
  e1=bool_expr (OP_AND e2=bool_expr {$exp = factory.newAndExpr($e1.exp, $e2.exp);})+
;

bool_expr returns[ExprNode exp]:  
   aexpr {$exp = $aexpr.exp;}
 |
  e1=aexpr relOp e2=aexpr
   {$exp = factory.newBinaryExpr($e1.exp, $e2.exp, $relOp.op);}
;

aexpr returns[ExprNode exp]:
    termOptUnary {$exp = $termOptUnary.exp;}
  |
    termOptUnary (addOp term)+
     {$exp = factory.newBinaryExpr($termOptUnary.exp, $term.exp, $addOp.op);}
;

termOptUnary returns[ExprNode exp]:
   OP_SUB term {$exp = factory.newMinusExpr($term.exp);}
  |
   term {$exp = $term.exp;}
;
 
term returns[ExprNode exp]:
   factor {$exp = $factor.exp;}
  |
   e1=factor (mulOp e2=factor)+
    {$exp = factory.newBinaryExpr($e1.exp, $e2.exp, $mulOp.op);}
;

factor returns[ExprNode exp]:
   attrORfuncall {$exp = $attrORfuncall.exp;}
  |
   number {$exp = $number.exp;}
  |
   STRING_LITERAL
    { 
      String s = $STRING_LITERAL.text;
      s = s.substring(1, s.length()-1);
      $exp = factory.newStringExpr(s);
    }
  |
   '(' expr ')' {$exp = $expr.exp;}
  |
   '!' f=factor {$exp = factory.newNotExpr($f.exp);}
  |
   TRUE {$exp = factory.newBooleanExpr(true);}
  |
   FALSE {$exp = factory.newBooleanExpr(false);}
  |
   meta_peg {$exp = $meta_peg.exp;}
;

attrORfuncall returns[ExprNode exp]:
   ID '(' actPars ')' {$exp = factory.newCallExpr($ID.text, $actPars.list);}
  |
   attribute_ref {$exp = $attribute_ref.exp;}
;

attribute_ref returns[AttributeExprNode exp]:
  ID {$exp = factory.newAttributeExpr($ID.text);}
 |
  '$g' {$exp = factory.newAttributeGrammarExpr();}
;

number returns[ExprNode exp]:
   INT_NUMBER {$exp = factory.newIntExpr(Integer.parseInt($INT_NUMBER.text));}
  |
   REAL_NUMBER {$exp = factory.newFloatExpr(Double.parseDouble($REAL_NUMBER.text));}
;

actPars returns[List<ExprNode> list]:
   {$list = new ArrayList<ExprNode>();} e1=aexpr {$list.add($e1.exp);}
    (',' e2=aexpr {$list.add($e2.exp);})*
  |
    {$list = new ArrayList<ExprNode>();}
; 

equalityOp returns[EqualityExprNode.EqualityOperator op]:
  OP_NE {$op = EqualityExprNode.EqualityOperator.NE;}
 |
  OP_EQ {$op = EqualityExprNode.EqualityOperator.EQ;};

relOp returns[BinaryExprNode.Operator op]:
 
   OP_LT {$op = BinaryExprNode.Operator.LT;}
  |
   OP_GT {$op = BinaryExprNode.Operator.GT;}
  |
   OP_LE {$op = BinaryExprNode.Operator.LE;}
  |
   OP_GE {$op = BinaryExprNode.Operator.GE;}
;

addOp returns[BinaryExprNode.Operator op]:
   
   OP_SUB {$op = BinaryExprNode.Operator.SUB;}
   |
   OP_ADD {$op = BinaryExprNode.Operator.ADD;}
 
;

mulOp returns[BinaryExprNode.Operator op]:
   OP_MUL {$op = BinaryExprNode.Operator.MUL;}
  |
   OP_DIV {$op = BinaryExprNode.Operator.DIV;}
  |
   OP_MOD {$op = BinaryExprNode.Operator.MOD;}
;

/***
 * Rules for metaprogramming
 */

meta_peg returns[MetaPegExprNode exp]:
'@[' expr ']'
  {$exp = factory.newMetaPeg($expr.exp); }
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

