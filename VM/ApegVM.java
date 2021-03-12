package VM;
import java.io.*;
import java.util.*;
import ProjectJava.PageStream;

public class ApegVM{
  PageStream page;
  Stack<CTX> ctx;

  public ApegVM(String path){
    try{
      //  ctx = new Stack();
      page = new PageStream(path);
    }catch(IOException e) {
      System.out.println(e);
    }
  }

  public boolean match(String s) throws IOException{
    return (page.match(s));
  }

  public boolean EPS(){
    return true;
  }

  public boolean any() throws IOException{
    try{
      return true;
    }catch (EOFException e) {
      return false;
    }
  }

  public void startRule(String n, Object v[]){
    //cria marca
    page.mark();
    CTX c = new CTX(v.length,n);
    ctx.add(c);
  }

  public void retry(){
    page.restore();
  }

  public void setValue(String n, Object v){
    ctx.peek().writeValue(n,v);
  }

  public Object getvalue(String var){
    return ctx.peek().readValue(var);
  }



}
