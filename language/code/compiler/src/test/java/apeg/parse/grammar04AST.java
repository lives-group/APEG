/*AttributeExprNode = quando uma variável é usadaa
VarDeclarationNode = para declarar uma variável
AssignmentNode é como se representasse o sinal de igual uma coisa =(get) outra coisa
*/

package apeg.parse;

import apeg.parse.ast.*;
import apeg.parse.ast.GrammarNode.GrammarOption;
import apeg.parse.ast.RuleNode.Annotation;
import apeg.parse.ast.BinaryExprNode.Operator;
import java.util.List;
import java.util.ArrayList;

public class Grammar04Ast{

	public static void main(String args[]){

		List<GrammarOptions>opts = new ArrayList<GrammarOptions>();
		List<RuleNode>rules = new ArrayList<RuleNode>();
		List<VarDeclarationNode>returns = new ArrayList<VarDeclarationNode>();
		List<VarDeclarationNode>param = new ArrayList<VarDeclarationNode>();
		List<PegNode>pegs = new ArrayList<PegNode>();
		List<ExprNode>attrs = new ArrayList<ExprNode>();
		List<AssignmentNode>assigs = new ArrayList<AssignmentNode>();


		//peg regra b
		assigs.add(new AssignmentNode(new AttributeExprNode("x"), new BinaryExprNode(new AttributeExprNode("x"), new IntExprNode(1), Operator.ADD)));
		pegs.add(new UpdatePegNode(assigs));
		pegs.add(new LiteralPegNode("2"));
		assigs.add(new AssignmentNode(new AttributeExprNode("x"), new AttributeExprNode("x1")));
		pegs.add(new UpdatePegNode(assigs));
		PegNode rigth = new SequencePegNode(pegs);
		assigs.add(new AssignmentNode(new AttributeExprNode("x"), new BinaryExprNode(new AttributeExprNode("x"), new IntExprNode(1), Operator.ADD)));
		pegs.add(new UpdatePegNode(assigs));
		pegs.add(new LiteralPegNode("1"));
		assigs.add(new AssignmentNode(new AttributeExprNode("x"), new AttributeExprNode("x1")));
		pegs.add(new UpdatePegNode(assigs));
		PegNode left = new SequencePegNode(pegs);
		PegNode rigth1 = new ChoicePegNode(left, rigth1);
		assigs.add(new AssignmentNode(new AttributeExprNode("x"), new BinaryExprNode(new AttributeExprNode("x"), new IntExprNode(1), Operator.ADD)));
		pegs.add(new UpdatePegNode(assigs));
		pegs.add(new LiteralPegNode("0"));
		assigs.add(new AssignmentNode(new AttributeExprNode("x"), new AttributeExprNode("x1")));
		pegs.add(new UpdatePegNode(assigs));
		PegNode left1 = new SequencePegNode(pegs);
		PegNode peg = new ChoicePegNode(left1, rigth1);

		//peg regra c

		assigs.add(new AttributeExprNode("x"), new BinaryExprNode(new AttributeExprNode("x"), new IntExprNode(0), Operator.ADD));
		pegs.add(new UpdatePegNode(assigs));
		pegs.add(new LiteralPegNode("2"));
		pegs.add(new StarPegNode(new SequencePegNode(pegs)));
		assigs.add(new AttributeExprNode("x"), new IntExprNode(0));
		pegs.add(new UpdatePegNode(assigs));
		peg = new SequencePegNode(pegs);


		
		//peg regra a
		returns.add(new VarDeclarationNode("k", new IntTypeNode()));
		attrs.add(new IntExprNode(0));
		attrs.add(new VarDeclarationNode("k",new IntTypeNode()));

		peg = (new NonterminalPegNode("b", attrs));

		// Regras da gramática
		RuleNode a = new RuleNode("a", Annotation.NONE, param, returns, peg);
		rules.add(a);

		returns.add(new VarDeclarationNode("x1", new FloatTypeNode()));
		param.add(new VarDeclarationNode("x", new IntTypeNode()));
		RuleNode b = new RuleNode("b", Annotation.NONE, param, returns, peg);
		rules.add(b);

		returns.add(new VarDeclarationNode("x",new IntTypeNode()));
		RuleNode c = new RuleNode("c", Annotation.NONE, param, returns, peg);
		rules.add(c);

		opts.add(null);

		GrammarNode gram = new GrammarNode("notDiscardChanges", opts,"", rules, null, null);
	}
}