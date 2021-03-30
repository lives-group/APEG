package apeg.vm;

import java.util.*;
import java.io.IOException;

public class Calc{

  private ApegVM vm;

  public Calc(ApegVM virtual){
    vm = virtual;
  }

  public CTX prog(CTX inh) throws IOException{

    CTX syn = new CTX(4);
    CTX ret;

    vm.beginRule("number", new CTX(1));
    ret = number(new CTX(1));
    syn.writeValue("n",ret.readValue("r"));

    vm.beginRule("opid", new CTX(1));
    ret = opid(new CTX(1));
    syn.writeValue("o",ret.readValue("r"));

    vm.beginRule("number", new CTX(1));
    ret = number(new CTX(1));
    syn.writeValue("m",ret.readValue("r"));


    CTX inhCalc = new CTX(3);
    inhCalc.writeValue("o",syn.readValue("o"));
    inhCalc.writeValue("n",syn.readValue("n"));
    inhCalc.writeValue("m",syn.readValue("m"));
    vm.beginRule("calc",inhCalc);
    ret = calc(inhCalc);
    syn.writeValue("x",ret.readValue("x"));

    vm.endRule();
    return syn;

  }

  public CTX calc(CTX inh){
    CTX syn = new CTX(1);
    CTX ret;

    vm.beginAlt();

    if ((Character)inh.readValue("o") == '-') {
      syn.writeValue("x",(Integer)inh.readValue("n") + (Integer)inh.readValue("m"));
    }else if ((Character)inh.readValue("0") == '+') {
      syn.writeValue("x",(Integer)inh.readValue("n") - (Integer)inh.readValue("m"));
    }else if ((Character)inh.readValue("o") == '*') {
      syn.writeValue("x",(Integer)inh.readValue("n") * (Integer)inh.readValue("m"));
    }else if ((Character)inh.readValue("o") == '/') {
      if((Integer)inh.readValue("m") != 0){
        syn.writeValue("x",(Integer)inh.readValue("n") / (Integer)inh.readValue("m"));
      }
    }

    vm.endAlt();
    vm.endRule();
    return syn;

  }

  public CTX digit(CTX inh) throws IOException{

    CTX syn = new CTX(1);
    CTX ret;

    vm.beginAlt();

    switch (vm.nextValue()) {
      case '0':
      syn.writeValue("x1",0);
      vm.endAlt();
      vm.endRule();
      return syn;
      case '1':
      syn.writeValue("x1",1);
      vm.endAlt();
      vm.endRule();
      return syn;
      case '2':
      syn.writeValue("x1",2);
      vm.endAlt();
      vm.endRule();
      return syn;
      case '3':
      syn.writeValue("x1",3);
      vm.endAlt();
      vm.endRule();
      return syn;
      case '4':
      syn.writeValue("x1",4);
      vm.endAlt();
      vm.endRule();
      return syn;
      case '5':
      syn.writeValue("x1",5);
      vm.endAlt();
      vm.endRule();
      return syn;
      case '6':
      syn.writeValue("x1",6);
      vm.endAlt();
      vm.endRule();
      return syn;
      case '7':
      syn.writeValue("x1",7);
      vm.endAlt();
      vm.endRule();
      return syn;
      case '8':
      syn.writeValue("x1",8);
      vm.endAlt();
      vm.endRule();
      return syn;
      case '9':
      syn.writeValue("x1",9);
      vm.endAlt();
      vm.endRule();
      return syn;
      default:
      vm.endAlt();
      vm.endRule();
      return null;
    }
  }

  public CTX opid(CTX inh) throws IOException{

    CTX syn = new CTX(1);
    CTX ret;

    vm.beginAlt();

    switch (vm.nextValue()) {
      case '+':
      syn.writeValue("o",'+');
      vm.endAlt();
      vm.endRule();
      return syn;
      case '-':
      syn.writeValue("x1",'-');
      vm.endAlt();
      vm.endRule();
      return syn;
      case '*':
      syn.writeValue("x1",'*');
      vm.endAlt();
      vm.endRule();
      return syn;
      case '/':
      syn.writeValue("x1",'/');
      vm.endAlt();
      vm.endRule();
      return syn;
      default:
      vm.endAlt();
      vm.endRule();
      return null;
    }
  }



  public CTX number(CTX inh)throws IOException{

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

}
