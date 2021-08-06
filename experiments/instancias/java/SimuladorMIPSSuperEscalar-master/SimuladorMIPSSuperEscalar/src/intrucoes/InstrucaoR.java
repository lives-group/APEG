package intrucoes;

import dataStructure.DataStructure;
import dataStructure.RegisterStatus;
import dataStructure.ReorderBuffer;
import dataStructure.ReorderBufferNode;
import dataStructure.ReservationStation;
import dataStructure.ReservationStationNode;

abstract public class InstrucaoR  implements Instrucao{
	int nByte_;
	int opCode_ = 0;
	int rs_;
	int rt_;
	int rd_;
	int funct_;
	boolean ehEspeculativo_ = false;
	String instrucao_;
	
	DataStructure dataStructure_;
	boolean iniciou = false;
	boolean terminou = false;
	boolean escrita = true;
	
	public boolean mudou = false;
	public boolean isMudou() {
		return mudou;
	}

	public void setMudou(boolean mudou) {
		this.mudou = mudou;
	}
	
	public InstrucaoR(int rs, int rt, int rd) {
		rs_ = rs;
		rt_ = rt;
		rd_ = rd;
	}
	@Override
	public int getNbyte() {
		return nByte_;
	}
	@Override
	public void setNbyte(int i) {
		nByte_ = i;
	}
	@Override
	public int getOpCode() {
		return opCode_;
	}
	@Override
	public boolean ehEspeculativo() {
		return ehEspeculativo_;
	}
	@Override
	public void setEspeculativo() {
		ehEspeculativo_ = true;
	}
	@Override
	public void freeEspeculativo() {
		ehEspeculativo_ = false;
	}
	@Override
	public String getInstrucao() {
		return instrucao_;
	}
	@Override
	public void setDataStructure(DataStructure dataStructure) {
		this.dataStructure_ = dataStructure;
		
	}
	@Override
	public void setInstrucao(String s) {
		instrucao_ = s;
	}
	int time;
	@Override
	public int getTime() {
		
		return time;
	}
	@Override
	public boolean terminouDeExecutar() {
		return terminou;
	}
	@Override
	public void setTerminou(boolean s){
		terminou = s;
	}
	@Override
	public boolean foiEscrita() {
		
		return escrita;
	}
	
	
	String InstrucaoOpCode;
	@Override
	public void setInstrucaoOpCode(String s) {
		InstrucaoOpCode = s;		
	}
	@Override
	public String getInstrucaoOpCode() {
		return InstrucaoOpCode;
	}
}
