package apeg.ast;

import java.util.*;
import apeg.ast.rules.*;
import apeg.util.*;
import apeg.ast.types.*;
import apeg.ast.expr.*;
import apeg.ast.expr.operators.*;

public class Grammar04AST {
	
	public static void main(String args[]){
		
		List<RulePEG>rules = new ArrayList<RulePEG>();
		List<Pair<Type, String>>inh = new ArrayList<Pair<Type, String>>();
		List<Expr>syn = new ArrayList<Expr>();
		List<Expr>arg = new ArrayList<Expr>();
		SymInfo s = new SymInfo(0,0);
		
		//Regra a
		
		APEG peg;
		
		s = new SymInfo(15,22);
		arg.add(new IntLit(s, 0));
		//arg.add()  int k
		s = new SymInfo(15,20);
		peg = new NonterminalPEG(s, "b", arg );
		
		inh.add(new Pair<Type, String>(new TyInt(new SymInfo(15,15)), "k"));
		
		s = new SymInfo(15,1);
		RulePEG a = new RulePEG(s, "a", RulePEG.Annotation.NONE, inh, syn, peg);
		rules.add(a);
		
		//Regra b
		
		inh.add(new Pair<Type, String>(new TyInt(new SymInfo(17,3)), "x"));
		syn.add(new Attribute(new SymInfo(14, 24), "x1"));
		
		s = new SymInfo(17,1);
		RulePEG b = new RulePEG(s, "b", RulePEG.Annotation.NONE, inh, syn, peg);
		rules.add(b);
		
		//Regra c
		
		APEG pegs[] = new APEG[4];
		
		syn.add(new Attribute(new SymInfo(26, 15), "x")); //??
		
		List<Pair<Attribute, Expr>>assigs = new ArrayList<Pair<Attribute, Expr>>();
		
		assigs.add(new Pair<Attribute, Expr>(new Attribute(new SymInfo(27,5), "x"), new IntLit(new SymInfo(27,9), '0')));
		s = new SymInfo(27,5);
		pegs[0] = new UpdatePEG(s, assigs);
		
		APEG e;
		
		assigs = new ArrayList<Pair<Attribute, Expr>>();
		Expr l, r;
		
		l = new Attribute(new SymInfo(29,11), "x");
		r = new IntLit(new SymInfo(29,15), 1);
		assigs.add(new Pair<Attribute, Expr>(new Attribute(new SymInfo(29,7), "x"), new Add(new SymInfo(29,11), l, r)));
		pegs[2] = new UpdatePEG(new SymInfo(29,7), assigs);
		pegs[3] = new LitPEG(new SymInfo(30,6), "2");
		e = new SeqPEG(new SymInfo(29,5), pegs);
		s = new SymInfo(28,3);
		pegs[1] = new PKlenee(s, e);
		
		s = new SymInfo(27,3);
		peg = new SeqPEG(s, pegs);
		
		s = new SymInfo(26,1);
		RulePEG c = new RulePEG(s, "c", RulePEG.Annotation.NONE, inh, syn, peg);
		rules.add(c);
		
		
		
		Grammar gram = new Grammar(new SymInfo(0,0), "notDiscardChanges", null, rules);
	}

}
