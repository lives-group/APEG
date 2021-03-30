package apeg.vm;

import java.util.*;
import java.io.IOException;
//import java.ih.NullPointerException;
public class Fat{
  private ApegVM vm;

  public Fat(ApegVM virtual){
    vm = virtual;
  }

  public CTX prog(CTX inh) throws IOException{

    CTX syn = new CTX(3);
    CTX ret;
    // fazer o fat

    vm.beginRule("number", new CTX(1));
    ret = number(new CTX(1));
    syn.writeValue("n",ret.readValue("r"));

    vm.match("!=");

    vm.beginRule("number", new CTX(1));
    ret = number(new CTX(1));
    syn.writeValue("m",ret.readValue("r"));

    CTX inhFat = new CTX(1);
    inhFat.writeValue("n",syn.readValue("n"));
    vm.beginRule("fat",inhFat);
    ret = fat(inhFat);
    syn.writeValue("r",ret.readValue("m"));

    if((Integer)syn.readValue("m") == (Integer)syn.readValue("r")){
      System.out.println("m == r");
    }else{
      System.out.println("ERRO: m != r");
    }

    vm.endRule();
    return syn;
  }


  public CTX fat(CTX inh){

    CTX syn = new CTX(2);
    CTX ret;

    vm.beginAlt();
    if ((Integer) inh.readValue("n")>0) {

      CTX inhFat = new CTX(1);
      inhFat.writeValue("n",(Integer)inh.readValue("n")-1);
      vm.beginRule("fat", inhFat);
      ret = fat(inhFat);
      syn.writeValue("r",ret.readValue("m"));

      syn.writeValue("m", (Integer)inh.readValue("n") * (Integer)ret.readValue("r"));

    }else{
      syn.writeValue("m",1);
    }

    vm.endRule();
    vm.endAlt();
    return syn;
  }


  public CTX number(CTX inh) throws IOException{
    
    CTX syn = new CTX(2);
    CTX ret;

    vm.beginRule("digit", new CTX(1));
    ret = digit(new CTX(1));
    syn.writeValue("r",ret.readValue("x1"));


    while(true) {

      vm.beginRule("digit", new CTX(1));
      ret = digit(new CTX(1));
      if(ret!=null){
        syn.writeValue("aux",ret.readValue("x1"));
        syn.writeValue("r",((Integer)syn.readValue("r")*10) + (Integer)ret.readValue("aux"));
      }else{
        break;
      }
    }

    vm.endRule();
    return syn;
  }

  public CTX digit(CTX inh) throws IOException{

    CTX syn = new CTX(1);
    CTX ret;

    switch (vm.nextValue()) {
      case '0':
      syn.writeValue("x1",0);
      vm.endRule();
      return syn;
      case '1':
      syn.writeValue("x1",1);
      vm.endRule();
      return syn;
      case '2':
      syn.writeValue("x1",2);
      vm.endRule();
      return syn;
      case '3':
      syn.writeValue("x1",3);
      vm.endRule();
      return syn;
      case '4':
      syn.writeValue("x1",4);
      vm.endRule();
      return syn;
      case '5':
      syn.writeValue("x1",5);
      vm.endRule();
      return syn;
      case '6':
      syn.writeValue("x1",6);
      vm.endRule();
      return syn;
      case '7':
      syn.writeValue("x1",7);
      vm.endRule();
      return syn;
      case '8':
      syn.writeValue("x1",8);
      vm.endRule();
      return syn;
      case '9':
      syn.writeValue("x1",9);
      vm.endRule();
      return syn;
      default:
      vm.endRule();
      return null;
    }
  }
}
