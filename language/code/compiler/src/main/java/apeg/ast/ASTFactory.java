package apeg.ast;

import java.util.List;

import apeg.ast.expr.*;
import apeg.ast.expr.operators.*;
import apeg.ast.rules.*;
import apeg.ast.types.*;
import apeg.util.*;
import apeg.ast.Grammar.GrammarOption;

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

	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @return a attribute expression node
	 */
	public AttributeGrammar newAttributeGrammarExpr(SymInfo s);    

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
	public Expr newBooleanExpr(SymInfo s, boolean value);
	
	/**
	 * 
	 * @param s 
	 * 			corresponding node Syminfo
	 * @param value
	 * 			char point expression value
	 * @return a char expression node
	 */

	public Expr newCharExpr(SymInfo s, char value);

	/**
	 * @param s 
	 * 			corresponding node SymInfo
	 * @param value
	 *            floating point expression value
	 * 
	 * @return a floating expression node
	 */

	public Expr newFloatExpr( SymInfo s, float value);

	/**
	 * @param s 
	 * 			corresponding node SymInfo
	 * @param value
	 * 			int point expression value
	 * 
	 * @return a floating point node
	 */
	public Expr newIntExpr(SymInfo s, int value);
	
	/**
	 * 
	 * @param s 
	 * 			corresponding node SymInfo
	 * @param assocs 
	 * 			a hasmap of names to values
	 * @return  a map node
	 */
	public Expr newMapExpr(SymInfo s, Pair<Expr, Expr>[] assocs);
	
	/**
	 * @param s 
	 * 		corresponding node SymInfo
	 * @param value
	 *            String point expression value
	 *            
	 * @return a plus parsing expression node
	 */

	public Expr newStringExpr(SymInfo s, String value);
	
	
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
	public Expr newAddExpr(SymInfo s, Expr left, Expr right);
	
	/**
	 * @param s corresponding node SymInfo
	 * @param left
	 *            the left-hand side expression
	 * @param right
	 *            the right-hand side expression
	 *            
	 * @return a AND expression node
	 */
	public Expr newAndExpr(SymInfo s, Expr left, Expr right);
	
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
	public Expr newComposeExpr(SymInfo s, Expr left, Expr right);
	
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
	public Expr newConcatExpr(SymInfo s, Expr left, Expr right);
	
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
	public Expr newDivExpr(SymInfo s, Expr left, Expr right);
	
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
	public Expr newMapExtension(SymInfo s, Expr map, Expr key, Expr value);
	
	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @param map
	 * @param index
	 * @return a map acces node
	 */
	public Expr newMapAcces(SymInfo s,Expr map,Expr index);

	/**
	 * 
	 * @param s
	 * 				corresponding SymInfo node
	 * @param left
	 * 				the left-hand side expression
	 * @param right
	 * 				the right-hand side expression
	 * @return a Mod expression node
	 */
	public Expr newModExpr(SymInfo s, Expr left, Expr right);
    
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
	public Expr newMultExpr(SymInfo s, Expr left, Expr right);
	
	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @param expr
	 * 				
	 * @return a not expression node
	 */
	public Expr newNotExpr(SymInfo s, Expr expr);
	
	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @param expr
	 * @return a not equals expression node
	 */
	public Expr newNotEqExpr(SymInfo s, Expr l, Expr r);
	
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
	public Expr newEqualityExpr(SymInfo s, Expr left, Expr right);

	/**
	 * @param s corresponding node SymInfo 
	 * @param Expr left: the left subexpression 
	 * @param Expr right: the right subexpression
	 * 
	 * @return  a pointer to an greater object
	 */
	public Expr newGreaterExpr(SymInfo s, Expr left, Expr right);

	/**
	 * @param s corresponding node SymInfo 
	 * @param Expr left: the left subexpression 
	 * @param Expr right: the right subexpression
	 * 
	 * @return  a pointer to an greater or equals object
	 */
	public Expr newGreaterEqExpr(SymInfo s, Expr left, Expr right);

	/**
	 * @param s corresponding node SymInfo 
	 * @param Expr left: the left subexpression 
	 * @param Expr right: the right subexpression
	 * 
	 * @return  a pointer to an less object
	 */
	public Expr newLessExpr(SymInfo s, Expr left, Expr right);

	/**
	 * @param s corresponding node SymInfo 
	 * @param Expr left: the left subexpression 
	 * @param Expr right: the right subexpression
	 * 
	 * @return  a pointer to an less or equals object
	 */
	public Expr newLessEqExpr(SymInfo s, Expr left, Expr right);


	/**
	 * @param s corresponding node SymInfo
	 * @param Expr left: the left subexpression
	 * @param Expr right: the right subexpression
	 * @return an integer type node
	 */
	public Expr newSubExpr(SymInfo s, Expr left, Expr right);

	

	/**
	 * @param s corresponding node SymInfo
	 * @param Expr left: the left subexpression
	 * @param Expr right: the right subexpression
	 * @return a not-predicate parsing expression node
	 */
	public Expr newOrExpr(SymInfo s, Expr left, Expr right);
	
	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @param e
	 * @return
	 */
	public Expr newUMinusExpr(SymInfo s,Expr e);

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
	public RangePEG newRangePEG(SymInfo s, CharInterval i);
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
	 * @param e
	 * 			a meta parsing expression
	 * @return meta and peg node
	 */
	public MetaAndPEG newMetaAndPEG(SymInfo s,Expr e);
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
	public MetaAttribute newMetaAttribute(SymInfo s,Expr name);
	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @param attribute
	 * @param e
	 * 			a meta expression
	 * @return meta bind peg node
	 */
	public MetaBindPEG newMetaBindPEG(SymInfo s, Expr attribute,Expr p);
	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @param value
	 * 				a boolean value
	 * @return meta boolean literal node
	 */
	public MetaRangePEG newMetaRangePEG(SymInfo s, CharInterval i);
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
	public MetaChoicePEG newMetaChoicePEG(SymInfo s,Expr leftPEG,Expr rightPEG);
	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @param e
	 * 			a meta expression
	 * @return a meta constraint peg node
	 */
	public MetaConstraintPEG newMetaConstraintPEG(SymInfo s,Expr e);
	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @param value
	 * 				a float value
	 * @return meta float literal node
	 */
	public MetaKleenePEG newMetaKleenePEG(SymInfo s,Expr e);
	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @param lit
	 * 			
	 * @return meta literal peg node
	 */
	public MetaLitPEG newMetaLitPEG(SymInfo s,Expr lit);
	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @param assocs
	 * @return a meta map literal node
	 */
	public MetaNonterminalPEG newMetaNonterminalPEG(SymInfo s,Expr name,Expr args);
	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo node
	 * @param e
	 * 			a meta expression
	 * @return meta not node
	 */
    public MetaNotPEG newMetaNotPEG(SymInfo s,Expr e);
    /**
     * 
     * @param s
     * 			corresponding SymInfo node
     * @param e
     * 			a meta parsing expression
     * @return meta optional peg node
     */
    public MetaOptionalPEG newMetaOptionalPEG(SymInfo s,Expr e);
    /**
     * 
     * @param s
     * 			corresponding SymInfo node
     * @param e
     * 			a meta parsing expression
     * @return a meta plus klenee node
     */
    public MetaPKleene newMetaPKleene(SymInfo s,Expr e);
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
    public MetaRulePEG newMetaRulePEG(SymInfo s,Expr ruleName,Expr anno,Expr types, Expr inh,Expr syn,Expr peg);
    /**
     * 
     * @param s
     * 			corresponding SymInfo node
     * @param p
     * 			set of meta parsing expression
     * @return meta sequence peg node
     */
	public MetaSeqPEG newMetaSeqPEG(SymInfo s,Expr[] p);
	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo Node
	 * @param value
	 * 			a string value
	 * @return meta string literal node
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
	public MetaTyMap newMetaTyMap(SymInfo s,Expr tyParameter);
	/**
	 * 
	 * @param s
	 * 			corresponding SymInfo 
	 * @return meta meta node
	 */
	public MetaTyTy newMetaTyTy(SymInfo s);
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
	public MetaUpdatePEG newMetaUpdatePEG(SymInfo s,Expr assigs);
	/**
	 * 
	 * @param s
	 * 		 corresponding SymInfo node
	 * @param name
	 * 			meta var's name
	 * @return meta var node
	 */


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
	 
    public Expr newLeftAssocBinOpList(List<Expr> l, List<BinOPFactory> funcs);

}
