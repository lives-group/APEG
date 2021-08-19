package apeg.ast;

import java.util.List;
import java.util.ArrayList;

import apeg.util.SymInfo;
import apeg.visitor.TestVisitor;
import apeg.visitor.semantics.TypeCheckerVisitor;
import apeg.visitor.semantics.ErrorEntry;
import apeg.visitor.semantics.ErrorsMsg;
import apeg.visitor.VMVisitor;
import apeg.ast.expr.*;
import apeg.ast.rules.*;
import apeg.ast.types.*;
import apeg.ast.Grammar;
import apeg.ast.Grammar.GrammarOption;
import apeg.util.*;
import java.io.StringReader;


public class GrammarMetaIntAST {
    
    public static void main(String args[]){
        
        /*
           Grammar MetaIntAST {
               
               a: 
                 {x = (| 2 |);}
               ;
           }
        */
        
        List<RulePEG>rules = new ArrayList<RulePEG>();
        List<Expr>arg = new ArrayList<Expr>();
        List<Pair<Type, String>>inh = new ArrayList<Pair<Type, String>>();
        List<Expr>syn = new ArrayList<Expr>();
        
        List<Pair<Attribute,Expr>> updates = new ArrayList<Pair<Attribute,Expr>>();

        APEG peg, update;
        Attribute att;
        Expr e;
        
        att = new Attribute(new SymInfo(2,5), "x"); 
        e =  new MetaIntLit(new SymInfo(2,15), new IntLit(new SymInfo(2,10), 2));
        updates.add(new Pair<Attribute,Expr>(att,e) );
        peg = new UpdatePEG(new SymInfo(2,10),updates);
        

        
        RulePEG a = new RulePEG(new SymInfo(1, 2), "a", RulePEG.Annotation.NONE, inh, syn, peg);
        rules.add(a);
        
      
        GrammarOption opts = new GrammarOption();
        opts.memoize = true;
    
        Grammar gram = new Grammar(new SymInfo (0,0), "Annotation", opts, rules);
        
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
