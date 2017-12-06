package apeg.parse.ast.visitor.Environments;

import apeg.parse.ast.TypeNode;

public class NTType {
   private TypeNode[] types;
   private int inherited;
   
   public NTType(TypeNode params[], TypeNode returns[]){
	   types = new TypeNode[params.length + returns.length];
	   int i = 0;
	   inherited = params.length;
	   for(TypeNode n : params){
		   types[i] = n;
		   i++;
	   }
	   for(TypeNode n : returns){
		   types[i] = n;
		   i++;   
	   }
   }
   
   public int getNumSintetized(){ 
	   return types.length - inherited;
   }
   
   public int getNumInherited(){ 
	   return inherited;
   }
   
   public TypeNode getParamAt(int i){
       if(i >= inherited){ throw new ArrayIndexOutOfBoundsException("Index:  " + i);} 
	   return types[i];
   }
   
   public TypeNode getReturnAt(int i){
       if(i > getNumSintetized()){ throw new ArrayIndexOutOfBoundsException("Index:  " + i);} 
	   return types[inherited+i];
   }
   
   public int getNumParams(){
	   return types.length;
   }
   
   public TypeNode getType(int i){
	return types[i];
	   
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
