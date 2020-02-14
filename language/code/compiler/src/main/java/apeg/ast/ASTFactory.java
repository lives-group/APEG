package apeg.ast;

import java.util.List;

/*import apeg.parse.ast.BinaryExprNode.Operator;
import apeg.parse.ast.EqualityExprNode.EqualityOperator;
import apeg.parse.ast.GrammarNode.GrammarOption;
import apeg.parse.ast.RuleNode.Annotation;
import apeg.ast.expr.operators.*;
 */
import apeg.ast.expr.*;
import apeg.ast.expr.operators.*;
import apeg.ast.rules.*;
import apeg.ast.types.*;
import apeg.util.*;
import apeg.ast.Grammar.GrammarOption;

//import apeg.util.Pair;

public interface ASTFactory {
	
	/*------------------------------------------------------
	 * Expr
	 * ------------------------------------------------------
	 */
	
	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @param attrName
	 * 			Attribute's name
	 * @return a attribute expression node
	 */
	public Attribute newAttributeExpr(SymInfo s, String attrName);
	

	/*------------------------------------------------------
	 * Expr: literals
	 * ------------------------------------------------------
	 */
	

	/**
	 * @param s 
	 * 			corresponding SymInfo node
	 * @param value
	 * 			boolean point expression value
	 * 
	 * @return a boolean expression node
	 */
	public BoolLit newBooleanExpr(SymInfo s, boolean value);
	
	/**
	 * 
	 * @param s 
	 * 			corresponding node Syminfo
	 * @param value
	 * 			char point expression value
	 * @return a char expression node
	 */

	public CharLit newCharExpr(SymInfo s, char value);

	/**
	 * @param s 
	 * 			corresponding node SymInfo
	 * @param value
	 *            floating point expression value
	 * 
	 * @return a floating expression node
	 */

	public FloatLit newFloatExpr( SymInfo s, float value);

	/**
	 * @param s 
	 * 			corresponding node SymInfo
	 * @param value
	 * 			int point expression value
	 * 
	 * @return a floating point node
	 */
	public IntLit newIntExpr(SymInfo s, int value);
	
	/**
	 * 
	 * @param s 
	 * 			corresponding node SymInfo
	 * @param assocs 
	 * 			a hasmap of names to values
	 * @return  a map node
	 */
	public MapLit newMapExpr(SymInfo s, Pair<Expr, Expr>[] assocs);
	
	/**
	 * @param s 
	 * 		corresponding node SymInfo
	 * @param value
	 *            String point expression value
	 *            
	 * @return a plus parsing expression node
	 */

	public StrLit newStringExpr(SymInfo s, String value);
	
	
	/*------------------------------------------------------
	 * Expr: operators
	 * ------------------------------------------------------
	 */
	
	/**
	 * 
	 * 
	 * @param s
	 * 			corresponding node SymInfo
	 * @param left
	 * 				the left-hand side expression
	 * @param right
	 * 				the right-hand side expression
	 * 
	 * @return a Add expression node
	 */
	public Add newAddExpr(SymInfo s, Expr left, Expr right);
	
	/**
	 * @param s corresponding node SymInfo
	 * @param left
	 *            the left-hand side expression
	 * @param right
	 *            the right-hand side expression
	 *            
	 * @return a AND expression node
	 */
	public And newAndExpr(SymInfo s, Expr left, Expr right);
	
	/**
	 * 
	 * @param s
	 * 				corresponding node SymInfo
	 * @param left
	 * 				the left-hand side expression
	 * @param right
	 * 				the right-hand side expression
	 * 
	 * @return a Compose expression node
	 */
	public Compose newComposeExpr(SymInfo s, Expr left, Expr right);
	
	/**
	 * 
	 * @param s
	 * 				corresponding SymInfo node
	 * @param left
	 * 				the left-hand side expression
	 * @param right
	 * 				the right-hand side expression
	 * 
	 * @return a Concat expression node
	 */
	public Concat newConcatExpr(SymInfo s, Expr left, Expr right);
	
	/**
	 * 
	 * @param s
	 * 				corresponding SymInfo node
	 * @param left
	 * 				the left-hand side expression
	 * @param right
	 * 				the right-hand side expression
	 * 
	 * @return a Div expression node
	 */
	public Div newDivExpr(SymInfo s, Expr left, Expr right);
	
	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @param map
	 * 			a map expression
	 * @param key
	 * 			a key expression
	 * @param value
	 * 			a value expression
	 * @return a Map Extension node
	 */
	public MapExtension newMapExtension(SymInfo s, Expr map, Expr key, Expr value);
	
	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @param map
	 * @param index
	 * @return a map acces node
	 */
	public MapAcces newMapAcces(SymInfo s,Expr map,Expr index);
	
	/**
	 * 
	 * @param s
	 * 				corresponding SymInfo node
	 * @param left
	 * 				the left-hand side expression
	 * @param right
	 * 				the right-hand side expression
	 * @return a Mult expression node
	 */
	public Mult newMultExpr(SymInfo s, Expr left, Expr right);
	
	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @param expr
	 * 				
	 * @return a not expression node
	 */
	public Not newNotExpr(SymInfo s, Expr expr);
	
	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @param expr
	 * @return a not equals expression node
	 */
	public NotEq newNotEqExpr(SymInfo s, Expr l, Expr r);
	
	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @param left
	 * 			the left-hand side expression
	 * @param right
	 * 			the right-hand side expression
	 * @return a pointer to an equals object
	 */
	public Equals newEqualityExpr(SymInfo s, Expr left, Expr right);

	/**
	 * @param s corresponding node SymInfo 
	 * @param Expr left: the left subexpression 
	 * @param Expr right: the right subexpression
	 * 
	 * @return  a pointer to an greater object
	 */
	public Greater newGreaterExpr(SymInfo s, Expr left, Expr right);

	/**
	 * @param s corresponding node SymInfo 
	 * @param Expr left: the left subexpression 
	 * @param Expr right: the right subexpression
	 * 
	 * @return  a pointer to an greater or equals object
	 */
	public GreaterEq newGreaterEqExpr(SymInfo s, Expr left, Expr right);

	/**
	 * @param s corresponding node SymInfo 
	 * @param Expr left: the left subexpression 
	 * @param Expr right: the right subexpression
	 * 
	 * @return  a pointer to an less object
	 */
	public Less newLessExpr(SymInfo s, Expr left, Expr right);

	/**
	 * @param s corresponding node SymInfo 
	 * @param Expr left: the left subexpression 
	 * @param Expr right: the right subexpression
	 * 
	 * @return  a pointer to an less or equals object
	 */
	public LessEq newLessEqExpr(SymInfo s, Expr left, Expr right);


	/**
	 * @param s corresponding node SymInfo
	 * @param Expr left: the left subexpression
	 * @param Expr right: the right subexpression
	 * @return an integer type node
	 */
	public Sub newMinusExpr(SymInfo s, Expr left, Expr right);

	

	/**
	 * @param s corresponding node SymInfo
	 * @param Expr left: the left subexpression
	 * @param Expr right: the right subexpression
	 * @return a not-predicate parsing expression node
	 */
	public Or newOrExpr(SymInfo s, Expr left, Expr right);
	
	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @param e
	 * @return
	 */
	public UMinus newUMinus(SymInfo s,Expr e);

	/*------------------------------------------------------
	 * Rules 
	 * ------------------------------------------------------
	 */
	
		/**
		 * 
		 * @param s
		 * 			corresponding SymInfo node
		 * @param peg
		 * 			a parsing expression
		 * @return a And peg node
		 */
	public AndPEG newAndPEG(SymInfo s, APEG peg);

	/**
	 * @param s
	 * 			corresponding SymInfo node
	 * 
	 * @return an any parsing expression node
	 */
	public AnyPEG newAnyPEG(SymInfo s);
	
	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @param attribute
	 * 			 the bind attribute's name
	 * @param peg
	 * 				parsing expression
	 * @return a bind peg node
	 * 			
	 */
	public BindPEG newBindPEG(SymInfo s, Attribute attribute, APEG peg );
	
	/**
	 * 
	 * @param s
	 * 				corresponding SymInfo node
	 * @param i
	 * 			character interval
	 * @return
	 * 			a choice list node
	 */
	public ChoiceList newChoiceList(SymInfo s, CharInterval i);
	/**
	 * 
	 * @param s
	 * 				corresponding SymInfo node
	 * @param left
	 * 				the left parsing expression
	 * @param right
	 * 				the right parsing expression
	 * @return
	 * 			a choice peg node
	 */
	public ChoicePEG newChoicePEG(SymInfo s, APEG left, APEG right);
	

	/**
	 * @param s
	 * 			corresponding SymInfo node
	 * @param expr
	 *            a boolean expression node
	 * @return a constraint parsing expression node
	 */
	public ConstraintPEG newConstraintPEG(SymInfo s, Expr expr);
	
	/**
	 * @param s
	 * 				corresponding SymInfo node
	 * @param peg
	 *            parsing expression
	 * @return a star parsing expression node
	 */
	public KleenePEG newStarPEG(SymInfo s, APEG peg);

	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 *
	 * @return a Lambda peg node
	 */
	public LambdaPEG newLambdaPEG(SymInfo s);
    
	/**
	 * @param s
	 * 			corresponding SymInfo node
	 * @param value
	 *            literal expression value
	 * @return a literal parsing expression node
	 */
	public LitPEG newLiteralPEG(SymInfo s, String value);

	
	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @param name
	 * 				nonterminal's name
	 * @param attrs
	 * 			attributes's list
	 * @return a nonterminal peg node
	 */
	public NonterminalPEG newNonterminalPEG(SymInfo s, String name,
			List<Expr> attrs);
	
	
	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @param peg
	 * 			a parsing expression
	 * @return a not peg node
	 */
	public NotPEG newNotPEG(SymInfo s, APEG peg);
	
	/**
	 * @param s
	 * 			corresponding SymInfo node
	 * @param peg
	 *            a parsing expression
	 * @return an optional parsing expression node
	 */
	public OptionalPEG newOptionalPEG(SymInfo s, APEG peg);
	
	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @param peg
	 * 			a parsing expression
	 * @return a positive Kleene peg node
	 */
	public PKleene newPositiveKleenePEG(SymInfo s, APEG peg);

	/**
	 * @param name
	 *            rule's name
	 * @param anno
	 *            rule's annotation
	 * @param inh
	 *            List of inherited attributes
	 * @param syn
	 *            List of synthesized attributes
	 * @param peg
	 *            rule parsing expression
	 * @return a rule node
	 */
	public RulePEG newRule(SymInfo s, String name, RulePEG.Annotation anno,
			List<Pair<Type,String>> inh,List<Expr> syn,APEG peg);
	

	/**
	 * @param s
	 * 			corresponding SymInfo node
	 * @param pegs
	 *            set of parsing expressions
	 *            
	 * @return a sequence parsing expression node
	 */
	public SeqPEG newSequencePEG(SymInfo s, APEG[] p);

	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @param assigs
	 * 				
	 * @return a update peg node
	 */
	public UpdatePEG newUpdatePEG(SymInfo s,List<Pair<Attribute, Expr>>assigs);
	

	/*------------------------------------------------------
	 * Types
	 * ------------------------------------------------------
	 */	

	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @return  a Grammar type node
	 */
	public TyBool newBooleanType(SymInfo s);

        /**
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @return  a Grammar type node
	 */
	public TyChar newCharType(SymInfo s);
    
	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @return
	 */
	public TyFloat newFloatType(SymInfo s);

	/**
	 * @param s
	 * 			corresponding SymInfo
	 * @return a grammar type node
	 */
	public TyGrammar newGrammarType(SymInfo s);
	
	/**
	 * @param s
	 * 			corresponding SymInfo
	 * @return a int type node
	 */
	public TyInt newIntType(SymInfo s);
	
	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @return a Lang type node
	 */
	public TyLang newLangType(SymInfo s);
	
	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @return a Map type node
	 */
	public TyMap newMapType(SymInfo s, Type tyParameter);
	
	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @return a Meta type node
	 */
	public TyMeta newMetaType(SymInfo s);

	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @return a String type node
	 */
	public TyString newStringType(SymInfo s);
	

	/*------------------------------------------------------
	 * Meta
	 * ------------------------------------------------------
	 */	
	
	
	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @param ml
	 * 			meta expression left
	 * @param mr
	 * 			meta expression right
	 * @return meta add node
	 */
	public MetaAdd newMetaAdd(SymInfo s,MetaExpr ml,MetaExpr mr);
	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @param ml
	 * 			meta expression left
	 * @param mr
	 * 			meta expression right
	 * @return meta and node
	 */
	public MetaAnd newMetaAnd(SymInfo s,MetaExpr ml,MetaExpr mr);
	
	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo node;
	 * @param ml
	 * 			meta expression left
	 * @param mr
	 * 			meta expression right
	 * @return meta compose node
	 */
	public MetaCompose newMetaCompose(SymInfo s,MetaExpr ml,MetaExpr mr);
	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @param ml
	 * 			meta expression left
	 * @param mr
	 * 			meta expression right
	 * @return meta concat node
	 */
	public MetaConcat newMetaConcat(SymInfo s,MetaExpr ml,MetaExpr mr);
	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @param ml
	 * 			meta expression left
	 * @param mr
	 * 			meta expression right
	 * @return meta div node
	 */
	public MetaDiv newMetaDiv(SymInfo s,MetaExpr ml,MetaExpr mr);
	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @param ml
	 * 			meta expression left
	 * @param mr
	 * 			meta expression right
	 * @return meta equals node
	 */
	public MetaEquals newMetaEquals(SymInfo s,MetaExpr ml,MetaExpr mr);
	/**
	 * 
	 * @param s
	 * corresponding SymInfo node
	 * @param ml
	 * 			meta expression left
	 * @param mr
	 * 			meta expression right
	 * @return meta greater node
	 */
	public MetaGreater newMetaGreater(SymInfo s,MetaExpr ml,MetaExpr mr);
	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @param ml
	 * 			meta expression left
	 * @param mr
	 * 			meta expression right
	 * @return meta greater-equals node
	 */
	public MetaGreaterEq newMetaGreaterEq(SymInfo s,MetaExpr ml,MetaExpr mr);
	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @param ml
	 * 			meta expression left
	 * @param mr
	 * 			meta expression right
	 * @return meta less node
	 */
	public MetaLess newMetaLess(SymInfo s,MetaExpr ml,MetaExpr mr);
	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @param ml
	 * 			meta expression left
	 * @param mr
	 * 			meta expression right
	 * @return meta less-equals node
	 */
	public MetaLessEq newMetaLessEq(SymInfo s,MetaExpr ml,MetaExpr mr);
	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @param map
	 * @param index
	 * @return meta MapAcces node
	 */
	public MetaMapAcces newMetaMapAcces(SymInfo s,Expr map,Expr index);
	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @param map
	 * @param key
	 * @param value
	 * @return meta map extension node
	 */
	public MetaMapExtension newMetaMapExtension(SymInfo s,Expr map,Expr key,Expr value);
	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @param ml
	 * 			meta expression left
	 * @param mr
	 * 			meta expression right
	 * @return
	 */
	public MetaMult newMetaMult(SymInfo s,MetaExpr ml,MetaExpr mr);

	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @param ml
	 * 			meta expression left
	 * @param mr
	 * 			meta expression right
	 * @return meta not-equals node
	 */
	public MetaNotEq newMetaNotEq(SymInfo s,MetaExpr ml,MetaExpr mr);
	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @param ml
	 * 			meta expression left
	 * @param mr
	 * 			meta expression right
	 * @return meta or node
	 */
	public MetaOr newMetaOr(SymInfo s,MetaExpr ml,MetaExpr mr);
	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @param ml
	 * 			meta expression left
	 * @param mr
	 * 			meta expression right
	 * @return meta Sub node
	 */
	public MetaSub newMetaSub(SymInfo s,MetaExpr ml,MetaExpr mr);

	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @param e
	 * 			a meta parsing expression
	 * @return meta and peg node
	 */
	public MetaAndPEG newMetaAndPEG(SymInfo s,MetaAPEG e);
	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @return meta any peg node
	 */
	public MetaAnyPEG newMetaAnyPEG(SymInfo s);
	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @param name
	 * 				attribute's name
	 * @return meta attribute node
	 */
	public MetaAttribute newMetaAttribute(SymInfo s,String name);
	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @param attribute
	 * @param e
	 * 			a meta expression
	 * @return meta bind peg node
	 */
	public MetaBindPEG newMetaBindPEG(SymInfo s, Attribute attribute,MetaAPEG p);
	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @param value
	 * 				a boolean value
	 * @return meta boolean literal node
	 */
	public MetaBoolLit newMetaBoolLit(SymInfo s,boolean value);
	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @param value
	 * 				 a char value
	 * @return meta char literal node
	 */
	public MetaCharLit newMetaCharLit(SymInfo s,char value);
	/** 
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @param i
	 * 		a char interval
	 * @return a meta choice list
	 */
	public MetaChoiceList newMetaChoiceList(SymInfo s,CharInterval i);
	/** 
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @param leftPEG
	 * 				left-hand side parsing expression
	 * @param rightPEG
	 * 				right-hand side parsing expression
	 * @return meta choice peg node
	 */
	public MetaChoicePEG newMetaChoicePEG(SymInfo s,MetaAPEG leftPEG,MetaAPEG rightPEG);
	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @param e
	 * 			a meta expression
	 * @return a meta constraint peg node
	 */
	public MetaConstraintPEG newMetaConstraintPEG(SymInfo s,MetaExpr e);
	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @param value
	 * 				a float value
	 * @return meta float literal node
	 */
	public MetaFloatLit newMetaFloatLit(SymInfo s,float value);
	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @param value
	 * 			a int value
	 * @return meta int literal node
	 */
	public MetaIntLit newMetaIntLit(SymInfo s,int value);
	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @param e
	 * 			a meta parsing expression
	 * @return meta star klenee peg node
	 */
	public MetaKleenePEG newMetaKleenePEG(SymInfo s,MetaAPEG e);
	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @param lit
	 * 			
	 * @return meta literal peg node
	 */
	public MetaLitPEG newMetaLitPEG(SymInfo s,String lit);
	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @param assocs
	 * @return a meta map literal node
	 */
	public MetaMapLit newMetaMapLit(SymInfo s,Pair<Expr,Expr>[] assocs);
	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @param name
	 * 			nonterminal's name
	 * @param args
	 * 			set of nonterminal's arguments
	 * @return meta nonterminal peg node
	 */
	public MetaNonterminalPEG newMetaNonterminalPEG(SymInfo s,String name,List<MetaExpr> args);
	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @param e
	 * 			a meta expression
	 * @return meta not node
	 */
	public MetaNot newMetaNot(SymInfo s,MetaExpr e);
	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @param e
	 * 			a meta parsing expression
	 * @return meta not peg node
	 */
    public MetaNotPEG newMetaNotPEG(SymInfo s,MetaAPEG e);
    /**
     * 
     * @param s
     * 			corresponding SymInfo node
     * @param e
     * 			a meta parsing expression
     * @return meta optional peg node
     */
    public MetaOptionalPEG newMetaOptionalPEG(SymInfo s,MetaAPEG e);
    /**
     * 
     * @param s
     * 			corresponding SymInfo node
     * @param e
     * 			a meta parsing expression
     * @return a meta plus klenee node
     */
    public MetaPKleene newMetaPKleene(SymInfo s,MetaAPEG e);
    /**
     * 
     * @param s
     * 			corresponding SymInfo node
     * @param ruleName
     * 				the rule's name
     * @param anno
     * 			rule's annotation
     * @param inh
     * 			set of inherited attributes
     * @param syn
     * 			set of synthesized  attributes
     * @param peg
     * 			set of meta parsing expression
     * @return
     */
    public MetaRulePEG newMetaRulePEG(SymInfo s,String ruleName,RulePEG.Annotation anno,List<Pair<MetaType,String>> inh,List<MetaExpr> syn,MetaAPEG peg);
    /**
     * 
     * @param s
     * 			corresponding SymInfo node
     * @param p
     * 			set of meta parsing expression
     * @return meta sequence peg node
     */
	public MetaSeqPEG newMetaSeqPEG(SymInfo s,MetaAPEG[] p);
	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo Node
	 * @param value
	 * 			a string value
	 * @return meta string literal node
	 */
	public MetaStrLit newMetaStrLit(SymInfo s,String value);
	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @return meta boolean node
	 */
	public MetaTyBool newMetaTyBool(SymInfo s);
	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @return meta char node
	 */
	public MetaTyChar newMetaTyChar(SymInfo s);
	/**
	 * 
	 * @param s
	 * 				corresponding SymInfo node
	 * @return meta float node
	 */
	public MetaTyFloat newMetaTyFloat(SymInfo s);
	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @return meta grammar node
	 */
	public MetaTyGrammar newMetaTyGrammar(SymInfo s);
	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @return meta int node
	 */
	public MetaTyInt newMetaTyInt(SymInfo s);
	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @return meta lang node
	 */
	public MetaTyLang newMetaTyLang(SymInfo s);
	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @param tyParameter
	 * @return meta map node
	 */
	public MetaTyMap newMetaTyMap(SymInfo s,MetaType tyParameter);
	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo 
	 * @return meta meta node
	 */
	public MetaTyMeta newMetaTyMeta(SymInfo s);
	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @return meta string node
	 */
	public MetaTyString newMetaTyString(SymInfo s);
	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @param e 
	 * 			meta expression
	 * @return meta unary minus node
	 */
	public MetaUMinus newMetaUMinus(SymInfo s,MetaExpr e);
	/** 
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @param assigs
	 * 				set of assignments
	 * @return meta update peg node
	 */
	public MetaUpdatePEG newMetaUpdatePEG(SymInfo s,List<Pair<MetaAttribute,MetaExpr>> assigs);
	/**
	 * 
	 * @param s
	 * 		 corresponding SymInfo node
	 * @param name
	 * 			meta var's name
	 * @return meta var node
	 */
	public MetaVar newMetaVar(SymInfo s,String name);


	/*------------------------------------------------------
	 * Generic nodes
	 * ------------------------------------------------------
	 */	

	/**
	 * 
	 * @param name
	 * 				grammar name
	 * @param opts
	 * 				set of grammar options
	 * @param rules
	 * 				set of grammar's rules
	 * @return a grammar node
	 */
	public Grammar newGrammar(SymInfo s, String name, GrammarOption opts, List<RulePEG> rules);
	
	
	/*
	 * Create an left associative tree of some kind of binary operator based on a list.
	 *
	 */
	 
    public BinaryOP newLeftAssocBinOpList(List<Expr> l, List<BinOPFactory> funcs);

}
