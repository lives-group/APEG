package apeg.vm;

import java.util.*;
import java.io.IOException;


public class PageStreamTest{
   public static void main(String[] args) throws IOException {
      PageStream p = new PageStream(args[0]);
      for(int i = 0; i< 5; i++){
          p.next();
      }
      // Começa o S;
      p.startBuffer();
      p.mark();
      for(int i = 0; i< 5; i++){
          p.next();
      }
      p.restore();
 
      for(int i = 0; i< 3; i++){
          p.next();
      }
      String s = p.getBuffer();
      p.endBuffer();
      // Acaba o S;
      
      System.out.println(" ; s = " + s);
   }

}
