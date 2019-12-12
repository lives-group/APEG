package apeg.visitor;

import java.util.ArrayDeque;


import java.util.Hashtable;


import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import apeg.parse.ast.*;

import apeg.util.path.Path;

import java.io.FileWriter;
import java.io.File;

public class ParserVisitor  extends FormalVisitor implements ASTNodeVisitor{

    enum Mode {STOPED,      // I'm nowhere ! 
               SINGLETON,   // I'm generating code for a SINGLE rule !
               INFLOW,      // I'm generating code for a sequence or a choice !
               TEMP,        // I'm generating code for a temporary (or helper) function !
               INFLOW_TEMP} // I'm generating code for a sequence, inside of a helper function ! 
	
	private STGroup groupTemplate;
	private ST template;
	private ArrayDeque<PegNode> q;
	private Mode mode, mode_bkup;
	private final String lSuc, lFail, suc, fail ;
	private String path;	

	// Template for current parsing expression, current expression and the last assignment visited 
	private ST peg_expr;
  
	private String currentRule;
	private Hashtable<PegNode,String> hnames;

	public ParserVisitor(Path filePath, Hashtable<PegNode,String> hnames) {
		groupTemplate = new STGroupFile(filePath.getAbsolutePath());
		lSuc =  "true";
		currentRule = " ";
		lFail = "false";
		suc =  "endSuccess()";
		fail = "endFail()";
		mode = Mode.STOPED;
		this.hnames = hnames;
		q = new ArrayDeque<PegNode>(); 
	}
	
	public ParserVisitor(Path filePath, Hashtable<PegNode,String> hnames, String path) {
		this(filePath,hnames);
		this.path = path;
	}

	private Mode modeSet(Mode m){
	    Mode s = mode;
	    if(((s == Mode.TEMP) || (s == Mode.INFLOW_TEMP)) && (m == Mode.INFLOW)){
	       mode = Mode.INFLOW_TEMP;
	    }else{
	       mode = m;
	    }
	    return s;
	}
	
	private boolean isModeTemp(){ return (mode == Mode.TEMP) || (mode == Mode.INFLOW_TEMP); }
	
	private void mkHelperFunctionCall(String name){
		//mode_bkup = modeSet(Mode.TEMP);
		ST aux_peg1 = groupTemplate.getInstanceOf("peg_nonterminal_call");
		aux_peg1.add("value", name);
		peg_expr = aux_peg1;
		//modeSet(mode_bkup);
	}
	
	@Override
	public void visit(AndPegNode peg) {
		// set the current parsing expression template
		ST aux_peg = groupTemplate.getInstanceOf("and_peg");
		String name = hnames.get(peg);
		if( name != null) {
			q.add(peg); //Adicionando o nome na fila
            mkHelperFunctionCall(name);
			return ; 
		}
		// visit the parsing expression
		peg.getPeg().accept(this);
		// set propriety for the bind parsing expression
		aux_peg.add("peg_expr", peg_expr);
		aux_peg.add("suc", isModeTemp() ? lSuc : suc);
        aux_peg.add("fail",isModeTemp() ? lFail : fail);
		// set the current parsing expression
		peg_expr = aux_peg;
	}

 	@Override
 	public void visit(AnyPegNode peg) {
 	    String name = hnames.get(peg);
		if( name != null) {
			q.add(peg); //Adicionando o nome na fila
            mkHelperFunctionCall(name);
			return ; 
		}
 		peg_expr = groupTemplate.getInstanceOf("any_peg");
 		peg_expr.add("suc", isModeTemp() ? lSuc : suc);
 		peg_expr.add("fail", isModeTemp() ? lFail : fail);
 	}

	@Override
	public void visit(ChoicePegNode peg) {
		String name = hnames.get(peg);
		if( name != null) {
			q.add(peg); //Adicionando o nome na fila
            mkHelperFunctionCall(name);
			return ; 
		}
		mode_bkup = modeSet(Mode.INFLOW);
        // set the current parsing expression template
		ST aux_peg = groupTemplate.getInstanceOf("choice_peg");
		// visit the left parsing expression
		peg.getLeftPeg().accept(this);
		// set propriety for the left parsing expression
		aux_peg.add("left_peg", peg_expr);
		// visit the right parsing expression
		peg.getRightPeg().accept(this);
		// set propriety for the right parsing expression
		if((peg.getRightPeg() instanceof NonterminalPegNode) 
		|| (peg.getRightPeg() instanceof LiteralPegNode)
		|| hnames.containsKey(peg.getRightPeg())){
		
		     ST aux_peg1 = groupTemplate.getInstanceOf("choice_right_singleton");
		     aux_peg1.add("peg_expr",peg_expr);
		     aux_peg1.add("suc", isModeTemp() ? lSuc  : suc);

		     aux_peg.add("right_peg", aux_peg1);
		}else{
		     aux_peg.add("right_peg", peg_expr);
		}
		aux_peg.add("suc",isModeTemp() ? lSuc : suc);
		// set the current parsing expression
		peg_expr = aux_peg;
		modeSet(mode_bkup);
	}

	@Override
	public void visit(LiteralPegNode peg) {
		if(mode != Mode.SINGLETON) {
	 	    peg_expr= groupTemplate.getInstanceOf("peg_literal_match");
		    peg_expr.add("value", peg.getValue());	
		}else {
		    peg_expr= groupTemplate.getInstanceOf("peg_literal");
		    peg_expr.add("value", peg.getValue());	
		}
	}

	@Override
	public void visit(NonterminalPegNode peg) {
		// set the current parsing expression template
		if(mode != Mode.SINGLETON) {
		    peg_expr = groupTemplate.getInstanceOf("peg_nonterminal_call");
		    peg_expr.add("value", peg.getName());
		}else {
			peg_expr = groupTemplate.getInstanceOf("peg_nonterminal");
		    peg_expr.add("value", peg.getName());
		}
	}

	@Override
	public void visit(NotPegNode peg) {
		mode_bkup = modeSet(Mode.INFLOW);
		String name = hnames.get(peg);
		if( name != null) {
			q.add(peg);
            mkHelperFunctionCall(name);
			return;
		}
		// set the current parsing expression template
		ST aux_peg = groupTemplate.getInstanceOf("not_peg");
		// visit the parsing expression
		peg.getPeg().accept(this);
		
		// adding the parsing expression on star template
		aux_peg.add("peg_expr", peg_expr);
		aux_peg.add("suc",isModeTemp() ? lSuc : suc);
		aux_peg.add("fail",isModeTemp() ? lFail : fail);
		peg_expr = aux_peg;
		modeSet(mode_bkup);
	}

	@Override
	public void visit(OptionalPegNode peg) {
		mode_bkup = modeSet(Mode.INFLOW);
		String name = hnames.get(peg);
		if( name != null) {
			q.add(peg);
            mkHelperFunctionCall(name);
			return;
		}
		// set the current parsing expression template
		ST aux_peg = groupTemplate.getInstanceOf("optional_peg");
		// visit the parsing expression
		peg.getPeg().accept(this);
		// adding the parsing expression on star template
		aux_peg.add("peg_expr", peg_expr);
		aux_peg.add("suc",isModeTemp() ? lSuc : suc);
		peg_expr = aux_peg;
		modeSet(mode_bkup);
	}

   @Override
	public void visit(PlusPegNode peg) {
		// set the current parsing expression template
		mode_bkup = modeSet(Mode.INFLOW);
		String name = hnames.get(peg);
		if( name != null) {
			q.add(peg);
            mkHelperFunctionCall(name);
			return;
		}
		ST aux_peg = groupTemplate.getInstanceOf("plus_peg");
		// visit the parsing expression
		peg.getPeg().accept(this);
		// adding the parsing expression on star template
		aux_peg.add("peg_expr", peg_expr);
		aux_peg.add("suc", isModeTemp() ? lSuc : suc);
		aux_peg.add("fail", isModeTemp() ? lFail : fail);
		peg_expr = aux_peg;
		modeSet(mode_bkup);
	}

	@Override
	public void visit(SequencePegNode peg) {
		// set the current parsing expression template
		String name = hnames.get(peg);
		if( name != null) {
			q.add(peg);
		    mkHelperFunctionCall(name);
			return;
		}
		mode_bkup = modeSet(Mode.INFLOW);
		ST aux_peg = groupTemplate.getInstanceOf("sequence_peg");

		for(PegNode p : peg.getPegs()) {
			// visit a parsing expression
		   	p.accept(this);
			// add the current parsing expression on the sequence template
			aux_peg.add("peg_exprs", peg_expr);
		}
		aux_peg.add("fail", isModeTemp() ? lFail : fail);
		peg_expr = aux_peg;
		modeSet(mode_bkup);
	}

	@Override
	public void visit(StarPegNode peg) {
		// set the current parsing expression template as a update parsing expression
		mode_bkup = modeSet(Mode.INFLOW);
		String name = hnames.get(peg);
		if( name != null) {
			q.add(peg);
		    mkHelperFunctionCall(name);
			return;
		}
		System.out.println(mode.toString());
		ST aux_peg = groupTemplate.getInstanceOf("star_peg");
		// visit the parsing expression
		peg.getPeg().accept(this);
		// adding the parsing expression on star template
		aux_peg.add("peg_expr", peg_expr);
		peg_expr = aux_peg;
		modeSet(mode_bkup);
	}

	@Override
	public void visit(GrammarNode grammar) {
	    String grmName;
		template = groupTemplate.getInstanceOf("apeg");
		grmName = grammar.getName();
		if(!Character.isUpperCase(grmName.charAt(0))){
		    char c = Character.toUpperCase(grmName.charAt(0));
		    grmName = ""+c + grmName.substring(1,grmName.length());
		}

		template.add("name", grmName);
		

		for(RuleNode rule : grammar.getRules()) {
			rule.accept(this);
		}
		if(path != null){
		     try{
		         path = path +(path != "" ? File.pathSeparatorChar : "")+ grmName+".java";
		         FileWriter w = new FileWriter(path);
		         System.out.println("Result saved to " + path);
		         w.write(template.render());
		         w.close();
		     }catch(Exception e){
		         e.printStackTrace();
		     }
        }else{
            System.out.println(template.render());
        }
	}

	@Override
	public void visit(RuleNode rule) {
        modeSet(Mode.SINGLETON);
		ST r = groupTemplate.getInstanceOf("rule");
		// setting rule name
		r.add("name", rule.getName());	
		currentRule = rule.getName();

		rule.getExpr().accept(this);
		r.add("peg_expr", peg_expr); // setting parsing expression propriety
		
		if(rule.getExpr() instanceof SequencePegNode) {
			r.add("suc_or_fail", suc);
		}else if(rule.getExpr() instanceof StarPegNode) {
			r.add("suc_or_fail", suc);
		}else if(rule.getExpr() instanceof LiteralPegNode) {
			r.add("suc_or_fail", suc);
		}else if(rule.getExpr() instanceof NonterminalPegNode) {
			r.add("suc_or_fail", suc);
		}else if(rule.getExpr() instanceof ChoicePegNode) {
			r.add("suc_or_fail", fail);
		}
		template.add("rules", r); // adding the rule template on the list of grammar rules
		
		mode_bkup = modeSet(Mode.TEMP);
        if(!q.isEmpty()) {
			String s;
			PegNode n;
		    while(!q.isEmpty()) {
		        r = groupTemplate.getInstanceOf("temp");
			    n = q.removeFirst();
			    s = hnames.remove(n);
				// setting rule name
				r.add("name", s);	

				n.accept(this);
				r.add("peg_expr", peg_expr); // setting parsing expression propriety
				
				if(n instanceof SequencePegNode) {
					r.add("suc_or_fail", lSuc);
				}else if(n instanceof StarPegNode) {
					r.add("suc_or_fail", lSuc);
				}else if(n instanceof LiteralPegNode) {
					r.add("suc_or_fail", lSuc);
				}else if(n instanceof NonterminalPegNode) {
					r.add("suc_or_fail", lSuc);
				}else if(n instanceof ChoicePegNode) {
					r.add("suc_or_fail", lFail);
				}
				template.add("rules", r);
		   }
		   
		}
		modeSet(mode_bkup);
	}


}
