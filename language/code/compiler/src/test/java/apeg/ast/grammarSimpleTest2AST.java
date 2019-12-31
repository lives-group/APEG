package apeg.ast;

import java.util.*;
import apeg.util.*;
import apeg.ast.expr.*;
import apeg.ast.rules.*;
import apeg.ast.types.*;
import apeg.ast.GrammarNode;

public class grammarSimpleTest2AST {
	public static void main (String args[]){
		
		List<RulePEG>rules = new ArrayList<RulePEG>();
		List<Pair<Type, String>>inh = new ArrayList<Pair<Type, String>>();
		List<Pair<Type, Expr>>syn = new ArrayList<Pair<Type, Expr>>();
		
		//Regra s
		
		APEG peg;
		APEG pegs[] = new APEG[2];
		
		pegs[0] = new LitPEG(new SymInfo(4, 5), "abc");
		pegs[1] = new NonterminalPEG(new SymInfo(4, 10), "a", null);
		peg = new SeqPEG(new SymInfo(4, 4), pegs);
		
		RulePEG s = new RulePEG(new SymInfo(4, 1), "s", RulePEG.Annotation.NONE, inh, syn, peg);
		rules.add(s);
		
		//Regra a
		
		APEG pegs1[] = new APEG[2];
		
		pegs1[0] = new LitPEG(new SymInfo(6, 6), "010");
		pegs1[1] = new LitPEG(new SymInfo(6, 12), "10");
		pegs[0] = new PKlenee(new SymInfo(6, 16), new SeqPEG(new SymInfo(6, 5), pegs1));
		pegs[1] = new LitPEG(new SymInfo(6, 19), "01001");
		peg = new SeqPEG(new SymInfo(6, 4), pegs);
		
		RulePEG a = new RulePEG(new SymInfo(6, 1), "a", RulePEG.Annotation.NONE, inh, syn, peg);
		rules.add(a);
		
		
		GrammarNode gram = new GrammarNode("simpleTest", null, rules);
	}
}
