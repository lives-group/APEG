package apeg.util;

import java.io.Serializable;

public class SymInfo implements Serializable{
    private int line, col;
    
    public SymInfo(int l, int c){
       line = l;
       col = c;
    }
    
    public int getLine(){return line;}
    public int getColumn(){return col;}

    public String toString(){
      return "( "+ line +" , "+col+" )";
    }
    
}
