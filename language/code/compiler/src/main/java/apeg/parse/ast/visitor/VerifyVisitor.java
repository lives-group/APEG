package apeg.parse.ast.visitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import apeg.parse.ast.*;
import apeg.parse.ast.visitor.Environments.Environment;
import apeg.parse.ast.visitor.Environments.NTInfo;
import apeg.parse.ast.visitor.Environments.NTType;
import apeg.parse.ast.visitor.Environments.TypeError;
import apeg.parse.ast.visitor.Environments.VarType;


public class VerifyVisitor extends FormalVisitor implements ASTNodeVisitor {
	
	
	private String currentRule;
	private int ruleCount;
	private String startRule;
	private Environment<String,NTInfo> ruleTable;
	private Set<String> ruleNames;
	private ArrayList<String> erros;
	private ArrayList<String> warnings;	
	private Stack<TypeNode> stk; 
	private Environment<String,ArrayList<NTType>> func; // Ambiente de typos de operadores e funcoes

	TypeNode tipos;
	
	
	 public VerifyVisitor(Environment<String,NTInfo> r, Environment<String,ArrayList<NTType>> f){
		 
		 erros = new ArrayList<String>();
		 warnings = new ArrayList<String>();
		 ruleCount = 0;
		 currentRule = "";
		 startRule = "";
		 ruleTable = r;
		 ruleNames = ruleTable.getNames();
		 stk = new Stack<TypeNode>();
		 func = f;
	 }
	 
	 /**
	  * ResolveOp is a particular case of the  resolveSymbol. 
	  *  
	  * @param op The name of the operator.
	  * @param e The type of the left argument.
	  * @param d The type of the right argument.
	  * @return The result from the operator to the types from the stack.
	  */
	 
	 private TypeNode resolveOp(String op, TypeNode e, TypeNode d){
		return resolveSymbol(op,new TypeNode[]{e,d});
	 }
	 
	 /**
	  * Resolves an overloaded symbol name to it's result type.
	  * Retrieve the symbol's types from the operator's table, and look for a type whose parameters match the 
	  * arguments. Returns the result type of the first successful match or null if no match is found.
	  *  
	  * @param op The name of operator
	  * @param t The array with the types of the parameters of the intended function call.
	  * @return  The result type from the function.
	  */ 
	 private TypeNode resolveSymbol(String op, TypeNode[] t){
		    TypeNode r = null;
		    ArrayList<NTType> arr = func.get(op);
		    if(arr != null){
		    	for(NTType ty : arr){
		    	  if(ty.matchInherited(t)){
		    			r = ty.getReturnAt(0);
		    			break;
		    	  }
		    	}
		     }
		     return r;
	 }
	 
	 /**
	  * Verify the types of the arithmetical operators.
	  * Pop two elements from the stack and match their types. This function uses the func Table that describes 
	  * the types of the parameters and the result of the operator. This function registers any errors on the 
	  * errors message list.
	  * 
	  * @param expr The EqualityExprNode to be verified.
	  */
	 
	 private void equalityBinOp(EqualityExprNode expr){ 
			TypeNode result = null;
			if(stk.size()<2){
				erros.add("("+ expr.getLine()+","+ expr.getColunm()+"): Numero de operandos insuficientes para o operador and");
			}else{
				System.out.print("EQUALITY: "); 
				pprintStak();
				TypeNode te = stk.pop();
				TypeNode td = stk.pop();
				result = resolveOp(expr.getEqualityType().name(),te,td);
				if(result == null){
					erros.add("("+ expr.getLine()+","+ expr.getColunm()+"): Nenhuma definição do operador "+ expr.getEqualityType().name() +" se aplica ao tipo " + te.getName() + " e " + td.getName());
				    result = new TypeError();
				}
				stk.push(result);
			}
		}
	 
	 /**
	  * Verify the types of the arithmetical operators.
	  * Pop two elements from the stack and match their types. This function uses the func Table that describes 
	  * the types of the parameters and the result of the operator. This function registers any errors on the 
	  * errors message list.
	  * 
	  * @param expr The LogicalBinExpr to be verified.
	  */
	 
	 private void logicalBinOp(LogicalBinExpr expr){ 
			TypeNode result = null;
			if(stk.size()<2){
				erros.add("("+ expr.getLine()+","+ expr.getColunm()+"): Numero de operandos insuficientes para o operador and");
			}else{
				System.out.print("Logical OP IN: ");
				pprintStak();
				TypeNode te = stk.pop();
				TypeNode td = stk.pop();
				result = resolveOp(expr.getOpName(),te,td);
				if(result == null){
					erros.add("("+ expr.getLine()+","+ expr.getColunm()+"): Nenhuma definição do operador "+ expr.getOpName() +" se aplica ao tipo " + te.getName() + " e " + td.getName());
				    result = new TypeError();
				}
				stk.push(result);
				System.out.print("Logical OP EXIT: ");
				pprintStak();
				
			}
		}
	 
	 /**
	  * Verify the types of the arithmetical operators and the operators >,<, >= ,<=.
	  * Pop two elements from the stack and match their types. This function uses the func Table that describes 
	  * the types of the parameters and the result of the operator. This function registers any errors on the 
	  * errors message list.
	  * 
	  * @param expr The BinaryExprNode to be verified.
	  */
	 private void arithBinOp(BinaryExprNode expr){ 
		TypeNode result = null;
		if(stk.size()<2){
			erros.add("("+ expr.getLine()+","+ expr.getColunm()+"): Numero de operandos insuficientes para o operador and");
		}else{
			System.out.print("Arith OP IN: ");
			pprintStak();
			TypeNode te = stk.pop();
			TypeNode td = stk.pop();
			result = resolveOp(expr.getOperation().name(),te,td);
			if(result == null){
				erros.add("("+ expr.getLine()+","+ expr.getColunm()+"): Nenhuma definição do operador "+ expr.getOperation().name() +" se aplica ao tipo " + te.getName() + " e " + td.getName());
			    result = new TypeError();
			}
			stk.push(result);
			System.out.print("Arith OP EXIT: ");
			pprintStak();
		}
	}
	 
	@Override
	public void visit(AndExprNode expr) {
		expr.getLeftExpr().accept(this);
		expr.getRightExpr().accept(this);
		
		logicalBinOp(expr);
	}
	
	@Override
	public void visit(NotExprNode expr) {
		expr.getExpr().accept(this); 	
		TypeNode r;
		if(!stk.isEmpty()){
		  r = resolveSymbol("NOT", new TypeNode[]{stk.peek()});
		  if(r == null){
			  erros.add("(" + expr.getLine() + "," + expr.getColunm() + " ): Nenhuma definicao do operador NOT se aplica ao tipo " + stk.peek().getName());  
		      r = new TypeError(); 
		  }
		  stk.pop();
		  stk.push(r);
		}else{
			erros.add("(" + expr.getLine() + "," + expr.getColunm() + " ): Operador NOT não está aplicado a nenhum argumento. ");  		
		}
	}

	@Override
	public void visit(OrExprNode expr){
		expr.getLeftExpr().accept(this);
		expr.getRightExpr().accept(this);	
		logicalBinOp(expr);	
	}
	

	@Override
	public void visit(AttributeExprNode expr) {	
		VarType t = ruleTable.get(currentRule).getLocals().get(expr.getName());
		if(t != null){
		  stk.push(t.getType());	
		}else{
			 erros.add("("+ expr.getLine() + "," +expr.getColunm() +"): Variavel " + expr.getName()+" nao foi declarada. ");  
		}	
	}

	@Override
	public void visit(BinaryExprNode expr) {
		expr.getLeftExpr().accept(this);
		expr.getRightExpr().accept(this);
		arithBinOp(expr);
	}

	@Override
	public void visit(BooleanExprNode expr) { ////////////////////////////////////////////
		stk.push(new BooleanTypeNode());
	}

	@Override
	public void visit(EqualityExprNode expr) { 
		expr.getLeftExpr().accept(this);
		expr.getRightExpr().accept(this);
		equalityBinOp(expr);
		
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
	public void visit(MinusExprNode expr) {
		expr.getExpr().accept(this);
		TypeNode r;
		if(!stk.isEmpty()){
		  r = resolveSymbol("MINUS", new TypeNode[]{stk.peek()});
		  if(r == null){
			  erros.add("(" + expr.getLine() + "," + expr.getColunm() + " ): Nenhuma definicao do operador MINUS se aplica ao tipo " + stk.peek().getName());  
		      r = new TypeError(); 
		  }
		  stk.pop();
		  stk.push(r);
		}else{
			erros.add("(" + expr.getLine() + "," + expr.getColunm() + " ): Operador MINUS não está aplicado a nenhum argumento. ");  		
		}
	}

	@Override
	public void visit(StringExprNode expr) { 
		stk.push(new StringTypeNode());
	}

	@Override
	public void visit(NonterminalPegNode peg) { ////////////////////////////////
	
		for(ExprNode p:peg.getExprs()) { p.accept(this); }
	    
	     // **O Não terminal utilizado nao foi definido**
	     if(!ruleTable.contains(peg.getName()) ){ 
	     erros.add("(" +peg.getLine() + ", " + peg.getColunm()+ ") : O nao terminal " +peg.getName()+ " chamado na regra "+ currentRule +" nao existe");
	     }
	     
	     // **Terminais não usados**
	     if(ruleNames.contains(peg.getName())){
		    	ruleNames.remove(peg.getName());
		 }
	     
	     // **Inteiros no lugar do parametro que recebe o retorno**
	     // Quantidade de parametros passados é diferente do definido na regra em questão
	     List<ExprNode> l = peg.getExprs();
	     NTType temp = ruleTable.get(peg.getName()).getSig();
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
	    	    }
	    	 }  
	     
	     
	} 

	@Override
	public void visit(AssignmentNode assign) {
	   assign.getExpr().accept(this);
	   VarType v = ruleTable.get(currentRule).getLocals().get(assign.getVariable());
         
	   if(v != null){
	      if(!(v.getType().match(stk.peek()))){  
    	      erros.add("("+ assign.getLine() + "," +assign.getColunm() +"): Atribuicao de tipos incompativeis: " +stk.peek().getName() + " e " + v.getType().getName());  
          }
	   }else{
		   ruleTable.get(currentRule).getLocals().add(assign.getVariable(), new VarType (stk.peek(),VarType.AttrDirection.LOCAL)); //adiciona uma nova variavel na tabela
		   //erros.add("("+ assign.getLine() + "," +assign.getColunm() +"): Variavel " + assign.getVariable()+" nao foi declarada. ");  
	   }
       
	   stk.pop(); 
		
	}
	
	
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
		
		if(ruleCount == 0){
			startRule = rule.getName();
		}
		rule.getExpr().accept(this);
		ruleCount++;
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
	
	private void pprintStak(){
	   System.out.print("Stack -> ");
	   for(TypeNode tn : stk ){
		   System.out.print(tn.getName() + ", ");
	   }
	   System.out.println(" <-Topo ");
	}

}
