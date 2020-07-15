
package apeg.util;

public class CharInterval{
    private char i,f;
    
    public CharInterval(char i, char f){
       this.i = i < f ? i : f;
       this.f = i < f ? f : i;
    }
    
    public CharInterval(char i){
       this.i = i;
       f = i;
    }
    
    public boolean inInterval(char c){
        return (i <= c) && (c <= f) ;
    }
    
    public char getStart(){return i;}
    public char getEnd(){return f;}

    public String toString() {
	if(i == f)
	    return String.valueOf(i);
        else
	    return i + "-" + f;
    }
}
