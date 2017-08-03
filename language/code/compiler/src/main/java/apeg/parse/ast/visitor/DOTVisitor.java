package apeg.parse.ast.visitor;

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

import java.io.FileWriter;
import java.io.IOException;

import java.util.Hashtable;


public class DOTVisitor implements ASTNodeVisitor {
    
	private FileWriter out;
	private Hashtable<ElementVisitor,Integer> ht;
	private int counter;
	
	public DOTVisitor(String filepath){
		try{
			ht = new Hashtable<ElementVisitor,Integer>();
			out = new FileWriter(filepath);
			out.write("Digraph G {");
			counter =0;
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void closeFile(){
		try{
			out.write("}");
			out.close();
		}catch(IOException e){
			System.out.println("Falha ao escrever no arquivo");
		}
	}
	
	private Integer numNode(ElementVisitor n){
	   	Integer v = ht.get(n); 
		if(v != null){
	   		return v;
	   	}else{
	   		v = new Integer(counter++); 
	   		ht.put(n, v);
	   	}
		return v;
	}
	
	private String mkNode(Integer n, String type){
		return "        no" + n.toString() + " [ shape = \"box\", label=\""+ type +"\" ];\n"; 
	}
	
	private String mkEdge(Integer n1, Integer n2){
		return  "        no" + n1.toString() + " -> no" + n2.toString() + "\n"; 
	}
	
	private void save(String s){
		try{
			out.write(s);
		}catch(IOException e){
			System.out.println("Falaha ao escrever \"" + s + "\" para o arquivo");
		}
	}
	
	public void visit(AndExprNode expr) {
  	    String d = "";
		expr.getLeftExpr().accept(this);
		expr.getRightExpr().accept(this);
		Integer n = numNode(expr);
		Integer f1 = numNode(expr.getLeftExpr());
		Integer f2 = numNode(expr.getRightExpr());
	    d = mkNode(n,"&&");
	    d += mkEdge(n,f1);
	    d += mkEdge(n,f2);
	    save(d);
	}

	@Override
	public void visit(AttributeExprNode expr) { 		
		String d = "";
		expr.getName();
		Integer n = numNode(expr);
	    d = mkNode(n,"Atribute:" + expr.getName());
	    save(d);
			
	}

	@Override
	public void visit(BinaryExprNode expr) { 
		
				String d = "";
				Integer n = numNode(expr);
				
				expr.getLeftExpr().accept(this);
				expr.getRightExpr().accept(this);
				
				
				Integer f1 = numNode(expr.getLeftExpr());
				Integer f2 = numNode(expr.getRightExpr());
				//Integer f3 = numNode(expr.getOperation());
			    switch(expr.getOperation()){
			        case GT: d = mkNode(n,"BinaryExpr (>)"); break;
			        case GE: d = mkNode(n,"BinaryExpr (>=)"); break;
			        case LT: d = mkNode(n,"BinaryExpr (<)"); break;
			        case LE: d = mkNode(n,"BinaryExpr (<=)"); break;
			        case ADD: d = mkNode(n,"BinaryExpr (+)"); break;
			        case SUB: d = mkNode(n,"BinaryExpr (-)"); break;
			        case MUL: d = mkNode(n,"BinaryExpr (*)"); break;
			        case DIV: d = mkNode(n,"BinaryExpr (/)"); break;
			        case MOD: d = mkNode(n,"BinaryExpr (%)"); break;
			        
			    }
			    d += mkEdge(n,f1);
			    d += mkEdge(n,f2);
			    save(d);
		}

		@Override
		public void visit(BooleanExprNode expr) {
			String d = "";
			expr.getValue();
			Integer n = numNode(expr);
		    d = mkNode(n,"BooleanExpr:" + expr.getValue());
		    save(d);
		}

		@Override
		public void visit(CallExprNode expr) { 
		       Integer n = numNode(expr);
		       Integer i;
		       String d = mkNode(n,"CallExprNode: " + expr.getName());
		       for(ExprNode p:expr.getParameters()) {
			     i = numNode(p);
			     p.accept(this);
			     d+= mkEdge(n,i);	
		       }	
		       save(d);  		
		}

		@Override
		public void visit(EqualityExprNode expr) { //equality type? 
			String d = "";
			expr.getLeftExpr().accept(this);
			expr.getRightExpr().accept(this);
			expr.getEqualityType();
			
			Integer n = numNode(expr);
			Integer f1 = numNode(expr.getLeftExpr());
			Integer f2 = numNode(expr.getRightExpr());
		    d = mkNode(n,"EqualityExpr");
		    
		    switch(expr.getEqualityType()){
		    
	        case EQ: d = mkNode(n,"EqualityExpr (=)"); 
	        break;
	        case NE: d = mkNode(n,"EqualityExpr (!=)"); 
	        break;     
	    }
		    
		    d += mkEdge(n,f1);
		    d += mkEdge(n,f2);
		    save(d);
		}

		@Override
		public void visit(FloatExprNode expr) { 
			String d = "";
			expr.getValue();
			Integer n = numNode(expr);
			d = mkNode(n,"Float:" + expr.getValue());
			save(d);
		}

		@Override
		public void visit(IntExprNode expr) { 
			    String d = "";
				expr.getValue();
				Integer n = numNode(expr);
			    d = mkNode(n,"Int:" + expr.getValue());
			    save(d);
		}

		@Override
		public void visit(MetaPegExprNode expr) { 
			    String d = "";
				expr.getExpr().accept(this);
				Integer n = numNode(expr);
				Integer f1 = numNode(expr.getExpr());
			    d = mkNode(n,"@[MetaExpr]");
			    d += mkEdge(n,f1);
			    save(d);
		}

		@Override
		public void visit(MinusExprNode expr) { 
			String d = "";
			expr.getExpr().accept(this);
			Integer n = numNode(expr);
			Integer f1 = numNode(expr.getExpr());
		    d = mkNode(n,"-");
		    d += mkEdge(n,f1);
		    save(d);
		}

		@Override
		public void visit(NotExprNode expr) { 
			String d = "";
			expr.getExpr().accept(this);
			Integer n = numNode(expr);
			Integer f1 = numNode(expr.getExpr());
		    d = mkNode(n,"!(NotExpr)");
		    d += mkEdge(n,f1);
		    save(d);
		}

		@Override
		public void visit(OrExprNode expr) {
		    String d = "";
			expr.getLeftExpr().accept(this);
			expr.getRightExpr().accept(this);
			Integer n = numNode(expr);
			Integer f1 = numNode(expr.getLeftExpr());
			Integer f2 = numNode(expr.getRightExpr());
		    d = mkNode(n,"||");
		    d += mkEdge(n,f1);
		    d += mkEdge(n,f2);
		    save(d);
		}

		@Override
		public void visit(StringExprNode expr) {
			String d = "";
			expr.getValue();
			Integer n = numNode(expr);
			d = mkNode(n,"StringValue:" + expr.getValue());
			save(d);
		}

		@Override
		public void visit(AndPegNode peg) { 
			String d = "";
			peg.getPeg().accept(this);
			Integer n = numNode(peg);
			Integer f1 = numNode(peg.getPeg());
		    d = mkNode(n,"&&(AndPeg)");
		    d += mkEdge(n,f1);
		    save(d);
		}

		@Override
		public void visit(AnyPegNode peg) { 
			
	    //peg.accept(this);
	    
		}

		@Override
		public void visit(BindPegNode peg) {
			String d = "";
			peg.getPeg().accept(this);
			peg.getVariable();
			Integer n = numNode(peg);
			Integer f2 = numNode(peg.getPeg());
		    d = mkNode(n," = (BindPeg)");
		    d = mkNode(n,"Variable:" + peg.getVariable());
		    d += mkEdge(n,f2);
		    save(d);
		    }

		@Override
		public void visit(ChoicePegNode peg) {
			String d = "";
			peg.getLeftPeg().accept(this);
			peg.getRightPeg().accept(this);
			Integer n = numNode(peg);
			Integer f1 = numNode(peg.getRightPeg());
			Integer f2 = numNode(peg.getLeftPeg());
		    d = mkNode(n,"/");
		    d += mkEdge(n,f1);
		    d += mkEdge(n,f2);	
		    save(d);
		}

		@Override
		public void visit(ConstraintPegNode peg) {
			String d = "";
			peg.getExpr().accept(this);
			Integer n = numNode(peg);
			Integer f1 = numNode(peg.getExpr());
		    d = mkNode(n,"{?ConstraintPeg}");
		    d += mkEdge(n,f1);
		    save(d);
		}

		@Override
		public void visit(GroupPegNode peg) {
			  String d = "";
			  peg.getRanges();
			  Integer n = numNode(peg);
			  d = mkNode(n,"GroupPeg:" +peg.getRanges());
			  save(d);
		}

		@Override
		public void visit(LambdaPegNode peg) { // ??
			
			 //peg.accept(this); 
			// peg_expr = groupTemplate.getInstanceOf("lambda");
	    
		}

		@Override
		public void visit(LiteralPegNode peg) {
			    String d = "";
			    peg.getValue();
				Integer n = numNode(peg);
				d = mkNode(n,"LiteralPegNode:" + peg.getValue());	
				save(d);
		}

		@Override
		public void visit(NonterminalPegNode peg) {
		
		       Integer n = numNode(peg);
		       Integer i;
		       String d = mkNode(n,"NonTerminalPegNode: " + peg.getName());
		       for(ExprNode p:peg.getExprs()) {
			     i = numNode(p);
			     p.accept(this);
			     d+=mkEdge(n,i);	
		       }	
		       save(d);
		}
		@Override
		public void visit(NotPegNode peg) {	
                String d = "";
			    peg.getPeg().accept(this);
				Integer n = numNode(peg);
				Integer f1 = numNode(peg.getPeg());
			    d = mkNode(n,"!.");
			    d += mkEdge(n,f1);
			    save(d);
		}
		

		@Override
		public void visit(OptionalPegNode peg) {
			
			  String d = "";
			  peg.getPeg().accept(this);
			  Integer n = numNode(peg);
			  Integer f1 = numNode(peg.getPeg());	
			  d = mkNode(n,"OptionalPeg");
			  d += mkEdge(n,f1);	
			  save(d);
		}

		@Override
		public void visit(PlusPegNode peg) {
			    String d = "";
			    peg.getPeg().accept(this);
				Integer n = numNode(peg);
				Integer f1 = numNode(peg.getPeg());
			    d = mkNode(n,"(PlusPeg)+");
			    d += mkEdge(n,f1);
			    save(d);
		}

		@Override
		public void visit(SequencePegNode peg) {
 
			    Integer n = numNode(peg);
				Integer i;
				String d = mkNode(n,"SequencePegNode");
				for(PegNode p:peg.getPegs()) {
					i = numNode(p);
					p.accept(this);
					d += mkEdge(n,i);	
				}	
				save(d);
		}

		@Override
		public void visit(StarPegNode peg) {
			    String d = "";
				peg.getPeg().accept(this);
				Integer n = numNode(peg);
				Integer f1 = numNode(peg.getPeg());
			    d = mkNode(n,"(StarPegNode)*");
			    d += mkEdge(n,f1);	   
			    save(d);
		}

		@Override
		public void visit(UpdatePegNode peg) {
			
			Integer n = numNode(peg);
			Integer i;
			String d = mkNode(n,"[UpdatePegNode]");
			for(AssignmentNode p:peg.getAssignments()) {
				i = numNode(p);
				p.accept(this);
				d += mkEdge(n,i);	
			}	
			save(d);
		}

		@Override
		public void visit(AssignmentNode assign) {
			  String d = "";
			  assign.getExpr().accept(this);
			 
			  Integer n = numNode(assign);
			  Integer f1 = numNode(assign.getExpr());
			  d = mkNode(n,"Assignment: " + assign.getVariable());
			  d += mkEdge(n,f1);	  
			  save(d);
		}
		

		@Override
		public void visit(FunctionNode func) {
			  String d = "";
			  func.getName();
			  Integer n = numNode(func);
			  d = mkNode(n,"Func:" + func.getName());
			  save(d);
		}

		@Override
		public void visit(GrammarNode grammar) {
			    Integer i;
			    Integer p = numNode(grammar);
			    String d = "";
			    grammar.getFunctions();
				grammar.getFunctionsSources();
				grammar.getName();
				grammar.getOptions();
				grammar.getPreamble();
				grammar.getRules();
				
				d = mkNode(p,"Grammar:" + grammar.getName());
//				d = mkNode(n,"GrammarFunctionSource:" + grammar.getFunctionsSources());
//				d = mkNode(n,"GrammarGetName:" + grammar.getName());
//				d = mkNode(n,"GrammarGetOption:" + grammar.getOptions());
//				d = mkNode(n,"GrammarGetPreamble:" + grammar.getPreamble());
				for(RuleNode n : grammar.getRules()){
					i = numNode(n);
					n.accept(this);
					d += mkEdge(p,i);
				}
				
				save(d);
		}

		@Override
		public void visit(RuleNode rule) {
			
			  Integer i;
			  Integer n = numNode(rule);
			  String d = "";

			  rule.getAnnotation();
			  rule.getExpr().accept(this);
			  rule.getName();
			  rule.getParameters();
			  rule.getReturns();
			 
			  Integer f2 = numNode(rule.getExpr());
		      d = mkNode(n,"RuleAnnotation:" + rule.getAnnotation());  
			  d = mkNode(n,"RuleGetName:" + rule.getName());
			  d += mkEdge(n,f2);	
			  
			  for(VarDeclarationNode p : rule.getReturns()){
					i = numNode(p);
					p.accept(this);
					d += mkEdge(n,i);
				}
			  
			  for(VarDeclarationNode p : rule.getParameters()){
					i = numNode(p);
					p.accept(this);
					d += mkEdge(n,i);
				}
			  
			  save(d);
		}

		@Override
		public void visit(TypeNode type) {	
			  String d = "";
			  type.getName();
			  Integer n = numNode(type);
			  d = mkNode(n,"TypeNode:" + type.getName());	
			  save(d);
		}

		@Override
		public void visit(VarDeclarationNode var) {
			
			  String d = "";
			  var.getName();
			  var.getType().accept(this);
			  Integer n = numNode(var);
			  d = mkNode(n,"Var");
			  d = mkNode(n,"DeclarationName:" + var.getName());
			  Integer f2 = numNode(var.getType());	
			  d += mkEdge(n,f2);
			  save(d);
		}
}