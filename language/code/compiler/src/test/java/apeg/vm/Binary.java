package apeg.vm;

import java.util.*;
import java.io.IOException;

public class Binary{

  private ApegVM vm;

  public Binary(ApegVM virtual){
    vm = virtual;
  }


  public CTX prog(CTX inh) throws IOException{

    CTX syn = new CTX(4);
    CTX ret;

    vm.beginRule("number", new CTX(1));
    ret = number(new CTX(1));
    syn.writeValue("r",ret.readValue("r"));

    while (true) {

      CTX inhVerify = new CTX(3);
      inhVerify.writeValue("r",syn.readValue("r"));
      vm.beginRule("verify",inhVerify);
      ret = verify(inhVerify);
      syn.writeValue("b",ret.readValue("b"));

      if((Boolean)syn.readValue("b") == true){
        //erro
        CTX inhConvert = new CTX(3);
        inhConvert.writeValue("r",syn.readValue("r"));
        vm.beginRule("conver",inhConvert);
        ret = convert(inhConvert);
        syn.writeValue("st",ret.readValue("st"));

        syn.writeValue("s",(Character)syn.readValue("st") + (Character)syn.readValue("s"));

      }else{
        break;
      }

    }

    syn.writeValue("s",'1' + (Character)syn.readValue("s"));

    vm.endRule();
    return syn;
  }


  public CTX verify(CTX inh) throws IOException{

    CTX syn = new CTX(3);
    CTX ret;

    syn.writeValue("b",true);
    syn.writeValue("aux",((Integer)inh.readValue("r")) / 2);
    if((Integer)syn.readValue("aux") == 1){
      syn.writeValue("b",false);
    }

    vm.endRule();
    return syn;
  }


  public CTX convert(CTX inh) throws IOException{

    CTX syn = new CTX(3);
    CTX ret;

    syn.writeValue("x",(Integer)inh.readValue("r") % 2);
    syn.writeValue("r",(Integer)inh.readValue("r") / 2);

    vm.beginAlt();

    if((Integer)syn.readValue("x") == 0){
      syn.writeValue("st",'0');
    }else if((Integer)syn.readValue("x") == 1){
      syn.writeValue("st",'1');
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
