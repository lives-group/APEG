package apeg.parse;

import apeg.parse.*;

import java.util.List;
import java.util.ArrayList;

public class Grammar10AST{

	public static void main(String args[]){

		List<GrammarOptions>opts = new ArrayList<GrammarOptions>();
		List<RuleNode>rules = new ArrayList<RuleNode>();
		List<PegNode>pegs = new ArrayList<PegNode>();

		pegs.add(new NonterminalPegNode("a1", null));
		pegs.add(new NonterminalPegNode("t3", null));
		pegs.add(new NonterminalPegNode("a6", null));
		pegs.add(new NonterminalPegNode("a7", null));
		PegNode peg = new SequencePegNode(pegs);

		RuleNode s = new RuleNode("s", Annotation.NONE, new ArrayList<VarDeclarationNode>(), new ArrayList<VarDeclarationNode>(), peg);
		rules.add(s);

		pegs.add(new NonterminalPegNode("a4", null));
		PegNode peg = new StarPegNode(pegs);

		RuleNode t1 = new RuleNode("t1", Annotation.NONE, new ArrayList<VarDeclarationNode>(), new ArrayList<VarDeclarationNode>(), peg);
		rules.add(t1);

		pegs.add(new NonterminalPegNode("a3", null));
		pegs.add(new NonterminalPegNode("t1", null));
		pegs.add(new NonterminalPegNode("a5", null));
		PegNode peg = new SequencePegNode(pegs);

		RuleNode t2 = new RuleNode("t2", Annotation.NONE, new ArrayList<VarDeclarationNode>(), new ArrayList<VarDeclarationNode>(), peg);
		rules.add(t2);

		PegNode peg, left, rigth;
		left = new NonterminalPegNode("a2", null);
		rigth = new NonterminalPegNode("t2", null);
		peg = new ChoicePegNode(left, rigth);

		RuleNode t3 = new RuleNode("t3", Annotation.NONE, new ArrayList<VarDeclarationNode>(), new ArrayList<VarDeclarationNode>(), peg);
		rules.add(t3);

		PegNode peg = new LiteralPegNode("a");

		RuleNode a1 = new RuleNode("a1", Annotation.NONE, new ArrayList<VarDeclarationNode>(), new ArrayList<VarDeclarationNode>(), peg);
		rules.add(a1);

		PegNode peg = new LiteralPegNode("b");

		RuleNode a2 = new RuleNode("a2", Annotation.NONE, new ArrayList<VarDeclarationNode>(), new ArrayList<VarDeclarationNode>(), peg);
		rules.add(a2);

		PegNode peg = new LiteralPegNode("c");

		RuleNode a3 = new RuleNode("a3", Annotation.NONE, new ArrayList<VarDeclarationNode>(), new ArrayList<VarDeclarationNode>(), peg);
		rules.add(a3);

		PegNode peg = new LiteralPegNode("d");

		RuleNode a4 = new RuleNode("a4", Annotation.NONE, new ArrayList<VarDeclarationNode>(), new ArrayList<VarDeclarationNode>(), peg);
		rules.add(a4);

		PegNode peg = new LiteralPegNode("e");

		RuleNode a5 = new RuleNode("a5", Annotation.NONE, new ArrayList<VarDeclarationNode>(), new ArrayList<VarDeclarationNode>(), peg);
		rules.add(a5);

		PegNode peg = new LiteralPegNode("f");

		RuleNode a6 = new RuleNode("a6", Annotation.NONE, new ArrayList<VarDeclarationNode>(), new ArrayList<VarDeclarationNode>(), peg);
		rules.add(a6);

		PegNode peg = new LiteralPegNode("g");

		RuleNode a7 = new RuleNode("a7", Annotation.NONE, new ArrayList<VarDeclarationNode>(), new ArrayList<VarDeclarationNode>(), peg);
		rules.add(a7);

		opts.add(NONE);
		GrammarNode gram = new GrammarNode("notDiscardChanges", opts, "", rules, null, null);
	}
}