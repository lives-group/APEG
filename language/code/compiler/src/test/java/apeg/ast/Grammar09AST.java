package apeg.ast;

import java.util.*;
import apeg.util.*;
import apeg.ast.rules.*;
import apeg.ast.GrammarNode;


public class Grammar09AST {
	public static void main(String args[]){
		
		List<RulePEG>rules = new ArrayList<RulePEG>();
		
		//Regra s
		APEG peg, lpeg, rpeg, lpeg1, rpeg1;
		APEG pegs[] = new APEG[2];
		
		pegs[0] = new NonterminalPEG(new SymInfo(5, 3), "a", null);
		lpeg = new NonterminalPEG(new SymInfo(5, 7), "e", null);
		lpeg1 = new NonterminalPEG(new SymInfo(7, 3), "b", null);
		rpeg1 = new NonterminalPEG(new SymInfo(9, 3), "c", null);
		rpeg = new ChoicePEG(new SymInfo(8, 2), lpeg1, rpeg1);
		pegs[1] = new ChoicePEG(new SymInfo(6, 2), lpeg, rpeg);
		peg = new SeqPEG(new SymInfo(5, 3), pegs);
		
		RulePEG s = new RulePEG(new SymInfo(4, 1), "s", RulePEG.Annotation.NONE, null, null, peg);
		rules.add(s);
		
		//Regra a
		
		lpeg = new NonterminalPEG(new SymInfo(13, 2), "d", null);
		rpeg = new NonterminalPEG(new SymInfo(15, 2), "c", null);
		peg = new ChoicePEG(new SymInfo(14,2 ), lpeg, rpeg); 
		
		RulePEG a = new RulePEG(new SymInfo(12, 1), "a", RulePEG.Annotation.NONE, null, null, peg);
		rules.add(a);
		
		//Regra b
		
		lpeg = new LitPEG(new SymInfo(19, 3), "abc");
		rpeg = new LitPEG(new SymInfo(21, 3), "abb");
		peg = new ChoicePEG(new SymInfo(20, 20), lpeg, rpeg);
		
		RulePEG b = new RulePEG(new SymInfo(18, 1), "b", RulePEG.Annotation.NONE, null, null, peg);
		rules.add(b);
		
		//Regra c
		
		lpeg = new LitPEG(new SymInfo(25, 3), "t");
		rpeg = new NonterminalPEG(new SymInfo(27, 3), "d", null);
		peg = new ChoicePEG(new SymInfo(26, 2), lpeg, rpeg);
		
		RulePEG c = new RulePEG(new SymInfo(24, 1), "c", RulePEG.Annotation.NONE, null, null, peg);
		rules.add(c);
		
		//Regra d
		
		peg = new LitPEG(new SymInfo(31, 4), "ab");
		
		RulePEG d = new RulePEG(new SymInfo(30, 1), "d", RulePEG.Annotation.NONE, null, null, peg);
		rules.add(d);
		
		//Regra e
		APEG pegs1[] = new APEG[2];
		
		pegs1[0] = new NonterminalPEG(new SymInfo(25, 3), "c", null);
		pegs[1] = new NonterminalPEG(new SymInfo(25, 7), "d", null);
		peg = new SeqPEG(new SymInfo(35, 3), pegs1);
		
		RulePEG e = new RulePEG(new SymInfo(34, 1), "e", RulePEG.Annotation.NONE, null, null, peg);
		rules.add(e);
		
		GrammarNode gram = new GrammarNode("notDiscardChanges", null, rules);
	}

}
