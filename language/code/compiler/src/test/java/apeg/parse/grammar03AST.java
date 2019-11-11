//Erro ao add em assigs
package apeg.parse;

import apeg.parse.ast.*;
import apeg.parse.ast.GrammarNode.GrammarOption;
import apeg.parse.ast.RuleNode.Annotation;
import java.util.List;
import java.util.ArrayList;
import apeg.parse.ast.visitor.*;

public class Grammar03AST{

	public static void main(String args[]){


		List<GrammarOption>opts = new ArrayList<GrammarOption>();
		List<RuleNode>rules = new ArrayList<RuleNode>();
		List<VarDeclarationNode>param = new ArrayList<VarDeclarationNode>();
		List<VarDeclarationNode>returns = new ArrayList<VarDeclarationNode>();
		List<AssignmentNode>assigs = new ArrayList<AssignmentNode>();

		
		
		assigs.add(new AttributeExprNode("i"),new BooleanExprNode(true));
		PegNode peg = new UpdatePegNode(assigs);

		param.add(new VarDeclarationNode("x", new IntTypeNode()));
		param.add(new VarDeclarationNode("y", new IntTypeNode()));
		param.add(new VarDeclarationNode("z", new IntTypeNode()));
		param.add(new VarDeclarationNode("w", new IntTypeNode()));
		returns.add(new VarDeclarationNode("i", new BooleanTypeNode()));



		RuleNode s = new RuleNode("s", Annotation.NONE, param, returns, peg);
		
		opts.add(null);
		GrammarNode gram = new GrammarNode("expression", opts, "", rules, null, null);

		FooVisitor visitor = new FooVisitor();
		gram.accept(visitor);
		
	}
}
