//INCOMPLETO

package apeg.parse;

import apeg.parse.ast.*;
import apeg.parse.ast.RuleNode.Annotation;
import apeg.parse.ast.GrammarNode.GrammarOption;
import apeg.parse.ast.BinaryExprNode.Operator;
import apeg.parse.ast.EqualityExprNode.EqualityOperator;

import java.util.List;
import java.util.ArrayList;

public class Grammar05AST{

	public void main(String args[]){

		List<GrammarOptions>opts = new ArrayList<GrammarOptions>();
		List<RuleNode>rules = new ArrayList<RuleNode>();
		List<VarDeclarationNode>param = new ArrayList<VarDeclarationNode>();
		List<VarDeclarationNode>returns = new ArrayList<VarDeclarationNode>();
		List<PegNode>pegs = new ArrayList<PegNode>();
		List<ExprNode>attrs = new ArrayList<ExprNode>();
		List<AssignmentPegNode>assigs = new ArrayList<AssignmentPegNode>();
		List<String>func = new ArrayList<String>();

		
		//peg regra teste

		assigs.add(new AttributeExprNode("z"), ); //completar z = add(x,y) ??
		pegs.add(new UpdatePegNode(assigs));
		pegs.add(new NonterminalPegNode("strN", new BinaryExprNode(new AttributeExprNode("z"), new IntExprNode(2), Operator.MUL))); 
		PegNode peg = new SequencePegNode(pegs);

		//peg regra strN

		pegs.add(new ConstraintPegNode(new LogicalBinExpr(new AttributeExprNode("n"), new IntExprNode(0)))); // {? n > 0} ??
		pegs.add(new NonterminalPegNode("CHAR", null));
		assigs.add(new AttributeExprNode("n"), new BinaryExprNode(new AttributeExprNode("n"), new IntExprNode(1), Operator.SUB));
		pegs.add(new UpdatePegNode(assigs)); 
		pegs.add(new StarPegNode(new SequencePegNode(pegs)));
		pegs.add(new ConstraintPegNode(new EqualityExprNode(new AttributeExprNode("n"), new IntExprNode(0), EqualityOperator.EQ))); // completar {? n == 0} ??
		pegs.add(new NotPegNode(new AnyPegNode()));
		peg = new SequencePegNode(pegs);

		//peg regra CHAR
		peg = new AnyPegNode();


		returns.add(new VarDeclarationNode("z", new IntTypeNode()));
		param.add(new VarDeclarationNode("x", new IntTypeNode()));
		param.add(new VarDeclarationNode("y", new IntTypeNode()));
		RuleNode teste = new RuleNode("teste", Annotation.NONE, param, returns, peg);
		rules.add(teste);

		param.add(new VarDeclarationNode("n", new IntTypeNode()));
		RuleNode strN = new RuleNode("strN", Annotation.NONE, param, new ArrayList<VarDeclarationNode>(), peg);
		rules.add(strN);

		RuleNode CHAR = new RuleNode("CHAR", Annotation.NONE, new ArrayList<VarDeclarationNode>(), new ArrayList<VarDeclarationNode>(), peg);
		rules.add(CHAR);

		func.add(new CallExprNode("Functions1 AdaptableFunctions"));
		opts.add(null);
		GrammarNode gram = new GrammarNode("testfunction", opts, " ", rules, null, null);
	}
}