
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
	    return char2string(i);
        else
	    return char2string(i) + "-" + char2string(f);
    }

    private String char2string(char ch) {
	switch(ch) {
	  case '\n': return "\\\\n";
	  case '\r': return "\\\\r";
          case '\t': return "\\\\t";
	  case '\b': return "\\\\b";
	  case '\f': return "\\\\f";
	  case '\\': return "\\\\";
	  case '\'': return "\\\'";
	  case '\"': return "\\\"";
	  default: return Character.toString(ch);
	}
    }
}
