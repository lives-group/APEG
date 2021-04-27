package apeg.ast;

import java.util.List;

import apeg.util.*;

import apeg.ast.rules.RulePEG;

import apeg.visitor.Visitor;

public class Grammar extends ASTNode {

	private String name;
	private GrammarOption opts;
	private List<RulePEG> rules;
	private Hashtable<String,RulePEG> hashRules;

	public Grammar(SymInfo s, String name, GrammarOption opts, List<RulePEG> rules) {
		super(s);
		this.name = name;
		this.opts = opts;
		//	this.preamble = preamble;
		this.rules = rules;
		hashRules = new Hashtable<String,RulePEG>();
		for(int i = 0 ; i < rules.size(); i++){
			hashRules.put(rules.get(i).getRuleName(),rules.get(i));
		}
		rules.get(0).accept(this);
	}

	public String getName() {
		return name;
	}

	public GrammarOption getOptions() {
		return opts;
	}

	public List<RulePEG> getRules() {
		return rules;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}

	public static class GrammarOption {
		public boolean adaptable; // if the grammar is adaptable or not
		public boolean memoize; // if it is to memoize intermediate results or not
		public boolean usual_semantics; // if it is to use conventional APEG choice semantics or not

		public GrammarOption() {
			adaptable = false;
			memoize = false;
			usual_semantics = true;
		}
	}

}
