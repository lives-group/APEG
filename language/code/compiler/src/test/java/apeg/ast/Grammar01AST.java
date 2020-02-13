package apeg.ast;

import java.util.List;
import java.util.ArrayList;

import apeg.util.SymInfo;
import apeg.visitor.TestVisitor;
import apeg.ast.expr.*;
import apeg.ast.rules.*;
import apeg.ast.Grammar;
import apeg.ast.Grammar.GrammarOption;


public class Grammar01AST {
	
	public static void main(String args[]){

		
		List<RulePEG>rules = new ArrayList<RulePEG>();
		List<Expr>arg = new ArrayList<Expr>();
		
		//Regra a

		APEG peg, leftPeg, rightPeg;
		APEG pegs[] = new APEG[2];
		APEG pegs1[] = new APEG[2];
		
		pegs[0] = new NonterminalPEG(new SymInfo(8,3), "b", null);
		
		leftPeg = new LitPEG(new SymInfo(8,6), "1");
		pegs1[0] = new NonterminalPEG(new SymInfo(10, 3), "b", null);
		pegs1[1] = new LitPEG(new SymInfo(	10, 6), "2");
		rightPeg = new SeqPEG(new SymInfo(10, 3), pegs1);
		
		pegs[1] = new ChoicePEG(new SymInfo(9,2), leftPeg, rightPeg);
		peg = new SeqPEG(new SymInfo(8,3), pegs);
		

		
		RulePEG a = new RulePEG(new SymInfo(3, 3), "a", RulePEG.Annotation.NONE, null, null, peg);
		rules.add(a);
		
		//Regra b
		
		APEG peg1;
		
		peg1 = new LitPEG(new SymInfo(15, 3), "b");
		
		
		RulePEG b = new RulePEG(new SymInfo(8,3), "b", RulePEG.Annotation.TRANSIENT, null, null, peg1 );
		rules.add(b);
	
		GrammarOption opts = new GrammarOption();
		opts.memoize = true;
		
		Grammar gram = new Grammar(new SymInfo (0,0), "Annotation", opts, rules);
		
		//TestVisitor v = new TestVisitor();
		//gram.accept(v);

		
	}

}