package apeg.vm;

import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

public class PageStream{

     private char[][] blocks;
     private int blocksize;
     private Stack<Integer> marks;
     private int pw;
     private int pr;
     
     private Stack<Integer> recStart; // para usar o Bind
     
     private FileReader f;

	 public PageStream(String path) throws IOException{
	    blocksize = 4;
	    f = new FileReader(path);
	    blocks = new char[128][];
	    pw = 0;
	    pr = 0;
	    load(0);
	    marks = new Stack<Integer>();
	    recStart = new Stack<Integer>();
	 }
	 
	 public void startBuffer(){ recStart.push(pr);}
	 
     public String getBuffer(){
         char v[] = new char[pr - recStart.peek() +1];
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
	     pr++;
	     return c;
	 }

	 public void mark() {
	   marks.push(pr);
	   //System.out.println("marcou: " + pr);
	 }

	 public void unmark(){
	   int pos = marks.pop();
	   //System.out.println("desmarcou " + pos);
	 }

	 public void restore(){
     // somente restauro a entrada, nao mais removo a marca, apenas unmark remove
     //pr = marks.peek();
       pr = marks.peek();
	   //System.out.println("de " + ant + " restaurou para " + pr);
	 }

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
