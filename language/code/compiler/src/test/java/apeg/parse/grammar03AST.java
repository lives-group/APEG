package apeg.parse;

import apeg.parse.*;

import java.util.List;
import java.util.ArrayList;

import apeg.parse.ast.visitor.*;

public class TestAST{

	public static void main(String args[]){


		List<GrammarOption>opts = new ArrayList<GrammarOption>();
		List<RuleNode>rules = new ArrayList<RuleNode>();
		List<VarDeclarationNode>param = new ArrayList<VarDeclarationNode>();
		List<VarDlecarationNode>returns = new ArrayList<VarDeclarationNode>();
		List<AssignmentNode>assigs = new ArrayList<AssignmentNode>();

		
		PegNode = peg;

		assigs.add(new AttributeExprNode("i"), new LiteralPegNode("true"));
		peg = new UpdatePegNode(assigs);

		param.add(VarDeclarationNode("x", int));
		param.add(VarDeclarationNode("y", int));
		param.add(VarDeclarationNode("z", int));
		param.add(VarDeclarationNode("w", int));
		returns.add(VarDeclarationNode("i", boolean));



		RuleNode s = new RuleNode("s", Annotation.NONE, param, returns, peg);

		opts.add(NONE);

		GrammarNode gram = new GrammarNode("expression", opts, "", rules, null, null);

		FooVisitor visitor = new FooVisitor();
		gram.accept(visitor);
		
	}
}
