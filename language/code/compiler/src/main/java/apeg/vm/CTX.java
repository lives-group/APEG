package apeg.vm;

import java.util.*;
import java.util.Map.Entry;

public class CTX{
  private Object[] context;
  private Object[] ext;
  private Hashtable<String,Integer> hashVar;
  private int p;
 
  public CTX(int tam){
    hashVar = new Hashtable<String,Integer>();
    context = new Object[tam];
    ext = new Object[0]; 
    p = 0;
  }
  
  private void growContext(int k){
      Object[] newExt;
      newExt = new Object[ ext.length + k];
      for(int i = 0; i < ext.length; i ++){
          newExt[i] = ext[i];
      }
      ext = newExt;
  }
  
  public void declareParam(String var, Integer i ,Object value){
    if(hashVar.get(var) != null){
      throw new RuntimeException("Redefined variable: " + var);
    }else{
       hashVar.put(var,i);
       writeValue(i,value);
    }
  }
  
  public void writeValue(String var, Object value){

    Integer i = hashVar.get(var);
    if(i!= null){
      writeValue(i,value);
    }else{
      if( p >= ext.length){
          growContext(4);
      }
      p = p +1;
      hashVar.put(var,context.length + p);
      writeValue(p,value);
    }
  }

  //mudei pra Integer

  public void writeValue(Integer i, Object value){
    if(i >= context.length){
        ext[i-context.length] = value;
    }else{
        context[i]=value;
    }
  }

  public Object readValue(String var){
    Integer i = hashVar.get(var);
    if( i != null ){
       return readValue(i);
    }else{
       throw new RuntimeException("Variable " + var + " not declared ");
    }
  }

  public Object readValue(Integer i){
    if(i >= context.length){
        return ext[i-context.length];
    }else{
        return context[i];
    }
  }

  //problema
  public Hashtable<String,Integer> cloneHashVar(){
    return (Hashtable<String,Integer>)(hashVar.clone());
  }

  //problema
  /** TODO Clonar o EXT também 
   */
  public CTX cloneContext(){
    CTX cl = new CTX(context.length);
    cl.context = new Object[context.length];
    for(int i=0;i<context.length;i++){
      cl.context[i] = context[i];
    }
    cl.hashVar = (Hashtable<String,Integer>)(hashVar.clone());
    return cl;
  }
    
   public String toString(){
        String s = " =========== Context =========== \n";
        s += " ---- Hash table ---- \n";
        for(Entry<String,Integer> e : hashVar.entrySet()){ 
          s += "   " + e.getKey().toString() + " : " + e.getValue().toString() + "\n";
        }
        s += " ------- x ------- \n";
        s += " ---- CTX Param ----\n";
        for(int i =0; i < context.length; i++){
           s += "    "+ i + " : " + context[i] + "\n";
        }
        s += " ---- CTX Ext ----\n";
        for(int i =0; i < ext.length; i++){
           s += "    "+ i + " : " + ext[i] + "\n";
        }
        s += " --x-x-x-x-x-x-x-x--\n";
        return s; 
   }
}
