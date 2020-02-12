package apeg.ast;

import java.util.*;
import apeg.util.Pair;
import apeg.util.SymInfo;
import apeg.ast.expr.operators.*;
import apeg.ast.expr.*;
import apeg.ast.rules.*;
import apeg.ast.types.*;

public class Grammar06AST {
	
	public static void main (String args[]){
		
		List<RulePEG>rules = new ArrayList<RulePEG>();
		List<Pair<Type, String>>inh = new ArrayList<Pair<Type, String>>();
		List<Expr>syn = new ArrayList<Expr>();
		
		
		
		//Regra literal3
		APEG peg;
		APEG pegs[] = new APEG[4];
		inh.add(new Pair<Type, String>(new TyGrammar(new SymInfo(9, 10)), "g"));
		
		List<Expr>arg = new ArrayList<Expr>();
		arg.add(new Attribute(new SymInfo(10, 13), "g"));
		pegs[0] = new NonterminalPEG(new SymInfo(10,5), "literal", arg);
		arg = new ArrayList<Expr>();
		arg.add(new Attribute(new SymInfo(10, 24), "g"));
		pegs[1] = new NonterminalPEG(new SymInfo(10, 16), "literal", arg);
		arg = new ArrayList<Expr>();
		arg.add(new Attribute(new SymInfo(10, 35), "g"));
		pegs[2] = new NonterminalPEG(new SymInfo(10, 27), "literal", arg);
		pegs[3] = new NotPEG(new SymInfo(10, 38), new AnyPEG(new SymInfo(10, 39)));
		peg = new SeqPEG(new SymInfo(10, 5), pegs);
		
		RulePEG literal3 = new RulePEG(new SymInfo(9,1), "literal3", RulePEG.Annotation.NONE, inh, syn, peg);
		rules.add(literal3);
		
		//Regra literal
		
		inh.add(new Pair<Type, String>(new TyGrammar(new SymInfo(12, 9)), "g"));
		syn.add(new Attribute(new SymInfo(12, 31), "n"));
		syn.add(new Attribute(new SymInfo(12, 42), "g1"));
		APEG pegs0[] = new APEG[5];
		
		arg = new ArrayList<Expr>();
		arg.add(new Attribute(new SymInfo(13, 12), "n"));
		
		pegs0[0] = new NonterminalPEG(new SymInfo(13, 5),"number", arg);
		List<Pair<Attribute, Expr>>assigs = new ArrayList<Pair<Attribute, Expr>>();
		//assigs.add(new Pair<Attribute, Expr>(new Attribute(new SymInfo(14, 5)), "g1"));
		pegs0[1] = new UpdatePEG(new SymInfo(14, 5), assigs);
		pegs0[2] = new LitPEG(new SymInfo(15, 4), "[");
		
		arg = new ArrayList<Expr>();
		arg.add(new Attribute(new SymInfo(16, 8), "g1"));
		pegs0[3] = new NonterminalPEG(new SymInfo(16, 3), "strN", arg);
		pegs0[4] = new LitPEG(new SymInfo(17, 4), "g1");
		
		
		peg = new SeqPEG(new SymInfo(13, 5), pegs0);
		
		RulePEG literal = new RulePEG(new SymInfo(12, 1), "literal", RulePEG.Annotation.NONE, inh, syn, peg);
		rules.add(literal);
		
		//Regra strN
		
		inh = new ArrayList<Pair<Type, String>>();
		inh.add(new Pair<Type, String>(new TyGrammar(new SymInfo(20, 6)), "g"));
		
		peg = new ConstraintPEG(new SymInfo(21, 7), new StrLit(new SymInfo(21, 8), "false"));
		
		RulePEG strN = new RulePEG(new SymInfo(20, 1), "strN", RulePEG.Annotation.NONE, inh, syn, peg);
		rules.add(strN);
		
		//Regra CHAR
		
		peg = new AnyPEG(new SymInfo(24, 8));
		
		RulePEG CHAR = new RulePEG(new SymInfo(24, 1), "CHAR", RulePEG.Annotation.NONE, inh, syn, peg);
		rules.add(CHAR);
		
		//Regra number
		
		inh = new ArrayList<Pair<Type, String>>();
		syn = new ArrayList<Expr>();
		inh.add(new Pair<Type, String>(new TyInt(new SymInfo(26, 16)), "r"));
		syn.add(new Attribute(new SymInfo(26, 34), "aux"));
		
		APEG pegs2[] = new APEG[2];
		APEG pegs1[] = new APEG[2];
		APEG e;
		arg = new ArrayList<Expr>();
		arg.add(new Attribute(new SymInfo(27, 11), "r"));
		pegs2[0] = new NonterminalPEG(new SymInfo(27, 5), "digit", arg);
		arg = new ArrayList<Expr>();
		arg.add(new Attribute(new SymInfo(27, 22), "aux"));
		pegs1[0] = new NonterminalPEG(new SymInfo(27, 16), "digit", arg);
		
		assigs = new ArrayList<Pair<Attribute, Expr>>();
		Expr l, r, le, ri;
		l = new Attribute(new SymInfo(27, 33), "r");
		le = new IntLit(new SymInfo(27, 37), 10);
		ri = new Attribute(new SymInfo(27, 42), "aux");
		r =  new Add(new SymInfo(27, 40), le, ri);
		assigs.add(new Pair<Attribute, Expr>(new Attribute(new SymInfo(27, 29), "r"), new Mult(new SymInfo(27, 35), l, r)));
		pegs1[1] = new UpdatePEG(new SymInfo(27, 29),assigs);
		e = new SeqPEG(new SymInfo(27, 16), pegs1);
		pegs2[1] = new PKlenee(new SymInfo(27, 14), e );
		peg = new SeqPEG(new SymInfo(27, 5), pegs2);
		
		RulePEG number = new RulePEG(new SymInfo(26, 1), "number",RulePEG.Annotation.NONE, inh, syn, peg);
		rules.add(number);
		
		//Regra digit
		
		inh = new ArrayList<Pair<Type, String>>();
		inh.add(new Pair<Type, String>(new TyInt(new SymInfo(30, 16)), "x1"));
		
		APEG pegs3[] = new APEG[2];
		APEG pegs4[] = new APEG[2];
		APEG pegs5[] = new APEG[2];
		APEG pegs6[] = new APEG[2];
		APEG p[] = new APEG[2];
		APEG p1[] = new APEG[2];
		APEG p2[] = new APEG[2];
		APEG p3[] = new APEG[2];
		APEG p4[] = new APEG[2];
		APEG p5[] = new APEG[2];
		APEG leftPEG, rightPEG, leftPEG1, rightPEG1, leftPEG2, rightPEG2, leftP, rightP, leftP1, rightP1, lP2, rP2, lP3, rP3, lP4, rP4, lP5, rP5;
		
		pegs3[0] = new LitPEG(new SymInfo(31, 4), "0");
		assigs = new ArrayList<Pair<Attribute, Expr>>();
		assigs.add(new Pair<Attribute, Expr>(new Attribute(new SymInfo(31, 10), "x1"), new IntLit(new SymInfo(31, 15), 0)));
		leftPEG = new UpdatePEG(new SymInfo(31, 13), assigs);
		pegs4[0] = new LitPEG(new SymInfo(32,6), "1");
		assigs = new ArrayList<Pair<Attribute, Expr>>();
		assigs.add(new Pair<Attribute, Expr>(new Attribute(new SymInfo(32, 12), "x1"), new IntLit(new SymInfo(32,17), 1)));
		leftPEG1 = new UpdatePEG(new SymInfo(32, 15), assigs);
		pegs5[0] = new LitPEG(new SymInfo(33, 6), "2");
		assigs = new ArrayList<Pair<Attribute, Expr>>();
		assigs.add(new Pair<Attribute, Expr>(new Attribute(new SymInfo(33, 12), "x1"), new IntLit(new SymInfo(33, 17), 2)));
		leftPEG2 = new UpdatePEG(new SymInfo(33, 15), assigs);
		pegs6[0] = new LitPEG(new SymInfo(34, 6), "3");
		assigs = new ArrayList<Pair<Attribute, Expr>>();
		assigs.add(new Pair<Attribute, Expr>(new Attribute(new SymInfo(34, 12), "x1"), new IntLit(new SymInfo(34, 17), 3)));
		leftP = new UpdatePEG(new SymInfo(34, 15), assigs);
		p[0] = new LitPEG(new SymInfo(35, 6), "4");
		assigs = new ArrayList<Pair<Attribute, Expr>>();
		assigs.add(new Pair<Attribute, Expr>(new Attribute(new SymInfo(35, 12), "x1"), new IntLit(new SymInfo(35, 17), 4)));
		leftP1 = new UpdatePEG(new SymInfo(35, 15), assigs);
		p1[0] = new LitPEG(new SymInfo(36, 6), "5");
		assigs = new ArrayList<Pair<Attribute, Expr>>();
		assigs.add(new Pair<Attribute, Expr>(new Attribute(new SymInfo(36, 12), "x1"), new IntLit(new SymInfo(36, 17), 5)));
		lP2 = new UpdatePEG(new SymInfo(36, 12), assigs);
		p2[0] = new LitPEG(new SymInfo(37, 6), "6");
		assigs = new ArrayList<Pair<Attribute, Expr>>();
		assigs.add(new Pair<Attribute, Expr>(new Attribute(new SymInfo(37, 12), "x1"), new IntLit(new SymInfo(37,17), 6)));
		lP3 = new UpdatePEG(new SymInfo(37, 15), assigs);
		p3[0] = new LitPEG(new SymInfo(38, 6), "7");
		assigs = new ArrayList<Pair<Attribute, Expr>>();
		assigs.add(new Pair<Attribute, Expr>(new Attribute(new SymInfo(38, 12), "x1"), new IntLit(new SymInfo(38, 17), 7)));
		lP4 = new UpdatePEG(new SymInfo(38, 15), assigs);
		p4[0] = new LitPEG(new SymInfo(39, 6), "8");
		assigs = new ArrayList<Pair<Attribute, Expr>>();
		assigs.add(new Pair<Attribute, Expr>(new Attribute(new SymInfo(39, 12), "x1"), new IntLit(new SymInfo(39, 17), 8)));
		lP5 = new UpdatePEG(new SymInfo(39, 15), assigs);
		p5[0] = new LitPEG(new SymInfo(40, 6), "9");
		assigs = new ArrayList<Pair<Attribute, Expr>>();
		assigs.add(new Pair<Attribute, Expr>(new Attribute(new SymInfo(40, 12), "x1"), new IntLit(new SymInfo(40, 17), 9)));
		p5[1] = new UpdatePEG(new SymInfo(40, 15), assigs);
		rP5 = new SeqPEG(new SymInfo(40, 5), p5);
		p4[1] = new ChoicePEG(new SymInfo(40, 3), lP5, rP5);
		rP4 = new SeqPEG(new SymInfo(39, 5), p4);
		p3[1] = new ChoicePEG(new SymInfo(39, 3), lP4, rP4);
		rP3 = new SeqPEG(new SymInfo(38, 5), p3);
		p2[1] = new ChoicePEG(new SymInfo(38, 3), lP3, rP3);
		rP2 = new SeqPEG(new SymInfo(37, 5), p2);
		p1[1] = new ChoicePEG(new SymInfo(37, 3), lP2, rP2);
		rightP1 = new SeqPEG(new SymInfo(36, 5), p1);
		p[1] = new ChoicePEG(new SymInfo(36, 3), leftP1, rightP1);
		rightP = new SeqPEG(new SymInfo(35, 5), p);
		pegs[1] = new ChoicePEG(new SymInfo(35, 3), leftP, rightP);
		rightPEG2 = new SeqPEG(new SymInfo(34, 4), pegs6);
		pegs5[1] = new ChoicePEG(new SymInfo(34, 3), leftPEG2, rightPEG2);
		rightPEG1 = new SeqPEG(new SymInfo(33, 5), pegs5);
		pegs4[1] = new ChoicePEG(new SymInfo(33, 3), leftPEG1, rightPEG1);
		rightPEG = new SeqPEG(new SymInfo(32, 5), pegs4);
		pegs3[1] = new ChoicePEG(new SymInfo(32,3), leftPEG, rightPEG);
		
		peg = new SeqPEG(new SymInfo(31, 3), pegs3);
		
		RulePEG digit = new RulePEG(new SymInfo(30, 1), "digit",RulePEG.Annotation.NONE, inh, syn, peg);
		rules.add(digit);
		
		
		Grammar gram = new Grammar(new SymInfo(0,0), "adapdatadependent", null, rules);
		
	}

}
