package apeg.ast;

import java.util.*;
import apeg.util.*;
import apeg.ast.expr.*;
import apeg.ast.rules.*;
import apeg.ast.types.*;
import apeg.ast.Grammar.GrammarOption;
import apeg.visitor.*;


public class grammarSimpleTestAST {
	public static void main(String args[]){
		
		List<RulePEG>rules = new ArrayList<RulePEG>();
		List<Pair<Type, String>>inh = new ArrayList<Pair<Type, String>>();
		List<Expr>syn = new ArrayList<Expr>();
		List<Expr>att = new ArrayList<Expr>();
		
		//Regra s
		
		APEG peg;
		APEG pegs[] = new APEG[3];
		
		pegs[0] = new NonterminalPEG(new SymInfo(4, 4), "a1", att);
		att = new ArrayList<Expr>();
		pegs[1] = new NonterminalPEG(new SymInfo(4, 9), "a2", att);
		att = new ArrayList<Expr>();
		pegs[2] = new NonterminalPEG(new SymInfo(4, 14), "a3", att);
		peg = new SeqPEG(new SymInfo(4, 4), pegs);
		
		RulePEG s = new RulePEG(new SymInfo(4, 1), "s",RulePEG.Annotation.NONE, inh, syn, peg);
		rules.add(s);
		
		GrammarOption opts = new GrammarOption();
		
		Grammar gram = new Grammar(new SymInfo(0,0), "simpleTest", opts, rules);
		
		// TestVisitor v = new TestVisitor();
		// gram.accept(v);
	}
}
