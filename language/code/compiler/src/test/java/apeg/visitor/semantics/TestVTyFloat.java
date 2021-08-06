package apeg.visitor.semantics;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestVTyFloat {
	
	private CTM ct;
		
	//Match method test
		@Test
		public void testMatchInt () {
			
			VTyFloat t1 = VTyFloat.getInstance();
			VTyInt t2 = VTyInt.getInstance();
			
			assertEquals(false, t1.match(t2));
				
		}
		
		@Test
		public void testMatchInt02 () {
			
			VTyFloat t1 = VTyFloat.getInstance();
			VTyInt t2 = VTyInt.getInstance();
			
			assertEquals(false, t2.match(t1));
				
		}
		
		@Test
		public void testMatchBool () {
			
			VTyFloat t1 = VTyFloat.getInstance();
			VTyBool t2 = VTyBool.getInstance();
			
			assertEquals(false, t1.match(t2));
				
		}
		
		@Test
		public void testMatchBool02 () {
			
			VTyFloat t1 = VTyFloat.getInstance();
			VTyBool t2 = VTyBool.getInstance();
			
			assertEquals(false, t2.match(t1));
				
		}
		
		@Test
		public void testMatchChar () {
			
			VTyFloat t1 = VTyFloat.getInstance();
			VTyChar t2 = VTyChar.getInstance();
			
			assertEquals(false, t1.match(t2));
				
		}
		
		@Test
		public void testMatchChar02 () {
			
			VTyFloat t1 = VTyFloat.getInstance();
			VTyChar t2 = VTyChar.getInstance();
			
			assertEquals(false, t2.match(t1));
				
		}
		
		@Test
		public void testMatchFloat () {
			
			VTyFloat t1 = VTyFloat.getInstance();
			VTyFloat t2 = VTyFloat.getInstance();
			
			assertEquals(true, t1.match(t2));
				
		}
		
		@Test
		public void testMatchFloat02 () {
			
			VTyFloat t1 = VTyFloat.getInstance();
			VTyFloat t2 = VTyFloat.getInstance();
			
			assertEquals(true, t2.match(t1));
				
		}
		
		@Test
		public void testMatchGrammar () {
			
			VTyFloat t1 = VTyFloat.getInstance();
			VTyGrammar t2 = VTyGrammar.getInstance();
			
			assertEquals(false, t1.match(t2));
				
		}
		
		@Test
		public void testMatchGrammar02 () {
			
			VTyFloat t1 = VTyFloat.getInstance();
			VTyGrammar t2 = VTyGrammar.getInstance();
			
			assertEquals(false, t2.match(t1));
				
		}
		
		@Test
		public void testMatchString () {
			
			VTyFloat t1 = VTyFloat.getInstance();
			VTyString t2 = VTyString.getInstance();
			
			assertEquals(false, t1.match(t2));
				
		}
		
		@Test
		public void testMatchString02 () {
			
			VTyFloat t1 = VTyFloat.getInstance();
			VTyString t2 = VTyString.getInstance();
			
			assertEquals(false, t2.match(t1));
				
		}
		
		@Test
		public void testMatchVar () {
			
			VTyFloat t1 = VTyFloat.getInstance();
			VTyVar t2 = new VTyVar("a");
			
			assertEquals(true, t1.match(t2));
		}
		
		@Test
		public void testMatchVar02 () {
			
			VTyFloat t1 = VTyFloat.getInstance();
			VTyVar t2 = new VTyVar("a");
			
			assertEquals(true, t2.match(t1));
		}
		
		@Test
		public void testMatchError () {
			
			VTyFloat t1 = VTyFloat.getInstance();
			TypeError t2 = TypeError.getInstance();
			
			assertEquals(true, t1.match(t2));
		}
		
		@Test
		public void testMatchError02 () {
			
			VTyFloat t1 = VTyFloat.getInstance();
			TypeError t2 = TypeError.getInstance();
			
			assertEquals(true, t2.match(t1));
		}
		
		//MatchCT method test
		
		@Test
		public void testMatchCTVar () {
			
			VTyVar t1 = new VTyVar("a");
			VTyFloat t2 = VTyFloat.getInstance();
			
			ct = new CTM();
			t2.matchCT(t1, ct);
			
			assertEquals(true, t2.matchCT(t1, ct));
		}
		
		@Test
		public void testMatchCTInt () {
			
			VTyFloat t1 = VTyFloat.getInstance();
			VTyInt t2= VTyInt.getInstance();
			ct = new CTM();
			
			assertEquals(false, t2.matchCT(t1, ct));
			
		}
		
		@Test
		public void testMatchCTFloat () {
			
			VTyFloat t1 = VTyFloat.getInstance();
			VTyFloat t2 = VTyFloat.getInstance();
			
			assertEquals(true, t2.matchCT(t1, ct));
		}
		
		//Unify method test
		
		@Test
		public void testUnifyFloat () {
			
			VTyFloat t1 = VTyFloat.getInstance();
			VTyFloat t2 = VTyFloat.getInstance();
			
			assertEquals(true, t1.Unify(t2));
		}
		
		@Test
		public void testUnifyInt () {
			
			VTyFloat t1 = VTyFloat.getInstance();
			VTyInt t2 = VTyInt.getInstance();
			
			assertEquals(false, t1.Unify(t2));
		}
		
		@Test
		public void testUnifyVar () {
			
			VTyFloat t1 = VTyFloat.getInstance();
			VTyVar t2 = new VTyVar("a");
			
			assertEquals(true, t1.Unify(t2));
		}
		
		@Test
		public void testUnifyError () {
			
			VTyFloat t1 = VTyFloat.getInstance();
			TypeError t2 = TypeError.getInstance();
			
			assertEquals(false, t1.Unify(t2));
		}
		
		
}
