package apeg.visitor;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import apeg.util.path.*;

import apeg.ast.*;
import apeg.ast.expr.*;
import apeg.ast.expr.operators.*;
import apeg.ast.rules.*;
import apeg.ast.types.*;
import apeg.util.*;

public class PrettyPrint extends Visitor{
	
	private STGroup groupTemplate;
	
	private ST template;
	
	private ST attr;
	private ST type;
	private ST expr, peg, assign;
	
	public PrettyPrint (Path filePath) {
		
		groupTemplate = new STGroupFile(filePath.getAbsolutePath());
	}

	@Override
	public void visit(Attribute n) {
		// TODO Auto-generated method stub
		
		//set the current expression template
		expr = groupTemplate.getInstanceOf("attribute");
		//set the attribute's name
		expr.add("name", n.getName());
		attr = expr;
		
	}

	@Override
	public void visit(AttributeGrammar n) {
		// TODO Auto-generated method stub
		
		//set the current expression template
		expr = groupTemplate.getInstanceOf("attribute_grammar");
		//set the grammar's name
		expr.add("name", n.getName());
		attr = expr;
		
	}

	@Override
	public void visit(BoolLit n) {
		// TODO Auto-generated method stub
		
		this.expr = groupTemplate.getInstanceOf("boolean_expr");
		this.expr.add("value", n.getValue());
	}

	@Override
	public void visit(CharLit n) {
		// TODO Auto-generated method stub
		
		this.expr = groupTemplate.getInstanceOf("char_expr");
		this.expr.add("value", n.getValue());
	}

	@Override
	public void visit(FloatLit n) {
		// TODO Auto-generated method stub
		
		this.expr = groupTemplate.getInstanceOf("float_expr");
		this.expr.add("value", n.getValue());
	}

	@Override
	public void visit(IntLit n) {
		// TODO Auto-generated method stub
		
		this.expr = groupTemplate.getInstanceOf("int_expr");
		this.expr.add("value", n.getValue());
	}

	@Override
	public void visit(MapLit n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(StrLit n) {
		// TODO Auto-generated method stub
		
		this.expr = groupTemplate.getInstanceOf("string_expr");
		this.expr.add("value", n.getValue());
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
	public void visit(MetaChoiceList n) {
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
		// TODO Auto-generated method stub
		
		ST aux_expr = groupTemplate.getInstanceOf("add_expr");
		
		n.getLeft().accept(this);
		aux_expr.add("left_expr", this.expr);
		
		n.getRight().accept(this);
		aux_expr.add("right_expr", this.expr);
		
		this.expr = aux_expr;
	}

	@Override
	public void visit(And n) {
		// TODO Auto-generated method stub
		
		ST aux_expr = groupTemplate.getInstanceOf("and_expr");
		
		n.getLeft().accept(this);
		aux_expr.add("left_expr", this.expr);
		
		n.getRight().accept(this);
		aux_expr.add("right_expr", this.expr);
		
		this.expr = aux_expr;
	}

	@Override
	public void visit(Compose n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Concat n) {
		// TODO Auto-generated method stub
		
		ST aux_expr = groupTemplate.getInstanceOf("concat_expr");
		
		n.getLeft().accept(this);
		aux_expr.add("left_expr", this.expr);
		
		n.getRight().accept(this);
		aux_expr.add("right_expr", this.expr);
		
		this.expr = aux_expr;
	}

	@Override
	public void visit(Div n) {
		// TODO Auto-generated method stub
		
		ST aux_expr = groupTemplate.getInstanceOf("div_expr");
		
		n.getLeft().accept(this);
		aux_expr.add("left_expr", this.expr);
		
		n.getRight().accept(this);
		aux_expr.add("right_expr", this.expr);
		
		this.expr = aux_expr;
	}

	@Override
	public void visit(Equals n) {
		// TODO Auto-generated method stub
		
		ST aux_expr = groupTemplate.getInstanceOf("equals_expr");
		
		n.getLeft().accept(this);
		aux_expr.add("left_expr", this.expr);
		
		n.getRight().accept(this);
		aux_expr.add("right_expr", this.expr);
		
		this.expr = aux_expr;
	}

	@Override
	public void visit(Greater n) {
		// TODO Auto-generated method stub
		
		ST aux_expr = groupTemplate.getInstanceOf("gt_expr");
		
		n.getLeft().accept(this);
		aux_expr.add("left_expr", this.expr);
		
		n.getRight().accept(this);
		aux_expr.add("right_expr", this.expr);
		
		this.expr = aux_expr;
	}

	@Override
	public void visit(GreaterEq n) {
		// TODO Auto-generated method stub
		
		ST aux_expr = groupTemplate.getInstanceOf("ge_expr");
		
		n.getLeft().accept(this);
		aux_expr.add("left_expr", this.expr);
		
		n.getRight().accept(this);
		aux_expr.add("right_expr", this.expr);
		
		this.expr = aux_expr;
	}

	@Override
	public void visit(Less n) {
		// TODO Auto-generated method stub
		
		ST aux_expr = groupTemplate.getInstanceOf("lt_expr");
		
		n.getLeft().accept(this);
		aux_expr.add("left_expr", this.expr);
		
		n.getRight().accept(this);
		aux_expr.add("right_expr", this.expr);
		
		this.expr = aux_expr;
	}

	@Override
	public void visit(LessEq n) {
		// TODO Auto-generated method stub
		
		ST aux_expr = groupTemplate.getInstanceOf("le_expr");
		
		n.getLeft().accept(this);
		aux_expr.add("left_expr", this.expr);
		
		n.getRight().accept(this);
		aux_expr.add("right_expr", this.expr);
		
		this.expr = aux_expr;
		
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
		// TODO Auto-generated method stub
		
		ST aux_expr = groupTemplate.getInstanceOf("mod_expr");
		
		n.getLeft().accept(this);
		aux_expr.add("left_expr", this.expr);
		
		n.getRight().accept(this);
		aux_expr.add("right_expr", this.expr);
		
		this.expr = aux_expr;
		
	}

	@Override
	public void visit(Mult n) {
		// TODO Auto-generated method stub
		
		ST aux_expr = groupTemplate.getInstanceOf("mul_expr");
		
		n.getLeft().accept(this);
		aux_expr.add("left_expr", this.expr);
		
		n.getRight().accept(this);
		aux_expr.add("right_expr", this.expr);
		
		this.expr = aux_expr;
	}

	@Override
	public void visit(Not n) {
		// TODO Auto-generated method stub
		
		ST aux_expr = groupTemplate.getInstanceOf("not_expr");
		
		n.getExpr().accept(this);
		aux_expr.add("expr", this.expr);
		this.expr = aux_expr;
	}

	@Override
	public void visit(NotEq n) {
		// TODO Auto-generated method stub
		
		ST aux_expr = groupTemplate.getInstanceOf("no_equals_expr");
		
		n.getLeft().accept(this);
		aux_expr.add("left_expr", this.expr);
		
		n.getRight().accept(this);
		aux_expr.add("right_expr", this.expr);
		
		this.expr = aux_expr;
		
		
	}

	@Override
	public void visit(Or n) {
		// TODO Auto-generated method stub
		
		ST aux_expr = groupTemplate.getInstanceOf("or_expr");
		
		n.getLeft().accept(this);
		aux_expr.add("left_expr", this.expr);
		
		n.getRight().accept(this);
		aux_expr.add("right_expr", this.expr);
		
		this.expr = aux_expr;
	}

	@Override
	public void visit(Sub n) {
		// TODO Auto-generated method stub
		
		ST peg_expr = groupTemplate.getInstanceOf("sub_expr");
		
		n.getLeft().accept(this);
		peg_expr.add("left_expr",expr);
		
		n.getRight().accept(this);
		peg_expr.add("right_expr",expr);
		
		expr = peg_expr;
	}

	@Override
	public void visit(UMinus n) {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
		ST aux_peg = groupTemplate.getInstanceOf("and_peg");
		
		n.getPegExp().accept(this);
		aux_peg.add("peg", peg);
		peg = aux_peg;
	}

	@Override
	public void visit(AnyPEG n) {
		// TODO Auto-generated method stub
		
		this.peg = groupTemplate.getInstanceOf("any_peg");
		
	}

	@Override
	public void visit(BindPEG n) {
		// TODO Auto-generated method stub
		
		ST aux_peg = groupTemplate.getInstanceOf("bind_peg");
		n.getAttribute().accept(this);
		aux_peg.add("name", expr);
		
		n.getExpr().accept(this);
		aux_peg.add("peg",peg);
	}

	@Override
	public void visit(ChoiceList n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ChoicePEG n) {
		// TODO Auto-generated method stub
		
		// set the current parsing expression template
				ST aux_peg = groupTemplate.getInstanceOf("choice_peg");
				// visit the left parsing expression
				n.getLeftPeg().accept(this);
				// set propriety for the left parsing expression
				aux_peg.add("left_peg", peg);
				// visit the right parsing expression
				n.getRightPeg().accept(this);
				// set propriety for the left parsing expression
				aux_peg.add("right_peg", peg);
				// set the current parsing expression
				peg = aux_peg;
		
		
	}

	@Override
	public void visit(ConstraintPEG n) {
		// TODO Auto-generated method stub
		
		ST peg_expr = groupTemplate.getInstanceOf("constraint_peg");
		
		n.getExpr().accept(this);
		peg_expr.add("expr",expr);
		
	}

	@Override
	public void visit(KleenePEG n) {
		// TODO Auto-generated method stub
		
		ST aux_peg = groupTemplate.getInstanceOf("star_peg");
		n.getPegExp().accept(this);
		aux_peg.add("peg_expr", this.peg);
		aux_peg = this.peg;
		
	}

	@Override
	public void visit(LambdaPEG n) {
		// TODO Auto-generated method stub
		
		this.peg = groupTemplate.getInstanceOf("lambda_peg");
		
	}

	@Override
	public void visit(LitPEG n) {
		// TODO Auto-generated method stub
		
		peg = groupTemplate.getInstanceOf("literal_peg");
		peg.add("value",n.getLit());
	}

	@Override
	public void visit(NonterminalPEG n) {
		// TODO Auto-generated method stub
		
		peg = groupTemplate.getInstanceOf("nonterminal_peg");
		
		
		peg.add("name",n.getName());
		
		for(Expr e:n.getArgs()) {
			e.accept(this);
			
			peg.add("attrs", expr);
		
		}
	}

	@Override
	public void visit(NotPEG n) {
		// TODO Auto-generated method stub
		
		// set the current parsing expression template
		ST aux_peg = groupTemplate.getInstanceOf("not_peg");
		// visit the parsing expression
		n.getPegExp().accept(this);
		// adding the parsing expression on star template
		aux_peg.add("peg_expr", peg);
		
		
	}

	@Override
	public void visit(OptionalPEG n) {
		// TODO Auto-generated method stub
		
		ST aux_peg = groupTemplate.getInstanceOf("optional_peg");
		n.getPegExp().accept(this);
		aux_peg.add("peg", peg);
		peg = aux_peg;
		
	}

	@Override
	public void visit(PKleene n) {
		// TODO Auto-generated method stub
		

		ST aux_expr = groupTemplate.getInstanceOf("star_peg");
		n.getPegExp().accept(this);
		aux_expr.add("peg",peg);
		peg = aux_expr;
	}

	@Override
	public void visit(RulePEG n) {
		// TODO Auto-generated method stub
		
		ST r = groupTemplate.getInstanceOf("rule");
		
		//setting rule name
		
		r.add("name",n.getRuleName());
		
		//setting annotation
		switch(n.getAnno()) {
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
		
		//visit inherited attributes
		
		for(Pair<Type, String>inh: n.getInh()) {
			inh.getFirst().accept(this);
			r.add("inh", inh.getSecond());
		}
		
		//visit synthesized attributes
		for(Expr syn: n.getSyn()) {
			syn.accept(this);
			r.add("syn", attr);
		}
		
		//visit the parsing expressions
		
		n.getPeg().accept(this);
		r.add("peg",peg);
		
		template.add("rules",r);
	
	}

	@Override
	public void visit(SeqPEG n) {
		// TODO Auto-generated method stub
		
		//set the current parsing expression template
		
		ST aux_peg = groupTemplate.getInstanceOf("sequence_peg");
		for(APEG p: n.getPegs()) {
			p.accept(this);
			aux_peg.add("peg_exprs",peg);
		}
		peg = aux_peg;
	}

	@Override
	public void visit(UpdatePEG n) {
		// TODO Auto-generated method stub
		
		//set the current parsing expression template as a update parsing expression
		
		peg = groupTemplate.getInstanceOf("update_peg");
		
		for(Pair<Attribute, Expr>assig: n.getAssigs()) {
			
			assig.getFirst().accept(this);
			peg.add("attrs",attr);
			
			assig.getSecond().accept(this);
			peg.add("expr", expr);
		}
		
	}

	@Override
	public void visit(TyBool n) {
		// TODO Auto-generated method stub
		
		this.type = groupTemplate.getInstanceOf("booelan_type");
	}

	@Override
	public void visit(TyChar n) {
		// TODO Auto-generated method stub
		
		this.type = groupTemplate.getInstanceOf("char_type");
	}

	@Override
	public void visit(TyFloat n) {
		// TODO Auto-generated method stub

		this.type = groupTemplate.getInstanceOf("float_type");
	}

	@Override
	public void visit(TyGrammar n) {
		// TODO Auto-generated method stub

		this.type = groupTemplate.getInstanceOf("grammar_type");
	}

	@Override
	public void visit(TyInt n) {
		// TODO Auto-generated method stub

		this.type = groupTemplate.getInstanceOf("int_type");
	}

	@Override
	public void visit(TyLang n) {
		// TODO Auto-generated method stub

		this.type = groupTemplate.getInstanceOf("lang_type");
	}

	@Override
	public void visit(TyMap n) {
		// TODO Auto-generated method stub

		//this.type = groupTemplate.getInstanceOf("char_type");
	}

	@Override
	public void visit(TyMeta n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(TyString n) {
		// TODO Auto-generated method stub

		this.type = groupTemplate.getInstanceOf("string_type");
	}

	@Override
	public void visit(Grammar n) {
		// TODO Auto-generated method stub
		template = groupTemplate.getInstanceOf("apeg");
		
		template.add("name", n.getName());
		
		// print the option
	
		if(n.getOptions().adaptable== true) {
			
			template.add("option", "adaptalbe");
			
			if((n.getOptions().memoize == true)) {
				
				template.add("option","memoize");
				if(n.getOptions().usual_semantics == false) {
					
					template.add("option","Not_usual_semantics");
				}
			}
		}
		else {
			if(n.getOptions().memoize == true) {
				
				template.add("option", "memoize");
				
				if(n.getOptions().usual_semantics == false) {
					
					template.add("option","Not_usual_semantics");
				}
			}
			if(n.getOptions().usual_semantics == false) {
				
				template.add("option","Not_usual_semantics");
			}
		}
		
		
		//visit the rules
		
		for(RulePEG rule: n.getRules()) {
			rule.accept(this);
		}
		
		render();
		
		
	}
	
	public void render() {
		//rendering template
		
		System.out.println(template.render());
	}

	

}
