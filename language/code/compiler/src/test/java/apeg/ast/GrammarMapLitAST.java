package apeg.ast;

import java.util.List;
import java.util.ArrayList;

import apeg.util.SymInfo;
import apeg.visitor.TestVisitor;
import apeg.visitor.VMVisitor;
import apeg.ast.expr.*;
import apeg.ast.rules.*;
import apeg.ast.types.*;
import apeg.ast.Grammar;
import apeg.ast.Grammar.GrammarOption;
import apeg.util.*;


public class Grammar01AST {

	public static void main(String args[]){


		List<RulePEG>rules = new ArrayList<RulePEG>();
		List<Expr>arg = new ArrayList<Expr>();
		List<Pair<Type, String>>inh = new ArrayList<Pair<Type, String>>();
		List<Expr>syn = new ArrayList<Expr>();

		//Regra a
        // A -> B(1/B2)
		//B -> b
		APEG peg, leftPeg, rightPeg;
		APEG pegs[] = new APEG[2];
		APEG pegs1[] = new APEG[2];

		pegs[0] = new NonterminalPEG(new SymInfo(8,3), "b", arg);

		leftPeg = new LitPEG(new SymInfo(8,6), "1");
		arg = new ArrayList<Expr>();
		peg = new MapLit(new Pair("x",leftPeg));

		RulePEG a = new RulePEG(new SymInfo(3, 3), "a", RulePEG.Annotation.NONE, inh, syn, peg);
		rules.add(a);


		GrammarOption opts = new GrammarOption();
		opts.memoize = true;

		Grammar gram = new Grammar(new SymInfo (0,0), "Annotation", opts, rules);

		//TestVisitor v = new TestVisitor();
        //gram.accept(v);
 		VMVisitor vm = new VMVisitor("/home/gigi/tcc/APEG/language/code/compiler/src/main/java/apeg/vm/input.txt",new Environment("TEST",new NTInfo()));
         gram.accept(vm);
         if(vm.succeed()){
             System.out.println("ok");
         }else{
             System.out.println("falha");
         }

	}

}
