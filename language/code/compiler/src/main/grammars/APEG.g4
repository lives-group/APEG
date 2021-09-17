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
    import apeg.util.CharInterval;
    
    import java.util.List;
    import java.util.ArrayList;
}

@parser::members
{
	ASTFactory factory, factoryNormal, factoryMeta;
        boolean metaLevel;
        int levelCount;
	
	public APEGParser(ASTFactory factory, ASTFactory factoryMeta, TokenStream input) {
		this(input);
		this.factory = factory;
                this.factoryNormal = factory;
                this.factoryMeta = factoryMeta;
                this.metaLevel = false;
                this.levelCount = 0;
	}

	char getCharAt(String s, int i) {
	     char c = s.charAt(i);
	     switch(c) {
	       case '\\':
	         switch(s.charAt(++i)) {
		   case 'n':
		     return '\n';
		   case 'r':
		     return '\r';
		   case 't':
		     return '\t';
		   case 'b':
		     return '\b';
		   case 'f':
		     return '\f';
		   case '\\':
		     return '\\';
		   case '\'':
		     return '\'';
		   case 'u':
		     char[] chs = Character.toChars(Integer.valueOf(s.substring(i+1,i+5), 16));
		     return chs[0];
		 }
	       default:
	         return c;
	     }
	}

        CharInterval splitRange(String s) {
            int i = 0;
	    char init = getCharAt(s,i);
	    if(s.charAt(i) == '\\' && s.charAt(++i) == 'u') {
	        i += 4;
	    }
	    i += 3;
	    return new CharInterval(init, getCharAt(s,i));
        }

        private void enterMeta(){
            levelCount++;
            metaLevel = true;
            factory = factoryMeta;
        }

        private void exitMeta(){
            levelCount--;

            if(levelCount <= 0){
                metaLevel = false;
                factory = factoryNormal;
            }
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
  t='apeg' ID ';' option  rules
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

rules returns[List<RulePEG> list, Expr mrules]:
   {
    $list = new ArrayList<RulePEG>();
    ArrayList l = new ArrayList<Expr>();
   }
   (r=production {if(!metaLevel) $list.add($r.rule); else l.add($r.mrule);})+
   {if(metaLevel) $mrules = factory.newMetaGrammar($r.mrule.getSymInfo(), factory.newListExpr($r.mrule.getSymInfo(), l));}
;

// A definition of an APEG rule
production returns[RulePEG rule, MetaRulePEG mrule]:
   annotation ID optDecls optReturn ':' peg_expr ';'
   {$rule = factory.newRule(new SymInfo($ID.line, $ID.pos) ,$ID.text, $annotation.anno, $optDecls.list, $optReturn.list, $peg_expr.peg);}
  |
   ID optDecls optReturn ':' peg_expr ';'
   {if(!metaLevel) $rule = factory.newRule(new SymInfo($ID.line, $ID.pos), $ID.text, RulePEG.Annotation.NONE,
   	                        $optDecls.list, $optReturn.list, $peg_expr.peg);

    else $mrule = factory.newMetaRulePEG(
                           new SymInfo($ID.line, $ID.pos),
                           factoryNormal.newStringExpr(new SymInfo($ID.line, $ID.pos), $ID.text),
                           null,
                           factory.newListExpr(new SymInfo($ID.line, $ID.pos), $optDecls.mtypes),
                           factory.newListExpr(new SymInfo($ID.line, $ID.pos), $optDecls.minh),
                           factory.newListExpr(new SymInfo($ID.line, $ID.pos), $optReturn.msyn),
                           $peg_expr.mpeg);
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
optDecls returns[List<Pair<Type, String>> list, ArrayList<Expr> mtypes, ArrayList<Expr> minh]:
   decls {
     if(!metaLevel) $list = $decls.list;
     else{
       $mtypes = $decls.mtypes;
       $minh = $decls.minh;
     }
   }
  |
   {
    if(!metaLevel) $list = new ArrayList<Pair<Type, String>>();
    else {
        $mtypes = new ArrayList<Expr>();
        $minh = new ArrayList<Expr>();
    }
   }
;

// This rule defines the list of synthesized attributes
optReturn returns[List<Expr> list, ArrayList<Expr> msyn]:
   'returns' exprs {
     if(!metaLevel) $list = $exprs.list;
     else $msyn = new ArrayList($exprs.list);
   }
  |
   {
     if(!metaLevel) $list = new ArrayList<Expr>();
     else $msyn = new ArrayList();
   }
;

// This rule defines the lists of all attributes
decls returns[List<Pair<Type, String>> list, ArrayList<Expr> mtypes, ArrayList<Expr> minh]:
  '[' {
    $list = new ArrayList<Pair<Type, String>>();
    $mtypes = new ArrayList<Expr>();
    $minh = new ArrayList<Expr>();
  }
  v1=varDecl {
   if(!metaLevel)
     $list.add($v1.var);
   else{
     $mtypes.add($v1.mvar.getFirst());
     $minh.add($v1.mvar.getSecond());
   }
  }
  (',' v2=varDecl {
   if(!metaLevel)
     $list.add($v2.var);
   else{
     $mtypes.add($v2.mvar.getFirst());
     $minh.add($v2.mvar.getSecond());
   }
  })* ']'
;

exprs returns[List<Expr> list] :
   {$list = new ArrayList<Expr>();}
   e1=expr {$list.add($e1.exp);} (',' e2=expr {$list.add($e2.exp);})*
;

varDecl returns[Pair<Type, String> var, Pair<Expr, Expr> mvar]:
  type ID {
     if(!metaLevel) $var = new Pair<Type, String>($type.tp, $ID.text);
     else $mvar = new Pair<Expr, Expr>($type.mtp, factoryNormal.newStringExpr(new SymInfo($ID.line, $ID.pos), $ID.text));
  }
;

type returns[Type tp, Expr mtp]:
  INT_TYPE {
     if(!metaLevel) $tp = factory.newIntType(new SymInfo($INT_TYPE.line, $INT_TYPE.pos));
     else $mtp = factory.newMetaTyInt(new SymInfo($INT_TYPE.line, $INT_TYPE.pos));
  }
 |
  FLOAT_TYPE {
     if(!metaLevel) $tp = factory.newFloatType(new SymInfo($FLOAT_TYPE.line, $FLOAT_TYPE.pos));
     else $mtp = factory.newMetaTyFloat(new SymInfo($FLOAT_TYPE.line, $FLOAT_TYPE.pos));
  }
 |
  BOOLEAN_TYPE {
     if(!metaLevel) $tp = factory.newBooleanType(new SymInfo($BOOLEAN_TYPE.line, $BOOLEAN_TYPE.pos));
     else $mtp = factory.newMetaTyBool(new SymInfo($BOOLEAN_TYPE.line, $BOOLEAN_TYPE.pos));
  }
 |
  STRING_TYPE {
     if(!metaLevel) $tp = factory.newStringType(new SymInfo($STRING_TYPE.line, $STRING_TYPE.pos));
     else $mtp = factory.newMetaTyString(new SymInfo($STRING_TYPE.line, $STRING_TYPE.pos));
  }
 |
  CHAR_TYPE {
     if(!metaLevel) $tp = factory.newCharType(new SymInfo($CHAR_TYPE.line, $CHAR_TYPE.pos));
     else $mtp = factory.newMetaTyChar(new SymInfo($CHAR_TYPE.line, $CHAR_TYPE.pos));
  }
 |
  GRAMMAR_TYPE {
     if(!metaLevel) $tp = factory.newGrammarType(new SymInfo($GRAMMAR_TYPE.line, $GRAMMAR_TYPE.pos));
     else $mtp = factory.newMetaTyGrammar(new SymInfo($GRAMMAR_TYPE.line, $GRAMMAR_TYPE.pos));
  }
 |
  LANGUAGE_TYPE {
     if(!metaLevel) $tp = factory.newLangType(new SymInfo($LANGUAGE_TYPE.line, $LANGUAGE_TYPE.pos));
     else $mtp = factory.newMetaTyLang(new SymInfo($LANGUAGE_TYPE.line, $LANGUAGE_TYPE.pos));
  }
 |
  t='{' type '}' {
     if(!metaLevel) $tp = factory.newMapType(new SymInfo($t.line, $t.pos), $type.tp);
     else $mtp = factory.newMetaTyMap(new SymInfo($t.line, $t.pos), $type.mtp);
  }
 |
  t='[' type ']' {
     if(!metaLevel) $tp = factory.newListType(new SymInfo($t.line, $t.pos), $type.tp);
  }
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
peg_expr returns[APEG peg, Expr mpeg]:
  peg_seq t='/' e=peg_expr {
    if(!metaLevel) $peg = factory.newChoicePEG(new SymInfo($t.line, $t.pos), $peg_seq.peg, $e.peg);
    else $mpeg = factory.newMetaChoicePEG(new SymInfo($t.line, $t.pos), $peg_seq.mpeg, $e.mpeg);
  }
 |
  peg_seq {
    if(!metaLevel) $peg = $peg_seq.peg;
    else $mpeg = $peg_seq.mpeg;
  }
;

// This rule defines a sequence operator: e1 e2 
// The precedence of sequence operator is 2
peg_seq returns[APEG peg, Expr mpeg]: 
   p1=peg_capturetext {
      List<APEG> l1 = new ArrayList<APEG>();
      List<Expr> l2 = new ArrayList<Expr>();

      if(!metaLevel) l1.add($p1.peg);
      else l2.add($p1.mpeg);
   }
   (p2=peg_capturetext {if(!metaLevel) l1.add($p2.peg); else l2.add($p2.mpeg);})+
   {
      if(!metaLevel) $peg = factory.newSequencePEG($p1.peg.getSymInfo(), l1.toArray(new APEG[l1.size()]));
      else $mpeg = factory.newMetaSeqPEG($p1.mpeg.getSymInfo(), l2.toArray(new Expr[l2.size()]));
   }
  |
   peg_capturetext {
     if(!metaLevel) $peg = $peg_capturetext.peg;
     else $mpeg = $peg_capturetext.mpeg;
   }
  |
   '\u03bb' {$peg = factory.newLambdaPEG(new SymInfo(_ctx.start.getLine(), _ctx.start.getCharPositionInLine()));}/ // LAMBDA parsing expression
;

peg_capturetext returns[APEG peg, Expr mpeg]:
   peg_unary_op {
     if(!metaLevel) $peg = $peg_unary_op.peg;
     else $mpeg = $peg_unary_op.mpeg;
   }
  |
   attribute_ref t='=' peg_unary_op {
     if(!metaLevel) $peg = factory.newBindPEG(new SymInfo($t.line, $t.pos), $attribute_ref.exp, $peg_unary_op.peg);
     else $mpeg = factory.newMetaBindPEG(new SymInfo($t.line, $t.pos), $attribute_ref.mexp, $peg_unary_op.mpeg);
   }
;


// This rule defines the operators with precedence 4 and 3  
// e? (Optional with precedence 4
// e* (Zero-or-more with precedence 4)
// e+ (One-or-more with precedence 4)
// &e (And-predicate with precedence 3)
// !e (Not-predicate with precedence 3)
peg_unary_op returns[APEG peg, Expr mpeg]:
   peg_factor t= '?' {
   if(!metaLevel) $peg = factory.newOptionalPEG(new SymInfo($t.line, $t.pos), $peg_factor.peg);
   else $mpeg = factory.newMetaOptionalPEG(new SymInfo($t.line, $t.pos), $peg_factor.mpeg);}
  | 
   peg_factor t='*' {
   if(!metaLevel) $peg = factory.newStarPEG(new SymInfo($t.line, $t.pos), $peg_factor.peg);
   else $mpeg = factory.newMetaKleenePEG(new SymInfo($t.line, $t.pos), $peg_factor.mpeg);}
  | 
   peg_factor t='+' {
   if(!metaLevel) $peg = factory.newPositiveKleenePEG(new SymInfo($t.line, $t.pos), $peg_factor.peg);
   else $mpeg = factory.newMetaPKleene(new SymInfo($t.line, $t.pos), $peg_factor.mpeg);
   }
  |
   peg_factor {
   if(!metaLevel) $peg = $peg_factor.peg;
   else $mpeg = $peg_factor.mpeg; }
  |
   t='&' peg_factor {
   if(!metaLevel) $peg = factory.newAndPEG(new SymInfo($t.line, $t.pos), $peg_factor.peg);
   else $mpeg = factory.newMetaAndPEG(new SymInfo($t.line, $t.pos), $peg_factor.mpeg);}
  |
   t='!' peg_factor {
   if(!metaLevel) $peg = factory.newNotPEG(new SymInfo($t.line, $t.pos), $peg_factor.peg);
   else $mpeg = factory.newMetaNotPEG(new SymInfo($t.line, $t.pos), $peg_factor.mpeg);}
  |
   t='{?' expr '}' {
   if(!metaLevel) $peg = factory.newConstraintPEG(new SymInfo($t.line, $t.pos), $expr.exp);
   else $mpeg = factory.newMetaConstraintPEG(new SymInfo($t.line, $t.pos), $expr.exp);}
  |
   t='{' st1=assign
     {
      List<Pair<Attribute, Expr>> l = new ArrayList<Pair<Attribute, Expr>>();
      ArrayList<Expr> ml = new ArrayList<Expr>();

      if(!metaLevel) l.add($st1.stm);
      else {
        ml.add($st1.mstm.getFirst());
        ml.add($st1.mstm.getSecond());
      }
     }(st2=assign {if(!metaLevel) l.add($st2.stm);
      else {
        ml.add($st2.mstm.getFirst());
        ml.add($st2.mstm.getSecond());
      }
      })*
   '}' {
   if(!metaLevel) $peg = factory.newUpdatePEG(new SymInfo($t.line, $t.pos), l);
   else $mpeg = factory.newMetaUpdatePEG(new SymInfo($t.line, $t.pos), factory.newListExpr(new SymInfo($t.line, $t.pos), ml));}
;

// This rule defines the other operators and basic expressions
// ' ' (Literal string with precedence 5)
// [ ] (Character class with precedence 5)
// _ (Any character with precedence 5)
// (e) (Grouping with precedence 5)
// A<...> (non-terminal basic expression)
// \lambda (empty basic expression)
peg_factor returns[APEG peg, Expr mpeg]:
   STRING_LITERAL { 
     String s = $STRING_LITERAL.text;
     s = s.substring(1, s.length()-1);
     if(!metaLevel) $peg = factory.newLiteralPEG(new SymInfo($STRING_LITERAL.line, $STRING_LITERAL.pos), s);
     else $mpeg = factory.newMetaLitPEG(new SymInfo($STRING_LITERAL.line, $STRING_LITERAL.pos),
                                        factoryNormal.newStringExpr(new SymInfo($STRING_LITERAL.line, $STRING_LITERAL.pos), s));
   }
  |
   ntcall {
     if(!metaLevel) $peg = $ntcall.peg;
     else $mpeg = $ntcall.mpeg;
   }
  |
   range {
     if(!metaLevel) $peg = factory.newRangePEG(new SymInfo($range.line, $range.pos), $range.ranges);
     else $mpeg = factory.newMetaRangePEG(new SymInfo($range.line, $range.pos), $range.ranges);
   }
  |
   t='_' {
     if(!metaLevel) $peg = factory.newAnyPEG(new SymInfo($t.line, $t.pos));
     else $mpeg = factory.newMetaAnyPEG(new SymInfo($t.line, $t.pos));
   }
  |
   '(' peg_expr ')' {
     if(!metaLevel) $peg = $peg_expr.peg;
     else $mpeg = $peg_expr.mpeg;
   }
;

ntcall returns[APEG peg, Expr mpeg]:
   ID '<' actPars '>' {
     if(!metaLevel) $peg = factory.newNonterminalPEG(new SymInfo($ID.line, $ID.pos), $ID.text, $actPars.list);
     $mpeg = factory.newMetaNonterminalPEG(new SymInfo($ID.line, $ID.pos), factoryNormal.newStringExpr(new SymInfo($ID.line, $ID.pos), $ID.text)
                                                                         , factoryNormal.newListExpr(new SymInfo($ID.line, $ID.pos), new ArrayList($actPars.list)));
   }
  |
   ID {
     if(!metaLevel) $peg = factory.newNonterminalPEG(new SymInfo($ID.line, $ID.pos), $ID.text, new ArrayList<Expr>());
     else $mpeg = factory.newMetaNonterminalPEG(new SymInfo($ID.line, $ID.pos), factoryNormal.newStringExpr(new SymInfo($ID.line, $ID.pos), $ID.text)
                                                                              , factoryNormal.newListExpr(new SymInfo($ID.line, $ID.pos), new ArrayList<Expr>()));
   }
;

range returns[CharInterval ranges, int line, int pos]: RANGE_LITERAL
 {
  $ranges = splitRange($RANGE_LITERAL.text);
  $line = $RANGE_LITERAL.line;
  $pos = $RANGE_LITERAL.pos;
 }
;

/***
 * Constraint and Update Expressions
 ***/

assign returns[Pair<Attribute, Expr> stm, Pair<Expr, Expr> mstm]:
  attribute_ref '=' expr ';'
     {
     if(!metaLevel) $stm = new Pair<Attribute, Expr>($attribute_ref.exp, $expr.exp);
     else $mstm = new Pair<Expr, Expr>($attribute_ref.mexp, $expr.exp);}
;

expr returns[Expr exp]: or_cond {$exp = $or_cond.exp;};

or_cond returns[Expr exp]:
   {List<Expr> list = new ArrayList<Expr>(); List<BinOPFactory> funcs = new ArrayList<BinOPFactory>();}
   e1=and_cond {list.add($e1.exp);} (OP_OR e2=and_cond
   {list.add($e2.exp);
     funcs.add((Expr ex1, Expr ex2) -> factory.newOrExpr(new SymInfo($OP_OR.line, $OP_OR.pos), ex1, ex2));
   }
   )*
   {$exp = factory.newLeftAssocBinOpList(list, funcs);}
;

and_cond returns[Expr exp]:
  {List<Expr> list = new ArrayList<Expr>(); List<BinOPFactory> funcs = new ArrayList<BinOPFactory>();}
  e1=condExpr {list.add($e1.exp);} (OP_AND e2=condExpr
  {
  list.add($e2.exp);
  funcs.add((Expr ex1, Expr ex2) -> factory.newAndExpr(new SymInfo($OP_AND.line, $OP_AND.pos), ex1, ex2));
  }
  )*
  {$exp = factory.newLeftAssocBinOpList(list, funcs);} 
;

condExpr returns[Expr exp]:
   {List<Expr> list = new ArrayList<Expr>(); List<BinOPFactory> funcs = new ArrayList<BinOPFactory>();}
   e1=bool_expr {list.add($e1.exp);} (op=equalityOp e2=bool_expr
   	 {
   	 list.add($e2.exp);
   	  if($op.isequals) // if operator is EQUALS
        funcs.add((Expr ex1, Expr ex2) -> factory.newEqualityExpr(new SymInfo($op.line, $op.pos), ex1, ex2));
      else // the operator is not equals
       funcs.add((Expr ex1, Expr ex2) -> factory.newNotEqExpr(new SymInfo($op.line, $op.pos), ex1, ex2));
   	 }
   )*
   {$exp = factory.newLeftAssocBinOpList(list, funcs);}
;

bool_expr returns[Expr exp]:  
  e1=aexpr {$exp = $aexpr.exp;}
  ( relOp e2=aexpr
     {
       if($relOp.op == 1) // LT
          $exp = factory.newLessExpr(new SymInfo($relOp.line, $relOp.pos), $e1.exp, $e2.exp);
       else if ($relOp.op == 2) // GT
          $exp = factory.newGreaterExpr(new SymInfo($relOp.line, $relOp.pos), $e1.exp, $e2.exp);
       else if ($relOp.op == 3) // LE
          $exp = factory.newLessEqExpr(new SymInfo($relOp.line, $relOp.pos), $e1.exp, $e2.exp);
       else if ($relOp.op == 4) // GE
          $exp = factory.newGreaterEqExpr(new SymInfo($relOp.line, $relOp.pos), $e1.exp, $e2.exp);
     }
   )?
 
;

aexpr returns[Expr exp]:
    {List<Expr> list = new ArrayList<Expr>(); List<BinOPFactory> funcs = new ArrayList<BinOPFactory>();}
    e1=term {list.add($e1.exp);} (addOp e2=term
    {
    list.add($e2.exp);
    if($addOp.op == 1) // it is an add
        funcs.add((Expr ex1, Expr ex2) -> factory.newAddExpr(new SymInfo($addOp.line, $addOp.pos), ex1, ex2));
    else if($addOp.op == 2) // it is a SUB
        funcs.add((Expr ex1, Expr ex2) -> factory.newSubExpr(new SymInfo($addOp.line, $addOp.pos), ex1, ex2));
    else if($addOp.op == 3) // it is a compose
        funcs.add((Expr ex1, Expr ex2) -> factory.newComposeExpr(new SymInfo($addOp.line, $addOp.pos), ex1, ex2));
    else if($addOp.op == 4) // it is a concat
        funcs.add((Expr ex1, Expr ex2) -> factory.newConcatExpr(new SymInfo($addOp.line, $addOp.pos), ex1, ex2));
    }
    )*
    {$exp = factory.newLeftAssocBinOpList(list, funcs);} 
;

term returns[Expr exp]:
   {List<Expr> list = new ArrayList<Expr>(); List<BinOPFactory> funcs = new ArrayList<BinOPFactory>();}
   e1=factor {list.add($e1.exp);} (mulOp e2=factor
   {
   list.add($e2.exp);
   if($mulOp.op == 1) // it is a mult operator
     funcs.add((Expr ex1, Expr ex2) -> factory.newMultExpr(new SymInfo($mulOp.line, $mulOp.pos), ex1, ex2));
   else if($mulOp.op == 2) // it is a div operator
     funcs.add((Expr ex1, Expr ex2) -> factory.newDivExpr(new SymInfo($mulOp.line, $mulOp.pos), ex1, ex2));
   else if($mulOp.op == 3) // it is a mod operator
     funcs.add((Expr ex1, Expr ex2) -> factory.newModExpr(new SymInfo($mulOp.line, $mulOp.pos), ex1, ex2));
   else if($mulOp.op == 4) // it is a double-exclamation operator
     funcs.add((Expr ex1, Expr ex2) -> factory.newListAcces(new SymInfo($mulOp.line, $mulOp.pos), ex1, ex2));
   }
   )*
   {$exp = factory.newLeftAssocBinOpList(list, funcs);} 
;


factor returns[Expr exp]: 
   {metaLevel}? t='#' primary // { $exp = factory.newUnquoteExpr(new SymInfo($t.line, $t.pos), $primary.exp); }
  |
   OP_SUB primary { $exp = factory.newUMinusExpr(new SymInfo($OP_SUB.line, $OP_SUB.pos), $primary.exp); }
  |
   t='!' f=primary {$exp = factory.newNotExpr(new SymInfo($t.line, $t.pos), $f.exp);}
  |
   primary {$exp = $primary.exp;}
 ;

primary returns[Expr exp]:
   attribute_ref {if(!metaLevel) $exp = $attribute_ref.exp; else $exp = $attribute_ref.mexp;}
  |
   number {$exp = $number.exp;}
  |
   STRING_LITERAL
    { 
      String s = $STRING_LITERAL.text;
      s = s.substring(1, s.length()-1);
      $exp = factory.newStringExpr(new SymInfo($STRING_LITERAL.line, $STRING_LITERAL.pos), s);
    }
  |
   '(' expr ')' {$exp = $expr.exp;}
  |
   TRUE {$exp = factory.newBooleanExpr(new SymInfo($TRUE.line, $TRUE.pos), true);}
  |
   FALSE {$exp = factory.newBooleanExpr(new SymInfo($FALSE.line, $FALSE.pos), false);}
  |
   meta {$exp = $meta.exp;}
  |
   listLit {$exp = $listLit.exp;}
  |
   mapLit {$exp = $mapLit.exp;}
  |
   f=primary t='[' e=expr ']' {$exp = factory.newMapAcces(new SymInfo($t.line, $t.pos), $f.exp, $e.exp);}
  |
   f=primary t='[' m=mapPair ']' {$exp = factory.newMapExtension(new SymInfo($t.line, $t.pos), $f.exp, $m.p.getFirst(), $m.p.getSecond());}
;

listLit returns[Expr exp]:
   {boolean j = false;}
   t='[' (exprs {j = true;})* ']' {$exp = factory.newListExpr(new SymInfo($t.line, $t.pos), j ? new ArrayList($exprs.list) : new ArrayList());}
;

mapLit returns[Expr exp]:
   t='{:' r=mapList ':}'
   {
       Pair<Expr, Expr>[] s = $r.l.toArray(new Pair[$r.l.size()]);
       $exp = factory.newMapExpr(new SymInfo($t.line, $t.pos), s);
   }
;

mapList returns[List<Pair<Expr, Expr>> l]:
   {$l = new ArrayList<Pair<Expr, Expr>>();} (m1=mapPair {$l.add($m1.p);})* (',' m2=mapPair {$l.add($m2.p);})* 
;

mapPair returns[Pair<Expr, Expr> p]:
   e1=expr '->' e2=expr {$p = new Pair<Expr, Expr>($e1.exp, $e2.exp);}
;

attribute_ref returns[Attribute exp, Expr mexp]:
  ID {
  if(!metaLevel) $exp = factory.newAttributeExpr(new SymInfo($ID.line, $ID.pos), $ID.text);
  else $mexp = factory.newMetaAttribute(new SymInfo($ID.line, $ID.pos), factoryNormal.newStringExpr(new SymInfo($ID.line, $ID.pos), $ID.text));}
 |
  t='$g' {$exp = factory.newAttributeGrammarExpr(new SymInfo($t.line, $t.pos));}
;

number returns[Expr exp]:
   INT_NUMBER {$exp = factory.newIntExpr(new SymInfo($INT_NUMBER.line, $INT_NUMBER.pos), Integer.parseInt($INT_NUMBER.text));}
  |
   REAL_NUMBER {$exp = factory.newFloatExpr(new SymInfo($REAL_NUMBER.line, $REAL_NUMBER.pos), Float.parseFloat($REAL_NUMBER.text));}
;

actPars returns[List<Expr> list]:
   {$list = new ArrayList<Expr>();} e1=aexpr {$list.add($e1.exp);}
    (',' e2=aexpr {$list.add($e2.exp);})*
  |
    {$list = new ArrayList<Expr>();}
; 

equalityOp returns[boolean isequals, int line, int pos]:
  OP_NE {$isequals = false; $line = $OP_NE.line; $pos = $OP_NE.pos;}
 |
  OP_EQ {$isequals = true; $line = $OP_EQ.line; $pos = $OP_EQ.pos;}
;

relOp returns[int op, int line, int pos]:
 
   OP_LT {$op = 1; $line = $OP_LT.line; $pos = $OP_LT.pos;}
  |
   OP_GT {$op = 2; $line = $OP_GT.line; $pos = $OP_GT.pos;}
  |
   OP_LE {$op = 3; $line = $OP_LE.line; $pos = $OP_LE.pos;}
  |
   OP_GE {$op = 4; $line = $OP_GE.line; $pos = $OP_GE.pos;}
;

addOp returns[int op, int line, int pos]:
   
   OP_ADD {$op = 1; $line = $OP_ADD.line; $pos = $OP_ADD.pos;}
   |
   OP_SUB {$op = 2; $line = $OP_SUB.line; $pos = $OP_SUB.pos;}
   |
   OP_COMPOSE {$op = 3; $line = $OP_COMPOSE.line; $pos = $OP_COMPOSE.pos;}
   |
   OP_CONC {$op = 4; $line = $OP_CONC.line; $pos = $OP_CONC.pos;}
;

mulOp returns[int op, int line, int pos]:
   OP_MUL {$op = 1; $line = $OP_MUL.line; $pos = $OP_MUL.pos;}
  |
   OP_DIV {$op = 2; $line = $OP_DIV.line; $pos = $OP_DIV.pos;}
  |
   OP_MOD {$op = 3; $line = $OP_MOD.line; $pos = $OP_MOD.pos;}
  |
   OP_DE {$op = 4; $line = $OP_DE.line; $pos = $OP_DE.pos;}
;

/***
 * Rules for metaprogramming
 */

meta returns[Expr exp]:
    '{|' {enterMeta();} peg_expr '|}' {exitMeta(); $exp = $peg_expr.mpeg;}
   |
    '{|' {enterMeta();} rules '|}' {exitMeta(); $exp = $rules.mrules;}
   |
    '(|' {enterMeta();} expr '|)' {exitMeta(); $exp = $expr.exp;}
;

/*************************************************
 ***************** Lexical *************************
 *************************************************/

/*
 * Types
 */
 
 /* Integers and real number */
 INT_TYPE: 'int';
FLOAT_TYPE: 'float';
/* Boolean type */
BOOLEAN_TYPE: 'boolean';
/* String type */
STRING_TYPE: 'string';
/* Char type */
CHAR_TYPE: 'char';
/* Grammars types */ 
GRAMMAR_TYPE: 'grammar';
LANGUAGE_TYPE: 'language';
/* Type for maps */
MAP_TYPE: 'map';
/* Type for meta-values */
META_TYPE: 'meta';

/*
 * Operators
 */
 
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
OP_COMPOSE : '<<';
OP_CONC : '++';
OP_MUL : '*';
OP_DIV : '/';
OP_MOD : '%';
OP_DE : '!!';

/*
 * Lexical rule for a range (character group)
 */

RANGE_LITERAL: RANGE_CHAR '..' RANGE_CHAR;
fragment RANGE_CHAR:
   ESC
 | ~('\n' | '\r' | '\t' | '\b' | '\f' | ' ' | '\\' | '\'')
;

/*
 * Literals
 */

STRING_LITERAL: '\'' (ESC | ~('\'' | '\\'))* '\'';

ESC : '\\' (SPECIAL_ESC | HEX_ESC | TOOL_ESC);

fragment SPECIAL_ESC: ('n' | 'r' | 't' | 'b' | 'f');
fragment HEX_ESC: 'u' XDIGIT XDIGIT XDIGIT XDIGIT;
fragment TOOL_ESC: ('\'' | '\\');

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

/*
 * Comments and whitespaces
 */
 
WS : (' ' | '\t' | '\r' | '\n') -> skip;
COMMENT : '/*' .*? ('*/' | EOF) -> skip ;
LINE_COMMENT : '--' ~('\n'|'\r')* '\r'? '\n' -> skip;

