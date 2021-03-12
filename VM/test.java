package VM;

import ProjectJava.ApegVM;
import ProjectJava.CTX;
import ProjectJava.PageStream;
import java.util.*;


public class test{

//adicionei nome da regra dentro do context
//startRule



public static void main(String[] args) {
  ApegVM m = new ApegVM("input.txt");

  Object[] o = new Object[1];
  m.startRule("A", o);
  if (m.match("X")) {
    m.setValue("n", "10");
  }else{
    m.retry();
  }
  if (m.match("V")) {
    m.setValue("n", "5");
  }else{
    m.retry();
  }
  if (m.match("I")) {
    m.setValue("n", "1");
  }else{
    m.retry();
  }

System.out.println(m.getvalue("n"));

}









}
