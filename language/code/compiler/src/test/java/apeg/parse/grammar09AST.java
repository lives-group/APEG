package apeg.parse;

import apeg.parse.ast.*;
import apeg.parse.ast.GrammarNode.GrammarOption;
import apeg.parse.ast.RuleNode.Annotation;
import apeg.parse.ast.BinaryExprNode.Operator;

import java.util.List;
import java.util.ArrayList;

public class Grammar09AST{

	public static void main(String args[]){

		List<GrammarOptions>opts = new ArrayList<GrammarOptions>();
		List<RuleNode>rules = new ArrayList<RuleNode>();
		List<PegNode>pegs = new ArrayList<PegNode>();
		List<ExprNode>attrs = new ArrayList<ExprNode>();

		PegNode peg, left, rigth;


		pegs.add(new NonterminalPegNode("a", null));
		left = new NonterminalPegNode("e", null);
		left = new NonterminalPegNode("b", null);
		rigth = new NonterminalPegNode("c", null);
		rigth = new ChoicePegNode(left, rigth);
		pegs.add(new ChoicePegNode(left, rigth));
		peg = new SequencePegNode(pegs);

		RuleNode s = new RuleNode("s", Annotation.NONE, new ArrayList<VarDeclarationNode>(), new ArrayList<VarDeclarationNode>(), peg);
		rules.add(s);

		//pegs regra a
		PegNode peg, left, rigth;

		left = new NonterminalPegNode("d", null);
		rigth = new NonterminalPegNode("c", null);
		peg = new ChoicePegNode(left, rigth);


		RuleNode a = new RuleNode("a", Annotation.NONE, new ArrayList<VarDeclarationNode>(), new ArrayList<VarDeclarationNode>(), peg);
		rules.add(a);

		//pegs regra b
		PegNode peg, left, rigth;

		left = new LiteralPegNode("abc");
		rigth = new LiteralPegNode("abb");
		peg = new ChoicePegNode(left, rigth);

		RuleNode b = new RuleNode("b", Annotation.NONE, new ArrayList<VarDeclarationNode>(), new ArrayList<VarDeclarationNode>(), peg);
		rules.add(b);

		//pegs regra c
		PegNode peg, left, rigth;

		left = new LiteralPegNode("t");
		rigth = new NonterminalPegNode("d", null);
		peg = new ChoicePegNode(left, rigth);

		RuleNode c = new RuleNode("c", Annotation.NONE, new ArrayList<VarDeclarationNode>(), new ArrayList<VarDeclarationNode>(), peg);
		rules.add(c);

		//pegs regra d
		PegNode peg = new LiteralPegNode("ab");

		RuleNode d = new RuleNode("d", Annotation.NONE, new ArrayList<VarDeclarationNode>(), new ArrayList<VarDeclarationNode>(), peg);
		rules.add(d);

		//pegs rega e

		pegs.add(new NonterminalPegNode("c", null));
		pegs.add(new NonterminalPegNode("d", null));
		PegNode peg = new SequencePegNode(pegs);

		RuleNode e = new RuleNode("e", Annotation.NONE, new ArrayList<VarDeclarationNode>(), new ArrayList<VarDeclarationNode>(), peg);
		rules.add(e);

		opts.add(null);
		GrammarNode gram = new GrammarNode("notDiscardChanges", opts, "", rules, null, null);
	}
}