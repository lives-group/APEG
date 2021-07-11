package apeg.visitor;
import java.util.*;
import apeg.ast.*;
import apeg.ast.expr.*;
import apeg.ast.expr.operators.*;
import apeg.ast.rules.*;
import apeg.ast.types.*;
import apeg.util.*;
import apeg.vm.*;
import java.io.*;
import apeg.visitor.semantics.*;



public class VMVisitor extends Visitor{

	private ApegVM vm;
	private Hashtable<String,RulePEG> hashRules;
	private Stack<Pair<VType,Object>> stk;
	private Environment<String,NTInfo> env;
	private NTInfo nti;
	// Criar uma variável NTInfo local;


	public VMVisitor(String path,Environment<String,NTInfo> e){
		try {
			env = e;
			stk = new Stack();
			vm = new ApegVM(path);
		} catch(Exception exc) {
			exc.printStackTrace();
		}
	}


	@Override
	public void visit(Attribute n) {
		VType vt = nti.getLocals().get(n.getName());
		stk.push(new Pair(vt,vm.getValue(n.getName())));
	}

	@Override
	public void visit(AttributeGrammar n) {
		// TODO Auto-generated method stub
	}

	@Override
	public void visit(BoolLit n) {
		stk.push(new Pair(VTyBool.getInstance(),n.getValue()));
	}

	@Override
	public void visit(CharLit n) {
		stk.push(new Pair(VTyChar.getInstance(),n.getValue()));
	}

	@Override
	public void visit(FloatLit n) {
		stk.push(new Pair(VTyFloat.getInstance(),n.getValue()));
	}

	@Override
	public void visit(IntLit n) {
		stk.push(new Pair(VTyInt.getInstance(),n.getValue()));
	}

	@Override
	public void visit(MapLit n) {
		Hashtable<String,Object>  map = new Hashtable<String,Object>();
		n.getAssocs()[0].getSecond().accept(this);
		VType ty = stk.peek().getFirst();
		n.getAssocs()[0].getFirst().accept(this); // Only because we dont have empty maps !
		map.put((String)stk.pop().getSecond(),stk.pop().getSecond());

		for (int i = 1; i < n.getAssocs().length; i++) {
			n.getAssocs()[i].getSecond().accept(this);
			n.getAssocs()[i].getFirst().accept(this);
			map.put((String)stk.pop().getSecond(),stk.pop().getSecond());
		}
		stk.push(new Pair(new VTyMap(ty),map));
	}

	@Override
	public void visit(StrLit n) {
		stk.push(new Pair(VTyString.getInstance(),n.getValue()));
	}

	@Override
	public void visit(MetaAndPEG n) {
	}

	@Override
	public void visit(MetaAnyPEG n) {
		
	}

	@Override
	public void visit(MetaAttribute n) {
		
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
         n.getExpr().accept(this);
         // Vair dexiar uma String no topo da pilha.
         // Lembre-se de terminar isso ! 
         
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
		n.getRight().accept(this);
		Pair<VType,Object> a = stk.pop();
		Pair<VType,Object> b = stk.pop();
		if(a.getFirst().getName().equals("int")){
			stk.push(new Pair(VTyInt.getInstance(),(Integer)a.getSecond() + (Integer)b.getSecond()));
		}else if(a.getFirst().getName().equals("float")){
			stk.push(new Pair(VTyFloat.getInstance(),(Float)a.getSecond() + (Float)b.getSecond()));
		}else if(a.getFirst().getName().equals("char")){
			stk.push(new Pair(VTyString.getInstance(),(Character)b.getSecond() + (Character)a.getSecond()));
		}else if(a.getFirst().getName().equals("string")){
			stk.push(new Pair(VTyString.getInstance(),(String)b.getSecond() + (String)a.getSecond()));
		}else{
			throw new RuntimeException("(" + n.getSymInfo().getLine() + "," + n.getSymInfo().getColumn() + ") Imcompatible operators for <=");
		}
	}

	@Override
	public void visit(And n) {
		n.getLeft().accept(this);
		n.getRight().accept(this);
		Boolean a = (Boolean)(stk.pop().getSecond());
		Boolean b = (Boolean)(stk.pop().getSecond());
		stk.push(new Pair(VTyBool.getInstance(),a && b));
	}

	@Override
	public void visit(Compose n) {
		// TODO Auto-generated method stub
		n.getLeft().accept(this);
		n.getRight().accept(this);
	}

	@Override
	public void visit(Concat n) {
		n.getLeft().accept(this);
		n.getRight().accept(this);
		Pair<VType,Object> a = stk.pop();
		Pair<VType,Object> b = stk.pop();
		if(a.getFirst().getName().equals("char")){
			stk.push(new Pair(VTyString.getInstance(),(Character)b.getSecond() + (Character)a.getSecond()));
		}else if(a.getFirst().getName().equals("string")){
			stk.push(new Pair(VTyString.getInstance(),(String)b.getSecond() + (String)a.getSecond()));
		}else{
			throw new RuntimeException("(" + n.getSymInfo().getLine() + "," + n.getSymInfo().getColumn() + ") Imcompatible operators for <=");
		}
	}


	@Override
	public void visit(Div n) {
		n.getLeft().accept(this);
		n.getRight().accept(this);
		Pair<VType,Object> a = stk.pop();
		Pair<VType,Object> b = stk.pop();
		if(a.getFirst().getName().equals("int")){
			stk.push(new Pair(VTyFloat.getInstance(),(Integer)b.getSecond() / (Integer)a.getSecond()));
		}else if(a.getFirst().getName().equals("float")){
			stk.push(new Pair(VTyFloat.getInstance(),(Float)b.getSecond() / (Float)a.getSecond()));
		}else{
			throw new RuntimeException("(" + n.getSymInfo().getLine() + "," + n.getSymInfo().getColumn() + ") Imcompatible operators for <=");
		}
	}

	@Override
	public void visit(Equals n) {
		n.getLeft().accept(this);
		n.getRight().accept(this);
		Pair<VType,Object> a = stk.pop();
		Pair<VType,Object> b = stk.pop();
		Boolean bl;
		if(a.getFirst().getName().equals("int")){
			bl = (Integer)a.getSecond() == (Integer)b.getSecond();
		}else if(a.getFirst().getName().equals("float")){
			bl = (Float)a.getSecond() == (Float)b.getSecond();
		}else if(a.getFirst().getName().equals("char")){
			bl = (Character)a.getSecond() == (Character)b.getSecond();
		}else if(a.getFirst().getName().equals("string")){
			bl = ((String)a.getSecond()).compareTo((String)b.getSecond()) == 0;
		}else{
			throw new RuntimeException("(" + n.getSymInfo().getLine() + "," + n.getSymInfo().getColumn() + ") Imcompatible operators for <=");
		}
		stk.push(new Pair(VTyBool.getInstance(),bl));
	}

	@Override
	public void visit(Greater n) {
		n.getLeft().accept(this);
		n.getRight().accept(this);
		Pair<VType,Object> a = stk.pop();
		Pair<VType,Object> b = stk.pop();
		Boolean bl;
		if(a.getFirst().getName().equals("int")){
			bl = (Integer)a.getSecond() > (Integer)b.getSecond();
		}else if(a.getFirst().getName().equals("float")){
			bl = (Float)a.getSecond() > (Float)b.getSecond();
		}else if(a.getFirst().getName().equals("char")){
			bl = (Character)a.getSecond() > (Character)b.getSecond();
		}else if(a.getFirst().getName().equals("string")){
			bl = ((String)a.getSecond()).compareTo((String)b.getSecond()) == 1;
		}else{
			throw new RuntimeException("(" + n.getSymInfo().getLine() + "," + n.getSymInfo().getColumn() + ") Imcompatible operators for <=");
		}
		stk.push(new Pair(VTyBool.getInstance(),bl));
	}

	@Override
	public void visit(GreaterEq n) {
		n.getLeft().accept(this);
		n.getRight().accept(this);
		Pair<VType,Object> a = stk.pop();
		Pair<VType,Object> b = stk.pop();
		Boolean bl;
		if(a.getFirst().getName().equals("int")){
			bl = (Integer)a.getSecond() >= (Integer)b.getSecond();
		}else if(a.getFirst().getName().equals("float")){
			bl = (Float)a.getSecond() >= (Float)b.getSecond();
		}else if(a.getFirst().getName().equals("char")){
			bl = (Character)a.getSecond() >= (Character)b.getSecond();
		}else if(a.getFirst().getName().equals("string")){
			bl = ((String)a.getSecond()).compareTo((String)b.getSecond()) == 1;
		}else{
			throw new RuntimeException("(" + n.getSymInfo().getLine() + "," + n.getSymInfo().getColumn() + ") Imcompatible operators for <=");
		}
		stk.push(new Pair(VTyBool.getInstance(),bl));
	}

	@Override
	public void visit(Less n) {
		n.getLeft().accept(this);
		n.getRight().accept(this);
		Pair<VType,Object> a = stk.pop();
		Pair<VType,Object> b = stk.pop();
		Boolean bl;
		if(a.getFirst().getName().equals("int")){
			bl = (Integer)a.getSecond() < (Integer)b.getSecond();
		}else if(a.getFirst().getName().equals("float")){
			bl = (Float)a.getSecond() < (Float)b.getSecond();
		}else if(a.getFirst().getName().equals("char")){
			bl = (Character)a.getSecond() < (Character)b.getSecond();
		}else if(a.getFirst().getName().equals("string")){
			bl = ((String)a.getSecond()).compareTo((String)b.getSecond()) == -1;
		}else{
			throw new RuntimeException("(" + n.getSymInfo().getLine() + "," + n.getSymInfo().getColumn() + ") Imcompatible operators for <=");
		}
		stk.push(new Pair(VTyBool.getInstance(),bl));
	}

	//eero == -1 menor igual
	@Override
	public void visit(LessEq n) {
		n.getLeft().accept(this);
		n.getRight().accept(this);
		Pair<VType,Object> a = stk.pop();
		Pair<VType,Object> b = stk.pop();
		Boolean bl;
		if(a.getFirst().getName().equals("int")){
			bl = (Integer)a.getSecond() <= (Integer)b.getSecond();
		}else if(a.getFirst().getName().equals("float")){
			bl = (Float)a.getSecond() <= (Float)b.getSecond();
		}else if(a.getFirst().getName().equals("char")){
			bl = (Character)a.getSecond() <= (Character)b.getSecond();
		}else if(a.getFirst().getName().equals("string")){
			bl = ((String)a.getSecond()).compareTo((String)b.getSecond()) == -1;
		}else{
			throw new RuntimeException("(" + n.getSymInfo().getLine() + "," + n.getSymInfo().getColumn() + ") Imcompatible operators for <=");
		}
		stk.push(new Pair(VTyBool.getInstance(),bl));
	}

	@Override
	// m["s"]
	public void visit(MapAcces n) {
		n.getIndex().accept(this);
		n.getMap().accept(this);
		Pair<VType,Object> m = stk.pop();
		// String i = (String)(stk.pop().getSecond())
		Pair<VType,Object> i = stk.pop();

		//stk.push(new Pair(VTyMap.getInstance(),m.getSecond().get(i.getSecond())));
		stk.push(new Pair( ((VTyMap)m.getFirst()).getTyParameter(), ((Hashtable)m.getSecond()).get((String)i.getSecond())) );
	}

	@Override
	public void visit(MapExtension n) {
		n.getKey().accept(this);
		n.getMap().accept(this);
		n.getValue().accept(this);
		Pair<VType,Object> v = stk.pop();
		Pair<VType,Object> m = stk.pop();
		Pair<VType,Object> k = stk.pop();
		((Hashtable)m.getSecond()).put((String)k.getSecond(),v.getSecond());
		stk.push(m);
	}

	@Override
	public void visit(Mod n) {
		n.getLeft().accept(this);
		n.getRight().accept(this);
		Pair<VType,Object> a = stk.pop();
		Pair<VType,Object> b = stk.pop();
		stk.push(new Pair(VTyInt.getInstance(),(Integer)b.getSecond() % (Integer)a.getSecond()));
	}

	@Override
	public void visit(Mult n) {
		n.getLeft().accept(this);
		n.getRight().accept(this);
		Pair<VType,Object> a = stk.pop();
		Pair<VType,Object> b = stk.pop();
		if(a.getFirst().getName().equals("int")){
			stk.push(new Pair(VTyInt.getInstance(),(Integer)a.getSecond() * (Integer)b.getSecond()));
		}else if(a.getFirst().getName().equals("float")){
			stk.push(new Pair(VTyFloat.getInstance(),(Float)a.getSecond() * (Float)b.getSecond()));
		}else{
			throw new RuntimeException("(" + n.getSymInfo().getLine() + "," + n.getSymInfo().getColumn() + ") Imcompatible operators for <=");
		}
	}

	@Override
	public void visit(Not n) {
		n.getExpr().accept(this);
		stk.push(new Pair(VTyBool.getInstance(),!(Boolean)stk.pop().getSecond()));
	}

	@Override
	public void visit(NotEq n) {
		n.getLeft().accept(this);
		n.getRight().accept(this);
		stk.push(new Pair(VTyBool.getInstance(),!stk.pop().getSecond().equals(stk.pop().getSecond())));
	}

	@Override
	public void visit(Or n) {
		n.getLeft().accept(this);
		n.getRight().accept(this);
		Boolean a = (Boolean)(stk.pop().getSecond());
		Boolean b = (Boolean)(stk.pop().getSecond());
		stk.push(new Pair(VTyBool.getInstance(),a || b));
	}

	@Override
	public void visit(Sub n) {
		n.getLeft().accept(this);
		n.getRight().accept(this);
		Pair<VType,Object> a = stk.pop();
		Pair<VType,Object> b = stk.pop();
		if(a.getFirst().getName().equals("int")){
			stk.push(new Pair(VTyInt.getInstance(),(Integer)b.getSecond() - (Integer)a.getSecond()));
		}else if(a.getFirst().getName().equals("float")){
			stk.push(new Pair(VTyFloat.getInstance(),(Float)b.getSecond() - (Float)a.getSecond()));
		}else{
			throw new RuntimeException("(" + n.getSymInfo().getLine() + "," + n.getSymInfo().getColumn() + ") Imcompatible operators for <=");
		}
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
// 		n.getLeft().accept(this);
// 		n.getRigth().accept(this);
// 		Expr a = (Expr)(stk.pop().getSecond());
// 		Expr b = (Expr)(stk.pop().getSecond());
// 		And and = new And(new SymInfo(-1,-1),a,b);
// 		stk.push(new Pair(TyMetaExpr.getInstance(),and));
	}

	@Override
	public void visit(MetaCompose n) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(MetaConcat n) {
// 		n.getLeft().accept(this);
// 		n.getRigth().accept(this);
// 		Expr a = (Expr)(stk.pop().getSecond());
// 		Expr b = (Expr)(stk.pop().getSecond());
// 		Concat concat = new Concat(new SymInfo(-1,-1),a,b);
// 		stk.push(new Pair(TyMetaExpr.getInstance(),concat));
	}

	@Override
	public void visit(MetaDiv n) {
// 		n.getLeft().accept(this);
// 		n.getRigth().accept(this);
// 		Expr a = (Expr)(stk.pop().getSecond());
// 		Expr b = (Expr)(stk.pop().getSecond());
// 		Div div = new Div(new SymInfo(-1,-1),a,b);
// 		stk.push(new Pair(TyMetaExpr.getInstance(),div));
    }

	@Override
	public void visit(MetaEquals n) {
// 		n.getLeft().accept(this);
// 		n.getRigth().accept(this);
// 		Expr a = (Expr)(stk.pop().getSecond());
// 		Expr b = (Expr)(stk.pop().getSecond());
// 		Equals equals = new Equals(new SymInfo(-1,-1),a,b);
// 		stk.push(new Pair(TyMetaExpr.getInstance(),equals));
	}

	@Override
	public void visit(MetaGreater n) {
// 		n.getLeft().accept(this);
// 		n.getRigth().accept(this);
// 		Expr a = (Expr)(stk.pop().getSecond());
// 		Expr b = (Expr)(stk.pop().getSecond());
// 		Greater greater = new Greater(new SymInfo(-1,-1),a,b);
// 		stk.push(new Pair(TyMetaExpr.getInstance(),greater));

	}

	@Override
	public void visit(MetaGreaterEq n) {
// 		n.getLeft().accept(this);
// 		n.getRigth().accept(this);
// 		Expr a = (Expr)(stk.pop().getSecond());
// 		Expr b = (Expr)(stk.pop().getSecond());
// 		GreaterEq greaterEq = new GreaterEq(new SymInfo(-1,-1),a,b);
// 		stk.push(new Pair(TyMetaExpr.getInstance(),greaterEq));
	}

	@Override
	public void visit(MetaLess n) {
// 		n.getLeft().accept(this);
// 		n.getRigth().accept(this);
// 		Expr a = (Expr)(stk.pop().getSecond());
// 		Expr b = (Expr)(stk.pop().getSecond());
// 		Less less = new Less(new SymInfo(-1,-1),a,b);
// 		stk.push(new Pair(TyMetaExpr.getInstance(),less));
	}

	@Override
	public void visit(MetaLessEq n) {
// 		n.getLeft().accept(this);
// 		n.getRigth().accept(this);
// 		Expr a = (Expr)(stk.pop().getSecond());
// 		Expr b = (Expr)(stk.pop().getSecond());
// 		LessEq lessEq = new LessEq(new SymInfo(-1,-1),a,b);
// 		stk.push(new Pair(TyMetaExpr.getInstance(),lessEq));
	}

	@Override
	public void visit(MetaMapAcces n) {
// 		n.getMap().accept(this);
// 		n.getIndex().accept(this);
// 		Expr a = (Expr)(stk.pop().getSecond());
// 		Expr b = (Expr)(stk.pop().getSecond());
// 		MapAcces mapAcces = new MapAcces(new SymInfo(-1,-1),b,a);
// 		stk.push(new Pair(TyMetaExpr.getInstance(),mapAcces));
	}

	@Override
	public void visit(MetaMapExtension n) {
// 		n.getMap().accept(this);
// 		n.getIndex().accept(this);
// 		Expr a = (Expr)(stk.pop().getSecond());
// 		Expr b = (Expr)(stk.pop().getSecond());
// 		MapExtension mapExtension = new MapExtension(new SymInfo(-1,-1),b,a);
// 		stk.push(new Pair(TyMetaExpr.getInstance(),mapExtension));

	}

	@Override
	public void visit(MetaMod n) {
// 		n.getLeft().accept(this);
// 		n.getRigth().accept(this);
// 		Expr a = (Expr)(stk.pop().getSecond());
// 		Expr b = (Expr)(stk.pop().getSecond());
// 		Mod mod = new Mod(new SymInfo(-1,-1),a,b);
// 		stk.push(new Pair(TyMetaExpr.getInstance(),mod));}
	}

	@Override
	public void visit(MetaMult n) {
// 		n.getLeft().accept(this);
// 		n.getRigth().accept(this);
// 		Expr a = (Expr)(stk.pop().getSecond());
// 		Expr b = (Expr)(stk.pop().getSecond());
// 		Mult mult = new Mult(new SymInfo(-1,-1),a,b);
// 		stk.push(new Pair(TyMetaExpr.getInstance(),mult));
	}
	

	@Override
	public void visit(MetaNot n) {
// 		n.getExpr().accept(this);
// 		Expr a = (Expr)(stk.pop().getSecond());
// 		Not mod = new Not(new SymInfo(-1,-1),a);
// 		stk.push(new Pair(TyMetaExpr.getInstance(),not));
	}

	

	@Override
	public void visit(MetaNotEq n) {
// 		n.getLeft().accept(this);
// 		n.getRigth().accept(this);
// 		Expr a = (Expr)(stk.pop().getSecond());
// 		Expr b = (Expr)(stk.pop().getSecond());
// 		NotEq notEq = new NotEq(new SymInfo(-1,-1),a,b);
// 		stk.push(new Pair(TyMetaExpr.getInstance(),notEq));
	}

	@Override
	public void visit(MetaOr n) {
// 		n.getLeft().accept(this);
// 		n.getRigth().accept(this);
// 		Expr a = (Expr)(stk.pop().getSecond());
// 		Expr b = (Expr)(stk.pop().getSecond());
// 		Or or = new Or(new SymInfo(-1,-1),a,b);
// 		stk.push(new Pair(TyMetaExpr.getInstance(),or));
	}

	@Override
	public void visit(MetaSub n) {
// 		n.getLeft().accept(this);
// 		n.getRigth().accept(this);
// 		Expr a = (Expr)(stk.pop().getSecond());
// 		Expr b = (Expr)(stk.pop().getSecond());
// 		Sub sub = new Sub(new SymInfo(-1,-1),a,b);
// 		stk.push(new Pair(TyMetaExpr.getInstance(),sub));
	}

	@Override
	public void visit(MetaUMinus n) {
// 		n.getExpr().accept(this);
// 		Expr a = (Expr)(stk.pop().getSecond());
// 		UMinus uMinus = new UMinus(new SymInfo(-1,-1),a);
// 		stk.push(new Pair(TyMetaExpr.getInstance(),uMinus));
	}

	@Override
	public void visit(AndPEG n) {
		n.getPegExp().accept(this);
		vm.restore();
	}

	@Override
	public void visit(AnyPEG n) {
		vm.any();
	}

	@Override
	public void visit(BindPEG n) {
		// TODO Auto-generated method stub
	}

	@Override
	public void visit(ChoiceList n) {
		try{
			char c = vm.nextValue();
			if(n.getInterval().inInterval(c)){
				vm.success();
			}else{
				vm.fail();
			}
		}catch(IOException e){
			e.printStackTrace();
			System.exit(1);
		}
	}

	@Override
	public void visit(ChoicePEG n) {
		vm.beginAlt();
		n.getLeftPeg().accept(this);
		if(vm.succeed()){
			vm.endAlt();
		}else{
			vm.retryAlt();
			n.getRightPeg().accept(this);
		}
	}

	@Override
	public void visit(ConstraintPEG n) {
		// TODO Auto-generated method stub
		n.getExpr().accept(this);
	}

	@Override
	public void visit(KleenePEG n) {
		do{
			vm.beginAlt();
			n.getPegExp().accept(this);
			if(vm.succeed()){
				vm.endAlt();
			}
		}while(vm.succeed());
		vm.failAlt();
		vm.success();
	}

	@Override
	public void visit(LambdaPEG n) {
		vm.success();
	}

	@Override
	public void visit(LitPEG n){
		try{
			vm.match(n.getLit());
		}catch(IOException e){
		}
	}

	@Override
	public void visit(NonterminalPEG n) {
		// Avaliar (ou visitar) os argumentos herdados e por na pilha.
		for(Expr e: n.getArgs()){
			e.accept(this);
		}
		// Visitar rulepeg. 
		RulePEG b = hashRules.get(n.getName());
		if(b==null){
			throw new RuntimeException("Rule "+n.getName()+" not found");
		}
		b.accept(this);
		//os ultimos serao os primeiros
		for (i=nti.getSig().getNumSintetized();i>0;i--) {
			vm.setValue(nti.getSig().getReturnAt(i).getName(),stk.pop().getSecond());
		}
	}

	@Override
	public void visit(NotPEG n) {
		n.getPegExp().accept(this);
		vm.restore();
		if(vm.succeed()){
			vm.fail();
		}else{
			vm.success();
		}
	}

	@Override
	public void visit(OptionalPEG n) {
		n.getPegExp().accept(this);
	}

	@Override
	public void visit(PKleene n) {
		n.getPegExp().accept(this);
		if(vm.succeed()){
			while(vm.succeed()){
				vm.beginAlt();
				n.getPegExp().accept(this);
				if(vm.succeed()){
					vm.endAlt();
				}
			}
			vm.failAlt();
			vm.success();
		}else{
			vm.fail();
		}
	}

	@Override
	public void visit(RulePEG n) {
		// Montar o contexto tirando os valores do topo da pila.
		// Visitar o coporp do rule;
		nti = env.get(n.getRuleName());
		CTX ctx = new CTX(nti.getSig().getNumParams()+nti.getLocals().getNames().size());
		for (i=nti.getSig().getNumInherited();i>0;i--) {
			ctx.writeValue(nti.getSig().getVType(i).getName(),stk.pop().getSecond());
		}
		vm.beginRule(n.getRuleName(),ctx);
		for(Expr e : n.getSyn()){
		e.accept(this);	
		}
		// Visitar expressões sintetizados
		// Empilhar os resultados.
		vm.endRule();
	}

	@Override
	public void visit(SeqPEG n) {
		int size = n.getSize();
		int i=0;
		do{
			n.getAt(i).accept(this);
			i++;
		}while(vm.succeed() && i < size);
	}

	@Override
	public void visit(UpdatePEG n) {
		for(Pair<Attribute, Expr> assigs: n.getAssigs()) {
			assigs.getSecond().accept(this);
			vm.setValue(assigs.getFirst().getName(),stk.pop().getSecond());
		}
	}

	@Override
	public void visit(TyBool n) {
		// TODO Auto-generated method stub
	}
	
    public void visit(TyMetaPeg n) {
		// TODO Auto-generated method stub
	}
	
	public void visit(TyMetaExr n) {
		// TODO Auto-generated method stub
	}
	
	public void visit(TyLan n) {
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
		hashRules = new Hashtable<String,RulePEG>();
		for(int i = 0 ; i < n.getRules().size(); i++){
			hashRules.put(n.getRules().get(i).getRuleName(),n.getRules().get(i));
		}
		n.getRules().get(0).accept(this);
	}

	public boolean succeed(){
		return vm.succeed();
	}


}
