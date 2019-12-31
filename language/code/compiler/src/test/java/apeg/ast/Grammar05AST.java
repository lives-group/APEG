package apeg.ast;

import java.util.*;
import apeg.util.*;
import apeg.ast.rules.*;
import apeg.util.Pair;
import apeg.ast.types.*;
import apeg.ast.expr.*;
import apeg.ast.expr.operators.*;

public class Grammar05AST {
	
	public void main (String args[]){
		
		List<RulePEG>rules = new ArrayList<RulePEG>();
		List<Pair<Type, String>>inh = new ArrayList<Pair<Type, String>>();
		List<Pair<Type, Expr>>syn = new ArrayList<Pair<Type, Expr>>();
		List<Pair<Attribute, Expr>>assigs = new ArrayList<Pair<Attribute, Expr>>();
	
		
		//Regra teste
		APEG peg;
		APEG pegs[] = new APEG[2];
		
		inh.add(new Pair<Type, String>(new TyInt(new SymInfo(5, 11)), "x"));
		inh.add(new Pair<Type, String>(new TyInt(new SymInfo(5, 14)), "y"));
		syn.add(new Pair<Type, Expr>(new TyInt(new SymInfo(5, 30)), new MetaVar(new SymInfo(5, 34), "z")));
		
//		assigs.add(new Pair<Attribute, Expr>(new Attribute(new SymInfo(6, 5), "z")));   add(x, y)
		
		pegs[0] = new UpdatePeg(new SymInfo(6, 5), assigs);
		List<Expr>arg = new ArrayList<Expr>();
		Expr l0, r0;
		l0 = new Attribute(new SymInfo(7, 9), "z");
		r0 = new IntLit(new SymInfo(7, 10), 2);
		arg.add(new Mult(new SymInfo(7, 9), l0, r0));
		pegs[1] = new NonterminalPEG(new SymInfo(7,3),"strn", arg);
		
		peg = new SeqPEG(new SymInfo(6, 5), pegs);
		
		RulePEG teste = new RulePEG(new SymInfo(5,1), "teste", RulePEG.Annotation.NONE, inh, syn, peg);
		rules.add(teste);
		
		//Regra strN
		inh = new ArrayList<Pair<Type, String>>();
		inh.add(new Pair<Type, String>(new TyInt(new SymInfo(10,6)), "n"));
		syn = new ArrayList<Pair<Type, Expr>>();
		
		APEG pegs0[] = new APEG[4];
		APEG pegs1[] = new APEG[4];
		APEG p;
		Expr e, l, r, le, ri;
		
		l = new Attribute(new SymInfo(10, 20), "n");
		r = new IntLit(new SymInfo(10, 24), 0);
		e = new Greater(new SymInfo(10,22), l, r);
		
		pegs1[0] = new ConstraintPEG(new SymInfo(10,17), e );
		pegs1[1] = new NonterminalPEG(new SymInfo(10, 28), "CHAR", null);
		
		assigs = new ArrayList<Pair<Attribute, Expr>>();
		le = new Attribute(new SymInfo(10, 39), "n");
		ri = new IntLit(new SymInfo(10, 43), 1);
		assigs.add(new Pair<Attribute, Expr>(new Attribute(new SymInfo(10, 35), "n"), new Sub(new SymInfo(10, 41), le, ri)));
		pegs1[2] = new UpdatePeg(new SymInfo(10, 33), assigs);
		
		p = new SeqPEG(new SymInfo(10,17), pegs1);
		pegs0[0] = new PKlenee(new SymInfo(10,49), p);
		
		l = new Attribute(new SymInfo(10, 54), "n");
		r = new IntLit(new SymInfo(10, 59), 0);
		e = new Equals(new SymInfo(10, 56), l, r);
		pegs0[1] = new ConstraintPEG(new SymInfo(10, 52), e);
		pegs0[2] = new NotPEG(new SymInfo(10, 63), new AnyPEG(new SymInfo(10, 64)));
		
		peg = new SeqPEG(new SymInfo(10, 15), pegs0);
		
		RulePEG srtN = new RulePEG(new SymInfo(10,1), "strN", RulePEG.Annotation.NONE, inh, syn, peg);
		rules.add(srtN);
		
		//regra CHAR
		inh = new ArrayList<Pair<Type, String>>();
		syn = new ArrayList<Pair<Type, Expr>>();
		
		peg = new AnyPEG(new SymInfo(12,8));
		
		RulePEG CHAR = new RulePEG(new SymInfo(12, 1), "CHAR", RulePEG.Annotation.NONE, inh, syn, peg); 
		rules.add(CHAR);
		
		
		GrammarNode gram = new GrammarNode("testfunction", null, rules );
	}
}
