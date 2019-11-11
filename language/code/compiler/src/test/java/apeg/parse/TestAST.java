//Erro ao add GrammarOption
package apeg.parse;

import apeg.parse.ast.*;
import apeg.parse.ast.GrammarNode.GrammarOption;
import apeg.parse.ast.RuleNode.Annotation;

import java.util.List;
import java.util.ArrayList;

public class TestAST{
	
	public static void main(String args[]){

		List<GrammarOption>opts = new ArrayList<GrammarOption>();
		List<RuleNode>rules = new ArrayList<RuleNode>();
		List<VarDeclarationNode>param = new ArrayList<VarDeclarationNode>();
		List<VarDeclarationNode>returns = new ArrayList<VarDeclarationNode>();
		

		PegNode peg, left, rigth;
		

		List<PegNode> pegs = new ArrayList<PegNode>();
		pegs.add(new NonterminalPegNode("b", null));
		pegs.add(new LiteralPegNode("1"));
		left = new SequencePegNode(pegs);

		pegs = new ArrayList<PegNode>();
		pegs.add(new NonterminalPegNode("b", null));
		pegs.add(new LiteralPegNode("2"));		
		rigth = new SequencePegNode(pegs);

		peg = new ChoicePegNode(left, rigth);
		

		RuleNode a = new RuleNode("a", Annotation.NONE, param, returns, peg);

		rules.add(a);

		peg = new LiteralPegNode("b");

		RuleNode b = new RuleNode("b", Annotation.TRANSIENT, param, returns, peg);

		rules.add(b);

		opts.add(MEMOIZE);
		GrammarNode gram = new GrammarNode("annotation", opts, "", rules, null, null);
	}
}