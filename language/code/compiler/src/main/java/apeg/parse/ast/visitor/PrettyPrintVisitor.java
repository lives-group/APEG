package apeg.parse.ast.visitor;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import apeg.parse.ast.AndExprNode;
import apeg.parse.ast.AndPegNode;
import apeg.parse.ast.AnyPegNode;
import apeg.parse.ast.AssignmentNode;
import apeg.parse.ast.AttributeExprNode;
import apeg.parse.ast.BinaryExprNode;
import apeg.parse.ast.BindPegNode;
import apeg.parse.ast.BooleanExprNode;
import apeg.parse.ast.BooleanTypeNode;
import apeg.parse.ast.CallExprNode;
import apeg.parse.ast.ChoicePegNode;
import apeg.parse.ast.ConstraintPegNode;
import apeg.parse.ast.EqualityExprNode;
import apeg.parse.ast.ExprNode;
import apeg.parse.ast.FloatExprNode;
import apeg.parse.ast.FloatTypeNode;
import apeg.parse.ast.GrammarNode;
import apeg.parse.ast.GrammarNode.GrammarOption;
import apeg.parse.ast.GrammarTypeNode;
import apeg.parse.ast.GroupPegNode;
import apeg.parse.ast.IntExprNode;
import apeg.parse.ast.IntTypeNode;
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
import apeg.parse.ast.RuleTypeNode;
import apeg.parse.ast.SequencePegNode;
import apeg.parse.ast.StarPegNode;
import apeg.parse.ast.StringExprNode;
import apeg.parse.ast.StringTypeNode;
import apeg.parse.ast.UpdatePegNode;
import apeg.parse.ast.UserTypeNode;
import apeg.parse.ast.VarDeclarationNode;
import apeg.util.path.Path;

public class PrettyPrintVisitor implements ASTNodeVisitor {
 
	private STGroup groupTemplate;
	private ST template;
	
	// Template for current attribute: inherited, synthesized attributes and so forth.
	private ST attr;
	// Template for current type
	private ST type;
	// Template for current parsing expression, current expression and the last assignment visited 
	private ST peg_expr, expr, assign;
	
	public PrettyPrintVisitor(Path filePath) {
		groupTemplate = new STGroupFile(filePath.getAbsolutePath());
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
		// set the current expression template
		this.expr = groupTemplate.getInstanceOf("atribute_expr");
		// set the name attribute
		this.expr.add("name", expr.getName());
	}

	@Override
	public void visit(BinaryExprNode expr) { 		
		ST aux_expr;
		switch(expr.getOperation()) {
			case GT: // >
				aux_expr = groupTemplate.getInstanceOf("gt_expr");
				break;
			case GE: // >=
				aux_expr = groupTemplate.getInstanceOf("ge_expr");
				break;
			case LT: // <
				aux_expr = groupTemplate.getInstanceOf("lt_expr");
				break;
			case LE: // <=
				aux_expr = groupTemplate.getInstanceOf("le_expr");
				break;
			case ADD: // +
				aux_expr = groupTemplate.getInstanceOf("add_expr");
				break;
			case SUB: // -
				aux_expr = groupTemplate.getInstanceOf("sub_expr");
				break;
			case MUL: // *
				aux_expr = groupTemplate.getInstanceOf("mul_expr");
				break;
			case DIV: // /
				aux_expr = groupTemplate.getInstanceOf("div_expr");
				break;
			case MOD: // %
				aux_expr = groupTemplate.getInstanceOf("mod_expr");
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

	@Override
	public void visit(BooleanExprNode expr) {
		// set the current expression template
		this.expr = groupTemplate.getInstanceOf("boolean_expr");
		// set value propriety
		this.expr.add("value", expr.getValue());
	}

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
		// set the function name attribute
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

	@Override
	public void visit(FloatExprNode expr) { 
		// set the current expression template
		this.expr = groupTemplate.getInstanceOf("float_expr");
		// set value propriety
		this.expr.add("value", expr.getValue());
	}

	@Override
	public void visit(IntExprNode expr) { 
		// set the current expression template
		this.expr = groupTemplate.getInstanceOf("int_expr");
		// set value propriety
		this.expr.add("value", expr.getValue());
	}

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

	@Override
	public void visit(StringExprNode expr) {
		// set the current expression template
		this.expr = groupTemplate.getInstanceOf("string_expr");
		// set value propriety
		this.expr.add("value", expr.getValue()); 
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

	@Override
	public void visit(BindPegNode peg) {
		// set the current parsing expression template
		ST aux_peg = groupTemplate.getInstanceOf("bind_peg");
		// set the variable name propriety
		aux_peg.add("name", peg.getVariable());
		// visit the parsing expression
		peg.getPeg().accept(this);
		// set propriety for the bind parsing expression
		aux_peg.add("peg_expr", peg_expr);
		// set the current parsing expression
		peg_expr = aux_peg;
	}


	@Override
	public void visit(ChoicePegNode peg) {
		// set the current parsing expression template
		ST aux_peg = groupTemplate.getInstanceOf("choice_peg");
		// visit the left parsing expression
		peg.getLeftPeg().accept(this);
		// set propriety for the left parsing expression
		aux_peg.add("left_peg", peg_expr);
		// visit the right parsing expression
		peg.getRightPeg().accept(this);
		// set propriety for the left parsing expression
		aux_peg.add("right_peg", peg_expr);
		// set the current parsing expression
		peg_expr = aux_peg;
	}

	@Override
	public void visit(ConstraintPegNode peg) {
		// set the current parsing expression template
		peg_expr = groupTemplate.getInstanceOf("constraint_peg");
        // visit constraint expression
		peg.getExpr().accept(this);
		// set expression propriety
		peg_expr.add("expr", expr);
	}

	@Override
	public void visit(GroupPegNode peg) {
		peg_expr = groupTemplate.getInstanceOf("group_peg");
		peg_expr.add("ranges", peg.getRanges());
	}

	@Override
	public void visit(LambdaPegNode peg) { // ?? 
		peg_expr = groupTemplate.getInstanceOf("lambda_peg");
	}

	@Override
	public void visit(LiteralPegNode peg) {
		peg_expr= groupTemplate.getInstanceOf("literal_peg");
		peg_expr.add("value", peg.getValue());
	}

	@Override
	public void visit(NonterminalPegNode peg) {
		// set the current parsing expression template
		peg_expr = groupTemplate.getInstanceOf("nonterminal_peg");
		
		// adding the name attribute
		peg_expr.add("name", peg.getName());
		
		// visit each attribute expression
		for(ExprNode p : peg.getExprs()) {
			p.accept(this);
			// adding the expression on nonterminal template
			peg_expr.add("attrs", expr);
		}
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
		// set the current parsing expression template
		ST aux_peg = groupTemplate.getInstanceOf("sequence_peg");
		for(PegNode p : peg.getPegs()) {
			// visit a parsing expression
			p.accept(this);
			// add the current parsing expression on the sequence template
			aux_peg.add("peg_exprs", peg_expr);
		}
		peg_expr = aux_peg;
	}

	@Override
	public void visit(StarPegNode peg) {
		// set the current parsing expression template as a update parsing expression
		ST aux_peg = groupTemplate.getInstanceOf("star_peg");
		// visit the parsing expression
		peg.getPeg().accept(this);
		// adding the parsing expression on star template
		aux_peg.add("peg_expr", peg_expr);
		peg_expr = aux_peg;
	}

	@Override
	public void visit(UpdatePegNode peg) {
		// set the current parsing expression template as a update parsing expression
		peg_expr = groupTemplate.getInstanceOf("update_peg");		
		for(AssignmentNode p : peg.getAssignments()) {
			// visit each assignment node
			p.accept(this);
			// add the current assignment visited
			peg_expr.add("assigns", assign);
		}
	}

	@Override
	public void visit(AssignmentNode assign) {
		// built the assignment template
		this.assign = groupTemplate.getInstanceOf("assign");
		// visit the assignment expression
		assign.getExpr().accept(this);
		// set the name template attribute
		this.assign.add("name", assign.getVariable());
		// set the expr template attribute
		this.assign.add("expr", expr);
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
		
		render();
		
	}

	@Override
	public void visit(RuleNode rule) {
		
		ST r = groupTemplate.getInstanceOf("rule");
				
		// setting annotation 
		switch(rule.getAnnotation()) {
		case MEMOIZE:
			r.add("annotation", "memoize");
			break;
		case NONE:
			break;
		case TRANSIENT:
			r.add("annotation", "transient");
			break;
		default:
			break;
		}
		
		// setting rule name
		r.add("name", rule.getName());	
		// visit inherited attributes
		for(VarDeclarationNode param : rule.getParameters()){
			param.accept(this);
			r.add("inh_attr", attr); // setting inherited attribute propriety
		}
		// visit synthesized attributes
		for(VarDeclarationNode param : rule.getReturns()){
			param.accept(this);
			r.add("syn_attr", attr); // setting synthesized attribute propriety
		}		
		// visit the parsing expression
		rule.getExpr().accept(this);
		r.add("peg_expr", peg_expr); // setting parsing expression propriety
		
		template.add("rules", r); // adding the rule template on the list of grammar rules		
	}

	@Override
	public void visit(VarDeclarationNode var) {
		// set the current attribute template
		attr = groupTemplate.getInstanceOf("decl");
		// visit type
		var.getType().accept(this);
		// setting attributes values
		attr.add("type", type);
		attr.add("name", var.getName());
	}

	@Override
	public void visit(BooleanTypeNode type) {
		this.type = groupTemplate.getInstanceOf("boolean_type");		
	}

	@Override
	public void visit(FloatTypeNode type) {
		this.type = groupTemplate.getInstanceOf("float_type");		
	}

	@Override
	public void visit(GrammarTypeNode type) {
		this.type = groupTemplate.getInstanceOf("grammar_type");		
	}

	@Override
	public void visit(IntTypeNode type) {
		this.type = groupTemplate.getInstanceOf("int_type");		
	}

	@Override
	public void visit(RuleTypeNode type) {
		this.type = groupTemplate.getInstanceOf("rule_type");		
	}

	@Override
	public void visit(StringTypeNode type) {
		this.type = groupTemplate.getInstanceOf("string_type");		
	}

	@Override
	public void visit(UserTypeNode type) {
		this.type = groupTemplate.getInstanceOf("user_type");
		this.type.add("name", type.getName());
	}
	
	public void render() {
		// rendering the template	
				System.out.println(template.render());
	}
	public void renderLastExpr() {
		// rendering the template	
				System.out.println(peg_expr.render());
	}
}
