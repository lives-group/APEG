package apeg.parse.ast.visitor;

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
import apeg.parse.ast.UpdatePegNode;
import apeg.parse.ast.UserTypeNode;
import apeg.parse.ast.VarDeclarationNode;

public class VerificaVisitor implements ASTNodeVisitor {
	

	String tipo = " ";
	String non = " ";
	
	
	
	
	 public VerificaVisitor(){
		 
		
	    	
	    }
	 
	
	public void naoterminal(String non){
		
	}
	
	@Override
	public void visit(AndExprNode expr) {
		expr.getLeftExpr().accept(this);
		expr.getRightExpr().accept(this);

	}

	@Override
	public void visit(AttributeExprNode expr) {
		expr.getName();
		
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
		peg.getVariable();
		
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
		peg.accept(this);
		
	}

	@Override
	public void visit(LiteralPegNode peg) {
		peg.getValue();
		
	}

	@Override
	public void visit(NonterminalPegNode peg) {
	
		for(ExprNode p:peg.getExprs()) {
			p.accept(this);
		}
	    
		 peg.getName();
	     non = peg.getName();	 
	     System.out.println(non); //nesse caso sei q [e b
	     naoterminal(non);
	     
	  
	    
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

	@Override
	public void visit(GrammarNode grammar) {
		grammar.getFunctions();
		grammar.getFunctionsSources(); //lista
		grammar.getName();
		grammar.getOptions(); //lista
		grammar.getPreamble();
		
		for(RuleNode rule : grammar.getRules())
			rule.accept(this);
		
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

	@Override
	public void visit(VarDeclarationNode var) {
		var.getName();
		var.getType().accept(this);
		
	}


	@Override
	public void visit(BooleanTypeNode type) {
		tipo = "boolean";		
	}


	@Override
	public void visit(FloatTypeNode type) {
		tipo = "float";		
	}


	@Override
	public void visit(GrammarTypeNode type) {
		tipo = "grammar";		
	}


	@Override
	public void visit(IntTypeNode type) {
		tipo = "int";		
	}


	@Override
	public void visit(RuleTypeNode type) {
		tipo = "rule";		
	}


	@Override
	public void visit(StringTypeNode type) {
		tipo = "string";		
	}


	@Override
	public void visit(UserTypeNode type) {
		tipo = type.getName();		
	}

}
