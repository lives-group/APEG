package apeg.vm;

import java.util.*;
import java.io.IOException;

public class Test{
   public static void main(String[] args) throws IOException {
       ApegVM vm = new ApegVM(args[0]);
       Binary bin = new Binary(vm);
       CTX inh = new CTX(1);
       inh.declareParam("x1",0,0);
       bin.digit(inh);
       System.out.print(inh.toString());

       /*CTX o = new CTX(1);
       o.writeValue("n",0);
       vm.beginRule("A", o);
       vm.beginAlt();
       if (vm.match("X")) {
           vm.setValue("n", "10");
           vm.endAlt();
       }else{
           vm.retryAlt();
           if (vm.match("V")) {
              vm.setValue("n", "5");
              vm.endAlt();
           }else{
              vm.retryAlt();
              if (vm.match("I")) {
                 vm.setValue("n", "1");
                 vm.endAlt();
              }
           }
       }
       System.out.println(vm.getvalue("n"));*/
       //Fat f = new Fat(vm);
       //f.prog();
   }
}
