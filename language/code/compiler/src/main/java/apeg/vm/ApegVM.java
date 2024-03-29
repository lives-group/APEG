package apeg.vm;

import java.io.*;
import java.util.*;

public class ApegVM {

  private PageStream page;
  private Stack<CTX> ctx;
  private Stack<String> rule;
  private boolean lastResult;

  public ApegVM(String path){
    try{
      ctx = new Stack<CTX>();
      page = new PageStream(path);
      rule = new Stack<String>();
      lastResult = true;
    }catch(IOException e) {
      System.out.println(e);
    }
  }
  public int size(){
    return ctx.size();
  }
  public boolean match(String s) throws IOException{
   lastResult = (page.match(s));
   return lastResult;
 }

 public boolean EPS(){
  lastResult = true;
  return true;
}

public char nextValue() throws IOException{
  return page.next();
}

public boolean succeed(){ return lastResult;}

public void fail(){ lastResult = false;}
public void success(){ lastResult = true;}

  //qualquer exetro " ou ' tem q por if != ' ou (char)34
public boolean any(){
  try {
    page.next();
  } catch(IOException e) {
    lastResult = false;
  }
  return lastResult;
}

public void beginRule(String n, CTX c){
 page.mark();
 ctx.push(c);
 rule.push(n);
}

public CTX endRule(/*boolean r*/){
 page.unmark();
 rule.pop();
 return ctx.pop(); 
}

public void beginAlt(){
 page.mark();
 ctx.push(ctx.peek().cloneContext());
}

public void endAlt(){
 page.unmark();
 CTX aux = ctx.pop();
 ctx.pop();
 ctx.push(aux);
 lastResult = true;
}

public void failAlt(){
 page.unmark();
 ctx.pop();
 lastResult = false;
}

public void retryAlt(){
 page.restore();
 ctx.pop();
     //ctx.push(ctx.peek().cloneContext());
}

public void setValue(String n, Object v){
  ctx.peek().writeValue(n,v);
}

public void setValue(int n, Object v){
  ctx.peek().writeValue(n,v);
}

public Object getValue(String var){
  return ctx.peek().readValue(var);
}

public Object getValue(int n){
  return ctx.peek().readValue(n);
}

public CTX getCTX(){
  return ctx.peek();
}

public void restore(){
  page.restore();
}

public void startBind(){
  page.startBuffer();
}

public String getBind(){
  return page.getBuffer();
}

public void stopBind(){
  page.endBuffer();
}



//   //testar clone
//   public String toString() {
//     Stack<CTX> c = (Stack<CTX>)ctx.clone();
//     Stack<String> r = (Stack<String>)rule.clone();
//     String s = "";
//     System.out.println(c.size()+""+r.size());
//     try{
//     System.out.println(r+""+c);
//     while(r != null && c != null){
//       s+="\n\n"+r.pop().toString()+"\n"+c.pop().toString();      
//     }
//     }catch(Exception e){
//         System.out.println(e);
//     }
//     return s;
//   }



}
