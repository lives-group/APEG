
import apeg.ast.expr.*;
import apeg.ast.expr.operators.*;
import apeg.ast.rules.*;
import apeg.ast.types.*;
import apeg.util.*;
import apeg.ast.*;

public class ASTFactoryImpl implements ASTFactory{

    public Add newAdd(){
        return new Add();
    }
    public Sub newSub(){
        return new Sub();
    }
    public Mult newMult(){
        return new Mult();
    }
    public Div newDiv(){
        return new Div();
    }
    public And newAnd(){
        return new And();
    }
    public Or newOr(){
        return new Or();
    }
    public Less newLess(){
        return new Less();
    }
    public Equals newEquals(){
        return new Equals();
    }
    public Greater newGreater(){
        return new Greater();
    }
    public LessEq newLessEq(){
        return new LessEq();
    }
    public GreaterEq newGreaterEq(){
        return new GreaterEq();
    }
    public NotEq newNotEq(){
        return new NotEq();
    }
    public Concat newConcat(){
        return new Concat();
    }
    public Compose newCompose(){
        return new Compose();
    }
    public MetaNot newMetaNot(Expr e){
        return new MetaNot(e);
    }
    public MetaUMinus newMetaUMinus(Expr e){
        return new MetaUMinus(e);
    }
    public MapAcces newMapAcces(Expr map,Expr index){
        return new MapAcces(map,index);
    }
    public MapExtension newMapExtension(Expr map,Expr key,Expr value){
        return new MapExtension(map,key,value);
    }
    public Not newNot(Expr e){
        return new Not(e);
    }
    public UMinus newUMinus(Expr e){
        return new UMinus(e);
    }
    public Attribute newAttribute(String name){
        return new Attribute(name);
    }
    public IntLit newIntLit(int value){
        return new IntLit(value);
    }
    public FloatLit newFloatLit(float value){
        return new FloatLit(value);
    }
    public CharLit newCharLit(char value){
        return new CharLit(value);
    }
    public BoolLit newBoolLit(boolean value){
        return new BoolLit(value);
    }
    public StrLit newStrLit(String value){
        return new StrLit(value);
    }
    public MapLit newMapLit(Pair<Expr,Expr>[] assocs){
        return new MapLit(assocs);
    }
    public MetaAdd newMetaAdd(){
        return new MetaAdd();
    }
    public MetaSub newMetaSub(){
        return new MetaSub();
    }
    public MetaMult newMetaMult(){
        return new MetaMult();
    }
    public MetaDiv newMetaDiv(){
        return new MetaDiv();
    }
    public MetaAnd newMetaAnd(){
        return new MetaAnd();
    }
    public MetaOr newMetaOr(){
        return new MetaOr();
    }
    public MetaLess newMetaLess(){
        return new MetaLess();
    }
    public MetaEquals newMetaEquals(){
        return new MetaEquals();
    }
    public MetaGreater newMetaGreater(){
        return new MetaGreater();
    }
    public MetaLessEq newMetaLessEq(){
        return new MetaLessEq();
    }
    public MetaGreaterEq newMetaGreaterEq(){
        return new MetaGreaterEq();
    }
    public MetaNotEq newMetaNotEq(){
        return new MetaNotEq();
    }
    public MetaConcat newMetaConcat(){
        return new MetaConcat();
    }
    public MetaCompose newMetaCompose(){
        return new MetaCompose();
    }
    public MetaMapAcces newMetaMapAcces(Expr map,Expr index){
        return new MetaMapAcces(map,index);
    }
    public MetaMapExtension newMetaMapExtension(Expr map,Expr key,Expr value){
        return new MetaMapExtension(map,key,value);
    }
    public MetaAttribute newMetaAttribute(String name){
        return new MetaAttribute(name);
    }
    public MetaIntLit newMetaIntLit(int value){
        return new MetaIntLit(value);
    }
    public MetaFloatLit newMetaFloatLit(float value){
        return new MetaFloatLit(value);
    }
    public MetaCharLit newMetaCharLit(char value){
        return new MetaCharLit(value);
    }
    public MetaBoolLit newMetaBoolLit(boolean value){
        return new MetaBoolLit(value);
    }
    public MetaStrLit newMetaStrLit(String value){
        return new MetaStrLit(value);
    }
    public MetaMapLit newMetaMapLit(Pair<Expr,Expr>[] assocs){
        return new MetaMapLit(assocs);
    }
    public MetaVar newMetaVar(String name){
        return new MetaVar(name);
    }
    public MetaAndPEG newMetaAndPEG(MetaAPEG e){
        return new MetaAndPEG(e);
    }
    public MetaNotPEG newMetaNotPEG(MetaAPEG e){
        return new MetaNotPEG(e);
    }
    public MetaKleneePEG newMetaKleneePEG(MetaAPEG e){
        return new MetaKleneePEG(e);
    }
    public MetaOptionalPEG newMetaOptionalPEG(MetaAPEG e){
        return new MetaOptionalPEG(e);
    }
    public MetaPKlenee newMetaPKlenee(MetaAPEG e){
        return new MetaPKlenee(e);
    }
    public MetaAnyPEG newMetaAnyPEG(){
        return new MetaAnyPEG();
    }
    public MetaLitPEG newMetaLitPEG(String lit){
        return new MetaLitPEG(lit);
    }
    public MetaConstraintPEG newMetaConstraintPEG(MetaExpr e){
        return new MetaConstraintPEG(e);
    }
    public MetaChoiceList newMetaChoiceList(CharInterval i){
        return new MetaChoiceList(i);
    }
    public MetaNonterminalPEG newMetaNonterminalPEG(String name,List<MetaExpr> args){
        return new MetaNonterminalPEG(name,args);
    }
    public MetaChoicePEG newMetaChoicePEG(MetaAPEG leftPeg,MetaAPEG rightPeg){
        return new MetaChoicePEG(leftPeg,rightPeg);
    }
    public MetaSeqPEG newMetaSeqPEG(MetaAPEG[] p){
        return new MetaSeqPEG(p);
    }
    public MetaRulePEG newMetaRulePEG(String ruleName,RulePEG.Annotation anno,List<Pair<MetaType,String>> inh,List<Pair<MetaType,MetaExpr>> syn,MetaAPEG peg){
        return new MetaRulePEG(ruleName,anno,inh,syn,peg);
    }
    public MetaUpdatePeg newMetaUpdatePeg(List<Pair<MetaAttribute,MetaExpr>> assigs){
        return new MetaUpdatePeg(assigs);
    }
    public MetaBindPEG newMetaBindPEG(String attribute,MetaExpr e){
        return new MetaBindPEG(attribute,e);
    }
    public MetaTyMap newMetaTyMap(MetaType tyParameter){
        return new MetaTyMap(tyParameter);
    }
    public MetaTyInt newMetaTyInt(){
        return new MetaTyInt();
    }
    public MetaTyFloat newMetaTyFloat(){
        return new MetaTyFloat();
    }
    public MetaTyBool newMetaTyBool(){
        return new MetaTyBool();
    }
    public MetaTyChar newMetaTyChar(){
        return new MetaTyChar();
    }
    public MetaTyString newMetaTyString(){
        return new MetaTyString();
    }
    public MetaTyLang newMetaTyLang(){
        return new MetaTyLang();
    }
    public MetaTyGrammar newMetaTyGrammar(){
        return new MetaTyGrammar();
    }
    public MetaTyMeta newMetaTyMeta(){
        return new MetaTyMeta();
    }
    public RulePEG newRulePEG(String ruleName,RulePEG.Annotation anno,List<Pair<Type,String>> inh,List<Pair<Type,Expr>> syn,APEG peg){
        return new RulePEG(ruleName,anno,inh,syn,peg);
    }
    public AndPEG newAndPEG(APEG e){
        return new AndPEG(e);
    }
    public NotPEG newNotPEG(APEG e){
        return new NotPEG(e);
    }
    public KleneePEG newKleneePEG(APEG e){
        return new KleneePEG(e);
    }
    public OptionalPEG newOptionalPEG(APEG e){
        return new OptionalPEG(e);
    }
    public PKlenee newPKlenee(APEG e){
        return new PKlenee(e);
    }
    public ChoicePEG newChoicePEG(APEG leftPeg,APEG rightPeg){
        return new ChoicePEG(leftPeg,rightPeg);
    }
    public SeqPEG newSeqPEG(APEG[] p){
        return new SeqPEG(p);
    }
    public UpdatePeg newUpdatePeg(List<Pair<Attribute,Expr>> assigs){
        return new UpdatePeg(assigs);
    }
    public BindPEG newBindPEG(String attribute,Expr e){
        return new BindPEG(attribute,e);
    }
    public ChoiceList newChoiceList(CharInterval i){
        return new ChoiceList(i);
    }
    public AnyPEG newAnyPEG(){
        return new AnyPEG();
    }
    public LitPEG newLitPEG(String lit){
        return new LitPEG(lit);
    }
    public ConstraintPEG newConstraintPEG(Expr e){
        return new ConstraintPEG(e);
    }
    public NonterminalPEG newNonterminalPEG(String name,List<Expr> args){
        return new NonterminalPEG(name,args);
    }
    public TyMap newTyMap(Type tyParameter){
        return new TyMap(tyParameter);
    }
    public TyInt newTyInt(){
        return new TyInt();
    }
    public TyFloat newTyFloat(){
        return new TyFloat();
    }
    public TyBool newTyBool(){
        return new TyBool();
    }
    public TyChar newTyChar(){
        return new TyChar();
    }
    public TyString newTyString(){
        return new TyString();
    }
    public TyLang newTyLang(){
        return new TyLang();
    }
    public TyGrammar newTyGrammar(){
        return new TyGrammar();
    }
    public TyMeta newTyMeta(){
        return new TyMeta();
    }

}