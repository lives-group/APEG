package apeg.vm;

import java.io.FileReader;
import java.io.StringReader;
import java.io.Reader;
import java.io.IOException;
import java.util.Stack;

public class PageStream{
     
     private class Tern{
        public Integer a,b,c;
        public Tern(Integer a,Integer b,Integer c){
           this.a = a;
           this.b = b;
           this.c = c;
        }
        public boolean equals(Object o){
           if(o instanceof Tern){
               return ((Tern)o).a == a;
           }
           return false;
        }
     }
     
     private char[][] blocks;
     private int blocksize;
     private Stack<Tern> marks;
     private int pw, pr;
     private int line,col;
     
     
     private Stack<Integer> recStart; // para usar o Bind
     
     private Reader f;

	 public PageStream(String path) throws IOException{
	    blocksize = 4;
	    f = new FileReader(path);
	    blocks = new char[128][];
	    pw = 0;
	    pr = 0;
	    load(0);
	    marks = new Stack<Tern>();
	    recStart = new Stack<Integer>();
	    line = 0;
        col = 0;
	 }
	 
	 public PageStream(StringReader sr) throws IOException{
	    blocksize = 4;
	    f = sr;
	    blocks = new char[128][];
	    pw = 0;
	    pr = 0;
	    load(0);
	    marks = new Stack<Tern>();
	    recStart = new Stack<Integer>();
	    line = 0;
        col = 0;
	 }
	 
	 public void startBuffer(){ recStart.push(pr);}
	 
     public String getBuffer(){
         char v[] = new char[pr - recStart.peek()];
		 for(int j = recStart.peek(); j < pr; j++){
			 v[j-recStart.peek()] = blocks[decode_page(j)][decode_pos(j)];
		 }
		 return new String(v);
     }
     
     public void endBuffer(){ recStart.pop();}
 
	 private int decode_page(int pos){ return pos / blocksize;}
	 private int decode_pos(int pos){ return pos % blocksize;}

	 private void load(int page) throws IOException{
		  if(blocks[page] == null){
		      blocks[page] = new char[blocksize];
		  }
	      int n = f.read(blocks[page]);
	      if(n != -1 ){
	          pw = pw + n;
	      }
	 }

	 public boolean match(String s) throws IOException{
		 int i = 0 ;
		 int pi = decode_page(pr);
		 int pf = decode_page(pr+s.length()-1);
		 for(int j = pi; j <= pf; j++){
			 if(blocks[j] == null){ load(j);}
		 }
		 if(pr+s.length() > pw){ return false;}
		 while( (i < s.length()) && (s.charAt(i) == blocks[decode_page(pr+i)][decode_pos(pr+i)])  ){
		     line =  blocks[decode_page(pr+i)][decode_pos(pr+i)] == '\n' ? line+1 : line; 
		     col =  blocks[decode_page(pr+i)][decode_pos(pr+i)] == '\n' ? 0 : col+1;
		     i++;
		 }
		 if(i == s.length()){
			 pr += i;
			 return true;
		 }
		 return false;
	 }

	 public char next() throws IOException{
	     if(pr >= pw){
	    	 load(decode_page(pr));
	     }
	     char c = blocks[decode_page(pr)][decode_pos(pr)];
	     line =  blocks[decode_page(pr)][decode_pos(pr)] == '\n' ? line+1 : line; 
         col =  blocks[decode_page(pr)][decode_pos(pr)] == '\n' ? 0 : col+1;
	     pr++;
	     return c;
	 }

	 public void mark() {
	   marks.push(new Tern(pr,line,col));
	   //System.out.println("marcou: " + pr);
	 }

	 public void unmark(){
        marks.pop();
	   //System.out.println("desmarcou " + pos);
	 }

	 public void restore(){
     // somente restauro a entrada, nao mais removo a marca, apenas unmark remove
     //pr = marks.peek();
       pr = marks.peek().a;
       line = marks.peek().b;
       col = marks.peek().c;
	 }
	 
	 public int getLine(){ return line;}
	 public int getColumn(){ return col;}

	 public String toString() {
	   String s = "";
	   String mk = "";
	   for(int i = 0; i < pw; i++) {
		    s = s + blocks[decode_page(i)][decode_pos(i)];
		    if(i != pr){
		    	mk = mk + (marks.contains(i) ? "*" : " ");
		    }else{ mk = mk + "^";}
	   }
	   return s + "\n" + mk;
	 }
}
