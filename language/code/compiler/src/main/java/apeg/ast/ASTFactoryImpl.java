package apeg.ast;

import apeg.ast.expr.*;
import apeg.ast.expr.operators.*;
import apeg.ast.rules.*;
import apeg.ast.types.*;
import apeg.util.*;
import java.util.List;
import java.util.ArrayList;
import apeg.ast.Grammar.GrammarOption;

public class ASTFactoryImpl implements ASTFactory{

    public Expr newAddExpr(SymInfo s,Expr l,Expr r){
        return new Add(s,l,r);
    }
    public Expr newSubExpr(SymInfo s,Expr l,Expr r){
        return new Sub(s,l,r);
    }

    public Expr newModExpr(SymInfo s,Expr l,Expr r){
        return new Mod(s,l,r);
    }

    public Expr newListAcces(SymInfo s, Expr list, Expr index){
        return new ListAcces(s, list, index);
    }
    
    public Expr newMultExpr(SymInfo s,Expr l,Expr r){
        return new Mult(s,l,r);
    }
    public Expr newDivExpr(SymInfo s,Expr l,Expr r){
        return new Div(s,l,r);
    }
    public Expr newAndExpr(SymInfo s,Expr l,Expr r){
        return new And(s,l,r);
    }
    public Expr newOrExpr(SymInfo s,Expr l,Expr r){
        return new Or(s,l,r);
    }
    public Expr newLessExpr(SymInfo s,Expr l,Expr r){
        return new Less(s,l,r);
    }
    public Expr newEqualityExpr(SymInfo s,Expr l,Expr r){
        return new Equals(s,l,r);
    }
    public Expr newGreaterExpr(SymInfo s,Expr l,Expr r){
        return new Greater(s,l,r);
    }
    public Expr newLessEqExpr(SymInfo s,Expr l,Expr r){
        return new LessEq(s,l,r);
    }
    public Expr newGreaterEqExpr(SymInfo s,Expr l,Expr r){
        return new GreaterEq(s,l,r);
    }
    public Expr newNotEqExpr(SymInfo s,Expr l, Expr r){
        return new NotEq(s,l, r);
    }
    public Expr newConcatExpr(SymInfo s,Expr l,Expr r){
        return new Concat(s,l,r);
    }
    public Expr newComposeExpr(SymInfo s,Expr l,Expr r){
        return new Compose(s,l,r);
    }
    public Expr newMapAcces(SymInfo s,Expr map,Expr index){
        return new MapAcces(s,map,index);
    }
    public Expr newMapExtension(SymInfo s,Expr map,Expr key,Expr value){
        return new MapExtension(s,map,key,value);
    }
    public Expr newNotExpr(SymInfo s,Expr e){
        return new Not(s,e);
    }
    public Expr newUMinusExpr(SymInfo s,Expr e){
        return new UMinus(s,e);
    }
    public Unquote newUnquoteExpr(SymInfo s,Expr e){
        return new Unquote(s,e);
    }
    public QuoteValue newQuoteValue(SymInfo s,Expr e){
        return new QuoteValue(s,e);
    }
    public Attribute newAttributeExpr(SymInfo s,String name){
        return new Attribute(s,name);
    }
    public AttributeGrammar newAttributeGrammarExpr(SymInfo s){
        return new AttributeGrammar(s);
    }    
    public Expr newIntExpr(SymInfo s,int value){
        return new IntLit(s,value);
    }
    public Expr newFloatExpr(SymInfo s,float value){
        return new FloatLit(s,value);
    }
    public Expr newCharExpr(SymInfo s,char value){
        return new CharLit(s,value);
    }
    public Expr newBooleanExpr(SymInfo s,boolean value){
        return new BoolLit(s,value);
    }
    public Expr newStringExpr(SymInfo s,String value){
        return new StrLit(s,value);
    }
    public Expr newListExpr(SymInfo s, ArrayList<Expr> elems){
        return new ListLit(s, elems);
    }
    public Expr newMapExpr(SymInfo s,Pair<Expr,Expr>[] assocs){
        return new MapLit(s,assocs);
    }
    public MetaAndPEG newMetaAndPEG(SymInfo s,Expr e){
        return new MetaAndPEG(s,e);
    }
    public MetaNotPEG newMetaNotPEG(SymInfo s,Expr e){
        return new MetaNotPEG(s,e);
    }
    public MetaKleenePEG newMetaKleenePEG(SymInfo s,Expr e){
        return new MetaKleenePEG(s,e);
    }
    public MetaOptionalPEG newMetaOptionalPEG(SymInfo s,Expr e){
        return new MetaOptionalPEG(s,e);
    }
    public MetaPKleene newMetaPKleene(SymInfo s,Expr e){
        return new MetaPKleene(s,e);
    }
    public MetaAnyPEG newMetaAnyPEG(SymInfo s){
        return new MetaAnyPEG(s);
    }
    public MetaLitPEG newMetaLitPEG(SymInfo s,Expr lit){
        return new MetaLitPEG(s,lit);
    }
    public MetaConstraintPEG newMetaConstraintPEG(SymInfo s,Expr e){
        return new MetaConstraintPEG(s,e);
    }
    public MetaRangePEG newMetaRangePEG(SymInfo s, CharInterval i){
        return new MetaRangePEG(s,i);
    }
    public MetaNonterminalPEG newMetaNonterminalPEG(SymInfo s,Expr name,Expr args){
        return new MetaNonterminalPEG(s,name,args);
    }
    public MetaChoicePEG newMetaChoicePEG(SymInfo s,Expr leftPeg,Expr rightPeg){
        return new MetaChoicePEG(s,leftPeg,rightPeg);
    }
    public MetaSeqPEG newMetaSeqPEG(SymInfo s,Expr[] p){
        return new MetaSeqPEG(s,p);
    }
    public MetaRulePEG newMetaRulePEG(SymInfo s,Expr ruleName,Expr anno,Expr types, Expr inh,Expr syn,Expr peg){
        return new MetaRulePEG(s,ruleName,anno, types, inh,syn,peg);
    }
    public MetaGrammar newMetaGrammar(SymInfo s, Expr listMetaRule){
        return new MetaGrammar(s, listMetaRule);
    }
    public MetaUpdatePEG newMetaUpdatePEG(SymInfo s,Expr assigs){
        return new MetaUpdatePEG(s,assigs);
    }
    public MetaBindPEG newMetaBindPEG(SymInfo s, Expr attribute,Expr p){
        return new MetaBindPEG(s,attribute,p);
    }
    public MetaTyMap newMetaTyMap(SymInfo s,Expr tyParameter){
        return new MetaTyMap(s,tyParameter);
    }
    public MetaTyInt newMetaTyInt(SymInfo s){
        return new MetaTyInt(s);
    }
    public MetaTyFloat newMetaTyFloat(SymInfo s){
        return new MetaTyFloat(s);
    }
    public MetaTyBool newMetaTyBool(SymInfo s){
        return new MetaTyBool(s);
    }
    public MetaTyChar newMetaTyChar(SymInfo s){
        return new MetaTyChar(s);
    }
    public MetaTyString newMetaTyString(SymInfo s){
        return new MetaTyString(s);
    }
    public MetaTyLang newMetaTyLang(SymInfo s){
        return new MetaTyLang(s);
    }
    public MetaTyGrammar newMetaTyGrammar(SymInfo s){
        return new MetaTyGrammar(s);
    }
    public MetaTyTy newMetaTyTy(SymInfo s){
        return new MetaTyTy(s);
    }
    public MetaAttribute newMetaAttribute(SymInfo s,Expr name){
        return new MetaAttribute(s,name);
    }
    public RulePEG newRule(SymInfo s,String ruleName,RulePEG.Annotation anno,List<Pair<Type,String>> inh,List<Expr> syn,APEG peg){
        return new RulePEG(s,ruleName,anno,inh,syn,peg);
    }
    public AndPEG newAndPEG(SymInfo s,APEG e){
        return new AndPEG(s,e);
    }
    public NotPEG newNotPEG(SymInfo s,APEG e){
        return new NotPEG(s,e);
    }
    public KleenePEG newStarPEG(SymInfo s,APEG e){
        return new KleenePEG(s,e);
    }
    public OptionalPEG newOptionalPEG(SymInfo s,APEG e){
        return new OptionalPEG(s,e);
    }
    public PKleene newPositiveKleenePEG(SymInfo s,APEG e){
        return new PKleene(s,e);
    }
    public ChoicePEG newChoicePEG(SymInfo s,APEG leftPeg,APEG rightPeg){
        return new ChoicePEG(s,leftPeg,rightPeg);
    }
    public SeqPEG newSequencePEG(SymInfo s,APEG[] p){
        return new SeqPEG(s,p);
    }
    public UpdatePEG newUpdatePEG(SymInfo s,List<Pair<Attribute,Expr>> assigs){
        return new UpdatePEG(s,assigs);
    }
    public BindPEG newBindPEG(SymInfo s, Attribute attribute,APEG peg){
        return new BindPEG(s,attribute,peg);
    }
    public RangePEG newRangePEG(SymInfo s, CharInterval i){
        return new RangePEG(s,i);
    }
    public AnyPEG newAnyPEG(SymInfo s){
        return new AnyPEG(s);
    }
    public LambdaPEG newLambdaPEG(SymInfo s) {
	return new LambdaPEG(s);
    }
    public LitPEG newLiteralPEG(SymInfo s,String lit){
        return new LitPEG(s,lit);
    }
    public ConstraintPEG newConstraintPEG(SymInfo s,Expr e){
        return new ConstraintPEG(s,e);
    }
    public NonterminalPEG newNonterminalPEG(SymInfo s,String name,List<Expr> args){
        return new NonterminalPEG(s,name,args);
    }
    public TyList newListType(SymInfo s,Type tyParameter){
        return new TyList(s,tyParameter);
    }
    public TyMap newMapType(SymInfo s,Type tyParameter){
        return new TyMap(s,tyParameter);
    }
    public TyInt newIntType(SymInfo s){
        return new TyInt(s);
    }
    public TyFloat newFloatType(SymInfo s){
        return new TyFloat(s);
    }
    public TyBool newBooleanType(SymInfo s){
        return new TyBool(s);
    }
    public TyChar newCharType(SymInfo s){
        return new TyChar(s);
    }
    public TyString newStringType(SymInfo s){
        return new TyString(s);
    }
    public TyLang newLangType(SymInfo s){
        return new TyLang(s);
    }
    public TyGrammar newGrammarType(SymInfo s){
        return new TyGrammar(s);
    }
    public TyMetaTy newMetaType(SymInfo s){
        return new TyMetaTy(s);
    }
    public TyMetaExpr newMetaTypeExpr(SymInfo s){
        return new TyMetaExpr(s);
    }
    public TyMetaPeg newMetaTypePeg(SymInfo s){
        return new TyMetaPeg(s);
    }
    public Grammar newGrammar(SymInfo s, String name, GrammarOption opts, List<RulePEG> rules) {
    	return new Grammar(s, name, opts, rules);
    }
    
    public Expr newLeftAssocBinOpList(List<Expr> l, List<BinOPFactory> funcs){
	    Expr root = null;
	    if(l.size() >= 2) {
	        BinOPFactory f = funcs.remove(0);
	        root = f.newOP(l.remove(0), l.remove(0));
	        for(Expr e : l){
		        f = funcs.remove(0);
		        root = f.newOP(root,e);
	        }
	    }else if(l.size() == 1){
	       return l.remove(0);
	    }
        return root;
    }


}
