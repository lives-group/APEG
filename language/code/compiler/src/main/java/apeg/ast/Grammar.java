package apeg.ast;

import java.util.List;

import apeg.ast.rules.RulePEG;

import apeg.visitor.Visitor;

public class Grammar extends ASTNode {

	private String name;
	private List<GrammarOption> opts;
	private List<RulePEG> rules;
	
	public GrammarNode(String name, List<GrammarOption> opts, List<RulePEG> rules) {
		this.name = name;
		this.opts = opts;
		this.preamble = preamble;
		this.rules = rules;
	}
	
	public String getName() {
		return name;
	}

	public List<GrammarOption> getOptions() {
		return opts;
	}
	
	public List<RulePEG> getRules() {
		return rules;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}

	public enum GrammarOption {
		ADAPTABLE, // if the grammar is adaptable
		NO_ADAPTABLE, // if the grammar is not adaptable
		MEMOIZE, // if it is to memoize intermediate results
		NO_MEMOIZE, // if it is not to memoize intermediate results
		USUAL_SEMANTICS, // if it is to use conventional APEG choice semantics
		SIMPLE_ENV_SEMANTICS // if it is to use non conventional APEG semantics
	}
	
}
