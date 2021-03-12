package VM;

import ProjectJava.PageStream;
import java.util.*;

public class CTX{
  Object[] context;
  Hashtable<String,Integer> hashVar;
  String nameRule;

  public CTX(int tam, String name){
    hashVar = new Hashtable<String,Integer>();
    context = new Object[tam];
    nameRule = name;
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
  @Override
  public void writeValue(Integer i, Object value){
    context[i]=value;
  }

  public Object readValue(String var){
    return context[hashVar.get(var)];
  }

  @Override
  public Object readValue(Integer i){
    return context[i];
  }

  //problema
  public Hashtable cloneHashVar(){
    return 	hashVar.clone();
  }

  //problema
  public Object[] cloneContext(){
    Object[] context2 = new Object[context.length];
    for(int i=0;i<context.length;i++){
      context2[i]=context[i].clone();
    }
    return context2;
  }





}
