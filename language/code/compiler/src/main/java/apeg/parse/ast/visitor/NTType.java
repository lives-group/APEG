package apeg.parse.ast.visitor;

import apeg.parse.ast.TypeNode;

public class NTType {
   private TypeNode[] types;
   private int inherited;
   private int last;
   
   public NTType(int numParam, int numInheriteds){
	   types = new TypeNode[numParam];
	   inherited = numInheriteds;
	   last = 0;
   }
   
   public boolean match(TypeNode[] args){
	   boolean b = true;
	   if(types.length == args.length){
		   for(int k = 0; (k < types.length) && b; k++){
			   b = b && types[k].match(args[k]);
		   }
		   return b;
	   }
	   return false;
   }
   
   public void add(TypeNode n){
       if(last >= types.length){ return; }
	   types[last]  = n;
       last++;
   }
   
   public String toString(){
	  String s = "("; 
      if(inherited > 0){
	     s += types[0].getName();
    	 for(int i =1; i< inherited; i++){
    	    s += ", " + types[i].getName();  
         }
      }
      s += ") -> (";
      if(types.length - inherited > 0){
        s += types[inherited].getName();
 	    for(int i =inherited+1; i< types.length; i++){
 	       s += ", " + types[i].getName();  
        }
      }
      s += ")";
      return s;
   }
}
