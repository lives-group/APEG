package apeg.parse.ast.visitor;

import java.util.ListIterator;
import java.util.List;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import apeg.parse.ast.*;
import apeg.util.path.Path;

import java.io.FileWriter;
import java.io.File;

public class StateFullCodeGen  extends FormalVisitor{
    
    private STGroup groupTemplate;
    private ST template;
    private String path;
    
    private ST peg_expr;

    public StateFullCodeGen(Path filePath) {
        groupTemplate = new STGroupFile(filePath.getAbsolutePath());
    }
    
    public StateFullCodeGen(Path filePath, String path) {
        this(filePath);
        this.path = path;
    }
    
     @Override
     public void visit(AndPegNode peg) {
         // set the current parsing expression template
         ST aux_peg = groupTemplate.getInstanceOf("and_peg");
         // visit the parsing expression
         peg.getPeg().accept(this);
         // set propriety for the bind parsing expression
         aux_peg.add("peg_expr", peg_expr);
         // set the current parsing expression
         peg_expr = aux_peg;
     }
 
     @Override
      public void visit(AnyPegNode peg) {
          peg_expr = groupTemplate.getInstanceOf("any_peg");
      }
 
//     @Override
//     public void visit(BindPegNode peg) {
//         // set the current parsing expression template
//         ST aux_peg = groupTemplate.getInstanceOf("bind_peg");
//         // set the variable name propriety
//         aux_peg.add("name", peg.getVariable());
//         // visit the parsing expression
//         peg.getPeg().accept(this);
//         // set propriety for the bind parsing expression
//         aux_peg.add("peg_expr", peg_expr);
//         // set the current parsing expression
//         peg_expr = aux_peg;
//     }


    @Override
    public void visit(ChoicePegNode peg) {
        ST aux_peg = groupTemplate.getInstanceOf("choice_peg");
        // visit the left parsing expression
        peg.getLeftPeg().accept(this);
        // set propriety for the left parsing expression
        aux_peg.add("left_peg", peg_expr);
        // visit the right parsing expression
        peg.getRightPeg().accept(this);
        // set propriety for the right parsing expression
        aux_peg.add("right_peg", peg_expr);
        peg_expr = aux_peg;
    }

//     @Override
//     public void visit(ConstraintPegNode peg) {
//         // set the current parsing expression template
//         peg_expr = groupTemplate.getInstanceOf("constraint_peg");
//         // visit constraint expression
//         peg.getExpr().accept(this);
//         // set expression propriety
//         peg_expr.add("expr", expr);
//     }
// 
//     @Override
//     public void visit(GroupPegNode peg) {
//         peg_expr = groupTemplate.getInstanceOf("group_peg");
//         peg_expr.add("ranges", peg.getRanges());
//     }
/*
    @Override
    public void visit(LambdaPegNode peg) { // ?? 
        peg_expr = groupTemplate.getInstanceOf("lambda_peg");
    }*/

    @Override
    public void visit(LiteralPegNode peg) {
        peg_expr= groupTemplate.getInstanceOf("match");
        peg_expr.add("value", peg.getValue());
    }

    @Override
    public void visit(NonterminalPegNode peg) {
        // set the current parsing expression template
        peg_expr = groupTemplate.getInstanceOf("call");
        peg_expr.add("name", peg.getName());
    }

     @Override
     public void visit(NotPegNode peg) {
         // set the current parsing expression template
         ST aux_peg = groupTemplate.getInstanceOf("not_peg");
         // visit the parsing expression
         peg.getPeg().accept(this);   
         // adding the parsing expression on star template
         aux_peg.add("peg_expr", peg_expr);
         peg_expr = aux_peg;
    }
 
     @Override
     public void visit(OptionalPegNode peg) {
         // set the current parsing expression template
         ST aux_peg = groupTemplate.getInstanceOf("optional_peg");
         // visit the parsing expression
         peg.getPeg().accept(this);
         // adding the parsing expression on star template
         aux_peg.add("peg_expr", peg_expr);
         peg_expr = aux_peg;
     }
 
    @Override
     public void visit(PlusPegNode peg) {
         // set the current parsing expression template
         ST aux_peg = groupTemplate.getInstanceOf("plus_peg");
         // visit the parsing expression
         peg.getPeg().accept(this);
         // adding the parsing expression on star template
         aux_peg.add("peg_expr", peg_expr);
         peg_expr = aux_peg;
     }

    @Override
    public void visit(SequencePegNode peg) {
        ST seq_peg = groupTemplate.getInstanceOf("cont");
        ST aux;
        List l = peg.getPegs();
        ListIterator<PegNode> li = l.listIterator(l.size());        
        PegNode p = li.previous();
        p.accept(this);
        seq_peg.add("expr1",peg_expr);
        while(li.hasPrevious()){
            aux = groupTemplate.getInstanceOf("cont");
            p = li.previous();
            p.accept(this);
            aux.add("expr1",peg_expr);
            aux.add("expr2",seq_peg);
            seq_peg = aux;
        }
        peg_expr = seq_peg;
    }

    @Override
    public void visit(StarPegNode peg) {
        ST aux_peg = groupTemplate.getInstanceOf("star_peg");
        // visit the parsing expression
        peg.getPeg().accept(this);
        // adding the parsing expression on star template
        aux_peg.add("peg_expr", peg_expr);
        peg_expr = aux_peg;
    }

    @Override
    public void visit(GrammarNode grammar) {
        String grmName;
        template = groupTemplate.getInstanceOf("apeg");
        grmName = grammar.getName();
        if(!Character.isUpperCase(grmName.charAt(0))){
            char c = Character.toUpperCase(grmName.charAt(0));
            grmName = ""+c + grmName.substring(1,grmName.length());
        }
        template.add("name", grmName);
        System.out.println("teste");

        for(RuleNode rule : grammar.getRules()) {
            rule.accept(this);
        }
        if(path == null){
            try{
                path = grmName +".java";
                FileWriter w = new FileWriter(path);
                System.out.println("Result saved to " + path);
                w.write(template.render());
                w.close();
             }catch(Exception e){
                 e.printStackTrace();
             }
        }else{
             System.out.println(template.render());
        }
    }

    @Override
    public void visit(RuleNode rule) {
    ST r = groupTemplate.getInstanceOf("rule");
        // setting rule name
        r.add("name", rule.getName());
        
        rule.getExpr().accept(this);
        r.add("peg_expr", peg_expr); // setting parsing expression propriety
        
        template.add("rules", r); // adding the rule template on the list of grammar rules
        
        
    }


}
