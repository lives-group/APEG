package apeg.rts;
import java.io.IOException;


public class PegResult {
     
       protected boolean success;
       private String errorMsg;
       
       public PegResult(boolean success){
           this.success = success;
       }
       
       public PegResult(String errorMsg){
           this.errorMsg = errorMsg;
           success = false;
       }
       
       public boolean isSuccess() { return success;}
       
       public String getErrMsg() { return errorMsg;}
}
