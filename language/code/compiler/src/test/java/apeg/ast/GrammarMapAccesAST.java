package apeg.ast;

import java.util.List;
import java.util.ArrayList;

import apeg.util.SymInfo;
import apeg.visitor.TestVisitor;
import apeg.visitor.VMVisitor;
import apeg.ast.expr.*;
import apeg.ast.expr.operators.*;
import apeg.ast.rules.*;
import apeg.ast.types.*;
import apeg.ast.Grammar;
import apeg.ast.Grammar.GrammarOption;
import apeg.util.*;
import apeg.visitor.semantics.*;

public class GrammarMapAccesAST {

	public static void main(String args[]){


		List<RulePEG>rules = new ArrayList<RulePEG>();
		List<Expr>arg = new ArrayList<Expr>();
		List<Pair<Type, String>>inh = new ArrayList<Pair<Type, String>>();
		List<Expr>syn = new ArrayList<Expr>();


		Expr map,mapA,value,key,att,att2,index;

		APEG leftPeg;

		value = new IntLit(new SymInfo(0,0), 1);

		key = new StrLit(new SymInfo(0,0),"x");
		arg = new ArrayList<Expr>();
		map = new MapLit(new SymInfo(0,0),new Pair[]{new Pair(key,value)});

		index = new StrLit(new SymInfo(0,0), "x");
		mapA = new MapAcces(new SymInfo(0,0),map,index);
		att2 = new Attribute(new SymInfo(0,0),"z");

		att = new Attribute(new SymInfo(0,0),"mapA");
		ArrayList<Pair<Attribute,Expr>> arry = new ArrayList();
		arry.add(new Pair(att,map));
		arry.add(new Pair(att2,mapA));
		leftPeg = new UpdatePEG(new SymInfo(0,0),arry);


		RulePEG a = new RulePEG(new SymInfo(3, 3), "a", RulePEG.Annotation.NONE, inh, syn, leftPeg);
		rules.add(a);


		GrammarOption opts = new GrammarOption();
		opts.memoize = true;

		Grammar gram = new Grammar(new SymInfo (0,0), "Annotation", opts, rules);

		//TypeCheckerVisitor tcv = new TypeCheckerVisitor();
		//gram.accept(tcv);
		VMVisitor vm = new VMVisitor("../src/main/java/apeg/vm/input.txt",new Environment<String,NTInfo>());
		gram.accept(vm);
		if(vm.succeed()){
			System.out.println("ok");
		}else{
			System.out.println("falha");
		}

	}

}
