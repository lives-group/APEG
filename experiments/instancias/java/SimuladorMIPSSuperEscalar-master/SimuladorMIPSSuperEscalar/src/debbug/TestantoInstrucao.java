package debbug;
import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Test;

import intrucoes.FactoryDeInstrucao;
import intrucoes.Instrucao;


public class TestantoInstrucao {
	@Test
	public void testandoSeEhPossivelCriarOArrayComOFactory(){
		ArrayList<Instrucao> ins = new ArrayList<Instrucao>();
		
		//Intrucao Add
		ins.add(FactoryDeInstrucao.getInstrucao("00000000001001110100100000100000"));
		ins.get(0).setInstrucao("I7: add R9,R1,R7");
		ins.get(0).setNbyte(0);
		assertEquals(ins.get(0).getOpCode(),0);
		assertEquals(ins.get(0).getNbyte(), 0);		
		
		//Intrucao Addi
		ins.add(FactoryDeInstrucao.getInstrucao("00100000000000010000000000000011"));
		ins.get(0).setInstrucao("ADDI R1,R0,3");
		ins.get(0).setNbyte(4);
		assertEquals(ins.get(1).getOpCode(),8);
		 

	}
	
}
