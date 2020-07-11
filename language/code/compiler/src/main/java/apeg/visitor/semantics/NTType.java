package apeg.visitor.semantics;

public class NTType extends VType {
   private VType[] VTypes;
   private int inherited;
   
   public NTType(VType params[], VType returns[]){
	   super("func");
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
   
@Override
   public boolean match(VType t){

        if(t instanceof NTType){

        NTType n = (NTType)t;
        System.out.println("NTType match called");
	   boolean r = false;
	   if(getNumInherited() == n.getNumInherited() && getNumSintetized() == n.getNumSintetized()){
		   r = true;
		   for(int i = 0; i < n.getNumParams(); i++){
			  r = r && getPType(i).match(n.getPType(i)); 
		   }
	   }
	   return r;
	   } else{
            return false;
            }
   }

    public VType getPType(int i){

        
        if(i >= VTypes.length){throw new ArrayIndexOutOfBoundsException("Index:  " + i);} 
	   return VTypes[i];
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
	     s += VTypes[0].toString();
    	 for(int i =1; i< inherited; i++){
    	    s += ", " + VTypes[i].toString();  
         }
      }
      s += ") -> (";
      if(VTypes.length - inherited > 0){
        s += VTypes[inherited].toString();
 	    for(int i =inherited+1; i< VTypes.length; i++){
 	       s += ", " + VTypes[i].toString();  
        }
      }
      s += ")";
      return s;
   }


@Override
public boolean matchCT(VType t, CTM ct) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean Unify(VType t) {
	// TODO Auto-generated method stub
	return false;
}
}
