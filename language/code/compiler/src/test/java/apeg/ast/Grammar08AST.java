package apeg.ast;

import java.util.*;
import apeg.util.*;
import apeg.ast.expr.*;
import apeg.ast.expr.operators.*;
import apeg.ast.rules.*;
import apeg.ast.types.*;
import apeg.ast.Grammar;


public class Grammar08AST {

	public static void main(String args[]){
		
		List<RulePEG>rules = new ArrayList<RulePEG>();
		List<Pair<Type, String>>inh = new ArrayList<Pair<Type, String>>();
		List<Expr>syn = new ArrayList<Expr>();
		List<Expr>arg = new ArrayList<Expr>();
		
		//Regra g
		APEG peg;
		APEG pegs[] = new APEG[4];
		
		arg.add(new Attribute(new SymInfo(9, 6), "na"));
		pegs[0] = new NonterminalPEG(new SymInfo(9, 4),"a",  arg);
		arg = new ArrayList<Expr>();
		arg.add(new Attribute(new SymInfo(9, 12), "nb"));
		pegs[1] = new NonterminalPEG(new SymInfo(9, 10), "b", arg);
		arg = new ArrayList<Expr>();
		arg.add(new Attribute(new SymInfo(9, 18), "nc"));
		pegs[2] = new NonterminalPEG(new SymInfo(9, 16), "c", arg);
		
		List<Pair<Attribute, Expr>>assigs = new ArrayList<Pair<Attribute, Expr>>();
		Expr l, r, l1, r1, l2, r2;
		
		l1 = new Attribute(new SymInfo(10, 10), "a");
		r1 = new Attribute(new SymInfo(10, 13), "b");
		l = new Equals(new SymInfo(10, 11), l1, r1);
		l2 = new Attribute(new SymInfo(10, 20), "b");
		r2 = new Attribute(new SymInfo(10, 23), "c");
		r = new Equals(new SymInfo(10, 21), l2, r2);
		assigs.add(new Pair<Attribute, Expr>(new Attribute(new SymInfo(10, 5), "b"), new And(new SymInfo(10, 16), l, r)));
		pegs[3] = new UpdatePEG(new SymInfo(10, 7), assigs);
		peg = new SeqPEG(new SymInfo(9, 4), pegs);
		
		syn.add( new Attribute(new SymInfo(8, 16), "b"));
		syn.add(new Attribute(new SymInfo(8, 23), "na"));
		syn.add( new Attribute(new SymInfo(8, 31), "nb"));
		syn.add(new Attribute(new SymInfo(8, 39), "nc"));

		RulePEG g = new RulePEG(new SymInfo(24, 1), "g", RulePEG.Annotation.NONE, inh, syn, peg);
		rules.add(g);
		
		//Regra a
		APEG pegs1[] = new APEG[3];
		
		pegs1[0] = new LitPEG(new SymInfo(13, 5), "a");  
		arg = new ArrayList<Expr>();
		arg.add(new Attribute(new SymInfo(13, 10), "na"));
		pegs1[1] = new NonterminalPEG(new SymInfo(13, 8),"a", arg);
		APEG lpeg, rpeg;
		assigs = new ArrayList<Pair<Attribute, Expr>>();
		l = new Attribute(new SymInfo(13, 18), "na");
		r = new IntLit(new SymInfo(13, 21), 1);
		assigs.add(new Pair<Attribute, Expr>(new Attribute(new SymInfo(13, 15), "na"), new Add(new SymInfo(13, 20), l, r)));
		lpeg = new UpdatePEG(new SymInfo(13, 17), assigs);
		APEG pegs2[] = new APEG[2];
		pegs2[0] = new LitPEG(new SymInfo(15, 5), "a");
		assigs = new ArrayList<Pair<Attribute, Expr>>();
		assigs.add(new Pair<Attribute, Expr>(new Attribute(new SymInfo(15, 9), "na"), new IntLit(new SymInfo(15, 14), 1)));
		pegs2[1] = new UpdatePEG(new SymInfo(15, 12), assigs);
		rpeg = new SeqPEG(new SymInfo(15, 4), pegs2);
		pegs1[2] = new ChoicePEG(new SymInfo(14, 5), lpeg, rpeg);
		peg = new SeqPEG(new SymInfo(13, 4), pegs1);
		
		syn = new ArrayList<Expr>();
		syn.add(new Attribute(new SymInfo(12, 15), "na"));
		
		RulePEG a = new RulePEG(new SymInfo(12, 1), "a", RulePEG.Annotation.NONE, inh, syn, peg);
		rules.add(a);
		
		//Regra b
		
		pegs1[0] = new LitPEG(new SymInfo(19, 5), "b");  
		arg = new ArrayList<Expr>();
		arg.add(new Attribute(new SymInfo(19, 10), "nb"));
		pegs1[1] = new NonterminalPEG(new SymInfo(19, 8),"b", arg);
		assigs = new ArrayList<Pair<Attribute, Expr>>();
		l = new Attribute(new SymInfo(19, 18), "nb");
		r = new IntLit(new SymInfo(19, 21), 1);
		assigs.add(new Pair<Attribute, Expr>(new Attribute(new SymInfo(19, 15), "nb"), new Add(new SymInfo(19, 20), l, r)));
		lpeg = new UpdatePEG(new SymInfo(19, 17), assigs);
		pegs2[0] = new LitPEG(new SymInfo(21, 5), "b");
		assigs = new ArrayList<Pair<Attribute, Expr>>();
		assigs.add(new Pair<Attribute, Expr>(new Attribute(new SymInfo(21, 9), "nb"), new IntLit(new SymInfo(21, 14), 1)));
		pegs2[1] = new UpdatePEG(new SymInfo(21, 12), assigs);
		rpeg = new SeqPEG(new SymInfo(21, 4), pegs2);
		pegs1[2] = new ChoicePEG(new SymInfo(20, 5), lpeg, rpeg);
		peg = new SeqPEG(new SymInfo(19, 4), pegs1);
		
		syn = new ArrayList<Expr>();
		syn.add(new Attribute(new SymInfo(18, 15), "nb"));
		
		RulePEG b = new RulePEG(new SymInfo(18, 1), "b", RulePEG.Annotation.NONE, inh, syn, peg);
		rules.add(b);
		
		//Regra c
			
		pegs1[0] = new LitPEG(new SymInfo(25, 5), "c");  
		arg = new ArrayList<Expr>();
		arg.add(new Attribute(new SymInfo(25, 10), "nc"));
		pegs1[1] = new NonterminalPEG(new SymInfo(25, 8),"c", arg);
		assigs = new ArrayList<Pair<Attribute, Expr>>();
		l = new Attribute(new SymInfo(25, 18), "nc");
		r = new IntLit(new SymInfo(25, 21), 1);
		assigs.add(new Pair<Attribute, Expr>(new Attribute(new SymInfo(25, 15), "nc"), new Add(new SymInfo(25, 20), l, r)));
		lpeg = new UpdatePEG(new SymInfo(25, 17), assigs);
		pegs2[0] = new LitPEG(new SymInfo(27, 5), "c");
		assigs = new ArrayList<Pair<Attribute, Expr>>();
		assigs.add(new Pair<Attribute, Expr>(new Attribute(new SymInfo(27, 9), "nc"), new IntLit(new SymInfo(27, 14), 1)));
		pegs2[1] = new UpdatePEG(new SymInfo(27, 12), assigs);
		rpeg = new SeqPEG(new SymInfo(27, 4), pegs2);
		pegs1[2] = new ChoicePEG(new SymInfo(26, 5), lpeg, rpeg);
		peg = new SeqPEG(new SymInfo(25, 4), pegs1);
		
		syn = new ArrayList<Expr>();
		syn.add(new Attribute(new SymInfo(24, 15), "nc"));
		RulePEG c = new RulePEG(new SymInfo(24, 1), "c", RulePEG.Annotation.NONE, inh, syn, peg);
		rules.add(c);
 		
		Grammar gram = new Grammar(new SymInfo(0,0), "notDiscardChanges", null, rules);
	}
}
