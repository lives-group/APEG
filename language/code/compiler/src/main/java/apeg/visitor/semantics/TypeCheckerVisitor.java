package apeg.visitor.semantics;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;


import apeg.util.Environment;
import apeg.util.Pair;
import apeg.util.SymInfo;
import apeg.visitor.semantics.*;
import apeg.visitor.*;

import apeg.ast.*;
import apeg.ast.expr.*;
import apeg.ast.expr.operators.*;
import apeg.ast.rules.*;
import apeg.ast.types.*;


public class TypeCheckerVisitor extends Visitor {


    private Stack<VType> s;
    private Environment <String, VType> gamma;
    private Environment<String, NTInfo> global;
    private List<ErrorEntry> error;
//    private String errorMessage;
    private VarPool pool;
    private Environment<String,ArrayList<NTType>> opTable;
    private CTM ct;
    private boolean addVar;
    private VType[] emptyVTyepArray;
    private boolean debug;

    public TypeCheckerVisitor() {
        s = new Stack<VType>();
        gamma = new Environment<String, VType>();
        global = new Environment<String, NTInfo>();
        pool = VarPool.getInstance();
        error = new ArrayList<ErrorEntry>();
        ct = new CTM();
        addVar = false;
        opTable = OperatorTables.mkArithmeticEnv();
        emptyVTyepArray = new VType[0];
        debug = false;
    }

    public TypeCheckerVisitor(boolean debug) {
        this();
        this.debug = debug;
    }

    public void setDebugMode(boolean b){ debug = b; }
    
    public List<ErrorEntry> getError(){
        return error;
    }
    
    public Environment<String,NTInfo> getEnv(){
        return global;
    }

    private void errorMsg(int c, SymInfo s){
          error.add(new ErrorEntry(c,s,"",emptyVTyepArray));
    }

    private void errorMsg(int c, SymInfo s, VType td){
          error.add(new ErrorEntry(c,s,"",new VType[]{td} ));
    }

    private void errorMsg(int c, SymInfo s, String name){
          error.add(new ErrorEntry(c,s,name,emptyVTyepArray));
    }

    private void errorMsg(int c, SymInfo s, String name, VType t){
          //errorMessage = "Error at (" + n.getSymInfo().getLine() + ", " + n.getSymInfo().getColumn() + ") :" + msg;
          error.add(new ErrorEntry(c,s,name,new VType[]{t} ));
          //System.out.println(errorMessage);
    }

    private void errorMsg(int c, SymInfo s,String name, VType te, VType td){
          error.add(new ErrorEntry(c,s,name,new VType[]{te,td} ));
    }
    
    // Construir função para substituir as variáveis.




    private boolean matchBinOp(String op, VType l, VType r){
        VTyVar t; 
        if((l instanceof VTyVar) || (r instanceof VTyVar)){
            t = pool.newVar();
            ct.addConstraint(new OpConstraint(op, new NTType(new VType[] {l, r}, new VType[] {t})));
            s.push(t);
            return true;
        }
        else {
            for(NTType nt : opTable.get(op)) {
                if(nt.matchInherited(new VType[] {l, r}) ) {
                    s.push(nt.getReturnAt(0));
                    return true;
                }
            }
        }
        s.push(TypeError.getInstance());
        return false;
    }
    
    private boolean checkBinaryMetaOperator(VType l, VType r){
        if(l.matchCT(VTyMetaExpr.getInstance(),ct) && r.matchCT(VTyMetaExpr.getInstance(),ct)){
            s.push(VTyMetaExpr.getInstance());
            return true;
        }
        s.push(TypeError.getInstance());
        return false;
    }

    @Override
    public void visit(Attribute n) {
        VType t = gamma.get(n.getName());
        if (t != null) {
            s.push(t);
        }
        else {
            if(addVar) {
                gamma.add(n.getName(), s.push(pool.newVar()));
                return;
            }
            s.push(TypeError.getInstance());
            errorMsg(6, n.getSymInfo(), n.getName());
        }
    }

    @Override
    public void visit(AttributeGrammar n) {
        VType t = gamma.get(n.getName());
        if(t != null) {
            s.push(t);
        }
        else {
            s.push(TypeError.getInstance());
            errorMsg(6, n.getSymInfo(), n.getName(),TypeError.getInstance());
        }
    }

    @Override
    public void visit(BoolLit n) {
        s.push(VTyBool.getInstance());
    }

    @Override
    public void visit(CharLit n) {
        s.push(VTyChar.getInstance());
    }

    @Override
    public void visit(FloatLit n) {
        s.push(VTyFloat.getInstance());
    }

    @Override
    public void visit(IntLit n) {
        s.push(VTyInt.getInstance());
    }

    @Override
    public void visit(MapLit n) {
        Pair<Expr,Expr>[] assocs = n.getAssocs();
        VType tyidx, tyval, tyaux;
        if(assocs == null || assocs.length == 0 ){
           s.push(TypeError.getInstance());
           errorMsg(7, n.getSymInfo());
           return;
        }
        assocs[0].getSecond().accept(this);
        tyval = s.pop();
        if(tyval instanceof TypeError ){
           s.push(TypeError.getInstance());
           errorMsg(4, n.getSymInfo(),tyval);
           return;
        }else{
           for(Pair<Expr,Expr> p : assocs){
              p.getFirst().accept(this);
              p.getSecond().accept(this);
              tyaux = s.pop();
              tyidx = s.pop();
              if(!tyidx.match(VTyString.getInstance()) ){
                  errorMsg(8, p.getFirst().getSymInfo(), tyidx);
                  s.push(TypeError.getInstance());
                  return;
              }else if(!tyaux.match(tyval) ){
                  errorMsg(10, p.getSecond().getSymInfo(), "", tyaux, tyval);
                  s.push(TypeError.getInstance());
                  return;
              }
           }
        }
        s.push(new VTyMap(tyval));
    }
    
    
    @Override
    public void visit(MetaAttribute n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(StrLit n) {
        s.push(VTyString.getInstance());
    }



    @Override
    public void visit(MetaBoolLit n) {
         n.getExpr().accept(this);
         if( !s.peek().matchCT(VTyBool.getInstance(),ct )){
             errorMsg(27,n.getSymInfo(),"Meta Bool", s.peek());
             s.push(TypeError.getInstance());
         }
         s.push(VTyMetaExpr.getInstance());
    }

    @Override
    public void visit(MetaCharLit n) {
         n.getExpr().accept(this);
         if( !s.peek().matchCT(VTyChar.getInstance(), ct )){
             errorMsg(27,n.getSymInfo(),"Meta Char", s.peek());
             s.push(TypeError.getInstance());
         }
         s.push(VTyMetaExpr.getInstance());
    }

    @Override
    public void visit(MetaRangePEG n){ s.push(VTyMetaPeg.getInstance()); }

    @Override
    public void visit(MetaChoicePEG n){ s.push(VTyMetaPeg.getInstance()); }

    @Override
    public void visit(MetaConstraintPEG n) {
         n.getExpr().accept(this);
         if(! s.peek().matchCT(VTyMetaExpr.getInstance() ,ct) ){
              errorMsg(27,n.getSymInfo(),"", s.peek());
              s.pop();
              s.push(TypeError.getInstance());
              return;
         }
         s.pop();
         s.push(VTyMetaPeg.getInstance());
    }

    @Override
    public void visit(MetaFloatLit n) {
         n.getExpr().accept(this);
         if( !s.peek().matchCT(VTyFloat.getInstance(), ct )){
             errorMsg(27,n.getSymInfo(),"Meta Float", s.peek());
             s.push(TypeError.getInstance());
         }
         s.push(VTyMetaExpr.getInstance());

    }

    @Override
    public void visit(MetaIntLit n){
         n.getExpr().accept(this);
         if( !s.peek().matchCT(VTyInt.getInstance(),ct )){
             errorMsg(27,n.getSymInfo(),"Meta Int", s.peek());
             s.pop();
             s.push(TypeError.getInstance());
             return;
         }
         s.pop();
         s.push(VTyMetaExpr.getInstance());
    }
    
    @Override
    public void visit(MetaMapLit n) {
           
    }
    
    @Override
    public void visit(MetaAndPEG n) {
         n.getPeg().accept(this);
         if( !s.peek().matchCT(VTyMetaPeg.getInstance(),ct )){
             errorMsg(27,n.getSymInfo(),"Meta Int", s.peek());
             s.push(TypeError.getInstance());
         }
         s.push(VTyMetaPeg.getInstance());
    }

    @Override
    public void visit(MetaAnyPEG n) {
        s.push(VTyMetaPeg.getInstance());
    }

    @Override
    public void visit(MetaBindPEG n) {
          VType tya;
          n.getExprAtt().accept(this);
          tya = s.pop();
          n.getExprP().accept(this);
          if(tya.matchCT(VTyMetaExpr.getInstance(),ct) && s.peek().matchCT(VTyMetaPeg.getInstance(),ct) ){
              return;
          }
          errorMsg(27,n.getSymInfo(),"{| = |}", tya,s.peek());
          s.pop();
          s.push(TypeError.getInstance());
    }

    @Override
    public void visit(MetaKleenePEG n) {
          n.getExpr().accept(this);
          if( !s.peek().matchCT(VTyMetaPeg.getInstance(),ct )){
             errorMsg(27,n.getSymInfo(),"{| * |}", s.peek());
             s.pop();
             s.push(TypeError.getInstance());
             return;
         }
    
    }

    @Override
    public void visit(MetaLitPEG n) {
          n.getExpr().accept(this);
          if( !s.peek().matchCT(VTyMetaPeg.getInstance(),ct )){
             errorMsg(27,n.getSymInfo(),"Meta Lit", s.peek());
             s.pop();
             s.push(TypeError.getInstance());
             return;
         }
    
    }


    @Override
    public void visit(MetaNonterminalPEG n){ 
          n.getName().accept(this);
          VType ty = s.pop();
          if(ty.matchCT(VTyString.getInstance(),ct) ){
               for(Expr e : n.getArgs()){
                   e.accept(this);
                   if( !s.peek().matchCT(VTyMetaExpr.getInstance(),ct) ){
                        errorMsg(27,n.getSymInfo(),"Meta nonterminal call", s.peek());
                        s.pop();
                        return;
                   }
                   s.pop();
               }
               s.push(VTyMetaPeg.getInstance());
               return;
          }else{
               errorMsg(27,n.getSymInfo(),"Meta nonterminal call name", ty);
               s.push(TypeError.getInstance());
          }
    }

    @Override
    public void visit(MetaNotPEG n) {
          n.getExpr().accept(this);
          if( !s.peek().matchCT(VTyMetaPeg.getInstance(),ct )){
             errorMsg(27,n.getSymInfo(),"{| ! |}", s.peek());
             s.pop();
             s.push(TypeError.getInstance());
             return;
         }
    }

    @Override
    public void visit(MetaOptionalPEG n) {
          n.getExpr().accept(this);
          if( !s.peek().matchCT(VTyMetaPeg.getInstance(),ct )){
             errorMsg(27,n.getSymInfo(),"{| ? |}", s.peek());
             s.pop();
             s.push(TypeError.getInstance());
             return;
         }
         
    }

    @Override
    public void visit(MetaPKleene n) {
          n.getPegExpr().accept(this);
          if( !s.peek().matchCT(VTyMetaPeg.getInstance(),ct )){
             errorMsg(27,n.getSymInfo(),"{| + |}", s.peek());
             s.pop();
             s.push(TypeError.getInstance());
             return;
         }
         s.pop();
    }

    @Override
    public void visit(MetaRulePEG n) {
                       
    }

    @Override
    public void visit(MetaSeqPEG n) {
         for( Expr e: n.getExpr()){
             e.accept(this);
             if( !s.peek().matchCT(VTyMetaPeg.getInstance(),ct ) ){
                 errorMsg(27,n.getSymInfo(),"Meta nonterminal call", s.peek());
                 return;
             }
             s.pop();
         }
         s.push(VTyMetaPeg.getInstance());
    }


    @Override
    public void visit(MetaUpdatePEG n) {
        // TODO Auto-generated method stub

        
    }

    @Override
    public void visit(MetaVar n) { 
        s.push(VTyMetaExpr.getInstance()); 
    }

    @Override
    public void visit(Add n) {
        n.getLeft().accept(this);
        VType left = s.peek();
        n.getRight().accept(this);
        VType right = s.peek();
        if(!matchBinOp("ADD", left, right)) {
            errorMsg(11, n.getSymInfo(),"+",left, right);
        }
    }


    @Override
    public void visit(And n) {
        n.getLeft().accept(this);
        VType left = s.peek();
        n.getRight().accept(this);
        VType right = s.peek();
        if(!matchBinOp("AND", left, right)) {
            errorMsg(12, n.getSymInfo(),"&&",left, right);
        }
    }

    @Override
    public void visit(Compose n) {
        n.getLeft().accept(this);
        VType left = s.peek();
        n.getRight().accept(this);
        VType right = s.peek();
        if(left instanceof VTyVar || right instanceof VTyVar) {
            VTyVar r = pool.newVar();
            s.push(r);
        }
        else {
            if(left == right) {
                s.push(left);
            }
            else {
                errorMsg(18,n.getSymInfo(),"<<",left,right);
            }
        }
    }


    @Override
    public void visit(Concat n) {
        n.getLeft().accept(this);
        VType left = s.peek();
        n.getRight().accept(this);
        VType right = s.peek();
        if(left == right) {
            s.push(left);
        }
        else {
            errorMsg(14, n.getSymInfo(),"++",left, right);
            s.push(TypeError.getInstance());
        }
    }

    @Override
    public void visit(Div n) {
        n.getLeft().accept(this);
        VType left = s.peek();
        n.getRight().accept(this);
        VType right = s.peek();
        if(!matchBinOp("DIV", left, right)) {
            errorMsg(11, n.getSymInfo(),"/",left, right);
        }
    }

    @Override
    public void visit(Equals n) {
        n.getLeft().accept(this);
        VType left = s.peek();
        n.getRight().accept(this);
        VType right = s.peek();
        if(!matchBinOp("EQ", left, right)) {
            errorMsg(13, n.getSymInfo(),"==",left, right);
        }
    }

    @Override
    public void visit(Greater n) {
        n.getLeft().accept(this);
        VType left = s.peek();
        n.getRight().accept(this);
        VType right = s.peek();
        if(!matchBinOp("GT", left, right)) {
            errorMsg(13, n.getSymInfo(),">",left, right);
        }
    }

    @Override
    public void visit(GreaterEq n) {
        n.getLeft().accept(this);
        VType left = s.peek();
        n.getRight().accept(this);
        VType right = s.peek();
        if(!matchBinOp("GE", left, right)) {
            errorMsg(13, n.getSymInfo(),">=",left, right);
        }
    }

    @Override
    public void visit(Less n) {
        n.getLeft().accept(this);
        VType left = s.peek();
        n.getRight().accept(this);
        VType right = s.peek();
        if(!matchBinOp("LT", left, right)) {
            errorMsg(13, n.getSymInfo(),"<",left, right);
        }
    }

    @Override
    public void visit(LessEq n) {
        n.getLeft().accept(this);
        VType left = s.peek();
        n.getRight().accept(this);
        VType right = s.peek();
        if(!matchBinOp("LE", left, right)) {
            errorMsg(14, n.getSymInfo(),"<=",left, right);
        }
    }

    @Override
    public void visit(MapAcces n) {
         n.getMap().accept(this);
         n.getIndex().accept(this);
         VType tm,ti;
         ti = s.pop();
         tm = s.pop();
         if( ti.matchCT(VTyString.getInstance(),ct)){
             if(tm instanceof VTyMap){
                 s.push( ((VTyMap)tm).getTyParameter());
                 return;
             }else if(tm instanceof VTyVar){
                  VTyVar tyv = pool.newVar();
                  VTyMap vmap = new VTyMap( tyv );
                  if(tm.matchCT(vmap,ct) ){
                        s.push(tyv);
                        return;
                  }
                  errorMsg(5,n.getSymInfo(),"",tm);
                  s.push(TypeError.getInstance());
                  return;
             }else if(tm instanceof TypeError){
                  errorMsg(3,n.getSymInfo(),"");
                  s.push(TypeError.getInstance()); 
                  return;
             }
             
         }
         errorMsg(8,n.getSymInfo(),"",ti);
         s.push(TypeError.getInstance());
    }

    @Override
    public void visit(MapExtension n) {
        n.getMap().accept(this);
        VType vmp = s.pop();
        if(vmp instanceof VTyMap ){
              n.getKey().accept(this);
              VType vkey = s.pop();
              if(vkey.matchCT(VTyString.getInstance(),ct) ){
                  n.getValue().accept(this);
                  VType val = s.pop();
                  if(val.matchCT( ((VTyMap)vmp).getTyParameter(),ct)){
                      s.push(vmp);
                      return;
                  }else{
                      errorMsg(9,n.getSymInfo(),"",val,((VTyMap)vmp).getTyParameter());
                      s.push(TypeError.getInstance());
                      return;
                  }
                  
              }else{
                 errorMsg(8,n.getSymInfo(),"",vkey);
                 s.push(TypeError.getInstance());
                 return;
              }
        }
        errorMsg(3,n.getSymInfo(),"");
        s.push(TypeError.getInstance()); 
        return;
    }

    @Override
    public void visit(Mod n) {
        n.getLeft().accept(this);
        VType left = s.peek();
        n.getRight().accept(this);
        VType right = s.peek();
        if(!matchBinOp("MOD", left, right)) {
            errorMsg(11, n.getSymInfo(),"%",left, right);
        }
    }

    @Override
    public void visit(Mult n) {
        n.getLeft().accept(this);
        VType left = s.peek();
        n.getRight().accept(this);
        VType right = s.peek();
        if(!matchBinOp("MUL", left, right)) {
            errorMsg(11, n.getSymInfo(),"++",left, right);
        }
    }

    @Override
    public void visit(Not n) {
        n.getExpr().accept(this);
        VType e = s.peek();
        if(!matchBinOp("NOT", e, null)) {
            errorMsg(11, n.getSymInfo(),"!",e);
        }
    }

    @Override
    public void visit(NotEq n) {
        n.getLeft().accept(this);
        VType left = s.peek();
        n.getRight().accept(this);
        VType right = s.peek();
        if(!matchBinOp("NE", left, right)) {
            errorMsg(13, n.getSymInfo(),"!=",left, right);
        }
    }

    @Override
    public void visit(Or n) {
        n.getLeft().accept(this);
        VType left = s.peek();
        n.getRight().accept(this);
        VType right = s.peek();
        if(!matchBinOp("OR", left, right)) {
            errorMsg(11, n.getSymInfo(),"||",left, right);
        }
    }

    @Override
    public void visit(Sub n) {
        n.getLeft().accept(this);
        VType left = s.peek();
        n.getRight().accept(this);
        VType right = s.peek();
        if(!matchBinOp("SUB", left, right)) {
            errorMsg(11, n.getSymInfo(),"-",left, right);
        }
    }

    @Override
    public void visit(UMinus n) {
       n.getExpr().accept(this);
       VType e = s.peek();
       if(e.matchCT(VTyInt.getInstance(),ct) ){ return; }
       if(e.matchCT(VTyFloat.getInstance(),ct) ){return;}
       errorMsg(2, n.getSymInfo(),"-",e);
    }

        @Override
    public void visit(MetaStrLit n) { 
         n.getExpr().accept(this);
         if( !s.peek().matchCT(VTyString.getInstance(), ct )){
             errorMsg(27,n.getSymInfo(),"String literal", s.peek());
             s.push(TypeError.getInstance());
         }
         s.push(VTyMetaExpr.getInstance()); 
    }

    @Override
    public void visit(MetaTyBool n) { s.push(VTyMetaType.getInstance()); }

    @Override
    public void visit(MetaTyChar n) { s.push(VTyMetaType.getInstance()); }

    @Override
    public void visit(MetaTyFloat n) {s.push(VTyMetaType.getInstance());}

    @Override
    public void visit(MetaTyGrammar n) {s.push(VTyMetaType.getInstance()); }

    @Override
    public void visit(MetaTyInt n) {s.push(VTyMetaType.getInstance());}

    @Override
    public void visit(MetaTyLang n) { s.push(VTyMetaType.getInstance()); }

    @Override
    public void visit(MetaTyMap n) { s.push(VTyMetaType.getInstance()); }

    @Override
    public void visit(MetaTyMeta n) {
        // TODO Auto-generated method stub
    }
    @Override
    public void visit(MetaTyString n) {s.push(VTyMetaExpr.getInstance()); }

    
    @Override
    public void visit(MetaAdd n) {
        n.getLeft().accept(this);
        n.getRight().accept(this);
        VType tyr = s.pop();
        VType tye = s.pop();
        if(! checkBinaryMetaOperator(tye,tyr)){
             errorMsg(27,n.getSymInfo(),"(| + |)", tye,tyr);
        }
    }

    @Override
    public void visit(MetaAnd n) {
        n.getLeft().accept(this);
        n.getRight().accept(this);
        VType tyr = s.pop();
        VType tye = s.pop();
        if(! checkBinaryMetaOperator(tye,tyr)){
             errorMsg(27,n.getSymInfo(),"(| & |)", tye,tyr);
        }
        
    }

    @Override
    public void visit(MetaCompose n) {
        n.getLeft().accept(this);
        n.getRight().accept(this);
        VType tyr = s.pop();
        VType tye = s.pop();
        if(! checkBinaryMetaOperator(tye,tyr)){
             errorMsg(27,n.getSymInfo(),"<<", tye,tyr);
        }
    }

    @Override
    public void visit(MetaConcat n) {
        n.getLeft().accept(this);
        n.getRight().accept(this);
        VType tyr = s.pop();
        VType tye = s.pop();
        if(! checkBinaryMetaOperator(tye,tyr)){
             errorMsg(27,n.getSymInfo(),"(| ++ |)", tye,tyr);
        }
    }

    @Override
    public void visit(MetaDiv n) {
        n.getLeft().accept(this);
        n.getRight().accept(this);
        VType tyr = s.pop();
        VType tye = s.pop();
        if(! checkBinaryMetaOperator(tye,tyr)){
             errorMsg(27,n.getSymInfo(),"(| / |)", tye,tyr);
        }
    }

    @Override
    public void visit(MetaEquals n) {
        n.getLeft().accept(this);
        n.getRight().accept(this);
        VType tyr = s.pop();
        VType tye = s.pop();
        if(! checkBinaryMetaOperator(tye,tyr)){
             errorMsg(27,n.getSymInfo(),"(| == |)", tye,tyr);
        }
    }

    @Override
    public void visit(MetaGreater n) {
        n.getLeft().accept(this);
        n.getRight().accept(this);
        VType tyr = s.pop();
        VType tye = s.pop();
        if(! checkBinaryMetaOperator(tye,tyr)){
             errorMsg(27,n.getSymInfo(),"(| > |)", tye,tyr);
        }
    }

    @Override
    public void visit(MetaGreaterEq n) {
        n.getLeft().accept(this);
        n.getRight().accept(this);
        VType tyr = s.pop();
        VType tye = s.pop();
        if(! checkBinaryMetaOperator(tye,tyr)){
             errorMsg(27,n.getSymInfo(),"(| >= |)", tye,tyr);
        }
    }

    @Override
    public void visit(MetaLess n) {
        n.getLeft().accept(this);
        n.getRight().accept(this);
        VType tyr = s.pop();
        VType tye = s.pop();
        if(! checkBinaryMetaOperator(tye,tyr)){
             errorMsg(27,n.getSymInfo(),"(| < |)", tye,tyr);
        }
    }

    @Override
    public void visit(MetaLessEq n) {
        n.getLeft().accept(this);
        n.getRight().accept(this);
        VType tyr = s.pop();
        VType tye = s.pop();
        if(! checkBinaryMetaOperator(tye,tyr)){
             errorMsg(27,n.getSymInfo(),"(| <= |)", tye,tyr);
        }
    }

    @Override
    public void visit(MetaMapAcces n) {
        n.getMap().accept(this);
        n.getIndex().accept(this);
        VType tyr = s.pop();
        VType tye = s.pop();
        if(! checkBinaryMetaOperator(tye,tyr)){
             errorMsg(27,n.getSymInfo()," metamap access operation", tye,tyr);
        }
    }

    @Override
    public void visit(MetaMapExtension n) {
        n.getMap().accept(this);
        n.getKey().accept(this);
        n.getValue().accept(this);
        VType tyv = s.pop();
        VType tyk = s.pop();
        VType tym = s.pop();
        if(tym instanceof VTyMetaExpr){
             if(tyk.matchCT(VTyMetaExpr.getInstance(), ct)){
                 if(tyv.matchCT(VTyMetaExpr.getInstance(), ct)){
                    s.push(VTyMetaExpr.getInstance());
                    return;
                 }
                 errorMsg(27,n.getSymInfo()," metamap extension value", tyv);
                 s.push(TypeError.getInstance());
                 return;
             }
             errorMsg(27,n.getSymInfo()," metamap extension key", tyk);
             s.push(TypeError.getInstance());
             return;
        }
        errorMsg(27,n.getSymInfo()," metamap extension map", tym);
        s.push(TypeError.getInstance());
    }

    @Override
    public void visit(MetaMod n) {
        n.getLeft().accept(this);
        n.getRight().accept(this);
        VType tyr = s.pop();
        VType tye = s.pop();
        if(! checkBinaryMetaOperator(tye,tyr)){
             errorMsg(27,n.getSymInfo(),"(| % |)", tye,tyr);
        }
    }

    @Override
    public void visit(MetaMult n) {
        n.getLeft().accept(this);
        n.getRight().accept(this);
        VType tyr = s.pop();
        VType tye = s.pop();
        if(! checkBinaryMetaOperator(tye,tyr)){
             errorMsg(27,n.getSymInfo(),"(| * |)", tye,tyr);
        }
    }

    @Override
    public void visit(MetaNot n) {
        n.getPegExpr().accept(this);
        if(! s.peek().matchCT(VTyMetaExpr.getInstance(),ct)){
            errorMsg(27,n.getSymInfo(),"(| ! |)", s.peek());
            s.pop();
            s.push(TypeError.getInstance());
        }
    }

    @Override
    public void visit(MetaNotEq n) {
        n.getLeft().accept(this);
        n.getRight().accept(this);
        VType tyr = s.pop();
        VType tye = s.pop();
        if(! checkBinaryMetaOperator(tye,tyr)){
             errorMsg(27,n.getSymInfo(),"(| != |)", tye,tyr);
        }
    }

    @Override
    public void visit(MetaOr n) {
        n.getLeft().accept(this);
        n.getRight().accept(this);
        VType tyr = s.pop();
        VType tye = s.pop();
        if(! checkBinaryMetaOperator(tye,tyr)){
             errorMsg(27,n.getSymInfo(),"(| || |)", tye,tyr);
        }
    }

    @Override
    public void visit(MetaSub n) {
        n.getLeft().accept(this);
        n.getRight().accept(this);
        VType tyr = s.pop();
        VType tye = s.pop();
        if(! checkBinaryMetaOperator(tye,tyr)){
             errorMsg(27,n.getSymInfo(),"(| - |)", tye,tyr);
        }
    }

    @Override
    public void visit(MetaUMinus n) {
        n.getExpr().accept(this);
        if(! s.peek().matchCT(VTyMetaExpr.getInstance(),ct)){
            s.pop();
            s.push(TypeError.getInstance());
        }

    }

    @Override
    public void visit(AndPEG n) {
        n.getPegExp().accept(this);
    }

    @Override
    public void visit(AnyPEG n) { }

    @Override
    public void visit(BindPEG n) {
        n.getExpr().accept(this);
        VType ty = gamma.get(n.getAttribute().getName());
        if(ty != null){
          if(!ty.matchCT(VTyString.getInstance(),ct ) ){
              errorMsg(15, n.getSymInfo(),"=",s.peek());
          }
        }else{
           gamma.add(n.getAttribute().getName(),VTyString.getInstance() );
        }
    }

    @Override
    public void visit(RangePEG n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(ChoicePEG n) {
        n.getLeftPeg().accept(this);
        n.getRightPeg().accept(this);
    }

    @Override
    public void visit(ConstraintPEG n) {
        n.getExpr().accept(this);
        VType t = s.pop();
        if(!t.matchCT(VTyBool.getInstance(),ct)){
            errorMsg(19, n.getSymInfo(),"",t);
        }
    }

    @Override
    public void visit(KleenePEG n) {
        n.getPegExp().accept(this);
    }

    @Override
    public void visit(LambdaPEG n) {

    }

    @Override
    public void visit(LitPEG n) {

    }

    @Override
    public void visit(NonterminalPEG n) {
        NTInfo nt = global.get(n.getName());
        VType ty;
        if(nt != null) {
            NTType t =  nt.getSig();
            int syn, inh;
            inh = t.getNumInherited();
            syn = t.getNumSintetized();
            List<Expr>a = n.getArgs();
            int i, k=0;
            for(i =0; i <inh && i<a.size(); i++) {
                a.get(k++).accept(this);
                ty = s.pop();
                if(!t.getParamAt(i).matchCT(ty, ct)) {
                    errorMsg(20, n.getSymInfo(), n.getName(),ty);
                }
            }
            addVar = true;
            for(i = 0; i<syn && k<a.size(); i++) {
                Expr e = a.get(k++);
                e.accept(this);
                if(!(e instanceof Attribute)) {
                    errorMsg(21, n.getSymInfo());
                }
                ty = s.pop();
                if(!t.getReturnAt(i).matchCT(ty, ct)) {
                    errorMsg(22, n.getSymInfo(),n.getName(),ty,t.getReturnAt(i));
                }
            }
            addVar = false;
            if(a.size() != inh + syn) {
                errorMsg(22, n.getSymInfo(), n.getName(),TypeError.getInstance());
            }
        }
        else {
            errorMsg(24, n.getSymInfo(),n.getName(),TypeError.getInstance());
        }
    }

    @Override
    public void visit(NotPEG n) {
        n.getPegExp().accept(this);
    }

    @Override
    public void visit(OptionalPEG n) {
        n.getPegExp().accept(this);
    }

    @Override
    public void visit(PKleene n) {
        n.getPegExp().accept(this);
    }

    @Override
    public void visit(RulePEG n) {        
        gamma = global.get(n.getRuleName()).getLocals();
        n.getPeg().accept(this);
        VType ty;
        VType returns [] = new VType [n.getSyn().size()];
        NTType nt = global.get(n.getRuleName()).getSig();
        int i = 0;
        for(Expr e: n.getSyn()) {
            e.accept(this);
            returns[i] = s.peek();
            ty = s.pop();
            if(!nt.getReturnAt(i++).matchCT(ty, ct)) {
                errorMsg(25, n.getSymInfo(),n.getRuleName(),ty);
            }
        }    
    }

    @Override
    public void visit(SeqPEG n) {
        for(APEG pegs: n.getPegs()) {
            pegs.accept(this);
        }
    }

    @Override
    public void visit(UpdatePEG n) {
        VType vt,ty;
        for(Pair<Attribute, Expr>assigs : n.getAssigs()) {
            String name = assigs.getFirst().getName();
            vt = gamma.get(name);
            assigs.getSecond().accept(this);
            if(vt!= null) {
                ty = s.pop();
                if(!vt.matchCT(ty, ct)) {
                    errorMsg(16, n.getSymInfo(),"=" ,vt,ty);
                }
            }
            else {
                gamma.add(name, s.pop());
            }
        }
    }

    @Override
    public void visit(TyBool n) {
        s.push(VTyBool.getInstance());
    }

    @Override
    public void visit(TyChar n) {
        s.push(VTyChar.getInstance());
    }

    @Override
    public void visit(TyFloat n) {
        s.push(VTyFloat.getInstance());
    }

    @Override
    public void visit(TyGrammar n) {
        s.push(VTyGrammar.getInstance());
    }

    @Override
    public void visit(TyInt n) {
        s.push(VTyInt.getInstance());
    }

    @Override
    public void visit(TyLang n) {
        s.push(VTyLang.getInstance());
    }

    @Override
    public void visit(TyMap n) {
        s.push(new VTyMap(s.pop()));
    }

    @Override
    public void visit(TyMeta n) {
        // TODO Auto-generated method stub
    }

    @Override
    public void visit(TyMetaExpr n) {
        // TODO Auto-generated method stub
    }

    @Override
    public void visit(TyMetaPeg n) {
        // TODO Auto-generated method stub
    }

    @Override
    public void visit(TyString n) {
        s.push(VTyString.getInstance());
    }

    @Override
    public void visit(Grammar n) {
        for(RulePEG rule: n.getRules()) {
            gamma = new Environment<String, VType>();
            VType inhs[] = new VType[rule.getInh().size()];
            int j=0;
            for(Pair<Type, String>inh : rule.getInh()) {
                inh.getFirst().accept(this);
                inhs[j++] = s.peek();
                gamma.add(inh.getSecond(), s.pop());
            }
            VType returns [] = new VType [rule.getSyn().size()];
            int i;
            for(i=0; i< rule.getSyn().size(); i++) {
                returns[i] = pool.newVar();
            }
            NTType rules = new NTType(inhs, returns);
            global.add(rule.getRuleName(), new NTInfo(rules, gamma));
        }
        for(RulePEG r : n.getRules()) {
            r.accept(this);
        }

        // System.out.println(global.toString());
        //if{debug){ System.out.println(ct.toString());}
        error.addAll(ct.resolveUnify(opTable));
        global.replace((k,v) -> {v.simplify(); return v;} );
        if(debug){
           System.out.println(global.toString());
           System.out.println(ct.toString());
        }
    }
}
