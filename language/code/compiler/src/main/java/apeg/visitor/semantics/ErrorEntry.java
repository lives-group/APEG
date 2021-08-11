package apeg.visitor.semantics;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import apeg.util.SymInfo;


public class ErrorEntry  {
     public int errCode;
     public SymInfo info;
     public String name;
     public VType t[];

     public ErrorEntry(int code, SymInfo s, String symName, VType t[]){
         this.info = s;
         this.t = t;
         this.name = symName;
         errCode = code;
     }
     
     public boolean equals(Object o){
          if(o instanceof ErrorEntry){
              return ((ErrorEntry)o).errCode == errCode;
          }
          return false;
     }
}
