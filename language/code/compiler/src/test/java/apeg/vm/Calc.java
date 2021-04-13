package apeg.vm;

import java.util.*;
import java.io.IOException;

public class Calc{

  private ApegVM vm;
  private CTX aux;

  public Calc(ApegVM virtual){
    vm = virtual;
  }

  public void prog(CTX c) throws IOException{
    vm.beginRule("prog",c);
    aux= new CTX(1);
    number(aux);
    if(vm.succeed()){
      c.writeValue("n",aux.readValue("r"));
      aux = new CTX(1);
      opid(aux);
      if(vm.succeed()){
        c.writeValue("o",aux.readValue("r"));
        aux = new CTX(1);
        number(aux);
        if(vm.succeed()){
          c.writeValue("m",aux.readValue("r"));
          aux = new CTX(4);
          aux.writeValue("o",c.readValue("o"));
          aux.writeValue("n",c.readValue("n"));
          aux.writeValue("m",c.readValue("m"));
          calc(aux);
          if(vm.succeed()){
            c.writeValue("x",aux.readValue("x"));
            vm.success();
          }
        }
      }
    }
    vm.endRule();
  }

  public void calc(CTX c){
    vm.beginRule("calc",c);
    vm.beginAlt();
    if(vm.succeed()){
      if ((Character)c.readValue("o") == '-') {
        c.writeValue("x",(Integer)c.readValue("n") + (Integer)c.readValue("m"));
        vm.endAlt();
      }
      if(!vm.succeed()){
        vm.failAlt();
      }
    }
    if(!vm.succeed()){
      vm.retryAlt();
      if((Character)c.readValue("0") == '+') {
        c.writeValue("x",(Integer)c.readValue("n") - (Integer)c.readValue("m"));
        vm.endAlt();
      }
      if(!vm.succeed()){
        vm.failAlt();
      }
    }
    if(!vm.succeed()){
      vm.retryAlt();
      if ((Character)c.readValue("o") == '*') {
        c.writeValue("x",(Integer)c.readValue("n") * (Integer)c.readValue("m"));
        vm.endAlt();
      }
      if(!vm.succeed()){
        vm.failAlt();
      }
    }if(!vm.succeed()){
      vm.retryAlt();
      if ((Character)c.readValue("o") == '/') {
        if((Integer)c.readValue("m") != 0){
          c.writeValue("x",(Integer)c.readValue("n") / (Integer)c.readValue("m"));
          vm.endAlt();
        }
      }
      if(!vm.succeed()){
        vm.failAlt();
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

  public void opid(CTX c ) throws IOException{
    vm.beginRule("digit",c);
    vm.beginAlt();
    if(vm.succeed()){
      if((Character)vm.nextValue() == '+'){
        c.writeValue("o",'+');
        vm.endAlt();
      }
      if(!vm.succeed()){
        vm.failAlt();
      }
    }
    if(!vm.succeed()){
      vm.retryAlt();
      if((Character)vm.nextValue() == '-'){
        c.writeValue("o",'-');
        vm.endAlt();
      }
      if(!vm.succeed()){
        vm.failAlt();
      }
    }if(!vm.succeed()){
      vm.retryAlt();
      if((Character)vm.nextValue() == '*'){
        c.writeValue("o",'*');
        vm.endAlt();
      }
      if(!vm.succeed()){
        vm.failAlt();
      }
    }
    if(!vm.succeed()){
      vm.retryAlt();
      if((Character)vm.nextValue() == '/'){
        c.writeValue("o",'/');
        vm.endAlt();
      }
      if(!vm.succeed()){
        vm.failAlt();
      }
    }
    vm.endRule();
  }

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
