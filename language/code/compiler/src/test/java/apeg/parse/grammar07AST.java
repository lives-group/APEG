
package apeg.parse;

import apeg.parse.ast.*;
import apeg.parse.ast.GrammarNode.GrammarOption;
import apeg.parse.ast.RuleNode.Annotation;
import apeg.parse.ast.BinaryExprNode.Operator;

import java.util.List;
import java.util.ArrayList;

public class grammar07AST{

	public static void main(String args[]){

		ASTFactory factory = new ASTFactoryImpl();

		List<GrammarOptions>opts = new ArrayList<GrammarOptions>();
		List<RuleNode>rules = new ArrayList<RuleNode>();
		List<VarDeclarationNode>param = new ArrayList<VarDeclarationNode>();
		List<VarDeclarationNode>returns = new ArrayList<VarDeclarationNode>();
		List<ExprNode>attrs = new ArrayList<ExprNode>();
		List<AssignmentNode>assigs = new ArrayList<AssignmentNode>();
		List<PegNode>pegs = new ArrayList<PegNode>();


		//pegs regra a
		attrs.add(new IntExprNode(0));
		attrs.add(new AttributeExprNode("k"));
		PegNode peg = new NonterminalPegNode("b", attrs);

		returns.add(new VarDeclarationNode("x", new IntTypeNode()));
		RuleNode a = new RuleNode("a", Annotation.NONE, new ArrayList<VarDeclarationNode>(), returns, peg);
		rules.add(a);

		//pegs regra b
		PegNode left;
		PegNode rigth;

		assigs.add(new AttributeExprNode("x"), new BinaryExprNode(new AttributeExprNode("x"), new IntExprNode(1), Operator.ADD));
		pegs.add(new UpdatePegNode(assigs));
		pegs.add(new LiteralPegNode("0"));
		assigs.add(new AttributeExprNode("x1"), new AttributeExprNode("x"));
		left = new UpdatePegNode(assigs);
		assigs.add(new AttributeExprNode("x"), new BinaryExprNode(new AttributeExprNode("x"), new IntExprNode(1), Operator.ADD));
		pegs.add(new UpdatePegNode(assigs));
		pegs.add(new LiteralPegNode("1"));
		assigs.add(new AttributeExprNode("x1"), new AttributeExprNode("x"));
		left = new UpdatePegNode(assigs);
		assigs.add(new AttributeExprNode("x"), new BinaryExprNode(new AttributeExprNode("x"), new IntExprNode(1), Operator.ADD));
		pegs.add(new UpdatePegNode(assigs));
		pegs.add(new LiteralPegNode("2"));
		assigs.add(new AttributeExprNode("x1"), new AttributeExprNode("x"));
		pegs.add(new UpdatePegNode(assigs));
		rigth = new SequencePegNode(pegs);
		pegs.add(new ChoicePegNode(left, rigth));
		rigth = new SequencePegNode(pegs);
		pegs.add(new ChoicePegNode(left, rigth));
		PegNode peg1 = new SequencePegNode(pegs);

		param.add(new VarDeclarationNode("x", new IntTypeNode()));
		returns.add(new VarDeclarationNode("x1", new BooleanTypeNode()));
		RuleNode b = new RuleNode("b", Annotation.NONE, param, returns, peg1);
		rules.add(b);


		opts.add(SIMPLE_ENV_SEMANTICS);
		GrammarNode gram = new GrammarNode("choiceback", opts, "", rules, null, null);
		GrammarNode gram = factory.newGrammar("choiceback",opts, "", rules, null, null);

	}
}
