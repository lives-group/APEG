package apeg.visitor.semantics;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestVTyInt {
	
	private CTM ct;
		
	//Match method test
		@Test
		public void testMatchInt () {
			
			VTyInt t1 = VTyInt.getInstance();
			VTyInt t2 = VTyInt.getInstance();
			
			assertEquals(true, t1.match(t2));
				
		}
		
		@Test
		public void testMatchInt02 () {
			
			VTyInt t1 = VTyInt.getInstance();
			VTyInt t2 = VTyInt.getInstance();
			
			assertEquals(true, t2.match(t1));
				
		}
		
		@Test
		public void testMatchBool () {
			
			VTyInt t1 = VTyInt.getInstance();
			VTyBool t2 = VTyBool.getInstance();
			
			assertEquals(false, t1.match(t2));
				
		}
		
		@Test
		public void testMatchBool02 () {
			
			VTyInt t1 = VTyInt.getInstance();
			VTyBool t2 = VTyBool.getInstance();
			
			assertEquals(false, t2.match(t1));
				
		}
		
		@Test
		public void testMatchChar () {
			
			VTyInt t1 = VTyInt.getInstance();
			VTyChar t2 = VTyChar.getInstance();
			
			assertEquals(false, t1.match(t2));
				
		}
		
		@Test
		public void testMatchChar02 () {
			
			VTyInt t1 = VTyInt.getInstance();
			VTyChar t2 = VTyChar.getInstance();
			
			assertEquals(false, t2.match(t1));
				
		}
		
		@Test
		public void testMatchFloat () {
			
			VTyInt t1 = VTyInt.getInstance();
			VTyFloat t2 = VTyFloat.getInstance();
			
			assertEquals(false, t1.match(t2));
				
		}
		
		@Test
		public void testMatchFloat02 () {
			
			VTyInt t1 = VTyInt.getInstance();
			VTyFloat t2 = VTyFloat.getInstance();
			
			assertEquals(false, t2.match(t1));
				
		}
		
		@Test
		public void testMatchGrammar () {
			
			VTyInt t1 = VTyInt.getInstance();
			VTyGrammar t2 = VTyGrammar.getInstance();
			
			assertEquals(false, t1.match(t2));
				
		}
		
		@Test
		public void testMatchGrammar02 () {
			
			VTyInt t1 = VTyInt.getInstance();
			VTyGrammar t2 = VTyGrammar.getInstance();
			
			assertEquals(false, t2.match(t1));
				
		}
		
		@Test
		public void testMatchString () {
			
			VTyInt t1 = VTyInt.getInstance();
			VTyString t2 = VTyString.getInstance();
			
			assertEquals(false, t1.match(t2));
				
		}
		
		@Test
		public void testMatchString02 () {
			
			VTyInt t1 = VTyInt.getInstance();
			VTyString t2 = VTyString.getInstance();
			
			assertEquals(false, t2.match(t1));
				
		}
		
		@Test
		public void testMatchVar () {
			
			VTyInt t1 = VTyInt.getInstance();
			VTyVar t2 = new VTyVar("a");
			
			assertEquals(true, t1.match(t2));
		}
		
		@Test
		public void testMatchVar02 () {
			
			VTyInt t1 = VTyInt.getInstance();
			VTyVar t2 = new VTyVar("a");
			
			assertEquals(true, t2.match(t1));
		}
		
		@Test
		public void testMatchError () {
			
			VTyInt t1 = VTyInt.getInstance();
			TypeError t2 = TypeError.getInstance();
			
			assertEquals(true, t1.match(t2));
		}
		
		@Test
		public void testMatchError02 () {
			
			VTyInt t1 = VTyInt.getInstance();
			TypeError t2 = TypeError.getInstance();
			
			assertEquals(true, t2.match(t1));
		}
		
		//MatchCT method test
		
		@Test
		public void testMatchCTVar () {
			
			VTyVar t1 = new VTyVar("a");
			VTyInt t2 = VTyInt.getInstance();
			
			ct = new CTM();
			t2.matchCT(t1, ct);
			
			assertEquals(true, t2.matchCT(t1, ct));
		}
		
		@Test
		public void testMatchCTBool () {
			
			VTyBool t1 = VTyBool.getInstance();
			VTyInt t2= VTyInt.getInstance();
			ct = new CTM();
			
			assertEquals(false, t1.matchCT(t2, ct));
			
		}
		
		@Test
		public void testMatchCTInt () {
			
			VTyInt t1 = VTyInt.getInstance();
			VTyInt t2 = VTyInt.getInstance();
			
			assertEquals(true, t2.matchCT(t1, ct));
		}
		
		//Unify method test
		
		@Test
		public void testUnifyInt () {
			
			VTyInt t1 = VTyInt.getInstance();
			VTyInt t2 = VTyInt.getInstance();
			
			assertEquals(true, t1.Unify(t2));
		}
		
		@Test
		public void testUnifyBool () {
			
			VTyBool t1 = VTyBool.getInstance();
			VTyInt t2 = VTyInt.getInstance();
			
			assertEquals(false, t2.Unify(t1));
		}
		
		@Test
		public void testUnifyVar () {
			
			VTyInt t1 = VTyInt.getInstance();
			VTyVar t2 = new VTyVar("a");
			
			assertEquals(true, t1.Unify(t2));
		}
		
		@Test
		public void testUnifyError () {
			
			VTyInt t1 = VTyInt.getInstance();
			TypeError t2 = TypeError.getInstance();
			
			assertEquals(false, t1.Unify(t2));
		}
		
		
		
}
