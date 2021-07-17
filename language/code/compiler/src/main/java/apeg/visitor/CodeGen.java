package apeg.visitor;

import apeg.ast.expr.operators.*;
import apeg.ast.expr.*;
import apeg.ast.rules.*;
import apeg.ast.types.*;
import apeg.ast.*;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;
import apeg.util.path.Path;
import java.io.FileWriter;
import apeg.util.*;
import apeg.util.lang.*;
import apeg.util.path.*;
import apeg.util.Environment;


public class CodeGen extends Visitor{
   private STGroup groupTemplate;
   private ST template;
   private String path, currentRule;
   Environment<String, String> ntTable;

   private ST peg_expr, expr, assign;

//GroupPegNode
//PlusPegNode
//StarPegNode peg
//AssignmentNode assign
//BinaryExprNode exp

    @Override
public void visit(Attribute n){}
    @Override
public void visit(AttributeGrammar n){}
    @Override
public void visit(BoolLit n){
  ST aux_peg = groupTemplate.getInstanceOf("lit");
   aux_peg.add("expr",n.getValue());
   this.expr=aux_peg;
}
    @Override
public void visit(CharLit n){
  ST aux_peg = groupTemplate.getInstanceOf("lit");
   aux_peg.add("expr",n.getValue());
   this.expr=aux_peg;
}
    @Override
public void visit(FloatLit n){
  ST aux_peg = groupTemplate.getInstanceOf("lit");
   aux_peg.add("expr",n.getValue());
   this.expr=aux_peg;
}
    @Override
public void visit(IntLit n){
  ST aux_peg = groupTemplate.getInstanceOf("lit");
   aux_peg.add("expr",n.getValue());
   this.expr=aux_peg;
}
    @Override
public void visit(MapLit n){

}
    @Override
public void visit(StrLit n){
  ST aux_peg = groupTemplate.getInstanceOf("lit");
   aux_peg.add("expr",n.getValue());
   this.expr=aux_peg;
}

    // Meta Expressions
    @Override
public void visit(MetaAndPEG n){}
    @Override
public void visit(MetaAnyPEG n){}
    @Override
public void visit(MetaAttribute n){}
    @Override
public void visit(MetaBindPEG n){}
    @Override
public void visit(MetaBoolLit n){}
    @Override
public void visit(MetaCharLit n){}
    @Override
public void visit(MetaChoiceList n){}
    @Override
public void visit(MetaChoicePEG n){}
    @Override
public void visit(MetaConstraintPEG n){}
    @Override
public void visit(MetaFloatLit n){}
    @Override
public void visit(MetaIntLit n){}
    @Override
public void visit(MetaKleenePEG n){}
    @Override
public void visit(MetaLitPEG n){}
    @Override
public void visit(MetaMapLit n){}
    @Override
public void visit(MetaNonterminalPEG n){}
    @Override
public void visit(MetaNotPEG n){}
    @Override
public void visit(MetaOptionalPEG n){}
    @Override
public void visit(MetaPKleene n){}
    @Override
public void visit(MetaRulePEG n){}
    @Override
public void visit(MetaSeqPEG n){}
    @Override
public void visit(MetaStrLit n){}
    @Override
public void visit(MetaTyBool n){}
    @Override
public void visit(MetaTyChar n){}
    @Override
public void visit(MetaTyFloat n){}
    @Override
public void visit(MetaTyGrammar n){}
    @Override
public void visit(MetaTyInt n){}
    @Override
public void visit(MetaTyLang n){}
    @Override
public void visit(MetaTyMap n){}
    @Override
public void visit(MetaTyMeta n){}
    @Override
public void visit(MetaTyString n){}
    @Override
public void visit(MetaUpdatePEG n){}
    @Override
public void visit(MetaVar n){}

    // Operators
    @Override
public void visit(Add n){}
    @Override
public void visit(And n){
  // set the current expression template
   ST aux_expr = groupTemplate.getInstanceOf("and");
   // visit left expression
   n.getLeft().accept(this);
   // set the left expression attribute
   aux_expr.add("left_expr", this.expr);
   // visit right expression
   n.getRight().accept(this);
   // set the right expression attribute
   aux_expr.add("right_expr", this.expr);
   // set the current expression
   this.expr = aux_expr;
}
    @Override
public void visit(Compose n){}
    @Override
public void visit(Concat n){}
    @Override
public void visit(Div n){}
    @Override
public void visit(Equals n){}
    @Override
public void visit(Greater n){}
    @Override
public void visit(GreaterEq n){}
    @Override
public void visit(Less n){}
    @Override
public void visit(LessEq n){}
    @Override
public void visit(MapAcces n){}
    @Override
public void visit(MapExtension n){}
    @Override
public void visit(Mod n){}
    @Override
public void visit(Mult n){}
    @Override
public void visit(Not n){}
    @Override
public void visit(NotEq n){}
    @Override
public void visit(Or n){}
    @Override
public void visit(Sub n){}
    @Override
public void visit(UMinus n){}

    // Meta operators
    @Override
public void visit(MetaAdd n){}
    @Override
public void visit(MetaAnd n){}
    @Override
public void visit(MetaCompose n){}
    @Override
public void visit(MetaConcat n){}
    @Override
public void visit(MetaDiv n){}
    @Override
public void visit(MetaEquals n){}
    @Override
public void visit(MetaGreater n){}
    @Override
public void visit(MetaGreaterEq n){}
    @Override
public void visit(MetaLess n){}
    @Override
public void visit(MetaLessEq n){}
    @Override
public void visit(MetaMapAcces n){}
    @Override
public void visit(MetaMapExtension n){}
    @Override
public void visit(MetaMod n){}
    @Override
public void visit(MetaMult n){}
    @Override
public void visit(MetaNot n){}
    @Override
public void visit(MetaNotEq n){}
    @Override
public void visit(MetaOr n){}
    @Override
public void visit(MetaSub n){}
    @Override
public void visit(MetaUMinus n){}

    // PEG Expressions
    @Override
public void visit(AndPEG n){}
    @Override
public void visit(AnyPEG n){


   this.expr = groupTemplate.getInstanceOf("any_peg");


}
    @Override
public void visit(BindPEG n){

}
    @Override
public void visit(ChoiceList n){}
    @Override
public void visit(ChoicePEG n){
  ST aux_peg = groupTemplate.getInstanceOf("choice_peg");
   // visit the left parsing expression
   n.getLeftPeg().accept(this);
   // set propriety for the left parsing expression
   aux_peg.add("left_peg", this.expr);
   // visit the right parsing expression
   n.getRightPeg().accept(this);
   // set propriety for the right parsing expression
   aux_peg.add("right_peg", this.expr);
   this.expr = aux_peg;
}
    @Override
public void visit(ConstraintPEG n){}
    @Override
public void visit(KleenePEG n){}
    @Override
public void visit(LambdaPEG n){
this.expr = groupTemplate.getInstanceOf("lambda_peg");
}
    @Override
public void visit(LitPEG n){

  this.expr= groupTemplate.getInstanceOf("match");
    this.expr.add("value", n.getLit());

}
    @Override
public void visit(NonterminalPEG n){
  // set the current expression template
      ST aux_expr = groupTemplate.getInstanceOf("call");
      // visit each function parameter expression
      for(Expr e : n.getArgs()){
        e.accept(this);
        // set the attribute params
        aux_expr.add("params", this.expr);
      }
      // set the function name attributec
      aux_expr.add("name", n.getName());

      // set the current expression
      this.expr = aux_expr;
}
    @Override
public void visit(NotPEG n){
  // set the current parsing expression template
    ST aux_peg = groupTemplate.getInstanceOf("not_peg");
    // visit the parsing expression
    n.getPegExp().accept(this);
    // adding the parsing expression on star template
    aux_peg.add("peg_expr", this.expr);
    this.expr = aux_peg;
}
    @Override
public void visit(OptionalPEG n){

// set the current parsing expression template
  ST aux_peg = groupTemplate.getInstanceOf("optional_peg");
  // visit the parsing expression
  n.getPegExp().accept(this);
  // adding the parsing expression on star template
  aux_peg.add("peg_expr", this.expr);
  this.expr = aux_peg;
}
    @Override
public void visit(PKleene n){}
    @Override
public void visit(RulePEG n){
  /*ST r = groupTemplate.getInstanceOf("rule");
   // setting rule name
   currentRule = n.getRuleName();

   r.add("name", n.getRuleName());

   n.getExpr().accept(this);
   r.add("peg_expr", this.expr); // setting parsing expression propriety

   template.add("rules", r); // adding the rule template on the list of grammar rules*/
}
    @Override
public void visit(SeqPEG n){
/*  ST seq_peg = groupTemplate.getInstanceOf("cont");
    ST aux;
    List l = n.getPegs();
    ListIterator li = l.listIterator(l.size());
    APEG p = li.previous();
    p.accept(this);
    seq_peg.add("expr1",this.expr);
    while(li.hasPrevious()){
      aux = groupTemplate.getInstanceOf("cont");
      p = li.previous();
      p.accept(this);
      aux.add("expr1",this.expr);
      aux.add("expr2",seq_peg);
      seq_peg = aux;
    }
    this.expr = seq_peg;*/
}
    @Override
public void visit(UpdatePEG n){
/*
  ST aux_peg = groupTemplate.getInstanceOf("seq_expr");
 for(AssignmentNode p:n.getAssigs()) {
   p.accept(this);
   aux_peg.add("exprs",this.expr);
 }
 this.expr=aux_peg;
*/}

    // Types
    @Override
public void visit(TyBool n){}
    @Override
public void visit(TyChar n){}
    @Override
public void visit(TyFloat n){}
    @Override
public void visit(TyGrammar n){}
    @Override
public void visit(TyInt n){}
    @Override
public void visit(TyLang n){}
    @Override
public void visit(TyMap n){}
    @Override
public void visit(TyMeta n){}
  @Override
public void visit(TyMetaExpr n){}
    @Override
public void visit(TyMetaPeg n){}
    @Override
public void visit(TyString n){}

    //Others
    @Override
public void visit(Grammar n){}


}
