/**
 *  APEG Tool Parser
 *  This code was automatically generated by APEG Parser Generator.
 *  Last modification 27/04/2018
 */
public class LitExpr extends StateFullBaseParser{

      public LitExpr(String fname) {
          super(fname);
          startRule("root");
      }

      public PegResult s(Object[] v){
         startRule("s");
         Object[] copy;
         mkBacktracPoint();
         for(int i=0;i < 2 ;i++) {copy[i]=v[i];}
         ss(v);
         for(int i=0;i < 2 ;i++) {v[i]=copy[i];}
         if(isOk()){
             match("0");
             if(isOk()){
                   v[2] = (int) v[0]  - (int) v[1] ;   
             } 
         } 
         if(isOk()){ return success();}
         restore();
         mkBacktracPoint();
         for(int i=0;i < 2 ;i++) {copy[i]=v[i];}
         ss(v);
         for(int i=0;i < 2 ;i++) {v[i]=copy[i];}
         if(isOk()){
             match("1");
             if(isOk()){
                   v[2] = (int) v[0]  + (int) v[1] ;   
             } 
         } 
         if(isOk()){ return success();}
         if(isOk()){return success();} else{return fail();}
      }

      public PegResult ss(Object[] v){
         startRule("ss");
         Object[] copy;
           v[0] = (int) v[0]  + 1;  
         if(isOk()){return success();} else{return fail();}
      }
}