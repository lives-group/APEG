package apeg.parse.ast.visitor.Environments;

import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.Set;

public class RuleEnvironment {

		private Hashtable<String, NTType> t;
		
	    public RuleEnvironment(){	
		   t = new Hashtable<String, NTType>(); 
	    }

	    public String toString(){
	    	String s = "------ Rule Environment ----- \n";
	    	System.out.println("\n");
	    	for(Entry<String,NTType> e : t.entrySet()){
	    		s += " " + e.getKey() + " |-> " + e.getValue().toString() + "\n";
	    	}
	    	return s;
	    }
	    
		public Hashtable<String, NTType> getTable() {
			return t;
		}
		
		public void add(String ruleName, NTType ruleType){
			t.put(ruleName, ruleType);
		}
		
		public Set<String> getRuleNames(){
			//System.out.println("chave: "+ t.keySet());
			return t.keySet();
		}
}
