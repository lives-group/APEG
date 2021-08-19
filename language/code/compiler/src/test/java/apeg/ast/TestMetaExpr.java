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

public class TestMetaExpr {
    
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
          6 }
        */
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
    public void testMetaAttribute(){
        Expr e;
        e =  new MetaAttribute(new SymInfo(5,21), new StrLit(new SymInfo(5,21), "nome"));
        Grammar g = buildAttGrammar("y",e,"x", null);
        Object r = runAndReportVar(g,"y");
        assertEquals("nome",r.toString());
    }

    @Test
    public void testMetaInt(){
        Expr e;
        e =  new MetaIntLit(new SymInfo(5,21), new IntLit(new SymInfo(5,21), 2));
        Grammar g = buildAttGrammar("y",e,"x", null);
        Object r = runAndReportVar(g,"y");
        assertEquals("2",r.toString());
    }

    @Test
    public void testMetaFloat(){
        Expr e;
        e =  new MetaFloatLit(new SymInfo(5,21), new FloatLit(new SymInfo(5,21), (float)2.0));
        Grammar g = buildAttGrammar("y",e,"x", null);
        Object r = runAndReportVar(g,"y");
        assertEquals("2.0",r.toString());
    }

    @Test
    public void testMetaBool(){
        Expr e;
        e =  new MetaBoolLit(new SymInfo(5,21), new BoolLit(new SymInfo(5,21),true));
        Grammar g = buildAttGrammar("y",e,"x", null);
        Object r = runAndReportVar(g,"y");
        assertEquals("true",r.toString());
    }

    @Test
    public void testMetaStr(){
        Expr e;
        e =  new StrLit(new SymInfo(5,21), "abc");
        Grammar g = buildAttGrammar("y",e,"x", null);
        Object r = runAndReportVar(g,"y");
        assertEquals("abc",r);
    }

    @Test
    public void testMetaNot(){
        Expr e,op;
        e =  new MetaBoolLit(new SymInfo(5,23), new BoolLit(new SymInfo(5,23), true));

        op =  new MetaNot(new SymInfo(5,21), e);
        Grammar g = buildAttGrammar("y",op,"x", null);
        Object r = runAndReportVar(g,"y");
        assertEquals("(! true)",r.toString());
    }

    @Test
    public void testMetaIntAdd(){
        Expr op,el,er,e;
        e =  new IntLit(new SymInfo(5,21), 2);

        el =  new MetaIntLit(new SymInfo(5,17), new Attribute(new SymInfo(5,17), "y"));
        er =  new MetaIntLit(new SymInfo(5,21), new IntLit(new SymInfo(5,21), 3));
        op =  new MetaAdd(new SymInfo(5,19),el,er);
        
        Grammar g = buildAttGrammar("y",e,"x",op);
        Object r = runAndReportVar(g,"x");
        assertEquals("(+ 2 3)",r.toString());
    }

    @Test
    public void testMetaIntSub(){
        Expr op,el,er,e;
        e =  new IntLit(new SymInfo(5,21), 2);

        el =  new MetaIntLit(new SymInfo(5,17), new Attribute(new SymInfo(5,17), "y"));
        er =  new MetaIntLit(new SymInfo(5,21), new IntLit(new SymInfo(5,21), 3));
        op =  new MetaSub(new SymInfo(5,19),el,er);

        Grammar g = buildAttGrammar("y",e,"x",op);
        Object r = runAndReportVar(g,"x");
        assertEquals("(- 2 3)",r.toString());
    }

    @Test
    public void testMetaIntMult(){
        Expr op,el,er,e;
        e =  new IntLit(new SymInfo(5,21), 2);

        el =  new MetaIntLit(new SymInfo(5,17), new Attribute(new SymInfo(5,17), "y"));
        er =  new MetaIntLit(new SymInfo(5,21), new IntLit(new SymInfo(5,21), 3));
        op =  new MetaMult(new SymInfo(5,19),el,er);

        Grammar g = buildAttGrammar("y",e,"x",op);
        Object r = runAndReportVar(g,"x");
        assertEquals("(* 2 3)",r.toString());
    }

    @Test
    public void testMetaIntDiv(){
        Expr op,el,er,e;
        e =  new IntLit(new SymInfo(5,21), 2);

        el =  new MetaIntLit(new SymInfo(5,17), new Attribute(new SymInfo(5,17), "y"));
        er =  new MetaIntLit(new SymInfo(5,21), new IntLit(new SymInfo(5,21), 3));
        op =  new MetaDiv(new SymInfo(5,19),el,er);

        Grammar g = buildAttGrammar("y",e,"x",op);
        Object r = runAndReportVar(g,"x");
        assertEquals("(/ 2 3)",r.toString());
    }
    
    
    @Test
    public void testMetaIntMod(){
        Expr op,el,er,e;
        e =  new IntLit(new SymInfo(5,21), 2);

        el =  new MetaIntLit(new SymInfo(5,17), new Attribute(new SymInfo(5,17), "y"));
        er =  new MetaIntLit(new SymInfo(5,21), new IntLit(new SymInfo(5,21), 3));
        op =  new MetaMod(new SymInfo(5,19),el,er);

        Grammar g = buildAttGrammar("y",e,"x",op);
        Object r = runAndReportVar(g,"x");
        assertEquals("(% 2 3)",r.toString());
    }
    
    
      @Test
    public void testMetaFloatAdd(){
        Expr op,el,er,e;
        e =  new FloatLit(new SymInfo(5,21), (float)2);

        el =  new MetaFloatLit(new SymInfo(5,17), new Attribute(new SymInfo(5,17), "y"));
        er =  new MetaFloatLit(new SymInfo(5,21), new FloatLit(new SymInfo(5,21), 3));
        op =  new MetaAdd(new SymInfo(5,19),el,er);
        
        Grammar g = buildAttGrammar("y",e,"x",op);
        Object r = runAndReportVar(g,"x");
        assertEquals("(+ 2.0 3.0)",r.toString());
    }

    @Test
    public void testMetaFloatSub(){
        Expr op,el,er,e;
        e =  new FloatLit(new SymInfo(5,21), 2);

        el =  new MetaFloatLit(new SymInfo(5,17), new Attribute(new SymInfo(5,17), "y"));
        er =  new MetaFloatLit(new SymInfo(5,21), new FloatLit(new SymInfo(5,21), 3));
        op =  new MetaSub(new SymInfo(5,19),el,er);

        Grammar g = buildAttGrammar("y",e,"x",op);
        Object r = runAndReportVar(g,"x");
        assertEquals("(- 2.0 3.0)",r.toString());
    }

    @Test
    public void testMetaFloatMult(){
        Expr op,el,er,e;
        e =  new FloatLit(new SymInfo(5,21), 2);

        el =  new MetaFloatLit(new SymInfo(5,17), new Attribute(new SymInfo(5,17), "y"));
        er =  new MetaFloatLit(new SymInfo(5,21), new FloatLit(new SymInfo(5,21), 3));
        op =  new MetaMult(new SymInfo(5,19),el,er);

        Grammar g = buildAttGrammar("y",e,"x",op);
        Object r = runAndReportVar(g,"x");
        assertEquals("(* 2.0 3.0)",r.toString());
    }

    @Test
    public void testMetaFloatDiv(){
        Expr op,el,er,e;
        e =  new FloatLit(new SymInfo(5,21), 2);

        el =  new MetaFloatLit(new SymInfo(5,17), new Attribute(new SymInfo(5,17), "y"));
        er =  new MetaFloatLit(new SymInfo(5,21), new FloatLit(new SymInfo(5,21), 3));
        op =  new MetaDiv(new SymInfo(5,19),el,er);

        Grammar g = buildAttGrammar("y",e,"x",op);
        Object r = runAndReportVar(g,"x");
        assertEquals("(/ 2.0 3.0)",r.toString());
    }
        
    
    @Test
    public void testMetaAnd(){
        Expr op,el,er;

        el =  new MetaBoolLit(new SymInfo(5,17), new BoolLit(new SymInfo(5,17), true));
        er =  new MetaBoolLit(new SymInfo(5,21), new BoolLit(new SymInfo(5,21), false));
        op =  new MetaAnd(new SymInfo(5,19),el,er);

        Grammar g = buildAttGrammar(null,null,"x",op);
        Object r = runAndReportVar(g,"x");
        assertEquals("(&& true false)",r.toString());
    }
    
    @Test
    public void testMetaOr(){
        Expr op,el,er;

        el =  new MetaBoolLit(new SymInfo(5,17), new BoolLit(new SymInfo(5,17), true));
        er =  new MetaBoolLit(new SymInfo(5,21), new BoolLit(new SymInfo(5,21), false));
        op =  new MetaOr(new SymInfo(5,19),el,er);

        Grammar g = buildAttGrammar(null,null,"x",op);
        Object r = runAndReportVar(g,"x");
        assertEquals("(|| true false)",r.toString());
    }
    
    @Test
    public void testMetaEq(){
        Expr op,el,er,e;
        e =  new FloatLit(new SymInfo(5,21), 2);

        el =  new MetaFloatLit(new SymInfo(5,17), new Attribute(new SymInfo(5,17), "y"));
        er =  new MetaFloatLit(new SymInfo(5,21), new FloatLit(new SymInfo(5,21), 3));
        op =  new MetaEquals(new SymInfo(5,19),el,er);

        Grammar g = buildAttGrammar("y",e,"x",op);
        Object r = runAndReportVar(g,"x");
        assertEquals("(== 2.0 3.0)",r.toString());
    }
    
    @Test
    public void testMetaNEq(){
        Expr op,el,er,e;
        e =  new FloatLit(new SymInfo(5,21), 2);

        el =  new MetaFloatLit(new SymInfo(5,17), new Attribute(new SymInfo(5,17), "y"));
        er =  new MetaFloatLit(new SymInfo(5,21), new FloatLit(new SymInfo(5,21), 3));
        op =  new MetaNotEq(new SymInfo(5,19),el,er);

        Grammar g = buildAttGrammar("y",e,"x",op);
        Object r = runAndReportVar(g,"x");
        assertEquals("(!= 2.0 3.0)",r.toString());
    }
    
    @Test
    public void testMetaLt(){
        Expr op,el,er,e;
        e =  new FloatLit(new SymInfo(5,21), 2);

        el =  new MetaFloatLit(new SymInfo(5,17), new Attribute(new SymInfo(5,17), "y"));
        er =  new MetaFloatLit(new SymInfo(5,21), new FloatLit(new SymInfo(5,21), 3));
        op =  new MetaLess(new SymInfo(5,19),el,er);

        Grammar g = buildAttGrammar("y",e,"x",op);
        Object r = runAndReportVar(g,"x");
        assertEquals("(< 2.0 3.0)",r.toString());
    }
    
    @Test
    public void testMetaGt(){
        Expr op,el,er,e;
        e =  new FloatLit(new SymInfo(5,21), 2);

        el =  new MetaFloatLit(new SymInfo(5,17), new Attribute(new SymInfo(5,17), "y"));
        er =  new MetaFloatLit(new SymInfo(5,21), new FloatLit(new SymInfo(5,21), 3));
        op =  new MetaGreater(new SymInfo(5,19),el,er);

        Grammar g = buildAttGrammar("y",e,"x",op);
        Object r = runAndReportVar(g,"x");
        assertEquals("(> 2.0 3.0)",r.toString());
    }
    
    @Test
    public void testMetaGte(){
        Expr op,el,er,e;
        e =  new FloatLit(new SymInfo(5,21), 2);

        el =  new MetaFloatLit(new SymInfo(5,17), new Attribute(new SymInfo(5,17), "y"));
        er =  new MetaFloatLit(new SymInfo(5,21), new FloatLit(new SymInfo(5,21), 3));
        op =  new MetaGreaterEq(new SymInfo(5,19),el,er);

        Grammar g = buildAttGrammar("y",e,"x",op);
        Object r = runAndReportVar(g,"x");
        assertEquals("(>= 2.0 3.0)",r.toString());
    }
    
    @Test
    public void testMetaLte(){
        Expr op,el,er,e;
        e =  new FloatLit(new SymInfo(5,21), 2);

        el =  new MetaFloatLit(new SymInfo(5,17), new Attribute(new SymInfo(5,17), "y"));
        er =  new MetaFloatLit(new SymInfo(5,21), new FloatLit(new SymInfo(5,21), 3));
        op =  new MetaLessEq(new SymInfo(5,19),el,er);

        Grammar g = buildAttGrammar("y",e,"x",op);
        Object r = runAndReportVar(g,"x");
        assertEquals("(<= 2.0 3.0)",r.toString());
    }
    
    @Test
    public void testList(){
        Expr op,el,er,e;
        ArrayList<Expr> arr = new ArrayList<Expr>();
        arr.add( new IntLit(new SymInfo(5,22), 2) );
        arr.add( new IntLit(new SymInfo(5,25), 3) );
        arr.add( new IntLit(new SymInfo(5,27), 4) );
        e =  new ListLit(new SymInfo(5,21), arr);

        Grammar g = buildAttGrammar("y",e,"x",null);
        Object r = runAndReportVar(g,"y");
        assertEquals("[2, 3, 4]",r.toString());
    }
    
    @Test
    public void testAtList(){
        Expr op,el,er,e;
        ArrayList<Expr> arr = new ArrayList<Expr>();
        arr.add( new IntLit(new SymInfo(5,22), 2) );
        arr.add( new IntLit(new SymInfo(5,25), 3) );
        arr.add( new IntLit(new SymInfo(5,27), 4) );
        e =  new ListLit(new SymInfo(5,21), arr);
        
        el = new ListAcces(new SymInfo(6,21),  new Attribute(new SymInfo(6,21), "y"), new IntLit(new SymInfo(6,24), 1) );
        
        Grammar g = buildAttGrammar("y",e,"x",el);
        Object r = runAndReportVar(g,"x");
        assertEquals("3",r.toString());
    }
    
    @Test
    public void testConcatList(){
        Expr el,er,e;
        ArrayList<Expr> arr = new ArrayList<Expr>();
        arr.add( new IntLit(new SymInfo(5,20), 1) );
        arr.add( new IntLit(new SymInfo(5,22), 2) );
        arr.add( new IntLit(new SymInfo(5,25), 3) );
        arr.add( new IntLit(new SymInfo(5,27), 4) );
        e =  new ListLit(new SymInfo(6,32), arr);
        
        ArrayList<Expr> arr1 = new ArrayList<Expr>();
        arr1.add( new IntLit(new SymInfo(6,32), 5) );
        arr1.add( new IntLit(new SymInfo(6,35), 6) );
        arr1.add( new IntLit(new SymInfo(6,37), 7) );
        er =  new ListLit(new SymInfo(6,32), arr1);
        
        el = new Concat(new SymInfo(6,29), new Attribute(new SymInfo(6,21), "y"), er );
        
        Grammar g = buildAttGrammar("y",e,"x",el);
        Object r = runAndReportVar(g,"x");
        assertEquals("[1, 2, 3, 4, 5, 6, 7]",r.toString());
    }

}
