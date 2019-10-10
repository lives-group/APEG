package apeg.parse;

import apeg.parse.*;

import java.util.List;
import java.util.ArrayList;

public class TestAST {

    public static void main(String args[]) {
	List<GrammarOption> opts new ArrayList<GrammarOption>();

	List<RuleNode> rules = new ArrayList<RuleNode>();


	PegNode peg, left, right;

	List<PegNode> pegs = new ArrayList<PegNode>();
	
	left = new SequencePegNode();


	
	peg = new ChoicePegNode(left, right);
	
	RuleNode a = new RuleNode("a", Annotation.NONE, new ArrayList<VarDeclarationNode>(),
				  new ArrayList<VarDeclarationNode>(), peg);
	rules.add(a);
	
	

	GrammarNode gram = new GrammarNode("annotation", opts, "", rules, null, null);
    }


}


