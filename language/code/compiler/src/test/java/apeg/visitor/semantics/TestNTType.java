package apeg.visitor.semantics;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestNTType {
	
	
	
	@Test
	public void testParams() {
		
		VType p[] = new VType[2];
		VType r[] = new VType[2];
		
		NTType t = new NTType(p, r);
		
		assertEquals(4, t.getNumParams());
	}
	
	@Test
	public void testNumS (){
		
		VType p[] = new VType[3];
		VType r[] = new VType[2];
		
		NTType t = new NTType(p, r);
		
		assertEquals(2, t.getNumSintetized());
	
	}
	
	@Test
	public void testGetI () {
		
		VType p[] = new VType[4];
		VType r[] = new VType[2];
		
		NTType t = new NTType(p, r);
		
		assertEquals(4, t.getNumInherited());
	}
	
	@Test
	public void testGetParam() {
		
		VType p[] = new VType[2];
		VType r[] = new VType[1];
		
		p[0] = VTyInt.getInstance();
		p[1] = VTyGrammar.getInstance();
		r[0] = VTyGrammar.getInstance();
		
		NTType t = new NTType(p, r);
		
		assertEquals(VTyGrammar.getInstance(),t.getParamAt(1));
		
	}
	
	//Match method test
	
	@Test
	public void testMatchTrue () {
		
		VType p2[] = new VType[2];
		VType r2[] = new VType[2];
		
		p2[0] = VTyInt.getInstance();
		p2[1] = VTyFloat.getInstance();
		r2[0] = VTyBool.getInstance();
		r2[1] = TypeError.getInstance();
		
		NTType t2 = new NTType(p2, r2);
		NTType t3 = new NTType(p2, r2);
		
		
		assertEquals(true, t2.match(t3));
	


	}

	@Test
	public void testMatchFalse () {
		
		VType p1[] = new VType[1];
		VType p2[] = new VType[1];
		VType r1[] = new VType[0];
				
		
		p1[0] = VTyInt.getInstance();
		p2[0] = VTyBool.getInstance();
		
		NTType t1 = new NTType(p1, r1);
		NTType t2 = new NTType(p2,r1);
		
		assertEquals(false, t1.match(t2));
	
		
	}
	
	@Test
	public void testMatchVar () {
		
		VType p1[] = new VType[1];
		VType p2[] = new VType[1];
		VType r1[] = new VType[0];
				
		
		p1[0] = VTyInt.getInstance();
		p2[0] = new VTyVar("a");
		
		NTType t1 = new NTType(p1, r1);
		NTType t2 = new NTType(p2,r1);
		
		assertEquals(true, t1.match(t2));

		
	}
	
	
	@Test
	public void testMatchError () {
		
		VType p1[] = new VType[1];
		VType p2[] = new VType[1];
		VType r1[] = new VType[0];
				
		
		p1[0] = VTyInt.getInstance();
		p2[0] = TypeError.getInstance();
		
		NTType t1 = new NTType(p1, r1);
		NTType t2 = new NTType(p2,r1);
		
		assertEquals(true, t1.match(t2));
	
		
	}
	
	//Unify method test
	
	
	@Test
	public void testUnifyFalse () {
		
		VType p1[] = new VType[1];
		VType p2[] = new VType[2];
		VType r1[] = new VType[0];
				
		
		p1[0] = VTyInt.getInstance();
		p2[0] = TypeError.getInstance();
		p2[1] = VTyBool.getInstance();
		
		NTType t1 = new NTType(p1, r1);
		NTType t2 = new NTType(p2,r1);
		
		assertEquals(false, t1.match(t2));
	
		
	}
	

	@Test
	public void testUnifyFalse02 () {
		
		VType p1[] = new VType[1];
		VType p2[] = new VType[1];
		VType r1[] = new VType[0];
				
		
		p1[0] = VTyInt.getInstance();
		p2[0] = VTyBool.getInstance();
		
		NTType t1 = new NTType(p1, r1);
		NTType t2 = new NTType(p2,r1);
		
		assertEquals(false, t1.match(t2));

		
	}
	

	@Test
	public void testUnifyTrue () {
		
		VType p1[] = new VType[1];
		VType p2[] = new VType[1];
		VType r1[] = new VType[0];
				
		
		p1[0] = VTyInt.getInstance();
		p2[0] = VTyInt.getInstance();
		
		NTType t1 = new NTType(p1, r1);
		NTType t2 = new NTType(p2,r1);
		
		assertEquals(true, t1.match(t2));
	
		
	}
	

	@Test
	public void testUnifyVar () {
		
		VType p1[] = new VType[1];
		VType p2[] = new VType[1];
		VType r1[] = new VType[0];
				
		
		p1[0] = VTyInt.getInstance();
		p2[0] = new VTyVar("a");
		
		NTType t1 = new NTType(p1, r1);
		NTType t2 = new NTType(p2,r1);
		
		assertEquals(true, t1.match(t2));
	
		
	}
	

	@Test
	public void testUnifyError () {
		
		VType p1[] = new VType[1];
		VType p2[] = new VType[1];
		VType r1[] = new VType[0];
				
		
		p1[0] = VTyInt.getInstance();
		p2[0] = TypeError.getInstance();
		
		NTType t1 = new NTType(p1, r1);
		NTType t2 = new NTType(p2,r1);
		
		assertEquals(true, t1.match(t2));
	
		
	}
	
	
}
