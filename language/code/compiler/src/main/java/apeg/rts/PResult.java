package apeg.rts;
import java.io.IOException;


public class PResult {
     
       private boolean success;
       private String errorMsg;
       
       public PResult(boolean success){
           this.success = success;
       }
       
       public PResult(String errorMsg){
           this.errorMsg = errorMsg;
           success = false;
       }
       
       public boolean isSuccess() { return success;}
       
       public void turnSuccess(){ succes = true;}
       public void turnFail(){ succes = false;}
       
       public String getErrMsg() { return errorMssg;}
}
