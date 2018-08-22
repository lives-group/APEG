package apeg.rts;


public class TesteStateFull extends StateFullBaseParser{

    public TesteStateFull(String fname) {
      super(fname);
    }
    //
    // S -> 'a' 'b' / 'c' Q;
    // Q -> '010'* '01' '10';
    // R -> &''
    //
    public PegResult s(){
        startRule("s");
        System.out.print("Starting rule s");
        mkBacktracPoint();
        match('a'); 
        if (isOk()){ 
           match('b');
           if (isOk()){ 
              return success();      
           }     
        }      
        System.out.println("\'ab\' failed.");
        restore();    
        mkBacktracPoint();
        System.out.println("Restored input/stack. Trying \'c\' Q");
        match('c');
        if (isOk()){ 
            q();
            if(isOk()){
               System.out.println("q call succeded");
               return success();  
            }
            System.out.println("q call failed");
        }
        dismissBacktracPoint();
        return fail(); 
    }
    
    public PegResult q(){
        startRule("q");
        System.out.println("Starting rule q");
        do{
           mark();
           match("010");
           if(!isOk()){restore();}else{unmark();}
        }while(isOk());
        done();
        match("01");
        if(isOk()){
           match("10");
           if(isOk()){
              System.out.println("Succed rule q");
              return success();
           }
        }
        System.out.println("Failed rule q");
        return fail();
    }
}
