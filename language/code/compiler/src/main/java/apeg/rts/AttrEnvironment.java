package apeg.rts;

import java.util.Stack;

public class AttrEnvironment {
	
	private Stack<Object[]> v;
	private int n;
	
	
	public AttrEnvironment(int n) {
		this.n = n;
		v = new Stack<Object[]>();
		v.push(new Object[n]);
	}
	
	public void setAt(int i, Object y) {
		v.peek()[i] = y;
	}
			
	public Object getAt(int i) {
		return v.peek()[i];
	}
	/**
	 * Start a temporary modification session. All values are copyed 
	 * into a new array at the of the stack. Modifications now will occur only
	 * at the top environment.
	 */
	public void tempMode(){
		Object[] base = v.peek();
		v.push(new Object[n]);
		for(int i =0; i< n;i++){v.peek()[i] = base[i];}
	}
	
	/**
	 * Consolidate the current values to the base environment, without however ending
	 * the temporary session. New modifications still take place at the top (temporary)
	 * environment.
	 */
	public void consolidateTemp(){ 
		if(v.size() > 1) {
		    Object[] v1 = v.pop();
		    Object[] base = v.peek();
		    v.push(v1);
	        for(int i =0; i< n;i++){base[i] = v1[i];}
		}
	}
	
	/**
	 * End the temp mode consolidating all changes.
	 */
	public void endTempMode(){ 
		if(v.size() > 1) {
		    Object[] v1 = v.pop();
	        for(int i =0; i< n;i++){v.peek()[i] = v1[i];}
		}
	}
	/**
	 * End the temp mode, discarding all changes.
	 */
	public void revokeChanges(){ 
		if(v.size() > 1) { v.pop();}
	}
	
	public int size(){return n;} 
	
	public void printTable(String r) {
	   System.out.println(" -=======VALUE ENV FOR "+ r +"======-");
	   for(int i = 0 ; i < n; i++) {
		   System.out.println("["+i+"] -> "+v.peek()[i].toString());   
	   }
	}
}
