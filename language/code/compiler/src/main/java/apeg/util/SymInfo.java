package apeg.util;

public class SymInfo {
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
