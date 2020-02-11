package apeg.ast;

import java.util.List;
import java.util.ArrayList;

import apeg.util.SymInfo;
import apeg.ast.GrammarNode;
import apeg.ast.GrammarNode.GrammarOption;
import apeg.ast.rules.*;


public class Grammar01AST {
	
	public static void main(String args[]){

		List<GrammarOption>opts = new ArrayList<GrammarOption>();
		List<RulePEG>rules = new ArrayList<RulePEG>();
		
		//Regra a

		APEG peg, leftPeg, rightPeg;
		APEG pegs[] = new APEG[4];
		
		SymInfo c = new SymInfo(8,3);
		pegs[0] = new NonterminalPEG(c, "b", null);

		
		c = new SymInfo(8,6);
		leftPeg = new LitPEG(c, "1");
		
		c = new SymInfo(10,2);
		rightPeg = new SeqPEG(c, pegs);
		
		c = new SymInfo(9,2);
		pegs[1] = new ChoicePEG(c, leftPeg, rightPeg );
		
		c = new SymInfo(10,3);
		pegs[2] = new NonterminalPEG(c ,"b", null);
		
		c = new SymInfo(10,6);
		pegs[3] = new LitPEG(c, "2");
		
		c = new SymInfo(8,3);
		peg = new SeqPEG(c, pegs);
		
		
		c = new SymInfo(3,3);
		RulePEG a = new RulePEG(c, "a", RulePEG.Annotation.NONE, null, null, peg);
		rules.add(a);
		
		//Regra b
		
		APEG peg1;
		c = new SymInfo(15,3);
		peg1 = new LitPEG(c, "b");
		
		c = new SymInfo(8,3);
		RulePEG b = new RulePEG(c, "b", RulePEG.Annotation.TRANSIENT, null, null, peg1 );
		rules.add(b);
	
		
		opts.add(GrammarOption.MEMOIZE);
		GrammarNode gram = new GrammarNode("Annotation", opts, rules);
	}

}