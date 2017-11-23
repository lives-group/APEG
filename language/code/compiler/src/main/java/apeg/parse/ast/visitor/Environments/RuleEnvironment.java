package apeg.parse.ast.visitor.Environments;

import java.util.HashSet;
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
	    
		//public Hashtable<String, NTType> getTable() {
		//	return t;
		//}
		
		public void add(String ruleName, NTType ruleType){
			t.put(ruleName, ruleType);
		}
		
		public NTType get(String s){
			return t.get(s);
		}
		
		public boolean contains(String s){
			return t.containsKey(s);
		}
		
		public Set<String> getRuleNames(){
			//System.out.println("chave: "+ t.keySet());
			HashSet<String> s = new HashSet<String>();
			for( String i : t.keySet()){
			    s.add(i);
			}
			return s;
		}
}
