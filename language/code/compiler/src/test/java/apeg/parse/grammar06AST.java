/*
		INCOMPLETO

*/


package apeg.parse;

import apeg.parse.ast.*;
import apeg.parse.ast.GrammarNode.GrammarOption;
import apeg.parse.ast.RuleNode.Annotation;
import apeg.parse.ast.BinaryExprNode.Operator;

import java.util.List;
import java.util.ArrayList;

public class Grammar06AST{

	public void main(String args[]){

		List<GrammarOption>opts = new ArrayList<GrammarOption>();
		List<RuleNode>rules = new ArrayList<RuleNode>();
		List<AssignmentNode>assigs = new ArrayList<AssignmentNode>();
		List<VarDeclarationNode>param = new ArrayList<VarDeclarationNode>();
		List<VarDeclarationNode>returns = new ArrayList<VarDeclarationNode>();
		List<ExprNode>attrs = new ArrayList<ExprNode>();
		List<String>func = new ArrayList<String>();
		List<PegNode>pegs = new ArrayList<PegNode>();



		//pegs regra literal3

		attrs.add(new AttributeExprNode("g")); // g?
		pegs.add(new NonterminalPegNode("literal", attrs));
		attrs.add(new AttributeExprNode("g")); // g?
		pegs.add(new NonterminalPegNode("literal", attrs));
		attrs.add(new AttributeExprNode("g")); //g?
		pegs.add(new NonterminalPegNode("literal", attrs));
		pegs.add(new NotPegNode(new AnyPegNode()));
		PegNode peg = new SequencePegNode(pegs);

		param.add(); // grammar g ????
		RuleNode literal3 = new RuleNode("literal3", Annotation.NONE, param, returns, peg);
		rules.add(literal3);

		//pegs regra literal

		attrs.add(new AttributeExprNode("n"));
		pegs.add(new NonterminalPegNode("number", attrs));
		pegs.add();
		attrs.add(new AttributeExprNode("g1"));
		pegs.add(new LiteralPegNode("["));
		pegs.add(new NonterminalPegNode("strN", attrs));
		pegs.add(new LiteralPegNode("]"));
		peg = new SequencePegNode(pegs);

		param.add(new Grammar g);
		RuleNode literal = new RuleNode("literal", Annotation.NONE, param, returns, peg);
		rules.add(literal);

		//pegs regra strN

		peg = new ConstraintPegNode(new BooleanExprNode(false));

		param.add(new NonterminalPegNode("g", null));
		RuleNode strN = new RuleNode("strN", Annotation.NONE, param, returns, peg);
		rules.add(strN);

		//pegs regra CHAR
		peg = new AnyPegNode();

		RuleNode CHAR = new RuleNode("CHAR", Annotation.NONE, param, returns, peg);
		rules.add(CHAR);

		//pegs regra number

		attrs.add(new AttributeExprNode("r"));
		pegs.add(new NonterminalPegNode("digit", attrs));
		attrs.add(new AttributeExprNode("aux")); 
		pegs.add(new NonterminalPegNode("digit", attrs));
		assigs.add(new AttributeExprNode("r"), new BinaryExprNode(new BinaryExprNode(new AttributeExprNode("r"), new IntExprNode(10), Operator.MUL), new AttributeExprNode("aux"), Operator.ADD));
		pegs.add(new UpdatePegNode(assigs));
		peg = new SequencePegNode(pegs);
		pegs.add(new StarPegNode(peg));
		peg = new SequencePegNode(pegs);

		returns.add(new VarDeclarationNode("r",new IntTypeNode()));

		RuleNode number = new RuleNode("number", Annotation.NONE, param, returns, peg);
		rules.add(number);

		//pegs regra digit
		PegNode peg, left, rigth;

		pegs.add(new LiteralPegNode("0"));
		assigs.add(new AttributeExprNode("x1"), new IntExprNode(0));
		left = new UpdatePegNode(assigs);
		pegs.add(new LiteralPegNode("1"));
		assigs.add(new AttributeExprNode("x1"), new IntExprNode(1));
		left = new UpdatePegNode(assigs);
		pegs.add(new LiteralPegNode("2"));
		assigs.add(new AttributeExprNode("x1"), new IntExprNode(2));
		left = new UpdatePegNode(assigs);
		pegs.add(new LiteralPegNode("3"));
		assigs.add(new AttributeExprNode("x1"), new IntExprNode(3));
		left = new UpdatePegNode(assigs);
		pegs.add(new LiteralPegNode("4"));
		assigs.add(new AttributeExprNode("x1"), new IntExprNode(4));
		left = new UpdatePegNode(assigs);
		pegs.add(new LiteralPegNode("5"));
		assigs.add(new AttributeExprNode("x1"), new IntExprNode(5));
		left = UpdatePegNode(assigs);
		pegs.add(new LiteralPegNode("6"));
		assigs.add(new AttributeExprNode("x1"), new IntExprNode(6));
		left = new UpdatePegNode(assigs);
		pegs.add(new LiteralPegNode("7"));
		assigs.add(new AttributeExprNode("x1"), new IntExprNode(7));
		left = new UpdatePegNode(assigs);
		pegs.add(new LiteralPegNode("8"));
		assigs.add(new AttributeExprNode("x1"), new IntExprNode(8));
		left = new UpdatePegNode(assigs);
		pegs.add(new LiteralPegNode("9"));
		assigs.add(new AttributeExprNode("x1"), new IntExprNode(9));
		pegs.add(new UpdatePegNode(assigs));
		rigth = new SequencePegNode(pegs);
		pegs.add(new ChoicePegNode(left, rigth));
		rigth = new SequencePegNode(pegs);
		pegs.add(new ChoicePegNode(left, rigth));
		rigth = new SequencePegNode(pegs);
		pegs.add(new ChoicePegNode(left, rigth));
		rigth = new SequencePegNode(pegs);
		pegs.add(new ChoicePegNode(left, rigth));
		rigth = new SequencePegNode(pegs);
		pegs.add(new ChoicePegNode(left, rigth));
		rigth = new SequencePegNode(pegs);
		pegs.add(new ChoicePegNode(left, rigth));
		rigth = new SequencePegNode(pegs);
		pegs.add(new ChoicePegNode(left, rigth));
		rigth = new SequencePegNode(pegs);
		pegs.add(new ChoicePegNode(left, rigth));
		rigth = new SequencePegNode(pegs);
		pegs.add(new ChoicePegNode(left, rigth));
		peg = new SequencePegNode(pegs);


		returns.add(new VarDeclarationNode("x1", new IntTypeNode()));
		RuleNode digit = new RuleNode("digit", Annotation.NONE, param, returns, peg);
		rules.add(digit);


		func.add("AdaptableFunctions");
		opts.add(GrammarOption.ADAPTABLE);
		GrammarNode gram = new GrammarNode("adapdatadependent", opts, " ", rules, func, null);
	}
}