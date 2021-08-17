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
import apeg.ast.expr.operators.*;
import apeg.ast.rules.*;
import apeg.ast.types.*;
import apeg.ast.Grammar;
import apeg.ast.Grammar.GrammarOption;
import apeg.util.*;
import apeg.vm.*;
import java.io.StringReader;


public class GrammarMetaIntAddAST2 {
    
    public static void main(String args[]){
        
        /*
                   10        20        30
          123456789012345678901234567890
          1 Grammar MetaIntAST {
          2     
          3     a: 
          4       { y =  2 ;
                    x = (| #i.y + 3 |);}
          5     ;
          6 }
        */
        CTX ctx;
        List<RulePEG>rules = new ArrayList<RulePEG>();
        List<Expr>arg = new ArrayList<Expr>();
        List<Pair<Type, String>>inh = new ArrayList<Pair<Type, String>>();
        List<Expr>syn = new ArrayList<Expr>();
        
        List<Pair<Attribute,Expr>> updates = new ArrayList<Pair<Attribute,Expr>>();

        APEG peg, update;
        Attribute att1,att2;
        Expr op,el,er,e, e2;
        
        att1 = new Attribute(new SymInfo(4,10), "y"); 
        e = new MetaIntLit(new SymInfo(5,21), new IntLit(new SymInfo(5,21), 2));
        e2 =  new IntLit(new SymInfo(5,21), 2);

        att2 = new Attribute(new SymInfo(5,10), "x"); 
        
        el =  new MetaIntLit(new SymInfo(5,17), new Attribute(new SymInfo(5,17), "y"));
        er =  new MetaIntLit(new SymInfo(5,21), new IntLit(new SymInfo(5,21), 3));
        op =  new MetaAdd(new SymInfo(5,19),el,er);
        
        updates.add(new Pair<Attribute,Expr>(att1,e2) );
        updates.add(new Pair<Attribute,Expr>(att2,op) );
        
        peg = new UpdatePEG(new SymInfo(5,12),updates);
        

        
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
        ctx = vm.getLastCTX();
        if(vm.succeed()){
             System.out.println("ok");
             if(ctx.readValue("x").toString().equals("(+ 2 3)")){
                System.out.println("Test passed");
             }
        }else{
             System.out.println("falha");
        }        
    }

}
