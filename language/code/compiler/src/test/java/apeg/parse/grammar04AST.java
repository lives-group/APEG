/*AttributeExprNode = quando uma variável é usadaa
VarDeclarationNode = para declarar uma variável
AssignmentNode é como se representasse o sinal de igual uma coisa =(get) outra coisa
*/

package apeg.parse;

import apeg.parse.*;

import java.util.List;
import java.util.ArrayList;

public class TesteAST{

	public static void main(String args[]){

		List<GrammarOptions>opts = new ArrayList<GrammarOptions>();
		List<RuleNode>rules = new ArrayList<RuleNode>();
		List<VarDeclarationNode>returns = new ArrayList<VarDeclarationNode>();
		List<VarDeclarationNode>param = new ArrayList<VarDeclarationNode>();
		List<PegNode>pegs = new ArrayList<VarDeclarationNode>();
		List<ExprNode>attrs = new ArrayList<ExprNode>();
		List<AssignmentNode>assigs = new ArrayList<AssignmentNode>();


		//peg regra b
		PegNode = peg, left, rigth;


		assigs.add(new AssignmentNode(new AttributeExprNode("x"), new BinaryExprNode(new AttributeExprNode("x"), new LiteralPegNode("1"), Operator.ADD)));
		pegs.add(new UpdatePegNode(assigs));
		pegs.add(new LiteralPegNode("2"));
		assigs.add(new AssignmentNode(new AttributeExprNode("x"), new AttributeExprNode("x1")));
		pegs.add(new UpdatePegNode(assigs));
		rigth = new SequencePegNode(pegs);
		assigs.add(new AssignmentNode(new AttributeExprNode("x"), new BinaryExprNode(new AttributeExprNode("x"), new LiteralPegNode("1"), Operator.ADD)));
		pegs.add(new UpdatePegNode(assigs));
		pegs.add(new LiteralPegNode("1"));
		assigs.add(new AssignmentNode(new AttributeExprNode("x"), new AttributeExprNode("x1")));
		pegs.add(new UpdatePegNode(assigs));
		left = new SequencePegNode(pegs);
		rigth = new ChoicePegNode(left, rigth);
		assigs.add(new AssignmentNode(new AttributeExprNode("x"), new BinaryExprNode(new AttributeExprNode("x"), new LiteralPegNode("1"), Operator.ADD)));
		pegs.add(new UpdatePegNode(assigs));
		pegs.add(new LiteralPegNode("0"));
		assigs.add(new AssignmentNode(new AttributeExprNode("x"), new AttributeExprNode("x1")));
		pegs.add(new UpdatePegNode(assigs));
		left = new SequencePegNode(pegs);
		peg = new ChoicePegNode(left, rigth)

		//peg regra c

		assigs.add(new AttributeExprNode("x"), new BinaryExprNode(new AttributeExprNode("x"), new LiteralPegNode("0"), Operator.ADD));
		pegs.add(new UpdatePegNode(assigs));
		pegs.add(new LiteralPegNode("2"));
		pegs.add(new SequencePegNode(pegs));
		pegs.add(new StarPegnode(pegs));
		assigs.add(new AttributeExprNode("x"), new LiteralPegNode("0"));
		pegs.add(new UpdatePegNode(assigs));
		peg = new SequencePegNode(pegs);


		
		//peg regra a
		returns.add(new VarDeclarationNode("k", int));
		attrs.add(new LiteralPegNode("0"));
		attrs.add(new VarDeclarationNode("k",int));

		peg (new NonterminalPegNode("b", attrs));

		// Regras da gramática
		RuleNode a = new RuleNode("a", Annotation.NONE, param, returns, peg);
		rules.add(a);

		returns.add(new VarDeclarationNode("x1", float));
		param.add(new VarDeclarationNode("x", int));
		RuleNode b = new RuleNode("b", Annotation.NONE, param, returns, peg);
		rules.add(b);

		returns.add(new VarDeclarationNode("x", int));
		RuleNode c = new RuleNode("c", Annotation.NONE, param, returns, peg);
		rules.add(c);

		opts.add(NONE);

		GrammarNode gram = new GrammarNode("notDiscardChanges", opts,"", rules, null, null);
	}
}