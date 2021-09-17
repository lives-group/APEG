package apeg.ast;

import java.util.List;
import java.util.ArrayList;

import apeg.util.SymInfo;
// import apeg.visitor.TestVisitor;
import apeg.visitor.semantics.TypeCheckerVisitor;
import apeg.visitor.semantics.ErrorEntry;
import apeg.visitor.semantics.ErrorsMsg;
import apeg.visitor.VMVisitor;
import apeg.ast.expr.*;
import apeg.ast.expr.operators.*;
import apeg.ast.rules.*;
import apeg.ast.types.*;
import apeg.ast.Grammar;
import apeg.ast.Grammar.GrammarOption;
import apeg.util.*;
import java.io.StringReader;


public class GrammarMetaIntAddAST {
    
    public static void main(String args[]){
        
        /*
                   10        20        30
          123456789012345678901234567890
          1 Grammar MetaIntAST {
          2     
          3     a: 
          4       {x = (| 2 + 3 |);}
          5     ;
          6 }
        */
        
        List<RulePEG>rules = new ArrayList<RulePEG>();
        List<Expr>arg = new ArrayList<Expr>();
        List<Pair<Type, String>>inh = new ArrayList<Pair<Type, String>>();
        List<Expr>syn = new ArrayList<Expr>();
        
        List<Pair<Attribute,Expr>> updates = new ArrayList<Pair<Attribute,Expr>>();

        APEG peg, update;
        Attribute att;
        Expr op,el,er;
        
        att = new Attribute(new SymInfo(4,10), "x"); 
        
        el =  new MetaIntLit(new SymInfo(4,17), new IntLit(new SymInfo(4,17), 2));
        er =  new MetaIntLit(new SymInfo(4,21), new IntLit(new SymInfo(4,21), 3));
        op =  new MetaAdd(new SymInfo(4,19),el,er);
        updates.add(new Pair<Attribute,Expr>(att,op) );
        peg = new UpdatePEG(new SymInfo(4,12),updates);
        

        
        RulePEG a = new RulePEG(new SymInfo(3, 7), "a", RulePEG.Annotation.NONE, inh, syn, peg);
        rules.add(a);
        
      
        GrammarOption opts = new GrammarOption();
        opts.memoize = true;
    
        Grammar gram = new Grammar(new SymInfo (1,1), "Annotation", opts, rules);
        
        ErrorsMsg emsg = ErrorsMsg.getInstance();
        
        TypeCheckerVisitor v = new TypeCheckerVisitor();
        gram.accept(v);
        
        if(v.getError().size() > 0){
            for(ErrorEntry err : v.getError()){
                System.out.println(emsg.translate(err));
            }
            return;
        }
        VMVisitor vm = new VMVisitor(new StringReader("0000"),v.getEnv());
        gram.accept(vm);
        if(vm.succeed()){
             System.out.println("ok");
        }else{
             System.out.println("falha");
        }        
    }

}
