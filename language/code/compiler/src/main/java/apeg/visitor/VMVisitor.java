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
	
	public VMVisitor(StringReader sr,Environment<String,NTInfo> e){
		try {
			env = e;
			stk = new Stack();
			vm = new ApegVM(sr);
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
		n.getPeg().accept(this);
		AndPEG z = new AndPEG(n.getSymInfo(),(APEG)stk.pop().getSecond();));
		stk.push(new Pair(VTyMetaPeg.getInstance(),z));
	}

	@Override
	public void visit(MetaAnyPEG n) {
		//AnyPEG z = new AnyPEG(new SymInfo(-1,-1));
		//stk.push(new Pair(VTyMetaPeg.getInstance(),z));
	}

	@Override
	public void visit(MetaAttribute n) {
		//n.getExpr().accept(this);
		//Attribute z = new Attribute(new SymInfo(-1,-1),(String)stk.pop().getSecond());
		//stk.push(new Pair(VTyMetaExpr.getInstance(),z));

	}

	@Override
	public void visit(MetaBindPEG n) {
		//Attribute a = n.getExprAtt().accept(this);
		//APEG b = n.getExprP().accept(this);
		//BindPEG z = new BindPEG(new SymInfo(-1,-1),b,a);
		//stk.push(new Pair(VTyMetaPeg.getInstance(),z));

	}


	@Override
	public void visit(MetaRangePEG n) {
		//n.getExpr().accept(this);
		//ChoiceList z = new ChoiceList(new SymInfo(-1,-1),(CharInterval)stk.pop().getSecond());
		//stk.push(new Pair(VTyMetaPeg.getInstance(),z));

	}

	@Override
	public void visit(MetaChoicePEG n) {
		//APEG a = n.getLeftExpr().accept(this);
		//APEG b = n.getRightExpr().accept(this);
		//ChoicePEG z = new ChoicePEG(new SymInfo(-1,-1),b,a);
		//stk.push(new Pair(VTyMetaPeg.getInstance(),z));
	}

	@Override
	public void visit(MetaConstraintPEG n) {
		//n.getExpr().accept(this);
		//ConstraintPEG z = new ConstraintPEG(new SymInfo(-1,-1),(Expr)stk.pop().getSecond());
		//stk.push(new Pair(VTyMetaPeg.getInstance(),z));

	}
	
	@Override
	public void visit(MetaBoolLit n) {
		n.getExpr().accept(this);
		BoolLit z = new BoolLit(n.getSymInfo() ,(boolean)stk.pop().getSecond());
		stk.push(new Pair(VTyMetaExpr.getInstance(),z));
	}

	@Override
	public void visit(MetaCharLit n) {
		n.getExpr().accept(this);
		CharLit z = new CharLit(n.getSymInfo(),(char)stk.pop().getSecond());
		stk.push(new Pair(VTyMetaExpr.getInstance(),z));

	}	

	@Override
	public void visit(MetaFloatLit n) {
		n.getExpr().accept(this);
		FloatLit z = new FloatLit(n.getSymInfo(),(float)stk.pop().getSecond());
		stk.push(new Pair(VTyMetaExpr.getInstance(),z));		
	}

	@Override
	public void visit(MetaIntLit n) {
		n.getExpr().accept(this);
		IntLit z = new IntLit(n.getSymInfo(),(int)stk.pop().getSecond());
		stk.push(new Pair(VTyMetaExpr.getInstance(),z));	
	}

	@Override
	public void visit(MetaStrLit n) {
		n.getExpr().accept(this);
		StrLit z = new StrLit(n.getSymInfo(),(String)stk.pop().getSecond());
		stk.push(new Pair(VTyMetaExpr.getInstance(),z));
	}

	@Override
	public void visit(MetaKleenePEG n) {
		//n.getExpr().accept(this);
		//KleenePEG z = new KleenePEG(new SymInfo(-1,-1),(APEG)stk.pop().getSecond());
		//stk.push(new Pair(VTyMetaPeg.getInstance(),z));

	}

	@Override
	public void visit(MetaLitPEG n) {
		//n.getExpr().accept(this);
		//LitPEG z = new LitPEG(new SymInfo(-1,-1),(String)stk.pop().getSecond());
		//stk.push(new Pair(VTyMetaPeg.getInstance(),z));
	}

	@Override
	public void visit(MetaMapLit n) {
		//n.getExpr().accept(this);
		//MapLit z = new MapLit(new SymInfo(-1,-1),(Pair<Expr,Expr>[])stk.pop().getSecond());
		//stk.push(new Pair(VTyMetaExpr.getInstance(),z));	
	}

	@Override
	public void visit(MetaNonterminalPEG n) {
		//APEG a = n.getLeftExpr().accept(this);
		//APEG b = n.getRightExpr().accept(this);
		//NonterminalPEG z = new NonterminalPEG(new SymInfo(-1,-1),b,a);
		//stk.push(new Pair(VTyMetaPeg.getInstance(),z));
	}

	@Override
	public void visit(MetaNotPEG n) {
		//n.getExpr().accept(this);
		//NotPEG z = new NotPEG(new SymInfo(-1,-1),(APEG)stk.pop().getSecond());
		//stk.push(new Pair(VTyMetaPeg.getInstance(),z));	
	}

	@Override
	public void visit(MetaOptionalPEG n) {
		//n.getExpr().accept(this);
		//OptionalPEG z = new OptionalPEG(new SymInfo(-1,-1),(APEG)stk.pop().getSecond());
		//stk.push(new Pair(VTyMetaPeg.getInstance(),z));	
	}

	@Override
	public void visit(MetaPKleene n) {
		//n.getExpr().accept(this);
		//PKleene z = new PKleene(new SymInfo(-1,-1),(APEG)stk.pop().getSecond());
		//stk.push(new Pair(VTyMetaPeg.getInstance(),z));	
	}

	@Override
	public void visit(MetaRulePEG n) {
		// TODO Auto-generated method stub
	}

	@Override
	public void visit(MetaSeqPEG n) {
		//n.getExpr().accept(this);
		//SeqPEG z = new SeqPEG(new SymInfo(-1,-1),(APEG[])stk.pop().getSecond());
		//stk.push(new Pair(VTyMetaPeg.getInstance(),z));	
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
		//n.getExpr().accept(this);
		//UpdatePEG z = new UpdatePEG(new SymInfo(-1,-1),(List<Pair<Attribute,Expr>>)stk.pop().getSecond());
		//stk.push(new Pair(VTyMetaPeg.getInstance(),z));	
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
		if(a.getFirst().match(VTyInt.getInstance())){
			stk.push(new Pair(VTyInt.getInstance(),(Integer)a.getSecond() + (Integer)b.getSecond()));
		}else if(a.getFirst().match(VTyFloat.getInstance())){
			stk.push(new Pair(VTyFloat.getInstance(),(Float)a.getSecond() + (Float)b.getSecond()));
		}else if(a.getFirst().match(VTyString.getInstance())){
			stk.push(new Pair(VTyString.getInstance(),(String)b.getSecond() + (String)a.getSecond()));
		}else{
			throw new RuntimeException("(" + n.getSymInfo().getLine() + "," + n.getSymInfo().getColumn() + ") Imcompatible operators for + "+a.getFirst().toString()+" , "+b.getFirst().toString());
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
		Pair<VType,Object> b = stk.pop();
		Pair<VType,Object> a = stk.pop();
		if(a.getFirst().match(VTyChar.getInstance())){
			stk.push(new Pair(VTyString.getInstance(),(Character)b.getSecond() + (Character)a.getSecond()));
		}else if(a.getFirst().match(VTyString.getInstance())){
			stk.push(new Pair(VTyString.getInstance(),(String)b.getSecond() + (String)a.getSecond()));
		}else{
			throw new RuntimeException("(" + n.getSymInfo().getLine() + "," + n.getSymInfo().getColumn() + ") Imcompatible operators for ++");
		}
	}


	@Override
	public void visit(Div n) {
		n.getLeft().accept(this);
		n.getRight().accept(this);
		Pair<VType,Object> a = stk.pop();
		Pair<VType,Object> b = stk.pop();
		if(a.getFirst().match(VTyInt.getInstance())){
			stk.push(new Pair(VTyFloat.getInstance(),(Integer)b.getSecond() / (Integer)a.getSecond()));
		}else if(a.getFirst().match(VTyFloat.getInstance())){
			stk.push(new Pair(VTyFloat.getInstance(),(Float)b.getSecond() / (Float)a.getSecond()));
		}else{
			throw new RuntimeException("(" + n.getSymInfo().getLine() + "," + n.getSymInfo().getColumn() + ") Imcompatible operators for /");
		}
	}

	@Override
	public void visit(Equals n) {
		n.getLeft().accept(this);
		n.getRight().accept(this);
		stk.push(new Pair(VTyBool.getInstance(),stk.pop().getSecond().equals(stk.pop().getSecond())));
	}

	@Override
	public void visit(Greater n) {
		n.getLeft().accept(this);
		n.getRight().accept(this);
		Pair<VType,Object> b = stk.pop();
		Pair<VType,Object> a = stk.pop();
		Boolean bl;
		if(a.getFirst().match(VTyInt.getInstance())){
			bl = (Integer)a.getSecond() > (Integer)b.getSecond();
		}else if(a.getFirst().match(VTyFloat.getInstance())){
			bl = (Float)a.getSecond() > (Float)b.getSecond();
		}else if(a.getFirst().match(VTyChar.getInstance())){
			bl = (Character)a.getSecond() > (Character)b.getSecond();
		}else if(a.getFirst().match(VTyString.getInstance())){
			bl = ((String)a.getSecond()).compareTo((String)b.getSecond()) == 1;
		}else{
			throw new RuntimeException("(" + n.getSymInfo().getLine() + "," + n.getSymInfo().getColumn() + ") Imcompatible operators for >");
		}
		stk.push(new Pair(VTyBool.getInstance(),bl));
	}

	@Override
	public void visit(GreaterEq n) {
		n.getLeft().accept(this);
		n.getRight().accept(this);
		Pair<VType,Object> b = stk.pop();
		Pair<VType,Object> a = stk.pop();
		Boolean bl;
		if(a.getFirst().match(VTyInt.getInstance())){
			bl = (Integer)a.getSecond() >= (Integer)b.getSecond();
		}else if(a.getFirst().match(VTyFloat.getInstance())){
			bl = (Float)a.getSecond() >= (Float)b.getSecond();
		}else if(a.getFirst().match(VTyChar.getInstance())){
			bl = (Character)a.getSecond() >= (Character)b.getSecond();
		}else if(a.getFirst().match(VTyString.getInstance())){
			bl = ((String)a.getSecond()).compareTo((String)b.getSecond()) == 1;
		}else{
			throw new RuntimeException("(" + n.getSymInfo().getLine() + "," + n.getSymInfo().getColumn() + ") Imcompatible operators for >=");
		}
		stk.push(new Pair(VTyBool.getInstance(),bl));
	}

	@Override
	public void visit(Less n) {
		n.getLeft().accept(this);
		n.getRight().accept(this);
		Pair<VType,Object> b = stk.pop();
		Pair<VType,Object> a = stk.pop();
		Boolean bl;
		if(a.getFirst().match(VTyInt.getInstance())){
			bl = (Integer)a.getSecond() < (Integer)b.getSecond();
		}else if(a.getFirst().match(VTyFloat.getInstance())){
			bl = (Float)a.getSecond() < (Float)b.getSecond();
		}else if(a.getFirst().match(VTyChar.getInstance())){
			bl = (Character)a.getSecond() < (Character)b.getSecond();
		}else if(a.getFirst().match(VTyString.getInstance())){
			bl = ((String)a.getSecond()).compareTo((String)b.getSecond()) == -1;
		}else{
			throw new RuntimeException("(" + n.getSymInfo().getLine() + "," + n.getSymInfo().getColumn() + ") Imcompatible operators for <");
		}
		stk.push(new Pair(VTyBool.getInstance(),bl));
	}

	//eero == -1 menor igual
	@Override
	public void visit(LessEq n) {
		n.getLeft().accept(this);
		n.getRight().accept(this);
		Pair<VType,Object> b = stk.pop();
		Pair<VType,Object> a = stk.pop();
		Boolean bl;
		if(a.getFirst().match(VTyInt.getInstance())){
			bl = (Integer)a.getSecond() <= (Integer)b.getSecond();
		}else if(a.getFirst().match(VTyFloat.getInstance())){
			bl = (Float)a.getSecond() <= (Float)b.getSecond();
		}else if(a.getFirst().match(VTyChar.getInstance())){
			bl = (Character)a.getSecond() <= (Character)b.getSecond();
		}else if(a.getFirst().match(VTyString.getInstance())){
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
		Pair<VType,Object> i = stk.pop();

		//stk.push(new Pair(VTyMap.getInstance(),m.getSecond().get(i.getSecond())));
		stk.push(new Pair( ((VTyMap)m.getFirst()).getTyParameter(), ((Hashtable)m.getSecond()).get( (String)i.getSecond()) ) );
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
		if(a.getFirst().match(VTyInt.getInstance())){
			stk.push(new Pair(VTyInt.getInstance(),(Integer)a.getSecond() * (Integer)b.getSecond()));
		}else if(a.getFirst().match(VTyFloat.getInstance())){
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
		if(a.getFirst().match(VTyInt.getInstance())){
			stk.push(new Pair(VTyInt.getInstance(),(Integer)b.getSecond() - (Integer)a.getSecond()));
		}else if(a.getFirst().match(VTyFloat.getInstance())){
			stk.push(new Pair(VTyFloat.getInstance(),(Float)b.getSecond() - (Float)a.getSecond()));
		}else{
			throw new RuntimeException("(" + n.getSymInfo().getLine() + "," + n.getSymInfo().getColumn() + ") Imcompatible operators for <=");
		}
	}

	@Override
	public void visit(UMinus n) {
		n.getExpr().accept(this);
		Pair<VType,Object> a = stk.pop();
		if(a.getFirst().match(VTyInt.getInstance())){
			stk.push(new Pair(VTyInt.getInstance(),-1 * (Integer)a.getSecond()));
		}else if(a.getFirst().match(VTyFloat.getInstance())){
			stk.push(new Pair(VTyFloat.getInstance(),-1.0 * (Float)a.getSecond()));
		}
	}

	@Override
	public void visit(MetaAdd n) {
 		n.getLeft().accept(this);
 		n.getRight().accept(this);
 		Expr a = (Expr)(stk.pop().getSecond());
 		Expr b = (Expr)(stk.pop().getSecond());
 		Add z = new Add(new SymInfo(-1,-1),b,a);
 		stk.push(new Pair(VTyMetaExpr.getInstance(),z));

	}

	@Override
	public void visit(MetaAnd n) {
 		n.getLeft().accept(this);
 		n.getRight().accept(this);
 		Expr a = (Expr)(stk.pop().getSecond());
 		Expr b = (Expr)(stk.pop().getSecond());
 		And z = new And(new SymInfo(-1,-1),b,a);
 		stk.push(new Pair(VTyMetaExpr.getInstance(),z));
	}

	@Override
	public void visit(MetaCompose n) {
// 		n.getLeft().accept(this);
// 		n.getRight().accept(this);
// 		Expr a = (Expr)(stk.pop().getSecond());
// 		Expr b = (Expr)(stk.pop().getSecond());
// 		Compose z = new Compose(new SymInfo(-1,-1),b,a);
// 		stk.push(new Pair(VTyMetaExpr.getInstance(),z));

	}

	@Override
	public void visit(MetaConcat n) {
// 		n.getLeft().accept(this);
// 		n.getRight().accept(this);
// 		Expr a = (Expr)(stk.pop().getSecond());
// 		Expr b = (Expr)(stk.pop().getSecond());
// 		Concat z = new Concat(new SymInfo(-1,-1),b,a);
// 		stk.push(new Pair(VTyMetaExpr.getInstance(),z));
	}

	@Override
	public void visit(MetaDiv n) {
 		n.getLeft().accept(this);
 		n.getRight().accept(this);
 		Expr a = (Expr)(stk.pop().getSecond());
 		Expr b = (Expr)(stk.pop().getSecond());
 		Div div = new Div(new SymInfo(-1,-1),b,a);
 		stk.push(new Pair(VTyMetaExpr.getInstance(),div));
	}

	@Override
	public void visit(MetaEquals n) {
 		n.getLeft().accept(this);
 		n.getRight().accept(this);
 		Expr a = (Expr)(stk.pop().getSecond());
 		Expr b = (Expr)(stk.pop().getSecond());
 		Equals z = new Equals(new SymInfo(-1,-1),b,a);
 		stk.push(new Pair(VTyMetaExpr.getInstance(),z));
	}

	@Override
	public void visit(MetaGreater n) {
 		n.getLeft().accept(this);
 		n.getRight().accept(this);
 		Expr a = (Expr)(stk.pop().getSecond());
 		Expr b = (Expr)(stk.pop().getSecond());
 		Greater z = new Greater(new SymInfo(-1,-1),b,a);
 		stk.push(new Pair(VTyMetaExpr.getInstance(),z));

	}

	@Override
	public void visit(MetaGreaterEq n) {
		n.getLeft().accept(this);
		n.getRight().accept(this);
		Expr a = (Expr)(stk.pop().getSecond());
		Expr b = (Expr)(stk.pop().getSecond());
		GreaterEq z = new GreaterEq(new SymInfo(-1,-1),b,a);
		stk.push(new Pair(VTyMetaExpr.getInstance(),z));
	}

	@Override
	public void visit(MetaLess n) {
		n.getLeft().accept(this);
		n.getRight().accept(this);
		Expr a = (Expr)(stk.pop().getSecond());
		Expr b = (Expr)(stk.pop().getSecond());
		Less z = new Less(new SymInfo(-1,-1),a,b);
		stk.push(new Pair(VTyMetaExpr.getInstance(),z));
	}

	@Override
	public void visit(MetaLessEq n) {
		n.getLeft().accept(this);
		n.getRight().accept(this);
		Expr a = (Expr)(stk.pop().getSecond());
		Expr b = (Expr)(stk.pop().getSecond());
		LessEq z = new LessEq(new SymInfo(-1,-1),b,a);
		stk.push(new Pair(VTyMetaExpr.getInstance(),z));
	}

	@Override
	public void visit(MetaMapAcces n) {
		n.getMap().accept(this);
		n.getIndex().accept(this);
		Expr a = (Expr)(stk.pop().getSecond());
		Expr b = (Expr)(stk.pop().getSecond());
		MapAcces z = new MapAcces(new SymInfo(-1,-1),b,a);
		stk.push(new Pair(VTyMetaExpr.getInstance(),z));
	}

	@Override
	public void visit(MetaMapExtension n) {
		n.getMap().accept(this);
		n.getKey().accept(this);
		n.getValue().accept(this);
		Expr a = (Expr)(stk.pop().getSecond());
		Expr b = (Expr)(stk.pop().getSecond());
		Expr c = (Expr)(stk.pop().getSecond());
		MapExtension z = new MapExtension(new SymInfo(-1,-1),c,b,a);
		stk.push(new Pair(VTyMetaExpr.getInstance(),z));

	}

	@Override
	public void visit(MetaMod n) {
		n.getLeft().accept(this);
		n.getRight().accept(this);
		Expr a = (Expr)(stk.pop().getSecond());
		Expr b = (Expr)(stk.pop().getSecond());
		Mod z = new Mod(new SymInfo(-1,-1),b,a);
		stk.push(new Pair(VTyMetaExpr.getInstance(),z));
	}

	@Override
	public void visit(MetaMult n) {
// 		n.getLeft().accept(this);
// 		n.getRight().accept(this);
// 		Expr a = (Expr)(stk.pop().getSecond());
// 		Expr b = (Expr)(stk.pop().getSecond());
// 		Mult z = new Mult(new SymInfo(-1,-1),b,a);
// 		stk.push(new Pair(VTyMetaExpr.getInstance(),z));
	}
	

	@Override
	public void visit(MetaNot n) {
// 		n.getExpr().accept(this);
// 		Expr a = (Expr)(stk.pop().getSecond());
// 		Not z = new Not(new SymInfo(-1,-1),a);
// 		stk.push(new Pair(VTyMetaExpr.getInstance(),z));
	}

	

	@Override
	public void visit(MetaNotEq n) {
// 		n.getLeft().accept(this);
// 		n.getRight().accept(this);
// 		Expr a = (Expr)(stk.pop().getSecond());
// 		Expr b = (Expr)(stk.pop().getSecond());
// 		NotEq z = new NotEq(new SymInfo(-1,-1),b,a);
// 		stk.push(new Pair(VTyMetaExpr.getInstance(),z));
	}

	@Override
	public void visit(MetaOr n) {
// 		n.getLeft().accept(this);
// 		n.getRight().accept(this);
// 		Expr a = (Expr)(stk.pop().getSecond());
// 		Expr b = (Expr)(stk.pop().getSecond());
// 		Or z = new Or(new SymInfo(-1,-1),b,a);
// 		stk.push(new Pair(VTyMetaExpr.getInstance(),z));
	}

	@Override
	public void visit(MetaSub n) {
// 		n.getLeft().accept(this);
// 		n.getRight().accept(this);
// 		Expr a = (Expr)(stk.pop().getSecond());
// 		Expr b = (Expr)(stk.pop().getSecond());
// 		Sub z = new Sub(new SymInfo(-1,-1),b,a);
// 		stk.push(new Pair(VTyMetaExpr.getInstance(),z));
	}

	@Override
	public void visit(MetaUMinus n) {
// 		n.getExpr().accept(this);
// 		Expr a = (Expr)(stk.pop().getSecond());
// 		UMinus z = new UMinus(new SymInfo(-1,-1),a);
// 		stk.push(new Pair(VTyMetaExpr.getInstance(),z));
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
		vm.startBind();
		n.getExpr().accept(this);
		if(vm.succeed()){
			vm.setValue(n.getAttribute().getName(),vm.getBind());
		}
		vm.stopBind();
	}

	@Override
	public void visit(RangePEG n) {
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
		if((Boolean)stk.pop().getSecond()){
			vm.success();
		}else{
			vm.fail();
		}
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
		vm.restore();
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
		NTInfo local = env.get(n.getName());
		List<Expr> l = n.getArgs();
		for(int i = 0;i < local.getSig().getNumInherited();i++){
			l.get(i).accept(this);
		}
		// Visitar rulepeg. 
		RulePEG b = hashRules.get(n.getName());
		if(b==null){
			throw new RuntimeException("Rule "+n.getName()+" not found");
		}
		b.accept(this);
		//os ultimos serao os primeiros
		if(vm.succeed()){
			for (int i = local.getSig().getNumSintetized()+local.getSig().getNumInherited();i>local.getSig().getNumInherited();i--) {
				vm.setValue(((Attribute)l.get(i-1)).getName(),stk.pop().getSecond());
			}		
		}else{}
		
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
		vm.beginAlt();
		n.getPegExp().accept(this);
		if(vm.succeed()){
		   vm.endAlt();
		   return;
		}
		vm.restore();
		vm.failAlt();
		vm.success();
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
			vm.restore();
			vm.failAlt();
			vm.success();
		}else{
			vm.fail();
		}
	}

	@Override
	public void visit(RulePEG n) {
		CTX t1=null,t2;

		// Montar o contexto tirando os valores do topo da pila.
		// Visitar o coporp do rule;
		NTInfo backupNti = nti;
		nti = env.get(n.getRuleName());
		CTX ctx = new CTX(nti.getSig().getNumInherited());
		for (int i=nti.getSig().getNumInherited();i>0;i--) {
			ctx.declareParam(n.getInh().get(i-1).getSecond(),i-1,stk.pop().getSecond());
		}
		
		vm.beginRule(n.getRuleName(),ctx);
		n.getPeg().accept(this);
		if(vm.succeed()){
			for(Expr e : n.getSyn()){
				e.accept(this);
			}
		// Visitar expressões sintetizados
		// Empilhar os resultados.
		}else{}
		System.out.println("context: "+n.getRuleName()+"\n  "+ctx.toString());
		vm.endRule();
		nti = backupNti;
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
		vm.success();
	}

	@Override
	public void visit(TyBool n) {
		// TODO Auto-generated method stub
	}

	public void visit(TyMetaPeg n) {
		// TODO Auto-generated method stub
	}

	public void visit(TyMetaExpr n) {
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
		System.out.println("Read until " + vm.getLine() + ", " + vm.getColumn());
	}

	public boolean succeed(){
		return vm.succeed();
	}


}
