/**
 *  APEG Tool Parser 
 *  This code was automatically generated by APEG Parser Generator.
 *  Last modification 27/04/2018
 */
public class Testfunction extends StateFullBaseParser{

      public Testfunction(String fname) {
          super(fname);
          startRule("root");
      }

      public PegResult teste(){
         startRule("teste");
         strN();
         if(isOk()){
             strN(); 
         } 
         if(isOk()){return success();} else{return fail();}      
      }

      public PegResult strN(){
         startRule("strN");
         do{
            mkBacktracPoint();
            CHAR();
            if(isOk()){
                CHAR();
                if(isOk()){
                    mkBacktracPoint();
                    matchNext();
                    if(isOk()){ return endSuccess();}
                    if(isOk()){ restore(); fail();} 
                    else{
                        dismissBacktracPoint();  
                        done(); 
                    } 
                } 
            } 
            if(!isOk()){restore();} else{dismissBacktracPoint();}
         }while(isOk());
         done();
         if(isOk()){
             mkBacktracPoint();
             matchNext();
             if(isOk()){ return endSuccess();}
             if(isOk()){ restore(); fail();} 
             else{
                 dismissBacktracPoint();  
                 done(); 
             }
             if(isOk()){
                 mkBacktracPoint();
                 matchNext();
                 if(isOk()){ return endSuccess();}
                 if(isOk()){ restore(); fail();} 
                 else{
                     dismissBacktracPoint();  
                     done(); 
                 } 
             } 
         } 
         if(isOk()){return success();} else{return fail();}      
      }

      public PegResult CHAR(){
         startRule("CHAR");
         matchNext();
         if(isOk()){ return endSuccess();}
         if(isOk()){return success();} else{return fail();}      
      } 
}