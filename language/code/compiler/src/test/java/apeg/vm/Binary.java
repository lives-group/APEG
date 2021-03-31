package apeg.vm;

import java.util.*;
import java.io.IOException;

public class Binary{

  private ApegVM vm;

  public Binary(ApegVM virtual){
    vm = virtual;
  }

  public void digit(CTX c) throws IOException{
    vm.beginRule("digit",c);
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
      case '4':
                c.writeValue("x1",4);
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
  
//   public void number(CTX inh)throws IOException{
//     vm.beginRule("number",inh);
//     Integer r, aux;
//     CTX c = new CTX(2);
//     CTX ret;
//     
//     ret = digit(new CTX(1));
//     c.writeValue("r",ret.readValue("x1"));
//     
//     if(vm.succeed()){
//        while(vm.succeed()) {
//          digit(new CTX(0));
//          c.writeValue("aux",ret.readValue("x1"));
//          c.writeValue("r",((Integer)c.readValue("r")*10) + (Integer)ret.readValue("aux"));
//        }
//        vm.succeed();
//        vm.endRule();
//        return c;
//     }
//     vm.endRule();
//     return c;
//   }


}
