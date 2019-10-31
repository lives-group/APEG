/*
 && ----> LogicalBinExpr ??

*/

package apeg.parse;

import apeg.parse.*;

import java.util.List;
import java.util.ArrayList;

public class Grammar08AST{

	public static void main(String args[]){

		List<GrammarOptions>opts = new ArrayList<GrammarOptions>();
		List<RuleNode>rules = new ArrayList<RuleNode>();
		List<VarDeclarationNode>returns = new ArrayList<VarDeclarationNode>();
		List<PegNode>pegs = new ArrayList<PegNode>();
		List<ExprNode>attrs = new ArrayList<ExprNode>();
		List<AssignmentExprNode>assigs = new ArrayList<AssignmentExprNode>();

		//pegs regra g
		ExprNode left, rigth;

		attrs.add(new AttributeExprNode("na"));
		pegs.add(new NonterminalPegNode("a", attrs));
		attrs.add(new AttributeExprNode("nb"))
		pegs.add(new NonterminalPegNode("b", attrs));
		attrs.add(new AttributeExprNode("nc"));
		pegs.add(new NonterminalPegNode("c", attrs));
		left = new NonterminalPegNode("b", null);
		rigth = new NonterminalPegNode("a", null);
		assigs.add(new AttributeExprNode("b"), new LogicalBinExpr(left, rigth));
		left = new UpdatePegNode(assigs);
		left = new NonterminalPegNode("b", null);
		rigth = new NonterminalPegNode("c", null);
		rigth = new LogicalBinExpr(left, rigth);
		pegs.add(new LogicalBinExpr(left, rigth));
		PegNode peg = new SequencePegNode(pegs);

		returns.add(new VarDeclarationNode("b", boolean));
		returns.add(new VarDeclarationNode("na", int));
		returns.add(new VarDeclarationNode("nb", int));
		returns.add(new VarDeclarationNode("nc", int));

		RuleNode g = new RuleNode("g", Annotation.NONE, new ArrayList<VarDeclarationNode>(), returns, peg);
		rules.add(g);

		//pegs regra a
		PegNode peg, left, rigth;

		pegs.add(new LiteralPegNode("a"));
		attrs.add(new AttributeExprNode("na"));
		pegs.add(new NonterminalPegNode("a", attrs));
		assigs.add(new AttributeExprNode("na"), new BinaryExprNode(new AttributeExprNode("na"), new LiteralPegNode("1"), Operator.ADD));
		left = new UpdatePegNode(assigs);
		pegs.add(new LiteralPegNode("a"));
		assigs.add(new AttributeExprNode("na"), new LiteralPegNode("1"));
		pegs.add(new UpdatePegNode(assigs));
		rigth = new SequencePegNode(pegs);
		pegs.add(new ChoicePegNode(left, rigth));
		peg = new SequencePegNode(pegs);

		RuleNode a = new RuleNode("a", Annotation.NONE, new ArrayList<VarDeclarationNode>(), returns, peg);
		rules.add(a);

		//pegs regra b
		PegNode peg, left, rigth;

		pegs.add(new LiteralPegNode("b"));
		attrs.add(new AttributeExprNode("nb"));
		pegs.add(new NonterminalPegNode("b", attrs));
		assigs.add(new AttributeExprNode("nb"), new BinaryExprNode(new AttributeExprNode("nb"), new LiteralPegNode("1"), Operator.ADD));
		left = new UpdatePegNode(assigs);
		pegs.add(new LiteralPegNode("b"));
		assigs.add(new AttributeExprNode("nb"), new LiteralPegNode("1"));
		pegs.add(new UpdatePegNode(assigs));
		rigth = new SequencePegNode(pegs);
		pegs.add(new ChoicePegNode(left, rigth));
		peg = new SequencePegNode(pegs);

		RuleNode b = new RuleNode("b", Annotation.NONE, new ArrayList<VarDeclarationNode>(), returns, peg);
		rules.add(b);

		//pegs regra c

		PegNode peg, left, rigth;

		pegs.add(new LiteralPegNode("c"));
		attrs.add(new AttributeExprNode("nc"));
		pegs.add(new NonterminalPegNode("c", attrs));
		assigs.add(new AttributeExprNode("nc"), new BinaryExprNode(new AttributeExprNode("nc"), new LiteralPegNode("1"), Operator.ADD));
		left = new UpdatePegNode(assigs);
		pegs.add(new LiteralPegNode("c"));
		assigs.add(new AttributeExprNode("nc"), new LiteralPegNode("1"));
		pegs.add(new UpdatePegNode(assigs));
		rigth = new SequencePegNode(pegs);
		pegs.add(new ChoicePegNode(left, rigth));
		peg = new SequencePegNode(pegs);

		RuleNode c = new RuleNode("c", Annotation.NONE, new ArrayList<VarDeclarationNode>(), returns, peg);
		rules.add(c); 

		opts.add(NONE);
		GrammarNode gram = new GrammarNode("notDiscardChanges", opts, "", rules, null, null);

	}
}