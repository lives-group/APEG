package apeg.rts;
import java.io.IOException;


public class PResult extends PegResult{
     
       private boolean success;
       
       public PResult(boolean success){
           this.success = success;
       }
       
       public boolean isSuccess() { return success;}
       
       public void turnSuccess(){ succes = true;}
       public void turnFail(){ succes = false;}
}
