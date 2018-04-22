package apeg.rts;


public class Teste extends BaseParser{

    public Teste(String fname) {
      super(fname);
      startRule("root");
    }

    public boolean s(){
        startRule("s");
        mark();
        if(a1() ){ 
          unmark(); 
          return endSuccess();
        }
        restore();
        alternate();
        mark();
        if(t3() ) { 
          unmark();
          return endSuccess();
        }
        return endFail(); 
      } 

      public boolean t2(){
        startRule("t2");
         
        if(!a1()){
          return endFail();
        }
       
        return endSuccess(); 
      } 

      public boolean t3(){
        startRule("t3");
        mark();
        if(a2() ){ 
          unmark(); 
          return endSuccess();
        }
        restore();
        alternate();
        mark();
        if(t2() ) { 
          unmark();
          return endSuccess();
        }
        return endFail(); 
      } 

      public boolean a1(){
        startRule("a1");
        if(!match("abc")){ 
          return endFail();
        } 
        return endSuccess(); 
      } 

      public boolean a2(){
        startRule("a2");
        if(!match("bb")){ 
          return endFail();
        } 
        return endSuccess(); 
      }  
}
