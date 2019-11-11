package apeg.parse

import apeg.parse.ast.*;
import apeg.parse.ast.GrammarNode.GrammarOption;
import apeg.parse.ast.RuleNode.Annotation;

import java.util.List;
import java.util.ArrayList;

public class Grammar12AST{

	public static void main(String args []){

		List<GrammarOptions>opts = new ArrayList<GrammarOptions>();
		List<RuleNode>rules = new ArrayList<RuleNode>();
		List<PegNode>pegs = new ArrayList<PegNode>();

		pegs.add(new LiteralPegNode("01"));
		pegs.add(new NonterminalPegNode("b", null));
		PegNode peg = new SequencePegNode(pegs);

		RuleNode s = new RuleNode("s", Annotation.NONE, new ArrayList<VarDeclarationNode>(), new ArrayList<VarDeclarationNode>(), peg);
		rules.add(s);

		pegs.add(new LiteralPegNode("010"));
		pegs.add(new LiteralPegNode("A"));
		pegs.add(new LiteralPegNode("B"));
		pegs.add(new StarPegNode(new SequencePegNode(pegs)));
		pegs.add(new LiteralPegNode("11"));
		pegs.add(new NonterminalPegNode("d", null));
		PegNode peg = new SequencePegNode(pegs);

		RuleNode b = new RuleNode("b", Annotation.NONE, new ArrayList<VarDeclarationNode>(), new ArrayList<VarDeclarationNode>(), peg);
		rules.add(b);

		pegs.add(new LiteralPegNode("010"));
		pegs.add(new LiteralPegNode("A"));
		pegs.add(new LiteralPegNode(":-("));
		PegNode peg = new SequencePegNode(pegs);

		RuleNode d = new RuleNode("d", Annotation.NONE, new ArrayList<VarDeclarationNode>(), new ArrayList<VarDeclarationNode>(), peg);
		rules.add(d);


		opts.add(null);
		GrammarNode gram = new GrammarNode("kleneeTest", opts, "", rules, null, null);
	}
}