package apeg.rts;
/**
 *  APEG Tool Parser 
 *  This code was automatically generated by APEG Parser Generator.
 *  Last modification 27/04/2018
 */
public class Peg extends StateFullBaseParser{

      public Peg(String fname) {
          super(fname);
          startRule("root");
      }

      public PegResult s(){
         startRule("s");
         MethodDecl();
         if(isOk()){
             do{
                mkBacktracPoint();
                MethodDecl();
                if(!isOk()){restore();} else{dismissBacktracPoint();}
             }while(isOk());
             done(); 
         } 
         if(isOk()){return success();} else{return fail();}      
      }

      public PegResult Type(){
         startRule("Type");
         mkBacktracPoint();
         match("INT");
         if(isOk()){ return success();}
         restore();    
         mkBacktracPoint();
         match("REAL");
         if(isOk()){ return success();}
         if(isOk()){return success();} else{return fail();}      
      }

      public PegResult MethodDecl(){
         startRule("MethodDecl");
         Type();
         if(isOk()){
             mkBacktracPoint();
             match("MAIN");
             if(isOk()){ dismissBacktracPoint();}
             else{ restore(); done(); } 
             if(isOk()){
                 Id();
                 if(isOk()){
                     match("(");
                     if(isOk()){
                         mkBacktracPoint();
                         FormalParams();
                         if(isOk()){ dismissBacktracPoint();}
                         else{ restore(); done(); } 
                         if(isOk()){
                             match(")");
                             if(isOk()){
                                 Block(); 
                             } 
                         } 
                     } 
                 } 
             } 
         } 
         if(isOk()){return success();} else{return fail();}      
      }

      public PegResult FormalParams(){
         startRule("FormalParams");
         FormalParam();
         if(isOk()){
             do{
                mkBacktracPoint();
                match(",");
                if(isOk()){
                    FormalParam(); 
                } 
                if(!isOk()){restore();} else{dismissBacktracPoint();}
             }while(isOk());
             done(); 
         } 
         if(isOk()){return success();} else{return fail();}      
      }

      public PegResult FormalParam(){
         startRule("FormalParam");
         Type();
         if(isOk()){
             Id(); 
         } 
         if(isOk()){return success();} else{return fail();}      
      }

      public PegResult Block(){
         startRule("Block");
         match("BEGIN");
         if(isOk()){
             Statement();
             if(!isOk()){
              return fail();
             }
             do{
                mkBacktracPoint();
                Statement();
                if(!isOk()){restore();} else{dismissBacktracPoint();}
             }while(isOk());
             done();
             if(isOk()){
                 match("END"); 
             } 
         } 
         if(isOk()){return success();} else{return fail();}      
      }

      public PegResult Statement(){
         startRule("Statement");
         mkBacktracPoint();
         Block();
         if(isOk()){ return success();}
         restore();    
         mkBacktracPoint();
         mkBacktracPoint();
         LocalVarDecl();
         if(isOk()){ return success();}
         restore();    
         mkBacktracPoint();
         mkBacktracPoint();
         AssignStmt();
         if(isOk()){ return success();}
         restore();    
         mkBacktracPoint();
         mkBacktracPoint();
         ReturnStmt();
         if(isOk()){ return success();}
         restore();    
         mkBacktracPoint();
         mkBacktracPoint();
         IfStmt();
         if(isOk()){ return success();}
         restore();    
         mkBacktracPoint();
         mkBacktracPoint();
         WriteStmt();
         if(isOk()){ return success();}
         restore();    
         mkBacktracPoint();
         ReadStmt();
         if(isOk()){ return success();}
         if(isOk()){ return success();}
         if(isOk()){ return success();}
         if(isOk()){ return success();}
         if(isOk()){ return success();}
         if(isOk()){ return success();}
         if(isOk()){return success();} else{return fail();}      
      }

      public PegResult LocalVarDecl(){
         startRule("LocalVarDecl");
         mkBacktracPoint();
         Type();
         if(isOk()){
             Id();
             if(isOk()){
                 match(";"); 
             } 
         } 
         if(isOk()){ return success();}
         restore();    
         mkBacktracPoint();
         Type();
         if(isOk()){
             AssignStmt(); 
         } 
         if(isOk()){ return success();}
         if(isOk()){return success();} else{return fail();}      
      }

      public PegResult AssignStmt(){
         startRule("AssignStmt");
         Id();
         if(isOk()){
             match(":=");
             if(isOk()){
                 Expression();
                 if(isOk()){
                     match(";"); 
                 } 
             } 
         } 
         if(isOk()){return success();} else{return fail();}      
      }

      public PegResult ReturnStmt(){
         startRule("ReturnStmt");
         match("RETURN");
         if(isOk()){
             Expression();
             if(isOk()){
                 match(";"); 
             } 
         } 
         if(isOk()){return success();} else{return fail();}      
      }

      public PegResult IfStmt(){
         startRule("IfStmt");
         mkBacktracPoint();
         match("IF");
         if(isOk()){
             match("(");
             if(isOk()){
                 BoolExpression();
                 if(isOk()){
                     match(")");
                     if(isOk()){
                         Statement(); 
                     } 
                 } 
             } 
         } 
         if(isOk()){ return success();}
         restore();    
         mkBacktracPoint();
         match("IF");
         if(isOk()){
             match("(");
             if(isOk()){
                 BoolExpression();
                 if(isOk()){
                     match(")");
                     if(isOk()){
                         Statement();
                         if(isOk()){
                             match("ELSE");
                             if(isOk()){
                                 Statement(); 
                             } 
                         } 
                     } 
                 } 
             } 
         } 
         if(isOk()){ return success();}
         if(isOk()){return success();} else{return fail();}      
      }

      public PegResult WriteStmt(){
         startRule("WriteStmt");
         match("WRITE");
         if(isOk()){
             match("(");
             if(isOk()){
                 Expression();
                 if(isOk()){
                     match(",");
                     if(isOk()){
                         Expression();
                         if(isOk()){
                             match(")");
                             if(isOk()){
                                 match(";"); 
                             } 
                         } 
                     } 
                 } 
             } 
         } 
         if(isOk()){return success();} else{return fail();}      
      }

      public PegResult ReadStmt(){
         startRule("ReadStmt");
         match("READ");
         if(isOk()){
             match("(");
             if(isOk()){
                 Id();
                 if(isOk()){
                     match(")");
                     if(isOk()){
                         match(";"); 
                     } 
                 } 
             } 
         } 
         if(isOk()){return success();} else{return fail();}      
      }

      public PegResult Expression(){
         startRule("Expression");
         MultiplicativeExpr();
         if(isOk()){
             do{
                mkBacktracPoint();
                mkBacktracPoint();
                match("+");
                if(isOk()){ return success();}
                restore();    
                mkBacktracPoint();
                match("-");
                if(isOk()){ return success();}
                if(isOk()){
                    MultiplicativeExpr(); 
                } 
                if(!isOk()){restore();} else{dismissBacktracPoint();}
             }while(isOk());
             done(); 
         } 
         if(isOk()){return success();} else{return fail();}      
      }

      public PegResult MultiplicativeExpr(){
         startRule("MultiplicativeExpr");
         PrimaryExpr();
         if(isOk()){
             do{
                mkBacktracPoint();
                mkBacktracPoint();
                match("*");
                if(isOk()){ return success();}
                restore();    
                mkBacktracPoint();
                match("/");
                if(isOk()){ return success();}
                if(isOk()){
                    PrimaryExpr(); 
                } 
                if(!isOk()){restore();} else{dismissBacktracPoint();}
             }while(isOk());
             done(); 
         } 
         if(isOk()){return success();} else{return fail();}      
      }

      public PegResult PrimaryExpr(){
         startRule("PrimaryExpr");
         mkBacktracPoint();
         Number();
         if(isOk()){ return success();}
         restore();    
         mkBacktracPoint();
         mkBacktracPoint();
         Id();
         if(isOk()){ return success();}
         restore();    
         mkBacktracPoint();
         mkBacktracPoint();
         match("(");
         if(isOk()){
             Expression();
             if(isOk()){
                 match(")"); 
             } 
         } 
         if(isOk()){ return success();}
         restore();    
         mkBacktracPoint();
         Id();
         if(isOk()){
             match("(");
             if(isOk()){
                 ActualParams();
                 if(isOk()){
                     match(")"); 
                 } 
             } 
         } 
         if(isOk()){ return success();}
         if(isOk()){ return success();}
         if(isOk()){ return success();}
         if(isOk()){return success();} else{return fail();}      
      }

      public PegResult BoolExpression(){
         startRule("BoolExpression");
         mkBacktracPoint();
         Expression();
         if(isOk()){
             match("==");
             if(isOk()){
                 Expression(); 
             } 
         } 
         if(isOk()){ return success();}
         restore();    
         mkBacktracPoint();
         Expression();
         if(isOk()){
             match("!=");
             if(isOk()){
                 Expression(); 
             } 
         } 
         if(isOk()){ return success();}
         if(isOk()){return success();} else{return fail();}      
      }

      public PegResult ActualParams(){
         startRule("ActualParams");
         mkBacktracPoint();
         Expression();
         if(isOk()){
             do{
                mkBacktracPoint();
                match(",");
                if(isOk()){
                    Expression(); 
                } 
                if(!isOk()){restore();} else{dismissBacktracPoint();}
             }while(isOk());
             done(); 
         } 
         if(isOk()){ dismissBacktracPoint();}
         else{ restore(); done(); } 
         if(isOk()){return success();} else{return fail();}      
      }

      public PegResult Number(){
         startRule("Number");
         do{
            mkBacktracPoint();
            Digit();
            if(!isOk()){restore();} else{dismissBacktracPoint();}
         }while(isOk());
         done();
         if(isOk()){
             match(".");
             if(isOk()){
                 do{
                    mkBacktracPoint();
                    Digit();
                    if(!isOk()){restore();} else{dismissBacktracPoint();}
                 }while(isOk());
                 done(); 
             } 
         } 
         if(isOk()){return success();} else{return fail();}      
      }

      public PegResult Id(){
         startRule("Id");
         mkBacktracPoint();
         match("a");
         if(isOk()){ return success();}
         restore();    
         mkBacktracPoint();
         mkBacktracPoint();
         match("b");
         if(isOk()){ return success();}
         restore();    
         mkBacktracPoint();
         mkBacktracPoint();
         match("c");
         if(isOk()){ return success();}
         restore();    
         mkBacktracPoint();
         mkBacktracPoint();
         match("d");
         if(isOk()){ return success();}
         restore();    
         mkBacktracPoint();
         mkBacktracPoint();
         match("e");
         if(isOk()){ return success();}
         restore();    
         mkBacktracPoint();
         mkBacktracPoint();
         match("f");
         if(isOk()){ return success();}
         restore();    
         mkBacktracPoint();
         mkBacktracPoint();
         match("g");
         if(isOk()){ return success();}
         restore();    
         mkBacktracPoint();
         mkBacktracPoint();
         match("h");
         if(isOk()){ return success();}
         restore();    
         mkBacktracPoint();
         mkBacktracPoint();
         match("i");
         if(isOk()){ return success();}
         restore();    
         mkBacktracPoint();
         mkBacktracPoint();
         match("j");
         if(isOk()){ return success();}
         restore();    
         mkBacktracPoint();
         match("k");
         if(isOk()){ return success();}
         if(isOk()){ return success();}
         if(isOk()){ return success();}
         if(isOk()){ return success();}
         if(isOk()){ return success();}
         if(isOk()){ return success();}
         if(isOk()){ return success();}
         if(isOk()){ return success();}
         if(isOk()){ return success();}
         if(isOk()){ return success();}
         if(isOk()){return success();} else{return fail();}      
      }

      public PegResult Digit(){
         startRule("Digit");
         mkBacktracPoint();
         match("0");
         if(isOk()){ return success();}
         restore();    
         mkBacktracPoint();
         mkBacktracPoint();
         match("1");
         if(isOk()){ return success();}
         restore();    
         mkBacktracPoint();
         mkBacktracPoint();
         match("2");
         if(isOk()){ return success();}
         restore();    
         mkBacktracPoint();
         mkBacktracPoint();
         match("3");
         if(isOk()){ return success();}
         restore();    
         mkBacktracPoint();
         mkBacktracPoint();
         match("4");
         if(isOk()){ return success();}
         restore();    
         mkBacktracPoint();
         mkBacktracPoint();
         match("5");
         if(isOk()){ return success();}
         restore();    
         mkBacktracPoint();
         mkBacktracPoint();
         match("6");
         if(isOk()){ return success();}
         restore();    
         mkBacktracPoint();
         mkBacktracPoint();
         match("7");
         if(isOk()){ return success();}
         restore();    
         mkBacktracPoint();
         mkBacktracPoint();
         match("8");
         if(isOk()){ return success();}
         restore();    
         mkBacktracPoint();
         match("9");
         if(isOk()){ return success();}
         if(isOk()){ return success();}
         if(isOk()){ return success();}
         if(isOk()){ return success();}
         if(isOk()){ return success();}
         if(isOk()){ return success();}
         if(isOk()){ return success();}
         if(isOk()){ return success();}
         if(isOk()){ return success();}
         if(isOk()){return success();} else{return fail();}      
      } 
}