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

  public boolean match(String s) throws IOException{
     lastResult = (page.match(s));
     return lastResult;
  }

  public boolean EPS(){
    lastResult = true;
    return true;
  }
  
  public boolean succeed(){ return lastResult;}
  
  public void fail(){ lastResult = false;}
  public void success(){ lastResult = true;}
  
  public boolean any(){ return true; }

  public void beginRule(String n, CTX c){
     page.mark();
     ctx.push(c);
     rule.push(n);
  }
  
  public void endRule(boolean r){
     page.unmark();
     ctx.pop();
     rule.pop();
     return r;
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
  }

  public void retryAlt(){ 
     page.restore();
     ctx.pop();
     ctx.push(ctx.peek().cloneContext());
  }

  public void setValue(String n, Object v){
    ctx.peek().writeValue(n,v);
  }
  
  public void setValue(int n, Object v){
    ctx.peek().writeValue(n,v);
  }
  
  public Object getvalue(String var){
    return ctx.peek().readValue(var);
  }
  
  public Object getvalue(int n){
    return ctx.peek().readValue(n);
  }
  
}
