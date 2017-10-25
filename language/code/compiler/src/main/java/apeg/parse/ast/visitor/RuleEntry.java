package apeg.parse.ast.visitor;

import java.util.Hashtable;
import java.util.Map.Entry;



public class RuleEntry {

		private String nome;
		private Hashtable<String, VarEntry> t;
		private NTType ntt;
		
	    public RuleEntry(String nome){	
		   this.nome = nome;
		   t = new Hashtable<String, VarEntry>(); 
	    }

	    public String toString(){
	    	String s = " ----- SYMBOL : " + nome + " :: " + ntt.toString() + "----- \n";
	    	System.out.println("\n");
	    	for(Entry<String,VarEntry> e : t.entrySet()){
	    		s += " " + e.getKey() + " -> " + e.getValue().toString() + "\n";
	    	}
	    	return s;
	    }
	    
	    public String getNome() {
			return nome;
		}
		public Hashtable<String, VarEntry> getTable() {
			return t;
		}
		public void setNTType(NTType ntt){
		    this.ntt = ntt;
		}
		public NTType getNTType(){
			return ntt;
		}

}