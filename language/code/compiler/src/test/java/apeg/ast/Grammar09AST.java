package apeg.ast;

import java.util.*;
import apeg.util.*;
import apeg.ast.rules.*;
import apeg.ast.expr.*;
import apeg.ast.types.*;
import apeg.ast.Grammar.GrammarOption;
import apeg.visitor.TestVisitor;



public class Grammar09AST {
	public static void main(String args[]){
		
		List<RulePEG>rules = new ArrayList<RulePEG>();
		List<Pair<Type, String>>inh = new ArrayList<Pair<Type, String>>();
		List<Expr>syn = new ArrayList<Expr>();
		List<Expr>att = new ArrayList<Expr>();
		
		//Regra s
		APEG peg, lpeg, rpeg, lpeg1, rpeg1;
		APEG pegs[] = new APEG[2];
		
		pegs[0] = new NonterminalPEG(new SymInfo(5, 3), "a", att);
		lpeg = new NonterminalPEG(new SymInfo(5, 7), "e", att);
		lpeg1 = new NonterminalPEG(new SymInfo(7, 3), "b", att);
		rpeg1 = new NonterminalPEG(new SymInfo(9, 3), "c", att);
		rpeg = new ChoicePEG(new SymInfo(8, 2), lpeg1, rpeg1);
		pegs[1] = new ChoicePEG(new SymInfo(6, 2), lpeg, rpeg);
		peg = new SeqPEG(new SymInfo(5, 3), pegs);
		
		RulePEG s = new RulePEG(new SymInfo(4, 1), "s", RulePEG.Annotation.NONE, inh, syn, peg);
		rules.add(s);
		
		//Regra a
		
		APEG l, r;
		
		l = new NonterminalPEG(new SymInfo(13, 2), "d", att);
		r = new NonterminalPEG(new SymInfo(15, 2), "c", att);
		peg = new ChoicePEG(new SymInfo(14,2 ), l, r); 
		
		RulePEG a = new RulePEG(new SymInfo(12, 1), "a", RulePEG.Annotation.NONE, inh, syn, peg);
		rules.add(a);
		
		//Regra b
		
		APEG l1, r1;
		
		l1 = new LitPEG(new SymInfo(19, 3), "abc");
		r1 = new LitPEG(new SymInfo(21, 3), "abb");
		peg = new ChoicePEG(new SymInfo(20, 20), l1, r1);
		
		RulePEG b = new RulePEG(new SymInfo(18, 1), "b", RulePEG.Annotation.NONE, inh, syn, peg);
		rules.add(b);
		
		//Regra c
		
		APEG l2, r2;
		
		l2 = new LitPEG(new SymInfo(25, 3), "t");
		r2 = new NonterminalPEG(new SymInfo(27, 3), "d", att);
		peg = new ChoicePEG(new SymInfo(26, 2), l2, r2);
		
		RulePEG c = new RulePEG(new SymInfo(24, 1), "c", RulePEG.Annotation.NONE, inh, syn, peg);
		rules.add(c);
		
		//Regra d
		
		peg = new LitPEG(new SymInfo(31, 4), "ab");
		
		RulePEG d = new RulePEG(new SymInfo(30, 1), "d", RulePEG.Annotation.NONE, inh, syn, peg);
		rules.add(d);
		
		//Regra e
		APEG pegs1[] = new APEG[2];
		
		pegs1[0] = new NonterminalPEG(new SymInfo(25, 3), "c", att);
		pegs1[1] = new NonterminalPEG(new SymInfo(25, 7), "d", att);
		peg = new SeqPEG(new SymInfo(35, 3), pegs1);
		
		RulePEG e = new RulePEG(new SymInfo(34, 1), "e", RulePEG.Annotation.NONE, inh, syn, peg);
		rules.add(e);
		
		GrammarOption opts = new GrammarOption();
		Grammar gram = new Grammar(new SymInfo(0,0), "notDiscardChanges", opts, rules);
		
		//TestVisitor v = new TestVisitor();
		//gram.accept(v);
	}

}
