package apeg.ast;

import java.util.*;
import apeg.util.*;
import apeg.ast.expr.*;
import apeg.ast.expr.operators.*;
import apeg.ast.rules.*;
import apeg.ast.types.*;
import apeg.ast.GrammarNode;
import apeg.ast.GrammarNode.GrammarOption;


public class Grammar07AST {
	
	public static void main(String args[]){
		
		List<RulePEG>rules = new ArrayList<RulePEG>();
		List<Pair<Type, String>>inh = new ArrayList<Pair<Type, String>>();
		List<Pair<Type, Expr>>syn = new ArrayList<Pair<Type, Expr>>();
		List<Expr>arg = new ArrayList<Expr>();
		
		//Regra a
		APEG peg;
		
		inh.add(new Pair<Type, String>(new TyInt(new SymInfo(12, 11)), "k"));
		
		arg.add(new Attribute(new SymInfo(12, 24), "k"));
		arg.add(new IntLit(new SymInfo(12, 22), 0));
		peg = new NonterminalPEG(new SymInfo(12, 20),"b", arg);
		
		RulePEG a = new RulePEG(new SymInfo(12,1), "a", RulePEG.Annotation.NONE, inh, syn, peg);
		rules.add(a);
		
		//Regra b
		
		APEG pegs[] = new APEG[3];
		APEG pegs1[] = new APEG[3];
		
		List<Pair<Attribute, Expr>>assigs = new ArrayList<Pair<Attribute, Expr>>();
		Expr l, r;
		
		l = new Attribute(new SymInfo(15, 9), "x");
		r = new IntLit(new SymInfo(15, 13), 1);
		assigs.add(new Pair<Attribute, Expr>(new Attribute(new SymInfo(15, 5), "x"), new Add(new SymInfo(15, 11), l, r) ));
		pegs[0] = new UpdatePeg(new SymInfo(15, 7), assigs);
		pegs[1] = new LitPEG(new SymInfo(15, 19), "0");
		APEG lpeg, rpeg;
		assigs = new ArrayList<Pair<Attribute, Expr>>();
		assigs.add(new Pair<Attribute, Expr>(new Attribute(new SymInfo(15, 24), "x1"), new Attribute(new SymInfo(15, 29), "x")));
		lpeg = new UpdatePeg(new SymInfo(15, 27), assigs);
		assigs = new ArrayList<Pair<Attribute, Expr>>();
		l = new Attribute(new SymInfo(17, 9), "x");
		r = new IntLit(new SymInfo(17, 13), 1);
		assigs.add(new Pair<Attribute, Expr>(new Attribute(new SymInfo(17, 5), "x"), new Add(new SymInfo(17, 11), l, r)));
		pegs1[0]= new UpdatePeg(new SymInfo(17, 7), assigs);
		pegs1[1] = new LitPEG(new SymInfo(17, 19), "1");
		APEG lpeg1, rpeg1;
		assigs = new ArrayList<Pair<Attribute, Expr>>();
		assigs.add(new Pair<Attribute, Expr>(new Attribute(new SymInfo(17, 24), "x1"), new Attribute(new SymInfo(17, 29), "x")));
		lpeg1 = new UpdatePeg(new SymInfo(17, 27), assigs);
		APEG pegs2[] = new APEG[3];
		assigs = new ArrayList<Pair<Attribute, Expr>>();
		l = new Attribute(new SymInfo(19, 9), "x");
		r = new IntLit(new SymInfo(19, 13), 1);
		assigs.add(new Pair<Attribute, Expr>(new Attribute(new SymInfo(19, 5), "x"), new Add(new SymInfo(19, 11), l, r)));
		pegs2[0] = new UpdatePeg(new SymInfo(19, 7), assigs);
		pegs2[1] = new LitPEG(new SymInfo(19, 19), "2");
		assigs = new ArrayList<Pair<Attribute, Expr>>();
		assigs.add(new Pair<Attribute, Expr>(new Attribute(new SymInfo(19, 24), "x1"), new Attribute(new SymInfo(19, 29), "x")));
		pegs2[2] = new UpdatePeg(new SymInfo(19, 27), assigs);
		rpeg1 = new SeqPEG(new SymInfo(19, 3), pegs2);
		pegs1[2] = new ChoicePEG(new SymInfo(18, 3), lpeg1, rpeg1);
		rpeg = new SeqPEG(new SymInfo(17, 3), pegs);
		pegs[2] = new ChoicePEG(new SymInfo(16, 3), lpeg, rpeg);
		peg = new SeqPEG(new SymInfo(15, 3), pegs);
		
		inh.add(new Pair<Type, String>(new TyInt(new SymInfo(14, 3)), "x"));
		syn.add(new Pair<Type, Expr>(new TyBool(new SymInfo(14, 18)), new Attribute(new SymInfo(14, 26), "x1")));
		
		RulePEG b = new RulePEG(new SymInfo(14, 1), "b", RulePEG.Annotation.NONE, inh, syn, peg);
		rules.add(b);
		
		List<GrammarOption>opts = new ArrayList<GrammarOption>();
		
		GrammarNode gram = new GrammarNode("choiceback", opts, rules);
	}

}
