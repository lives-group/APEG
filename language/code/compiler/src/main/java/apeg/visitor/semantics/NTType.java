package apeg.visitor.semantics;

public class NTType {
   private VType[] VTypes;
   private int inherited;
   
   public NTType(VType params[], VType returns[]){
	   VTypes = new VType[params.length + returns.length];
	   int i = 0;
	   inherited = params.length;
	   for(VType n : params){
		   VTypes[i] = n;
		   i++;
	   }
	   for(VType n : returns){
		   VTypes[i] = n;
		   i++;   
	   }
   }
   
   public int getNumSintetized(){ 
	   return VTypes.length - inherited;
   }
   
   public int getNumInherited(){ 
	   return inherited;
   }
   
   public VType getParamAt(int i){
       if(i >= inherited){ throw new ArrayIndexOutOfBoundsException("Index:  " + i);} 
	   return VTypes[i];
   }
   
   public boolean match(NTType n){
	   boolean r = false;
	   if(getNumInherited() == n.getNumInherited() && getNumSintetized() == n.getNumSintetized()){
		   r = true;
		   for(int i = 0; i < n.getNumParams(); i++){
			  r = r && getParamAt(i).match(n.getParamAt(i)); 
		   }
	   }
	   return r;
	   
   }
   
   public boolean matchInherited(VType n[]){
	   boolean r = false;
	   if(getNumInherited() == n.length){
		   r = true;
		   for(int i=0; i< n.length; i++){
			   r = r && getParamAt(i).match(n[i]);
		   }
	   }
	   return r;
   }
   
   public boolean matchSintetized(VType n[]){
	   boolean r = false;
	   if(getNumSintetized() == n.length){
		   r = true;
		   for(int i=0; i< n.length; i++){
			   r = r && getReturnAt(i).match(n[i]);
		   }
	   }
	   return r;
   }
   
   
   
   public VType getReturnAt(int i){
       if(i > getNumSintetized()){ throw new ArrayIndexOutOfBoundsException("Index:  " + i);} 
	   return VTypes[inherited+i];
   }
   
   public int getNumParams(){
	   return VTypes.length;
   }
   
   public VType getVType(int i){
	return VTypes[i];
	   
   }
     
   public String toString(){
	  String s = "("; 
      if(inherited > 0){
	     s += VTypes[0].getName();
    	 for(int i =1; i< inherited; i++){
    	    s += ", " + VTypes[i].getName();  
         }
      }
      s += ") -> (";
      if(VTypes.length - inherited > 0){
        s += VTypes[inherited].getName();
 	    for(int i =inherited+1; i< VTypes.length; i++){
 	       s += ", " + VTypes[i].getName();  
        }
      }
      s += ")";
      return s;
   }
}
