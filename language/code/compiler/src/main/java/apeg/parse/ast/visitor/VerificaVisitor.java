package apeg.parse.ast.visitor;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import org.stringtemplate.v4.ST;
import apeg.parse.ast.*;
import apeg.parse.ast.visitor.Environments.Environment;
import apeg.parse.ast.visitor.Environments.NTType;
import apeg.parse.ast.visitor.Environments.TypeError;
import apeg.parse.ast.visitor.Environments.VarType;


public class VerificaVisitor implements ASTNodeVisitor {
	
	
	private String currentRule;
	private int ruleCount;
	private String startRule;
	private Environment<String,NTType> ruleTable;
	private Set<String> ruleNames;
	private ArrayList<String> erros;
	private ArrayList<String> warnings;	
	private Stack<TypeNode> stk; 
	private Environment<String,VarType> varTable;
	private Environment<String,ArrayList<NTType>> func; // Ambeinte de typos de operadores e funcoes
	
	//private NTType ntt;
	TypeNode tipos;
	
	
	 public VerificaVisitor(Environment<String,NTType> r, Environment<String,ArrayList<NTType>> f){
		 
		 erros = new ArrayList<String>();
		 warnings = new ArrayList<String>();
		 ruleCount = 0;
		 currentRule = "";
		 startRule = "";
		 ruleTable = r;
		 ruleNames = ruleTable.getNames();
		 varTable = new Environment<String,VarType>();
		 stk = new Stack<TypeNode>();
		 func = f;
	 }
	 
	 private TypeNode resolveOp(String op, TypeNode e, TypeNode d){
	    TypeNode r = null;
	    ArrayList<NTType> arr = func.get(op);
	    if(arr != null){
	    	for(NTType ty : arr){
	    		if(ty.getParamAt(0).match(e) && ty.getParamAt(0).match(d)){
	    			r = ty.getReturnAt(0);
	    			break;
	    		}
	    	}
	    }
	    return r;
	 }
	 
	 private void binOp(BinaryExprNode expr){
		TypeNode result = null;
		if(stk.size()<2){
			erros.add("("+ expr.getLine()+","+ expr.getColunm()+"): Numero de operandos insuficientes para o operador and");
		}else{
			TypeNode te = stk.pop();
			TypeNode td = stk.pop();
			result = resolveOp(expr.getOperation().name(),te,td);
			if(result == null){
				erros.add("("+ expr.getLine()+","+ expr.getColunm()+"): Nenhuma definição do operador "+ expr.getOperation().name() +"se aplica aod tipo " + te.getName() + " e " + td.getName());
			    result = new TypeError();
			}
			stk.push(result);
		}
	}
	 
	@Override
	public void visit(AndExprNode expr) {
		expr.getLeftExpr().accept(this);
		expr.getRightExpr().accept(this);
		
	}
	

	@Override
	public void visit(AttributeExprNode expr) {
		expr.getName();
		
	}

	@Override
	public void visit(BinaryExprNode expr) {
		expr.getLeftExpr().accept(this);
		expr.getRightExpr().accept(this);
		binOp(expr);
		
//		switch(expr.getOperation()) {
//		case GT: // >
//			
//			break;
//		case GE: // >=
//			
//			break;
//		case LT: // <
//			
//			break;
//		case LE: // <=
//			
//			break;
//		case ADD: // +
//		
//			break;
//		case SUB: // -
//			
//			break;
//		case MUL: // *
//			
//			break;
//		case DIV: // /
//			
//			break;
//		case MOD: // %
//			
//			break;
//		default: // Should never reach the default case
//			break;
//	}
		
	}

	@Override
	public void visit(BooleanExprNode expr) { ////////////////////////////////////////////
		stk.push(new BooleanTypeNode());
	}

	@Override
	public void visit(CallExprNode expr) {
		// TODO Auto-generated method stub
		expr.getName();

		for(ExprNode p : expr.getParameters()){
			p.accept(this);
		}
	
	}

	@Override
	public void visit(EqualityExprNode expr) { 
		expr.getLeftExpr().accept(this);
		expr.getRightExpr().accept(this);
		
		
		switch(expr.getEqualityType()) {
		case EQ: //==
			
			break;
		case NE: // /=
			
			break;
		default: // Should never reach the default case
			
			break;
	}
		
	}

	@Override
	public void visit(FloatExprNode expr) { ////////////////////////////
		stk.push(new FloatTypeNode());	
	}

	@Override
	public void visit(IntExprNode expr) { ////////////////////////////////
		stk.push(new IntTypeNode());
	}

	@Override
	public void visit(MetaPegExprNode expr) {
		expr.getExpr().accept(this);
	}

	@Override
	public void visit(MinusExprNode expr) {
		expr.getExpr().accept(this);
		
	}

	@Override
	public void visit(NotExprNode expr) { //bool tbem?
		expr.getExpr().accept(this); 
		
		if(stk.size()<2){
			erros.add("("+ expr.getLine()+","+ expr.getColunm()+"): Numero de operandos insuficientes para o operador and");
		}else{
			TypeNode t = stk.pop();
			if(!stk.peek().match(t)){
				if(!stk.peek().match(new BooleanTypeNode())){
					stk.pop();
					stk.push(new BooleanTypeNode());
				}
				erros.add("("+ expr.getLine()+","+ expr.getColunm()+"): Operandos de tipo incompatíveis");
			}
		}
		
	}

	@Override
	public void visit(OrExprNode expr) {
		
		expr.getLeftExpr().accept(this);
		expr.getRightExpr().accept(this);
		
		if(stk.size()<2){
			erros.add("("+ expr.getLine()+","+ expr.getColunm()+"): Numero de operandos insuficientes para o operador and");
		}else{
			TypeNode t = stk.pop();
			if(!stk.peek().match(t)){ 
				if(!stk.peek().match(new BooleanTypeNode())){
					stk.pop();
					stk.push(new BooleanTypeNode());
				}
				erros.add("("+ expr.getLine()+","+ expr.getColunm()+"): Operandos de tipo incompatíveis");
			}
		}
		
	}

	@Override
	public void visit(StringExprNode expr) { // oq faco em relacao a string?
		stk.push(new StringTypeNode());
		
		
	}

	@Override
	public void visit(AndPegNode peg) {
		peg.getPeg().accept(this);
		
	}

	@Override
	public void visit(AnyPegNode peg) {
		
	}

	@Override
	public void visit(BindPegNode peg) {
		peg.getPeg().accept(this);
		peg.getVariable();	//se o tipo e string
	}

	@Override
	public void visit(ChoicePegNode peg) {
		peg.getLeftPeg().accept(this);
		peg.getRightPeg().accept(this);
	}

	@Override
	public void visit(ConstraintPegNode peg) {
		peg.getExpr().accept(this);
	}

	@Override
	public void visit(GroupPegNode peg) {
		peg.getRanges();
	}

	@Override
	public void visit(LambdaPegNode peg) {
	}

	@Override
	public void visit(LiteralPegNode peg) {
		peg.getValue();
	}

	@Override
	public void visit(NonterminalPegNode peg) { ////////////////////////////////
	
		for(ExprNode p:peg.getExprs()) { p.accept(this); }
	    
	     // **O Não terminal utilizado nao foi definido**
	     if(!ruleTable.contains(peg.getName()) ){ 
	    	 //System.out.println("(" + peg.getLine() + ", " + peg.getColunm()+ ") : O nao terminal " +peg.getName()+ " chamado na regra "+ currentRule +" nao existe");
	     erros.add("(" +peg.getLine() + ", " + peg.getColunm()+ ") : O nao terminal " +peg.getName()+ " chamado na regra "+ currentRule +" nao existe");
	     }
	     
	     // **Terminais não usados**
	     if(ruleNames.contains(peg.getName())){
		    	ruleNames.remove(peg.getName());
		 }
	     
	     // **Inteiros no lugar do parametro que recebe o retorno**
	     // Quantidade de parametros passados é diferente do definido na regra em questão
	     List<ExprNode> l = peg.getExprs();
	     NTType temp = ruleTable.get(peg.getName());
	     if(temp != null){
	    	 if(temp.getNumParams() != peg.getExprs().size()){
	        	  erros.add("(" + peg.getLine() +", " + peg.getColunm() + ") Aridade de não terminal incosistente: " + 
	    	                peg.getName() + " usado com " + peg.getExprs().size() + " argumentos mas definido com " + 
	        			    temp.getNumParams() + " parametros.");
	         }else if(temp.getNumParams() > temp.getNumInherited()){
	    	    for(int i =temp.getNumInherited(); i< temp.getNumParams(); i++ ){
	    	    	if(!(l.get(i) instanceof AttributeExprNode)){
	    	    		erros.add("(" + peg.getLine() +", " + peg.getColunm() + ") Atributo variável esperado aqui, mas expressão " +l.get(i).toString() + " encontrada.");
	    	    	}
	    	    } 
	    	    
	    	    // se getnumparams == getexprs.size
	    	    
	    	    System.out.println(peg.getName() +" : num param sintetizado " + temp.getNumSintetized()); //quantidade sintetizado
	    	    
	    	    for(int i=0; i< temp.getNumSintetized(); i++){  
	    	    	System.out.println(peg.getName() +" primeiro param sintetizado " + temp.getType(i).getName());
	    	    }
	    	    
	    	    
	    	    System.out.println(peg.getName() +" : num param herdado " + temp.getNumInherited()); //quantidade herdado 
	    	    
	    	    for(int i= temp.getNumSintetized(); i< temp.getNumParams(); i++){  
	    	    	System.out.println(peg.getName() +" primeiro param sintetizado " + temp.getType(i).getName());
	    	    }
	    	    
	    	 
	    	    }
	    	 }  
	     
	     
	} 

	@Override
	public void visit(NotPegNode peg) {
		peg.getPeg().accept(this);
		
	}

	@Override
	public void visit(OptionalPegNode peg) {
		peg.getPeg().accept(this);
		
	}

	@Override
	public void visit(PlusPegNode peg) {
		peg.getPeg().accept(this);
		
	}

	@Override
	public void visit(SequencePegNode peg) {
		
		for(PegNode p:peg.getPegs()) {
			p.accept(this);
		}
		
	}

	@Override
	public void visit(StarPegNode peg) {
		peg.getPeg().accept(this);
		
	}

	@Override
	public void visit(UpdatePegNode peg) {
		for(AssignmentNode p:peg.getAssignments()) {
			p.accept(this);
		}
		
	}

	@Override
	public void visit(AssignmentNode assign) {
	   assign.getExpr().accept(this);
	   assign.getVariable();
		
	}

	//public void visit(FunctionNode func) {
	//	func.getName();
		
	//}
	
	
	public void visit(GrammarNode grammar) {
		grammar.getFunctions();
		grammar.getFunctionsSources(); //lista
		grammar.getName();
		grammar.getOptions(); //lista
		grammar.getPreamble();
		
		for(RuleNode r : grammar.getRules()){
			System.out.println("\n*******************REGRA: "+ r.getName()+"*********************");
			currentRule = r.getName();
			r.accept(this);
		}
		currentRule = null;
	}

	@Override
	public void visit(RuleNode rule) {
		rule.getAnnotation();
		rule.getExpr().accept(this);
		rule.getName();
	    	
		varTable.clear();
		
		if(ruleCount == 0){
			startRule = rule.getName();
		}
		for(VarDeclarationNode param : rule.getParameters()){
			 VarType v = new VarType(param.getType(), VarType.AttrDirection.HERDADO);
			 varTable.add(param.getName(), v);
		}
		
		for(VarDeclarationNode param : rule.getReturns()){ 
			 VarType v = new VarType(param.getType(), VarType.AttrDirection.SINTETIZADO);
			 varTable.add(param.getName(), v);	
		}
		
		ruleCount++;
	}

	public void visit(TypeNode type) {
		type.getName();
	}

	@Override
	public void visit(VarDeclarationNode var) {
		var.getName();
		var.getType().accept(this);
	}

	@Override
	public void visit(BooleanTypeNode type) {
		// TODO Auto-generated method stub
	}


	@Override
	public void visit(FloatTypeNode type) {
		// TODO Auto-generated method stub	
	}


	@Override
	public void visit(GrammarTypeNode type) {
		// TODO Auto-generated method stub
	}


	@Override
	public void visit(IntTypeNode type) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void visit(RuleTypeNode type) {
		// TODO Auto-generated method stub
	}


	@Override
	public void visit(StringTypeNode type) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void visit(UserTypeNode type) {
		// TODO Auto-generated method stub
		
	}	
	
	//Retorna um set com todas as regras nao utilizadas
	public String[] getUnusedNames(){
		return (String[])ruleNames.toArray();
	}
	//verifica se o programa possui erros, se a lista erros está vazia
	public boolean hasErrors(){
		return !erros.isEmpty();
	}
	//verifica se o programa possui erros, se a lista erros está vazia
	public boolean hasWarnings(){
		if(!ruleNames.isEmpty()){
			for(String i : ruleNames){
				if(!i.equals(startRule)){
				   warnings.add("Não terminal não utilizado: " + i);
				}
			}
		}
		return !warnings.isEmpty();
	}
	
	public ArrayList<String> getErros() {
		return erros;
	}

	public ArrayList<String> getWarnings() {
		return warnings;
	}

}
