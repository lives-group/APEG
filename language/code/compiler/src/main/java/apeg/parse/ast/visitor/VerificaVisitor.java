package apeg.parse.ast.visitor;

import java.util.Hashtable;
import apeg.parse.ast.AndExprNode;
import apeg.parse.ast.AndPegNode;
import apeg.parse.ast.AnyPegNode;
import apeg.parse.ast.AssignmentNode;
import apeg.parse.ast.AttributeExprNode;
import apeg.parse.ast.BinaryExprNode;
import apeg.parse.ast.BindPegNode;
import apeg.parse.ast.BooleanExprNode;
import apeg.parse.ast.BooleanTypeNode;
import apeg.parse.ast.CallExprNode;
import apeg.parse.ast.ChoicePegNode;
import apeg.parse.ast.ConstraintPegNode;
import apeg.parse.ast.EqualityExprNode;
import apeg.parse.ast.ExprNode;
import apeg.parse.ast.FloatExprNode;
import apeg.parse.ast.FloatTypeNode;
import apeg.parse.ast.GrammarNode;
import apeg.parse.ast.GrammarTypeNode;
import apeg.parse.ast.GroupPegNode;
import apeg.parse.ast.IntExprNode;
import apeg.parse.ast.IntTypeNode;
import apeg.parse.ast.LambdaPegNode;
import apeg.parse.ast.LiteralPegNode;
import apeg.parse.ast.MetaPegExprNode;
import apeg.parse.ast.MinusExprNode;
import apeg.parse.ast.NonterminalPegNode;
import apeg.parse.ast.NotExprNode;
import apeg.parse.ast.NotPegNode;
import apeg.parse.ast.OptionalPegNode;
import apeg.parse.ast.OrExprNode;
import apeg.parse.ast.PegNode;
import apeg.parse.ast.PlusPegNode;
import apeg.parse.ast.RuleNode;
import apeg.parse.ast.RuleTypeNode;
import apeg.parse.ast.SequencePegNode;
import apeg.parse.ast.StarPegNode;
import apeg.parse.ast.StringExprNode;
import apeg.parse.ast.StringTypeNode;
import apeg.parse.ast.TypeNode;
import apeg.parse.ast.UpdatePegNode;
import apeg.parse.ast.UserTypeNode;
import apeg.parse.ast.VarDeclarationNode;
import apeg.parse.ast.visitor.Environments.NTType;
import apeg.parse.ast.visitor.Environments.RuleEnvironment;

public class VerificaVisitor implements ASTNodeVisitor {
	
	private String non= "" ;
	private String atribute= "" ;
	private String regra= "" ;
	private int contador=0;
	private int count = 0;
	private final RuleEnvironment table;
	
	
	TypeNode tipos;
	
	
	 public VerificaVisitor(RuleEnvironment ruleEnvironment){
		 table=ruleEnvironment;
	 }
	 
	@Override
	public void visit(AndExprNode expr) {
		expr.getLeftExpr().accept(this);
		expr.getRightExpr().accept(this);
	}

	@Override
	public void visit(AttributeExprNode expr) {
		expr.getName();
		atribute = expr.getName();
	}

	@Override
	public void visit(BinaryExprNode expr) {
		expr.getLeftExpr().accept(this);
		expr.getRightExpr().accept(this);
		expr.getOperation();
		
	}

	@Override
	public void visit(BooleanExprNode expr) { ////////////////////////////////////////////
		expr.getValue();
		System.out.println(expr.getValue());
	}

	@Override
	public void visit(CallExprNode expr) {
		// TODO Auto-generated method stub
		expr.getName();

		for(ExprNode p : expr.getParameters()){
			p.accept(this);
		}
	
	}

	@Override
	public void visit(EqualityExprNode expr) { 
		expr.getLeftExpr().accept(this);
		expr.getRightExpr().accept(this);
		expr.getEqualityType(); //switch 
		
	}

	@Override
	public void visit(FloatExprNode expr) { ////////////////////////////
		expr.getValue();		
	}

	@Override
	public void visit(IntExprNode expr) { ////////////////////////////////
		expr.getValue();
		//System.out.println(expr.getValue());
	}

	@Override
	public void visit(MetaPegExprNode expr) {
		expr.getExpr().accept(this);
		
	}

	@Override
	public void visit(MinusExprNode expr) {
		expr.getExpr().accept(this);
		
	}

	@Override
	public void visit(NotExprNode expr) {
		expr.getExpr().accept(this);
		
	}

	@Override
	public void visit(OrExprNode expr) {
		
		expr.getLeftExpr().accept(this);
		expr.getRightExpr().accept(this);
		
	}

	@Override
	public void visit(StringExprNode expr) { ///////////////////////////
		expr.getValue();
	}

	@Override
	public void visit(AndPegNode peg) {
		peg.getPeg().accept(this);
		
	}

	@Override
	public void visit(AnyPegNode peg) {
		
	}

	@Override
	public void visit(BindPegNode peg) {
		peg.getPeg().accept(this);
		peg.getVariable();	//se o tipo e string
	}

	@Override
	public void visit(ChoicePegNode peg) {
		peg.getLeftPeg().accept(this);
		peg.getRightPeg().accept(this);
	}

	@Override
	public void visit(ConstraintPegNode peg) {
		peg.getExpr().accept(this);
	}

	@Override
	public void visit(GroupPegNode peg) {
		peg.getRanges();
	}

	@Override
	public void visit(LambdaPegNode peg) {
	}

	@Override
	public void visit(LiteralPegNode peg) {
		peg.getValue();
	}

	@Override
	public void visit(NonterminalPegNode peg) { ////////////////////////////////
	
		for(ExprNode p:peg.getExprs()) {
			p.accept(this);
			contador += count + 1;
		}
		 
	    // non = peg.getName();
	     //System.out.println("Non:"+non);
	     
	     //System.out.println("Keys: "+ table.getTable().get(non));
	    
	    
	     //System.out.println("Quantidade de parametros passados para o nao terminal "+ non + ":" + contador+ " parâmetros\n");
	     
	   //if(!(table.get(regra).getTable().containsKey(atribute))){
    	 // System.err.println("Parâmetro " + atribute + " nao declarado");
       //}
   
	  //ntt.match(verifica);
	     //if(table.get(non).getTable().size() != contador){
	    	// System.err.println("Error: Quantidade errada de parâmetros!!!!\n");
				//}
	     
	     atribute = " ";
	     contador = 0;
	         
	}

	@Override
	public void visit(NotPegNode peg) {
		peg.getPeg().accept(this);
		
	}

	@Override
	public void visit(OptionalPegNode peg) {
		peg.getPeg().accept(this);
		
	}

	@Override
	public void visit(PlusPegNode peg) {
		peg.getPeg().accept(this);
		
	}

	@Override
	public void visit(SequencePegNode peg) {
		
		for(PegNode p:peg.getPegs()) {
			p.accept(this);
		}
		
	}

	@Override
	public void visit(StarPegNode peg) {
		peg.getPeg().accept(this);
		
	}

	@Override
	public void visit(UpdatePegNode peg) {
		for(AssignmentNode p:peg.getAssignments()) {
			p.accept(this);
		}
		
	}

	@Override
	public void visit(AssignmentNode assign) {
	   assign.getExpr().accept(this);
	   assign.getVariable();
		
	}

	//public void visit(FunctionNode func) {
	//	func.getName();
		
	//}
	
	
	public void visit(GrammarNode grammar) {
		grammar.getFunctions();
		grammar.getFunctionsSources(); //lista
		grammar.getName();
		grammar.getOptions(); //lista
		grammar.getPreamble();
		
		for(RuleNode r : grammar.getRules()){
			System.out.println("\n*******************REGRA: "+ r.getName()+"*********************");
			regra = r.getName();
			r.accept(this);
		}
		
	}

	@Override
	public void visit(RuleNode rule) {
		rule.getAnnotation();
		rule.getExpr().accept(this);
		rule.getName();
		
	
		for(VarDeclarationNode param : rule.getParameters()){
			param.accept(this);
		}
		
		for(VarDeclarationNode param : rule.getReturns()){ 
			param.accept(this);
		}
	}

	public void visit(TypeNode type) {
		type.getName();
	}

	@Override
	public void visit(VarDeclarationNode var) {
		var.getName();
		var.getType().accept(this);
		
	}

	@Override
	public void visit(BooleanTypeNode type) {
		// TODO Auto-generated method stub
	}


	@Override
	public void visit(FloatTypeNode type) {
		// TODO Auto-generated method stub
		 
		
	}


	@Override
	public void visit(GrammarTypeNode type) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void visit(IntTypeNode type) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void visit(RuleTypeNode type) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void visit(StringTypeNode type) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void visit(UserTypeNode type) {
		// TODO Auto-generated method stub
		
	}	

}
