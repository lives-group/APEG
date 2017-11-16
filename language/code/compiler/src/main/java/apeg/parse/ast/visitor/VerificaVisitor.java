package apeg.parse.ast.visitor;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import org.stringtemplate.v4.ST;

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
	
	
	private String currentRule;
	private int ruleCount;
	private String startRule;
	private final RuleEnvironment table;
	private Set<String> ruleNames;

	private NTType ntt;
	TypeNode tipos;
	
	
	 public VerificaVisitor(RuleEnvironment ruleEnvironment){
		 table = ruleEnvironment;
		 erros = new ArrayList<String>();
		 warnings = new ArrayList<String>();
		 ruleNames = table.getRuleNames();
		 ruleCount = 0;
		 currentRule = "";
		 startRule = "";
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
		}
		 
	     //System.out.println("Nao terminal encontrado " + peg.getName());
	     
	     // **O Não terminal utilizado nao foi definido**
	     if(!table.contains(peg.getName()) ){ 
	    	 //System.out.println("(" + peg.getLine() + ", " + peg.getColunm()+ ") : O nao terminal " +peg.getName()+ " chamado na regra "+ currentRule +" nao existe");
	     erros.add("(" +peg.getLine() + ", " + peg.getColunm()+ ") : O nao terminal " +peg.getName()+ " chamado na regra "+ currentRule +" nao existe");
	    
	     }
	     
	     // **Terminais não usados** *****************Fazer outra funcao**********************
	     if(ruleNames.contains(peg.getName())){
		    	ruleNames.remove(peg.getName());
		 }
	     
	     
	     if(table.get(peg.getName()) != null){
	         System.out.println("   Declarado : " + table.get(peg.getName()).getNumParams());
	         System.out.println("   Uso: " + peg.getExprs().size());
	     }
	     
	     //ntt.getNumParams(); 
	   
	     //ntt.getNumInherited();
	     //System.out.println(ntt.getNumInherited());
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
			currentRule = r.getName();
			r.accept(this);
		}
		currentRule = null;
	}

	@Override
	public void visit(RuleNode rule) {
		rule.getAnnotation();
		rule.getExpr().accept(this);
		rule.getName();
		
		if(ruleCount == 0){
			startRule = rule.getName();
		}
	
		for(VarDeclarationNode param : rule.getParameters()){
			param.accept(this);
		}
		
		for(VarDeclarationNode param : rule.getReturns()){ 
			param.accept(this);
		}
		
		ruleCount++;
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
	
	//Retorna um set com todas as regras nao utilizadas
	public String[] getUnusedNames(){
		return (String[])ruleNames.toArray();
	}
	
	public boolean hasErrors(){
		return !erros.isEmpty();
	}
	
	public boolean hasWarnings(){
		if(!ruleNames.isEmpty()){
			for(String i : ruleNames){
				if(!i.equals(startRule)){
				   warnings.add("Não terminal não utilizado: " + i);
				}
			}
		}
		return !warnings.isEmpty();
	}
	
	public ArrayList<String> getErros() {
		return erros;
	}

	public ArrayList<String> getWarnings() {
		return warnings;
	}

	private ArrayList<String> erros;
	private ArrayList<String> warnings;	

}
