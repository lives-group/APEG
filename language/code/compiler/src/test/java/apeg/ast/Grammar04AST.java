package apeg.ast;

import java.util.*;
import apeg.ast.rules.*;
import apeg.util.*;
import apeg.ast.types.*;
import apeg.ast.expr.*;
import apeg.ast.expr.operators.*;
import apeg.visitor.TestVisitor;
import apeg.ast.Grammar.GrammarOption;

public class Grammar04AST {
	
	public static void main(String args[]){
		
		List<RulePEG>rules = new ArrayList<RulePEG>();
		List<Pair<Type, String>>inh = new ArrayList<Pair<Type, String>>();
		List<Pair<Attribute, Expr>>assigs = new ArrayList<Pair<Attribute, Expr>>();
		List<Expr>syn = new ArrayList<Expr>();
		List<Expr>arg = new ArrayList<Expr>();

		
		//Regra a
		
		APEG peg;
		
	
		arg.add(new IntLit(new SymInfo(15, 22), 0));
		arg.add(new Attribute(new SymInfo(15, 24), "k"));
		
		peg = new NonterminalPEG(new SymInfo(15, 20), "b", arg );
		
		syn.add(new Attribute(new SymInfo(15, 15), "k"));
		
		
		RulePEG a = new RulePEG(new SymInfo(15, 1), "a", RulePEG.Annotation.NONE, inh, syn, peg);
		rules.add(a);
		
		//Regra b
		inh = new ArrayList<Pair<Type, String>>();
		syn = new ArrayList<Expr>();
		assigs = new ArrayList<Pair<Attribute, Expr>>();
		
		inh.add(new Pair<Type, String>(new TyInt(new SymInfo(17, 3)), "x"));
		syn.add(new Attribute(new SymInfo(17, 24), "x1"));
		
		APEG p[] = new APEG[3];
		APEG p2[] = new APEG[3];
		APEG p3[] = new APEG[3];
		APEG lp, rp, leftPeg, rightPeg;
		Expr l, r, l1, l2, r1, r2;
		
		l = new Attribute(new SymInfo(18, 9), "x");
		r = new IntLit(new SymInfo(18, 13), 1);
		assigs.add(new Pair<Attribute, Expr>(new Attribute(new SymInfo(18, 5), "x"), new Add(new SymInfo(18, 11), l ,r)));
		p[0] = new UpdatePEG(new SymInfo(18, 7), assigs);
		p[1] =new LitPEG(new SymInfo(18, 19), "0");
		assigs = new ArrayList<Pair<Attribute, Expr>>();
		assigs.add(new Pair<Attribute, Expr>(new Attribute(new SymInfo(18, 24), "x1"), new Attribute(new SymInfo(18, 29), "x")));
		lp = new UpdatePEG(new SymInfo(18, 27), assigs);
		l1 = new Attribute(new SymInfo(20, 9), "x");
		r1 = new IntLit(new SymInfo(20, 13), 1);
		assigs = new ArrayList<Pair<Attribute, Expr>>();
		assigs.add(new Pair<Attribute, Expr>(new Attribute(new SymInfo(20, 5), "x"), new Add(new SymInfo(20, 11), l1, r1)));
		p2[0] = new UpdatePEG(new SymInfo(20, 7), assigs);
		p2[1] = new LitPEG(new SymInfo(20, 19), "1");
		assigs = new ArrayList<Pair<Attribute, Expr>>();
		assigs.add(new Pair<Attribute, Expr>(new Attribute(new SymInfo(20, 24), "x1"), new Attribute(new SymInfo(20, 29), "x")));
		leftPeg = new UpdatePEG(new SymInfo(20, 27), assigs);
		l2= new Attribute(new SymInfo(22, 9), "x" );
	    r2 = new IntLit(new SymInfo(22, 13), 1);
	    assigs = new ArrayList<Pair<Attribute, Expr>>();
		assigs.add(new Pair<Attribute, Expr>(new Attribute(new SymInfo(22, 5), "x"), new Add(new SymInfo(22, 11), l2 ,r2)));
		p3[0] = new UpdatePEG(new SymInfo(22, 7), assigs);
		p3[1] = new LitPEG(new SymInfo(22, 19), "2");
		assigs = new ArrayList<Pair<Attribute, Expr>>();
		assigs.add(new Pair<Attribute, Expr>(new Attribute(new SymInfo(22, 24), "x1"), new Attribute(new SymInfo(22, 29), "x")));
		p3[2] = new UpdatePEG(new SymInfo(22, 27), assigs);
		rightPeg = new SeqPEG(new SymInfo(22, 5), p3);
		p2[2] = new ChoicePEG(new SymInfo(21, 3), leftPeg, rightPeg);
		rp = new SeqPEG(new SymInfo(20, 3), p2);
		p[2] = new ChoicePEG(new SymInfo(19, 3), lp, rp );
		
		peg = new SeqPEG(new SymInfo(18, 3), p);
		
		RulePEG b = new RulePEG(new SymInfo(17, 1), "b", RulePEG.Annotation.NONE, inh, syn, peg);
		rules.add(b);
		
		//Regra c
		
		syn = new ArrayList<Expr>();
		inh = new ArrayList<Pair<Type, String>>();
		assigs = new ArrayList<Pair<Attribute, Expr>>();
		
		syn.add(new Attribute(new SymInfo(26, 15), "x"));
		
		APEG p4[] = new APEG[2];
		APEG p5[] = new APEG[2];
		APEG pe;
		
		assigs.add(new Pair<Attribute, Expr>(new Attribute(new SymInfo(27, 5), "x"), new IntLit(new SymInfo(27, 9), 0)));
		p4[0] = new UpdatePEG(new SymInfo(27, 7), assigs);
		l = new Attribute(new SymInfo(29, 11), "x");
		r = new IntLit(new SymInfo(29, 15), 1);
		assigs = new ArrayList<Pair<Attribute, Expr>>();
		assigs.add(new Pair<Attribute, Expr>(new Attribute(new SymInfo(29, 7), "x"), new Add(new SymInfo(29, 13), l, r)));
		p5[0] = new UpdatePEG(new SymInfo(29, 9),assigs);
		p5[1] = new LitPEG(new SymInfo(30, 6), "2");
		pe = new SeqPEG(new SymInfo(29, 5), p5);
		p4[1] = new KleenePEG(new SymInfo(31, 5), pe);
		peg = new SeqPEG(new SymInfo(27, 3), p4);
		

		RulePEG c = new RulePEG(new SymInfo(26, 1), "c", RulePEG.Annotation.NONE, inh, syn, peg);
		rules.add(c);
		
		GrammarOption opts = new GrammarOption();
		
		Grammar gram = new Grammar(new SymInfo(0,0), "notDiscardChanges", opts, rules);
		
		TestVisitor v = new TestVisitor();
		gram.accept(v);
		
	}

}
