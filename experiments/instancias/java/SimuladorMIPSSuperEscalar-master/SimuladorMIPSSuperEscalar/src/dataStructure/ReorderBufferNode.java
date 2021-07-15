package dataStructure;

import intrucoes.Instrucao;

public class ReorderBufferNode {
	
	public int ID;
	public boolean busy;
	public String instruction;
	public String state;
	public int destination;
	public int value;
	public Instrucao _instrucao;
	
	public void setInstrucao (Instrucao instrucao){
		_instrucao = instrucao;
	}
	public void commit(){
		_instrucao.commit();
	}
	
}
