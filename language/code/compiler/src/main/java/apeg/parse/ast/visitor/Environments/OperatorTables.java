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
	
	private static ArrayList<NTType> mkLogicTable(){
		 ArrayList<NTType> t = new ArrayList<NTType>();
		 t.add(new NTType(new TypeNode[]{new BooleanTypeNode(),new BooleanTypeNode()},new TypeNode[]{new BooleanTypeNode()}));
		 return t;
	}
	
	private static ArrayList<NTType> mkRelationalTable(){
		 ArrayList<NTType> t = new ArrayList<NTType>();
		 t.add(new NTType(new TypeNode[]{new IntTypeNode(),new IntTypeNode()},new TypeNode[]{new BooleanTypeNode()}));
		 t.add(new NTType(new TypeNode[]{new FloatTypeNode(),new FloatTypeNode()},new TypeNode[]{new BooleanTypeNode()}));
		 t.add(new NTType(new TypeNode[]{new StringTypeNode(),new StringTypeNode()},new TypeNode[]{new BooleanTypeNode()}));
		 return t;
	}
	
	public static Environment<String,ArrayList<NTType>> mkArithmeticEnv(){
		 Environment<String,ArrayList<NTType>> env = new Environment<String,ArrayList<NTType>>();
		 ArrayList<NTType> t = mkArithmeticTable();
		 ArrayList<NTType> tl = mkLogicTable();
		 ArrayList<NTType> tr = mkRelationalTable();
		 env.add(apeg.parse.ast.BinaryExprNode.Operator.ADD.name(), t);
		 env.add(apeg.parse.ast.BinaryExprNode.Operator.SUB.name(), t);
		 
		 env.add("AND", tl);
		 env.add("OR", tl);
		 
		 
		 env.add(apeg.parse.ast.BinaryExprNode.Operator.GT.name() , tr); // >
		 env.add(apeg.parse.ast.BinaryExprNode.Operator.LT.name(), tr); // <
		 env.add(apeg.parse.ast.BinaryExprNode.Operator.GE.name(), tr); // >=
		 env.add(apeg.parse.ast.BinaryExprNode.Operator.LE.name(), tr); // <=
		 
		 env.add(apeg.parse.ast.EqualityExprNode.EqualityOperator.EQ.name(), tr);
		 
		 
		 return env;
	}
	
}
