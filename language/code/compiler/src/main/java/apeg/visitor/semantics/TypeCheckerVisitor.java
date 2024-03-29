package apeg.visitor.semantics;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;


import apeg.util.Environment;
import apeg.util.Pair;

import apeg.util.Environment;
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
    private List<Pair<String, VType>>error;
    private String errorMessage;
    private VarPool pool;
    private Environment<String,ArrayList<NTType>> opTable;
    private CTM ct;
    private boolean addVar;

    public TypeCheckerVisitor() {
        s = new Stack<VType>();
        gamma = new Environment<String, VType>();
        global = new Environment<String, NTInfo>();
        pool = VarPool.getInstance();
        error = new ArrayList<Pair<String, VType>>();
        ct = new CTM();
        addVar = false;
        opTable = OperatorTables.mkArithmeticEnv();
    }
    
    public List<Pair<String, VType>> getError(){
        return error;
    }
    
    public Environment<String,NTInfo> getEnv(){
        return global;
    }

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
            errorMessage = "Error06 at: " + n.getSymInfo().getLine() + ", " + n.getSymInfo().getColumn();
            System.out.println(errorMessage);
            error.add(new Pair<String, VType>(errorMessage,TypeError.getInstance() ));
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
            errorMessage = "Error07 at: " + n.getSymInfo().getLine() + "," + n.getSymInfo().getColumn();
            System.out.println(errorMessage);
            error.add(new Pair<String, VType>(errorMessage,TypeError.getInstance() ));
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
           errorMessage = "Error at: " + n.getSymInfo().getLine() + "," + n.getSymInfo().getColumn() + " Empty map literal is not allowed.";
           System.out.println(errorMessage);
           error.add(new Pair<String, VType>(errorMessage,TypeError.getInstance() ));
           s.push(TypeError.getInstance());
           return;
        }
        boolean b = true;
        assocs[0].getFirst().accept(this);
        assocs[0].getSecond().accept(this);
        tyval = s.pop();
        if(! s.pop().match(VTyString.getInstance()) ){
           errorMessage = "Error at: " + assocs[0].getFirst().getSymInfo().getLine() + "," + assocs[0].getFirst().getSymInfo().getColumn() + " Map index must be of type string.";
           System.out.println(errorMessage);
           error.add(new Pair<String, VType>(errorMessage,TypeError.getInstance() ));
           s.push(TypeError.getInstance());
        }
        for(Pair<Expr,Expr> p : assocs){
           p.getFirst().accept(this);
           p.getSecond().accept(this);
           tyaux = s.pop();
           tyidx = s.pop();
           if(!tyidx.match(VTyString.getInstance()) ){
               errorMessage = "Error at: " + assocs[0].getFirst().getSymInfo().getLine() + "," + assocs[0].getFirst().getSymInfo().getColumn() + " Map index must be of type string.";
               System.out.println(errorMessage);
               error.add(new Pair<String, VType>(errorMessage,TypeError.getInstance() ));
               s.push(TypeError.getInstance());
           }
           b = b && tyidx.match(VTyString.getInstance()) && (tyaux.match(tyval));
        }
    }

    @Override
    public void visit(StrLit n) {
        s.push(VTyString.getInstance());
    }

    @Override
    public void visit(MetaAndPEG n) {
        // TODO Auto-generated method stub
    }

    @Override
    public void visit(MetaAnyPEG n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(MetaAttribute n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(MetaBindPEG n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(MetaBoolLit n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(MetaCharLit n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(MetaRangePEG n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(MetaChoicePEG n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(MetaConstraintPEG n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(MetaFloatLit n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(MetaIntLit n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(MetaKleenePEG n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(MetaLitPEG n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(MetaMapLit n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(MetaNonterminalPEG n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(MetaNotPEG n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(MetaOptionalPEG n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(MetaPKleene n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(MetaRulePEG n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(MetaSeqPEG n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(MetaStrLit n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(MetaTyBool n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(MetaTyChar n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(MetaTyFloat n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(MetaTyGrammar n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(MetaTyInt n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(MetaTyLang n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(MetaTyMap n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(MetaTyMeta n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(MetaTyString n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(MetaUpdatePEG n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(MetaVar n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(Add n) {
        n.getLeft().accept(this);
        VType left = s.peek();
        n.getRight().accept(this);
        VType right = s.peek();
        if(!matchBinOp("ADD", left, right)) {
            errorMessage = "Error08 at: " + n.getSymInfo().getLine() + ", " + n.getSymInfo().getColumn();
            System.out.println(errorMessage);
            error.add(new Pair<String, VType>(errorMessage,TypeError.getInstance() ));    
        }

    }


    @Override
    public void visit(And n) {
        n.getLeft().accept(this);
        VType left = s.peek();
        n.getRight().accept(this);
        VType right = s.peek();
        if(!matchBinOp("AND", left, right)) {
            errorMessage = "Error09 at: " + n.getSymInfo().getLine() + ", " + n.getSymInfo().getColumn();
            System.out.println(errorMessage);
            error.add(new Pair<String, VType>(errorMessage, TypeError.getInstance()));    
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
                s.push(TypeError.getInstance());
                errorMessage = "Error10 at: " + n.getSymInfo().getLine() + n.getSymInfo().getColumn();
                System.out.println(errorMessage);
                error.add(new Pair<String, VType>(errorMessage,TypeError.getInstance() ));
            }
        }
    }


    @Override
    public void visit(Concat n) {
        n.getLeft().accept(this);
        VType left = s.peek();
        n.getRight().accept(this);
        VType right = s.peek();
        if(left instanceof VTyVar || right instanceof VTyVar) {

        }
        if(left == right) {
            s.push(left);
        }
        else {

            s.push(TypeError.getInstance());

            errorMessage = "Error11 at: " + n.getSymInfo().getLine() + n.getSymInfo().getColumn();
            System.out.println(errorMessage);
            error.add(new Pair<String, VType>(errorMessage, TypeError.getInstance() ));

        }
    }

    @Override
    public void visit(Div n) {
        n.getLeft().accept(this);
        VType left = s.peek();
        n.getRight().accept(this);
        VType right = s.peek();
        if(!matchBinOp("DIV", left, right)) {
            errorMessage = "Error12 at: " + n.getSymInfo().getLine() + ", " + n.getSymInfo().getColumn();
            System.out.println(errorMessage);
            error.add(new Pair<String, VType>(errorMessage, TypeError.getInstance() ));    
        }
    }

    @Override
    public void visit(Equals n) {
        n.getLeft().accept(this);
        VType left = s.peek();
        n.getRight().accept(this);
        VType right = s.peek();
        if(!matchBinOp("EQ", left, right)) {
            errorMessage = "Error13 at: " + n.getSymInfo().getLine() + ", " + n.getSymInfo().getColumn();
            System.out.println(errorMessage);
            error.add(new Pair<String, VType>(errorMessage,TypeError.getInstance() ));    
        }

    }

    @Override
    public void visit(Greater n) {
        n.getLeft().accept(this);
        VType left = s.peek();
        n.getRight().accept(this);
        VType right = s.peek();
        if(!matchBinOp("GT", left, right)) {
            errorMessage = "Error14 at: " + n.getSymInfo().getLine() + ", " + n.getSymInfo().getColumn();
            System.out.println(errorMessage);
            error.add(new Pair<String, VType>(errorMessage, TypeError.getInstance() ));    
        }
    }

    @Override
    public void visit(GreaterEq n) {
        n.getLeft().accept(this);
        VType left = s.peek();
        n.getRight().accept(this);
        VType right = s.peek();
        if(!matchBinOp("GE", left, right)) {
            errorMessage = "Error15 at: " + n.getSymInfo().getLine() + ", " + n.getSymInfo().getColumn();
            System.out.println(errorMessage);
            error.add(new Pair<String, VType>(errorMessage, TypeError.getInstance() ));    
        }

    }

    @Override
    public void visit(Less n) {
        n.getLeft().accept(this);
        VType left = s.peek();
        n.getRight().accept(this);
        VType right = s.peek();
        if(!matchBinOp("LT", left, right)) {
            errorMessage = "Error at: " + n.getSymInfo().getLine() + ", " + n.getSymInfo().getColumn();
            System.out.println(errorMessage);
            error.add(new Pair<String, VType>(errorMessage, TypeError.getInstance() ));    
        }

    }

    @Override
    public void visit(LessEq n) {


        n.getLeft().accept(this);
        VType left = s.peek();

        n.getRight().accept(this);
        VType right = s.peek();

        if(!matchBinOp("LE", left, right)) {

            

            errorMessage = "Error at: " + n.getSymInfo().getLine() + ", " + n.getSymInfo().getColumn();
            System.out.println(errorMessage);
            error.add(new Pair<String, VType>(errorMessage, TypeError.getInstance()));    
        }
    }

    @Override
    public void visit(MapAcces n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(MapExtension n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(Mod n) {

        n.getLeft().accept(this);
        VType left = s.peek();

        n.getRight().accept(this);
        VType right = s.peek();

        if(!matchBinOp("MOD", left, right)) {
            errorMessage = "Error at: " + n.getSymInfo().getLine() + ", " + n.getSymInfo().getColumn();
            System.out.println(errorMessage);
            error.add(new Pair<String, VType>(errorMessage, TypeError.getInstance() ));    
        }
    }

    @Override
    public void visit(Mult n) {



        n.getLeft().accept(this);
        VType left = s.peek();

        n.getRight().accept(this);
        VType right = s.peek();

        if(!matchBinOp("MUL", left, right)) {

        

            errorMessage = "Error at: " + n.getSymInfo().getLine() + ", " + n.getSymInfo().getColumn();
            System.out.println(errorMessage);
            error.add(new Pair<String, VType>(errorMessage, TypeError.getInstance() ));    
        }

    }

    @Override
    public void visit(Not n) {


        n.getExpr().accept(this);
        VType e = s.peek();

        if(!matchBinOp("NOT", e, null)) {
            errorMessage = "Error at: " + n.getSymInfo().getLine() + ", " + n.getSymInfo().getColumn();
            System.out.println(errorMessage);
            error.add(new Pair<String, VType>(errorMessage, TypeError.getInstance() ));    
        }
    }

    @Override
    public void visit(NotEq n) {
    

        n.getLeft().accept(this);
        VType left = s.peek();

        n.getRight().accept(this);
        VType right = s.peek();

        if(!matchBinOp("NE", left, right)) {
            errorMessage = "Error at: " + n.getSymInfo().getLine() + ", " + n.getSymInfo().getColumn();
            System.out.println(errorMessage);
            error.add(new Pair<String, VType>(errorMessage, TypeError.getInstance() ));    
        }
    }

    @Override
    public void visit(Or n) {
        n.getLeft().accept(this);
        VType left = s.peek();
        n.getRight().accept(this);
        VType right = s.peek();
        if(!matchBinOp("OR", left, right)) {
            errorMessage = "Error at: " + n.getSymInfo().getLine() + ", " + n.getSymInfo().getColumn();
            System.out.println(errorMessage);
            error.add(new Pair<String, VType>(errorMessage, TypeError.getInstance() ));    
        }
    }

    @Override
    public void visit(Sub n) {
        n.getLeft().accept(this);
        VType left = s.peek();
        n.getRight().accept(this);
        VType right = s.peek();
        if(!matchBinOp("SUB", left, right)) {
            errorMessage = "Error at: " + n.getSymInfo().getLine() + ", " + n.getSymInfo().getColumn();
            System.out.println(errorMessage);
            error.add(new Pair<String, VType>(errorMessage, TypeError.getInstance() ));    
        }
    }


    @Override
    public void visit(UMinus n) {
        n.getExpr().accept(this);
        VType e = s.peek();
       if(!matchBinOp("MINUS", e, null)) {
            errorMessage = "Error at: " + n.getSymInfo().getLine() + ", " + n.getSymInfo().getColumn();
            System.out.println(errorMessage);
            error.add(new Pair<String, VType>(errorMessage, TypeError.getInstance() ));    
        }
    }

    @Override
    public void visit(MetaAdd n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(MetaAnd n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(MetaCompose n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(MetaConcat n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(MetaDiv n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(MetaEquals n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(MetaGreater n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(MetaGreaterEq n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(MetaLess n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(MetaLessEq n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(MetaMapAcces n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(MetaMapExtension n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(MetaMod n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(MetaMult n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(MetaNot n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(MetaNotEq n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(MetaOr n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(MetaSub n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(MetaUMinus n) {
        // TODO Auto-generated method stub

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
              errorMessage = "Error at: " + n.getSymInfo().getLine() + ", " + n.getSymInfo().getColumn() + ": Incompatible bind type at bind " + n.getAttribute().getName();
              System.out.println(errorMessage);
              error.add(new Pair<String, VType>(errorMessage, TypeError.getInstance() )); 
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

        if(nt != null) {

            NTType t =  nt.getSig();

            int syn, inh;
            inh = t.getNumInherited();
            syn = t.getNumSintetized();

            List<Expr>a = n.getArgs();

            int i, k=0;
            for(i =0; i <inh && i<a.size(); i++) {

                a.get(k++).accept(this);
                if(!t.getParamAt(i).matchCT(s.pop(), ct)) {

                    errorMessage = "Error01 at: " + n.getSymInfo().getLine() + ", " + n.getSymInfo().getColumn();
                    System.out.println(errorMessage);
                    error.add(new Pair<String, VType>(errorMessage, TypeError.getInstance() ));    
                }
            }

            addVar = true;
            for(i = 0; i<syn && k<a.size(); i++) {


                Expr e = a.get(k++);

                e.accept(this);


                if(!(e instanceof Attribute)) {

                    errorMessage = "Error02 at: " + n.getSymInfo().getLine() + ", " + n.getSymInfo().getColumn();
                    System.out.println(errorMessage);
                    error.add(new Pair<String, VType>(errorMessage, TypeError.getInstance() ));    
                }
                if(!t.getReturnAt(i).matchCT(s.pop(), ct)) {

                    errorMessage = "Error03 at: " + n.getSymInfo().getLine() + ", " + n.getSymInfo().getColumn();
                    System.out.println(errorMessage);
                    error.add(new Pair<String, VType>(errorMessage, TypeError.getInstance() ));    
                }

            }
            addVar = false;
            if(a.size() != inh + syn) {

                errorMessage = "Error04 at: " + n.getSymInfo().getLine() + ", " + n.getSymInfo().getColumn();
                System.out.println(errorMessage);
                error.add(new Pair<String, VType>(errorMessage, TypeError.getInstance() ));    
            }
        }
        else {

            errorMessage = "Error05 at: " + n.getSymInfo().getLine() + ", " + n.getSymInfo().getColumn();
            System.out.println(errorMessage);
            error.add(new Pair<String, VType>(errorMessage, TypeError.getInstance() ));    

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

        VType returns [] = new VType [n.getSyn().size()];
        NTType nt = global.get(n.getRuleName()).getSig();
        
        int i = 0;

        for(Expr e: n.getSyn()) {

            e.accept(this);
            
            
            returns[i] = s.peek();
            if(!nt.getReturnAt(i++).matchCT(s.pop(), ct)) {
                
                errorMessage = "Error at : " + n.getSymInfo().getLine() + ", " + n.getSymInfo().getColumn() + "k";
                System.out.println(errorMessage);
                error.add(new Pair<String, VType>(errorMessage, TypeError.getInstance() ));
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

        VType vt;

        for(Pair<Attribute, Expr>assigs : n.getAssigs()) {

            String name = assigs.getFirst().getName();
            vt = gamma.get(name);

            assigs.getSecond().accept(this);

            if(vt!= null) {

                if(!vt.matchCT(s.pop(), ct)) {

                    errorMessage = "Error at: " + n.getSymInfo().getLine() + ", " + n.getSymInfo().getColumn();
                    System.out.println(errorMessage);
                    error.add(new Pair<String, VType>(errorMessage, TypeError.getInstance() ));

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

                
        System.out.println(global.toString());
        System.out.println(ct.toString());
        error.addAll(ct.resolveUnify(opTable));
        
        
    
        System.out.println(global.toString());
        System.out.println(ct.toString());

    }
}
