package apeg.ast;

import apeg.ast.expr.*;
import apeg.ast.expr.operators.*;
import apeg.ast.rules.*;
import apeg.ast.types.*;
import apeg.util.*;
import java.util.List;
import apeg.ast.Grammar.GrammarOption;

public class ASTFactoryImpl implements ASTFactory{

    public Add newAddExpr(SymInfo s,Expr l,Expr r){
        return new Add(s,l,r);
    }
    public Sub newSubExpr(SymInfo s,Expr l,Expr r){
        return new Sub(s,l,r);
    }

    public Mod newModExpr(SymInfo s,Expr l,Expr r){
        return new Mod(s,l,r);
    }
    
    public Mult newMultExpr(SymInfo s,Expr l,Expr r){
        return new Mult(s,l,r);
    }
    public Div newDivExpr(SymInfo s,Expr l,Expr r){
        return new Div(s,l,r);
    }
    public And newAndExpr(SymInfo s,Expr l,Expr r){
        return new And(s,l,r);
    }
    public Or newOrExpr(SymInfo s,Expr l,Expr r){
        return new Or(s,l,r);
    }
    public Less newLessExpr(SymInfo s,Expr l,Expr r){
        return new Less(s,l,r);
    }
    public Equals newEqualityExpr(SymInfo s,Expr l,Expr r){
        return new Equals(s,l,r);
    }
    public Greater newGreaterExpr(SymInfo s,Expr l,Expr r){
        return new Greater(s,l,r);
    }
    public LessEq newLessEqExpr(SymInfo s,Expr l,Expr r){
        return new LessEq(s,l,r);
    }
    public GreaterEq newGreaterEqExpr(SymInfo s,Expr l,Expr r){
        return new GreaterEq(s,l,r);
    }
    public NotEq newNotEqExpr(SymInfo s,Expr l, Expr r){
        return new NotEq(s,l, r);
    }
    public Concat newConcatExpr(SymInfo s,Expr l,Expr r){
        return new Concat(s,l,r);
    }
    public Compose newComposeExpr(SymInfo s,Expr l,Expr r){
        return new Compose(s,l,r);
    }
    public MetaNot newMetaNot(SymInfo s, Expr e){
        return new MetaNot(s, e);
    }
    public MetaUMinus newMetaUMinus(SymInfo s,Expr e){
        return new MetaUMinus(s, e);
    }
    public MapAcces newMapAcces(SymInfo s,Expr map,Expr index){
        return new MapAcces(s,map,index);
    }
    public MapExtension newMapExtension(SymInfo s,Expr map,Expr key,Expr value){
        return new MapExtension(s,map,key,value);
    }
    public Not newNotExpr(SymInfo s,Expr e){
        return new Not(s,e);
    }
    public UMinus newUMinusExpr(SymInfo s,Expr e){
        return new UMinus(s,e);
    }
    public Attribute newAttributeExpr(SymInfo s,String name){
        return new Attribute(s,name);
    }
    public AttributeGrammar newAttributeGrammarExpr(SymInfo s){
        return new AttributeGrammar(s);
    }    
    public IntLit newIntExpr(SymInfo s,int value){
        return new IntLit(s,value);
    }
    public FloatLit newFloatExpr(SymInfo s,float value){
        return new FloatLit(s,value);
    }
    public CharLit newCharExpr(SymInfo s,char value){
        return new CharLit(s,value);
    }
    public BoolLit newBooleanExpr(SymInfo s,boolean value){
        return new BoolLit(s,value);
    }
    public StrLit newStringExpr(SymInfo s,String value){
        return new StrLit(s,value);
    }
    public MapLit newMapExpr(SymInfo s,Pair<Expr,Expr>[] assocs){
        return new MapLit(s,assocs);
    }
    public MetaAdd newMetaAdd(SymInfo s,Expr ml,Expr mr){
        return new MetaAdd(s,ml,mr);
    }
    public MetaSub newMetaSub(SymInfo s,Expr ml,Expr mr){
        return new MetaSub(s,ml,mr);
    }
    public MetaMod newMetaMod(SymInfo s,Expr ml,Expr mr){
        return new MetaMod(s,ml,mr);
    }
    public MetaMult newMetaMult(SymInfo s,Expr ml,Expr mr){
        return new MetaMult(s,ml,mr);
    }
    public MetaDiv newMetaDiv(SymInfo s,Expr ml,Expr mr){
        return new MetaDiv(s,ml,mr);
    }
    public MetaAnd newMetaAnd(SymInfo s,Expr ml,Expr mr){
        return new MetaAnd(s,ml,mr);
    }
    public MetaOr newMetaOr(SymInfo s,Expr ml,Expr mr){
        return new MetaOr(s,ml,mr);
    }
    public MetaLess newMetaLess(SymInfo s,Expr ml,Expr mr){
        return new MetaLess(s,ml,mr);
    }
    public MetaEquals newMetaEquals(SymInfo s,Expr ml,Expr mr){
        return new MetaEquals(s,ml,mr);
    }
    public MetaGreater newMetaGreater(SymInfo s,Expr ml,Expr mr){
        return new MetaGreater(s,ml,mr);
    }
    public MetaLessEq newMetaLessEq(SymInfo s,Expr ml,Expr mr){
        return new MetaLessEq(s,ml,mr);
    }
    public MetaGreaterEq newMetaGreaterEq(SymInfo s,Expr ml,Expr mr){
        return new MetaGreaterEq(s,ml,mr);
    }
    public MetaNotEq newMetaNotEq(SymInfo s,Expr ml,Expr mr){
        return new MetaNotEq(s,ml,mr);
    }
    public MetaConcat newMetaConcat(SymInfo s,Expr ml,Expr mr){
        return new MetaConcat(s,ml,mr);
    }
    public MetaCompose newMetaCompose(SymInfo s,Expr ml,Expr mr){
        return new MetaCompose(s,ml,mr);
    }
    public MetaMapAcces newMetaMapAcces(SymInfo s,Expr map,Expr index){
        return new MetaMapAcces(s,map,index);
    }
    public MetaMapExtension newMetaMapExtension(SymInfo s,Expr map,Expr key,Expr value){
        return new MetaMapExtension(s,map,key,value);
    }
    public MetaAttribute newMetaAttribute(SymInfo s,Expr name){
        return new MetaAttribute(s,name);
    }
    public MetaIntLit newMetaIntLit(SymInfo s,Expr value){
        return new MetaIntLit(s,value);
    }
    public MetaFloatLit newMetaFloatLit(SymInfo s,Expr value){
        return new MetaFloatLit(s,value);
    }
    public MetaCharLit newMetaCharLit(SymInfo s,Expr value){
        return new MetaCharLit(s,value);
    }
    public MetaBoolLit newMetaBoolLit(SymInfo s,Expr value){
        return new MetaBoolLit(s,value);
    }
    public MetaStrLit newMetaStrLit(SymInfo s,Expr value){
        return new MetaStrLit(s,value);
    }
    public MetaMapLit newMetaMapLit(SymInfo s,Expr assocs){
        return new MetaMapLit(s,assocs);
    }
    public MetaVar newMetaVar(SymInfo s,Expr name){
        return new MetaVar(s,name);
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
    public MetaSeqPEG newMetaSeqPEG(SymInfo s,Expr p){
        return new MetaSeqPEG(s,p);
    }
    public MetaRulePEG newMetaRulePEG(SymInfo s,Expr ruleName,Expr anno,Expr inh,Expr syn,Expr peg){
        return new MetaRulePEG(s,ruleName,anno,inh,syn,peg);
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
    public MetaTyMeta newMetaTyMeta(SymInfo s){
        return new MetaTyMeta(s);
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
    public TyMeta newMetaType(SymInfo s){
        return new TyMeta(s);
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
	    BinaryOP root = null;
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
