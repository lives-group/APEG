package apeg.ast;

import java.util.*;
import apeg.util.*;
import apeg.ast.expr.*;
import apeg.ast.rules.*;
import apeg.ast.types.*;
import apeg.ast.GrammarNode;


public class Grammar11AST {
	public static void main(String args[]){
		
		List<RulePEG>rules = new ArrayList<RulePEG>();
		List<Pair<Type, String>>inh = new ArrayList<Pair<Type, String>>();
		List<Pair<Type, Expr>>syn = new ArrayList<Pair<Type, Expr>>();
		
		//Regra s
		
		APEG peg, lpeg, rpeg;
		APEG pegs[] = new APEG[6];
		
		pegs[0] = new NonterminalPEG(new SymInfo(4, 4), "a1", null);
		lpeg = new NonterminalPEG(new SymInfo(4, 10), "a2", null);
		rpeg = new NonterminalPEG(new SymInfo(4, 17), "a4", null);
		pegs[1] = new PKlenee(new SymInfo(4, 22), new ChoicePEG(new SymInfo(4, 15), lpeg, rpeg));
		pegs[2] = new AndPEG(new SymInfo(4, 24), new NonterminalPEG(new SymInfo(4, 25), "a3", null));
		pegs[3] = new NonterminalPEG(new SymInfo(4, 30), "b3", null);
		//pegs[4] = new     'e'+
		pegs[5] = new NonterminalPEG(new SymInfo(4, 40), "b1", null);
		peg = new SeqPEG(new SymInfo(4, 4), pegs);
		
		RulePEG s = new RulePEG(new SymInfo(4, 1), "s", RulePEG.Annotation.NONE, inh, syn, peg);
		rules.add(s);
		
		//Regra a2
		APEG pegs2[] = new APEG[3];
		APEG pegs1[] = new APEG[2];
		APEG lpeg1, rpeg1, lpeg2, rpeg2;
		
		lpeg = new LitPEG(new SymInfo(6, 7), "x");
		pegs[0] = new ConstraintPEG(new SymInfo(6, 15), new StrLit(new SymInfo(6, 13), "y"));
		lpeg1 = new LitPEG(new SymInfo(6, 17), "www");
		pegs2[0] = new LitPEG(new SymInfo(6, 25), "nada");
		pegs2[1] = new LitPEG(new SymInfo(6, 32), "aver");
		lpeg2 = new NonterminalPEG(new SymInfo(6, 38), "a1", null);
		rpeg2 = new NonterminalPEG(new SymInfo(6, 45), "b0", null);
		pegs2[2] = new ChoicePEG(new SymInfo(6, 43), lpeg2, rpeg2);
		rpeg1 = new SeqPEG(new SymInfo(6, 24), pegs2);
		pegs[1] = new ChoicePEG(new SymInfo(6, 22), lpeg1, rpeg1);
		rpeg = new SeqPEG(new SymInfo(6, 12), pegs1);
		peg = new ChoicePEG(new SymInfo(6, 10), lpeg, rpeg);
		
		RulePEG a2 = new RulePEG(new SymInfo(6, 1), "a2", RulePEG.Annotation.NONE, inh, syn, peg);
		rules.add(a2);
		
		//Regra b0
		
		pegs2[0] = new LitPEG(new SymInfo(8, 7), "(");
		pegs2[1] = new NotPEG(new SymInfo(8, 10), new LitPEG(new SymInfo(8, 13), ")"));
		peg = new SeqPEG(new SymInfo(8, 6), pegs2);
		
		RulePEG b0 = new RulePEG(new SymInfo(8, 1), "b0", RulePEG.Annotation.NONE, inh, syn, peg);
		rules.add(b0);
		
		//Regra b2
		
		peg = new NotPEG(new SymInfo(9, 6), null);
		
		RulePEG b2 = new RulePEG(new SymInfo(9, 1), "b2", RulePEG.Annotation.NONE, inh, syn, peg);
		rules.add(b2);
		
		//Regra b1
		
		//peg = new 'm'+
		
		RulePEG b1 = new RulePEG(new SymInfo(11, 1), "b1", RulePEG.Annotation.NONE, inh, syn, peg);
		rules.add(b1);
		
		//Regra b3
		
		peg = new AndPEG(new SymInfo(13, 5), new LitPEG(new SymInfo(13, 7), "xyz"));
		
		RulePEG b3 = new RulePEG(new SymInfo(13, 1), "b3", RulePEG.Annotation.NONE, inh, syn, peg);
		rules.add(b3);
		
		//Regra a1
		
		lpeg = new LitPEG(new SymInfo(15, 6), "a");
		lpeg1 = new PKlenee(new SymInfo(15, 14), new LitPEG(new SymInfo(15,12), "b"));
		rpeg1 = new NotPEG(new SymInfo(15, 18), new LitPEG(new SymInfo(15, 20), "c"));
		rpeg = new ChoicePEG(new SymInfo(15, 16), lpeg1, rpeg1);
		peg = new ChoicePEG(new SymInfo(15, 9), lpeg, rpeg);
		
		RulePEG a1 = new RulePEG(new SymInfo(15, 1), "a1", RulePEG.Annotation.NONE, inh, syn, peg);
		rules.add(a1);
		
		//Regra a3
		
		peg = new NotPEG(new SymInfo(17, 5), new LitPEG(new SymInfo(17, 7), "zzz"));
		
		RulePEG a3 = new RulePEG(new SymInfo(17, 1), "a3", RulePEG.Annotation.NONE, inh, syn, peg);
		rules.add(a3);
		
		//Regra a4
		
		peg = new ConstraintPEG(new SymInfo(19, 9), new StrLit(new SymInfo(19, 6), "cd" ));
		
		RulePEG a4 = new RulePEG(new SymInfo(19, 1), "a4", RulePEG.Annotation.NONE, inh, syn, peg);
		rules.add(a4);
		
		GrammarNode gram = new GrammarNode("notDiscardChanges", null, rules);
	}

}
