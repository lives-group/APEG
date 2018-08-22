package apeg.rts;
import java.util.ArrayList;



public class NonTerminal extends Symbol{

	private String rule;
	private ArrayList<Symbol> childs;
	
	public NonTerminal(String rule) {
		super(-1,-1);
		this.rule = rule;
		childs = new ArrayList<Symbol>();
	}
	
	public void addChilds(Symbol s){
		childs.add(s);
	}
	
	public void addFrontChilds(Symbol s){
		childs.add(0,s); 
	}
	
	public int getNumChilds(){
		return childs.size();
	}
	
	public Symbol getChildAt(int n){
		return childs.get(n);
	}
	
	
	
	public String getNonTerminalName(){
		return rule;
	}
	
	protected void pprint(int i){
		for(int j = 0; j < i; j++) {System.out.print(" ");};
		System.out.println("[+] " + rule);
		for (Symbol s : childs) {
			s.pprint(i+4);
		}
	}
    
	public String toString() {
		return "NT"+rule + ":" + childs.size();
	}

}
