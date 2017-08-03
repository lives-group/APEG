package apeg.parse.ast.visitor;

import java.util.ArrayList;
import java.util.List;

import org.stringtemplate.v4.*;

import apeg.parse.ast.AndExprNode;
import apeg.parse.ast.AndPegNode;
import apeg.parse.ast.AnyPegNode;
import apeg.parse.ast.AssignmentNode;
import apeg.parse.ast.AttributeExprNode;
import apeg.parse.ast.BinaryExprNode;
import apeg.parse.ast.BindPegNode;
import apeg.parse.ast.BooleanExprNode;
import apeg.parse.ast.CallExprNode;
import apeg.parse.ast.ChoicePegNode;
import apeg.parse.ast.ConstraintPegNode;
import apeg.parse.ast.EqualityExprNode;
import apeg.parse.ast.ExprNode;
import apeg.parse.ast.FloatExprNode;
import apeg.parse.ast.FunctionNode;
import apeg.parse.ast.GrammarNode;
import apeg.parse.ast.GrammarNode.GrammarOption;
import apeg.parse.ast.GroupPegNode;
import apeg.parse.ast.IntExprNode;
import apeg.parse.ast.LambdaPegNode;
import apeg.parse.ast.LiteralPegNode;
import apeg.parse.ast.MetaPegExprNode;
import apeg.parse.ast.MinusExprNode;
import apeg.parse.ast.NonterminalPegNode;
import apeg.parse.ast.NotExprNode;
import apeg.parse.ast.NotPegNode;
import apeg.parse.ast.OptionalPegNode;
import apeg.parse.ast.OrExprNode;
import apeg.parse.ast.PegNode;
import apeg.parse.ast.PlusPegNode;
import apeg.parse.ast.RuleNode;
import apeg.parse.ast.SequencePegNode;
import apeg.parse.ast.StarPegNode;
import apeg.parse.ast.StringExprNode;
import apeg.parse.ast.TypeNode;
import apeg.parse.ast.UpdatePegNode;
import apeg.parse.ast.VarDeclarationNode;
import apeg.util.path.Path;

public class PrettyPrintVisitor implements ASTNodeVisitor {
 
	private STGroup groupTemplate;
	private ST template;
	
	// List for declarations: inherited, synthesized attributes and so forth.
	List<Object> attrs;
	// curent Type
	ST type;
	
	private ST peg_expr, expr;
	
	
	
	public PrettyPrintVisitor(Path filePath) {
		groupTemplate = new STGroupFile(filePath.getAbsolutePath());
		
	}
	
	@Override
	public void visit(AndExprNode expr) {
   
       expr.getLeftExpr().accept(this);
		
		ST aux_left = this.expr;
		
		
		expr.getRightExpr().accept(this);
		
		ST aux_right = this.expr;
		
		
		this.expr = groupTemplate.getInstanceOf("and_expr");
	
		this.expr.add("e", aux_left);
		this.expr.add("d", aux_right);
	}

	@Override
	public void visit(AttributeExprNode expr) { 
		
		this.expr = groupTemplate.getInstanceOf("atribute_expr");
		
		this.expr.add("atribute", expr.getName());
		
	}

	@Override
	public void visit(BinaryExprNode expr) { 
		
        expr.getLeftExpr().accept(this);
		
		ST aux_left = this.expr;
		
		
		expr.getRightExpr().accept(this);
		
		ST aux_right = this.expr;
		
		switch(expr.getOperation()) {
			case LT:
				this.expr = groupTemplate.getInstanceOf("lt_expr");
				break;
			case MUL:
				this.expr = groupTemplate.getInstanceOf("mul_expr");
				break;
			case ADD:
				this.expr = groupTemplate.getInstanceOf("add_expr");
				break;
			case GT: 
				this.expr = groupTemplate.getInstanceOf("gt_expr");
				break;		
			case SUB: 
				this.expr = groupTemplate.getInstanceOf("sub_expr");
				break;
				
				
		default:
			this.expr = groupTemplate.getInstanceOf("binary_expr");
			break;
		}

		
		this.expr.add("e", aux_left); 
		//this.expr.add("o", expr.getOperation());
		this.expr.add("d", aux_right);
		

	}

	@Override
	public void visit(BooleanExprNode expr) {
	     
	     this.expr = groupTemplate.getInstanceOf("boolean_expr");
			
		 this.expr.add("valor", expr.getValue());
		
	}

	@Override
	public void visit(CallExprNode expr) { 
		
		List<ST> l = new ArrayList<ST>();
		for(ExprNode p : expr.getParameters()){
			p.accept(this);
			l.add(this.expr);
		}
		
		
		this.expr = groupTemplate.getInstanceOf("call_expr");
		
		 this.expr.add("name", expr.getName());
		 this.expr.add("param", l );
		
		
	}

	@Override
	public void visit(EqualityExprNode expr) { //equality type?
		
        expr.getLeftExpr().accept(this);
		
		ST aux_left = this.expr;
		
		
		expr.getRightExpr().accept(this);
		
		ST aux_right = this.expr; 
		
		switch(expr.getEqualityType()) {
		case EQ:
			this.expr = groupTemplate.getInstanceOf("eq_expr");
			break;
	
	default:
		this.expr = groupTemplate.getInstanceOf("equality_expr");
		break;
	}
		
		
        //this.expr = groupTemplate.getInstanceOf("equality_expr");
		
        this.expr.add("e", aux_left);
        this.expr.add("d", aux_right);
      	
        
	}

	@Override
	public void visit(FloatExprNode expr) { 
		
		this.expr = groupTemplate.getInstanceOf("float_expr");
			
		this.expr.add("valor", expr.getValue());
	
	

	}

	@Override
	public void visit(IntExprNode expr) { 
		
		this.expr = groupTemplate.getInstanceOf("int_expr");
		
		this.expr.add("valor", expr.getValue());
	

	}

	@Override
	public void visit(MetaPegExprNode expr) { 
		
        expr.getExpr().accept(this);
		
		ST aux_expr = this.expr;
		
		this.expr = groupTemplate.getInstanceOf("meta_expr");
		
		this.expr.add("valor", aux_expr);
	}

	@Override
	public void visit(MinusExprNode expr) { 
		
        expr.getExpr().accept(this);
		
		ST aux_expr = this.expr;
		
        this.expr = groupTemplate.getInstanceOf("minus_expr");
		
        this.expr.add("minus", aux_expr);

	}

	@Override
	public void visit(NotExprNode expr) {
        
		expr.getExpr().accept(this);
		
		ST aux_expr = this.expr;
		
		this.expr = groupTemplate.getInstanceOf("not_expr");
		
		this.expr.add("not", aux_expr);


	}

	@Override
	public void visit(OrExprNode expr) {
	
       expr.getLeftExpr().accept(this);
		
		ST aux_left = this.expr;
			
		expr.getRightExpr().accept(this);
		
		ST aux_right = this.expr;
			
		this.expr = groupTemplate.getInstanceOf("or_expr");
		
		this.expr.add("e", aux_left);
		this.expr.add("d", aux_right);

	}

	@Override
	public void visit(StringExprNode expr) {
		
		this.expr = groupTemplate.getInstanceOf("string_expr");
		
		this.expr.add("string", expr.getValue()); 
		

	}

	@Override
	public void visit(AndPegNode peg) { 
		
        peg.getPeg().accept(this);
		
		ST aux_expr = peg_expr;
		
		peg_expr = groupTemplate.getInstanceOf("and_peg");
		
        peg_expr.add("and", aux_expr);


	}

	@Override
	public void visit(AnyPegNode peg) { 
		
    //peg.accept(this);
    
	}

	@Override
	public void visit(BindPegNode peg) {
		
		peg.getPeg().accept(this);
		ST aux_expr = peg_expr;
		
		peg_expr = groupTemplate.getInstanceOf("bind_peg");
		
		peg_expr.add("bind", aux_expr);
		peg_expr.add("variable", peg.getVariable());

	}

	@Override
	public void visit(ChoicePegNode peg) {
		
		peg.getLeftPeg().accept(this);
		
		ST aux_left = peg_expr;
		
		
		peg.getRightPeg().accept(this);
		
		ST aux_right = peg_expr;
			
		peg_expr = groupTemplate.getInstanceOf("choice_peg");
		
		peg_expr.add("e", aux_left);
		peg_expr.add("d", aux_right);
		
	}

	@Override
	public void visit(ConstraintPegNode peg) {
		
		
        peg.getExpr().accept(this);
		
		ST aux = expr;
		
		peg_expr = groupTemplate.getInstanceOf("constraint_peg");
		
		peg_expr.add("constraint", aux);
	}

	@Override
	public void visit(GroupPegNode peg) {
	
		peg_expr = groupTemplate.getInstanceOf("gr_peg");
		
		peg_expr.add("gr", peg.getRanges());

	}

	@Override
	public void visit(LambdaPegNode peg) { // ??
		
		 //peg.accept(this); 
		// peg_expr = groupTemplate.getInstanceOf("lambda");
    
	}

	@Override
	public void visit(LiteralPegNode peg) {
		
		peg_expr= groupTemplate.getInstanceOf("literal_peg");
		
		peg_expr.add("l", peg.getValue());
		
		

	}

	@Override
	public void visit(NonterminalPegNode peg) {
		
		List<ST> l = new ArrayList<ST>();
		for(ExprNode p:peg.getExprs()) {
			p.accept(this);
			l.add(peg_expr);
		}
	 	
		
		peg_expr = groupTemplate.getInstanceOf("non_peg");
		
		peg_expr.add("name", peg.getName());
		peg_expr.add("exprr", l);

	}

	@Override
	public void visit(NotPegNode peg) {
		
		peg.getPeg().accept(this);
		ST aux = peg_expr; 
		
		peg_expr = groupTemplate.getInstanceOf("no_peg");
		peg_expr.add("no", aux);

	}

	@Override
	public void visit(OptionalPegNode peg) {
		
        peg.getPeg().accept(this);
		
		ST aux = peg_expr;
		
		peg_expr = groupTemplate.getInstanceOf("optional_peg");
		peg_expr.add("optional", aux);


		
	}

	@Override
	public void visit(PlusPegNode peg) {
    
       peg.getPeg().accept(this);
		
		ST aux = peg_expr;
		
		peg_expr = groupTemplate.getInstanceOf("plus_peg");
		peg_expr.add("plus", aux);

	}

	@Override
	public void visit(SequencePegNode peg) {
		

		List<ST> l = new ArrayList<ST>();
		for(PegNode p:peg.getPegs()) {
			p.accept(this);
			l.add(peg_expr);
		}
		peg_expr = groupTemplate.getInstanceOf("sequence_peg");
		peg_expr.add("sequence", l);
		
	}

	@Override
	public void visit(StarPegNode peg) {
		
		peg.getPeg().accept(this);
		ST aux = peg_expr;
		
		peg_expr = groupTemplate.getInstanceOf("star_peg");
		peg_expr.add("star", aux);
	}

	@Override
	public void visit(UpdatePegNode peg) {
		
		List<ST> l = new ArrayList<ST>();
		for(AssignmentNode p:peg.getAssignments()) {
			p.accept(this);
			l.add(peg_expr);
		}
		
		peg_expr = groupTemplate.getInstanceOf("update_peg");
		peg_expr.add("update", l);	
		
	}

	@Override
	public void visit(AssignmentNode assign) {
		
		assign.getExpr().accept(this);
		ST aux = expr;
		peg_expr = groupTemplate.getInstanceOf("assign");
		peg_expr.add("as", assign.getVariable());
		peg_expr.add("a", aux);
	
	}

	@Override
	public void visit(FunctionNode func) {
		peg_expr= groupTemplate.getInstanceOf("func");
		peg_expr.add("f", func.getName());
	}

	@Override
	public void visit(GrammarNode grammar) {
		template = groupTemplate.getInstanceOf("apeg");
		
		template.add("name", grammar.getName());
		// print the options
		for(GrammarOption opt : grammar.getOptions()) {
			switch(opt) {
			case ADAPTABLE:
				template.add("option", "isAdaptable = true");
				break;
			case MEMOIZE:
				template.add("option", "memoize = true");
				break;
			case NO_ADAPTABLE:
				template.add("option", "isAdaptable = false");
				break;
			case NO_MEMOIZE:
				template.add("option", "memoize = false");
				break;
			case SIMPLE_ENV_SEMANTICS:
				template.add("option", "envSemantics = simple");
				break;
			case USUAL_SEMANTICS:
				template.add("option", "envSemantics = discardChangesWhenFail");
				break;
			default:
				break;
			}
		}
		// print header
		if(grammar.getPreamble() != "") // there is a header
			template.add("header", grammar.getPreamble());
		// print external functions
		for(String func : grammar.getFunctions()) // functions
			template.add("functions", func);
		for(String source : grammar.getFunctionsSources()) // function sources
			template.add("func_sources", source);
		// visit the rules
		for(RuleNode rule : grammar.getRules())
			rule.accept(this);
		
		// rendering the template
	
		System.out.println(template.render());
		
		
	}

	@Override
	public void visit(RuleNode rule) {
		
		RuleSymbol r = new RuleSymbol();
		
		// setting annotation 
		switch(rule.getAnnotation()) {
		case MEMOIZE:
			r.annotation = "memoize";
			break;
		case NONE:
			r.annotation = null;
			break;
		case TRANSIENT:
			r.annotation = "transient";
			break;
		default:
			r.annotation = null;
			break;
		
		}
		
		// setting rule name
		r.name = rule.getName();
		
		// visit inherited attributes
		attrs = new ArrayList<Object>();
		for(VarDeclarationNode param : rule.getParameters()){
		
			param.accept(this);
			
		}
		r.inh_attr = attrs;
	
		
		
		// visit synthesized attributes
		attrs = new ArrayList<Object>();
		for(VarDeclarationNode param : rule.getReturns()){
			param.accept(this);
		}
		r.syn_attr = attrs;
		
		
		// visit the parsing expression
		rule.getExpr().accept(this);

		r.peg_expr = peg_expr;
		
		
		template.add("rules", r);
		
			
	}

	@Override
	public void visit(TypeNode type) {
		// create a template for the specific type
		switch(type.getName()) {
		case "int":
			this.type = groupTemplate.getInstanceOf("int");
			break;
		case "float":
			this.type = groupTemplate.getInstanceOf("float");
			break;
		case "string":
			this.type = groupTemplate.getInstanceOf("string");
			break;
		case "boolean":
			this.type = groupTemplate.getInstanceOf("boolean");
			break;
		case "grammar":
			this.type = groupTemplate.getInstanceOf("grammar");
			break;
		case "rule":
			this.type = groupTemplate.getInstanceOf("ruleType");
			break;
		default:
			this.type = groupTemplate.getInstanceOf("idType");
			this.type.add("name", type.getName());
			break;
		}
	}

	@Override
	public void visit(VarDeclarationNode var) {
		// visit type
		var.getType().accept(this);
		// setting attributes values
		VarSymbol v = new VarSymbol();
		v.name = var.getName();
		v.type = type;
		attrs.add(v);

	
	
	}

	class RuleSymbol {
		public String annotation, name;
		public Object inh_attr, syn_attr, peg_expr, expr;
		
		
	}
	
	class VarSymbol {
		public String name;
		public ST type;
	}
}
