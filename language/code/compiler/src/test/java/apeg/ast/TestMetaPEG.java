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
    
    @Test
    public void testMetaNonterminal(){
        Expr name, al;
        MetaAPEG p;
        
        ArrayList<Expr> arr = new ArrayList<Expr>();
        
        arr.add(  new MetaIntLit(new SymInfo(5,21), new IntLit(new SymInfo(5,21), 2)) );
        arr.add(  new MetaIntLit(new SymInfo(5,13), new IntLit(new SymInfo(5,17), 3)) );
        al =  new ListLit(new SymInfo(5,20), arr);
        
        name =  new StrLit(new SymInfo(5,21), "r2dd");

        p = new MetaNonterminalPEG(new SymInfo(6,20), name, new Attribute(new SymInfo(6,27), "es") );
        
        Grammar g = buildAttGrammar("es",al,"y", p);
        Object r = runAndReportVar(g,"y");
        assertEquals("(r2dd 2 3)",r.toString());
    }
    
    @Test
    public void testMetaNonterminal2(){
        Expr name, al, el, er, op;
        MetaAPEG p;
        
        ArrayList<Expr> arr = new ArrayList<Expr>();
        
        el =  new MetaIntLit(new SymInfo(5,17), new IntLit(new SymInfo(5,17), 7));
        er =  new MetaIntLit(new SymInfo(5,19), new IntLit(new SymInfo(5,19), 2));
        op =  new MetaAdd(new SymInfo(5,19),el,er);
        
        arr.add(  op  );
        arr.add(  new MetaIntLit(new SymInfo(5,13), new IntLit(new SymInfo(5,17), 3)) );
        al =  new ListLit(new SymInfo(5,20), arr);
        
        name =  new StrLit(new SymInfo(5,21), "r2dd");

        p = new MetaNonterminalPEG(new SymInfo(6,20), name, new Attribute(new SymInfo(6,27), "es") );
        
        Grammar g = buildAttGrammar("es",al,"y", p);
        Object r = runAndReportVar(g,"y");
        assertEquals("(r2dd (+ 7 2) 3)",r.toString());
    }

    @Test
    public void testMetaRule(){
        Expr name, tyl, al, rl, el, er, op;
        MetaAPEG b;
        MetaRulePEG p;
        
        
        ArrayList<Expr> arrTy = new ArrayList<Expr>();
        arrTy.add( new MetaTyInt(new SymInfo(5,17)) );
        arrTy.add( new MetaTyInt(new SymInfo(5,19) ));
        tyl =  new ListLit(new SymInfo(7,28), arrTy);
        
        ArrayList<Expr> arrRet = new ArrayList<Expr>();
        arrRet.add( new MetaIntLit(new SymInfo(6,17), new IntLit(new SymInfo(6,17), 7)) );
        rl =  new ListLit(new SymInfo(7,28), arrRet);
        
        ArrayList<Expr> arr = new ArrayList<Expr>();
        arr.add( new  StrLit(new SymInfo(6, 17), "x") );
        arr.add( new  StrLit(new SymInfo(6, 19), "y") );
        al =  new ListLit(new SymInfo(7,28), arr);
        
        name =  new StrLit(new SymInfo(7,10), "r2dd");
        
        b = new MetaLitPEG(new SymInfo(7,10), new StrLit(new SymInfo(7,10), "a") );

        p = new MetaRulePEG(new SymInfo(8,10), name, null, tyl, al, rl, b);
        
        Grammar g = buildAttGrammar("y",p,"y", null);
        Object r = runAndReportVar(g,"y");

        assertEquals("[(rule r2dd NONE ( (:: int x) (:: int y)) ( 7) 'a')]",r.toString());
    }   

    @Test
    public void testMetaGrammarCompose(){
        Expr name, tyl, al, rl, el, er, op;
        MetaAPEG b;
        MetaRulePEG p,p2;
        
        
        ArrayList<Expr> arrTy = new ArrayList<Expr>();
        arrTy.add( new MetaTyInt(new SymInfo(5,17)) );
        arrTy.add( new MetaTyInt(new SymInfo(5,19) ));
        tyl =  new ListLit(new SymInfo(7,28), arrTy);
        
        ArrayList<Expr> arrRet = new ArrayList<Expr>();
        arrRet.add( new MetaIntLit(new SymInfo(6,17), new IntLit(new SymInfo(6,17), 7)) );
        rl =  new ListLit(new SymInfo(7,28), arrRet);
        
        ArrayList<Expr> arr = new ArrayList<Expr>();
        arr.add( new  StrLit(new SymInfo(6, 17), "x") );
        arr.add( new  StrLit(new SymInfo(6, 19), "y") );
        al =  new ListLit(new SymInfo(7,28), arr);
        
        name =  new StrLit(new SymInfo(7,10), "r2dd");
        
        b = new MetaLitPEG(new SymInfo(7,10), new StrLit(new SymInfo(7,10), "a") );

        p = new MetaRulePEG(new SymInfo(8,10), name, null, tyl, al, rl, b);
        
        
        arrTy = new ArrayList<Expr>();
        arrTy.add( new MetaTyInt(new SymInfo(5,17)) );
        tyl =  new ListLit(new SymInfo(7,28), arrTy);
        
        arrRet = new ArrayList<Expr>();
        rl =  new ListLit(new SymInfo(7,28), arrRet);
        
        arr = new ArrayList<Expr>();
        arr.add( new  StrLit(new SymInfo(6, 17), "x") );
        al =  new ListLit(new SymInfo(7,28), arr);
        
        name =  new StrLit(new SymInfo(7,10), "c3po");
        
        b = new MetaLitPEG(new SymInfo(7,10), new StrLit(new SymInfo(7,10), "c") );

        p2 = new MetaRulePEG(new SymInfo(8,10), name, null, tyl, al, rl, b);
        
        op = new Compose(new SymInfo(9,10), p, p2);
        
        Grammar g = buildAttGrammar("y",op,"y", null);
        Object r = runAndReportVar(g,"y");
        System.out.println(r);
        assertEquals("[(rule r2dd NONE ( (:: int x) (:: int y)) ( 7) 'a'), (rule c3po NONE ( (:: int x)) () 'c')]",r.toString());
    }
    
    
    
    @Test
    public void testMetaGrammarCompose2(){
        Expr name, tyl, al, rl, el, er, op;
        MetaAPEG b;
        MetaRulePEG p,p2;
        
        
        ArrayList<Expr> arrTy = new ArrayList<Expr>();
        arrTy.add( new MetaTyInt(new SymInfo(5,17)) );
        tyl =  new ListLit(new SymInfo(7,28), arrTy);
        
        ArrayList<Expr> arrRet = new ArrayList<Expr>();
        rl =  new ListLit(new SymInfo(7,28), arrRet);
        
        ArrayList<Expr> arr = new ArrayList<Expr>();
        arr.add( new  StrLit(new SymInfo(6, 17), "x") );
        al =  new ListLit(new SymInfo(7,28), arr);
        
        name =  new StrLit(new SymInfo(7,10), "c3po");
        
        b = new MetaLitPEG(new SymInfo(7,10), new StrLit(new SymInfo(7,10), "a") );

        p = new MetaRulePEG(new SymInfo(8,10), name, null, tyl, al, rl, b);
        
        
        arrTy = new ArrayList<Expr>();
        arrTy.add( new MetaTyInt(new SymInfo(5,17)) );
        tyl =  new ListLit(new SymInfo(7,28), arrTy);
        
        arrRet = new ArrayList<Expr>();
        rl =  new ListLit(new SymInfo(7,28), arrRet);
        
        arr = new ArrayList<Expr>();
        arr.add( new  StrLit(new SymInfo(6, 17), "x") );
        al =  new ListLit(new SymInfo(7,28), arr);
        
        name =  new StrLit(new SymInfo(7,10), "c3po");
        
        b = new MetaLitPEG(new SymInfo(7,10), new StrLit(new SymInfo(7,10), "c") );

        p2 = new MetaRulePEG(new SymInfo(8,10), name, null, tyl, al, rl, b);
        
        op = new Compose(new SymInfo(9,10), p, p2);
        
        Grammar g = buildAttGrammar("y",op,"y", null);
        Object r = runAndReportVar(g,"y");
        System.out.println(r);
        assertEquals("[(rule c3po NONE ( (:: int x)) () (/ 'a' 'c'))]",r.toString());
    }
    
    @Test
    public void testMetaLanCompose(){
        Expr name, tyl, al, rl, el, er, op;
        MetaAPEG b;
        MetaRulePEG p,p2;
        
        
        ArrayList<Expr> arrTy = new ArrayList<Expr>();
        arrTy.add( new MetaTyInt(new SymInfo(5,17)) );
        arrTy.add( new MetaTyInt(new SymInfo(5,19) ));
        tyl =  new ListLit(new SymInfo(7,28), arrTy);
        
        ArrayList<Expr> arrRet = new ArrayList<Expr>();
        arrRet.add( new MetaIntLit(new SymInfo(6,17), new IntLit(new SymInfo(6,17), 7)) );
        rl =  new ListLit(new SymInfo(7,28), arrRet);
        
        ArrayList<Expr> arr = new ArrayList<Expr>();
        arr.add( new  StrLit(new SymInfo(6, 17), "x") );
        arr.add( new  StrLit(new SymInfo(6, 19), "y") );
        al =  new ListLit(new SymInfo(7,28), arr);
        
        name =  new StrLit(new SymInfo(7,10), "r2dd");
        
        b = new MetaLitPEG(new SymInfo(7,10), new StrLit(new SymInfo(7,10), "a") );

        p = new MetaRulePEG(new SymInfo(8,10), name, null, tyl, al, rl, b);
        
        
        arrTy = new ArrayList<Expr>();
        arrTy.add( new MetaTyInt(new SymInfo(5,17)) );
        tyl =  new ListLit(new SymInfo(7,28), arrTy);
        
        arrRet = new ArrayList<Expr>();
        rl =  new ListLit(new SymInfo(7,28), arrRet);
        
        arr = new ArrayList<Expr>();
        arr.add( new  StrLit(new SymInfo(6, 17), "x") );
        al =  new ListLit(new SymInfo(7,28), arr);
        
        name =  new StrLit(new SymInfo(7,10), "c3po");
        
        b = new MetaLitPEG(new SymInfo(7,10), new StrLit(new SymInfo(7,10), "c") );

        p2 = new MetaRulePEG(new SymInfo(8,10), name, null, tyl, al, rl, b);
        
        op = new Compose(new SymInfo(9,10), p, p2);
        
        Grammar g = buildAttGrammar("y",op,"y", null);
        Object r = runAndReportVar(g,"y");
        System.out.println(r);
        assertEquals("[(rule r2dd NONE ( (:: int x) (:: int y)) ( 7) 'a'), (rule c3po NONE ( (:: int x)) () 'c')]",r.toString());
    }      
}
