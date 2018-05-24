package apeg.rts;

import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;


public class PageStream {
     private FileReader f; 
     private char[][] blocks;
     private int blocksize;
     private Stack<Integer> marks;
     private int pw;
     private int pr;
	
	 public PageStream(String path) throws IOException{
	    blocksize = 4;
	    f = new FileReader(path);
	    blocks = new char[128][];
	    pw = 0;
	    pr = 0;
	    load(0);
	    marks = new Stack<Integer>();
	 }
	 
	 private int decode_page(int pos){ return pos / blocksize;}
	 private int decode_pos(int pos){ return pos % blocksize;}
	 
	 private void load(int page) throws IOException{
		  if(blocks[page] == null){
		      blocks[page] = new char[blocksize];
		  }
	      int n = f.read(blocks[page]);
	      pw = pw + n;
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
	   int ant = pr;
	   pr = marks.pop();
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
