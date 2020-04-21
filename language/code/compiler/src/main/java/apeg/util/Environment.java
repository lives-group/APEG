package apeg.util;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Stack;

/**
 * @class Environment: 
 *                    Implements a generic symbol table parameterized by a symbol name (N) and it's value (V).
 *                    This table is based on a hash table so that access to the values is quick. It supports
 *                    expand and retract operations by means of a stack of Hashtables. All operations, except for
 *                    gobalGet, act only on the table at the top of the stack.                        
 * @author deise
 * @since 01/12/2017 
 */

public class Environment <N,V> {

		private Stack<Hashtable<N, V>> st;
		
		/**
		 * Default constructor. 
		 */
	    public Environment(){	
		   st = new Stack<Hashtable<N, V>>();
		   st.push(new Hashtable<N, V>());
	    }
	    
	    /**
	     * convert this environment into a String. For debug purposes only. 
	     */
	    public String toString(){
	    	return toStringf("");
	    }
	    
	    public String toStringf(String id){
	    	String s = id + "------ Environment ----- \n";
	    	for(Entry<N,V> e : st.peek().entrySet()){
	    		s += id + " " + e.getKey().toString() + " |-> " + e.getValue().toString() + "\n";
	    	}
	    	return s;
	    }
	    	
	    
		public void add(N n, V v){ st.peek().put(n,v); }
		
		/**
		 * Returns then value associated with the given name, or null if the name is not present in the top table.
		 * Only the top table is searched.
		 * @param n
		 * @return The value associated with he given name. 
		 */
		public V get(N n){return st.peek().get(n);}
		
		/**
		 * Returns then value associated with the given name, or null if the name is not present in any of the base tables.
	     * All tables on stack are searched. If the given name isn't in any of then, null will be returned.
		 * @param n
		 * @return The value associated with he given name. 
		 */
		public V globalGet(N n){
			 V v = null;
			 for(Hashtable<N,V> t: st){
				v = t.get(n);
				if(v != null){ return v; }
			 }
			 return v;
		}
		
		public boolean contains(N s){ return st.peek().containsKey(s);}
		
		public void extend(){ 
		    st.push(new Hashtable<N,V>());
		}
		
		public Set<N> getNames(){
			//System.out.println("chave: "+ t.keySet());
			HashSet<N> s = new HashSet<N>();
			for( N i : st.peek().keySet()){
			    s.add(i);
			}
			return s;
		}
		
		/**
		 *  Clear the hashtable on the top of the stack.
		 */
		public void clear(){
			st.peek().clear();
		}
}
