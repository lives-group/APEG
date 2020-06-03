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
	private VarPool pool;
	private ArrayList<Constraint>cons;
	private Environment<String,ArrayList<NTType>> opTable;

	public TypeCheckerVisitor() {

		s = new Stack<VType>();
		gamma = new Environment<String, VType>();
		global = new Environment<String, NTInfo>();
		pool = VarPool.getInstance();
		error = new ArrayList<Pair<String, VType>>();
		cons = new ArrayList<Constraint>();

		opTable = OperatorTables.mkArithmeticEnv();
	}

	@Override
	public void visit(Attribute n) {


		VType t = gamma.get(n.getName());

		if (t != null) {
			s.push(t);
		}
		else {

			s.push(new TypeError());

			errorMessage = "Error at: " + n.getSymInfo().getLine() + ", " + n.getSymInfo().getColumn();
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

			errorMessage = "Error at: " + n.getSymInfo().getLine() + "," + n.getSymInfo().getColumn();
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



		n.getLeft().accept(this);
		VType left = s.peek();

		n.getRight().accept(this);
		VType right = s.peek();

		if(left instanceof VTyVar || right instanceof VTyVar) {

			VTyVar r = pool.newVar();
			cons.add(new OpConstraint("ADD", new NTType(new VType[] {left, right}, new VType[] {r})));
			s.push(r);
		}
		else {

			for(NTType nt : opTable.get("ADD")) {


				if(nt.matchInherited(new VType[] {left, right}) ) {
					s.push(nt.getReturnAt(0));
					return;
				}

			}

			s.push(new TypeError());

			errorMessage = "Error at: " + n.getSymInfo().getLine() + ", " + n.getSymInfo().getColumn();
			System.out.println(errorMessage);
			error.add(new Pair<String, VType>(errorMessage, new TypeError() ));	


		}
	}

	@Override
	public void visit(And n) {


		n.getLeft().accept(this);
		VType left = s.peek();

		n.getRight().accept(this);
		VType right = s.peek();

		if(left instanceof VTyVar || right instanceof VTyVar) {

			VTyVar r = pool.newVar();
			cons.add(new OpConstraint("AND", new NTType(new VType[] {left, right}, new VType[] {r})));
			s.push(r);
		}
		else {

			for(NTType nt: opTable.get("AND")) {

				if(nt.matchInherited(new VType[] {left, right})) {
					s.push(nt.getReturnAt(0));
					return;
				}
			}

			s.push(new TypeError());

			errorMessage = "Error at: " + n.getSymInfo().getLine() + ", " + n.getSymInfo().getColumn();
			System.out.println(errorMessage);
			error.add(new Pair<String, VType>(errorMessage, new TypeError()));
		}
	}

	@Override
	public void visit(Compose n) {
		//TODO

		n.getLeft().accept(this);
		VType left = s.peek();

		n.getRight().accept(this);
		VType right = s.peek();

		if(left instanceof VTyVar || right instanceof VTyVar) {

			VTyVar r = pool.newVar();
			s.push(r);
		}
		else {

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
	}


	@Override
	public void visit(Concat n) {
		// TODO Auto-generated method stub

		n.getLeft().accept(this);
		VType left = s.peek();

		n.getRight().accept(this);
		VType right = s.peek();
		
		if(left instanceof VTyVar || right instanceof VTyVar) {
			
		}
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


		n.getLeft().accept(this);
		VType left = s.peek();

		n.getRight().accept(this);
		VType right = s.peek();

		if(left instanceof VTyVar || right instanceof VTyVar) {

			VTyVar r = pool.newVar();
			cons.add(new OpConstraint("DIV", new NTType(new VType[] {left, right}, new VType[] {r})));
			s.push(r);
		}
		else {

			for(NTType nt: opTable.get("DIV")) {

				if(nt.matchInherited(new VType[] {left, right})) {
					s.push(nt.getReturnAt(0));
					return;
				}
			}

			s.push(new TypeError());
			errorMessage = "Error at: " + n.getSymInfo().getLine() + ", " + n.getSymInfo().getColumn();
			error.add(new Pair<String, VType>(errorMessage, new TypeError()));
		}

	}

	@Override
	public void visit(Equals n) {


		n.getLeft().accept(this);
		VType left = s.peek();

		n.getRight().accept(this);
		VType right = s.peek();

		if(left instanceof VTyVar || right instanceof VTyVar) {

			VTyVar r = pool.newVar();
			cons.add(new OpConstraint("EQ", new NTType(new VType[] {left, right}, new VType[] {r})));
			s.push(r);
		}
		else {

			for(NTType nt: opTable.get("EQ")) {

				if(nt.matchInherited(new VType[] {left, right})) {
					s.push(nt.getReturnAt(0));
					return;
				}
			}

			s.push(new TypeError());
			errorMessage = "Error at: " + n.getSymInfo().getLine() + ", " + n.getSymInfo().getColumn();
			error.add(new Pair<String, VType>(errorMessage, new TypeError()));
		}

	}

	@Override
	public void visit(Greater n) {


		n.getLeft().accept(this);
		VType left = s.peek();

		n.getRight().accept(this);
		VType right = s.peek();

		if(left instanceof VTyVar || right instanceof VTyVar) {

			VTyVar r = pool.newVar();
			cons.add(new OpConstraint("GT", new NTType(new VType[] {left, right}, new VType[] {r})));
			s.push(r);
		}
		else {

			for(NTType nt: opTable.get("GT")) {

				if(nt.matchInherited(new VType[] {left, right})) {
					s.push(nt.getReturnAt(0));
					return;
				}
			}

			s.push(new TypeError());
			errorMessage = "Error at: " + n.getSymInfo().getLine() + ", " + n.getSymInfo().getColumn();
			error.add(new Pair<String, VType>(errorMessage, new TypeError()));
		}
	}

	@Override
	public void visit(GreaterEq n) {

		n.getLeft().accept(this);
		VType left = s.peek();

		n.getRight().accept(this);
		VType right = s.peek();
		if(left instanceof VTyVar || right instanceof VTyVar) {

			VTyVar r = pool.newVar();
			cons.add(new OpConstraint("GE", new NTType(new VType[] {left, right}, new VType[] {r})));
			s.push(r);
		}
		else {

			for(NTType nt: opTable.get("GE")) {

				if(nt.matchInherited(new VType[] {left, right})) {
					s.push(nt.getReturnAt(0));
					return;
				}
			}

			s.push(new TypeError());
			errorMessage = "Error at: " + n.getSymInfo().getLine() + ", " + n.getSymInfo().getColumn();
			error.add(new Pair<String, VType>(errorMessage, new TypeError()));
		}

	}

	@Override
	public void visit(Less n) {


		n.getLeft().accept(this);
		VType left = s.peek();

		n.getRight().accept(this);
		VType right = s.peek();

		if(left instanceof VTyVar || right instanceof VTyVar) {

			VTyVar r = pool.newVar();
			cons.add(new OpConstraint("LT", new NTType(new VType[] {left, right}, new VType[] {r})));
			s.push(r);
		}
		else {

			for(NTType nt: opTable.get("LT")) {

				if(nt.matchInherited(new VType[] {left, right})) {
					s.push(nt.getReturnAt(0));
					return;
				}
			}

			s.push(new TypeError());
			errorMessage = "Error at: " + n.getSymInfo().getLine() + ", " + n.getSymInfo().getColumn();
			error.add(new Pair<String, VType>(errorMessage, new TypeError()));
		}

	}

	@Override
	public void visit(LessEq n) {


		n.getLeft().accept(this);
		VType left = s.peek();

		n.getRight().accept(this);
		VType right = s.peek();

		if(left instanceof VTyVar || right instanceof VTyVar) {

			VTyVar r = pool.newVar();
			cons.add(new OpConstraint("LE", new NTType(new VType[] {left, right}, new VType[] {r})));
			s.push(r);
		}
		else {

			for(NTType nt: opTable.get("LE")) {

				if(nt.matchInherited(new VType[] {left, right})) {
					s.push(nt.getReturnAt(0));
					return;
				}
			}

			s.push(new TypeError());
			errorMessage = "Error at: " + n.getSymInfo().getLine() + ", " + n.getSymInfo().getColumn();
			error.add(new Pair<String, VType>(errorMessage, new TypeError()));
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


		n.getLeft().accept(this);
		VType left = s.peek();

		n.getRight().accept(this);
		VType right = s.peek();

		if(left instanceof VTyVar || right instanceof VTyVar) {

			VTyVar r = pool.newVar();
			cons.add(new OpConstraint("MOD", new NTType(new VType[] {left, right}, new VType[] {r})));
			s.push(r);
		}
		else {

			for(NTType nt: opTable.get("MOD")) {

				if(nt.matchInherited(new VType[] {left, right})) {
					s.push(nt.getReturnAt(0));
					return;
				}
			}

			s.push(new TypeError());
			errorMessage = "Error at: " + n.getSymInfo().getLine() + ", " + n.getSymInfo().getColumn();
			error.add(new Pair<String, VType>(errorMessage, new TypeError()));
		}


	}

	@Override
	public void visit(Mult n) {



		n.getLeft().accept(this);
		VType left = s.peek();

		n.getRight().accept(this);
		VType right = s.peek();
		if(left instanceof VTyVar || right instanceof VTyVar) {

			VTyVar r = pool.newVar();
			cons.add(new OpConstraint("MUL", new NTType(new VType[] {left, right}, new VType[] {r})));
			s.push(r);
		}
		else {

			for(NTType nt: opTable.get("MUL")) {

				if(nt.matchInherited(new VType[] {left, right})) {
					s.push(nt.getReturnAt(0));
					return;
				}
			}

			s.push(new TypeError());
			errorMessage = "Error at: " + n.getSymInfo().getLine() + ", " + n.getSymInfo().getColumn();
			error.add(new Pair<String, VType>(errorMessage, new TypeError()));
		}

	}

	@Override
	public void visit(Not n) {


		n.getExpr().accept(this);
		VType ty = s.peek();

		if(ty instanceof VTyVar) {

			VTyVar r = pool.newVar();
			cons.add(new OpConstraint("NOT", new NTType(new VType[] {ty}, new VType[] {r})));
			s.push(r);
		}
		else {

			for(NTType nt: opTable.get("NOT")) {

				if(nt.matchInherited(new VType[] {ty})) {
					s.push(nt.getReturnAt(0));
					return;
				}
			}

			s.push(new TypeError());
			errorMessage = "Error at: " + n.getSymInfo().getLine() + ", " + n.getSymInfo().getColumn();
			error.add(new Pair<String, VType>(errorMessage, new TypeError()));
		}
	}

	@Override
	public void visit(NotEq n) {
		// TODO Auto-generated method stub

		n.getLeft().accept(this);
		VType left = s.peek();

		n.getRight().accept(this);
		VType right = s.peek();

		if(left instanceof VTyVar || right instanceof VTyVar) {

			VTyVar r = pool.newVar();
			cons.add(new OpConstraint("EQ", new NTType(new VType[] {left, right}, new VType[] {r})));
			s.push(r);
		}
		else {

			for(NTType nt: opTable.get("EQ")) {

				if(nt.matchInherited(new VType[] {left, right})) {
					s.push(nt.getReturnAt(0));
					return;
				}
			}

			s.push(new TypeError());
			errorMessage = "Error at: " + n.getSymInfo().getLine() + ", " + n.getSymInfo().getColumn();
			error.add(new Pair<String, VType>(errorMessage, new TypeError()));
		}
	}

	@Override
	public void visit(Or n) {


		n.getLeft().accept(this);
		VType left = s.peek();

		n.getRight().accept(this);
		VType right = s.peek();

		if(left instanceof VTyVar || right instanceof VTyVar) {

			VTyVar r = pool.newVar();
			cons.add(new OpConstraint("OR", new NTType(new VType[] {left, right}, new VType[] {r})));
			s.push(r);
		}
		else {

			for(NTType nt: opTable.get("OR")) {

				if(nt.matchInherited(new VType[] {left, right})) {
					s.push(nt.getReturnAt(0));
					return;
				}
			}

			s.push(new TypeError());
			errorMessage = "Error at: " + n.getSymInfo().getLine() + ", " + n.getSymInfo().getColumn();
			error.add(new Pair<String, VType>(errorMessage, new TypeError()));
		}
	}

	@Override
	public void visit(Sub n) {


		n.getLeft().accept(this);
		VType left = s.peek();

		n.getRight().accept(this);
		VType right = s.peek();

		if(left instanceof VTyVar || right instanceof VTyVar) {

			VTyVar r = pool.newVar();
			cons.add(new OpConstraint("SUB", new NTType(new VType[] {left, right}, new VType[] {r})));
			s.push(r);
		}
		else {

			for(NTType nt: opTable.get("SUB")) {

				if(nt.matchInherited(new VType[] {left, right})) {
					s.push(nt.getReturnAt(0));
					return;
				}
			}

			s.push(new TypeError());
			errorMessage = "Error at: " + n.getSymInfo().getLine() + ", " + n.getSymInfo().getColumn();
			error.add(new Pair<String, VType>(errorMessage, new TypeError()));
		}
	}


	@Override
	public void visit(UMinus n) {
		// TODO Auto-generated method stub

		n.getExpr().accept(this);
		VType expr = s.peek();

		if(expr instanceof VTyVar) {

			VTyVar r = pool.newVar();
			cons.add(new OpConstraint("MINUS", new NTType(new VType[] {expr}, new VType[] {r})));
			s.push(r);
		}
		else {

			for(NTType nt: opTable.get("MINUS")) {

				if(nt.matchInherited(new VType[] {expr})) {
					s.push(nt.getReturnAt(0));
					return;
				}
			}

			s.push(new TypeError());
			errorMessage = "Error at: " + n.getSymInfo().getLine() + ", " + n.getSymInfo().getColumn();
			error.add(new Pair<String, VType>(errorMessage, new TypeError()));
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

		gamma = global.get(n.getRuleName()).getLocals();

		n.getPeg().accept(this);

		VType returns [] = new VType [n.getSyn().size()];

		int i = 0;

		for(Expr e: n.getSyn()) {

			e.accept(this);
			returns[i++] = s.peek();

		}	


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

				if(vt instanceof VTyVar) {

					cons.add(new VarConstraint((VTyVar) vt, s.pop())); 

				}
				else
					if(s.peek() instanceof VTyVar) {
						cons.add(new VarConstraint((VTyVar)s.pop(), vt));
					}else
						if(! s.pop().match(vt)) {

							s.push(new TypeError());

							errorMessage = "Error at: " + n.getSymInfo().getLine() + ", " + n.getSymInfo().getColumn();
							System.out.println(errorMessage);
							error.add(new Pair<String, VType>(errorMessage, new TypeError() ));

						}
			}
			else {
				gamma.add(name, s.pop());
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

			gamma = new Environment<String, VType>();


			VType inhs[] = new VType[rule.getInh().size()];

			int j=0;
			for(Pair<Type, String>inh : rule.getInh()) {

				inh.getFirst().accept(this);
				inhs[j++] = s.peek();
				gamma.add(inh.getSecond(), s.peek());
			}

			VType returns [] = new VType [rule.getSyn().size()];

			int i;

			for(i=0; i< rule.getSyn().size(); i++) {

				returns[i] = pool.newVar();				
			}

			NTType rules = new NTType(inhs, returns);

			global.add(rule.getRuleName(), new NTInfo(rules, gamma));
		}

		for(RulePEG r : n.getRules()) {

			r.accept(this);
		}
	}
}