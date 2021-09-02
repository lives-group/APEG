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
	private CTX lastctx;
	private boolean debug;
	// Criar uma variável NTInfo local;


	public VMVisitor(String path,Environment<String,NTInfo> e){
		try {
			env = e;
			stk = new Stack();
			vm = new ApegVM(path);
			debug = false;
		} catch(Exception exc) {
			exc.printStackTrace();
		}
	}
	
	public VMVisitor(StringReader sr,Environment<String,NTInfo> e){
		try {
			env = e;
			stk = new Stack();
			vm = new ApegVM(sr);
			debug = false;
		} catch(Exception exc) {
			exc.printStackTrace();
		}
	}

	public void setDebugMode(boolean b){ debug = b; }

	public ApegVM getVM(){ return vm; }

	public CTX getLastCTX(){return lastctx;}

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
		AndPEG z = new AndPEG(n.getSymInfo(),(APEG)stk.pop().getSecond());
		stk.push(new Pair(VTyMetaPeg.getInstance(),z));
	}

	@Override
	public void visit(MetaAnyPEG n) {
		AnyPEG z = new AnyPEG(n.getSymInfo());
		stk.push(new Pair(VTyMetaPeg.getInstance(),z));
	}

	@Override
	public void visit(MetaAttribute n) {
		n.getExpr().accept(this);
		Attribute z = new Attribute(n.getSymInfo(),(String)stk.pop().getSecond());
		stk.push(new Pair(VTyMetaExpr.getInstance(),z));

	}

	@Override
	public void visit(MetaBindPEG n) {
        n.getExprAtt().accept(this);
		Attribute a = (Attribute)stk.pop().getSecond();
		n.getExprP().accept(this);
		APEG b = (APEG)stk.pop().getSecond();
		BindPEG z = new BindPEG(n.getSymInfo(),a,b);
		stk.push(new Pair(VTyMetaPeg.getInstance(),z));
	}


	@Override
	public void visit(MetaRangePEG n) {
		n.getEndExpr().accept(this);
		n.getStartExpr().accept(this);
		Character s = (Character)stk.pop().getSecond();
		Character e = (Character)stk.pop().getSecond();
		n.getEndExpr().accept(this);
		RangePEG z = new RangePEG(n.getSymInfo(),new CharInterval(s,e));
		stk.push(new Pair(VTyMetaPeg.getInstance(),z));
	}

	@Override
	public void visit(MetaChoicePEG n) {
		n.getLeftPeg().accept(this);
		APEG a = (APEG)stk.pop().getSecond();
		n.getRightPeg().accept(this);
		APEG b = (APEG)stk.pop().getSecond();
		ChoicePEG z = new ChoicePEG(n.getSymInfo(),b,a);
		stk.push(new Pair(VTyMetaPeg.getInstance(),z));
	}

	@Override
	public void visit(MetaConstraintPEG n) {
		n.getExpr().accept(this);
		ConstraintPEG z = new ConstraintPEG(n.getSymInfo(),(Expr)stk.pop().getSecond());
		stk.push(new Pair(VTyMetaPeg.getInstance(),z));

	}


	@Override
	public void visit(MetaNonterminalPEG n) {
		n.getName().accept(this);
        String a = (String)stk.pop().getSecond();

		n.getArgs().accept(this);
		ArrayList<Expr> b = (ArrayList<Expr>)stk.pop().getSecond();

		NonterminalPEG z = new NonterminalPEG(n.getSymInfo(),a,b);
		stk.push(new Pair(VTyMetaPeg.getInstance(),z));
	}

	@Override
	public void visit(MetaNotPEG n) {
		n.getExpr().accept(this);
		NotPEG z = new NotPEG(n.getSymInfo(),(APEG)stk.pop().getSecond());
		stk.push(new Pair(VTyMetaPeg.getInstance(),z));
	}

	@Override
	public void visit(MetaOptionalPEG n) {
		n.getExpr().accept(this);
		OptionalPEG z = new OptionalPEG(n.getSymInfo(),(APEG)stk.pop().getSecond());
		stk.push(new Pair(VTyMetaPeg.getInstance(),z));
	}

	public void visit(MetaPKleene n) {
		n.getPegExpr().accept(this);
		PKleene z = new PKleene(n.getSymInfo(),(APEG)stk.pop().getSecond());
		stk.push(new Pair(VTyMetaPeg.getInstance(),z));
	}

	public void visit(MetaRulePEG n) {
        n.getRuleName().accept(this);
        String s = (String)stk.pop().getSecond();
        
        n.getTypes().accept(this);
        ArrayList<Type> lty = (ArrayList<Type>)stk.pop().getSecond();

        n.getInh().accept(this);
        ArrayList<String> linh = (ArrayList<String>)stk.pop().getSecond();

        n.getSyn().accept(this);
        ArrayList<Expr> lsyn = (ArrayList<Expr>)stk.pop().getSecond();

        n.getPeg().accept(this);
        APEG body = (APEG)stk.pop().getSecond();
        
        if(lty.size() != linh.size()){
            throw new RuntimeException("List of types and Inherited arguments must have same size"); 
        }
        ArrayList<Pair<Type,String>> param = new ArrayList<Pair<Type,String>>(linh.size());
        for(int i =0; i < linh.size(); i++){
            param.add(new Pair<Type,String>(lty.get(i), linh.get(i)));
        }
        RulePEG rl = new RulePEG(n.getSymInfo(),s, RulePEG.Annotation.NONE,param,lsyn, body );          
        ArrayList<RulePEG> ruleList = new ArrayList<RulePEG>();
        ruleList.add(rl);
        stk.push(new Pair(VTyGrammar.getInstance(),ruleList));
	}

	public void visit(MetaSeqPEG n) {
		APEG[] zs = new APEG[n.getExpr().length];
		int k = 0;
		for(Expr x :  n.getExpr()){
		   x.accept(this);
           zs[k++] = (APEG)stk.pop().getSecond();
		}
		SeqPEG z = new SeqPEG(n.getSymInfo(),zs);
		stk.push(new Pair(VTyMetaPeg.getInstance(),z));
	}

	@Override
	public void visit(MetaTyBool n){
	     stk.push(new Pair<VType,Object>(VTyMetaType.getInstance(),new TyBool(n.getSymInfo()) ) );
    }

	@Override
	public void visit(MetaTyChar n) {
		stk.push(new Pair<VType,Object>(VTyMetaType.getInstance(),new TyChar(n.getSymInfo()) ) );
	}

	@Override
	public void visit(MetaTyFloat n) {
		stk.push(new Pair<VType,Object>(VTyMetaType.getInstance(),new TyFloat(n.getSymInfo()) ) );
	}

	@Override
	public void visit(MetaTyGrammar n) {
		stk.push(new Pair<VType,Object>(VTyMetaType.getInstance(),new TyGrammar(n.getSymInfo()) ) );
	}

	@Override
	public void visit(MetaTyInt n) {
		stk.push(new Pair<VType,Object>(VTyMetaType.getInstance(),new TyInt(n.getSymInfo()) ) );
	}

	@Override
	public void visit(MetaTyLang n) {
		stk.push(new Pair<VType,Object>(VTyMetaType.getInstance(),new TyLang(n.getSymInfo()) ) );
	}

	@Override
	public void visit(MetaTyMap n) {
		n.getExpr().accept(this);
        Type t = new TyMap(n.getSymInfo(),(Type)stk.pop().getSecond());
        stk.push(new Pair(VTyMetaType.getInstance(), t));
	}

	@Override
	public void visit(MetaTyTy n) {
        
	}

	@Override
	public void visit(MetaTyString n) {
		stk.push(new Pair<VType,Object>(VTyMetaType.getInstance(),new TyString(n.getSymInfo()) ) );
	}

	@Override
	public void visit(MetaUpdatePEG n) {
		//TODO
		//n.getExpr().accept(this);
		//UpdatePEG z = new UpdatePEG(new SymInfo(-1,-1),(List<Pair<Attribute,Expr>>)stk.pop().getSecond());
		//stk.push(new Pair(VTyMetaPeg.getInstance(),z));	
	}

	@Override
	public void visit(MetaVar n) {
		// TODO Auto-generated method stub
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
		n.getExpr().accept(this);
		KleenePEG z = new KleenePEG(n.getSymInfo(),(APEG)stk.pop().getSecond());
		stk.push(new Pair(VTyMetaPeg.getInstance(),z));
	}

	@Override
	public void visit(MetaLitPEG n) {
		n.getExpr().accept(this);
		LitPEG z = new LitPEG(n.getSymInfo(),(String)stk.pop().getSecond());
		stk.push(new Pair(VTyMetaPeg.getInstance(),z));
	}

	@Override
	public void visit(MetaMapLit n) {
	    //TODO
		//n.getExpr().accept(this);
		//MapLit z = new MapLit(new SymInfo(-1,-1),(Pair<Expr,Expr>[])stk.pop().getSecond());
		//stk.push(new Pair(VTyMetaExpr.getInstance(),z));
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
//
//g1   [a : 'a', b : '2',c : '8']
//
//g2   [c : 'b',a : '0', d : 'e'] 
//  g3 = g1 << g2 = [f: 'a', h 'b'] XX
//  g3 = g1 << g2 = [f: 'a' / 'b'] XX
//
    
    private boolean chkParams(List<Pair<Type,String>> le, List<Pair<Type,String>> lr){
       boolean r = true;
       if(le.size() == lr.size()){
           for(int i =0; i < le.size(); i++){
               r = r && le.get(i).getFirst().match(lr.get(i).getFirst());
           }
       }else{ return false;}
       return r;
    }
    
    private ArrayList<RulePEG> mergeGrammars(ArrayList<RulePEG> re,ArrayList<RulePEG> rd){
        ArrayList<RulePEG> a = new ArrayList<RulePEG>();
        boolean merger = false;
        RulePEG intersec;
        APEG body;
        RulePEG y;
        for (RulePEG x: re) {
            body = x.getPeg();
            int j = 0;
            while (j < rd.size()) {
                y = rd.get(j);
                if (x.getRuleName() == y.getRuleName()) {
                    if(!chkParams(x.getInh(), y.getInh()) || x.getSyn().size() != y.getSyn().size()){
                        throw new RuntimeException(x.getSymInfo().toString()+" Type parameters for rules diverge: rules " + x.getRuleName() + " and " + y.getRuleName());
                    }
                    body = new ChoicePEG(y.getSymInfo(), body, y.getPeg());
                    rd.remove(j);
                    merger = true;
                }else{ j++;}
            }
            a.add(new RulePEG(x.getSymInfo(),x.getRuleName(),x.getAnno(),x.getInh(),x.getSyn(),body));
        }
        a.addAll(rd);
        return a;
    }
     
	@Override
	public void visit(Compose n) {
		n.getLeft().accept(this);
		ArrayList<RulePEG> re,rd;
		if(stk.peek().getFirst().match(VTyGrammar.getInstance()) ){
		    n.getRight().accept(this);
		    if(stk.peek().getFirst().match(VTyGrammar.getInstance()) ){
		        rd = (ArrayList<RulePEG>)stk.pop().getSecond();
		        re = (ArrayList<RulePEG>)stk.pop().getSecond();
		        //re.addAll(rd);
		        re = mergeGrammars(re,rd);
		        stk.push(new Pair<VType,Object>(VTyGrammar.getInstance(),re));
		        return;
		    }
		}else if(stk.peek().getFirst().match(VTyLang.getInstance())){
		    Pair<Grammar,Environment<String,NTInfo>> lan = (Pair<Grammar,Environment<String,NTInfo>>)stk.pop().getSecond();
		    Grammar gext;
		    n.getRight().accept(this);
		    if(stk.peek().getFirst().match(VTyGrammar.getInstance()) ){
		        gext = (Grammar)lan.getFirst();
		        re =  (ArrayList<RulePEG>)((Grammar)lan.getFirst()).cloneRules();
		        rd = mergeGrammars((ArrayList)re, (ArrayList<RulePEG>)stk.peek().getSecond());
		        gext = new Grammar(gext.getSymInfo(), gext.getName(),gext.getOptions(),rd);
		        TypeCheckerVisitor tychk = new TypeCheckerVisitor();
		        gext.accept(tychk);
		        if(tychk.getError().size() == 0){
		            System.out.println("Type erros when composing grammars at " + n.getSymInfo().toString());
		            ErrorsMsg err = ErrorsMsg.getInstance();
		            for(ErrorEntry e: tychk.getError()){
		                 System.out.println(err.translate(e));
		            }
		            throw new RuntimeException("Composition error at " + n.getSymInfo().toString()); 
                }
		    }
		    return;
		}
	}

	@Override
	public void visit(Concat n) {
		n.getLeft().accept(this);
		n.getRight().accept(this);
		Pair<VType,Object> b = stk.pop();
		Pair<VType,Object> a = stk.pop();
		if(a.getFirst().match(VTyChar.getInstance())){
			stk.push(new Pair(VTyString.getInstance(),(Character)a.getSecond() + (Character)b.getSecond()));
		}else if(a.getFirst().match(VTyString.getInstance())){
			stk.push(new Pair(VTyString.getInstance(),(String)a.getSecond() + (String)b.getSecond()));
		}if(a.getFirst() instanceof VTyList && b.getFirst() instanceof VTyList){
		    ((ArrayList<Object>)a.getSecond()).addAll( (ArrayList<Object>)b.getSecond() );
		    stk.push(new Pair(a.getFirst(),a.getSecond()) );
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


	public void visit(ListAcces n){
	    n.getList().accept(this);
	    ArrayList<Object> o = (ArrayList<Object>)stk.pop().getSecond();
	    n.getIndex().accept(this);
	    
	    Integer idx = (Integer)stk.peek().getSecond();
	    VType ty = stk.peek().getFirst();
	    stk.push(new Pair(ty, o.get(idx)) );
	}

	public void visit(ListLit n){
	    ArrayList<Object> l = new ArrayList<Object>();
	    VType ty = null;
	    for(Expr e : n.getElems()){
	        e.accept(this);
	        l.add(stk.peek().getSecond());
	        ty = stk.pop().getFirst();
	    }
	    stk.push(new Pair(new VTyList(ty),l) );
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
        //TODO
// 		n.getLeft().accept(this);
// 		n.getRight().accept(this);
// 		Expr a = (Expr)(stk.pop().getSecond());
// 		Expr b = (Expr)(stk.pop().getSecond());
// 		Compose z = new Compose(new SymInfo(-1,-1),b,a);
// 		stk.push(new Pair(VTyMetaExpr.getInstance(),z));

	}

	@Override
	public void visit(MetaConcat n) {
 		n.getLeft().accept(this);
 		n.getRight().accept(this);
 		Expr a = (Expr)(stk.pop().getSecond());
 		Expr b = (Expr)(stk.pop().getSecond());
 		Concat z = new Concat(n.getSymInfo(),b,a);
 		stk.push(new Pair(VTyMetaExpr.getInstance(),z));
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
		Less z = new Less(new SymInfo(-1,-1),b,a);
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
 		n.getLeft().accept(this);
 		n.getRight().accept(this);
 		Expr a = (Expr)(stk.pop().getSecond());
 		Expr b = (Expr)(stk.pop().getSecond());
 		Mult z = new Mult(n.getSymInfo(),b,a);
 		stk.push(new Pair(VTyMetaExpr.getInstance(),z));
	}
	

	@Override
	public void visit(MetaNot n) {
 		n.getPegExpr().accept(this);
 		Expr a = (Expr)(stk.pop().getSecond());
 		Not z = new Not(n.getSymInfo(),a);
 		stk.push(new Pair(VTyMetaExpr.getInstance(),z));
	}

	

	@Override
	public void visit(MetaNotEq n) {
 		n.getLeft().accept(this);
 		n.getRight().accept(this);
 		Expr a = (Expr)(stk.pop().getSecond());
 		Expr b = (Expr)(stk.pop().getSecond());
 		NotEq z = new NotEq(n.getSymInfo(),b,a);
 		stk.push(new Pair(VTyMetaExpr.getInstance(),z));
	}

	@Override
	public void visit(MetaOr n) {
 		n.getLeft().accept(this);
 		n.getRight().accept(this);
 		Expr a = (Expr)(stk.pop().getSecond());
 		Expr b = (Expr)(stk.pop().getSecond());
 		Or z = new Or(n.getSymInfo(),b,a);
 		stk.push(new Pair(VTyMetaExpr.getInstance(),z));
	}

	@Override
	public void visit(MetaSub n) {
 		n.getLeft().accept(this);
 		n.getRight().accept(this);
 		Expr a = (Expr)(stk.pop().getSecond());
 		Expr b = (Expr)(stk.pop().getSecond());
 		Sub z = new Sub(n.getSymInfo(),b,a);
 		stk.push(new Pair(VTyMetaExpr.getInstance(),z));
	}

	@Override
	public void visit(MetaUMinus n) {
 		n.getExpr().accept(this);
 		Expr a = (Expr)(stk.pop().getSecond());
 		UMinus z = new UMinus(n.getSymInfo(),a);
 		stk.push(new Pair(VTyMetaExpr.getInstance(),z));
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
		RulePEG rule = null;
		if( local.getSig().getNumInherited() > 0){ 
		    l.get(0).accept(this);
		    if(stk.peek().getFirst().match(VTyLang.getInstance()) ){
		         Grammar r = (Grammar)stk.peek().getSecond();   
		         for(RulePEG rp : r.getRules()){
		              if( rp.getRuleName().equals(n.getName()) ){
		                   rule = rp;
		                   break;
		              }
		         }
		         if(rule == null){ throw new RuntimeException(n.getSymInfo() + " Calling an abscented composed rule " + n.getName()); }
		    }
		    for(int i = 1;i < local.getSig().getNumInherited();i++){
			    l.get(i).accept(this);
		    }
		}
		// Visitar rulepeg. 
		rule = rule == null ? hashRules.get(n.getName()) : rule;
		if(rule==null){
			throw new RuntimeException("Rule "+n.getName()+" not found");
		}
		rule.accept(this);
		//os ultimos serao os primeiros
		if(vm.succeed()){
			for (int i = local.getSig().getNumSintetized()+local.getSig().getNumInherited();i>local.getSig().getNumInherited();i--) {
				vm.setValue(((Attribute)l.get(i-1)).getName(),stk.pop().getSecond());
			}		
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
		CTX t1=null;

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
		lastctx = ctx;
		if(debug){System.out.println("context: "+n.getRuleName()+"\n  "+ctx.toString());}
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

	public void visit(TyBool n){}
	public void visit(TyMetaPeg n){}
	public void visit(TyMetaExpr n){}
	public void visit(TyMetaTy n){}
	public void visit(TyChar n){}
	public void visit(TyFloat n){}
	public void visit(TyGrammar n){}
	public void visit(TyInt n){}
	public void visit(TyLang n){}
	public void visit(TyMap n){}
	public void visit(TyMeta n){}
	public void visit(TyString n){}
	public void visit(TyList n){}

	@Override
	public void visit(Grammar n) {
		hashRules = new Hashtable<String,RulePEG>();
		for(int i = 0 ; i < n.getRules().size(); i++){
			hashRules.put(n.getRules().get(i).getRuleName(),n.getRules().get(i));
		}
		n.getRules().get(0).accept(this);
		if(debug){System.out.println("Read until " + vm.getLine() + ", " + vm.getColumn());}
	}

	public boolean succeed(){
		return vm.succeed();
	}
}
