package apeg.visitor.semantics;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestVTyChar {
	
	private CTM ct;
	
	//Match method test
		@Test
		public void testMatchInt () {
			
			VTyChar t1 = VTyChar.getInstance();
			VTyInt t2 = VTyInt.getInstance();
			
			assertEquals(false, t1.match(t2));
				
		}
		
		@Test
		public void testMatchInt01 () {
			
			VTyChar t1 = VTyChar.getInstance();
			VTyInt t2 = VTyInt.getInstance();
			
			assertEquals(false, t2.match(t1));
				
		}
		
		@Test
		public void testMatchBool () {
			
			VTyChar t1 = VTyChar.getInstance();
			VTyBool t2 = VTyBool.getInstance();
			
			assertEquals(false, t1.match(t2));
				
		}
		
		@Test
		public void testMatchBool02 () {
			
			VTyChar t1 = VTyChar.getInstance();
			VTyBool t2 = VTyBool.getInstance();
			
			assertEquals(false, t2.match(t1));
				
		}
		
		@Test
		public void testMatchChar () {
			
			VTyChar t1 = VTyChar.getInstance();
			VTyChar t2 = VTyChar.getInstance();
			
			assertEquals(true, t1.match(t2));
				
		}
		
		@Test
		public void testMatchChar02 () {
			
			VTyChar t1 = VTyChar.getInstance();
			VTyChar t2 = VTyChar.getInstance();
			
			assertEquals(true, t2.match(t1));
				
		}
		
		@Test
		public void testMatchFloat () {
			
			VTyChar t1 = VTyChar.getInstance();
			VTyFloat t2 = VTyFloat.getInstance();
			
			assertEquals(false, t1.match(t2));
				
		}
		
		@Test
		public void testMatchFloat02 () {
			
			VTyChar t1 = VTyChar.getInstance();
			VTyFloat t2 = VTyFloat.getInstance();
			
			assertEquals(false, t2.match(t1));
				
		}
		
		@Test
		public void testMatchGrammar () {
			
			VTyChar t1 = VTyChar.getInstance();
			VTyGrammar t2 = VTyGrammar.getInstance();
			
			assertEquals(false, t1.match(t2));
				
		}
		
		@Test
		public void testMatchGrammar02 () {
			
			VTyChar t1 = VTyChar.getInstance();
			VTyGrammar t2 = VTyGrammar.getInstance();
			
			assertEquals(false, t2.match(t1));
				
		}
		
		@Test
		public void testMatchString () {
			
			VTyChar t1 = VTyChar.getInstance();
			VTyString t2 = VTyString.getInstance();
			
			assertEquals(false, t1.match(t2));
				
		}
		
		@Test
		public void testMatchString02 () {
			
			VTyChar t1 = VTyChar.getInstance();
			VTyString t2 = VTyString.getInstance();
			
			assertEquals(false, t2.match(t1));
				
		}
		
		@Test
		public void testMatchVar () {
			
			VTyChar t1 = VTyChar.getInstance();
			VTyVar t2 = new VTyVar("a");
			
			assertEquals(true, t1.match(t2));
		}
		
		@Test
		public void testMatchVar02 () {
			
			VTyChar t1 = VTyChar.getInstance();
			VTyVar t2 = new VTyVar("a");
			
			assertEquals(true, t2.match(t1));
		}
		
		@Test
		public void testMatchError () {
			
			VTyChar t1 = VTyChar.getInstance();
			TypeError t2 = TypeError.getInstance();
			
			assertEquals(true, t1.match(t2));
		}
		
		@Test
		public void testMatchError02 () {
			
			VTyChar t1 = VTyChar.getInstance();
			TypeError t2 = TypeError.getInstance();
			
			assertEquals(true, t2.match(t1));
		}
		
		// MatchCT method test
		
		@Test
		public void testMatchCTVar () {
			
			VTyVar t1 = new VTyVar("a");
			VTyChar t2 = VTyChar.getInstance();
			
			ct = new CTM();
			t2.matchCT(t1, ct);
			
			assertEquals(ct.toString(), "a=char\n");
		}
		
		@Test
		public void testMatchCTInt () {
			
			VTyChar t1 = VTyChar.getInstance();
			VTyInt t2= VTyInt.getInstance();
			ct = new CTM();
			
			assertEquals(false, t2.matchCT(t1, ct));
			
		}
		
		@Test
		public void testMatchCTChar () {
			
			VTyChar t1 = VTyChar.getInstance();
			VTyChar t2 = VTyChar.getInstance();
			
			assertEquals(true, t2.matchCT(t1, ct));
		}
		
		//Unify method test
		
		@Test
		public void testUnifyChar () {
			
			VTyChar t1 = VTyChar.getInstance();
			VTyChar t2 = VTyChar.getInstance();
			
			assertEquals(true, t1.Unify(t2));
		}
		
		@Test
		public void testUnifyInt () {
			
			VTyChar t1 = VTyChar.getInstance();
			VTyInt t2 = VTyInt.getInstance();
			
			assertEquals(false, t1.Unify(t2));
		}
		
		@Test
		public void testUnifyVar () {
			
			VTyChar t1 = VTyChar.getInstance();
			VTyVar t2 = new VTyVar("a");
			
			assertEquals(true, t1.Unify(t2));
		}
		
		@Test
		public void testUnifyError () {
			
			VTyChar t1 = VTyChar.getInstance();
			TypeError t2 = TypeError.getInstance();
			
			assertEquals(false, t1.Unify(t2));
		}
		
		
		
}
