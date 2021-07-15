package intrucoes;

import dataStructure.ReorderBufferNode;
import dataStructure.ReservationStationNode;

public class InstrucaoJOpJmp extends InstrucaoJ implements Instrucao{

	public InstrucaoJOpJmp(int t) {
		super(t);
	}
	
	@Override
	public boolean issue() {
		if(dataStructure_.getReservationStation().isFullAdd() || 
				dataStructure_.getReorderBuffer_().isFull()) return false;
		
		ReservationStationNode rsNode = new ReservationStationNode();
		ReorderBufferNode robNode = new ReorderBufferNode();
		
		rsNode.setVj(0);
		rsNode.setQj(0);
		
		rsNode.setBusy(true);
		rsNode.setInstrucao(this);
		rsNode.setOp(instrucao_);
		
		int b;
		b = dataStructure_.getReorderBuffer_().getROBList().size();
		rsNode.setDest(b);
		
		robNode._instrucao = this;
		robNode.instruction = instrucao_;
		robNode.destination = 0;
		robNode.busy = true;
		
		if(dataStructure_.getReorderBuffer_().getROBList().size() == 0)
			robNode.ID = 0;
		else
			robNode.ID = dataStructure_.getReorderBuffer_().getROBList().get(dataStructure_.getReorderBuffer_().getROBList().size()-1).ID+1;
		rsNode.setDest(robNode.ID);
		rsNode.setVk(0);
		rsNode.setQk(0);
		
		//dataStructure_.getRegisterStatus_().getReorder().set(rd_, b);
		//dataStructure_.getRegisterStatus_().getBusy().set(rd_, true);
		
		robNode.state = "Issue";
		
		dataStructure_.getReorderBuffer_().getROBList().add(robNode);
		dataStructure_.getReservationStation().getAddList().add(rsNode);
		dataStructure_.sPointer++;
		mudou = true;
		return true;
	}
	@Override
	public boolean isExecutable(int i) {
		return dataStructure_.getReservationStation().getAddList().get(i).getQj() == 0 &&
				dataStructure_.getReservationStation().getAddList().get(i).getQk() == 0;
	}
	@Override
	public void commit() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean write(int i) {
		// TODO Auto-generated method stub
		return false;
	}

	

	@Override
	public boolean execute(int i) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
