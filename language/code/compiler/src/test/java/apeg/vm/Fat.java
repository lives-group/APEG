package apeg.vm;

import java.util.*;
import java.io.IOException;
//import java.io.NullPointerException;
public class Fat{
  private ApegVM vm;

  public Fat(ApegVM virtual){
    vm = virtual;
  }

  public void prog() throws IOException{
    CTX o = new CTX(3);
    o.writeValue("n",0);
    o.writeValue("m",0);
    o.writeValue("r",0);
    vm.beginRule("prog", o);

    vm.beginRule("prog", o);
    vm.setValue("n",number());
    vm.match("!=");
    vm.setValue("m",number());
    vm.setValue("r",fat((Integer)vm.getvalue("n")));

    if((Integer)vm.getvalue("m") == (Integer)vm.getvalue("r")){
      System.out.println("m == r");
    }else{
      System.out.println("ERRO: m != r");
    }

    vm.endRule();
  }


  public int fat(int n){
    CTX o = new CTX(3);
    o.writeValue("n",n);
    o.writeValue("m",0);
    o.writeValue("r",0);
    vm.beginRule("fat", o);

    vm.beginAlt();
    if ((Integer) vm.getvalue("n")>0) {
      vm.setValue("r",fat((Integer)vm.getvalue("n")-1));
      vm.setValue("m", (Integer)vm.getvalue("n") * (Integer)vm.getvalue("r"));
    }else{
      vm.setValue("m",1);
    }

    Object i =(Integer)vm.getvalue("m");
    vm.endRule();
    vm.endAlt();
    return (Integer)i;
  }


  public int number() throws IOException{
    CTX o = new CTX(3);
    o.writeValue("r",0);
    o.writeValue("aux",0);
    vm.beginRule("number", o);
    vm.setValue("r",digit());

    try {
      vm.setValue("aux",digit());

      while ((Integer)vm.getvalue("aux") != null) {
        vm.setValue("r",((Integer)vm.getvalue("r")*10) + (Integer)vm.getvalue("aux"));
        vm.setValue("aux",digit());
      }

      Object i = (Integer)vm.getvalue("r");
      vm.endRule();
      return (Integer)i;

    }catch (IOException e) {

      Object i = (Integer)vm.getvalue("r");
      vm.endRule();
      return (Integer)i;

    }catch (NullPointerException e) {
    }

    Object i = (Integer)vm.getvalue("r");
    vm.endRule();
    return (Integer)i;
  }

  public Integer digit() throws IOException{

    //chato escrever begin end pra cada um
    //pode haver erro, ler o que nao devia

    switch (vm.nextValue()) {
      case '0': return 1;
      case '1': return 1;
      case '2': return 2;
      case '3': return 3;
      case '4': return 4;
      case '5': return 5;
      case '6': return 6;
      case '7': return 7;
      case '8': return 8;
      case '9': return 9;
      default:
      return null;
    }
  }
}
