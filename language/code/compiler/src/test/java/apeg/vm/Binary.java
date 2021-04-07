package apeg.vm;

import java.util.*;
import java.io.IOException;

public class Binary{

  private ApegVM vm;

  public Binary(ApegVM virtual){
    vm = virtual;
  }



  public void prog(CTX c) throws IOException{
    vm.beginRule("prog",c);
    CTX aux = new CTX(1);
    number(aux);
    if(!vm.succeed()){
      vm.fail();
      vm.endRule();
      return;
    }
    c.writeValue("r",aux.readValue("r"));
    
    aux = new CTX(2);
    aux.writeValue("r",c.readValue("r"));
    convert(aux);
    if (!vm.succeed()) {
      vm.fail();
      vm.endRule();
      return;
    }
    c.writeValue("st",aux.readValue("st"));
    vm.success();
    vm.endRule();
  }

  public void convert(CTX c){
    vm.beginRule("convert",c);
    CTX aux = new CTX(2);

    c.writeValue("x",(Integer)c.readValue("r")%2);

    vm.beginAlt();

    if((Integer)c.readValue("x") == 0){
      c.writeValue("st",'0');
    }else if((Integer)c.readValue("x") == 1){
      c.writeValue("st",'1');
    }

    vm.success();
    vm.endAlt();
    //duvida r/2 int
    c.writeValue("r",(Integer)c.readValue("r") / 2);

    aux.writeValue("r",c.readValue("r"));
    convert(aux);
    if (!vm.succeed()) {
      vm.fail();
      vm.endRule();
      return;
    }
    c.writeValue("s",aux.readValue("st"));

    c.writeValue("st",(String)c.readValue("s") + (String)c.readValue("st"));

    vm.success();
    vm.endRule();

  }

  public void digit(CTX c) throws IOException{
    vm.beginRule("digit",c);
    CTX aux = new CTX(3);

    vm.beginAlt();
    switch (vm.nextValue()) {
      case '0': c.writeValue("x1",0);
      vm.endAlt();
      vm.endRule();
      return ;
      case '1': c.writeValue("x1",1);
      vm.endAlt();
      vm.endRule();
      return ;
      case '2': c.writeValue("x1",2);
      vm.endAlt();
      vm.endRule();
      return ;
      case '3': c.writeValue("x1",3);
      vm.endAlt();
      vm.endRule();
      return ;
      case '4': c.writeValue("x1",4);
      vm.endAlt();
      vm.endRule();
      return ;
      case '5': c.writeValue("x1",5);
      vm.endAlt();
      vm.endRule();
      return ;
      case '6': c.writeValue("x1",6);
      vm.endAlt();
      vm.endRule();
      return ;
      case '7': c.writeValue("x1",7);
      vm.endAlt();
      vm.endRule();
      return ;
      case '8': c.writeValue("x1",8);
      vm.endAlt();
      vm.endRule();
      return ;
      case '9': c.writeValue("x1",9);
      vm.endAlt();
      vm.endRule();
      return ;
      default:
      vm.failAlt();
      vm.endRule();
      return ;
    }
  }

  // CTX
  // 0  .. k ... n |
  // Inh   | c   | locals

  public void number(CTX c)throws IOException{
    vm.beginRule("number",c);
    CTX aux = new CTX(3);

    digit(aux);
    if (!vm.succeed()) {
      vm.fail();
      vm.endRule();
      return;
    }
    c.writeValue("r",aux.readValue("x1"));

    while(vm.succeed()){
      digit(aux);
      if (!vm.succeed()) {
        vm.fail();
        vm.endRule();
        return;
      }
      c.writeValue("aux",aux.readValue("x1"));
      c.writeValue("r",((Integer)c.readValue("r")*10) + (Integer)c.readValue("aux"));
    }

    vm.succeed();
    vm.endRule();
  }


}
