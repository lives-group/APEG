package apeg.visitor;

import apeg.parse.ast.TypeNode;
import apeg.parse.ast.RuleNode;
import apeg.parse.ast.VarDeclarationNode;
import apeg.parse.ast.visitor.Environments.Environment;
import apeg.parse.ast.visitor.Environments.NTInfo;
import apeg.parse.ast.visitor.Environments.NTType;
import apeg.parse.ast.visitor.Environments.VarType;

/**
 * @class BuildRuleEnvironmetVisitor : E resposavel pela construcao das tabelas de tipos e por coletar e verificar informacoes sobre
 *                                     nao terminais e atributos.
 * @author deise
 *
 */

public class BuildRuleEnvironmetVisitor extends FormalVisitor {

	private Environment<String,NTInfo> r;
    
    /**
     * BuildRuleEnvironmetVisitor(): Construtor padrao. Inicializa as tabelas de simbolos. 
     */
    public BuildRuleEnvironmetVisitor(){
    	r = new Environment<String, NTInfo>();
    }
	
	@Override
	public void visit(RuleNode rule) {
		TypeNode[] parameters = new TypeNode[rule.getParameters().size()];
		TypeNode[] returns = new TypeNode[rule.getReturns().size()];
		Environment<String, VarType> temp = new Environment<String, VarType> ();
		VarType v;
		int i = 0;
		for(VarDeclarationNode param : rule.getParameters()){
			parameters[i] = param.getType();
			v = new VarType(param.getType(), VarType.AttrDirection.HERDADO);
			temp.add(param.getName(), v);
			i++;
		}
		i = 0;
		for(VarDeclarationNode param : rule.getReturns()){
			returns[i] = param.getType();
			v = new VarType(param.getType(), VarType.AttrDirection.SINTETIZADO);
			temp.add(param.getName(), v);
			i++;
		}
		r.add(rule.getName(), new NTInfo(new NTType(parameters, returns), temp)) ;
	}
	
	public void printTable(){
	   System.out.println(r.toString());
	}

	public Environment<String, NTInfo> getTable() {
		return r;
	}
}
