package intrucoes;

import dataStructure.ReorderBufferNode;
import dataStructure.ReservationStationNode;

public class InstrucaoROpMul extends InstrucaoR implements Instrucao{
	int time = 3;
	public InstrucaoROpMul(int r1, int r2, int r3) {
		super(r1, r2, r3);
		funct_ = 34;		
	}
	@Override
	public boolean issue() {
		dataStructure_.issued = null;
		if(dataStructure_.getReservationStation().isFullMult() || 
				dataStructure_.getReorderBuffer_().isFull()) return false;
		
		ReservationStationNode rsNode = new ReservationStationNode();
		ReorderBufferNode robNode = new ReorderBufferNode();
		
		if(dataStructure_.getRegisterStatus_().isBusy(rs_)){
			int h = dataStructure_.getRegisterStatus_().getReorder(rs_);
			if(!dataStructure_.getReorderBuffer_().getNodeID(h).busy){
				rsNode.setVj(dataStructure_.getReorderBuffer_().getValue(h));
				rsNode.setQj(0);
			}else{
				rsNode.setQj(h);
			}
		}else{
			rsNode.setVj(dataStructure_.getRegisters_().getReg(rs_));
			rsNode.setQj(0);
		}
		
		rsNode.setBusy(true);
		rsNode.setInstrucao(this);
		rsNode.setOp(instrucao_);
		
		int b;
		b = dataStructure_.getReorderBuffer_().getROBList().size();
		rsNode.setDest(b);
		
		robNode._instrucao = this;
		robNode.instruction = instrucao_;
		robNode.destination = rd_;
		robNode.busy = true;
		
		if(dataStructure_.getReorderBuffer_().getROBList().size() == 0)
			robNode.ID = 1;
		else
			robNode.ID = dataStructure_.getReorderBuffer_().getROBList().get(dataStructure_.getReorderBuffer_().getROBList().size()-1).ID+1;
		System.out.println("RobNode ID: " + robNode.ID);
		rsNode.setDest(robNode.ID);
		if(dataStructure_.getRegisterStatus_().isBusy(rt_)){
			int h = dataStructure_.getRegisterStatus_().getReorder(rt_);
			System.out.println("Rob ID: " + h);
			if(!dataStructure_.getReorderBuffer_().getNodeID(h).busy){
				rsNode.setVk(dataStructure_.getReorderBuffer_().getValue(h));
				rsNode.setQk(0);
			}else{
				rsNode.setQk(h);
			}
		}else{
			rsNode.setVk(dataStructure_.getRegisters_().getReg(rt_));
			rsNode.setQk(0);
		}
		
		dataStructure_.getRegisterStatus_().getReorder().set(rd_,robNode.ID);
		dataStructure_.getRegisterStatus_().getBusy().set(rd_, true);
		
		robNode.state = "Issue";
		
		dataStructure_.getReorderBuffer_().getROBList().add(robNode);
		dataStructure_.getReservationStation().getMultList().add(rsNode);
		dataStructure_.issued = rsNode;
		dataStructure_.sPointer++;
		mudou = true;
		return true;
	}
	@Override
	public boolean isExecutable(int i) {
		return dataStructure_.getReservationStation().getMultList().get(i).getQj() == 0 &&
				dataStructure_.getReservationStation().getMultList().get(i).getQk() == 0;
	}
	
	@Override
	public boolean execute(int i) {
		if(dataStructure_.getReorderBuffer_().getRobNodeDest(dataStructure_.getReservationStation().getMultList().get(i).getDest()) != null)
		dataStructure_.getReorderBuffer_().getRobNodeDest(dataStructure_.getReservationStation().getMultList().get(i).getDest()).state = "Executando";
		if(time == 0){
			//dataStructure_.getReservationStation().getMultList().get(i).setVj(dataStructure_.getRegisters_().getReg(dataStructure_.getReservationStation().getMultList().get(i).getVj()));
			//dataStructure_.getReservationStation().getMultList().get(i).setVk(dataStructure_.getRegisters_().getReg(dataStructure_.getReservationStation().getMultList().get(i).getVk()));
			this.setTerminou(true);
			mudou = false;
			return true;
		}
		time--;
		return false;
	}
	
	@Override
	public boolean write(int i) {
			ReorderBufferNode robnode = null;
			for(ReorderBufferNode r : dataStructure_.getReorderBuffer_().getROBList()){
				if(r._instrucao.equals(this))
					robnode = r;
			}
			robnode.value = /*dataStructure_.getRegisters_().getReg(*/dataStructure_.getReservationStation().getMultList().get(i).getVj()/*)*/ *
					/*dataStructure_.getRegisters_().getReg(*/dataStructure_.getReservationStation().getMultList().get(i).getVk()/*)*/;
			robnode.busy = false;
			robnode.state = "Escrita";
			int b = dataStructure_.getReservationStation().getMultList().get(i).getDest();
			dataStructure_.getReservationStation().getMultList().get(i).setBusy(false);
		
			for(ReservationStationNode r : dataStructure_.getReservationStation().getAddList()){
				if(r.getQj() == b) {
					r.setVj(robnode.value);
					r.setQj(0);
				}
				
				if(r.getQk() == b){
					r.setVk(robnode.value);
					r.setQk(0);
				}
			}
			
			for(ReservationStationNode r : dataStructure_.getReservationStation().getMultList()){
				if(r.getQj() == b) {
					r.setVj(robnode.value);
					r.setQj(0);
				}
				
				if(r.getQk() == b){
					r.setVk(robnode.value);
					r.setQk(0);
				}
			}
		
			for(ReservationStationNode r : dataStructure_.getReservationStation().getLoadList()){
				if(r.getQj() == b) {
					r.setVj(robnode.value);
					r.setQj(0);
				}
				if(r.getQk() == b){
					r.setVk(robnode.value);
					r.setQk(0);
				}
			}
			dataStructure_.write = robnode;
			escrita = true;
			return true;
	}
	
	@Override
	public void commit(){
		if(dataStructure_.getReorderBuffer_().getROBList().get(0).state == "Escrita"){
			if (!dataStructure_.getReorderBuffer_().getBusy(0)){
				int d = dataStructure_.getReorderBuffer_().getDest(0);
				
				dataStructure_.getRegisters_().setReg(rd_, dataStructure_.getReorderBuffer_().getValue(0));
				//dataStructure_.getReorderBuffer_().setBusy(0, false);
				if(dataStructure_.getRegisterStatus_().getReorder(rd_) == dataStructure_.getRegisterStatus_().getReorder(dataStructure_.getReorderBuffer_().getDest(0))){
					dataStructure_.getRegisterStatus_().getBusy().set(rd_, false);
					dataStructure_.getRegisterStatus_().getReorder().set(rd_, 0);
				}
			}
			dataStructure_.getReorderBuffer_().getROBList().remove(0);
		}
	}
}