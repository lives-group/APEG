/**
 *  APEG Tool Parser 
 *  This code was automatically generated by APEG Parser Generator.
 *  Last modification 27/04/2018
 */
public class KleneeTest extends StateFullBaseParser{

      public KleneeTest(String fname) {
          super(fname);
          startRule("root");
      }

      public PegResult s(){
         startRule("s");
         match("01");
         if(isOk()){
             b(); 
         } 
         if(isOk()){return success();} else{return fail();}      
      }

      public PegResult b(){
         startRule("b");
         do{
            mkBacktracPoint();
            match("010");
            if(isOk()){
                match("A");
                if(isOk()){
                    match("B"); 
                } 
            } 
            if(!isOk()){restore();} else{dismissBacktracPoint();}
         }while(isOk());
         done();
         if(isOk()){
             match("11");
             if(isOk()){
                 d(); 
             } 
         } 
         if(isOk()){return success();} else{return fail();}      
      }

      public PegResult d(){
         startRule("d");
         match("010");
         if(isOk()){
             match("A");
             if(isOk()){
                 match(":-("); 
             } 
         } 
         if(isOk()){return success();} else{return fail();}      
      } 
}