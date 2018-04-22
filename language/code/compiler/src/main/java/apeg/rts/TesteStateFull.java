package apeg.rts;


public class TesteStateFull extends StateFullBaseParser{

    public TesteStateFull(String fname) {
      super(fname);
    }
    //
    // S -> 'a' 'b' / 'c' Q;
    // Q -> '01'* 
    //
    public PegResult s(){
        startRule("s");
        beginChoices();
        
        match('a'); 
        if (isOk()){ 
           match('b');
           if (isOk()){ 
              return success();      
           }     
        }
        
        undoChoice();   
        
        match('c');
        if (isOk()){ 
            q();
            if(isOk()){
               return success();  
            }
        }
        endChoices();
        
        return fail(); 
    }
    
    public PegResult q(){
        startRule("q");
        do{
           match("01");
        }while(isOk());
        return success();
    }
}
