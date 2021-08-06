package apeg.visitor.semantics;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestVTyGrammar {
	
	private CTM ct;
		
		//Match method test
	
		@Test
		public void testMatchInt () {
			
			VTyGrammar t1 = VTyGrammar.getInstance();
			VTyInt t2 = VTyInt.getInstance();
			
			assertEquals(false, t1.match(t2));
				
		}
		
		@Test
		public void testMatchInt02 () {
			
			VTyGrammar t1 = VTyGrammar.getInstance();
			VTyInt t2 = VTyInt.getInstance();
			
			assertEquals(false, t2.match(t1));
				
		}
		
		@Test
		public void testMatchBool () {
			
			VTyGrammar t1 = VTyGrammar.getInstance();
			VTyBool t2 = VTyBool.getInstance();
			
			assertEquals(false, t1.match(t2));
				
		}
		
		@Test
		public void testMatchBool02 () {
			
			VTyGrammar t1 = VTyGrammar.getInstance();
			VTyBool t2 = VTyBool.getInstance();
			
			assertEquals(false, t2.match(t1));
				
		}
		
		@Test
		public void testMatchChar () {
			
			VTyGrammar t1 = VTyGrammar.getInstance();
			VTyChar t2 = VTyChar.getInstance();
			
			assertEquals(false, t1.match(t2));
				
		}
		
		@Test
		public void testMatchChar02 () {
			
			VTyGrammar t1 = VTyGrammar.getInstance();
			VTyChar t2 = VTyChar.getInstance();
			
			assertEquals(false, t2.match(t1));
				
		}
		
		@Test
		public void testMatchFloat () {
			
			VTyGrammar t1 = VTyGrammar.getInstance();
			VTyFloat t2 = VTyFloat.getInstance();
			
			assertEquals(false, t1.match(t2));
				
		}
		
		@Test
		public void testMatchFloat02 () {
			
			VTyGrammar t1 = VTyGrammar.getInstance();
			VTyFloat t2 = VTyFloat.getInstance();
			
			assertEquals(false, t2.match(t1));
				
		}
		
		@Test
		public void testMatchGrammar () {
			
			VTyGrammar t1 = VTyGrammar.getInstance();
			VTyGrammar t2 = VTyGrammar.getInstance();
			
			assertEquals(true, t1.match(t2));
				
		}
		
		@Test
		public void testMatchGrammar02 () {
			
			VTyGrammar t1 = VTyGrammar.getInstance();
			VTyGrammar t2 = VTyGrammar.getInstance();
			
			assertEquals(true, t2.match(t1));
				
		}
		
		@Test
		public void testMatchString () {
			
			VTyGrammar t1 = VTyGrammar.getInstance();
			VTyString t2 = VTyString.getInstance();
			
			assertEquals(false, t1.match(t2));
				
		}
		
		@Test
		public void testMatchString02 () {
			
			VTyGrammar t1 = VTyGrammar.getInstance();
			VTyString t2 = VTyString.getInstance();
			
			assertEquals(false, t2.match(t1));
				
		}
		
		@Test
		public void testMatchVar () {
			
			VTyGrammar t1 = VTyGrammar.getInstance();
			VTyVar t2 = new VTyVar("a");
			
			assertEquals(true, t1.match(t2));
		}
		
		@Test
		public void testMatchVar02 () {
			
			VTyGrammar t1 = VTyGrammar.getInstance();
			VTyVar t2 = new VTyVar("a");
			
			assertEquals(true, t2.match(t1));
		}
		
		@Test
		public void testMatchError () {
			
			VTyGrammar t1 = VTyGrammar.getInstance();
			TypeError t2 = TypeError.getInstance();
			
			assertEquals(true, t1.match(t2));
		}
		
		@Test
		public void testMatchError02 () {
			
			VTyGrammar t1 = VTyGrammar.getInstance();
			TypeError t2 = TypeError.getInstance();
			
			assertEquals(true, t2.match(t1));
		}
		
		//MatchCT method test
		
		@Test
		public void testMatchCTVar () {
			
			VTyVar t1 = new VTyVar("a");
			VTyGrammar t2 = VTyGrammar.getInstance();
			
			ct = new CTM();
			t2.matchCT(t1, ct);
			
			assertEquals(true, t2.matchCT(t1, ct));
		}
		
		@Test
		public void testMatchCTInt () {
			
			VTyGrammar t1 = VTyGrammar.getInstance();
			VTyInt t2= VTyInt.getInstance();
			ct = new CTM();
			
			assertEquals(false, t2.matchCT(t1, ct));
			
		}
		
		@Test
		public void testMatchCTGrammar () {
			
			VTyGrammar t1 = VTyGrammar.getInstance();
			VTyGrammar t2 = VTyGrammar.getInstance();
			
			assertEquals(true, t2.matchCT(t1, ct));
		}
		
		//Unify method test
		
		@Test
		public void testUnifyGrammar () {
			
			VTyGrammar t1 = VTyGrammar.getInstance();
			VTyGrammar t2 = VTyGrammar.getInstance();
			
			assertEquals(true, t1.Unify(t2));
		}
		
		@Test
		public void testUnifyInt () {
			
			VTyGrammar t1 = VTyGrammar.getInstance();
			VTyInt t2 = VTyInt.getInstance();
			
			assertEquals(false, t1.Unify(t2));
		}
		
		@Test
		public void testUnifyVar () {
			
			VTyGrammar t1 = VTyGrammar.getInstance();
			VTyVar t2 = new VTyVar("a");
			
			assertEquals(true, t1.Unify(t2));
		}
		
		@Test
		public void testUnifyError () {
			
			VTyGrammar t1 = VTyGrammar.getInstance();
			TypeError t2 = TypeError.getInstance();
			
			assertEquals(false, t1.Unify(t2));
		}
		
		
}