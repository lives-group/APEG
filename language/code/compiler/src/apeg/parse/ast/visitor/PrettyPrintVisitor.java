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
	private ST template, peg_expr;
	
	// List for declarations: inherited, synthesized attributes and so forth.
	List<Object> attrs;
	// curent Type
	ST type;
	
	
	public PrettyPrintVisitor(Path filePath) {
		groupTemplate = new STGroupFile(filePath.getAbsolutePath());
	}
	
	@Override
	public void visit(AndExprNode expr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(AttributeExprNode expr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(BinaryExprNode expr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(BooleanExprNode expr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(CallExprNode expr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(EqualityExprNode expr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(FloatExprNode expr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(IntExprNode expr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(MetaPegExprNode expr) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(MinusExprNode peg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(NotExprNode peg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(OrExprNode peg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(StringExprNode peg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(AndPegNode peg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(AnyPegNode peg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(BindPegNode peg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(ChoicePegNode peg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(ConstraintPegNode peg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(GroupPegNode peg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(LambdaPegNode peg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(LiteralPegNode peg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(NonterminalPegNode peg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(NotPegNode peg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(OptionalPegNode peg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(PlusPegNode peg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(SequencePegNode peg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(StarPegNode peg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(UpdatePegNode peg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(AssignmentNode assign) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visit(FunctionNode func) {
		// TODO Auto-generated method stub

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
		for(VarDeclarationNode param : rule.getParameters())
			param.accept(this);
		r.inh_attr = attrs;
		
		// visit synthesized attributes
		attrs = new ArrayList<Object>();
		for(VarDeclarationNode param : rule.getReturns())
			param.accept(this);
		r.syn_attr = attrs;
		
		// visit the parsing expression
		rule.getExpr().accept(this);
		//TODO r.peg_expr =
		
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
		public Object inh_attr, syn_attr, peg_expr;
	}
	
	class VarSymbol {
		public String name;
		public ST type;
	}
}
