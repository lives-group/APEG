package apeg.parse;

import apeg.parse.ast.*;
import apeg.parse.ast.GrammarNode.GrammarOption;
import apeg.parse.ast.RuleNode.Annotation;

import java.util.List;
import java.util.ArrayList;

public class Grammar11AST{

	public static void main (String args[]){

		List<GrammarOption>opts = new ArrayList<GrammarOption>();
		List<RuleNode>rules = new ArrayList<RuleNode>();
		List<PegNode>pegs = new ArrayList<PegNode>();

		PegNode peg, left, rigth;

		pegs.add(new NonterminalPegNode("a1", null));
		left = pegs.add(new StarPegNode(new ChoicePegNode(new NonterminalPegNode("a2", null),new NonterminalPegNode("a4", null))));
		rigth = new NonterminalPegNode("a3", null);
		pegs.add(new LogicalBinExprNode(left, rigth));
		pegs.add(new PlusPegNode(new LiteralPegNode("e")));
		pegs.add(new NonterminalPegNode("b3", null));
		peg = new SequencePegNode(pegs);

		RuleNode s = new RuleNode("s", Annotation.NONE, new ArrayList<VarDeclarationNode>(), new ArrayList<VarDeclarationNode>(), peg);
		rules.add(s);

		PegNode peg, left, rigth;

		left = new LiteralPegNode("x");
		pegs.add(new LiteralPegNode("y"));
		pegs.add(new OptionalPegNode(new LiteralPegNode("www")));
		left = new SequencePegNode(pegs);
		pegs.add(new LiteralPegNode("nada"));
		pegs.add(new LiteralPegNode("aver"));
		left = new NonterminalPegNode("a1", null);
		rigth = new NonterminalPegNode("b0", null);
		pegs.add(new ChoicePegNode(left, rigth));
		rigth = new SequencePegNode(pegs);
		rigth = new ChoicePegNode(left, rigth);
		peg = new ChoicePegNode(left, rigth);

		RuleNode a2 = new RuleNode("a2", Annotation.NONE, new ArrayList<VarDeclarationNode>(), new ArrayList<VarDeclarationNode>(), peg);
		rules.add(a2);

		pegs.add(new LiteralPegNode("("));
		pegs.add(new AnyPegnode());
		pegs.add(new LiteralPegNode(")"));
		PegNode peg = new SequencePegNode(pegs);

		RuleNode b0 = new RuleNode("b0", Annotation.NONE, new ArrayList<VarDeclarationNode>(), new ArrayList<VarDeclarationNode>(), peg);
		rules.add(b0);

		PegNode peg = new AnyPegnode();

		RuleNode b2 = new RuleNode("b2", Annotation.NONE, new ArrayList<VarDeclarationNode>(), new ArrayList<VarDeclarationNode>(), peg);
		rules.add(b2);

		PegNode peg = new PlusPegNode(new LiteralPegNode("m"));

		RuleNode b1 = new RuleNode("b1", Annotation.NONE, new ArrayList<VarDeclarationNode>(), new ArrayList<VarDeclarationNode>(), peg);
		rules.add(b1);

		PegNode peg = new AndPegNode(new LiteralPegNode("xyz"));

		RuleNode b3 = new RuleNode("b3", Annotation.NONE, new ArrayList<VarDeclarationNode>(), new ArrayList<VarDeclarationNode>(), peg);
		rules.add(b3);

		PegNode peg, left, rigth;

		left = new LiteralPegNode("a");
		left = new StarPegNode(new LiteralPegNode("b"));
		rigth = new NotPegNode(new LiteralPegNode("c"));
		rigth = new ChoicePegNode(left, rigth);
		peg = new ChoicePegNode(left, rigth);

		RuleNode a1 = new RuleNode("a1", Annotation.NONE, new ArrayList<VarDeclarationNode>(), new ArrayList<VarDeclarationNode>(), peg);
		rules.add(a1);

		PegNode peg = new NotPegNode(new LiteralPegNode("zzz"));

		RuleNode a3 = new RuleNode("a3", Annotation.NONE, new ArrayList<VarDeclarationNode>(), new ArrayList<VarDeclarationNode>(), peg);
		rules.add(a3);

		PegNode peg = new OptionalPegNode(new LiteralPegNode("cd"));

		RuleNode a4 = new RuleNode("a4", Annotation.NONE, new ArrayList<VarDeclarationNode>(), new ArrayList<VarDeclarationNode>(), peg);
		rules.add(a4);


		opts.add(null);
		GrammarNode gram = new GrammarNode("notDiscardChanges", opts, "", rules, null, null);
	}
}