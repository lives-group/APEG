package apeg.visitor;

import java.util.ArrayList;
import java.util.List;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import apeg.ast.Grammar;
import apeg.ast.expr.Attribute;
import apeg.ast.expr.AttributeGrammar;
import apeg.ast.expr.BoolLit;
import apeg.ast.expr.CharLit;
import apeg.ast.expr.Expr;
import apeg.ast.expr.FloatLit;
import apeg.ast.expr.IntLit;
import apeg.ast.expr.MapLit;
import apeg.ast.expr.MetaAndPEG;
import apeg.ast.expr.MetaAnyPEG;
import apeg.ast.expr.MetaAttribute;
import apeg.ast.expr.MetaBindPEG;
import apeg.ast.expr.MetaBoolLit;
import apeg.ast.expr.MetaCharLit;
import apeg.ast.expr.MetaChoiceList;
import apeg.ast.expr.MetaChoicePEG;
import apeg.ast.expr.MetaConstraintPEG;
import apeg.ast.expr.MetaFloatLit;
import apeg.ast.expr.MetaIntLit;
import apeg.ast.expr.MetaKleenePEG;
import apeg.ast.expr.MetaLitPEG;
import apeg.ast.expr.MetaMapLit;
import apeg.ast.expr.MetaNonterminalPEG;
import apeg.ast.expr.MetaNot;
import apeg.ast.expr.MetaNotPEG;
import apeg.ast.expr.MetaOptionalPEG;
import apeg.ast.expr.MetaPKleene;
import apeg.ast.expr.MetaRulePEG;
import apeg.ast.expr.MetaSeqPEG;
import apeg.ast.expr.MetaStrLit;
import apeg.ast.expr.MetaTyBool;
import apeg.ast.expr.MetaTyChar;
import apeg.ast.expr.MetaTyFloat;
import apeg.ast.expr.MetaTyGrammar;
import apeg.ast.expr.MetaTyInt;
import apeg.ast.expr.MetaTyLang;
import apeg.ast.expr.MetaTyMap;
import apeg.ast.expr.MetaTyMeta;
import apeg.ast.expr.MetaTyString;
import apeg.ast.expr.MetaUMinus;
import apeg.ast.expr.MetaUpdatePEG;
import apeg.ast.expr.MetaVar;
import apeg.ast.expr.StrLit;
import apeg.ast.expr.operators.Add;
import apeg.ast.expr.operators.And;
import apeg.ast.expr.operators.Compose;
import apeg.ast.expr.operators.Concat;
import apeg.ast.expr.operators.Div;
import apeg.ast.expr.operators.Equals;
import apeg.ast.expr.operators.Greater;
import apeg.ast.expr.operators.GreaterEq;
import apeg.ast.expr.operators.Less;
import apeg.ast.expr.operators.LessEq;
import apeg.ast.expr.operators.MapAcces;
import apeg.ast.expr.operators.MapExtension;
import apeg.ast.expr.operators.MetaAdd;
import apeg.ast.expr.operators.MetaAnd;
import apeg.ast.expr.operators.MetaCompose;
import apeg.ast.expr.operators.MetaConcat;
import apeg.ast.expr.operators.MetaDiv;
import apeg.ast.expr.operators.MetaEquals;
import apeg.ast.expr.operators.MetaGreater;
import apeg.ast.expr.operators.MetaGreaterEq;
import apeg.ast.expr.operators.MetaLess;
import apeg.ast.expr.operators.MetaLessEq;
import apeg.ast.expr.operators.MetaMapAcces;
import apeg.ast.expr.operators.MetaMapExtension;
import apeg.ast.expr.operators.MetaMod;
import apeg.ast.expr.operators.MetaMult;
import apeg.ast.expr.operators.MetaNotEq;
import apeg.ast.expr.operators.MetaOr;
import apeg.ast.expr.operators.MetaSub;
import apeg.ast.expr.operators.Mod;
import apeg.ast.expr.operators.Mult;
import apeg.ast.expr.operators.Not;
import apeg.ast.expr.operators.NotEq;
import apeg.ast.expr.operators.Or;
import apeg.ast.expr.operators.Sub;
import apeg.ast.expr.operators.UMinus;
import apeg.ast.rules.APEG;
import apeg.ast.rules.AndPEG;
import apeg.ast.rules.AnyPEG;
import apeg.ast.rules.BindPEG;
import apeg.ast.rules.ChoiceList;
import apeg.ast.rules.ChoicePEG;
import apeg.ast.rules.ConstraintPEG;
import apeg.ast.rules.KleenePEG;
import apeg.ast.rules.LambdaPEG;
import apeg.ast.rules.LitPEG;
import apeg.ast.rules.NonterminalPEG;
import apeg.ast.rules.NotPEG;
import apeg.ast.rules.OptionalPEG;
import apeg.ast.rules.PKleene;
import apeg.ast.rules.RulePEG;
import apeg.ast.rules.SeqPEG;
import apeg.ast.rules.UpdatePEG;
import apeg.ast.types.TyBool;
import apeg.ast.types.TyChar;
import apeg.ast.types.TyFloat;
import apeg.ast.types.TyGrammar;
import apeg.ast.types.TyInt;
import apeg.ast.types.TyLang;
import apeg.ast.types.TyMap;
import apeg.ast.types.TyMeta;
import apeg.ast.types.TyString;
import apeg.ast.types.Type;
import apeg.util.Pair;
import apeg.util.path.Path;

public class DOTVisitor extends Visitor{
private STGroup groupTemplate;
	
	private ST template;
	
	private ST attr;
	private ST type;
	private ST expr, peg;
	private List<ST> inh;
	private ST assig;
	
	
	public DOTVisitor (Path filePath) {
		
		groupTemplate = new STGroupFile(filePath.getAbsolutePath());
		inh = new ArrayList<ST> ();
		
	}

	@Override
	public void visit(Attribute n) {
		// TODO Auto-generated method stub
		
		// set the current expression template
		attr = groupTemplate.getInstanceOf("attribute_lable");
		// set the name attribute
		attr.add("name", n.getName());
		expr = attr;
		
	}

	@Override
	public void visit(AttributeGrammar n) {
		// TODO Auto-generated method stub
		
		//set the current expression template
		expr = groupTemplate.getInstanceOf("attribute_grammar_lable");
		//set the grammar's name
		expr.add("name", n.getName());
		attr = expr;
		
	}

	@Override
	public void visit(BoolLit n) {
		// TODO Auto-generated method stub
		
		//set the current expression template
		expr = groupTemplate.getInstanceOf("boolean_expr_lable");
		//set value propriety
		expr.add("value", n.getValue());
		
	}

	@Override
	public void visit(CharLit n) {
		// TODO Auto-generated method stub
		
		//set the current expression template
		expr = groupTemplate.getInstanceOf("char_expr_lable");
		//set value propriety
		expr.add("value", n.getValue());
	}

	@Override
	public void visit(FloatLit n) {
		// TODO Auto-generated method stub
		
		//set the current expression template
		expr = groupTemplate.getInstanceOf("float_expr_lable");
		//set value propriety
	    expr.add("value", n.getValue());
	    
	}

	@Override
	public void visit(IntLit n) {
		// TODO Auto-generated method stub
		
		//set the current expression template
		expr = groupTemplate.getInstanceOf("int_expr_lable");
		//set value propriety
		expr.add("value", n.getValue());
	}

	@Override
	public void visit(MapLit n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(StrLit n) {
		// TODO Auto-generated method stub
		
		//set the current expression template
		expr = groupTemplate.getInstanceOf("string_expr_lable");
		//set value propriety
		expr.add("value", n.getValue());
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
		
		//set the current expression template
		expr = groupTemplate.getInstanceOf("add_expr_lable");
		n.getLeft().accept(this);
		n.getRight().accept(this);
	}

	@Override
	public void visit(And n) {
		// TODO Auto-generated method stub
		
		// set the current expression template
		expr = groupTemplate.getInstanceOf("and_expr_lable");
		
		n.getLeft().accept(this);
		n.getRight().accept(this);
	}

	@Override
	public void visit(Compose n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Concat n) {
		// TODO Auto-generated method stub
		
		//set the current expression template
		expr = groupTemplate.getInstanceOf("concat_expr_lable");
		
	}

	@Override
	public void visit(Div n) {
		// TODO Auto-generated method stub
		
		expr = groupTemplate.getInstanceOf("div_expr_lable");
		
		n.getLeft().accept(this);
		n.getRight().accept(this);
	}

	@Override
	public void visit(Equals n) {
		// TODO Auto-generated method stub
		
		expr = groupTemplate.getInstanceOf("equals_expr_lable");
		
		n.getLeft().accept(this);
		n.getRight().accept(this);
	}

	@Override
	public void visit(Greater n) {
		// TODO Auto-generated method stub
		
		expr = groupTemplate.getInstanceOf("gt_expr_lable");
		
		n.getLeft().accept(this);
		n.getRight().accept(this);
	}

	@Override
	public void visit(GreaterEq n) {
		// TODO Auto-generated method stub
		
		expr = groupTemplate.getInstanceOf("ge_expr_lable");
		
		n.getLeft().accept(this);
		n.getRight().accept(this);
	}

	@Override
	public void visit(Less n) {
		// TODO Auto-generated method stub
		
		expr= groupTemplate.getInstanceOf("lt_expr_lable");
		
		n.getLeft().accept(this);
		n.getRight().accept(this);
	}

	@Override
	public void visit(LessEq n) {
		// TODO Auto-generated method stub
		
		expr = groupTemplate.getInstanceOf("le_expr_lable");
		
		n.getLeft().accept(this);
		n.getRight().accept(this);
		
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
		
		expr = groupTemplate.getInstanceOf("mod_expr_lable");
		
		n.getLeft().accept(this);
		n.getRight().accept(this);
		
	}

	@Override
	public void visit(Mult n) {
		// TODO Auto-generated method stub
		
		expr = groupTemplate.getInstanceOf("mul_expr_lable");
		
		n.getLeft().accept(this);
		n.getRight().accept(this);
	}

	@Override
	public void visit(Not n) {
		// TODO Auto-generated method stub
		
		expr = groupTemplate.getInstanceOf("not_expr_lable");
		
		n.getExpr().accept(this);
		
	}

	@Override
	public void visit(NotEq n) {
		// TODO Auto-generated method stub
		
		expr = groupTemplate.getInstanceOf("no_equals_expr_lable");
		
		n.getLeft().accept(this);
		n.getRight().accept(this);
		
		
	}

	@Override
	public void visit(Or n) {
		// TODO Auto-generated method stub
		
		expr = groupTemplate.getInstanceOf("or_expr_lable");
		
		n.getLeft().accept(this);
		n.getRight().accept(this);
	}

	@Override
	public void visit(Sub n) {
		// TODO Auto-generated method stub
		
		expr = groupTemplate.getInstanceOf("sub_expr_lable");
		
		n.getLeft().accept(this);
		n.getRight().accept(this);
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
		
		peg = groupTemplate.getInstanceOf("and_peg_lable");
		n.getPegExp().accept(this);
	}

	@Override
	public void visit(AnyPEG n) {
		// TODO Auto-generated method stub
		
		peg = groupTemplate.getInstanceOf("any_peg_lable");
		
	}

	@Override
	public void visit(BindPEG n) {
		// TODO Auto-generated method stub
		
		peg= groupTemplate.getInstanceOf("bind_peg_lable");
		n.getAttribute().accept(this);
		n.getExpr().accept(this);
	}
	 

	@Override
	public void visit(ChoiceList n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ChoicePEG n) {
		// TODO Auto-generated method stub
		
		ST aux_peg = groupTemplate.getInstanceOf("node");
		
		aux_peg.add("node", "ChoicePEG");
		
		
	}

	@Override
	public void visit(ConstraintPEG n) {
		// TODO Auto-generated method stub
		
		peg= groupTemplate.getInstanceOf("constraint_peg_lable");
		
		n.getExpr().accept(this);
		
	}

	@Override
	public void visit(KleenePEG n) {
		// TODO Auto-generated method stub

		peg = groupTemplate.getInstanceOf("star_peg_lable");
		
		n.getPegExp().accept(this);
		
	}

	@Override
	public void visit(LambdaPEG n) {
		// TODO Auto-generated method stub
		
		peg = groupTemplate.getInstanceOf("lambda_peg_lable");
		
	}

	@Override
	public void visit(LitPEG n) {
		// TODO Auto-generated method stub
		
		peg = groupTemplate.getInstanceOf("literal_peg_lable");
	    peg.add("value",n.getLit());
	}

	@Override
	public void visit(NonterminalPEG n) {
		// TODO Auto-generated method stub
		
		peg = groupTemplate.getInstanceOf("nonterminal_peg_lable");
		
		
		peg.add("name",n.getName());
		
		for(Expr e:n.getArgs()) {
			
			e.accept(this);
			
		}
	}

	@Override
	public void visit(NotPEG n) {
		// TODO Auto-generated method stub
		
		peg = groupTemplate.getInstanceOf("not_peg_lable");
		
		n.getPegExp().accept(this);
	}

	@Override
	public void visit(OptionalPEG n) {
		// TODO Auto-generated method stub
		
		peg = groupTemplate.getInstanceOf("optional_peg_lable");
		n.getPegExp().accept(this);
		
		
	}

	@Override
	public void visit(PKleene n) {
		// TODO Auto-generated method stub
		
		peg = groupTemplate.getInstanceOf("plus_peg_lable");
		
		n.getPegExp().accept(this);
	}

	@Override
	public void visit(RulePEG n) {
		// TODO Auto-generated method stub
		
		ST r = groupTemplate.getInstanceOf("rule");
		
		//setting rule name
		
		r.add("rname",n.getRuleName());
		
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
		ST inh;
		for(Pair<Type, String>in: n.getInh()) {
			in.getFirst().accept(this);
			inh = groupTemplate.getInstanceOf("inh");
			inh.add("type", this.type);
			inh.add("name", in.getSecond());
			this.inh.add(inh);
		}
		r.add("inh", this.inh);
		
		//visit synthesized attributes
		for(Expr syn: n.getSyn()) {
			syn.accept(this);
			r.add("syn", this.attr);
		}
		
		//visit the parsing expressions
		
		n.getPeg().accept(this);
		r.add("peg",this.peg);
		
		r.add("nodeName", n.getRuleName().concat("Rule"));
		
		template.add("rule",r);
	
	}

	@Override
	public void visit(SeqPEG n) {
		// TODO Auto-generated method stub
		
		//set the current parsing expression template
		
		peg = groupTemplate.getInstanceOf("sequence_peg_lable");
		
		for(APEG p: n.getPegs()) {
			p.accept(this);
			
		}
		
	}

	@Override
	public void visit(UpdatePEG n) {
		// TODO Auto-generated method stub
		peg = groupTemplate.getInstanceOf("update_peg_lable");
		
		for(Pair<Attribute, Expr>assigs: n.getAssigs()) {
			
			assigs.getFirst().accept(this);
			assig = groupTemplate.getInstanceOf("assign_lable");
			assig.add("var", attr);
			assigs.getSecond().accept(this);
		}
	}

	@Override
	public void visit(TyBool n) {
		// TODO Auto-generated method stub
		
		this.type = groupTemplate.getInstanceOf("boolean_type");
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
	}

	@Override
	public void visit(TyMap n) {
		// TODO Auto-generated method stub

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
			inh = new ArrayList<ST>();
			rule.accept(this);
		}
		
		render();
		
		
	}
	
	public void render() {
		//rendering template
		
		System.out.println(template.render());
	}
}
