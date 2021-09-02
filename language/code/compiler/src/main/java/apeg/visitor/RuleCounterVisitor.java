package apeg.visitor;

import apeg.ast.*;
import apeg.ast.expr.*;
import apeg.ast.expr.operators.*;
import apeg.ast.rules.*;
import apeg.ast.types.*;

public class RuleCounterVisitor extends Visitor {
	
	private int counter;
	
	public RuleCounterVisitor() {
		counter =0;
	}

	@Override
	public void visit(Attribute n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(AttributeGrammar n) {
		// TODO Auto-generated method stub
		
	}
    
	@Override
	public void visit(BoolLit n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(CharLit n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(FloatLit n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(IntLit n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MapLit n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(StrLit n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MetaAndPEG n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MetaAnyPEG n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MetaAttribute n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MetaBindPEG n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MetaBoolLit n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MetaCharLit n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MetaRangePEG n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MetaChoicePEG n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MetaConstraintPEG n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MetaFloatLit n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MetaIntLit n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MetaKleenePEG n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MetaLitPEG n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MetaMapLit n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MetaNonterminalPEG n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MetaNotPEG n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MetaOptionalPEG n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MetaPKleene n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MetaRulePEG n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MetaSeqPEG n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MetaStrLit n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MetaTyBool n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MetaTyChar n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MetaTyFloat n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MetaTyGrammar n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MetaTyInt n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MetaTyLang n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MetaTyMap n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MetaTyTy n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MetaTyString n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MetaUpdatePEG n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MetaVar n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Add n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(And n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Compose n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Concat n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Div n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Equals n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Greater n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(GreaterEq n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Less n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(LessEq n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MapAcces n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MapExtension n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Mult n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Mod n) {
		// TODO Auto-generated method stub
		
	}    
    
	@Override
	public void visit(Not n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(NotEq n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Or n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Sub n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(UMinus n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MetaAdd n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MetaAnd n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MetaCompose n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MetaConcat n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MetaDiv n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MetaEquals n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MetaGreater n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MetaGreaterEq n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MetaLess n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MetaLessEq n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MetaMapAcces n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MetaMapExtension n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MetaMult n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MetaMod n) {
		// TODO Auto-generated method stub
		
	}
    
	@Override
	public void visit(MetaNot n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MetaNotEq n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MetaOr n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MetaSub n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MetaUMinus n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(AndPEG n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(AnyPEG n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(BindPEG n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(RangePEG n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ChoicePEG n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ConstraintPEG n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(KleenePEG n) {
		// TODO Auto-generated method stub
		
	}

        @Override
	public void visit(LambdaPEG n) {
	    // TODO Auto-generated method stub
	}

	@Override
	public void visit(LitPEG n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(NonterminalPEG n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(NotPEG n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(OptionalPEG n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(PKleene n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(RulePEG n) {
		// TODO Auto-generated method stub
		counter ++;
	}

	@Override
	public void visit(SeqPEG n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(UpdatePEG n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(TyBool n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(TyChar n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(TyFloat n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(TyGrammar n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(TyInt n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(TyLang n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(TyMap n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(TyMeta n) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void visit(TyMetaExpr n) {
		// TODO Auto-generated method stub
	}

	@Override
	public void visit(TyMetaPeg n) {
		// TODO Auto-generated method stub

	}
	@Override
	public void visit(TyString n) {
		// TODO Auto-generated method stub
		
	}
	
	public void visit(TyMetaTy n){}

	@Override
	public void visit(Grammar n) {
		// TODO Auto-generated method stub
		System.out.println(n.getRules().size());
	}
	
	public void report () {
		System.out.println("Número de regras:" + counter);
	}
	
	public void visit(TyList n){}
	public void visit(ListAcces n){}
	public void visit(ListLit n){}
}
