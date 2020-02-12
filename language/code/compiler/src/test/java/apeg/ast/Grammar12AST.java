package apeg.ast;

import java.util.*;
import apeg.util.*;
import apeg.ast.expr.*;
import apeg.ast.rules.*;
import apeg.ast.types.*;



public class Grammar12AST {
	public static void main(String args[]){
		
		List<RulePEG>rules = new ArrayList<RulePEG>();
		List<Pair<Type, String>>inh = new ArrayList<Pair<Type, String>>();
		List<Expr>syn = new ArrayList<Expr>();
		
		
		//Regra s
		
		APEG peg;
		APEG pegs[] = new APEG[2];
		
		pegs[0] = new LitPEG(new SymInfo(4, 5), "01");
		pegs[1] = new NonterminalPEG(new SymInfo(4, 9), "b", null);
		peg = new SeqPEG(new SymInfo(4, 4), pegs);
		
		RulePEG s = new RulePEG(new SymInfo(4, 1), "s", RulePEG.Annotation.NONE, inh, syn, peg);
		rules.add(s);
		
		// Regra b
		
		APEG pegs1[] = new APEG[3];
		APEG pegs2[] = new APEG[3];
		
		pegs1[0] = new LitPEG(new SymInfo(6,6), "010");
		pegs2[1] = new LitPEG(new SymInfo(6, 12), "A");
		pegs2[2] = new LitPEG(new SymInfo(6, 16), "B");
		pegs1[0] = new PKlenee(new SymInfo(6, 19), new SeqPEG(new SymInfo(6, 5), pegs2));
		pegs1[1] = new LitPEG(new SymInfo(6, 22), "11");
		pegs1[2] = new NonterminalPEG(new SymInfo(6, 26), "d", null);
		peg = new SeqPEG(new SymInfo(6, 4), pegs1);
		
		RulePEG b = new RulePEG(new SymInfo(6, 1), "b", RulePEG.Annotation.NONE, inh, syn, peg);
		rules.add(b);
		
		//Regra d
		
		pegs1[0] = new LitPEG(new SymInfo(8, 5), "010");
		pegs1[1] = new LitPEG(new SymInfo(8, 11), "A");
		pegs1[2] = new LitPEG(new SymInfo(8, 15), ":-(");
		peg = new SeqPEG(new SymInfo(8, 4), pegs1);
		
		RulePEG d = new RulePEG(new SymInfo(8, 1), "d", RulePEG.Annotation.NONE, inh, syn, peg);
		rules.add(d);
		
		Grammar gram = new Grammar(new SymInfo(0,0), "kleneeTest", null, rules);
	}
	
}
