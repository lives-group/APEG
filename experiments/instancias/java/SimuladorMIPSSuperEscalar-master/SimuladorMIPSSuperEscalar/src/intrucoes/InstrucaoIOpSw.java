package intrucoes;

import dataStructure.ReorderBufferNode;
import dataStructure.ReservationStationNode;

public class InstrucaoIOpSw extends InstrucaoI implements Instrucao {
	int time = 4;
	public InstrucaoIOpSw(int r1, int r2, int r3, int r4) {
		super(r1, r2, r3, r4);
	}
	@Override
	public boolean issue() {
		dataStructure_.issued = null;
		if(dataStructure_.getReservationStation().isFullAdd() || 
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
		robNode.destination = 0; //Coloquei zero, pq na execução vai pegar o valor real
		robNode.busy = true;
		
		if(dataStructure_.getReorderBuffer_().getROBList().size() == 0)
			robNode.ID = 1;
		else
			robNode.ID = dataStructure_.getReorderBuffer_().getROBList().get(dataStructure_.getReorderBuffer_().getROBList().size()-1).ID+1;
		rsNode.setDest(robNode.ID);
		if(dataStructure_.getRegisterStatus_().isBusy(rt_)){
			int h = dataStructure_.getRegisterStatus_().getReorder(rt_);
			if(!dataStructure_.getReorderBuffer_().getNodeID(h).busy){
				System.out.println(h);
				rsNode.setVk(dataStructure_.getReorderBuffer_().getNodeID(h).value);
				rsNode.setQk(0);
			}else{
				rsNode.setQk(h);
			}
		}else{
			rsNode.setVk(dataStructure_.getRegisters_().getReg(rt_));
			rsNode.setQk(0);
		}
		
		//dataStructure_.getRegisterStatus_().getReorder().set(rd_, robNode.ID);
		//dataStructure_.getRegisterStatus_().getBusy().set(rd_, true);
		
		robNode.state = "Issue";
		
		dataStructure_.getReorderBuffer_().getROBList().add(robNode);
		dataStructure_.getReservationStation().getLoadList().add(rsNode);
		dataStructure_.issued = rsNode;
		dataStructure_.sPointer++;
		mudou = true;
		return true;
	}
	
	@Override
	public boolean isExecutable(int i) {
		System.out.println("is executable!");
		return dataStructure_.getReservationStation().getLoadList().get(i).getQj() == 0 &&
				dataStructure_.getReservationStation().getLoadList().get(i).getQk() == 0 &&
				dataStructure_.getReorderBuffer_().getROBList().get(0)._instrucao.equals(this);
	}
	@Override
	public boolean execute(int i) {
		if(dataStructure_.getReorderBuffer_().getRobNodeDest(dataStructure_.getReservationStation().getLoadList().get(i).getDest())!=null)
			dataStructure_.getReorderBuffer_().getRobNodeDest(dataStructure_.getReservationStation().getLoadList().get(i).getDest()).state = "Executando";
		iniciou = true;
		if(time == 0){
			dataStructure_.getReorderBuffer_().getROBList().get(0).destination = 
					dataStructure_.getReservationStation().getLoadList().get(i).getVk() +
						dataStructure_.getReservationStation().getLoadList().get(i).getA();
			//dataStructure_.getReservationStation().getAddList().get(i).setVj(dataStructure_.getRegisters_().getReg(dataStructure_.getReservationStation().getAddList().get(i).getVj()));
			//dataStructure_.getReservationStation().getAddList().get(i).setVk(dataStructure_.getRegisters_().getReg(dataStructure_.getReservationStation().getAddList().get(i).getVk()));
			terminou = true;
			mudou = true;
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
			robnode.value = dataStructure_.getReservationStation().getLoadList().get(i).getVk();
			robnode.busy = false;
			robnode.state = "Escrita";
			dataStructure_.getReservationStation().getLoadList().get(i).setBusy(false);
			//dataStructure_.getReservationStation().getLoadList().remove(i);
			mudou = true;
			dataStructure_.write = robnode;
			escrita = true;
			return true;

	}
	
	@Override
	public void commit(){
		System.out.println("Tentativa desesperada de dar o commit");
		if(dataStructure_.getReorderBuffer_().getROBList().get(0).state == "Escrita"){
			System.out.println("Entrou, mas consolidou?");
			int h = 0;
			if (!dataStructure_.getReorderBuffer_().getBusy(h)){
				int d = dataStructure_.getReorderBuffer_().getDest(h);
				System.out.println("sim");
				//dataStructure_.getRegisters_().setReg(d, dataStructure_.getReorderBuffer_().getValue(h));
				dataStructure_.getMemory_().setMem(d, dataStructure_.getReorderBuffer_().getValue(h));
				
				//dataStructure_.getReorderBuffer_().setBusy(h, false);
				if(dataStructure_.getRegisterStatus_().getReorder(d) == dataStructure_.getReorderBuffer_().getDest(h))
				{
					dataStructure_.getRegisterStatus_().getBusy().set(d, false);
					dataStructure_.getRegisterStatus_().getReorder().set(d, 0);
				}
			}
			dataStructure_.getReorderBuffer_().getROBList().remove(0);
		}
	}
}
