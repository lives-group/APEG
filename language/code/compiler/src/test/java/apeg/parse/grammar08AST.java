/*
 && ----> LogicalBinExpr ??

*/

package apeg.parse;

import apeg.parse.ast.*;
import apeg.parse.ast.RuleNode.Annotation;
import apeg.parse.ast.GrammarNode.GrammarOption;
import apeg.parse.ast.BinaryExprNode.Operator;

import java.util.List;
import java.util.ArrayList;

public class Grammar08Ast {

	public static void main(String args[]){

		List<GrammarOptions>opts = new ArrayList<GrammarOptions>();
		List<RuleNode>rules = new ArrayList<RuleNode>();
		List<VarDeclarationNode>returns = new ArrayList<VarDeclarationNode>();
		List<PegNode>pegs = new ArrayList<PegNode>();
		List<ExprNode>attrs = new ArrayList<ExprNode>();
		List<AssignmentNode>assigs = new ArrayList<AssignmentNode>();

		//pegs regra g
		PegNode left, rigth;

		attrs.add(new AttributeExprNode("na"));
		pegs.add(new NonterminalPegNode("a", attrs));
		attrs.add(new AttributeExprNode("nb"));
		pegs.add(new NonterminalPegNode("b", attrs));
		attrs.add(new AttributeExprNode("nc"));
		pegs.add(new NonterminalPegNode("c", attrs));
		assigs.add(new AttributeExprNode("b"), new LogicalBinExpr(new NonterminalPegNode("b", null),new NonterminalPegNode("a", null)));
		left = new UpdatePegNode(assigs);
		left = new NonterminalPegNode("b", null);
		rigth = new NonterminalPegNode("c", null);
		rigth = new LogicalBinExpr(left, rigth);
		pegs.add(new LogicalBinExpr(left, rigth));
		PegNode peg = new SequencePegNode(pegs);

		returns.add(new VarDeclarationNode("b", new BooleanTypeNode()));
		returns.add(new VarDeclarationNode("na", new IntTypeNode()));
		returns.add(new VarDeclarationNode("nb", new IntTypeNode()));
		returns.add(new VarDeclarationNode("nc", new IntTypeNode()));

		RuleNode g = new RuleNode("g", Annotation.NONE, new ArrayList<VarDeclarationNode>(), returns, peg);
		rules.add(g);

		//pegs regra a
		PegNode peg, left, rigth;

		pegs.add(new LiteralPegNode("a"));
		attrs.add(new AttributeExprNode("na"));
		pegs.add(new NonterminalPegNode("a", attrs));
		assigs.add(new AttributeExprNode("na"), new BinaryExprNode(new AttributeExprNode("na"), new IntExprNode(1), Operator.ADD));
		left = new UpdatePegNode(assigs);
		pegs.add(new LiteralPegNode("a"));
		assigs.add(new AttributeExprNode("na"), new IntExprNode(1));
		pegs.add(new UpdatePegNode(assigs));
		rigth = new SequencePegNode(pegs);
		pegs.add(new ChoicePegNode(left, rigth));
		peg = new SequencePegNode(pegs);

		RuleNode a = new RuleNode("a", Annotation.NONE, new ArrayList<VarDeclarationNode>(), returns, peg);
		rules.add(a);

		//pegs regra b
		PegNode peg2, left2, rigth2;

		pegs.add(new LiteralPegNode("b"));
		attrs.add(new AttributeExprNode("nb"));
		pegs.add(new NonterminalPegNode("b", attrs));
		assigs.add(new AttributeExprNode("nb"), new BinaryExprNode(new AttributeExprNode("nb"), new IntExprNode("1"), Operator.ADD));
		left2 = new UpdatePegNode(assigs);
		pegs.add(new LiteralPegNode("b"));
		assigs.add(new AttributeExprNode("nb"), new IntExprNode("1"));
		pegs.add(new UpdatePegNode(assigs));
		rigth2 = new SequencePegNode(pegs);
		pegs.add(new ChoicePegNode(left2, rigth2));
		peg2 = new SequencePegNode(pegs);

		RuleNode b = new RuleNode("b", Annotation.NONE, new ArrayList<VarDeclarationNode>(), returns, peg2);
		rules.add(b);

		//pegs regra c

		PegNode peg1, left1, rigth1;

		pegs.add(new LiteralPegNode("c"));
		attrs.add(new AttributeExprNode("nc"));
		pegs.add(new NonterminalPegNode("c", attrs));
		assigs.add(new AttributeExprNode("nc"), new BinaryExprNode(new AttributeExprNode("nc"), new IntExprNode(1), Operator.ADD));
		left1 = new UpdatePegNode(assigs);
		pegs.add(new LiteralPegNode("c"));
		assigs.add(new AttributeExprNode("nc"), new IntExprNode(1));
		pegs.add(new UpdatePegNode(assigs));
		rigth1 = new SequencePegNode(pegs);
		pegs.add(new ChoicePegNode(left1, rigth1));
		peg1 = new SequencePegNode(pegs);

		RuleNode c = new RuleNode("c", Annotation.NONE, new ArrayList<VarDeclarationNode>(), returns, peg1);
		rules.add(c); 

		opts.add(null);
		GrammarNode gram = new GrammarNode("notDiscardChanges", opts, "", rules, null, null);

	}
}