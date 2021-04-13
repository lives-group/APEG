package apeg.vm;

import java.util.*;
import java.io.IOException;

public class Binary{

  private ApegVM vm;
  private CTX aux;

  public Binary(ApegVM virtual){
    vm = virtual;
  }

  //erro loop. restaurar caso erro
  // vm usando string no lugar de char
  //erro match
  public void prog(CTX c) throws IOException{
    vm.beginRule("prog",c);
    aux = new CTX(1);
    number(aux);
    if(vm.succeed()){
      c.writeValue("r",aux.readValue("r"));
      aux = new CTX(2);
      aux.writeValue("r",c.readValue("r"));
      convert(aux);
      if (vm.succeed()) {
        c.writeValue("st",aux.readValue("st"));
      }
    }
    vm.endRule();
  }

  public void convert(CTX c){
    vm.beginRule("convert",c);
    c.writeValue("x",(Integer)c.readValue("r")%2);
    vm.beginAlt();
    if(vm.succeed()){
      if((Integer)c.readValue("x") == 0){
        c.writeValue("st","0");
        vm.endAlt();
      }
      if(!vm.succeed()){
        vm.failAlt();
      }
    }
    if(!vm.succeed()){
      vm.retryAlt();
      if((Integer)c.readValue("x") == 1){
        c.writeValue("st","1");
        vm.endAlt();
      }
      if(!vm.succeed()){
        vm.failAlt();
      }
    }
    if(vm.succeed()){
      //duvida r/2 int
      c.writeValue("r",(Integer)c.readValue("r") / 2);
      aux=new CTX(2);
      aux.writeValue("r",c.readValue("r"));
      convert(aux);
      if (vm.succeed()) {
        c.writeValue("s",aux.readValue("st"));
        c.writeValue("st",(String)c.readValue("s") + (String)c.readValue("st"));
      }
    }
    vm.endRule();
  }

  public void digit(CTX c) throws IOException{
    vm.beginRule("digit",c);
    vm.beginAlt();
    if(vm.succeed()){
      if((Character)vm.nextValue() == '0') {
        c.writeValue("x1",0);
        vm.success();
        vm.endRule();
      }
      if(!vm.succeed()){
        vm.failAlt();
      }
    }
    if(!vm.succeed()){
      vm.retryAlt();
      if((Character)vm.nextValue() == '1') {
        c.writeValue("x1",1);
        vm.success();
      }
      if(!vm.succeed()){
        vm.failAlt();
      }
    }
    if(!vm.succeed()){
      vm.retryAlt();
      if((Character)vm.nextValue() == '2') {
        c.writeValue("x1",2);
        vm.success();
      }
      if(!vm.succeed()){
        vm.failAlt();
      }
    }
    if(!vm.succeed()){
      vm.retryAlt();
      if((Character)vm.nextValue() == '3') {
        c.writeValue("x1",3);
        vm.success();
      }
      if(!vm.succeed()){
        vm.failAlt();
      }
    }
    if(!vm.succeed()){
      vm.retryAlt();
      if((Character)vm.nextValue() == '4') {
        c.writeValue("x1",4);
        vm.success();
      }
      if(!vm.succeed()){
        vm.failAlt();
      }
    }
    if(!vm.succeed()){
      vm.retryAlt();
      if((Character)vm.nextValue() == '5') {
        c.writeValue("x1",5);
        vm.success();
      }
      if(!vm.succeed()){
        vm.failAlt();
      }
    }
    if(!vm.succeed()){
      vm.retryAlt();
      if((Character)vm.nextValue() == '6') {
        c.writeValue("x1",6);
        vm.success();
      }
      if(!vm.succeed()){
        vm.failAlt();
      }
    }
    if(!vm.succeed()){
      vm.retryAlt();
      if((Character)vm.nextValue() == '7') {
        c.writeValue("x1",7);
        vm.success();
      }
      if(!vm.succeed()){
        vm.failAlt();
      }
    }
    if(!vm.succeed()){
      vm.retryAlt();
      if((Character)vm.nextValue() == '8') {
        c.writeValue("x1",8);
        vm.success();
      }
      if(!vm.succeed()){
        vm.failAlt();
      }
    }
    if(!vm.succeed()){
      vm.retryAlt();
      if((Character)vm.nextValue() == '9') {
        c.writeValue("x1",9);
        vm.success();
      }
      if(!vm.succeed()){
        vm.failAlt();
      }
    }
    vm.endRule();
  }

  // CTX
  // 0  .. k ... n |
  // Inh   | c   | locals

  public void number(CTX c)throws IOException{
    vm.beginRule("number",c);
    aux = new CTX(1);
    digit(aux);
    if (vm.succeed()) {
      c.writeValue("r",aux.readValue("x1"));
      while(vm.succeed()){
        aux = new CTX(1);
        digit(aux);
        if (vm.succeed()) {
          c.writeValue("aux",aux.readValue("x1"));
          c.writeValue("r",((Integer)c.readValue("r")*10) + (Integer)c.readValue("aux"));
          vm.success();
        }
      }
    }
    vm.endRule();
  }

}
