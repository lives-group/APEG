grammar APEG;

@parser::header
{
    package apeg.parse;
    
    import apeg.parse.ast.*;
    
    import java.util.List;
    import java.util.ArrayList;
}

@parser::members
{
	AstFactory factory;
	
	public APEGParser(AstFactory factory, TokenStream input) {
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

grammarDef returns[apeg.parse.ast.GrammarNode gram]:
  'apeg' ID ';' option header functions rules
  {$gram = factory.newGrammar($ID.text, $option.list, $header.h,
  	                  $rules.list, $functions.func, $functions.func_sources);
  }
;

/***
 *  Option Section
 ***/

option returns[List<apeg.parse.ast.GrammarNode.GrammarOption> list]:
   {$list = new ArrayList<apeg.parse.ast.GrammarNode.GrammarOption>();}
   'options' '{' (r=grammar_opt {$list.add($r.opt);} ';')* '}'
  |
   {$list = new ArrayList<apeg.parse.ast.GrammarNode.GrammarOption>();}
;

grammar_opt returns[apeg.parse.ast.GrammarNode.GrammarOption opt]:
  'isAdaptable' '=' 
   ('true' {$opt = apeg.parse.ast.GrammarNode.GrammarOption.ADAPTABLE;}
  | 'false' {$opt = apeg.parse.ast.GrammarNode.GrammarOption.NO_ADAPTABLE;}
  )
 |
  'memoize' '='
   ('true' {$opt = apeg.parse.ast.GrammarNode.GrammarOption.MEMOIZE;}
  | 'false' {$opt = apeg.parse.ast.GrammarNode.GrammarOption.NO_MEMOIZE;}
  )
 |
  'envSemantics' '='
   ('simple' {$opt = apeg.parse.ast.GrammarNode.GrammarOption.SIMPLE_ENV_SEMANTICS;}
  | 'discardChangesWhenFail'
         {$opt = apeg.parse.ast.GrammarNode.GrammarOption.USUAL_SEMANTICS;}
  )
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
 * Functions Declaration
 ***/
 
functions returns[List<String> func, List<String> func_sources]:
   {$func = new ArrayList<String>();}
   {$func_sources = new ArrayList<String>();}
   'functions' (f=ID {$func.add($f.text);})*
   'from' '{' (s=ID {$func_sources.add($s.text);})+ '}' ';'
  |
   {$func = new ArrayList<String>();}
   {$func_sources = new ArrayList<String>();}
;

/***
 * Grammar Rule Section
 ***/

rules returns[List<apeg.parse.ast.RuleNode> list]:
   {$list = new ArrayList<apeg.parse.ast.RuleNode>();}
   (r=production {$list.add($r.rule);})+
;

// A definition of an APEG rule
production returns[apeg.parse.ast.RuleNode rule]:
   annotation ID optDecls optReturn optLocals ':' peg_expr ';'
   {$rule = factory.newRule($ID.text, $annotation.anno, $optDecls.list,
   	                        $optReturn.list, $peg_expr.peg);
   }
  |
   ID optDecls optReturn optLocals ':' peg_expr ';'
   {$rule = factory.newRule($ID.text, apeg.parse.ast.RuleNode.Annotation.NONE,
   	                        $optDecls.list, $optReturn.list, $peg_expr.peg);
   }
;

annotation returns[apeg.parse.ast.RuleNode.Annotation anno]:
   // to use together with the option memoize=false
   '@memoize' {$anno = apeg.parse.ast.RuleNode.Annotation.MEMOIZE;}
  |
   // to use together with the option memoize=true
   '@transient' {$anno = apeg.parse.ast.RuleNode.Annotation.TRANSIENT;}
  ;

/***
 * Attributes Definition Section
 ***/

// This rule defines the list of inherited attributes
optDecls returns[List<VarDeclarationNode> list]:
   decls {$list = $decls.list;}
  |
   {$list = new ArrayList<VarDeclarationNode>();}
;

// This rule defines the list of synthesized attributes
optReturn returns[List<VarDeclarationNode> list]:
   'returns' decls {$list = $decls.list;}
  |
   {$list = new ArrayList<VarDeclarationNode>();}
;

optLocals returns[List<VarDeclarationNode> list]:
   'locals' decls {$list = $decls.list;}
  |
   {$list = new ArrayList<VarDeclarationNode>();}
;

// This rule defines the lists of all attributes
decls returns[List<VarDeclarationNode> list]:
  '[' {$list = new ArrayList<VarDeclarationNode>();} 
  v1=varDecl {$list.add($v1.var);} (',' v2=varDecl {$list.add($v2.var);})* ']'
;

varDecl returns[VarDeclarationNode var]:
  type ID {$var = factory.newVarDeclaration($ID.text, $type.tp);}
;

type returns[TypeNode tp]:
  INT_TYPE {$tp = factory.newIntType();}
 |
  FLOAT_TYPE {$tp = factory.newFloatType();}
 |
  BOOLEAN_TYPE {$tp = factory.newBooleanType();}
 |
  STRING_TYPE {$tp = factory.newStringType();}
 |
  GRAMMAR_TYPE {$tp = factory.newGrammarType();}
 |
  RULE_TYPE {$tp = factory.newRuleType();}
 |
  ID {$tp = factory.newType($ID.text);}
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
   '!' factor {$exp = factory.newNotExpr($factor.exp);}
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
GRAMMAR_TYPE: 'grammar';
RULE_TYPE: 'rule';

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

