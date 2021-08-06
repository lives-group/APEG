package intrucoes;

import dataStructure.ReorderBufferNode;
import dataStructure.ReservationStationNode;

public class InstrucaoIOpBeq extends InstrucaoI implements Instrucao {
	int time = 1;
	public InstrucaoIOpBeq(int r1, int r2, int r3, int r4) {
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
		//robNode.destination = rt_;
		robNode.busy = true;
		
		if(dataStructure_.getReorderBuffer_().getROBList().size() == 0)
			robNode.ID = 1;
		else
			robNode.ID = dataStructure_.getReorderBuffer_().getROBList().get(dataStructure_.getReorderBuffer_().getROBList().size()-1).ID+1;
		rsNode.setDest(robNode.ID);
		rsNode.setVk(dataStructure_.getRegisters_().getReg(rt_));
		rsNode.setQk(0);
		
		rsNode.setA(immediate_);
		
		//dataStructure_.getRegisterStatus_().getReorder().set(rt_, b);
		//dataStructure_.getRegisterStatus_().getBusy().set(rt_, true);
		
		robNode.state = "Issue";
		
		dataStructure_.getReorderBuffer_().getROBList().add(robNode);
		dataStructure_.getReservationStation().getAddList().add(rsNode);
		if(dataStructure_.pred <= dataStructure_.predType/2)
			dataStructure_.sPointer =  immediate_/4;
		else
			dataStructure_.sPointer++;
		dataStructure_.issued = rsNode;
		mudou = true;
		return true;
	}
	
	@Override
	public boolean isExecutable(int i) {
		return dataStructure_.getReservationStation().getAddList().get(i).getQj() == 0 &&
				dataStructure_.getReservationStation().getAddList().get(i).getQk() == 0;
	}
	
	@Override
	public boolean execute(int i) {
		if(dataStructure_.getReorderBuffer_().getRobNodeDest(dataStructure_.getReservationStation().getAddList().get(i).getDest())!=null)
			dataStructure_.getReorderBuffer_().getRobNodeDest(dataStructure_.getReservationStation().getAddList().get(i).getDest()).state = "Executando";
		if(time == 0){
			System.out.println("entou no execute e terminou e foi para escrita");
			//dataStructure_.getReservationStation().getAddList().get(i).setVj(dataStructure_.getRegisters_().getReg(dataStructure_.getReservationStation().getAddList().get(i).getVj()));
			//dataStructure_.getReservationStation().getAddList().get(i).setVj(45);
			//dataStructure_.getReservatdataStructure.getReorderBuffer_().getROBList().size()ionStation().getAddList().get(i).setVk(dataStructure_.getRegisters_().getReg(dataStructure_.getReservationStation().getAddList().get(i).getVk()));
			this.setTerminou(true);
			mudou = false;
			return true;
		}
		time--;
		return false;
	}
	
	@Override
	public boolean write(int i) {
		System.out.println("Veio escrever");
			ReorderBufferNode robnode = null;
			for(ReorderBufferNode r : dataStructure_.getReorderBuffer_().getROBList()){
				if(r._instrucao.equals(this))
					robnode = r;
			}
			if(dataStructure_.getRegisters_().getReg(dataStructure_.getReservationStation().getAddList().get(i).getVj()) ==
					dataStructure_.getReservationStation().getAddList().get(i).getVk())
					robnode.value = immediate_;
			else
				robnode.value = 1 + nByte_/4;
			robnode.busy = false;
			robnode.state = "Escrita";
			int b = dataStructure_.getReservationStation().getAddList().get(i).getDest();
			dataStructure_.getReservationStation().getAddList().get(i).setBusy(false);
			//dataStructure_.getReservationStation().getAddList().remove(i);
			mudou = true;
			escrita = true;
			return true;
	}
	
	@Override
	public void commit(){
		System.out.println("entrou aqui");
		if(dataStructure_.getReorderBuffer_().getROBList().get(0).state == "Escrita"){
			System.out.println("entrou aqui");
			int h = 0;
			if (!dataStructure_.getReorderBuffer_().getBusy(h)){
				int d = dataStructure_.getReorderBuffer_().getDest(h);
				
				if(nByte_/4 >= dataStructure_.getFilaDeIntrucao_().fila_.size()-1){
					if(dataStructure_.getReorderBuffer_().getROBList().get(0).value/4 != nByte_/4 + 1){
						//Ainda para implementar
						dataStructure_.sPointer++;
					}
				}else{
					if(dataStructure_.getReorderBuffer_().getROBList().get(1)._instrucao.getNbyte()/4 != dataStructure_.getReorderBuffer_().getROBList().get(0).value){
						dataStructure_.sPointer = dataStructure_.getReorderBuffer_().getROBList().get(0).value/4;
						//Apaga tudo
						dataStructure_.getReservationStation().clearAll();
						dataStructure_.getReorderBuffer_().clearAll();
						dataStructure_.getRegisterStatus_().clearAll();
					}else{
					
						dataStructure_.getReorderBuffer_().setBusy(h, false);
						if(dataStructure_.getRegisterStatus_().getReorder(d) == dataStructure_.getReorderBuffer_().getDest(h)){
							dataStructure_.getRegisterStatus_().getBusy().set(d, false);
							dataStructure_.getRegisterStatus_().getReorder().set(d, 0);
						}
					}
				}
				
				
				
			}
			if(dataStructure_.getReorderBuffer_().getROBList().size() > 0)
				dataStructure_.getReorderBuffer_().getROBList().remove(0);
		}
	}
}
