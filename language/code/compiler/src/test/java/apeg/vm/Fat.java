package apeg.vm;

import java.util.*;
import java.io.IOException;
//import java.ih.NullPointerException;
public class Fat{
  private ApegVM vm;
  private CTX aux;
  
  public Fat(ApegVM virtual){
    vm = virtual;
  }

  public void prog(CTX c) throws IOException{
    vm.beginRule("prog",c);
    aux = new CTX(1);
    number(aux);
    if(vm.succeed()){
      c.writeValue("n",aux.readValue("r"));
      vm.match("!=");
      aux = new CTX(1);
      number(aux);
      if(vm.succeed()){
        c.writeValue("m",aux.readValue("r"));
        aux = new CTX(2);
        aux.writeValue("n",c.readValue("n"));
        fat(aux);
        if(vm.succeed()){
          c.writeValue("n",aux.readValue("r"));
          if((Integer)c.readValue("m") == (Integer)c.readValue("r")){
            vm.success();
          }
        }
      }
    }
    vm.endRule();
  }


  public void fat(CTX c){
    vm.beginRule("fat",c);
    vm.beginAlt();
    if(vm.succeed()){
      if ((Integer) c.readValue("n") > 0) {
        aux = new CTX(2);
        aux.writeValue("n",(Integer)c.readValue("n") - 1);
        fat(aux);
        if(vm.succeed()){
          c.writeValue("r",aux.readValue("r"));
          c.writeValue("m", (Integer)c.readValue("n") * (Integer)c.readValue("r"));
          vm.endAlt();
        }
      }
      if(!vm.succeed()){
        vm.failAlt();
      }
    }
    if(!vm.succeed()){
      vm.retryAlt();
      c.writeValue("m",1);
      vm.endAlt();
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
