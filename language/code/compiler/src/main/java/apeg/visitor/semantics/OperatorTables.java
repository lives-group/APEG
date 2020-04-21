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
		 t.add(new NTType(new Type[]{new TyInt(null),new TyInt(null)},new Type[]{new TyInt(null)}));
		 t.add(new NTType(new Type[]{new TyFloat(null),new TyFloat(null)},new Type[]{new TyFloat(null)}));
		 return t;
	}
	
	private static ArrayList<NTType> mkLogicTable(){
		 ArrayList<NTType> t = new ArrayList<NTType>();
		 t.add(new NTType(new Type[]{new TyBool(null),new TyBool(null)},new Type[]{new TyBool(null)}));
		 return t;
	}
	
	private static ArrayList<NTType> mkRelationalTable(){
		 ArrayList<NTType> t = new ArrayList<NTType>();
		 t.add(new NTType(new Type[]{new TyInt(null),new TyInt(null)},new Type[]{new TyBool(null)}));
		 t.add(new NTType(new Type[]{new TyFloat(null),new TyFloat(null)},new Type[]{new TyBool(null)}));
		 t.add(new NTType(new Type[]{new TyString(null),new TyString(null)},new Type[]{new TyBool(null)}));
		 return t;
	}
	
	private static ArrayList<NTType> mkMinus(){
		 ArrayList<NTType> t = new ArrayList<NTType>();
		 t.add(new NTType(new Type[]{new TyInt(null)},new Type[]{new TyInt(null)}));
		 t.add(new NTType(new Type[]{new TyFloat(null)},new Type[]{new TyFloat(null)}));
		 return t;
	}
	
	private static ArrayList<NTType> mkNot(){
		 ArrayList<NTType> t = new ArrayList<NTType>();
		 t.add(new NTType(new Type[]{new TyBool(null)},new Type[]{new TyBool(null)}));
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
		 
		 env.add("MINUS",tm);
		 
		 env.add("NOT",tn);
		 
		 return env;
	}
	
}
