package apeg.ast;

import java.util.*;
import apeg.util.*;
import apeg.ast.expr.*;
import apeg.ast.rules.*;
import apeg.ast.types.*;
import apeg.ast.GrammarNode;


public class Grammar10AST {
	public static void main(String args[]){
		
		List<RulePEG>rules = new ArrayList<RulePEG>();
		List<Pair<Type, String>>inh = new ArrayList<Pair<Type, String>>();
		List<Pair<Type, Expr>>syn = new ArrayList<Pair<Type, Expr>>();
		
		//Regra s
		
		APEG peg;
		APEG pegs[] = new APEG[4];
		
		pegs[0] = new NonterminalPEG(new SymInfo(4, 4), "a1", null);
		pegs[1] = new NonterminalPEG(new SymInfo(4, 10), "t3", null);
		pegs[2] = new NonterminalPEG(new SymInfo(4, 15), "a6", null);
		pegs[3] = new NonterminalPEG(new SymInfo(4, 20), "a7", null);
		peg = new SeqPEG(new SymInfo(4, 4), pegs);
		
		RulePEG s = new RulePEG(new SymInfo(4,1), "s", RulePEG.Annotation.NONE, inh, syn, peg);
		rules.add(s);
		
		//Regra t1
		
		peg = new PKlenee(new SymInfo(7, 11),new NonterminalPEG(new SymInfo(7, 6), "a4", null));
		
		RulePEG t1 = new RulePEG(new SymInfo(7, 1), "t1", RulePEG.Annotation.NONE, inh, syn, peg);
		rules.add(t1);
		
		//Regra t2
		
		APEG pegs1[] = new APEG[3];
		
		pegs[0] = new NonterminalPEG(new SymInfo(10, 5), "a3", null);
		pegs[1] = new NonterminalPEG(new SymInfo(10, 10), "t1", null);
		pegs[2] = new NonterminalPEG(new SymInfo(10, 15), "a5", null);
		peg = new SeqPEG(new SymInfo(10, 5), pegs1);
		
		RulePEG t2 = new RulePEG(new SymInfo(10, 1), "t2", RulePEG.Annotation.NONE, inh, syn, peg);
		rules.add(t2);
		
		//Regra t3
		
		APEG lpeg, rpeg;
		
		lpeg = new NonterminalPEG(new SymInfo(13, 5), "a2", null);
		rpeg = new NonterminalPEG(new SymInfo(15, 4), "t2", null);
		peg = new ChoicePEG(new SymInfo(14, 4), lpeg, rpeg);
		
		RulePEG t3 = new RulePEG(new SymInfo(13, 1), "t3",RulePEG.Annotation.NONE, inh, syn, peg);
		rules.add(t3);
		
		//Regra a1
		
		peg = new LitPEG(new SymInfo(18, 6), "a");
		
		RulePEG a1 = new RulePEG(new SymInfo(18, 1), "a1",RulePEG.Annotation.NONE, inh, syn, peg);
		rules.add(a1);
		
		//Regra a2
		
		peg = new LitPEG(new SymInfo(21, 6), "b");
		
		RulePEG a2 = new RulePEG(new SymInfo(21, 1), "a2", RulePEG.Annotation.NONE, inh, syn, peg);
		rules.add(a2);
		
		//Regra a3
		
		peg = new LitPEG(new SymInfo(24, 6), "c");
		
		RulePEG a3 = new RulePEG(new SymInfo(24, 1), "a3", RulePEG.Annotation.NONE, inh, syn, peg);
		rules.add(a3);
		
		//Regra a4
		
		peg = new LitPEG(new SymInfo(27, 6), "d");
		
		RulePEG a4 = new RulePEG(new SymInfo(27, 1), "a4", RulePEG.Annotation.NONE, inh, syn, peg);
		rules.add(a4);
		
		//Regra a5
		
		peg = new LitPEG(new SymInfo(30, 6), "e");
		
		RulePEG a5 = new RulePEG(new SymInfo(30, 1), "a5", RulePEG.Annotation.NONE, inh, syn, peg);
		rules.add(a5);
		
		//Regra a6
		
		peg = new LitPEG(new SymInfo(33, 6), "f");
		
		RulePEG a6 = new RulePEG(new SymInfo(33, 1), "a6", RulePEG.Annotation.NONE, inh, syn, peg);
		rules.add(a6);
		
		//regra a7
		
		peg = new LitPEG(new SymInfo(36, 6), "g");
		
		RulePEG a7 = new RulePEG(new SymInfo(36, 1), "a7", RulePEG.Annotation.NONE, inh, syn, peg);
		rules.add(a7);
		
		GrammarNode gram = new GrammarNode("notDiscardChanges", null, rules);
	}

}
