package apeg.visitor.semantics;

import java.util.ArrayList;
import apeg.util.Environment;
import apeg.ast.types.*;

/**
 * Table with the types of the each operator.
 * @author deise
 *
 */

public class OperatorTables {

	
	private static ArrayList<NTType> mkArithmeticTable(){
		 ArrayList<NTType> t = new ArrayList<NTType>();
		 t.add(new NTType(new VType[]{VTyInt.getInstance(),VTyInt.getInstance()},new VType[]{VTyInt.getInstance()}));
		 t.add(new NTType(new VType[]{VTyFloat.getInstance(),VTyFloat.getInstance()},new VType[]{VTyFloat.getInstance()}));
		 return t;
	}
	
	private static ArrayList<NTType> mkLogicTable(){
		 ArrayList<NTType> t = new ArrayList<NTType>();
		 t.add(new NTType(new VType[]{VTyBool.getInstance(), VTyBool.getInstance()},new VType[]{VTyBool.getInstance()}));
		 return t;
	}
	
	private static ArrayList<NTType> mkRelationalTable(){
		 ArrayList<NTType> t = new ArrayList<NTType>();
		 t.add(new NTType(new VType[]{VTyInt.getInstance(),VTyInt.getInstance()},new VType[]{VTyBool.getInstance()}));
		 t.add(new NTType(new VType[]{VTyFloat.getInstance(),VTyFloat.getInstance()},new VType[]{VTyBool.getInstance()}));
		 t.add(new NTType(new VType[]{VTyString.getInstance(),VTyString.getInstance()},new VType[]{VTyBool.getInstance()}));
		 return t;
	}
	
	private static ArrayList<NTType> mkMinus(){
		 ArrayList<NTType> t = new ArrayList<NTType>();
		 t.add(new NTType(new VType[]{VTyInt.getInstance()},new VType[]{VTyInt.getInstance()}));
		 t.add(new NTType(new VType[]{VTyFloat.getInstance()},new VType[]{VTyFloat.getInstance()}));
		 return t;
	}
	
	private static ArrayList<NTType> mkNot(){
		 ArrayList<NTType> t = new ArrayList<NTType>();
		 t.add(new NTType(new VType[]{VTyBool.getInstance()},new VType[]{VTyBool.getInstance()}));
		 return t;
	}
	
    private static ArrayList<NTType> mkCompose(){
		 ArrayList<NTType> t = new ArrayList<NTType>();
		 t.add(new NTType(new VType[]{VTyLang.getInstance(),VTyLang.getInstance() },new VType[ {VTyLang.getInstance()}));
         t.add(new NTType(new VType[]{VTyLang.getInstance(),VTyGrammar.getInstance() },new VType[ {VTyLang.getInstance()}));
         t.add(new NTType(new VType[]{VTyGrammar.getInstance(), VTyLang.getInstance() },new VType[ {VTyLang.getInstance()}));
		 t.add(new NTType(new VType[]{VTyGrammar.getInstance(), VTyGrammar.getInstance() },new VType[ {VTyGrammar.getInstance()}));
		 return t;
	}
	
	public static Environment<String,ArrayList<NTType>> mkArithmeticEnv(){
		 Environment<String,ArrayList<NTType>> env = new Environment<String,ArrayList<NTType>>();
		 ArrayList<NTType> t = mkArithmeticTable();
		 ArrayList<NTType> tl = mkLogicTable();
		 ArrayList<NTType> tr = mkRelationalTable();
		 ArrayList<NTType> tm = mkMinus();
		 ArrayList<NTType> tn = mkNot();
		 
		 
		 env.add("ADD", t);
		 env.add("SUB", t);
		 env.add("MUL", t);
		 env.add("DIV", t);
		 env.add("MOD", t);
		 
		 
		 
		 env.add("AND", tl);
		 env.add("OR", tl);
		 
		 
		 env.add("GT" , tr); // >
		 env.add("LT", tr); // <
		 env.add("GE", tr); // >=
		 env.add("LE", tr); // <=
		 
		 env.add("EQ", tr);
		 env.add("NE", tr);
		 
		 env.add("MINUS",tm);
		 
		 env.add("NOT",tn);
		 
		 env.add("<<",mkCompose());
		 
		 return env;
	}
	
}
