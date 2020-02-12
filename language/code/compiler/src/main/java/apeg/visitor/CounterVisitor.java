package apeg.visitor;

import apeg.visitor.*;
import apeg.ast.*;
import apeg.ast.expr.*;
import apeg.ast.expr.operators.*;
import apeg.ast.rules.*;
import apeg.ast.types.*;

import java.util.*;

public class CounterVisitor extends Visitor{
	
	int expr[] = new int[7];
	int operators[] = new int[18];
	int rules[] = new int[15];
	int types[] = new int[9];
	
	public CounterVisitor() {
		
		
		for(int i =0; i<7; i++) {
			expr[i] = 0;
		}
		for(int i =0; i<18; i++) {
			operators[i] = 0;
		}
		for(int i = 0; i<15; i++) {
			rules[1] = 0;
		}
		for(int i = 0; i< 9; i++) {
			types[i] = 0;
		}
		
	}

	@Override
	public void visit(Attribute n) {
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
	public void visit(MetaChoiceList n) {
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
	public void visit(MetaKleneePEG n) {
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
	public void visit(MetaPKlenee n) {
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
	public void visit(MetaTyMeta n) {
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
	public void visit(ChoiceList n) {
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
	public void visit(KleneePEG n) {
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
	public void visit(PKlenee n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(RulePEG n) {
		// TODO Auto-generated method stub
		
		System.out.println("Rule : " + n.getRuleName());
		System.out.println("Number of syn attributes: " + n.getSyn().size());
		System.out.println("Number of inh attributes: " + n.getInh().size());
		
		
		
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
	public void visit(TyString n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Grammar n) {
		// TODO Auto-generated method stub
		
		System.out.println("Grammar " + n.getName());
		System.out.println("Options: " + n.getOptions());
		
		for(RulePEG r:n.getRules()) {
			
			r.accept(this);
		}
		
		
		
	}

}
