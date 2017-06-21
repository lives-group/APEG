package apeg.parse.ast.visitor;

import apeg.parse.ast.AndExprNode;
import apeg.parse.ast.AndPegNode;
import apeg.parse.ast.AnyPegNode;
import apeg.parse.ast.AssignmentNode;
import apeg.parse.ast.AttributeExprNode;
import apeg.parse.ast.BinaryExprNode;
import apeg.parse.ast.BindPegNode;
import apeg.parse.ast.BooleanExprNode;
import apeg.parse.ast.CallExprNode;
import apeg.parse.ast.ChoicePegNode;
import apeg.parse.ast.ConstraintPegNode;
import apeg.parse.ast.EqualityExprNode;
import apeg.parse.ast.FloatExprNode;
import apeg.parse.ast.FunctionNode;
import apeg.parse.ast.GrammarNode;
import apeg.parse.ast.GroupPegNode;
import apeg.parse.ast.IntExprNode;
import apeg.parse.ast.LambdaPegNode;
import apeg.parse.ast.LiteralPegNode;
import apeg.parse.ast.MetaPegExprNode;
import apeg.parse.ast.MinusExprNode;
import apeg.parse.ast.NonterminalPegNode;
import apeg.parse.ast.NotExprNode;
import apeg.parse.ast.NotPegNode;
import apeg.parse.ast.OptionalPegNode;
import apeg.parse.ast.OrExprNode;
import apeg.parse.ast.PlusPegNode;
import apeg.parse.ast.RuleNode;
import apeg.parse.ast.SequencePegNode;
import apeg.parse.ast.StarPegNode;
import apeg.parse.ast.StringExprNode;
import apeg.parse.ast.TypeNode;
import apeg.parse.ast.UpdatePegNode;
import apeg.parse.ast.VarDeclarationNode;

public interface ASTNodeVisitor {
	/**
	 * Visit method for each AST element
	 */
	// Expressions
	void visit(AndExprNode expr);
	void visit(AttributeExprNode expr);
	void visit(BinaryExprNode expr);
	void visit(BooleanExprNode expr);
	void visit(CallExprNode expr);
	void visit(EqualityExprNode expr);
	void visit(FloatExprNode expr);
	void visit(IntExprNode expr);
	void visit(MetaPegExprNode expr);
	void visit(MinusExprNode expr);
	void visit(NotExprNode expr);
	void visit(OrExprNode expr);
	void visit(StringExprNode expr);
	
	// PEG Expressions
	void visit(AndPegNode peg);
	void visit(AnyPegNode peg);
	void visit(BindPegNode peg);
	void visit(ChoicePegNode peg);
	void visit(ConstraintPegNode peg);
	void visit(GroupPegNode peg);
	void visit(LambdaPegNode peg);
	void visit(LiteralPegNode peg);
	void visit(NonterminalPegNode peg);
	void visit(NotPegNode peg);
	void visit(OptionalPegNode peg);
	void visit(PlusPegNode peg);
	void visit(SequencePegNode peg);
	void visit(StarPegNode peg);
	void visit(UpdatePegNode peg);	
	
	// Others
	void visit(AssignmentNode assign);
	void visit(FunctionNode func);
	void visit(GrammarNode grammar);
	void visit(RuleNode rule);
	void visit(TypeNode type);
	void visit(VarDeclarationNode var);
}
