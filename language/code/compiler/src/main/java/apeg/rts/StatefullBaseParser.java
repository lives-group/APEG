package apeg.rts;
import java.io.IOException;
import java.util.Stack;


public class StatefullBaseParser {
	
	private Stack<Symbol> stk;
	private Stack<RuleInfo> ruleStk;
	private PResult result;
	
	private boolean lrsc; // last rule succesfull
	//private Symbol root;
	private PageStream s;
	
	
	public StatefullBaseParser(String fname){
		stk = new Stack<Symbol>();
		ruleStk = new Stack<RuleInfo>();
		result = new PResult(true);
		try {
			s = new PageStream(fname);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void startRule(String ruleName){
		ruleStk.push(new RuleInfo(ruleName));
	}
	
	public void success(){
		NonTerminal raiz = new NonTerminal(ruleStk.peek().rname);
	    for(int i=0; i < ruleStk.peek().n;i++){
	    	raiz.addFrontChilds(stk.pop());
	    }
	    stk.push(raiz);
	    ruleStk.pop();
	    ruleStk.peek().n++;
		result.turnSuccess();
	}
	
	public void fail(){
		for(int i=0; i< ruleStk.peek().n;i++){
	    	stk.pop();
	    }
		ruleStk.pop();
		result.turnFail();
	}
	
	public void undoChoice(){
	    restore();
		for(int i=0; i< ruleStk.peek().n;i++){
	    	stk.pop();
	    }
		ruleStk.peek().n = 0;
		mark();
	}
	
	public void beginChoices(){
	    mark();
	}
	public void endChoices(){
	    unmark();
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
	
	public boolean matchNext(){
	     try {
			char c = s.next();
			stk.push(new Terminal(0,0,c+""));
            ruleStk.peek().n++;
            return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
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
