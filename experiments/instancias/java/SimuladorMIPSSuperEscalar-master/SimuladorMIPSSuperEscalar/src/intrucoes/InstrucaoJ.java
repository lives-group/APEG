package intrucoes;

import dataStructure.DataStructure;
import dataStructure.RegisterStatus;
import dataStructure.ReorderBuffer;
import dataStructure.ReservationStation;

abstract public class InstrucaoJ implements Instrucao{
	int nByte_;
	int opCode_ = 2;
	int target_;
	boolean ehEspeculativo_ = false;
	String instrucao_;
	public boolean mudou = false;
	public boolean terminou = false;
	public boolean escrita = false;
	public boolean isMudou() {
		return mudou;
	}

	public void setMudou(boolean mudou) {
		this.mudou = mudou;
	}
	DataStructure dataStructure_;
	
	
	public InstrucaoJ(int target) {
		target_ = target;
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
		// TODO Auto-generated method stub
		
	}
	int time;
	@Override
	public int getTime() {
		
		return time;
	}
	@Override
	public boolean terminouDeExecutar() {
		return false;
	}
	@Override
	public boolean foiEscrita() {
		
		return escrita;
	}
	@Override
	public void setTerminou(boolean s){
		terminou = s;
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
