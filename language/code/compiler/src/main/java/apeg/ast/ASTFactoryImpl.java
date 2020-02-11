package apeg.ast;

public class ASTFactoryImpl extends ASTFactory{

    public Add newAdd(SymInfo s,Expr l,Expr r){
        return new Add(s,l,r);
    }
    public Sub newSub(SymInfo s,Expr l,Expr r){
        return new Sub(s,l,r);
    }
    public Mult newMult(SymInfo s,Expr l,Expr r){
        return new Mult(s,l,r);
    }
    public Div newDiv(SymInfo s,Expr l,Expr r){
        return new Div(s,l,r);
    }
    public And newAnd(SymInfo s,Expr l,Expr r){
        return new And(s,l,r);
    }
    public Or newOr(SymInfo s,Expr l,Expr r){
        return new Or(s,l,r);
    }
    public Less newLess(SymInfo s,Expr l,Expr r){
        return new Less(s,l,r);
    }
    public Equals newEquals(SymInfo s,Expr l,Expr r){
        return new Equals(s,l,r);
    }
    public Greater newGreater(SymInfo s,Expr l,Expr r){
        return new Greater(s,l,r);
    }
    public LessEq newLessEq(SymInfo s,Expr l,Expr r){
        return new LessEq(s,l,r);
    }
    public GreaterEq newGreaterEq(SymInfo s,Expr l,Expr r){
        return new GreaterEq(s,l,r);
    }
    public NotEq newNotEq(SymInfo s,Expr l,Expr r){
        return new NotEq(s,l,r);
    }
    public Concat newConcat(SymInfo s,Expr l,Expr r){
        return new Concat(s,l,r);
    }
    public Compose newCompose(SymInfo s,Expr l,Expr r){
        return new Compose(s,l,r);
    }
    public MetaNot newMetaNot(SymInfo s,Expr l,Expr r,Expr e){
        return new MetaNot(s,l,r,e);
    }
    public MetaUMinus newMetaUMinus(SymInfo s,Expr l,Expr r,Expr e){
        return new MetaUMinus(s,l,r,e);
    }
    public MapAcces newMapAcces(SymInfo s,Expr map,Expr index){
        return new MapAcces(s,map,index);
    }
    public MapExtension newMapExtension(SymInfo s,Expr map,Expr key,Expr value){
        return new MapExtension(s,map,key,value);
    }
    public Not newNot(SymInfo s,Expr e){
        return new Not(s,e);
    }
    public UMinus newUMinus(SymInfo s,Expr e){
        return new UMinus(s,e);
    }
    public Attribute newAttribute(SymInfo s,String name){
        return new Attribute(s,name);
    }
    public IntLit newIntLit(SymInfo s,int value){
        return new IntLit(s,value);
    }
    public FloatLit newFloatLit(SymInfo s,float value){
        return new FloatLit(s,value);
    }
    public CharLit newCharLit(SymInfo s,char value){
        return new CharLit(s,value);
    }
    public BoolLit newBoolLit(SymInfo s,boolean value){
        return new BoolLit(s,value);
    }
    public StrLit newStrLit(SymInfo s,String value){
        return new StrLit(s,value);
    }
    public MapLit newMapLit(SymInfo s,Pair<Expr,Expr>[] assocs){
        return new MapLit(s,assocs);
    }
    public MetaAdd newMetaAdd(SymInfo s,MetaExpr ml,MetaExpr mr){
        return new MetaAdd(s,ml,mr);
    }
    public MetaSub newMetaSub(SymInfo s,MetaExpr ml,MetaExpr mr){
        return new MetaSub(s,ml,mr);
    }
    public MetaMult newMetaMult(SymInfo s,MetaExpr ml,MetaExpr mr){
        return new MetaMult(s,ml,mr);
    }
    public MetaDiv newMetaDiv(SymInfo s,MetaExpr ml,MetaExpr mr){
        return new MetaDiv(s,ml,mr);
    }
    public MetaAnd newMetaAnd(SymInfo s,MetaExpr ml,MetaExpr mr){
        return new MetaAnd(s,ml,mr);
    }
    public MetaOr newMetaOr(SymInfo s,MetaExpr ml,MetaExpr mr){
        return new MetaOr(s,ml,mr);
    }
    public MetaLess newMetaLess(SymInfo s,MetaExpr ml,MetaExpr mr){
        return new MetaLess(s,ml,mr);
    }
    public MetaEquals newMetaEquals(SymInfo s,MetaExpr ml,MetaExpr mr){
        return new MetaEquals(s,ml,mr);
    }
    public MetaGreater newMetaGreater(SymInfo s,MetaExpr ml,MetaExpr mr){
        return new MetaGreater(s,ml,mr);
    }
    public MetaLessEq newMetaLessEq(SymInfo s,MetaExpr ml,MetaExpr mr){
        return new MetaLessEq(s,ml,mr);
    }
    public MetaGreaterEq newMetaGreaterEq(SymInfo s,MetaExpr ml,MetaExpr mr){
        return new MetaGreaterEq(s,ml,mr);
    }
    public MetaNotEq newMetaNotEq(SymInfo s,MetaExpr ml,MetaExpr mr){
        return new MetaNotEq(s,ml,mr);
    }
    public MetaConcat newMetaConcat(SymInfo s,MetaExpr ml,MetaExpr mr){
        return new MetaConcat(s,ml,mr);
    }
    public MetaCompose newMetaCompose(SymInfo s,MetaExpr ml,MetaExpr mr){
        return new MetaCompose(s,ml,mr);
    }
    public MetaMapAcces newMetaMapAcces(SymInfo s,Expr map,Expr index){
        return new MetaMapAcces(s,map,index);
    }
    public MetaMapExtension newMetaMapExtension(SymInfo s,Expr map,Expr key,Expr value){
        return new MetaMapExtension(s,map,key,value);
    }
    public MetaAttribute newMetaAttribute(SymInfo s,String name){
        return new MetaAttribute(s,name);
    }
    public MetaIntLit newMetaIntLit(SymInfo s,int value){
        return new MetaIntLit(s,value);
    }
    public MetaFloatLit newMetaFloatLit(SymInfo s,float value){
        return new MetaFloatLit(s,value);
    }
    public MetaCharLit newMetaCharLit(SymInfo s,char value){
        return new MetaCharLit(s,value);
    }
    public MetaBoolLit newMetaBoolLit(SymInfo s,boolean value){
        return new MetaBoolLit(s,value);
    }
    public MetaStrLit newMetaStrLit(SymInfo s,String value){
        return new MetaStrLit(s,value);
    }
    public MetaMapLit newMetaMapLit(SymInfo s,Pair<Expr,Expr>[] assocs){
        return new MetaMapLit(s,assocs);
    }
    public MetaVar newMetaVar(SymInfo s,String name){
        return new MetaVar(s,name);
    }
    public MetaAndPEG newMetaAndPEG(SymInfo s,MetaAPEG e){
        return new MetaAndPEG(s,e);
    }
    public MetaNotPEG newMetaNotPEG(SymInfo s,MetaAPEG e){
        return new MetaNotPEG(s,e);
    }
    public MetaKleneePEG newMetaKleneePEG(SymInfo s,MetaAPEG e){
        return new MetaKleneePEG(s,e);
    }
    public MetaOptionalPEG newMetaOptionalPEG(SymInfo s,MetaAPEG e){
        return new MetaOptionalPEG(s,e);
    }
    public MetaPKlenee newMetaPKlenee(SymInfo s,MetaAPEG e){
        return new MetaPKlenee(s,e);
    }
    public MetaAnyPEG newMetaAnyPEG(SymInfo s){
        return new MetaAnyPEG(s);
    }
    public MetaLitPEG newMetaLitPEG(SymInfo s,String lit){
        return new MetaLitPEG(s,lit);
    }
    public MetaConstraintPEG newMetaConstraintPEG(SymInfo s,MetaExpr e){
        return new MetaConstraintPEG(s,e);
    }
    public MetaChoiceList newMetaChoiceList(SymInfo s,CharInterval i){
        return new MetaChoiceList(s,i);
    }
    public MetaNonterminalPEG newMetaNonterminalPEG(SymInfo s,String name,List<MetaExpr> args){
        return new MetaNonterminalPEG(s,name,args);
    }
    public MetaChoicePEG newMetaChoicePEG(SymInfo s,MetaAPEG leftPeg,MetaAPEG rightPeg){
        return new MetaChoicePEG(s,leftPeg,rightPeg);
    }
    public MetaSeqPEG newMetaSeqPEG(SymInfo s,MetaAPEG[] p){
        return new MetaSeqPEG(s,p);
    }
    public MetaRulePEG newMetaRulePEG(SymInfo s,String ruleName,RulePEG.Annotation anno,List<Pair<MetaType,String>> inh,List<Pair<MetaType,MetaExpr>> syn,MetaAPEG peg){
        return new MetaRulePEG(s,ruleName,anno,inh,syn,peg);
    }
    public MetaUpdatePeg newMetaUpdatePeg(SymInfo s,List<Pair<MetaAttribute,MetaExpr>> assigs){
        return new MetaUpdatePeg(s,assigs);
    }
    public MetaBindPEG newMetaBindPEG(SymInfo s,String attribute,MetaExpr e){
        return new MetaBindPEG(s,attribute,e);
    }
    public MetaTyMap newMetaTyMap(SymInfo s,MetaType tyParameter){
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
    public RulePEG newRulePEG(SymInfo s,String ruleName,RulePEG.Annotation anno,List<Pair<Type,String>> inh,List<Pair<Type,Expr>> syn,APEG peg){
        return new RulePEG(s,ruleName,anno,inh,syn,peg);
    }
    public AndPEG newAndPEG(SymInfo s,APEG e){
        return new AndPEG(s,e);
    }
    public NotPEG newNotPEG(SymInfo s,APEG e){
        return new NotPEG(s,e);
    }
    public KleneePEG newKleneePEG(SymInfo s,APEG e){
        return new KleneePEG(s,e);
    }
    public OptionalPEG newOptionalPEG(SymInfo s,APEG e){
        return new OptionalPEG(s,e);
    }
    public PKlenee newPKlenee(SymInfo s,APEG e){
        return new PKlenee(s,e);
    }
    public ChoicePEG newChoicePEG(SymInfo s,APEG leftPeg,APEG rightPeg){
        return new ChoicePEG(s,leftPeg,rightPeg);
    }
    public SeqPEG newSeqPEG(SymInfo s,APEG[] p){
        return new SeqPEG(s,p);
    }
    public UpdatePeg newUpdatePeg(SymInfo s,List<Pair<Attribute,Expr>> assigs){
        return new UpdatePeg(s,assigs);
    }
    public BindPEG newBindPEG(SymInfo s,String attribute,Expr e){
        return new BindPEG(s,attribute,e);
    }
    public ChoiceList newChoiceList(SymInfo s,CharInterval i){
        return new ChoiceList(s,i);
    }
    public AnyPEG newAnyPEG(SymInfo s){
        return new AnyPEG(s);
    }
    public LitPEG newLitPEG(SymInfo s,String lit){
        return new LitPEG(s,lit);
    }
    public ConstraintPEG newConstraintPEG(SymInfo s,Expr e){
        return new ConstraintPEG(s,e);
    }
    public NonterminalPEG newNonterminalPEG(SymInfo s,String name,List<Expr> args){
        return new NonterminalPEG(s,name,args);
    }
    public TyMap newTyMap(SymInfo s,Type tyParameter){
        return new TyMap(s,tyParameter);
    }
    public TyInt newTyInt(SymInfo s){
        return new TyInt(s);
    }
    public TyFloat newTyFloat(SymInfo s){
        return new TyFloat(s);
    }
    public TyBool newTyBool(SymInfo s){
        return new TyBool(s);
    }
    public TyChar newTyChar(SymInfo s){
        return new TyChar(s);
    }
    public TyString newTyString(SymInfo s){
        return new TyString(s);
    }
    public TyLang newTyLang(SymInfo s){
        return new TyLang(s);
    }
    public TyGrammar newTyGrammar(SymInfo s){
        return new TyGrammar(s);
    }
    public TyMeta newTyMeta(SymInfo s){
        return new TyMeta(s);
    }

}
