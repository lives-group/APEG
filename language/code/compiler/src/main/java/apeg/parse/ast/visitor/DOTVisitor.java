package apeg.parse.ast.visitor;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import apeg.parse.ast.CallExprNode;
import apeg.parse.ast.ChoicePegNode;
import apeg.parse.ast.ConstraintPegNode;
import apeg.parse.ast.EqualityExprNode;
import apeg.parse.ast.FloatExprNode;
import apeg.parse.ast.FunctionNode;
import apeg.parse.ast.GrammarNode;
import apeg.parse.ast.GrammarNode.GrammarOption;
import apeg.parse.ast.ExprNode;
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


public class DOTVisitor implements ASTNodeVisitor {

	private FileWriter out;
	private int c_assing = 0, c_peg_expr = 0, c_expr = 0;

	private STGroup groupTemplate;
	private ST template, type;
	
	private List<ST> nodes;
	
	private String nodeName, parent;
	
	private ST lable; // node label
	
	public DOTVisitor(Path filepath, Path template){
		// open the dot template
		groupTemplate = new STGroupFile(template.getAbsolutePath());
		try{
			out = new FileWriter(filepath.getAbsolutePath());
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public void visit(AndExprNode expr) {
		ST node = groupTemplate.getInstanceOf("node"); // get a node template
		node.add("parent", parent); // set the attribute parent node
		nodeName = "expr_and" + c_expr++; // set the current node name
		node.add("node", nodeName); //  set current node name
		node.add("lable", groupTemplate.getInstanceOf("and_expr_lable")); // set node label
		nodes.add(node);
		
		String s = nodeName;
		parent = s; // set the current parent
		expr.getLeftExpr().accept(this); // visit the left expr
		parent = s; // set the current parent
		expr.getRightExpr().accept(this); // visit the right expr		
	}

	@Override
	public void visit(AttributeExprNode expr) {
		ST node = groupTemplate.getInstanceOf("node"); // get a node template
		node.add("parent", parent); // set the attribute parent node
		nodeName = "expr_var" + c_expr++; // set the current node name
		node.add("node", nodeName); //  set current node name 
		node.add("lable", groupTemplate.getInstanceOf("attribute_expr_lable").add("name", expr.getName())); // set node label
		nodes.add(node);
	}

	@Override
	public void visit(BinaryExprNode expr) { 
		ST node = groupTemplate.getInstanceOf("node"); // get a node template
		node.add("parent", parent); // set the attribute parent node
		nodeName = "expr_binary" + c_expr++; // set the current node name
		node.add("node", nodeName); //  set current node name
		switch(expr.getOperation()) {
			case GT: // >
				node.add("lable", groupTemplate.getInstanceOf("gt_expr_lable")); // set node label
				break;
			case GE: // >=
				node.add("lable", groupTemplate.getInstanceOf("ge_expr_lable")); // set node label
				break;
			case LT: // <
				node.add("lable", groupTemplate.getInstanceOf("lt_expr_lable")); // set node label
				break;
			case LE: // <=
				node.add("lable", groupTemplate.getInstanceOf("le_expr_lable")); // set node label
				break;
			case ADD: // +
				node.add("lable", groupTemplate.getInstanceOf("add_expr_lable")); // set node label
				break;
			case SUB: // -
				node.add("lable", groupTemplate.getInstanceOf("sub_expr_lable")); // set node label
				break;
			case MUL: // *
				node.add("lable", groupTemplate.getInstanceOf("mul_expr_lable")); // set node label
				break;
			case DIV: // /
				node.add("lable", groupTemplate.getInstanceOf("div_expr_lable")); // set node label
				break;
			case MOD: // %
				node.add("lable", groupTemplate.getInstanceOf("mod_expr_lable")); // set node label
				break;
			default: // Should never reach the default case
				break;
		}
		nodes.add(node);
		
		String s = nodeName;
		parent = s; // set the current parent
		expr.getLeftExpr().accept(this); // visit the left expr
		parent = s; // set the current parent
		expr.getRightExpr().accept(this); // visit the right expr
	}

	@Override
	public void visit(BooleanExprNode expr) {
		ST node = groupTemplate.getInstanceOf("node"); // get a node template
		node.add("parent", parent); // set the attribute parent node
		nodeName = "expr_boolean" + c_expr++; // set the current node name
		node.add("node", nodeName); //  set current node name
		node.add("lable", groupTemplate.getInstanceOf("boolean_expr_lable").add("value", expr.getValue())); // set node label
		nodes.add(node);
	}

	@Override
	public void visit(CallExprNode expr) { 
		ST node = groupTemplate.getInstanceOf("node"); // get a node template
		node.add("parent", parent); // set the attribute parent node
		nodeName = "expr_function_call" + c_expr++; // set the current node name
		node.add("node", nodeName); //  set current node name 
		node.add("lable", groupTemplate.getInstanceOf("call_expr_lable").add("name", expr.getName())); // set node label
		nodes.add(node);
		
		String s = nodeName;
		// visit each function parameter expression
		for(ExprNode e : expr.getParameters()){
			parent = s; // set the current parent
			e.accept(this);
		}
	}

	@Override
	public void visit(EqualityExprNode expr) { //equality type? 
		ST node = groupTemplate.getInstanceOf("node"); // get a node template
		node.add("parent", parent); // set the attribute parent node
		nodeName = "expr_equals" + c_expr++; // set the current node name
		node.add("node", nodeName); //  set current node name
		switch(expr.getEqualityType()) {
		case EQ:
			node.add("lable", groupTemplate.getInstanceOf("equals_expr_lable")); // set node label
			break;
		case NE:
			node.add("lable", groupTemplate.getInstanceOf("no_equals_expr_lable")); // set node label
			break;
		default: // Should never reach the default case
			break;
		}
		nodes.add(node);
		
		String s = nodeName;
		parent = s; // set the current parent
		expr.getLeftExpr().accept(this); // visit the left expr
		parent = s; // set the current parent
		expr.getRightExpr().accept(this); // visit the right expr
	}

	@Override
	public void visit(FloatExprNode expr) { 
		ST node = groupTemplate.getInstanceOf("node"); // get a node template
		node.add("parent", parent); // set the attribute parent node
		nodeName = "expr_float" + c_expr++; // set the current node name
		node.add("node", nodeName); //  set current node name
		node.add("lable", groupTemplate.getInstanceOf("float_expr_lable").add("value", expr.getValue())); // set node label
		nodes.add(node);
	}

	@Override
	public void visit(IntExprNode expr) { 
		ST node = groupTemplate.getInstanceOf("node"); // get a node template
		node.add("parent", parent); // set the attribute parent node
		nodeName = "expr_int" + c_expr++; // set the current node name
		node.add("node", nodeName); //  set current node name
		node.add("lable", groupTemplate.getInstanceOf("int_expr_lable").add("value", expr.getValue())); // set node label
		nodes.add(node);
	}

	@Override
	public void visit(MetaPegExprNode expr) { 
		ST node = groupTemplate.getInstanceOf("node"); // get a node template
		node.add("parent", parent); // set the attribute parent node
		nodeName = "expr_meta" + c_expr++; // set the current node name
		node.add("node", nodeName); //  set current node name
		node.add("lable", groupTemplate.getInstanceOf("meta_expr_lable")); // set node label
		nodes.add(node);
		
		parent = nodeName; // set the current parent
		expr.getExpr().accept(this); // visit expr
	}

	@Override
	public void visit(MinusExprNode expr) { 
		ST node = groupTemplate.getInstanceOf("node"); // get a node template
		node.add("parent", parent); // set the attribute parent node
		nodeName = "expr_minus" + c_expr++; // set the current node name
		node.add("node", nodeName); //  set current node name
		node.add("lable", groupTemplate.getInstanceOf("minus_expr_lable")); // set node label
		nodes.add(node);
		
		parent = nodeName; // set the current parent
		expr.getExpr().accept(this); // visit expr
	}

	@Override
	public void visit(NotExprNode expr) { 
		ST node = groupTemplate.getInstanceOf("node"); // get a node template
		node.add("parent", parent); // set the attribute parent node
		nodeName = "expr_not" + c_expr++; // set the current node name
		node.add("node", nodeName); //  set current node name
		node.add("lable", groupTemplate.getInstanceOf("not_expr_lable")); // set node label
		nodes.add(node);
		
		parent = nodeName; // set the current parent
		expr.getExpr().accept(this); // visit expr
	}

	@Override
	public void visit(OrExprNode expr) {
		ST node = groupTemplate.getInstanceOf("node"); // get a node template
		node.add("parent", parent); // set the attribute parent node
		nodeName = "expr_or" + c_expr++; // set the current node name
		node.add("node", nodeName); //  set current node name
		node.add("lable", groupTemplate.getInstanceOf("or_expr_lable")); // set node label
		nodes.add(node);
		
		String s = nodeName;
		parent = s; // set the current parent
		expr.getLeftExpr().accept(this); // visit the left expr
		parent = s; // set the current parent
		expr.getRightExpr().accept(this); // visit the right expr
	}

	@Override
	public void visit(StringExprNode expr) {
		ST node = groupTemplate.getInstanceOf("node"); // get a node template
		node.add("parent", parent); // set the attribute parent node
		nodeName = "expr_string" + c_expr++; // set the current node name
		node.add("node", nodeName); //  set current node name
		node.add("lable", groupTemplate.getInstanceOf("string_expr_lable").add("value", expr.getValue())); // set node label
		nodes.add(node);
	}

	@Override
	public void visit(AndPegNode peg) { 
		ST node = groupTemplate.getInstanceOf("node"); // get a node template
		node.add("parent", parent); // set the attribute parent node
		nodeName = "p_expr_and" + c_peg_expr++; // set the current node name
		node.add("node", nodeName); //  set current node name
		node.add("lable", groupTemplate.getInstanceOf("and_peg_lable")); // set node label
		nodes.add(node); 
		
		parent = nodeName; // set the new parent node name
		// visit the parsing expression
		peg.getPeg().accept(this);
	}

	@Override
	public void visit(AnyPegNode peg) { 
		ST node = groupTemplate.getInstanceOf("node"); // get a node template
		node.add("parent", parent); // set the attribute parent node
		nodeName = "p_expr_any" + c_peg_expr++; // set the current node name
		node.add("node", nodeName); //  set current node name
		node.add("lable", groupTemplate.getInstanceOf("any_peg_lable")); // set node label
		nodes.add(node); 
	}

	@Override
	public void visit(BindPegNode peg) {
		ST node = groupTemplate.getInstanceOf("node"); // get a node template
		node.add("parent", parent); // set the attribute parent node
		nodeName = "p_expr_bind" + c_peg_expr++; // set the current node name
		node.add("node", nodeName); //  set current node name
		node.add("lable", groupTemplate.getInstanceOf("bind_peg_lable").add("name", peg.getVariable())); // set node label
		nodes.add(node);
		
		parent = nodeName; // set the new parent node name
		// visit the parsing expression
		peg.getPeg().accept(this);
	}

	@Override
	public void visit(ChoicePegNode peg) {
		ST node = groupTemplate.getInstanceOf("node"); // get a node template
		node.add("parent", parent); // set the attribute parent node
		nodeName = "p_expr_choice" + c_peg_expr++; // set the current node name
		node.add("node", nodeName); //  set current node name
		node.add("lable", groupTemplate.getInstanceOf("choice_peg_lable")); // set node label
		nodes.add(node); 

		String s = nodeName; // save the node name
		parent = s; // set the new parent node name
		// visit the left parsing expression
		peg.getLeftPeg().accept(this);
		parent = s; // set the new parent node name
		// visit the right parsing expression
		peg.getRightPeg().accept(this);
	}

	@Override
	public void visit(ConstraintPegNode peg) {	
		ST node = groupTemplate.getInstanceOf("node"); // get a node template
		node.add("parent", parent); // set the attribute parent node
		nodeName = "p_expr_constraint" + c_peg_expr++; // set the current node name
		node.add("node", nodeName); //  set current node name
		node.add("lable", groupTemplate.getInstanceOf("constraint_peg_lable")); // set node label
		nodes.add(node); 
		
		parent = nodeName; // set the new parent node name
		// visit the constraint expression
		peg.getExpr().accept(this);
	}

	@Override
	public void visit(GroupPegNode peg) {		
		ST node = groupTemplate.getInstanceOf("node"); // get a node template
		node.add("parent", parent); // set the attribute parent node
		nodeName = "p_expr_ranges" + c_peg_expr++; // set the current node name
		node.add("node", nodeName); //  set current node name
		node.add("lable", groupTemplate.getInstanceOf("group_peg_lable").add("ranges", peg.getRanges())); // set node label
		nodes.add(node); 
	}

	@Override
	public void visit(LambdaPegNode peg) {
		ST node = groupTemplate.getInstanceOf("node"); // get a node template
		node.add("parent", parent); // set the attribute parent node
		nodeName = "p_expr_lambda" + c_peg_expr++; // set the current node name
		node.add("node", nodeName); //  set current node name
		node.add("lable", groupTemplate.getInstanceOf("lambda_peg_lable")); // set node label
		nodes.add(node); 
	}

	@Override
	public void visit(LiteralPegNode peg) {
		ST node = groupTemplate.getInstanceOf("node"); // get a node template
		node.add("parent", parent); // set the attribute parent node
		nodeName = "p_expr_literal" + c_peg_expr++; // set the current node name
		node.add("node", nodeName); //  set current node name
		node.add("lable", groupTemplate.getInstanceOf("literal_peg_lable").add("value", peg.getValue())); // set node label
		nodes.add(node); 
	}

	@Override
	public void visit(NonterminalPegNode peg) {
		ST node = groupTemplate.getInstanceOf("node"); // get a node template
		node.add("parent", parent); // set the attribute parent node
		nodeName = "p_expr_nonterminal" + c_peg_expr++; // set the current node name
		node.add("node", nodeName); //  set current node name
		node.add("lable", groupTemplate.getInstanceOf("nonterminal_peg_lable").add("name", peg.getName())); // set node label
		nodes.add(node); 
			
		String s = nodeName; // save the node name
		// visit each attribute expression
		for(ExprNode p : peg.getExprs()) {
			parent = s;
			p.accept(this);
		}		
	}
	
	@Override
	public void visit(NotPegNode peg) {	
		ST node = groupTemplate.getInstanceOf("node"); // get a node template
		node.add("parent", parent); // set the attribute parent node
		nodeName = "p_expr_not" + c_peg_expr++; // set the current node name
		node.add("node", nodeName); //  set current node name
		node.add("lable", groupTemplate.getInstanceOf("not_peg_lable")); // set node label
		nodes.add(node); 
		
		parent = nodeName; // set the new parent node name
		// visit the parsing expression
		peg.getPeg().accept(this);
	}


	@Override
	public void visit(OptionalPegNode peg) {
		ST node = groupTemplate.getInstanceOf("node"); // get a node template
		node.add("parent", parent); // set the attribute parent node
		nodeName = "p_expr_optional" + c_peg_expr++; // set the current node name
		node.add("node", nodeName); //  set current node name
		node.add("lable", groupTemplate.getInstanceOf("optional_peg_lable")); // set node label
		nodes.add(node); 
		
		parent = nodeName; // set the new parent node name
		// visit the parsing expression
		peg.getPeg().accept(this);
	}

	@Override
	public void visit(PlusPegNode peg) {
		ST node = groupTemplate.getInstanceOf("node"); // get a node template
		node.add("parent", parent); // set the attribute parent node
		nodeName = "p_expr_plus" + c_peg_expr++; // set the current node name
		node.add("node", nodeName); //  set current node name
		node.add("lable", groupTemplate.getInstanceOf("plus_peg_lable")); // set node label
		nodes.add(node); 
		
		parent = nodeName; // set the new parent node name
		// visit the parsing expression
		peg.getPeg().accept(this);
	}

	@Override
	public void visit(SequencePegNode peg) {
		ST node = groupTemplate.getInstanceOf("node"); // get a node template
		node.add("parent", parent); // set the attribute parent node
		nodeName = "p_expr_sequence" + c_peg_expr++; // set the current node name
		node.add("node", nodeName); //  set current node name
		node.add("lable", groupTemplate.getInstanceOf("sequence_peg_lable")); // set node label
		nodes.add(node);

		String s = nodeName; // save the node name
		for(PegNode p : peg.getPegs()) {
			parent = s; // set the new parent node name
			// visit a parsing expression
			p.accept(this);
		}	
	}

	@Override
	public void visit(StarPegNode peg) {
		ST node = groupTemplate.getInstanceOf("node"); // get a node template
		node.add("parent", parent); // set the attribute parent node
		nodeName = "p_expr_star" + c_peg_expr++; // set the current node name
		node.add("node", nodeName); //  set current node name
		node.add("lable", groupTemplate.getInstanceOf("star_peg_lable")); // set node label
		nodes.add(node); 
		
		parent = nodeName; // set the new parent node name
		// visit the parsing expression
		peg.getPeg().accept(this);
	}

	@Override
	public void visit(UpdatePegNode peg) {
		ST node = groupTemplate.getInstanceOf("node"); // get a node template
		node.add("parent", parent); // set the attribute parent node
		nodeName = "p_expr_update" + c_peg_expr++; // set the current node name
		node.add("node", nodeName); //  set current node name
		node.add("lable", groupTemplate.getInstanceOf("update_peg_lable")); // set node label
		nodes.add(node);  
		String s = nodeName; // save the node name
		for(AssignmentNode p : peg.getAssignments()) {
			parent = s; // set the new parent node name
			// visit each assignment node
			p.accept(this);
		}
	}

	@Override
	public void visit(AssignmentNode assign) {
		ST node = groupTemplate.getInstanceOf("node"); // get a node template
		node.add("parent", parent); // set the attribute parent node
		nodeName = "assignment" + c_assing++; // set the current node name
		node.add("node", nodeName); //  set current node name
		node.add("lable", groupTemplate.getInstanceOf("assign_lable").add("var", assign.getVariable())); // set node label
		nodes.add(node);
		// visit the assignment expression
		parent = nodeName; // set the new parent node name
		assign.getExpr().accept(this);
	}


	@Override
	public void visit(FunctionNode func) {
		/**
		 * Functionality not implemented yet
		 */	
	}

	@Override
	public void visit(GrammarNode grammar) {

		template = groupTemplate.getInstanceOf("apeg");

		template.add("name", grammar.getName());
		// set the options
		for(GrammarOption opt : grammar.getOptions()) {
			switch(opt) {
			case ADAPTABLE:
				template.add("option", "isAdaptable");
				break;
			case MEMOIZE:
				template.add("option", "memoize");
				break;
			case NO_ADAPTABLE:
				template.add("option", "notAdaptable");
				break;
			case NO_MEMOIZE:
				template.add("option", "noMemoize");
				break;
			case SIMPLE_ENV_SEMANTICS:
				template.add("option", "simpleSemantics");
				break;
			case USUAL_SEMANTICS:
				template.add("option", "discardChanges");
				break;
			default:
				break;
			}
		}
		// we do not include the header text in the graphical view 
		// print external functions
		for(String func : grammar.getFunctions()) // functions
			template.add("functions", func);
		for(String source : grammar.getFunctionsSources()) // function sources
			template.add("func_sources", source);
		// visit the rules
		for(RuleNode rule : grammar.getRules())
			rule.accept(this);
		// rendering the template TODO
		String saida = template.render();
		try {
			out.write(saida);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		
		r.add("rname", rule.getName()); // set the rule name
		String rName = rule.getName() + "Rule"; // set dot node name as the rule name followed by the "Rule" word
		r.add("nodeName", rName); // set the dot node name
		
		ST node;
		// visit inherited attributes
		for(VarDeclarationNode param : rule.getParameters()){
			parent = rName;
			param.accept(this);
			node = groupTemplate.getInstanceOf("node"); // get a node template
			node.add("parent", rName); // set the attribute parent node
			node.add("node", rName + "_" + nodeName); // set current node name
			node.add("lable", groupTemplate.getInstanceOf("inh_label").add("attr", lable)); // set node label
			r.add("inh_attr", node);			
		}
		// visit synthesized attributes
		for(VarDeclarationNode param : rule.getReturns()){
			parent = rName;
			param.accept(this);
			node = groupTemplate.getInstanceOf("node"); // get a node template
			node.add("parent", rName); // set the attribute parent node
			node.add("node", rName + "_" + nodeName); // set current node name
			node.add("lable", groupTemplate.getInstanceOf("syn_label").add("attr", lable)); // set node label
			r.add("syn_attr", node);
		}		
		// visit the parsing expression
		parent = rName;
		nodes = new ArrayList<ST>();
		rule.getExpr().accept(this);
		r.add("peg_expr", nodes); // setting parsing expression propriety
		
		template.add("rules", r); // adding the rule template on the list of grammar rules	
	}

	@Override
	public void visit(TypeNode type) {	
		// create a template for the specific type
		switch(type.getName()) {
		case "int":
			this.type = groupTemplate.getInstanceOf("int_type");
			break;
		case "float":
			this.type = groupTemplate.getInstanceOf("float_type");
			break;
		case "string":
			this.type = groupTemplate.getInstanceOf("string_type");
			break;
		case "boolean":
			this.type = groupTemplate.getInstanceOf("boolean_type");
			break;
		case "grammar":
			this.type = groupTemplate.getInstanceOf("grammar_type");
			break;
		case "rule":
			this.type = groupTemplate.getInstanceOf("rule_type");
			break;
		default:
			this.type = groupTemplate.getInstanceOf("user_type");
			this.type.add("name", type.getName());
			break;
		}
	}

	@Override
	public void visit(VarDeclarationNode var) {
		// set the current attribute template
		lable = groupTemplate.getInstanceOf("decl_label");
		// visit type
		var.getType().accept(this);
		// setting attributes values
		lable.add("type", type);
		// set node name
		nodeName = var.getName();
		lable.add("name", nodeName);
		
	}
}