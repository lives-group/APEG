package dataStructure;

import java.util.ArrayList;

import intrucoes.Instrucao;

public class FilaDeInstrucao {
	public ArrayList<Instrucao> fila_;
	DataStructure dataStructure_;
	
	public void setDataStructure(DataStructure dataStructure) {
		this.dataStructure_ = dataStructure;
	}
	
	public void setNaFilaDeInstrucao(String arquivo, DataStructure dataStructure ) {
		fila_ = SetDeEntradaDeInstrucoes.getSetIntrucaoDaEntrada(arquivo, dataStructure);
		dataStructure.filaDeIntrucao_ = this;
	}
	
	public void issue(){
		if(dataStructure_.sPointer < fila_.size()){
			//System.out.println(fila_.get(dataStructure_.sPointer).getInstrucao());
			//System.out.println(fila_.get(dataStructure_.sPointer).getOpCode());
			//fila_.get(dataStructure_.sPointer).issue();
			//System.out.println("SP: "+dataStructure_.sPointer);
			//System.out.println("Instrucao para issue: "+fila_.get(dataStructure_.sPointer).getInstrucao());
			//System.out.println(SetDeEntradaDeInstrucoes.getSetIntrucaoDaEntrada(fila_.get(dataStructure_.sPointer),dataStructure_));
			SetDeEntradaDeInstrucoes.getSetIntrucaoDaEntrada(fila_.get(dataStructure_.sPointer),dataStructure_).issue();
			
		}
	}
}
