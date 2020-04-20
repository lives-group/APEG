package apeg.ast;

import java.util.*;
import apeg.util.*;
import apeg.ast.expr.*;
import apeg.ast.rules.*;
import apeg.ast.types.*;
import apeg.visitor.*;
import apeg.ast.Grammar.GrammarOption;



public class Grammar11AST {
	public static void main(String args[]){
		
		List<RulePEG>rules = new ArrayList<RulePEG>();
		List<Pair<Type, String>>inh = new ArrayList<Pair<Type, String>>();
		List<Expr>syn = new ArrayList<Expr>();
		List<Expr>att = new ArrayList<Expr>();
		
		//Rule s
		
		APEG peg, leftP, rightP;
		APEG p3[] = new APEG[6];
		
		p3[0] = new NonterminalPEG(new SymInfo(4, 4), "a4", att);
		att = new ArrayList<Expr>();
		leftP = new NonterminalPEG(new SymInfo(4, 10), "a2", att);
		att = new ArrayList<Expr>();
		rightP = new NonterminalPEG(new SymInfo(4, 17), "a4", att);
		p3[1] = new KleenePEG(new SymInfo(4, 22), new ChoicePEG(new SymInfo(4, 15), leftP, rightP));
		att = new ArrayList<Expr>();
		p3[2] = new AndPEG(new SymInfo(4, 24), new NonterminalPEG(new SymInfo(4, 25), "a3", att));
		att = new ArrayList<Expr>();
		p3[3] = new NonterminalPEG(new SymInfo(4, 30), "b3", att);
		p3[4] = new PKleene(new SymInfo(4, 38), new LitPEG(new SymInfo(4, 36), "e"));
		att = new ArrayList<Expr>();
		p3[5] = new NonterminalPEG(new SymInfo(4, 40), "b1", att);
		peg = new SeqPEG(new SymInfo(4, 4), p3);
		
		RulePEG s = new RulePEG(new SymInfo(4, 1), "s", RulePEG.Annotation.NONE, inh, syn, peg);
		rules.add(s);
		
		//Rule a2
		
		inh = new ArrayList<Pair<Type, String>>();
		syn = new ArrayList<Expr>();
		
		APEG  l, r, l1, r1, l2, r2;
		APEG p[] = new APEG[2];
		APEG p1[] = new APEG[3];
		
		l = new LitPEG(new SymInfo(6, 7),"x" );
		p[0] = new ConstraintPEG(new SymInfo(6, 15), new CharLit(new SymInfo(6, 13), 'y'));
		l1 = new LitPEG(new SymInfo(6, 17), "www");
		p1[0] = new LitPEG(new SymInfo(6, 25), "nada");
		p1[1] = new LitPEG(new SymInfo(6, 32), "aver");
		att = new ArrayList<Expr>();
		l2 = new NonterminalPEG(new SymInfo(6, 38), "a1", att);
		att = new ArrayList<Expr>();
		r2 = new NonterminalPEG(new SymInfo(6, 45), "b0", att);
		p1[2] = new ChoicePEG(new SymInfo(6, 43), l2, r2);
		r1 = new SeqPEG(new SymInfo(6, 24), p1);
		p[1] = new ChoicePEG(new SymInfo(6, 22), l1, r1);
		r = new SeqPEG(new SymInfo(6, 12), p);
	    peg = new ChoicePEG(new SymInfo(6, 10), l, r);
		
		RulePEG a2 = new RulePEG(new SymInfo(6, 1), "a2", RulePEG.Annotation.NONE, inh, syn, peg );
		rules.add(a2);
		
		//Rule b0
		
		APEG p2[] = new APEG[3];
		
		inh = new ArrayList<Pair<Type, String>>();
		syn = new ArrayList<Expr>();
		
		p2[0] = new LitPEG(new SymInfo(8, 7), "(");
		p2[1] = new AnyPEG(new SymInfo(8, 10));
		p2[2] = new LitPEG(new SymInfo(8, 13), ")");
		peg = new SeqPEG(new SymInfo(8, 6), p2);
		
		RulePEG b0 = new RulePEG(new SymInfo(8, 1), "b0", RulePEG.Annotation.NONE, inh, syn, peg);
		rules.add(b0);
		
		//Rule b2
		
		inh = new ArrayList<Pair<Type, String>>();
		syn = new ArrayList<Expr>();
		
		peg = new AnyPEG(new SymInfo(9, 6));
		
		RulePEG b2 = new RulePEG(new SymInfo(9, 1), "b2", RulePEG.Annotation.NONE, inh, syn, peg);
		rules.add(b2);
		
		//Rule b1
		
		inh = new ArrayList<Pair<Type, String>>();
		syn = new ArrayList<Expr>();
		
		peg = new PKleene(new SymInfo(11, 9), new LitPEG(new SymInfo(11, 7), "m"));
		
		RulePEG b1 = new RulePEG(new SymInfo(11, 1), "b1", RulePEG.Annotation.NONE, inh, syn, peg);
		rules.add(b1);
		
		//Rule b3
		
		inh = new ArrayList<Pair<Type, String>>();
		syn = new ArrayList<Expr>();
		
		peg = new AndPEG(new SymInfo(13, 5), new LitPEG(new SymInfo(13, 7), "xyz"));
		
		RulePEG b3 = new RulePEG(new SymInfo(13, 1), "b3", RulePEG.Annotation.NONE, inh, syn, peg);
		rules.add(b3);
		
		//Rule a1
		
		APEG l3, r3, l4, r4;
		
		inh = new ArrayList<Pair<Type, String>>();
		syn = new ArrayList<Expr>();
		
		l3 = new LitPEG(new SymInfo(15, 6), "a");
		l4 = new KleenePEG(new SymInfo(15, 14), new LitPEG(new SymInfo(15, 12), "b"));
		r4 = new NotPEG(new SymInfo(15, 18), new LitPEG(new SymInfo(15, 20), "c"));
		r3 = new ChoicePEG(new SymInfo(15, 16), l4, r4);
		peg = new ChoicePEG(new SymInfo(15, 9), l3, r3);
		
		RulePEG a1 = new RulePEG(new SymInfo(15, 1), "a1", RulePEG.Annotation.NONE, inh, syn, peg);
		rules.add(a1);
		
		//Rule a3
		
		inh = new ArrayList<Pair<Type, String>>();
		syn = new ArrayList<Expr>();
		
		peg = new NotPEG(new SymInfo(17, 5), new LitPEG(new SymInfo(17, 7), "zzz"));
		
		RulePEG a3 = new RulePEG(new SymInfo(17, 1), "a3", RulePEG.Annotation.NONE, inh, syn, peg);
		rules.add(a3);
		
		//Rule a4
		
		inh = new ArrayList<Pair<Type, String>>();
		syn = new ArrayList<Expr>();
		
		peg = new ConstraintPEG(new SymInfo(19, 9), new StrLit(new SymInfo(19, 6), "cd"));
		
		RulePEG a4 = new RulePEG(new SymInfo(19, 1), "a4", RulePEG.Annotation.NONE, inh, syn, peg);
		rules.add(a4);
		
		
		
		GrammarOption opts = new GrammarOption();
		
		Grammar gram = new Grammar(new SymInfo(0,0), "notDiscardChanges",opts,  rules);
		
		//TestVisitor v = new TestVisitor();
		//gram.accept(v);
	}

}
