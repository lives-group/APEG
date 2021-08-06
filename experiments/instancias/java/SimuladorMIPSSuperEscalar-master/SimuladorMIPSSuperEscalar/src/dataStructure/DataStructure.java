package dataStructure;

import intrucoes.Instrucao;

public class DataStructure {
	public int sPointer = 0;
	public int pred = 0;
	public double predType;
	public FilaDeInstrucao filaDeIntrucao_;
	Memory memory_;
	Registers registers_;
	RegisterStatus registerStatus_;
	ReorderBuffer reorderBuffer_;
	ReservationStation reservationStation;
	
	public ReservationStationNode addexecute = null, loadexecute = null, multexecute = null, issued = null; 
	public ReorderBufferNode write; 

	public DataStructure(FilaDeInstrucao fdi,
						Memory mem,
						Registers regs,
						RegisterStatus regsStat,
						ReorderBuffer reoBuff,
						ReservationStation resStion) {
		filaDeIntrucao_ = fdi;
		memory_ = mem;
		registers_ = regs;
		registerStatus_ = regsStat;
		reorderBuffer_ = reoBuff;
		reservationStation = resStion;
		
		filaDeIntrucao_.setDataStructure(this);
		reorderBuffer_.setDataStructure(this);
		reservationStation.setDataStructure(this);		
	}
	public FilaDeInstrucao getFilaDeIntrucao_() {
		return filaDeIntrucao_;
	}
	public Memory getMemory_() {
		return memory_;
	}
	public Registers getRegisters_() {
		return registers_;
	}
	public RegisterStatus getRegisterStatus_() {
		return registerStatus_;
	}
	public ReorderBuffer getReorderBuffer_() {
		return reorderBuffer_;
	}
	public ReservationStation getReservationStation() {
		return reservationStation;
	}
	/*public void setFilaDeIntrucao_(FilaDeInstrucao filaDeIntrucao_) {
		this.filaDeIntrucao_ = filaDeIntrucao_;
	}*/
}
