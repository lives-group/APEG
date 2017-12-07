package apeg.parse.ast.visitor.Environments;

import apeg.parse.ast.TypeNode;
import apeg.parse.ast.visitor.ASTNodeVisitor;


/**
 * This class represents an error type. This particular kind of type should not occur in the syntax
 * level and matches with any other type. The purpose of this type is to allow semantic verification
 * to go on whenever it encounters an error.  
 *    
 * @author deise
 * 06/12/2017
 */
public class TypeError extends TypeNode {
     
	  public TypeError(){
	     super("Error");  
	  }
	  
	  public boolean match(TypeNode type) { return true; }
	  
	  public void accept(ASTNodeVisitor v){}
}
