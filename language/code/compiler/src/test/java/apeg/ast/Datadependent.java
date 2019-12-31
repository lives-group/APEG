package apeg.ast;

import apeg.ast.*;
import apeg.ast.expr.*;
import apeg.ast.expr.operators.*;
import apeg.ast.rules.*;
import apeg.ast.types.*;

import java.util.List;
import java.util.ArrayList;

public class Datadependent {
    
    public static void main(String args []) {
       
       List<GrammarOption>opts = new ArrayList<GrammarOption>();
       opts.add(GrammarOption.NO_ADAPTABLE);
      
       
       List<RulePEG> rules = new ArrayList<RulePEG> ();
       
       // regra para o literal
       
       
       // criar objeto para a regra strN
       
       
       // criar objeto para a regra number
       
       
       // criar objeto para a regra digit
       
       
       // criar objeto para a regra CHAR
       
       GrammarNode gram =  new GrammarNode("datadependet", opts, null, null, rules);
		
    }

}
