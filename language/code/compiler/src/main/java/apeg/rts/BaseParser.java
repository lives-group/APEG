package apeg.rts;
import java.io.IOException;
import java.util.Stack;


class RuleCounter{
	public String rname;
	public int n;
	
	public RuleCounter(String name){ 
		 rname = name;
		 n = 0;
	}
}

public class BaseParser {
	
	private Stack<Symbol> stk;
	private Stack<RuleCounter> ruleStk;
	
	private boolean lrsc; // last rule succesfull
	//private Symbol root;
	private PageStream s;
	
	
	public BaseParser(String fname){
		stk = new Stack<Symbol>();
		ruleStk = new Stack<RuleCounter>();
		
		try {
			s = new PageStream(fname);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void startRule(String ruleName){
		ruleStk.push(new RuleCounter(ruleName));
	}
	
	public boolean endSuccess(){
		NonTerminal raiz = new NonTerminal(ruleStk.peek().rname);
	    for(int i=0; i < ruleStk.peek().n;i++){
	    	raiz.addFrontChilds(stk.pop());
	    }
	    stk.push(raiz);
	    ruleStk.pop();
	    ruleStk.peek().n++;
		return true;
	}
	
	public boolean endFail(){
		for(int i=0; i< ruleStk.peek().n;i++){
	    	stk.pop();
	    }
		ruleStk.pop();
		return false;
	}
	
	public boolean alternate(){
		for(int i=0; i< ruleStk.peek().n;i++){
	    	stk.pop();
	    }
		ruleStk.peek().n = 0;
		return false;
	}
	
	public boolean match(String p){
		try {
			if(s.match(p)){
				stk.push(new Terminal(0,0,p));
				ruleStk.peek().n++;
				return true;
			}else{
				return false;
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}
	
	public boolean match(char c){ return match(""+c);}
	
	public Symbol getDerivationTreeRoot() {
		return stk.peek();
	}
	
	public void mark() {
		s.mark();
	}
	
	public void unmark() {
		s.unmark();
	}
	
	public void restore() {
		s.restore();
	}

}
