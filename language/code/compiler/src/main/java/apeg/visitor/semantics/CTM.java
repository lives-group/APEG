package apeg.visitor.semantics;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import apeg.util.Environment;
import apeg.util.Pair;

public class CTM {


	private LinkedList<Constraint> ct;
	private List<Pair<String, VType>>error;
	String errorMessage;

	public CTM(){
		ct = new LinkedList<Constraint>();
		error = new ArrayList<Pair<String, VType>>();
	}


	public void addConstraint(Constraint c){ ct.add(c);}


	private List<NTType> verifyOp( ArrayList<NTType> nt , VType t){

		ArrayList<NTType>r = new ArrayList<NTType>();

		for(NTType ty : nt) {

			if(ty.match(t)) {
				r.add(ty);
			}
		}

		return r;
	}

	public List<Pair<String, VType>> resolveUnify(Environment<String,ArrayList<NTType>> opTable) {
		//TODO

		OpConstraint op;
		VarConstraint v;
		List<NTType>type;
		int t1 = ct.size()+1;
		
        
		while(!ct.isEmpty() && ct.size() < t1) {
			
            Constraint last = ct.peekLast();
            t1 = ct.size();
             Constraint c;  
            do{
                
             c = ct.remove();   
			
			
			if(c instanceof OpConstraint) {

				op = (OpConstraint) c;


				type = verifyOp(opTable.get(op.getOpName()), op.getType());
				if(type.size() == 0) {
					errorMessage = "Error: No constraints matching for " + op.getOpName() ;
					System.out.println(errorMessage);
					error.add(new Pair<String, VType>(errorMessage, new TypeError() ));
				}
				else {
					if(type.size()>1) {
						
						ct.add(c);
					}
					else {
						if(type.size() == 1) {

							op.getType().Unify(type.get(0));
							
						}
					}
				}

			}
			else {

				if(c instanceof VarConstraint) {

					v = (VarConstraint) c;

					if(!v.getVarName().Unify(v.getType())) {

						ct.add(c);
					}
					

				}
			}
			
        }while(c != last);

		}
        if(!ct.isEmpty()){
                       errorMessage = "Error: impossible to solve constraints" ;
					System.out.println(errorMessage);
					error.add(new Pair<String, VType>(errorMessage, new TypeError() ));
        }
		return error;
	}


	public String toString(){
		String s = "";
		for(Constraint c : ct){
			s += c.toString() + "\n";
		}
		return s;
	}

}


