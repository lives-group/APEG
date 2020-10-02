package apeg.visitor.semantics;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestVTyString {
	
	private CTM ct;
	
		//Match method test
	
		@Test
		public void testMatchInt () {
			
			VTyString t1 = VTyString.getInstance();
			VTyInt t2 = VTyInt.getInstance();
			
			assertEquals(false, t1.match(t2));
				
		}
		
		@Test
		public void testMatchInt02 () {
			
			VTyString t1 = VTyString.getInstance();
			VTyInt t2 = VTyInt.getInstance();
			
			assertEquals(false, t2.match(t1));
				
		}
		
		@Test
		public void testMatchBool () {
			
			VTyString t1 = VTyString.getInstance();
			VTyBool t2 = VTyBool.getInstance();
			
			assertEquals(false, t1.match(t2));
				
		}
		
		@Test
		public void testMatchBool02 () {
			
			VTyString t1 = VTyString.getInstance();
			VTyBool t2 = VTyBool.getInstance();
			
			assertEquals(false, t2.match(t1));
				
		}
		
		@Test
		public void testMatchChar () {
			
			VTyString t1 = VTyString.getInstance();
			VTyChar t2 = VTyChar.getInstance();
			
			assertEquals(false, t1.match(t2));
				
		}
		
		@Test
		public void testMatchChar02 () {
			
			VTyString t1 = VTyString.getInstance();
			VTyChar t2 = VTyChar.getInstance();
			
			assertEquals(false, t2.match(t1));
				
		}
		
		@Test
		public void testMatchFloat () {
			
			VTyString t1 = VTyString.getInstance();
			VTyFloat t2 = VTyFloat.getInstance();
			
			assertEquals(false, t1.match(t2));
				
		}
		
		@Test
		public void testMatchFloat02 () {
			
			VTyString t1 = VTyString.getInstance();
			VTyFloat t2 = VTyFloat.getInstance();
			
			assertEquals(false, t2.match(t1));
				
		}
		
		@Test
		public void testMatchGrammar () {
			
			VTyString t1 = VTyString.getInstance();
			VTyGrammar t2 = VTyGrammar.getInstance();
			
			assertEquals(false, t1.match(t2));
				
		}
		
		@Test
		public void testMatchGrammar02 () {
			
			VTyString t1 = VTyString.getInstance();
			VTyGrammar t2 = VTyGrammar.getInstance();
			
			assertEquals(false, t2.match(t1));
				
		}
		
		@Test
		public void testMatchString () {
			
			VTyString t1 = VTyString.getInstance();
			VTyString t2 = VTyString.getInstance();
			
			assertEquals(true, t1.match(t2));
				
		}
		
		@Test
		public void testMatchString02 () {
			
			VTyString t1 = VTyString.getInstance();
			VTyString t2 = VTyString.getInstance();
			
			assertEquals(true, t2.match(t1));
				
		}
		
		@Test
		public void testMatchVar () {
			
			VTyString t1 = VTyString.getInstance();
			VTyVar t2 = new VTyVar("a");
			
			assertEquals(true, t1.match(t2));
		}
		
		@Test
		public void testMatchVar02 () {
			
			VTyString t1 = VTyString.getInstance();
			VTyVar t2 = new VTyVar("a");
			
			assertEquals(true, t2.match(t1));
		}
		
		@Test
		public void testMatchError () {
			
			VTyString t1 = VTyString.getInstance();
			TypeError t2 = TypeError.getInstance();
			
			assertEquals(true, t1.match(t2));
		}
		
		@Test
		public void testMatchError02 () {
			
			VTyString t1 = VTyString.getInstance();
			TypeError t2 = TypeError.getInstance();
			
			assertEquals(true, t2.match(t1));
		}
		
		//MatchCT method test
		
		@Test
		public void testMatchCTVar () {
			
			VTyVar t1 = new VTyVar("a");
			VTyString t2 = VTyString.getInstance();
			
			ct = new CTM();
			t2.matchCT(t1, ct);
			
			assertEquals(true, t2.matchCT(t1, ct));
		}
		
		@Test
		public void testMatchCTInt () {
			
			VTyString t1 = VTyString.getInstance();
			VTyInt t2= VTyInt.getInstance();
			ct = new CTM();
			
			assertEquals(false, t2.matchCT(t1, ct));
			
		}
		
		@Test
		public void testMatchCTString () {
			
			VTyString t1 = VTyString.getInstance();
			VTyString t2 = VTyString.getInstance();
			
			assertEquals(true, t2.matchCT(t1, ct));
		}
		
		//Unify method test
		
		@Test
		public void testUnifyString () {
			
			VTyString t1 = VTyString.getInstance();
			VTyString t2 = VTyString.getInstance();
			
			assertEquals(true, t1.Unify(t2));
		}
		
		@Test
		public void testUnifyInt () {
			
			VTyString t1 = VTyString.getInstance();
			VTyInt t2 = VTyInt.getInstance();
			
			assertEquals(false, t1.Unify(t2));
		}
		
		@Test
		public void testUnifyVar () {
			
			VTyString t1 = VTyString.getInstance();
			VTyVar t2 = new VTyVar("a");
			
			assertEquals(true, t1.Unify(t2));
		}
		
		@Test
		public void testUnifyError () {
			
			VTyString t1 = VTyString.getInstance();
			TypeError t2 = TypeError.getInstance();
			
			assertEquals(false, t1.Unify(t2));
		}
		
		
}