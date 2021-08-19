package apeg.visitor;

import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import apeg.ast.*;
import apeg.ast.expr.*;
import apeg.ast.expr.operators.*;
import apeg.ast.rules.*;
import apeg.ast.types.*;

import apeg.util.Pair;
import apeg.util.path.Path;
import apeg.util.CharInterval;

public class DOTVisitor extends Visitor{
private STGroup groupTemplate;

        private FileWriter out;
    
	private ST template;
	

	private List<ST> inh;
	private ST assig;
	private String parent, nodeName;
	private ST type, lable;
	private String var;

	private List<ST> nodes;
	
	private int c_peg=0, c_expr=0, c_assig=0;
	
	
    public DOTVisitor (Path filepath, Path template) {
		
		groupTemplate = new STGroupFile(template.getAbsolutePath());
		inh = new ArrayList<ST> ();

		try{
		    out = new FileWriter(filepath.getAbsolutePath());
		} catch(IOException e){
		    e.printStackTrace();
		}
		
	}

	@Override
	public void visit(Attribute n) {
		// TODO Auto-generated method stub
		
		
		ST node = groupTemplate.getInstanceOf("node");
		
		node.add("parent", parent);
		nodeName = "attributeExpr" + c_expr++;
		node.add("node", nodeName);
		node.add("lable", groupTemplate.getInstanceOf("attribute_expr_lable").add("name", n.getName()));
		
		this.var = n.getName();
		
		nodes.add(node);
		
	}

	@Override
	public void visit(AttributeGrammar n) {
		// TODO Auto-generated method stub
		
		ST node = groupTemplate.getInstanceOf("node");
		node.add("parent", parent);
		nodeName = "grammarAttributeExpr" + c_expr++;
		node.add("node", nodeName);
		node.add("lable", groupTemplate.getInstanceOf("attribute_grammar_lable").add("name", n.getName()));
		
		this.var = n.getName();
		nodes.add(node);
		
	}

	@Override
	public void visit(BoolLit n) {
		// TODO Auto-generated method stub
		
		ST node = groupTemplate.getInstanceOf("node");
		
		node.add("parent", parent);
		nodeName = "BooleanLiteralExpr" + c_expr++;
		node.add("node", nodeName);
		node.add("lable", groupTemplate.getInstanceOf("boolean_expr_lable").add("value", n.getValue()));
		
		nodes.add(node);
		
	}

	@Override
	public void visit(CharLit n) {
		// TODO Auto-generated method stub
		
		ST node = groupTemplate.getInstanceOf("node");
		
		node.add("parent", parent);
		nodeName = "CharLiteralExpr" + c_expr++;
		node.add("node", nodeName);
		node.add("lable", groupTemplate.getInstanceOf("char_expr_lable").add("value", n.getValue()));
		
		nodes.add(node);
	}

	@Override
	public void visit(FloatLit n) {
		// TODO Auto-generated method stub
		
		ST node = groupTemplate.getInstanceOf("node");
		
		node.add("parent", parent);
		nodeName = "FloatLiteralExpr" + c_expr++;
		node.add("node", nodeName);
		node.add("lable", groupTemplate.getInstanceOf("float_expr_lable").add("value", n.getValue()));
		
		nodes.add(node);
	}

	@Override
	public void visit(IntLit n) {
		// TODO Auto-generated method stub
		
		ST node = groupTemplate.getInstanceOf("node");
		
		node.add("parent", parent);
		nodeName = "IntLiteralExpr" + c_expr++;
		node.add("node", nodeName);
		node.add("lable", groupTemplate.getInstanceOf("int_expr_lable").add("value", n.getValue()));
		
		nodes.add(node);
	}

	@Override
	public void visit(MapLit n) {
		// TODO Auto-generated method stub
		
                ST node = groupTemplate.getInstanceOf("node");
                node.add("parent", parent);
                nodeName = "mapLit" + c_expr++;
                node.add("node", nodeName);
                node.add("lable", groupTemplate.getInstanceOf("mapLit_expr_lable"));
                nodes.add(node);                  
  
                String s = nodeName;
                for(Pair<Expr, Expr> p : n.getAssocs()){
                        parent = s;
                        p.getFirst().accept(this);
                        parent = s;
                        p.getSecond().accept(this);
                }
	}

	@Override
	public void visit(StrLit n) {
		// TODO Auto-generated method stub
		
		ST node = groupTemplate.getInstanceOf("node");
		
		node.add("parent", parent);
		nodeName = "StringLiteralExpr" + c_expr++;
		node.add("node", nodeName);
		node.add("lable", groupTemplate.getInstanceOf("string_expr_lable").add("value", n.getValue()));
		
		nodes.add(node);
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
	public void visit(MetaRangePEG n) {
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
		
		ST node = groupTemplate.getInstanceOf("node");
		node.add("parent", parent);
		nodeName = "AddExpr" + c_expr++;
		node.add("node", nodeName);
		node.add("lable", groupTemplate.getInstanceOf("add_expr_lable"));
		
		nodes.add(node);
		
		String s = nodeName;
		parent = s;
		n.getLeft().accept(this);
		parent = s;
		n.getRight().accept(this);
	}

	@Override
	public void visit(And n) {
		// TODO Auto-generated method stub
		
		ST node = groupTemplate.getInstanceOf("node");
		
		node.add("parent", parent);
		nodeName = "AndExpr" + c_expr++;
		node.add("node", nodeName);
		node.add("lable", groupTemplate.getInstanceOf("and_expr_lable"));
		
		nodes.add(node);
		
		String s = nodeName;
		parent = s;
		n.getLeft().accept(this);
		parent = s;
		n.getRight().accept(this);
	}

	@Override
	public void visit(Compose n) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Concat n) {
		// TODO Auto-generated method stub
		
		ST node = groupTemplate.getInstanceOf("node");
		
		node.add("parent", parent);
		nodeName = "ConcatExpr" + c_expr++;
		node.add("node", nodeName);
		node.add("lable", groupTemplate.getInstanceOf("concat_expr_lable"));
		
		nodes.add(node);
		
		String s = nodeName;
		parent = s;
		n.getLeft().accept(this);
		parent = s;
		n.getRight().accept(this);
		
	}

	@Override
	public void visit(Div n) {
		// TODO Auto-generated method stub
		
		ST node = groupTemplate.getInstanceOf("node");
		
		node.add("parent", parent);
		nodeName = "DivExpr" + c_expr++;
		node.add("node", nodeName);
		node.add("lable", groupTemplate.getInstanceOf("div_expr_lable"));
		
		nodes.add(node);
		
		String s = nodeName;
		parent = s;
		n.getLeft().accept(this);
		parent = s;
		n.getRight().accept(this);
	}

	@Override
	public void visit(Equals n) {
		// TODO Auto-generated method stub
		
		ST node = groupTemplate.getInstanceOf("node");
		
		node.add("parent", parent);
		nodeName = "EqualsExpr" + c_expr++;
		node.add("node", nodeName);
		node.add("lable", groupTemplate.getInstanceOf("equals_expr_lable"));
		
		nodes.add(node);
		
		String s = nodeName;
		parent = s;
		n.getLeft().accept(this);
		parent = s;
		n.getRight().accept(this);
		
	}

	@Override
	public void visit(Greater n) {
		// TODO Auto-generated method stub
		
		ST node = groupTemplate.getInstanceOf("node");
		
		node.add("parent", parent);
		nodeName = "GreaterExpr" + c_expr++;
		node.add("node", nodeName);
		node.add("lable", groupTemplate.getInstanceOf("gt_expr_lable"));
		
		nodes.add(node);
		
		String s = nodeName;
		parent = s;
		n.getLeft().accept(this);
		parent = s;
		n.getRight().accept(this);
	}

	@Override
	public void visit(GreaterEq n) {
		// TODO Auto-generated method stub
		
		ST node = groupTemplate.getInstanceOf("node");
		
		node.add("parent", parent);
		nodeName = "GreaterEqualsExpr" + c_expr++;
		node.add("node", nodeName);
		node.add("lable", groupTemplate.getInstanceOf("ge_expr_lable"));
		
		nodes.add(node);
		
		String s = nodeName;
		parent = s;
		n.getLeft().accept(this);
		parent = s;
		n.getRight().accept(this);
	}

	@Override
	public void visit(Less n) {
		// TODO Auto-generated method stub
		
		ST node = groupTemplate.getInstanceOf("node");
		
		node.add("parent", parent);
		nodeName = "LessExpr" + c_expr++;
		node.add("node", nodeName);
		node.add("lable", groupTemplate.getInstanceOf("lt_expr_lable"));
		
		nodes.add(node);
		
		String s = nodeName;
		parent = s;
		n.getLeft().accept(this);
		parent = s;
		n.getRight().accept(this);
	}

	@Override
	public void visit(LessEq n) {
		// TODO Auto-generated method stub
		
		ST node = groupTemplate.getInstanceOf("node");
		
		node.add("parent", parent);
		nodeName = "LessEqualsExpr" + c_expr++;
		node.add("node", nodeName);
		node.add("lable", groupTemplate.getInstanceOf("le_expr_lable"));
		
		nodes.add(node);
		
		String s = nodeName;
		parent = s;
		n.getLeft().accept(this);
		parent = s;
		n.getRight().accept(this);
		
	}

	@Override
	public void visit(MapAcces n) {
		// TODO Auto-generated method stub

                ST node = groupTemplate.getInstanceOf("node");
                node.add("parent", parent);
                nodeName = "mapAcces" + c_expr++; 
                node.add("node", nodeName);
                node.add("lable", groupTemplate.getInstanceOf("mapAcces_expr_lable"));
                nodes.add(node);
  
                String s = nodeName;
                parent = s;
                n.getMap().accept(this);
                parent = s;
                n.getIndex().accept(this);
		
	}

	@Override
	public void visit(MapExtension n) {
		// TODO Auto-generated method stub

                ST node = groupTemplate.getInstanceOf("node");
                node.add("parent", parent);
                nodeName = "mapAcces" + c_expr++;
                node.add("node", nodeName);    
                node.add("lable", groupTemplate.getInstanceOf("mapExtension_expr_lable"));
                nodes.add(node);
 
                String s = nodeName;
                parent = s;
                n.getMap().accept(this);
                parent = s;
                n.getKey().accept(this);
                parent = s;
                n.getValue().accept(this);
		
	}

	@Override
	public void visit(Mod n) {
		// TODO Auto-generated method stub
		
		ST node = groupTemplate.getInstanceOf("node");
		
		node.add("parent", parent);
		nodeName = "ModExpr" + c_expr++;
		node.add("node", nodeName);
		node.add("lable", groupTemplate.getInstanceOf("mod_expr_lable"));
		
		nodes.add(node);
		
		String s = nodeName;
		parent = s;
		n.getLeft().accept(this);
		parent = s;
		n.getRight().accept(this);
		
	}

	@Override
	public void visit(Mult n) {
		// TODO Auto-generated method stub
		
		ST node = groupTemplate.getInstanceOf("node");
		
		node.add("parent", parent);
		nodeName = "MultExpr" + c_expr++;
		node.add("node", nodeName);
		node.add("lable", groupTemplate.getInstanceOf("mul_expr_lable"));
		
		nodes.add(node);
		
		String s = nodeName;
		parent = s;
		n.getLeft().accept(this);
		parent = s;
		n.getRight().accept(this);
	}

	@Override
	public void visit(Not n) {
		// TODO Auto-generated method stub
		ST node = groupTemplate.getInstanceOf("node");
		
		node.add("parent", parent);
		nodeName = "NotExpr" + c_expr++;
		node.add("node", nodeName);
		node.add("lable", groupTemplate.getInstanceOf("not_expr_lable"));
		
		nodes.add(node);
		
		parent = nodeName;
		n.getExpr().accept(this);
		
	}

	@Override
	public void visit(NotEq n) {
		// TODO Auto-generated method stub
		
		ST node = groupTemplate.getInstanceOf("node");
		
		node.add("parent", parent);
		nodeName = "NotEqualsExpr" + c_expr++;
		node.add("node", nodeName);
		node.add("lable", groupTemplate.getInstanceOf("no_equals_expr_lable"));
		
		nodes.add(node);
		
		String s = nodeName;
		parent = s;
		n.getLeft().accept(this);
		parent = s;
		n.getRight().accept(this);
		
	}

	@Override
	public void visit(Or n) {
		// TODO Auto-generated method stub
		
		ST node = groupTemplate.getInstanceOf("node");
		
		node.add("parent", parent);
		nodeName = "OrExpr" + c_expr++;
		node.add("node", nodeName);
		node.add("lable", groupTemplate.getInstanceOf("or_expr_lable"));
		
		nodes.add(node);
		
		String s = nodeName;
		parent = s;
		n.getLeft().accept(this);
		parent = s;
		n.getRight().accept(this);
	}

	@Override
	public void visit(Sub n) {
		// TODO Auto-generated method stub
		
		ST node = groupTemplate.getInstanceOf("node");
		
		node.add("parent", parent);
		nodeName = "SubExpr" + c_expr++;
		node.add("node", nodeName);
		node.add("lable", groupTemplate.getInstanceOf("sub_expr_lable"));
		
		nodes.add(node);
		
		String s = nodeName;
		parent = s;
		n.getLeft().accept(this);
		parent = s;
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
		
		ST node = groupTemplate.getInstanceOf("node");
		
		node.add("parent", parent);
		nodeName = "AndPeg" + c_peg++;
		node.add("node", nodeName);
		node.add("lable", groupTemplate.getInstanceOf("and_peg_lable"));
		
		nodes.add(node);
		
		parent = nodeName;
		n.getPegExp().accept(this);
	}

	@Override
	public void visit(AnyPEG n) {
		// TODO Auto-generated method stub
		
		ST node = groupTemplate.getInstanceOf("node");
		
		node.add("parent", parent);
		nodeName = "AnyPeg" + c_peg++;
		node.add("node", nodeName);
		node.add("lable", groupTemplate.getInstanceOf("any_peg_lable"));
		
		nodes.add(node);
		
	}

	@Override
	public void visit(BindPEG n) {
		// TODO Auto-generated method stub

		ST node = groupTemplate.getInstanceOf("node");
		
		node.add("parent", parent);
		nodeName = "BindPeg" + c_peg++;
		node.add("node", nodeName);
		node.add("lable", groupTemplate.getInstanceOf("bind_peg_lable"));
		
		nodes.add(node);
		
		parent = nodeName;
		n.getExpr().accept(this);
	
	}
	 

	@Override
	public void visit(RangePEG n) {
	    ST node = groupTemplate.getInstanceOf("node");

	    node.add("parent", parent);
	    nodeName = "rangePeg" + c_peg++;
	    node.add("node", nodeName);

	    ST label = groupTemplate.getInstanceOf("range_peg_label");
	    CharInterval c = n.getInterval();
	    label.add("ranges", c.toString());
	    node.add("lable", label);

	    nodes.add(node);
	}

	@Override
	public void visit(ChoicePEG n) {		
		ST node = groupTemplate.getInstanceOf("node");
		
		node.add("parent", parent);
		nodeName = "choicePeg" + c_peg++;
		node.add("node", nodeName);
		node.add("lable", groupTemplate.getInstanceOf("choice_peg_lable"));
		
		nodes.add(node);
		
		String s = nodeName;
		parent = s;
		
		n.getLeftPeg().accept(this);
		parent = s;
		n.getRightPeg().accept(this);
		
		
	}

	@Override
	public void visit(ConstraintPEG n) {
		// TODO Auto-generated method stub
		
		ST node = groupTemplate.getInstanceOf("node");
		
		node.add("parent", parent);
		nodeName = "ConstraintPeg" + c_peg++;
		node.add("node", nodeName);
		node.add("lable", groupTemplate.getInstanceOf("constraint_peg_lable"));
		
		nodes.add(node);
		
		parent = nodeName;
		n.getExpr().accept(this);
		
	}

	@Override
	public void visit(KleenePEG n) {
		// TODO Auto-generated method stub

		ST node = groupTemplate.getInstanceOf("node");
		
		node.add("parent", parent);
		nodeName = "KleenePeg" + c_peg++;
		node.add("node", nodeName);
		node.add("lable", groupTemplate.getInstanceOf("star_peg_lable"));
		
		nodes.add(node);
		
		parent = nodeName;
		n.getPegExp().accept(this);
	}

	@Override
	public void visit(LambdaPEG n) {
		// TODO Auto-generated method stub
		
		ST node = groupTemplate.getInstanceOf("node");
		
		node.add("parent", parent);
		nodeName = "LambdaPeg" + c_peg++;
		node.add("node", nodeName);
		node.add("lable", groupTemplate.getInstanceOf("lambda_peg_lable"));
		
		nodes.add(node);
		
	}

	@Override
	public void visit(LitPEG n) {
		// TODO Auto-generated method stub
		
		ST node = groupTemplate.getInstanceOf("node");
		
		node.add("parent", parent);
		nodeName = "LiteralPeg" + c_peg++;
		node.add("node", nodeName);
		node.add("lable", groupTemplate.getInstanceOf("literal_peg_lable").add("value", n.getLit()));
		
		nodes.add(node);
		
	}

	@Override
	public void visit(NonterminalPEG n) {
		// TODO Auto-generated method stub
		
		ST node = groupTemplate.getInstanceOf("node");
		
		node.add("parent", parent);
		nodeName = "NonterminalPeg" + c_peg++;
		node.add("node", nodeName);
		node.add("lable", groupTemplate.getInstanceOf("nonterminal_peg_lable").add("name", n.getName()));
		
		nodes.add(node);
		
		String s = nodeName;
		
		for(Expr e: n.getArgs()) {
			
			parent = s;
			e.accept(this);
		}
	}

	@Override
	public void visit(NotPEG n) {
		// TODO Auto-generated method stub
		
		ST node = groupTemplate.getInstanceOf("node");
		
		node.add("parent", parent);
		nodeName = "NotPeg" + c_peg++;
		node.add("node", nodeName);
		node.add("lable", groupTemplate.getInstanceOf("not_peg_lable"));
		
		nodes.add(node);
		
		parent = nodeName;
		n.getPegExp().accept(this);
	}

	@Override
	public void visit(OptionalPEG n) {
		// TODO Auto-generated method stub
		ST node = groupTemplate.getInstanceOf("node");
		
		node.add("parent", parent);
		nodeName = "OptionalPeg" + c_peg++;
		node.add("node", nodeName);
		node.add("lable", groupTemplate.getInstanceOf("optional_peg_lable"));
		
		nodes.add(node);
		
		parent = nodeName;
		n.getPegExp().accept(this);
	}

	@Override
	public void visit(PKleene n) {
		// TODO Auto-generated method stub
		
		ST node = groupTemplate.getInstanceOf("node");
		
		node.add("parent", parent);
		nodeName = "PlusKleenePeg" + c_peg++;
		node.add("node", nodeName);
		node.add("lable", groupTemplate.getInstanceOf("plus_peg_lable"));
		
		nodes.add(node);
		
		parent = nodeName;
		n.getPegExp().accept(this);
	}

	@Override
	public void visit(RulePEG n) {
		// TODO Auto-generated method stub
		//nodes = new ArrayList<ST>();
		ST r = groupTemplate.getInstanceOf("rule");
        r.add("rname", n.getRuleName()); // set the rule name
		r.add("nodeName", n.getRuleName().concat("Rule")); // set the dot node name
		nodes = new ArrayList<ST>();
		// visit the parsing expression
		parent = n.getRuleName().concat("Rule");
		// setting annotation 
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
		
		ST nodeInh = groupTemplate.getInstanceOf("nodeLabels");
		nodeInh.add("parent",parent);
		nodeInh.add("node","inh" + c_assig++);
		
		if(n.getInh().size() > 0){
			
            ArrayList<ST> inhAttr = new ArrayList<ST>(); 
            
            for(Pair<Type, String>i : n.getInh()) {
			
                i.getFirst().accept(this);
                inhAttr.add(groupTemplate.getInstanceOf("inh").add("attr",i.getSecond()));
			
            }
            
            nodeInh.add("label",inhAttr);
            nodes.add(nodeInh);
		}
		
		if(n.getSyn().size() > 0) {
			
			ST node = groupTemplate.getInstanceOf("node");
			
			node.add("parent", parent);
			nodeName = "SynAtt" + c_assig++;
			node.add("node", nodeName);
			node.add("lable", groupTemplate.getInstanceOf("syn"));
			
			String s = parent;
			parent = nodeName;
			
			for(Expr syn: n.getSyn()) {
				
				syn.accept(this);
			}
			
			parent = s;
			nodes.add(node);
		}
		
		n.getPeg().accept(this);
		r.add("peg", nodes); // setting parsing expression propriety	
	
		template.add("rule",r);
	}

	@Override
	public void visit(SeqPEG n) {
		// TODO Auto-generated method stub
	
		ST node = groupTemplate.getInstanceOf("node"); // get a node template
		node.add("parent", parent); // set the attribute parent node
		nodeName = "SequencePeg" + c_peg++; // set the current node name
		node.add("node", nodeName); //  set current node name
		node.add("lable", groupTemplate.getInstanceOf("sequence_peg_lable")); // set node label
		nodes.add(node);

		String s = nodeName; // save the node name
		for(APEG p : n.getPegs()) {
			parent = s; // set the new parent node name
			// visit a parsing expression
			p.accept(this);
		}	
	}

	@Override
	public void visit(UpdatePEG n) {
		// TODO Auto-generated method stub
		
		ST node = groupTemplate.getInstanceOf("node");
		
		node.add("parent", parent);
		nodeName = "UpdatePeg" + c_peg++;
		node.add("node", nodeName);
		node.add("lable", groupTemplate.getInstanceOf("update_peg_lable"));
		
		nodes.add(node);
		
		String s = nodeName;
		
		ST aux_node;
		for(Pair<Attribute, Expr>a : n.getAssigs()) {
			
			aux_node = groupTemplate.getInstanceOf("node");
			
			parent = s;
			aux_node.add("parent", parent);
			nodeName = "assignmentNode" + c_assig++;
			aux_node.add("node", nodeName);
			
			parent = s;
			a.getFirst().accept(this);
			parent = s;
			a.getSecond().accept(this);
			aux_node.add("lable", groupTemplate.getInstanceOf("assign_lable").add("var", this.var));
			
			nodes.add(aux_node);
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
	public void visit(TyMetaExpr n) {
		// TODO Auto-generated method stub
	}

	@Override
	public void visit(TyMetaPeg n) {
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
		// rendering the template
		String saida = template.render();
		try {
			out.write(saida);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void visit(TyList n){}
	public void visit(ListAcces n){}
	public void visit(ListLit n){}
}
