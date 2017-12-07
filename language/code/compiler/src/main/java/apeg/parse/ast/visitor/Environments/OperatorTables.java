package apeg.parse.ast.visitor.Environments;

import java.util.ArrayList;
import apeg.parse.ast.*;

/**
 * Table with the types of the each operator.
 * @author deise
 *
 */

public class OperatorTables {

	
	private static ArrayList<NTType> mkArithmeticTable(){
		 ArrayList<NTType> t = new ArrayList<NTType>();
		 t.add(new NTType(new TypeNode[]{new IntTypeNode(),new IntTypeNode()},new TypeNode[]{new IntTypeNode()}));
		 t.add(new NTType(new TypeNode[]{new FloatTypeNode(),new FloatTypeNode()},new TypeNode[]{new FloatTypeNode()}));
		 return t;
	}
	
	public static Environment<String,ArrayList<NTType>> mkArithmeticEnv(){
		 Environment<String,ArrayList<NTType>> env = new Environment<String,ArrayList<NTType>>();
		 ArrayList<NTType> t = mkArithmeticTable();
		 env.add(apeg.parse.ast.BinaryExprNode.Operator.ADD.name(), t);
		 env.add(apeg.parse.ast.BinaryExprNode.Operator.SUB.name(), t);
		 
		 
		 return env;
	}
	
}
