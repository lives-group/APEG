package apeg.parse.ast;

import java.util.List;

import apeg.parse.ast.visitor.ASTNodeVisitor;

public class GrammarNode extends ASTNode {

	private String name, preamble;
	private List<GrammarOption> opts;
	private List<RuleNode> rules;
	List<String> funcs, sfuncs;
	
	public GrammarNode(String name, List<GrammarOption> opts, String preamble,
			List<RuleNode> rules, List<String> func, List<String> sfuncs) {
		this.name = name;
		this.opts = opts;
		this.preamble = preamble;
		this.rules = rules;
		this.funcs = func;
		this.sfuncs = sfuncs;
	}
	
	public String getName() {
		return name;
	}

	public List<GrammarOption> getOptions() {
		return opts;
	}

	public String getPreamble() {
		return preamble;
	}

	public List<String> getFunctions() {
		return funcs;
	}

	public List<String> getFunctionsSources() {
		return sfuncs;
	}
	
	public List<RuleNode> getRules() {
		return rules;
	}

	public void accept(ASTNodeVisitor v) {
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
