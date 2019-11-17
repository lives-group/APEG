package apeg.parse.ast.visitor;

import apeg.parse.ast.*;

public class FooVisitor implements ASTNodeVisitor {


    public void visit(AttributeExprNode expr) {
	System.out.println("\nAttributeNode - Name: " + expr.getName());
    }
    public void visit(AndExprNode expr){
    	System.out.println("\nAndExprNode - Name: " + expr.getOpName());
   
    }
    public void visit(AndPegNode peg){
    	System.out.println("\nAndPegNode - Peg: " + peg.getPeg());
    }
    public void visit(AnyPegNode peg){
    	System.out.println("\nAnyPegNode");
    }
    public void visit(AssignmentNode assign){
    	System.out.println("\nAssignmentNode - Var: " + assign.getVariable() + "\n Expression: " + assign.getExpr() );
    	
    }
    public void visit(AttributeGrammarExprNode expr){
    	System.out.println("\nAttributeGrammarExprNode - Name: " + expr.getName());
  
    }
    public void visit(BinaryExprNode expr){
    	System.out.println("\nBinaryExprNode -Left Expression: " + expr.getLeftExpr() + "\n Operator: " + expr.getOperation() + "\n Right Expression: " + expr.getRightExpr());
    }
    public void visit(BindPegNode peg){
    	System.out.println("\nBindPegNode -Variable: " + peg.getVariable() + "\n Peg: " + peg.getPeg());
    	
    }
    public void visit(BooleanExprNode expr){
    	System.out.println("\nBooleanExprNode - Value: " + expr.getValue());
    	
    }
    public void visit(BooleanTypeNode type){
    	System.out.println("\nBooleanTypeNode - Type: " + type.getName());
    	System.out.println(type.getName());
    }
    public void visit(CallExprNode expr){
    	System.out.println("\nCallExprNode - Name: " + expr.getName() + "\n Parameters " + expr.getParameters());

    }
    public void visit(ChoicePegNode peg){
    	System.out.println("\nChoicePegNode - Left Peg: " + peg.getLeftPeg() + "\n Right Peg: " + peg.getRightPeg());

    }
    public void visit(ConstraintPegNode peg){
    	System.out.println("\nConstraintPegNode - Expression: " + peg.getExpr());
    	
    }
    public void visit(EqualityExprNode expr){
    	System.out.println("\nEqualityExprNode - Equality Type: " + expr.getEqualityType() + "\n Left Expression: " + expr.getLeftExpr() + "\n Right Expression: " + expr.getRightExpr());

    }
    public void visit(ExprNode expr){
    	System.out.println("\nExprNode");
    	
    }
    public void visit(FloatExprNode expr){
    	System.out.println("\nFloatExprNode - Value: " + expr.getValue());
    	
    }
    public void visit(FloatTypeNode type){
    	System.out.println("\nFloatTypeNode");
    	
    }
    public void visit(GrammarNode grammar){
    	System.out.println("\nGrammarNode - Name: " + grammar.getName() + "\n Preamble: " + grammar.getPreamble() + "\n Functions: " + grammar.getFunctions() + "\n Functions Sources: " + grammar.getFunctionsSources() + "\n Options: " + grammar.getOptions());
    	for(RuleNode rule : grammar.getRules())
    		rule.accept(this);
    	
    	
    }
    public void visit(GrammarTypeNode type){
    	System.out.println("\nGrammarTypeNode");
    	
    }
    public void visit(GroupPegNode peg){
    	System.out.println("\nGroupPegnode - Ranges: " + peg.getRanges());
    	
    }
    public void visit(IntExprNode expr){
    	System.out.println("\nIntExprNode - Value: " + expr.getValue());
    	
    }
    public void visit(IntTypeNode type){
    	System.out.println("\nIntTypeNode");
    	
    }
    public void visit(LambdaPegNode peg){
    	System.out.println("\nLambdaPegNode");
    
    }
    public void visit(LiteralPegNode peg){
    	System.out.println("\nLiteralPegNode - Value: " + peg.getValue());
    	
    }
    public void visit(LogicalBinExpr expr){
    	System.out.println("\nLogicalBinExpr - Name: " + expr.getOpName() + "\n Left Expression: " + expr.getLeftExpr() + "\n Right Expression: " + expr.getRightExpr());
    	
    }
    public void visit(MetaPegExprNode expr){
    	System.out.println("\nMetaPegExprNode -Expression: " + expr.getExpr());
    	
    }
    public void visit(MinusExprNode expr){
    	System.out.println("\nMinusExprNode -Expression: " + expr.getExpr());
    	
    }
    public void visit(NonterminalPegNode peg){
    	System.out.println("\nNonterminalPegNode -Name: " + peg.getName() + "\n Expressions: " + peg.getExprs());
    
    }
    public void visit(NotExprNode expr){
    	System.out.println("\nNotExprNode -Expression: " + expr.getExpr());
    	
    }
    public void visit(NotPegNode peg){
    	System.out.println("\nNotPegNode -Peg: " + peg.getPeg());
    	
    }
    public void visit(OptionalPegNode peg){
    	System.out.println("\nOptionalPegNode -Peg: " + peg.getPeg());
    	
    }
    public void visit(OrExprNode expr){
    	System.out.println("\nOrExprNode -Name: " + expr.getOpName());
    	
    }
    public void visit(PegNode peg){
    	System.out.println("\n PegNode");
    }
    public void visit(PlusPegNode peg){
    	System.out.println("\nPlusPegNode: " + peg.getPeg());
    	
    }
    public void visit(RuleNode rule){
    	System.out.println("\nRuleNode -Name: " + rule.getName() + "\nAnnotation: " + rule.getAnnotation() + "\nExperssion: " + rule.getExpr() + "\nParameters: " + rule.getParameters() + "\nReturns: " + rule.getReturns());
    	rule.getExpr().accept(this);
    }
    public void visit(RuleTypeNode type){
    	System.out.println("\nRuleTypeNode");
    
    }
    public void visit(SequencePegNode peg){
    	System.out.println("\nSequencePegNode -Pegs: " + peg.getPegs());
    	
    }
    public void visit(StarPegNode peg){
    	System.out.println("\nStarPegNode -Peg: " + peg.getPeg());
    	
    }
    public void visit(StringExprNode expr){
    	System.out.println("\nStringExprNode -Value: " + expr.getValue());
    	
    }
    public void visit(StringTypeNode type){
    	System.out.println("\nStringTypeNode");
    	
    }
    public void visit(TypeNode type){
    	System.out.println("\nTypeNode - Name: " + type.getName());
    	
    }
    public void visit(UpdatePegNode peg){
    	System.out.println("\nUpdatePegNode -Assignments: " + peg.getAssignments());
    	
    }
    public void visit(VarDeclarationNode var){
    	System.out.println("\nVarDeclarationNode -Name: " + var.getName() + "\n Type: " + var.getType());
    
    }
	@Override
	public void visit(UserTypeNode type) {
		// TODO Auto-generated method stub
		
	}

}
