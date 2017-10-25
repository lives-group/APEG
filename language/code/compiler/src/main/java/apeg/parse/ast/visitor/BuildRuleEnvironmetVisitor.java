package apeg.parse.ast.visitor;

import apeg.parse.ast.TypeNode;
import apeg.parse.ast.RuleNode;
import apeg.parse.ast.VarDeclarationNode;
import apeg.parse.ast.visitor.Environments.NTType;
import apeg.parse.ast.visitor.Environments.RuleEnvironment;

/**
 * @class BuildingVisitor : E resposavel pela construcao das tabelas de tipos e por coletar e verificar informacoes sobre
 *                          sobre nao terminais e atributos.
 * @author deise
 *
 */

public class BuildRuleEnvironmetVisitor extends FormalVisitor {

	private RuleEnvironment r;
    
    /**
     * BuildingVisitor(): Construtor padrao. Inicializa as tabelas de simbolos. 
     */
    public BuildRuleEnvironmetVisitor(){
    	r = new RuleEnvironment();
    }
	
	@Override
	public void visit(RuleNode rule) {
		TypeNode[] parameters = new TypeNode[rule.getParameters().size()];
		TypeNode[] returns = new TypeNode[rule.getReturns().size()];
		int i = 0;
		for(VarDeclarationNode param : rule.getParameters()){
			parameters[i] = param.getType();
			i++;
		}
		i = 0;
		for(VarDeclarationNode param : rule.getReturns()){
			returns[i] = param.getType();
			i++;
		}
		r.add(rule.getName(), new NTType(parameters, returns)) ;
	}
	
	public void printTable(){
	   System.out.println(r.toString());
	}

	public RuleEnvironment getTable() {
		return r;
	}
}