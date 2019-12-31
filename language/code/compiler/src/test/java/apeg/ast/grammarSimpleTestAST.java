package apeg.ast;

import java.util.*;
import apeg.util.*;
import apeg.ast.expr.*;
import apeg.ast.rules.*;
import apeg.ast.types.*;
import apeg.ast.GrammarNode;


public class grammarSimpleTestAST {
	public static void main(String args[]){
		
		List<RulePEG>rules = new ArrayList<RulePEG>();
		List<Pair<Type, String>>inh = new ArrayList<Pair<Type, String>>();
		List<Pair<Type, Expr>>syn = new ArrayList<Pair<Type, Expr>>();
		
		//Regra s
		
		APEG peg;
		APEG pegs[] = new APEG[3];
		
		pegs[0] = new NonterminalPEG(new SymInfo(4, 4), "a1", null);
		pegs[1] = new NonterminalPEG(new SymInfo(4, 9), "a2", null);
		pegs[2] = new NonterminalPEG(new SymInfo(4, 14), "a3", null);
		peg = new SeqPEG(new SymInfo(4, 4), pegs);
		
		RulePEG s = new RulePEG(new SymInfo(4, 1), "s",RulePEG.Annotation.NONE, inh, syn, peg);
		rules.add(s);
		
		GrammarNode gram = new GrammarNode("simpleTest", null, rules);
	}
}
