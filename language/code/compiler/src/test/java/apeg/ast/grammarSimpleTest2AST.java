package apeg.ast;

import java.util.*;
import apeg.util.*;
import apeg.ast.expr.*;
import apeg.ast.rules.*;
import apeg.ast.types.*;
import apeg.visitor.TestVisitor;
import apeg.ast.Grammar.GrammarOption;


public class grammarSimpleTest2AST {
	public static void main (String args[]){
		
		List<RulePEG>rules = new ArrayList<RulePEG>();
		List<Pair<Type, String>>inh = new ArrayList<Pair<Type, String>>();
		List<Expr>syn = new ArrayList<Expr>();
		List<Expr>att = new ArrayList<Expr>();
		
		//Regra s
		
		APEG peg;
		APEG pegs[] = new APEG[2];
		
		pegs[0] = new LitPEG(new SymInfo(4, 5), "abc");
		pegs[1] = new NonterminalPEG(new SymInfo(4, 10), "a", att);
		peg = new SeqPEG(new SymInfo(4, 4), pegs);
		
		RulePEG s = new RulePEG(new SymInfo(4, 1), "s", RulePEG.Annotation.NONE, inh, syn, peg);
		rules.add(s);
		
		//Regra a
		
		inh = new ArrayList<Pair<Type, String>>();
		syn = new ArrayList<Expr>();
		
		APEG pegs1[] = new APEG[2];
		APEG p[] = new APEG[2];
		
		pegs1[0] = new LitPEG(new SymInfo(6, 6), "010");
		pegs1[1] = new LitPEG(new SymInfo(6, 12), "10");
		p[0] = new KleenePEG(new SymInfo(6, 16), new SeqPEG(new SymInfo(6, 5), pegs1));
		p[1] = new LitPEG(new SymInfo(6, 19), "01001");
		peg = new SeqPEG(new SymInfo(6, 4), p);
		
		RulePEG a = new RulePEG(new SymInfo(6, 1), "a", RulePEG.Annotation.NONE, inh, syn, peg);
		rules.add(a);
		
		GrammarOption opts = new GrammarOption();
		
		Grammar gram = new Grammar(new SymInfo(0,0), "simpleTest", opts, rules);
		
		//TestVisitor v = new TestVisitor();
		//gram.accept(v);
	}
}
