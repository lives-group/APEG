package apeg.vm;

import java.util.*;

public class CTX{
  Object[] context;
  Hashtable<String,Integer> hashVar;


  public CTX(int tam){
    hashVar = new Hashtable<String,Integer>();
    context = new Object[tam];
  }

  public void writeValue(String var, Object value){

    Integer i=hashVar.get(var);
    if(i!= null){
      context[i]=value;
    }else{
      i = hashVar.size();
      hashVar.put(var,i);
      /// realocar
      context[i]=value;
    }
  }

  //mudei pra Integer

  public void writeValue(Integer i, Object value){
    context[i]=value;
  }

  public Object readValue(String var){
    return context[hashVar.get(var)];
  }

  public Object readValue(Integer i){
    return context[i];
  }

  //problema
  public Hashtable<String,Integer> cloneHashVar(){
    return (Hashtable<String,Integer>)(hashVar.clone());
  }

  //problema
  public CTX cloneContext(){
    CTX cl = new CTX(context.length);
    cl.context = new Object[context.length];
    for(int i=0;i<context.length;i++){
      cl.context[i] = context[i];
    }
    cl.hashVar = (Hashtable<String,Integer>)(hashVar.clone());
    return cl;
  }

}
