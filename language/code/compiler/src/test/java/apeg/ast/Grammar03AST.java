package apeg.ast;

import java.util.*;
import apeg.util.*;
import apeg.ast.expr.*;
import apeg.ast.rules.*;
import apeg.ast.types.*;
import apeg.ast.GrammarNode;


public class Grammar03AST {
	
	public static void main (String args[]){

		List<RulePEG>rules = new ArrayList<RulePEG>();
		List<Pair<Type, String>>inh = new ArrayList<Pair<Type, String>>();
		List<Pair<Attribute, Expr>>assigs = new ArrayList<Pair<Attribute, Expr>>();
		
		APEG peg;
		
		assigs.add(new Pair<Attribute, Expr>(new Attribute(new SymInfo(5, 4), "i"), new BoolLit(new SymInfo(5, 8),true)));
		peg = new UpdatePeg(new SymInfo(5, 6), assigs);
		
		inh.add(new Pair<Type, String>(new TyInt(new SymInfo(3, 3)), "x"));
		inh.add(new Pair<Type, String>(new TyInt(new SymInfo(3, 10)), "y"));
		inh.add(new Pair<Type, String>(new TyInt(new SymInfo(3, 17)), "z"));
		inh.add(new Pair<Type, String>(new TyInt(new SymInfo(3, 24)), "w"));
		inh.add(new Pair<Type, String>(new TyBool(new SymInfo(3, 31)), "i"));
		
		RulePEG s = new RulePEG(new SymInfo(3, 1), "s", RulePEG.Annotation.NONE, inh, null, peg);
		
		GrammarNode gram = new GrammarNode("expression", null, rules);
		
	}

}
