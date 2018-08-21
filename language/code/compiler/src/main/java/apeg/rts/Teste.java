package apeg.rts;

/**
 *  APEG Tool Parser 
 *  This code was automatically generated. 
 */
public class Teste extends BaseParser{

      public Teste(String fname) {
          super(fname);
          startRule("root");
      }

      public boolean s(){
         startRule("s");
         if(!match("01")){return endFail();}  
         if(!b() ){return endFail();}  
         return endSuccess(); 
      }

      public boolean b(){
         startRule("b");
         if(!_t1() ){return endFail();}  
         if(!d() ){return endFail();}  
         return endSuccess(); 
      }

      private boolean _t1(){
         while(_t0() ){}
         return true;
      }

      private boolean _t0(){
         if(!match("010")){return false;}  
         if(!match("A")){return false;}  
         if(!match("B")){return false;}  
         return true;
      }

      public boolean d(){
         startRule("d");
         if(!match("010")){return endFail();}  
         if(!match("A")){return endFail();}  
         if(!match(":-(")){return endFail();}  
         return endSuccess(); 
      } 
}