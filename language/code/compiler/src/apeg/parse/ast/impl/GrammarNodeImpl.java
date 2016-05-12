package apeg.parse.ast.impl;

import java.util.List;

import apeg.parse.ast.GrammarNode;
import apeg.parse.ast.RuleNode;
import apeg.parse.ast.visitor.ASTNodeVisitor;

public class GrammarNodeImpl implements GrammarNode {

	private String name, preamble;
	private List<GrammarOption> opts;
	private List<RuleNode> rules;
	List<String> funcs, sfuncs;
	
	public GrammarNodeImpl(String name, List<GrammarOption> opts, String preamble,
			List<RuleNode> rules, List<String> func, List<String> sfuncs) {
		this.name = name;
		this.opts = opts;
		this.preamble = preamble;
		this.rules = rules;
		this.funcs = func;
		this.sfuncs = sfuncs;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public List<GrammarOption> getOptions() {
		return opts;
	}

	@Override
	public String getPreamble() {
		return preamble;
	}

	@Override
	public List<String> getFunctions() {
		return funcs;
	}

	@Override
	public List<String> getFunctionsSources() {
		return sfuncs;
	}
	
	@Override
	public List<RuleNode> getRules() {
		return rules;
	}

	@Override
	public void accept(ASTNodeVisitor v) {
		v.visit(this);
	}

}
