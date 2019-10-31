//INCOMPLETO

package apeg.parse;

import apeg.parse.*;

import java.util.List;
import java.util.ArrayList;

public class Grammar04AST{

	public void main(String args[]){

		List<GrammarOptions>opts = new ArrayList<GrammarOptions>();
		List<RuleNode>rules = new ArrayList<RuleNode>();
		List<VarDeclarationNode>param = new ArrayList<VarDeclarationNode>();
		List<VarDeclarationNode>returns = new ArrayList<VarDeclarationNode>();
		List<PegNode>pegs = new ArrayList<PegNode>();
		List<ExprNode>attrs = new ArrayList<ExprNode>();
		List<AssignmentPegNode>assigs = new ArrayList<AssignmentPegNode>();

		
		//peg regra teste

		assigs.add(new AttributeExprNode("z"), ); //completar z = add(x,y) ??
		pegs.add(new UpdatePegNode(assigs));
		pegs.add(new NonterminalPegNode()); // completar strN<z*2> ??
		PegNode peg = new SequencePegNode(pegs);

		//peg regra strN

		pegs.add(); // {? n > 0} ??
		attrs.add(); // como definir uma regra sem atributos?
		pegs.add(new NonterminalPegNode("CHAR", attrs));
		assigs.add(new AttributeExprNode("n", new BinaryExprNode(new AttributeExprNode("n"), new LiteralPegNode("1"), Operator.SUB)));
		pegs.add(new UpdatePegNode(assigs)); 
		pegs.add(new StarPegNode(new SequencePegNode(pegs)));
		pegs.add(); // completar {? n == 0} ??
		pegs.add(new NotPegNode(new AnyPegNode));
		PegNode peg = new SequencePegNode(pegs);

		//peg regra CHAR
		PegNode peg = new AnyPegNode;


		returns.add(new VarDeclarationNode("z", int));
		param.add(new VarDeclarationNode("x", int));
		param.add(new VarDeclarationNode("y", int));
		RuleNode teste = new RuleNode("teste", Annotation.NONE, param, returns, peg);
		rules.add(teste);

		param.add(new VarDeclarationNode("n", int));
		RuleNode strN = new RuleNode("strN", Annotation.NONE, param, new ArrayList<VarDeclarationNode>(), peg);
		rules.add(strN);

		RuleNode CHAR = new RuleNode("CHAR", Annotation.NONE, new ArrayList<VarDeclarationNode>(), new ArrayList<VarDeclarationNode>(), peg);
		rules.add(CHAR);

		opts.add(NONE);
		GrammarNode gram = new GrammarNode("testfunction", opts, " ", rules, null, null);
	}
}