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
import apeg.parse.ast.GrammarNode.GrammarOption;
import apeg.util.path.Path;

public class ParserVisitor  extends FormalVisitor implements ASTNodeVisitor{

	private STGroup groupTemplate;
	private ST template;

	// Template for current parsing expression, current expression and the last assignment visited 
	private ST peg_expr, expr;

	private String state;
	private String currentRule;

	public ParserVisitor(Path filePath) {
		groupTemplate = new STGroupFile(filePath.getAbsolutePath());
		state = "";
		currentRule = " ";
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
		state = "choice";
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
		aux_peg.add("name", currentRule);
		// set the current parsing expression
		peg_expr = aux_peg;
		state = "";
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

		 if(state == "choice") { 
			peg_expr.add("choice", "");
		 }else if(state == "star") { 
			peg_expr.add("star", "");
	     }else {
			peg_expr.add("lit", "");
		}
	}

	@Override
	public void visit(NonterminalPegNode peg) {
		// set the current parsing expression template
		peg_expr = groupTemplate.getInstanceOf("nonterminal_peg");
		peg_expr.add("name", peg.getName());

	
		if(state == "choice") {
			peg_expr.add("choice", "");
		}else if(state == "star") {
			peg_expr.add("star", "");
		}else {
			peg_expr.add("non", "");
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
		state = "sequence";
		// set the current parsing expression template
		ST aux_peg = groupTemplate.getInstanceOf("sequence_peg");
		for(PegNode p : peg.getPegs()) {
			// visit a parsing expression
			p.accept(this);
			// add the current parsing expression on the sequence template
			aux_peg.add("peg_exprs", peg_expr);
		}
		peg_expr = aux_peg;
		state = "";
	}

	@Override
	public void visit(StarPegNode peg) {

		state = "star";
		// set the current parsing expression template as a update parsing expression
		ST aux_peg = groupTemplate.getInstanceOf("star_peg");
		// visit the parsing expression
		peg.getPeg().accept(this);
		// adding the parsing expression on star template
		aux_peg.add("peg_expr", peg_expr);
		aux_peg.add("name", currentRule);
		peg_expr = aux_peg;
		state = " ";
	}


	@Override
	public void visit(GrammarNode grammar) {
		template = groupTemplate.getInstanceOf("apeg");

		template.add("name", grammar.getName());

		for(RuleNode rule : grammar.getRules())
			rule.accept(this);

		System.out.println(template.render());
	}

	@Override
	public void visit(RuleNode rule) {

		ST r = groupTemplate.getInstanceOf("rule");
		// setting rule name
		r.add("name", rule.getName());	
		currentRule = rule.getName();

		rule.getExpr().accept(this);
		r.add("peg_expr", peg_expr); // setting parsing expression propriety
		
		if(rule.getExpr() instanceof SequencePegNode) {
			r.add("suc_or_fail", "endSuccess()");
		}else if(rule.getExpr() instanceof StarPegNode) {
			r.add("suc_or_fail", "endSuccess()");
		}else if(rule.getExpr() instanceof LiteralPegNode) {
			r.add("suc_or_fail", "endSuccess()");
		}else if(rule.getExpr() instanceof NonterminalPegNode) {
			r.add("suc_or_fail", "endSuccess()");
		}else if(rule.getExpr() instanceof ChoicePegNode) {
			r.add("suc_or_fail", "endFail()");
		}
		
		template.add("rules", r); // adding the rule template on the list of grammar rules	

	}


}
