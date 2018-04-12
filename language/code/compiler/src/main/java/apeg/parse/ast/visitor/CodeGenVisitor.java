package apeg.parse.ast.visitor;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

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
import apeg.util.path.Path;

public class CodeGenVisitor implements ASTNodeVisitor {
	
	private STGroup groupTemplate;
	private ST template;
	
	public CodeGenVisitor(Path filePath) {
		groupTemplate = new STGroupFile(filePath.getAbsolutePath());
	}

	@Override
	public void visit(AndExprNode expr) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(AttributeExprNode expr) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(BinaryExprNode expr) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(BooleanExprNode expr) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(CallExprNode expr) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(EqualityExprNode expr) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(FloatExprNode expr) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(IntExprNode expr) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MetaPegExprNode expr) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(MinusExprNode expr) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(NotExprNode expr) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(OrExprNode expr) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(StringExprNode expr) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(AndPegNode peg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(AnyPegNode peg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(BindPegNode peg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ChoicePegNode peg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ConstraintPegNode peg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(GroupPegNode peg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(LambdaPegNode peg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(LiteralPegNode peg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(NonterminalPegNode peg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(NotPegNode peg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(OptionalPegNode peg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(PlusPegNode peg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(SequencePegNode peg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(StarPegNode peg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(UpdatePegNode peg) {
		// TODO Auto-generated method stub
		
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

	@Override
	public void visit(AssignmentNode assign) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(GrammarNode grammar) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(RuleNode rule) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(VarDeclarationNode var) {
		// TODO Auto-generated method stub
		
	}

}
