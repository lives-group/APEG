package apeg.visitor.semantics;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import apeg.util.Environment;
import apeg.visitor.semantics.*;
import apeg.ast.Grammar;
import apeg.ast.expr.Attribute;
import apeg.ast.expr.AttributeGrammar;
import apeg.ast.expr.BoolLit;
import apeg.ast.expr.CharLit;
import apeg.ast.expr.Expr;
import apeg.ast.expr.FloatLit;
import apeg.ast.expr.IntLit;
import apeg.ast.expr.MapLit;
import apeg.ast.expr.MetaAndPEG;
import apeg.ast.expr.MetaAnyPEG;
import apeg.ast.expr.MetaAttribute;
import apeg.ast.expr.MetaBindPEG;
import apeg.ast.expr.MetaBoolLit;
import apeg.ast.expr.MetaCharLit;
import apeg.ast.expr.MetaChoiceList;
import apeg.ast.expr.MetaChoicePEG;
import apeg.ast.expr.MetaConstraintPEG;
import apeg.ast.expr.MetaFloatLit;
import apeg.ast.expr.MetaIntLit;
import apeg.ast.expr.MetaKleenePEG;
import apeg.ast.expr.MetaLitPEG;
import apeg.ast.expr.MetaMapLit;
import apeg.ast.expr.MetaNonterminalPEG;
import apeg.ast.expr.MetaNot;
import apeg.ast.expr.MetaNotPEG;
import apeg.ast.expr.MetaOptionalPEG;
import apeg.ast.expr.MetaPKleene;
import apeg.ast.expr.MetaRulePEG;
import apeg.ast.expr.MetaSeqPEG;
import apeg.ast.expr.MetaStrLit;
import apeg.ast.expr.MetaTyBool;
import apeg.ast.expr.MetaTyChar;
import apeg.ast.expr.MetaTyFloat;
import apeg.ast.expr.MetaTyGrammar;
import apeg.ast.expr.MetaTyInt;
import apeg.ast.expr.MetaTyLang;
import apeg.ast.expr.MetaTyMap;
import apeg.ast.expr.MetaTyMeta;
import apeg.ast.expr.MetaTyString;
import apeg.ast.expr.MetaUMinus;
import apeg.ast.expr.MetaUpdatePEG;
import apeg.ast.expr.MetaVar;
import apeg.ast.expr.StrLit;
import apeg.ast.expr.operators.Add;
import apeg.ast.expr.operators.And;
import apeg.ast.expr.operators.Compose;
import apeg.ast.expr.operators.Concat;
import apeg.ast.expr.operators.Div;
import apeg.ast.expr.operators.Equals;
import apeg.ast.expr.operators.Greater;
import apeg.ast.expr.operators.GreaterEq;
import apeg.ast.expr.operators.Less;
import apeg.ast.expr.operators.LessEq;
import apeg.ast.expr.operators.MapAcces;
import apeg.ast.expr.operators.MapExtension;
import apeg.ast.expr.operators.MetaAdd;
import apeg.ast.expr.operators.MetaAnd;
import apeg.ast.expr.operators.MetaCompose;
import apeg.ast.expr.operators.MetaConcat;
import apeg.ast.expr.operators.MetaDiv;
import apeg.ast.expr.operators.MetaEquals;
import apeg.ast.expr.operators.MetaGreater;
import apeg.ast.expr.operators.MetaGreaterEq;
import apeg.ast.expr.operators.MetaLess;
import apeg.ast.expr.operators.MetaLessEq;
import apeg.ast.expr.operators.MetaMapAcces;
import apeg.ast.expr.operators.MetaMapExtension;
import apeg.ast.expr.operators.MetaMod;
import apeg.ast.expr.operators.MetaMult;
import apeg.ast.expr.operators.MetaNotEq;
import apeg.ast.expr.operators.MetaOr;
import apeg.ast.expr.operators.MetaSub;
import apeg.ast.expr.operators.Mod;
import apeg.ast.expr.operators.Mult;
import apeg.ast.expr.operators.Not;
import apeg.ast.expr.operators.NotEq;
import apeg.ast.expr.operators.Or;
import apeg.ast.expr.operators.Sub;
import apeg.ast.expr.operators.UMinus;
import apeg.ast.rules.APEG;
import apeg.ast.rules.AndPEG;
import apeg.ast.rules.AnyPEG;
import apeg.ast.rules.BindPEG;
import apeg.ast.rules.ChoiceList;
import apeg.ast.rules.ChoicePEG;
import apeg.ast.rules.ConstraintPEG;
import apeg.ast.rules.KleenePEG;
import apeg.ast.rules.LambdaPEG;
import apeg.ast.rules.LitPEG;
import apeg.ast.rules.NonterminalPEG;
import apeg.ast.rules.NotPEG;
import apeg.ast.rules.OptionalPEG;
import apeg.ast.rules.PKleene;
import apeg.ast.rules.RulePEG;
import apeg.ast.rules.SeqPEG;
import apeg.ast.rules.UpdatePEG;
import apeg.ast.types.TyBool;
import apeg.ast.types.TyChar;
import apeg.ast.types.TyFloat;
import apeg.ast.types.TyGrammar;
import apeg.ast.types.TyInt;
import apeg.ast.types.TyLang;
import apeg.ast.types.TyMap;
import apeg.ast.types.TyMeta;
import apeg.ast.types.TyString;
import apeg.ast.types.Type;
import apeg.util.Environment;
import apeg.util.Pair;
import apeg.visitor.*;

public class TypeCheckerVisitor extends Visitor {
	
	
	private Stack<VType> s;
	private Environment <String, VType> gamma;
	private Environment<String, NTInfo> global;
	private List<Pair<String, VType>>error;
	private String errorMessage;
	
	public TypeCheckerVisitor() {
		
		s = new Stack<VType>();
		gamma = new Environment<String, VType>();
		global = new Environment<String, NTInfo>();
	
		error = new ArrayList<Pair<String, VType>>();
	}
	
	public void opTyChecker() {
		
		
	}
	
	@Override
	public void visit(Attribute n) {
		
		
		VType t = gamma.get(n.getName());
		
		if (t != null) {
			s.push(t);
		}
		else {
			
			s.push(new TypeError());
			
			errorMessage = "Error at: " + n.getSymInfo().getLine() + n.getSymInfo().getColumn();
			System.out.println(errorMessage);
			error.add(new Pair<String, VType>(errorMessage, new TypeError() ));
			
		}
	}

	@Override
	public void visit(AttributeGrammar n) {
	
		
		VType t = gamma.get(n.getName());
		
		if(t != null) {
			s.push(t);
		}
		else {

			s.push(new TypeError());
			
			errorMessage = "Error at: " + n.getSymInfo().getLine() + n.getSymInfo().getColumn();
			System.out.println(errorMessage);
			error.add(new Pair<String, VType>(errorMessage, new TypeError() ));
			
		}
	}

	@Override
	public void visit(BoolLit n) {
		
		
		s.push(VTyBool.getInstance());
	}

	@Override
	public void visit(CharLit n) {
	
		
		s.push(VTyChar.getInstance());
	}

	@Override
	public void visit(FloatLit n) {
	
		
		s.push(VTyFloat.getInstance());
	}

	@Override
	public void visit(IntLit n) {
	
		
		s.push(VTyInt.getInstance());
	}

	@Override
	public void visit(MapLit n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(StrLit n) {
		
		
		s.push(VTyString.getInstance());
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
		
		n.getLeft().accept(this);
		VType left = s.peek();
		
		n.getRight().accept(this);
		VType right = s.peek();
		
		if(left.match(right) && (left.match(VTyInt.getInstance()) || left.match(VTyFloat.getInstance()))) {
			s.push(left);
		}
		else {

			s.push(new TypeError());
			
			errorMessage = "Error at: " + n.getSymInfo().getLine() + n.getSymInfo().getColumn();
			System.out.println(errorMessage);
			error.add(new Pair<String, VType>(errorMessage, new TypeError() ));
			
		}
	}

	@Override
	public void visit(And n) {
		// TODO Auto-generated method stub

		
		n.getLeft().accept(this);
		VType left = s.peek();
		
		n.getRight().accept(this);
		VType right = s.peek();
		
		if(left.match(right) && left.match(VTyBool.getInstance())) {
			
			s.push(VTyBool.getInstance());
		}
		else {
			

			s.push(new TypeError());
			
			errorMessage = "Error at: " + n.getSymInfo().getLine() + n.getSymInfo().getColumn();
			System.out.println(errorMessage);
			error.add(new Pair<String, VType>(errorMessage, new TypeError() ));
			
		}
	}

	@Override
	public void visit(Compose n) {

		
		n.getLeft().accept(this);
		VType left = s.peek();
		
		n.getRight().accept(this);
		VType right = s.peek();
		
		if(left == right) {
			s.push(left);
		}
		else {

			s.push(new TypeError());
			
			errorMessage = "Error at: " + n.getSymInfo().getLine() + n.getSymInfo().getColumn();
			System.out.println(errorMessage);
			error.add(new Pair<String, VType>(errorMessage, new TypeError() ));
			
		}
	}
	

	@Override
	public void visit(Concat n) {
		// TODO Auto-generated method stub
		
		n.getLeft().accept(this);
		VType left = s.peek();
		
		n.getRight().accept(this);
		VType right = s.peek();
		
		if(left == right) {
			s.push(left);
		}
		else {

			s.push(new TypeError());
			
			errorMessage = "Error at: " + n.getSymInfo().getLine() + n.getSymInfo().getColumn();
			System.out.println(errorMessage);
			error.add(new Pair<String, VType>(errorMessage, new TypeError() ));
			
		}
	}

	@Override
	public void visit(Div n) {
		// TODO Auto-generated method stub
		
		n.getLeft().accept(this);
		VType left = s.peek();
		
		n.getRight().accept(this);
		VType right = s.peek();
		
		if(left.match(right)) {
			if(left.match(VTyInt.getInstance())) {
				s.push(VTyInt.getInstance());
			}
			else {
				if(left.match(VTyFloat.getInstance())) {
				
				s.push(VTyFloat.getInstance());
				}
				else {

					s.push(new TypeError());
					
					errorMessage = "Error at: " + n.getSymInfo().getLine() + n.getSymInfo().getColumn();
					System.out.println(errorMessage);
					error.add(new Pair<String, VType>(errorMessage, new TypeError() ));
					
				}
			}
		}
		else {

			s.push(new TypeError());
			
			errorMessage = "Error at: " + n.getSymInfo().getLine() + n.getSymInfo().getColumn();
			System.out.println(errorMessage);
			error.add(new Pair<String, VType>(errorMessage, new TypeError() ));
			
		}
		
	}

	@Override
	public void visit(Equals n) {
		// TODO Auto-generated method stub
		
		n.getLeft().accept(this);
		VType left = s.peek();
		
		n.getRight().accept(this);
		VType right = s.peek();
		
		if(left.match(right)) {
			if(left.match(VTyInt.getInstance())) {
				s.push(VTyBool.getInstance());
			}
			else {
				if(left.match(VTyFloat.getInstance()) || left.match(VTyString.getInstance())) {
					s.push(VTyBool.getInstance());
				}
				else {

					s.push(new TypeError());
					
					errorMessage = "Error at: " + n.getSymInfo().getLine() + n.getSymInfo().getColumn();
					System.out.println(errorMessage);
					error.add(new Pair<String, VType>(errorMessage, new TypeError() ));
					
				}
			}
		}
		else {

			s.push(new TypeError());
			
			errorMessage = "Error at: " + n.getSymInfo().getLine() + n.getSymInfo().getColumn();
			System.out.println(errorMessage);
			error.add(new Pair<String, VType>(errorMessage, new TypeError() ));
			
		}
		
	}

	@Override
	public void visit(Greater n) {
		// TODO Auto-generated method stub
	
		n.getLeft().accept(this);
		VType left = s.peek();
		
		n.getRight().accept(this);
		VType right = s.peek();
		
		if(left.match(right)) {
			if(left.match(VTyInt.getInstance())) {
				s.push(VTyBool.getInstance());
			}
			else {
				if(left.match(VTyFloat.getInstance()) || left.match(VTyString.getInstance())) {
					s.push(VTyBool.getInstance());
				}
				else {

					s.push(new TypeError());
					
					errorMessage = "Error at: " + n.getSymInfo().getLine() + n.getSymInfo().getColumn();
					System.out.println(errorMessage);
					error.add(new Pair<String, VType>(errorMessage, new TypeError() ));
					
				}
			}
		}
		else {

			s.push(new TypeError());
			
			errorMessage = "Error at: " + n.getSymInfo().getLine() + n.getSymInfo().getColumn();
			System.out.println(errorMessage);
			error.add(new Pair<String, VType>(errorMessage, new TypeError() ));
			
		}
	}

	@Override
	public void visit(GreaterEq n) {
		// TODO Auto-generated method stub
	
		n.getLeft().accept(this);
		VType left = s.peek();
		
		n.getRight().accept(this);
		VType right = s.peek();
		
		if(left.match(right)) {
			if(left.match(VTyInt.getInstance())) {
				s.push(VTyBool.getInstance());
			}
			else {
				if(left.match(VTyFloat.getInstance()) || left.match(VTyString.getInstance())) {
					s.push(VTyBool.getInstance());
				}
				else {

					s.push(new TypeError());
					
					errorMessage = "Error at: " + n.getSymInfo().getLine() + n.getSymInfo().getColumn();
					System.out.println(errorMessage);
					error.add(new Pair<String, VType>(errorMessage, new TypeError() ));
					
				}
			}
		}
		else {

			s.push(new TypeError());
			
			errorMessage = "Error at: " + n.getSymInfo().getLine() + n.getSymInfo().getColumn();
			System.out.println(errorMessage);
			error.add(new Pair<String, VType>(errorMessage, new TypeError() ));
			
		}
		
	}

	@Override
	public void visit(Less n) {
		// TODO Auto-generated method stub
		
		n.getLeft().accept(this);
		VType left = s.peek();
		
		n.getRight().accept(this);
		VType right = s.peek();
		
		if(left.match(right)) {
			if(left.match(VTyInt.getInstance())) {
				s.push(VTyBool.getInstance());
			}
			else {
				if(left.match(VTyFloat.getInstance()) || left.match(VTyString.getInstance())) {
					s.push(VTyBool.getInstance());
				}
				else {

					s.push(new TypeError());
					
					errorMessage = "Error at: " + n.getSymInfo().getLine() + n.getSymInfo().getColumn();
					System.out.println(errorMessage);
					error.add(new Pair<String, VType>(errorMessage, new TypeError() ));
					
				}
			}
		}
		else {

			s.push(new TypeError());
			
			errorMessage = "Error at: " + n.getSymInfo().getLine() + n.getSymInfo().getColumn();
			System.out.println(errorMessage);
			error.add(new Pair<String, VType>(errorMessage, new TypeError() ));
			
		}
		
	}

	@Override
	public void visit(LessEq n) {
		// TODO Auto-generated method stub
		
		n.getLeft().accept(this);
		VType left = s.peek();
		
		n.getRight().accept(this);
		VType right = s.peek();
		
		if(left.match(right)) {
			if(left.match(VTyInt.getInstance())) {
				s.push(VTyBool.getInstance());
			}
			else {
				if(left.match(VTyFloat.getInstance()) || left.match(VTyString.getInstance())) {
					s.push(VTyBool.getInstance());
				}
				else {

					s.push(new TypeError());
					
					errorMessage = "Error at: " + n.getSymInfo().getLine() + n.getSymInfo().getColumn();
					System.out.println(errorMessage);
					error.add(new Pair<String, VType>(errorMessage, new TypeError() ));
					
				}
			}
		}
		else {

			s.push(new TypeError());
			
			errorMessage = "Error at: " + n.getSymInfo().getLine() + n.getSymInfo().getColumn();
			System.out.println(errorMessage);
			error.add(new Pair<String, VType>(errorMessage, new TypeError() ));
			
		}
		
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
	public void visit(Mod n) {
		// TODO Auto-generated method stub
		
		n.getLeft().accept(this);
		VType left = s.peek();
		
		n.getRight().accept(this);
		VType right = s.peek();
		
		if(left.match(right)) {
			if(left.match(VTyInt.getInstance())) {
				s.push(VTyInt.getInstance());
			}
			else {
				if(left.match(VTyFloat.getInstance())) {
				
				s.push(VTyFloat.getInstance());
				}
				else 

					s.push(new TypeError());
					
					errorMessage = "Error at: " + n.getSymInfo().getLine() + n.getSymInfo().getColumn();
					System.out.println(errorMessage);
					error.add(new Pair<String, VType>(errorMessage, new TypeError() ));
					
				
			}
		}
		else 

			s.push(new TypeError());
			
			errorMessage = "Error at: " + n.getSymInfo().getLine() + n.getSymInfo().getColumn();
			System.out.println(errorMessage);
			error.add(new Pair<String, VType>(errorMessage, new TypeError() ));
			
		
		
	}

	@Override
	public void visit(Mult n) {
		// TODO Auto-generated method stub
		
		
		n.getLeft().accept(this);
		VType left = s.peek();
		
		n.getRight().accept(this);
		VType right = s.peek();
		
		if(left.match(right)) {
			if(left.match(VTyInt.getInstance())) {
				s.push(VTyInt.getInstance());
			}
			else {
				if(left.match(VTyFloat.getInstance())) {
				
				s.push(VTyFloat.getInstance());
				}
				else {

					s.push(new TypeError());
					
					errorMessage = "Error at: " + n.getSymInfo().getLine() + n.getSymInfo().getColumn();
					System.out.println(errorMessage);
					error.add(new Pair<String, VType>(errorMessage, new TypeError() ));
					
				}
			}
		}
		else {

			s.push(new TypeError());
			
			errorMessage = "Error at: " + n.getSymInfo().getLine() + n.getSymInfo().getColumn();
			System.out.println(errorMessage);
			error.add(new Pair<String, VType>(errorMessage, new TypeError() ));
			
		}
		
	}

	@Override
	public void visit(Not n) {
		// TODO Auto-generated method stub
		
		n.getExpr().accept(this);
		
		VType ty = s.peek();
		VType i = VTyBool.getInstance();
		
		if(ty.match(VTyBool.getInstance())) {
			s.push(i);
		}
		else {

			s.push(new TypeError());
			
			errorMessage = "Error at: " + n.getSymInfo().getLine() + n.getSymInfo().getColumn();
			System.out.println(errorMessage);
			error.add(new Pair<String, VType>(errorMessage, new TypeError() ));
			
		}
	}

	@Override
	public void visit(NotEq n) {
		// TODO Auto-generated method stub
		
		n.getLeft().accept(this);
		VType left = s.peek();
		
		n.getRight().accept(this);
		VType right = s.peek();
		
		if(left.match(right) && left.match(VTyBool.getInstance())) {
			s.push(VTyBool.getInstance());
		}
		else {

			s.push(new TypeError());
			
			errorMessage = "Error at: " + n.getSymInfo().getLine() + n.getSymInfo().getColumn();
			System.out.println(errorMessage);
			error.add(new Pair<String, VType>(errorMessage, new TypeError() ));
			
		}
	}

	@Override
	public void visit(Or n) {
		// TODO Auto-generated method stub
		
		n.getLeft().accept(this);
		VType left = s.peek();
		
		n.getRight().accept(this);
		VType right = s.peek();
		
		if(left.match(right) && left.match(VTyBool.getInstance())) {
			
			s.push(VTyBool.getInstance());
		}
		else {
			

			s.push(new TypeError());
			
			errorMessage = "Error at: " + n.getSymInfo().getLine() + n.getSymInfo().getColumn();
			System.out.println(errorMessage);
			error.add(new Pair<String, VType>(errorMessage, new TypeError() ));
			
		}
		
	}

	@Override
	public void visit(Sub n) {
		// TODO Auto-generated method stub
		
		
		n.getLeft().accept(this);
		VType left = s.peek();
		
		n.getRight().accept(this);
		VType right = s.peek();
		
		if(left.match(right)) {
			if(left.match(VTyInt.getInstance())) {
				s.push(VTyInt.getInstance());
			}
			else {
				if(left.match(VTyFloat.getInstance())) {
				
				s.push(VTyFloat.getInstance());
				}
				else {

					s.push(new TypeError());
					
					errorMessage = "Error at: " + n.getSymInfo().getLine() + n.getSymInfo().getColumn();
					System.out.println(errorMessage);
					error.add(new Pair<String, VType>(errorMessage, new TypeError() ));
					
				}
			}
		}
		else {

			s.push(new TypeError());
			
			errorMessage = "Error at: " + n.getSymInfo().getLine() + n.getSymInfo().getColumn();
			System.out.println(errorMessage);
			error.add(new Pair<String, VType>(errorMessage, new TypeError() ));
			
		}
	}
	

	@Override
	public void visit(UMinus n) {
		// TODO Auto-generated method stub
		
		n.getExpr().accept(this);
		VType expr = s.peek();
		
		if(expr.match(VTyInt.getInstance())) {
			s.push(VTyInt.getInstance());
		}
		else {
			if(expr.match(VTyFloat.getInstance())){
				s.push(VTyFloat.getInstance());
			}
			else {

				s.push(new TypeError());
				
				errorMessage = "Error at: " + n.getSymInfo().getLine() + n.getSymInfo().getColumn();
				System.out.println(errorMessage);
				error.add(new Pair<String, VType>(errorMessage, new TypeError() ));
				
			}
		}
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
	public void visit(MetaMod n) {
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
		
		
		n.getPegExp().accept(this);
	}

	@Override
	public void visit(AnyPEG n) {
		
		
	}

	@Override
	public void visit(BindPEG n) {
		// TODO Auto-generated method stub
		
		n.getExpr().accept(this);
		n.getAttribute().accept(this);
		
	}

	@Override
	public void visit(ChoiceList n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ChoicePEG n) {
		// TODO Auto-generated method stub
		
		n.getLeftPeg().accept(this);
		n.getRightPeg().accept(this);
		
		
	}

	@Override
	public void visit(ConstraintPEG n) {
		
		n.getExpr().accept(this);
	}

	@Override
	public void visit(KleenePEG n) {
		
		n.getPegExp().accept(this);
		
	}

	@Override
	public void visit(LambdaPEG n) {
	
	}

	@Override
	public void visit(LitPEG n) {
		
	
	}

	@Override
	public void visit(NonterminalPEG n) {
		// TODO Auto-generated method stub
		
		for(Expr args: n.getArgs()) {
			
			args.accept(this);
		}
		
		n.getName();
	}

	@Override
	public void visit(NotPEG n) {
		
		n.getPegExp().accept(this);
	}

	@Override
	public void visit(OptionalPEG n) {
		
		n.getPegExp().accept(this);
	}

	@Override
	public void visit(PKleene n) {
		
		n.getPegExp().accept(this);
		
	}

	@Override
	public void visit(RulePEG n) {
		// TODO Auto-generated method stub
		
		
		gamma = new Environment<String, VType>();
		
		
		VType inhs[] = new VType[n.getInh().size()];
		
		int j=0;
		for(Pair<Type, String>inh : n.getInh()) {
			
			inh.getFirst().accept(this);
			inhs[j++] = s.peek();
			gamma.add(inh.getSecond(), s.peek());
		}
		
		n.getPeg().accept(this);
		
		VType returns [] = new VType [n.getSyn().size()];
		
		int i = 0;
		
		for(Expr e: n.getSyn()) {
			
			e.accept(this);
			returns[i++] = s.peek();
		
		}
		
		NTType rules = new NTType(inhs, returns);
		
		global.add(n.getRuleName(), new NTInfo(rules, gamma ));
		
		
	}

	@Override
	public void visit(SeqPEG n) {
		
		for(APEG pegs: n.getPegs()) {
			pegs.accept(this);

		}
	}

	@Override
	public void visit(UpdatePEG n) {
		
		VType vt;
		
		for(Pair<Attribute, Expr>assigs : n.getAssigs()) {
			
			String name = assigs.getFirst().getName();
			vt = gamma.get(name);
			
			assigs.getSecond().accept(this);
			
			if(vt!= null) {
				
				if(! s.peek().match(vt)) {

					s.push(new TypeError());
					
					errorMessage = "Error at: " + n.getSymInfo().getLine() + n.getSymInfo().getColumn();
					System.out.println(errorMessage);
					error.add(new Pair<String, VType>(errorMessage, new TypeError() ));
					
				}
			}
			else {
				gamma.add(name, s.peek());
			}
		}
	}

	@Override
	public void visit(TyBool n) {
		
		s.push(VTyBool.getInstance());
	
		
	}

	@Override
	public void visit(TyChar n) {
		
		s.push(VTyChar.getInstance());
		
		
	}

	@Override
	public void visit(TyFloat n) {
		
		s.push(VTyFloat.getInstance());
		
	}

	@Override
	public void visit(TyGrammar n) {
		
		s.push(VTyGrammar.getInstance());
		
	
	}

	@Override
	public void visit(TyInt n) {
		
		s.push(VTyInt.getInstance());
		
	}

	@Override
	public void visit(TyLang n) {

		s.push(VTyLang.getInstance());
		
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

		
		s.push(VTyString.getInstance());
		
	}

	@Override
	public void visit(Grammar n) {
		
		for(RulePEG rule: n.getRules()) {
			
			rule.accept(this);
		}
		
		System.out.println(gamma.toString());
	}

}
