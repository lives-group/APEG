package apeg.visitor;

import apeg.ast.expr.operators.*;
import apeg.ast.expr.*;
import apeg.ast.rules.*;
import apeg.ast.types.*;
import apeg.ast.*;

public abstract class Visitor {

    /**
     * Visit method for each AST element
     */
    // Expressions
    public abstract void visit(Attribute n);
    public abstract void visit(AttributeGrammar n);    
    public abstract void visit(BoolLit n);
    public abstract void visit(CharLit n);
    public abstract void visit(FloatLit n);
    public abstract void visit(IntLit n);
    public abstract void visit(MapLit n);
    public abstract void visit(StrLit n);

    // Meta Expressions
    public abstract void visit(MetaAndPEG n);
    public abstract void visit(MetaAnyPEG n);
    public abstract void visit(MetaAttribute n);
    public abstract void visit(MetaBindPEG n);
    public abstract void visit(MetaBoolLit n);
    public abstract void visit(MetaCharLit n);
    public abstract void visit(MetaChoiceList n);
    public abstract void visit(MetaChoicePEG n);
    public abstract void visit(MetaConstraintPEG n);
    public abstract void visit(MetaFloatLit n);
    public abstract void visit(MetaIntLit n);
    public abstract void visit(MetaKleenePEG n);
    public abstract void visit(MetaLitPEG n);
    public abstract void visit(MetaMapLit n);
    public abstract void visit(MetaNonterminalPEG n);
    public abstract void visit(MetaNotPEG n);
    public abstract void visit(MetaOptionalPEG n);
    public abstract void visit(MetaPKleene n);
    public abstract void visit(MetaRulePEG n);
    public abstract void visit(MetaSeqPEG n);
    public abstract void visit(MetaStrLit n);
    public abstract void visit(MetaTyBool n);
    public abstract void visit(MetaTyChar n);
    public abstract void visit(MetaTyFloat n);
    public abstract void visit(MetaTyGrammar n);
    public abstract void visit(MetaTyInt n);
    public abstract void visit(MetaTyLang n);
    public abstract void visit(MetaTyMap n);
    public abstract void visit(MetaTyMeta n);
    public abstract void visit(MetaTyString n);
    public abstract void visit(MetaUpdatePEG n);
    public abstract void visit(MetaVar n);

    // Operators
    public abstract void visit(Add n);
    public abstract void visit(And n);
    public abstract void visit(Compose n);
    public abstract void visit(Concat n);
    public abstract void visit(Div n);
    public abstract void visit(Equals n);
    public abstract void visit(Greater n);
    public abstract void visit(GreaterEq n);
    public abstract void visit(Less n);
    public abstract void visit(LessEq n);
    public abstract void visit(MapAcces n);
    public abstract void visit(MapExtension n);
    public abstract void visit(Mod n);    
    public abstract void visit(Mult n);
    public abstract void visit(Not n);
    public abstract void visit(NotEq n);
    public abstract void visit(Or n);
    public abstract void visit(Sub n);
    public abstract void visit(UMinus n);

    // Meta operators
    public abstract void visit(MetaAdd n);
    public abstract void visit(MetaAnd n);
    public abstract void visit(MetaCompose n);
    public abstract void visit(MetaConcat n);
    public abstract void visit(MetaDiv n);
    public abstract void visit(MetaEquals n);
    public abstract void visit(MetaGreater n);
    public abstract void visit(MetaGreaterEq n);
    public abstract void visit(MetaLess n);
    public abstract void visit(MetaLessEq n);
    public abstract void visit(MetaMapAcces n);
    public abstract void visit(MetaMapExtension n);
    public abstract void visit(MetaMod n);    
    public abstract void visit(MetaMult n);
    public abstract void visit(MetaNot n);
    public abstract void visit(MetaNotEq n);
    public abstract void visit(MetaOr n);
    public abstract void visit(MetaSub n);
    public abstract void visit(MetaUMinus n);    

    // PEG Expressions
    public abstract void visit(AndPEG n);
    public abstract void visit(AnyPEG n);
    public abstract void visit(BindPEG n);
    public abstract void visit(ChoiceList n);
    public abstract void visit(ChoicePEG n);
    public abstract void visit(ConstraintPEG n);
    public abstract void visit(KleenePEG n);
    public abstract void visit(LambdaPEG n);
    public abstract void visit(LitPEG n);
    public abstract void visit(NonterminalPEG n);
    public abstract void visit(NotPEG n);
    public abstract void visit(OptionalPEG n);
    public abstract void visit(PKleene n);
    public abstract void visit(RulePEG n);
    public abstract void visit(SeqPEG n);
    public abstract void visit(UpdatePEG n);
        
    // Types
    public abstract void visit(TyBool n);
    public abstract void visit(TyChar n);
    public abstract void visit(TyFloat n);
    public abstract void visit(TyGrammar n);
    public abstract void visit(TyInt n);
    public abstract void visit(TyLang n);
    public abstract void visit(TyMap n);
    public abstract void visit(TyMeta n);
    public abstract void visit(TyString n);
    
    //Others
    public abstract void visit(Grammar n);
}
