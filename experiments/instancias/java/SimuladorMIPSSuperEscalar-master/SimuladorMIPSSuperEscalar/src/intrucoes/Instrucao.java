package intrucoes;

import dataStructure.DataStructure;
import dataStructure.RegisterStatus;
import dataStructure.ReorderBuffer;
import dataStructure.ReservationStation;

public interface Instrucao {
	public int getOpCode();
	public int getNbyte();
	public String getInstrucao();
	
	//Instrucoes para setar uma intrucao completamente
	public void setNbyte(int i);
	public void setInstrucao(String s);
	public void setDataStructure(DataStructure dataStructure);
	public void setInstrucaoOpCode(String s);
	public String getInstrucaoOpCode();
	
	public void setEspeculativo();
	public void freeEspeculativo();
	public boolean ehEspeculativo();
	public boolean terminouDeExecutar();
	public boolean foiEscrita();
	public boolean isMudou() ;
	public boolean isExecutable(int i);
	public void setMudou(boolean mudou) ;
	
	public int getTime();
	
	public void setTerminou(boolean s);
	
	public boolean issue();
	public boolean execute(int i);
	public boolean write(int i);
	public void commit();
}
