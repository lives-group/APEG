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

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class TestMetaPEG {
    
    private Grammar buildAttGrammar(String v1, Expr e1,String v2, Expr e2){
        /*
                   10        20        30
          123456789012345678901234567890
          1 Grammar MetaIntAST {
          2
          3     a:
          4       { _v1_ = _e1_ ; // Only if _e1_ != null
                    _v2_ = _e2_;} // Only if _e2_ != null
          5     ;
          6 }*/
          
        List<RulePEG>rules = new ArrayList<RulePEG>();
        List<Expr>arg = new ArrayList<Expr>();
        List<Pair<Type, String>>inh = new ArrayList<Pair<Type, String>>();
        List<Expr>syn = new ArrayList<Expr>();
        List<Pair<Attribute,Expr>> updates = new ArrayList<Pair<Attribute,Expr>>();
        Attribute att1 = new Attribute(new SymInfo(4,10), v1);
        Attribute att2 = new Attribute(new SymInfo(5,10), v2);
        if(e1 != null){updates.add(new Pair<Attribute,Expr>(att1,e1) );}
        if(e2 != null){updates.add(new Pair<Attribute,Expr>(att2,e2) );}
        APEG peg = new UpdatePEG(new SymInfo(5,12),updates);
        RulePEG a = new RulePEG(new SymInfo(3, 7), "a", RulePEG.Annotation.NONE, inh, syn, peg);
        rules.add(a);
        GrammarOption opts = new GrammarOption();
        opts.memoize = false;
        return new Grammar(new SymInfo (1,1), "Annotation", opts, rules);
    }

    private Object runAndReportVar(Grammar gram, String x){
        CTX ctx;
        ErrorsMsg emsg = ErrorsMsg.getInstance();
        TypeCheckerVisitor v = new TypeCheckerVisitor();
        gram.accept(v);
        if(v.getError().size() > 0){
            for(ErrorEntry err : v.getError()){
                System.out.println(emsg.translate(err));
            }
            return "-TYPE-ERROS-";
        }
        VMVisitor vm = new VMVisitor(new StringReader("00"),v.getEnv());
        //vm.setDebugMode(true);
        gram.accept(vm);
        ctx = vm.getLastCTX();
        if(vm.succeed()){
             return ctx.readValue(x);
        }
        return "-PARSE-FAIL-";
    }
    
    @Test
    public void testMetaLitPEG(){
         Expr e;
         e =  new MetaLitPEG(new SymInfo(5,21), new StrLit(new SymInfo(5,21), "nome"));
         Grammar g = buildAttGrammar("y",e,"x", null);
         Object r = runAndReportVar(g,"y");
         assertEquals("nome",((LitPEG)r).getLit());
    }
    
    
    @Test
    public void testMetaKleneePEG(){
         Expr e,e2;
         e =  new MetaLitPEG(new SymInfo(5,21), new StrLit(new SymInfo(5,21), "aa"));
         e2 =  new MetaKleenePEG(new SymInfo(5,19), e);
         Grammar g = buildAttGrammar("y",e2,null, null);
         Object r = runAndReportVar(g,"y");
         assertEquals("aa",((LitPEG)((KleenePEG)r).getPegExp()).getLit());
    }


}
