package apeg.visitor.semantics;

import apeg.ast.types.Type;
import apeg.visitor.Visitor;
import apeg.util.SymInfo;

/**
 * This class represents an error type. This particular kind of type should not occur in the syntax
 * level and matches with any other type. The purpose of this type is to allow semantic verification
 * to go on whenever it encounters an error.  
 *    
 * @author deise
 * 06/12/2017
 */
public class TypeError extends VType {
     
	  private TypeError(){
	      super("Error");  
	  }
	  
	  private static TypeError instance = new TypeError();
		
		public static TypeError getInstance() {
			
			return instance;
		}
	  
	  public boolean match(VType type) { return true; }

       public boolean matchCT(VType type, CTM ct) { return true;}
	  
	  public void accept(Visitor v){}

      public boolean Unify (VType t){ return true;}

}
