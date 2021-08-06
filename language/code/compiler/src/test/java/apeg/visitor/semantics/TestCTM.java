package apeg.visitor.semantics;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import apeg.util.Environment;
import apeg.util.Pair;


public class TestCTM {
	
	private Environment<String,ArrayList<NTType>> opTable = OperatorTables.mkArithmeticEnv();
	private CTM ct;
	private List<Pair<String, VType>> error;
	private String errorMessage;
	
	// ResolveUnify method test
	
	@Test
	public void testResolveUnify01 () {
		
		
		error = new ArrayList<Pair<String, VType>>();
		ct = new CTM();
		
		VTyVar a = new VTyVar("a");
		VarConstraint v = new VarConstraint(a, VTyInt.getInstance());
		ct.addConstraint(v);
		
		VTyVar b = new VTyVar("b");
		VarConstraint v1 = new VarConstraint(a, b);
		ct.addConstraint(v1);
		
		VarConstraint v2 = new VarConstraint(b, a);
		ct.addConstraint(v2);
		
		assertEquals(error, ct.resolveUnify(opTable));
		
	}
	
	@Test
	public void testResolveUnify02 () {
		
		error = new ArrayList<Pair<String, VType>>();
		errorMessage = "Error: impossible to solve constraints" ;
		error.add(new Pair<String, VType>(errorMessage, TypeError.getInstance()));
		
		ct = new CTM();
		
		VTyVar a = new VTyVar("a");
		VTyVar b = new VTyVar("b");
		
		VarConstraint v1 = new VarConstraint(a, b);
		ct.addConstraint(v1);
		VarConstraint v2 = new VarConstraint(b, a);
		ct.addConstraint(v2);
		
		assertEquals(error, ct.resolveUnify(opTable));
	}
	
	@Test
	public void testResolveUnify03 () {
		
		error = new ArrayList<Pair<String, VType>>();
		ct = new CTM();
		
		VTyVar a = new VTyVar("a");
		VTyVar b = new VTyVar("b");
		VTyVar c = new VTyVar("c");
		
		VarConstraint v1 = new VarConstraint(a, b);
		ct.addConstraint(v1);
		VarConstraint v2 = new VarConstraint(b, c);
		ct.addConstraint(v2);
		VarConstraint v3 = new VarConstraint(c, VTyInt.getInstance());
		ct.addConstraint(v3);
		
		assertEquals(error, ct.resolveUnify(opTable));
	}

}
