package apeg.ast;

import java.util.*;
import apeg.util.Pair;
import apeg.util.SymInfo;
import apeg.ast.expr.operators.*;
import apeg.ast.expr.*;
import apeg.ast.rules.*;
import apeg.ast.types.*;
// import apeg.visitor.TestVisitor;
import apeg.ast.Grammar.GrammarOption;;

public class Grammar06AST {
	
	public static void main (String args[]){
		
		List<RulePEG>rules = new ArrayList<RulePEG>();
		List<Pair<Type, String>>inh = new ArrayList<Pair<Type, String>>();
		List<Expr>syn = new ArrayList<Expr>();
		
		APEG peg;
		
		//Regra Literal3
		
		inh.add(new Pair<Type, String>(new TyGrammar(new SymInfo(9, 10)), "g"));
		
		APEG p[] = new APEG[4];
		List<Expr>att = new ArrayList<Expr>();
		
		att.add(new Attribute(new SymInfo(10, 13), "g"));
		p[0] = new NonterminalPEG(new SymInfo(10, 5), "literal", att);
		att.add(new Attribute(new SymInfo(10, 13), "g"));
		p[1] = new NonterminalPEG(new SymInfo(10, 5), "literal", att);
		att.add(new Attribute(new SymInfo(10, 13), "g"));
		p[2] = new NonterminalPEG(new SymInfo(10, 5), "literal", att);
		p[3] = new NotPEG(new SymInfo(10, 38), new AnyPEG(new SymInfo(10, 39)));
		peg = new SeqPEG(new SymInfo(10, 5), p);
		
		RulePEG literal3 = new RulePEG(new SymInfo(9, 1), "literal3", RulePEG.Annotation.NONE, inh, syn,  peg);
		rules.add(literal3);
		
		// Rega literal
		
		syn = new ArrayList<Expr>();
		inh = new ArrayList<Pair<Type, String>>();
		
		APEG p1[] = new APEG[3];
		
		inh.add(new Pair<Type, String>(new TyGrammar(new SymInfo(12, 9)), "g"));
		syn.add(new Attribute(new SymInfo(12, 31), "n"));
		syn.add(new Attribute(new SymInfo(12, 34), "g1"));
		
		p1[0] = new LitPEG(new SymInfo(15, 3), "[");
		att = new ArrayList<Expr>();
		att.add(new Attribute(new SymInfo(16, 7), "g1"));
		p1[1] = new NonterminalPEG(new SymInfo(16, 2), "strN", att);
		p1[2] = new LitPEG(new SymInfo(17, 3), "]");
		peg = new SeqPEG(new SymInfo(15, 2), p1);
		
		RulePEG literal = new RulePEG(new SymInfo(12, 1), "literal", RulePEG.Annotation.NONE, inh, syn, peg);
		rules.add(literal);
		
		//Regra strN
		
		syn = new ArrayList<Expr>();
		inh = new ArrayList<Pair<Type, String>>();
		
		inh.add(new Pair<Type, String>(new TyGrammar(new SymInfo(20, 6)), "g"));
		
		peg = new ConstraintPEG(new SymInfo(21, 6), new BoolLit(new SymInfo(21, 8), false));
		
		RulePEG strN = new RulePEG(new SymInfo(20, 1), "strN", RulePEG.Annotation.NONE, inh, syn, peg);
		rules.add(strN);
		
		//Regra CHAR
		
		inh = new ArrayList<Pair<Type, String>>();
		syn = new ArrayList<Expr>();
		
		peg = new AnyPEG(new SymInfo(24, 8));
		
		RulePEG CHAR = new RulePEG(new SymInfo(24, 1), "CHAR", RulePEG.Annotation.NONE, inh, syn, peg);
		rules.add(CHAR);
		
		//Regra number
		
		List<Pair<Attribute, Expr>>assigs = new ArrayList<Pair<Attribute, Expr>>();
		inh = new ArrayList<Pair<Type, String>>();
		syn = new ArrayList<Expr>();
		
		APEG p2[] = new APEG[2];
		APEG p3[] = new APEG[2];
		APEG pe;
		
		Expr l, r, l1, r1;
		
		att = new ArrayList<Expr>();
		att.add(new Attribute(new SymInfo(27, 11), "r"));
		p2[0] = new NonterminalPEG(new SymInfo(27, 5), "digit", att);
		att.add(new Attribute(new SymInfo(27, 22), "aux"));
		p3[0] = new NonterminalPEG(new SymInfo(27, 16), "digit", att);
		l = new Attribute(new SymInfo(27, 33), "r");
		l1 = new IntLit(new SymInfo(27, 37), 10);
		r1 = new Attribute(new SymInfo(27, 41), "aux");
		r = new Add(new SymInfo(27, 40), l1, r1);
		assigs.add(new Pair<Attribute, Expr>(new Attribute(new SymInfo(27, 29), "r"), new Mult(new SymInfo(27, 35), l, r)));
		p3[1] = new UpdatePEG(new SymInfo(27, 31), assigs);
		pe = new SeqPEG(new SymInfo(27, 16), p3);
		p2[1] = new KleenePEG(new SymInfo(27, 50),pe);
		peg = new SeqPEG(new SymInfo(27, 5), p2);
		
		RulePEG number = new RulePEG(new SymInfo(26, 1), "number", RulePEG.Annotation.NONE, inh, syn, peg);
		rules.add(number);
		
		//regra  digit
		
		inh = new ArrayList<Pair<Type, String>>();
		syn = new ArrayList<Expr>();
		assigs = new ArrayList<Pair<Attribute, Expr>>();
		
		syn.add(new Attribute(new SymInfo(30, 20), "x1"));
		
		APEG p4[] = new APEG[2];
		APEG p5[] = new APEG[2];
		APEG p6[] = new APEG[2];
		APEG p7[] = new APEG[2];
		APEG p8[] = new APEG[2];
		APEG p9[] = new APEG[2];
		APEG p10[] = new APEG[2];
		APEG p11[] = new APEG[2];
		APEG p12[] = new APEG[2];
		APEG p13[] = new APEG[2];
		APEG l2, r2, left, right, left1, right1, l3, r3, l4, r4, l5, r5, l6, r6, l7, r7, l8, r8;
		
		p4[0] = new LitPEG(new SymInfo(31, 4), "0");
		assigs.add(new Pair<Attribute, Expr>(new Attribute(new SymInfo(31, 10), "x1"), new IntLit(new SymInfo(31, 15), 0)));
		l2 = new UpdatePEG(new SymInfo(31, 13), assigs);
		p5[0] = new LitPEG(new SymInfo(32, 6), "1");
		assigs = new ArrayList<Pair<Attribute, Expr>>();
		assigs.add(new Pair<Attribute, Expr>(new Attribute(new SymInfo(32, 12), "x1"), new IntLit(new SymInfo(32, 17), 1)));
		left = new UpdatePEG(new SymInfo(32, 15), assigs);
		p6[0] = new LitPEG(new SymInfo(33, 6), "2");
		assigs = new ArrayList<Pair<Attribute, Expr>>();
		assigs.add(new Pair<Attribute, Expr>(new Attribute(new SymInfo(33, 12), "x1"), new IntLit(new SymInfo(33, 17), 2)));
		left1 = new UpdatePEG(new SymInfo(33, 15), assigs);
		p7[0] = new LitPEG(new SymInfo(34, 6), "3");
		assigs = new ArrayList<Pair<Attribute, Expr>>();
		assigs.add(new Pair<Attribute, Expr>(new Attribute(new SymInfo(34, 12), "x1"), new IntLit(new SymInfo(34, 17), 3)));
		l3 = new UpdatePEG(new SymInfo(34, 15), assigs);
		p8[0] = new LitPEG(new SymInfo(35, 6), "4");
		assigs = new ArrayList<Pair<Attribute, Expr>>();
		assigs.add(new Pair<Attribute, Expr>(new Attribute(new SymInfo(35, 12), "x1"), new IntLit(new SymInfo(35, 17), 4)));
		l4 = new UpdatePEG(new SymInfo(35, 15), assigs);
		p9[0] = new LitPEG(new SymInfo(36,6), "5");
		assigs = new ArrayList<Pair<Attribute, Expr>>();
		assigs.add(new Pair<Attribute, Expr>(new Attribute(new SymInfo(36, 12), "x1"), new IntLit(new SymInfo(36, 17), 5)));
		l5 = new UpdatePEG(new SymInfo(36, 15), assigs);
		p10[0] = new LitPEG(new SymInfo(37, 6), "6");
		assigs = new ArrayList<Pair<Attribute, Expr>>();
		assigs.add(new Pair<Attribute, Expr>(new Attribute(new SymInfo(37, 12), "x1"), new IntLit(new SymInfo(37, 17), 6)));
		l6 = new UpdatePEG(new SymInfo(37, 15), assigs);
		p11[0] = new LitPEG(new SymInfo(38, 6), "7");
		assigs = new ArrayList<Pair<Attribute, Expr>>();
		assigs.add(new Pair<Attribute, Expr>(new Attribute(new SymInfo(38, 12), "x1"), new IntLit(new SymInfo(38, 17), 7)));
		l7 = new UpdatePEG(new SymInfo(38, 15), assigs);
		assigs = new ArrayList<Pair<Attribute, Expr>>();
		assigs.add(new Pair<Attribute, Expr>(new Attribute(new SymInfo(39, 12), "x1"), new IntLit(new SymInfo(39, 17), 8)));
		p12[0] = new LitPEG(new SymInfo(39, 6), "8");
		l8 = new UpdatePEG(new SymInfo(39, 15), assigs);
		p13[0] = new LitPEG(new SymInfo(39, 6), "9");
		assigs = new ArrayList<Pair<Attribute, Expr>>();
		assigs.add(new Pair<Attribute, Expr>(new Attribute(new SymInfo(40, 12), "x1"), new IntLit(new SymInfo(40, 17), 9)));
		p13[1] = new UpdatePEG(new SymInfo(40, 15), assigs);
		r8 = new SeqPEG(new SymInfo(40, 5), p13);
		p12[1] = new ChoicePEG(new SymInfo(40, 3), l8, r8);
		r7 = new SeqPEG(new SymInfo(39, 5), p12);
		p11[1] = new ChoicePEG(new SymInfo(	39, 3), l7, r7);
		r6 = new SeqPEG(new SymInfo(38, 5), p11);
		p10[1] = new ChoicePEG(new SymInfo(38, 3), l6, r6);
		r5 = new SeqPEG(new SymInfo(37, 5), p10);
		p9[1] = new ChoicePEG(new SymInfo(37, 3), l5, r5);
		r4 = new SeqPEG(new SymInfo(36, 5), p9);
		p8[1] = new ChoicePEG(new SymInfo(36, 3), l4, r4);
		r3 = new SeqPEG(new SymInfo(35, 5), p8);
		p7[1] = new ChoicePEG(new SymInfo(35, 3), l3, r3 );
		right1 = new SeqPEG(new SymInfo(34, 5), p7);
		p6[1] = new ChoicePEG(new SymInfo(34, 3), left1, right1);
		right = new SeqPEG(new SymInfo(33, 5), p6);
		p5[1] = new ChoicePEG(new SymInfo(33, 3), left, right);
		r2 = new SeqPEG(new SymInfo(32, 5), p5);
		p4[1]= new ChoicePEG(new SymInfo(32, 3), l2, r2);
		peg = new SeqPEG(new SymInfo(31, 3), p4);
		
		RulePEG digit = new RulePEG(new SymInfo(30, 1), "digit", RulePEG.Annotation.NONE, inh, syn, peg);
		rules.add(digit);
		
		GrammarOption opts = new GrammarOption();
		
		Grammar  gram = new Grammar (new SymInfo(0, 0), "adapdatadependent", opts, rules);
		
		//TestVisitor v = new TestVisitor();
		//gram.accept(v);
		
	}

}
