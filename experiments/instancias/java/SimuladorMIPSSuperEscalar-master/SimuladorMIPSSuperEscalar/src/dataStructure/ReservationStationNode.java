package dataStructure;

import intrucoes.Instrucao;

public class ReservationStationNode {
	
	Instrucao _instrucao;
	boolean _busy;
	String _op;
	int _Vj,_Vk;
	int _Qj,_Qk;
	int _dest;
	int _A;
	
	public void setDest(int dest){
		_dest=dest;
	}
	
	public void setInstrucao(Instrucao instrucao){
		_instrucao=instrucao;
	}
	
	public void setBusy(boolean busy){
		_busy=busy;
	}
	
	public void setOp(String op){
		_op=op;
	}
	
	public void setVj(int Vj){
		_Vj=Vj;
	}
	
	public void setVk(int Vk){
		_Vk=Vk;
	}
	
	public void setQj(int Qj){
		_Qj=Qj;
	}
	
	public void setQk(int Qk){
		_Qk=Qk;
	}
	
	public void setA(int A){
		_A=A;
	}
	
	public Instrucao getInstrucao(){
		return _instrucao;
	}
	
	public boolean getBusy(){
		return _busy;
	}
	
	public String getOp(){
		return _op;
	}
	
	public int getVj(){
		return _Vj;
	}
	
	public int getDest(){
		return _dest;
	}
	
	public int getVk(){
		return _Vk;
	}
	
	public int getQk(){
		return _Qk;
	}
	
	public int getQj(){
		return _Qj;
	}
	
	public int getA(){
		return _A;
	}
	
	public boolean execute(int i){
		return _instrucao.execute(i);
	}
	
	public boolean write(int i){
		return _instrucao.write(i);
	}
	
}
