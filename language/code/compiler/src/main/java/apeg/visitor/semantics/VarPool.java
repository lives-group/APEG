package apeg.visitor.semantics;

import java.util.ArrayList;

public class VarPool {

	private ArrayList<VTyVar>varList;
	private int i;
	
	private static VarPool instance = new VarPool();
	
	public static VarPool getInstance() {
		
		return instance;
	}
	
	private VarPool() {
		
		varList = new ArrayList<VTyVar>();
		i =0;
	}
	
	public VTyVar newVar() {
		
		VTyVar x = new VTyVar("_" + i);
		
		varList.add(x);
		
		i++;
		
		return x;
	}
		
}
