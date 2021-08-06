package apeg.visitor.semantics;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestVTyBool {

	private CTM ct;

	//Match method test
	@Test
	public void testMatchInt () {

		VTyBool t1 = VTyBool.getInstance();
		VTyInt t2 = VTyInt.getInstance();

		assertEquals(false, t1.match(t2));

	}
	@Test
	public void testMatchInt02 () {

		VTyBool t1 = VTyBool.getInstance();
		VTyInt t2 = VTyInt.getInstance();

		assertEquals(false, t2.match(t1));

	}

	@Test
	public void testMatchBool () {

		VTyBool t1 = VTyBool.getInstance();
		VTyBool t2 = VTyBool.getInstance();

		assertEquals(true, t1.match(t2));

	}
	
	@Test public void testMatchBool02 () {
		
		VTyBool t1 = VTyBool.getInstance();
		VTyBool t2 = VTyBool.getInstance();
		
		assertEquals(true, t2.match(t2));
	}

	@Test
	public void testMatchChar () {

		VTyBool t1 = VTyBool.getInstance();
		VTyChar t2 = VTyChar.getInstance();

		assertEquals(false, t1.match(t2));

	}

	@Test
	public void testMatchChar02 () {

		VTyBool t1 = VTyBool.getInstance();
		VTyChar t2 = VTyChar.getInstance();

		assertEquals(false, t2.match(t1));

	}

	@Test
	public void testMatchFloat () {

		VTyBool t1 = VTyBool.getInstance();
		VTyFloat t2 = VTyFloat.getInstance();

		assertEquals(false, t1.match(t2));

	}

	@Test
	public void testMatchFloat02 () {

		VTyBool t1 = VTyBool.getInstance();
		VTyFloat t2 = VTyFloat.getInstance();

		assertEquals(false, t2.match(t1));

	}

	@Test
	public void testMatchGrammar () {

		VTyBool t1 = VTyBool.getInstance();
		VTyGrammar t2 = VTyGrammar.getInstance();

		assertEquals(false, t1.match(t2));

	}

	@Test
	public void testMatchGrammar02 () {

		VTyBool t1 = VTyBool.getInstance();
		VTyGrammar t2 = VTyGrammar.getInstance();

		assertEquals(false, t2.match(t1));

	}

	@Test
	public void testMatchString () {

		VTyBool t1 = VTyBool.getInstance();
		VTyString t2 = VTyString.getInstance();

		assertEquals(false, t1.match(t2));

	}

	@Test
	public void testMatchString02 () {

		VTyBool t1 = VTyBool.getInstance();
		VTyString t2 = VTyString.getInstance();

		assertEquals(false, t2.match(t1));

	}

	@Test
	public void testMatchVar () {

		VTyBool t1 = VTyBool.getInstance();
		VTyVar t2 = new VTyVar("a");

		assertEquals(true, t1.match(t2));
	}

	@Test
	public void testMatchVar02 () {

		VTyBool t1 = VTyBool.getInstance();
		VTyVar t2 = new VTyVar("a");

		assertEquals(true, t2.match(t1));
	}

	@Test
	public void testMatchError () {

		VTyBool t1 = VTyBool.getInstance();
		TypeError t2 = TypeError.getInstance();

		assertEquals(true, t1.match(t2));
	}

	@Test
	public void testMatchError02 () {

		VTyBool t1 = VTyBool.getInstance();
		TypeError t2 = TypeError.getInstance();

		assertEquals(true, t2.match(t1));
	}
	

	//MatchCT method test

	@Test
	public void testMatchCTVar () {

		VTyVar t1 = new VTyVar("a");
		VTyBool t2 = VTyBool.getInstance();

		ct = new CTM();
		t2.matchCT(t1, ct);

		//assertEquals("a=boolean", ct.toString());
	}

	@Test
	public void testMatchCTInt () {

		VTyBool t1 = VTyBool.getInstance();
		VTyInt t2= VTyInt.getInstance();
		ct = new CTM();

		assertEquals(false, t2.matchCT(t1, ct));

	}

	@Test
	public void testMatchCTBool () {

		VTyBool t1 = VTyBool.getInstance();
		VTyBool t2 = VTyBool.getInstance();

		assertEquals(true, t2.matchCT(t1, ct));
	}

	//Unify method test

	@Test
	public void testUnifyBool () {

		VTyBool t1 = VTyBool.getInstance();
		VTyBool t2 = VTyBool.getInstance();

		assertEquals(true, t1.Unify(t2));
	}

	@Test
	public void testUnifyInt () {

		VTyBool t1 = VTyBool.getInstance();
		VTyInt t2 = VTyInt.getInstance();

		assertEquals(false, t1.Unify(t2));
	}

	@Test
	public void testUnifyVar () {

		VTyBool t1 = VTyBool.getInstance();
		VTyVar t2 = new VTyVar("a");

		assertEquals(true, t1.Unify(t2));
	}

	@Test
	public void testUnifyError () {

		VTyBool t1 = VTyBool.getInstance();
		TypeError t2 = TypeError.getInstance();

		assertEquals(false, t1.Unify(t2));
	}


}
