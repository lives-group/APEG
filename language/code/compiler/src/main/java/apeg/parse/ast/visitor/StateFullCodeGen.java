package apeg.parse.ast.visitor;

import java.util.ListIterator;
import java.util.List;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import apeg.parse.ast.*;
import apeg.parse.ast.visitor.Environments.Environment;
import apeg.parse.ast.visitor.Environments.NTInfo;
import apeg.parse.ast.visitor.Environments.VarType;
import apeg.util.path.Path;

import java.io.FileWriter;


public class StateFullCodeGen  extends FormalVisitor{

    private STGroup groupTemplate;
    private ST template;
    private String path, currentRule;
    Environment<String, NTInfo> ntTable;

    private ST peg_expr, expr, assign;

    public StateFullCodeGen(Path filePath, Environment<String, NTInfo> ntTable) {
        groupTemplate = new STGroupFile(filePath.getAbsolutePath());
        this.ntTable = ntTable;
    }

    public StateFullCodeGen(Path filePath, String path, Environment<String, NTInfo> ntTable) {
        this(filePath, ntTable);
        this.path = path;
    }


	@Override
	public void visit(AndExprNode expr) {
		// set the current expression template
		ST aux_expr = groupTemplate.getInstanceOf("and_expr");
		// visit left expression
		expr.getLeftExpr().accept(this);
		// set the left expression attribute
		aux_expr.add("left_expr", this.expr);
		// visit right expression
		expr.getRightExpr().accept(this);
		// set the right expression attribute
		aux_expr.add("right_expr", this.expr);
		// set the current expression
		this.expr = aux_expr;
	}

	@Override
	public void visit(AttributeExprNode expr) {

    ST aux_peg =  groupTemplate.getInstanceOf("access_key");
    aux_peg.add("key",ntTable.get(currentRule).getLocals().get(expr.getName()).getAccessCode());
		aux_peg.add("type",ntTable.get(currentRule).getLocals().get(expr.getName()).getType().getName());
    aux_peg.add("name","v");

    this.expr = aux_peg;
  }


  //
	// @Override
	// public void visit(BinaryExprNode expr) {
	// 	ST aux_expr;

	// 	switch(expr.getOperation()) {
	// 		case GT: // >
	// 			aux_expr = groupTemplate.getInstanceOf("gt_expr");
	// 			break;
	// 		case GE: // >=
	// 			aux_expr = groupTemplate.getInstanceOf("ge_expr");
	// 			break;
	// 		case LT: // <
	// 			aux_expr = groupTemplate.getInstanceOf("lt_expr");
	// 			break;
	// 		case LE: // <=
	// 			aux_expr = groupTemplate.getInstanceOf("le_expr");
	// 			break;
	// 		case ADD: // +
	// 			aux_expr = groupTemplate.getInstanceOf("add_expr");
	// 			break;
	// 		case SUB: // -
	// 			aux_expr = groupTemplate.getInstanceOf("sub_expr");
	// 			break;
	// 		case MUL: // *
	// 			aux_expr = groupTemplate.getInstanceOf("mul_expr");
	// 			break;
	// 		case DIV: // /
	// 			aux_expr = groupTemplate.getInstanceOf("div_expr");
	// 			break;
	// 		case MOD: // %
	// 			aux_expr = groupTemplate.getInstanceOf("mod_expr");
	// 			break;
	// 		default: // Should never reach the default case
	// 			aux_expr = null;
	// 			break;
	// 	}
	// 	// visit left expression
  //       expr.getLeftExpr().accept(this);
  //       // set the left expression attribute
  //    	aux_expr.add("left_expr", this.expr);
	// 	// visit the right expression
	// 	expr.getRightExpr().accept(this);
	// 	// set the right expression attribute
	// 	aux_expr.add("right_expr", this.expr);
	// 	// set the current expression
	// 	this.expr = aux_expr;
	// }

	// @Override
	// public void visit(BooleanExprNode expr) {
	// 	// set the current expression template
	// 	this.expr = groupTemplate.getInstanceOf("boolean_expr");
	// 	// set value propriety
	// 	this.expr.add("value", expr.getValue());
	// }

	@Override
	public void visit(CallExprNode expr) {
		// set the current expression template
		ST aux_expr = groupTemplate.getInstanceOf("call_expr");
		// visit each function parameter expression
		for(ExprNode e : expr.getParameters()){
			e.accept(this);
			// set the attribute params
			aux_expr.add("params", this.expr);
		}
		// set the function name attributec
		aux_expr.add("name", expr.getName());

		// set the current expression
		this.expr = aux_expr;
	}

	@Override
	public void visit(EqualityExprNode expr) {
		// set the current expression template
		ST aux_expr;
		switch(expr.getEqualityType()) {
			case EQ:
				aux_expr = groupTemplate.getInstanceOf("equals_expr");
				break;
			case NE:
				aux_expr = groupTemplate.getInstanceOf("no_equals_expr");
				break;
			default: // Should never reach the default case
				aux_expr = null;
				break;
}

		// visit left expression
        expr.getLeftExpr().accept(this);
        // set the left expression attribute
     	aux_expr.add("left_expr", this.expr);
		// visit the right expression
		expr.getRightExpr().accept(this);
		// set the right expression attribute
		aux_expr.add("right_expr", this.expr);
		// set the current expression
		this.expr = aux_expr;
	}

	// @Override
	// public void visit(FloatExprNode expr) {
	// 	// set the current expression template
	// 	this.expr = groupTemplate.getInstanceOf("float_expr");
	// 	// set value propriety
	// 	this.expr.add("value", expr.getValue());
	// }

	// @Override
	// public void visit(IntExprNode expr) {
	// 	// set the current expression template
	// 	this.expr = groupTemplate.getInstanceOf("int_expr");
	// 	// set value propriety
	// 	this.expr.add("value", expr.getValue());
	// }

	@Override
	public void visit(MetaPegExprNode expr) {
		// set the current expression template
		ST aux_expr = groupTemplate.getInstanceOf("meta_expr");
		// visit expression
		expr.getExpr().accept(this);
		// set the expression attribute
		aux_expr.add("expr", this.expr);
		// set the current expression
		this.expr = aux_expr;
	}

	@Override
	public void visit(MinusExprNode expr) {
		// set the current expression template
		ST aux_expr = groupTemplate.getInstanceOf("minus_expr");
		// visit expression
		expr.getExpr().accept(this);
		// set the expression attribute
		aux_expr.add("expr", this.expr);
		// set the current expression
		this.expr = aux_expr;
	}

	@Override
	public void visit(NotExprNode expr) {
		// set the current expression template
		ST aux_expr = groupTemplate.getInstanceOf("not_expr");
		// visit expression
		expr.getExpr().accept(this);
		// set the expression attribute
		aux_expr.add("expr", this.expr);
		// set the current expression
		this.expr = aux_expr;
	}

	@Override
	public void visit(OrExprNode expr) {
		// set the current expression template
		ST aux_expr = groupTemplate.getInstanceOf("or_expr");
		// visit left expression
		expr.getLeftExpr().accept(this);
		// set the left expression attribute
		aux_expr.add("left_expr", this.expr);
		// visit right expression
		expr.getRightExpr().accept(this);
		// set the right expression attribute
		aux_expr.add("right_expr", this.expr);
		// set the current expression
		this.expr = aux_expr;
	}

	// @Override
	// public void visit(StringExprNode expr) {
	// 	// set the current expression template
	// 	this.expr = groupTemplate.getInstanceOf("string_expr");
	// 	// set value propriety
	// 	this.expr.add("value", expr.getValue());
	// }

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
    	  System.out.println("any");
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
     @Override
     public void visit(GroupPegNode peg) {
         peg_expr = groupTemplate.getInstanceOf("group_peg");
         peg_expr.add("ranges", peg.getRanges());
     }

    @Override
    public void visit(LambdaPegNode peg) {
        peg_expr = groupTemplate.getInstanceOf("lambda_peg");
    }

    @Override
    public void visit(LiteralPegNode peg) {
        peg_expr= groupTemplate.getInstanceOf("match");
        peg_expr.add("value", peg.getValue());
    }

    @Override
    public void visit(NonterminalPegNode peg) {
        // set the current parsing expression template
    	ST aux = groupTemplate.getInstanceOf("call");
        aux.add("name", peg.getName());
        String envname = peg.getName() + "_env";
        aux.add("env", envname);


        int tam = ntTable.get(peg.getName()).getLocals().size();
        int ninh = ntTable.get(peg.getName()).getSig().getNumInherited();
        int ntp = ntTable.get(peg.getName()).getSig().getNumParams();
        int nsyn = ntTable.get(peg.getName()).getSig().getNumSintetized();
        aux.add("tam", tam);
        ST root = groupTemplate.getInstanceOf("iniParamList");
        ST last = root;
        ST temp;
        List<ExprNode> l = peg.getExprs();
        if(ninh > 0) {
        	l.get(0).accept(this);
        	last.add("env", envname);
        	last.add("key", 0);
        	last.add("value", expr);
        	int i = 0;
        	for(i = 1; i < ninh; i++) {
        		l.get(i).accept(this);
            	temp  = groupTemplate.getInstanceOf("iniParamList");
            	temp.add("env", envname);
            	temp.add("key", i);
            	temp.add("value", expr);
            	last.add("init2", temp);
            	last = temp;
        	}
        	aux.add("iniList", root);
        }
        if(nsyn > 0) {
        	root = groupTemplate.getInstanceOf("iniParamList");
        	last = root;
        	String vname = ((AttributeExprNode)l.get(ninh)).getName();
        	last.add("env", "env");
        	last.add("key", ntTable.get(currentRule).getLocals().get(vname).getAccessCode());
        	last.add("value", envname+".get("+ninh+")");
        	int i;
        	for(i = ninh+1; i < ntp; i++) {
        		vname = ((AttributeExprNode)l.get(i)).getName();
            	temp  = groupTemplate.getInstanceOf("iniParamList");
            	temp.add("env", "env");
            	temp.add("key", ntTable.get(currentRule).getLocals().get(vname).getAccessCode());
            	temp.add("value", expr);
            	last.add("init2", envname+".get("+ninh+")");
            	last = temp;
        	}
        	aux.add("posList", root);
        }
        peg_expr = aux;
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

   public void visit(AssignmentNode assign) {
    ST aux_peg = groupTemplate.getInstanceOf("record_key");
    assign.getExpr().accept(this);

    aux_peg.add("name","v");
    //peg_expr.add("type",ntTable.get(currentRule).getLocals().get(assign.getVariable()).getType().getName());
	 	aux_peg.add("key",ntTable.get(currentRule).getLocals().get(assign.getVariable()).getAccessCode());
    aux_peg.add("value",peg_expr);
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


        for(RuleNode rule : grammar.getRules()) {
            rule.accept(this);
        }
        if(path == null){
            try{
                path = grmName +".java";
                FileWriter w = new FileWriter(path);
                System.out.println("\nResult saved to " + path);
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
        currentRule = rule.getName();

        r.add("name", rule.getName());

        rule.getExpr().accept(this);
        r.add("peg_expr", peg_expr); // setting parsing expression propriety

        template.add("rules", r); // adding the rule template on the list of grammar rules

    }

  @Override
  public void visit(UpdatePegNode peg) {
    ST aux_peg = groupTemplate.getInstanceOf("seq_expr");
    for(AssignmentNode p:peg.getAssignments()) {
      p.accept(this);
      aux_peg.add("exprs",peg_expr);
    }
    peg_expr=aux_peg;
  }
  // @Override
	// public void visit(UpdatePegNode peg) {
  //   	// set the current parsing expression template as a update parsing expression
  //   	ST aux_expr = groupTemplate.getInstanceOf("update_peg");
  //   	for(AssignmentNode p : peg.getAssignments()) {
  //   	// visit each assignment node
  //   		p.accept(this);
  //   		// add the current assignment visited
  //   		aux_expr.add("assigns", peg_expr);
  //   	}
  //     peg_expr=aux_expr;
	// }



  @Override
  public void visit(BooleanExprNode expr) {
    ST aux_peg = groupTemplate.getInstanceOf("lit");
    aux_peg.add("expr",expr.getValue());
    peg_expr=aux_peg;
  }

  @Override
  public void visit(FloatExprNode expr) {
    ST aux_peg = groupTemplate.getInstanceOf("lit");
    aux_peg.add("expr",expr.getValue());
    peg_expr=aux_peg;

  }

  @Override
  public void visit(IntExprNode expr) {
    ST aux_peg = groupTemplate.getInstanceOf("lit");
    aux_peg.add("expr",expr.getValue());
    peg_expr=aux_peg;

  }

  @Override
  public void visit(StringExprNode expr) {
    ST aux_peg = groupTemplate.getInstanceOf("lit");
    aux_peg.add("expr",expr.getValue());
    peg_expr=aux_peg;
  }

  @Override
  public void visit(BinaryExprNode expr) {
    ST aux_peg = groupTemplate.getInstanceOf("bexpr");

    expr.getLeftExpr().accept(this);
    aux_peg.add("exprl",this.expr);
    expr.getRightExpr().accept(this);
    aux_peg.add("exprr",this.expr);

    switch (expr.getOperation()) {
      case ADD:
      aux_peg.add("op","+");
      break;

      case SUB:
      aux_peg.add("op","-");
      break;

      case MUL:
      aux_peg.add("op","*");
      break;

      case DIV:
      aux_peg.add("op","/");
      break;

      case GT:
      aux_peg.add("op",">");
      break;

      case GE:
      aux_peg.add("op",">=");
      break;

      case LT:
      aux_peg.add("op","<");
      break;

      case LE:
      aux_peg.add("op","<=");
      break;
    }
    peg_expr=aux_peg;
  }

//   @Override
//   public void visit(VarDeclarationNode var) {
//     var.getType().accept(this);
//     ST aux_peg = groupTemplate.getInstanceOf("var_decl");
//     aux_peg.add("type",peg_expr);
//     aux_peg.add("name",var.getName());
//     peg_expr=aux_peg;
//   }
}
