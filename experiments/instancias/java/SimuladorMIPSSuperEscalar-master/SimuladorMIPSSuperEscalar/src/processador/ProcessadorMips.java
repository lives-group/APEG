package processador;
import java.util.ArrayList;

import javax.xml.crypto.Data;
import javax.xml.transform.sax.SAXTransformerFactory;
import GUI.InterfaceMIPS;
import dataStructure.DataStructure;
import dataStructure.FilaDeInstrucao;
import dataStructure.Memory;
import dataStructure.RegisterStatus;
import dataStructure.Registers;
import dataStructure.ReorderBuffer;
import dataStructure.ReorderBufferNode;
import dataStructure.ReservationStation;
import dataStructure.ReservationStationNode;

public class ProcessadorMips {
	public DataStructure dataStructure = new DataStructure(new FilaDeInstrucao(),
									new Memory(), new Registers(), new RegisterStatus(),
									new ReorderBuffer(), new ReservationStation());	
	InterfaceMIPS _GUI = new InterfaceMIPS();
	
	int nclock = 0,nInst=0;
	
	public ProcessadorMips(String arquivo) {
		dataStructure.getFilaDeIntrucao_().setNaFilaDeInstrucao(arquivo, dataStructure);
	}
	
	public void RunClock(InterfaceMIPS GUI){
		if(dataStructure.sPointer <= dataStructure.getFilaDeIntrucao_().fila_.size()){
			_GUI=GUI;
			
			if(_GUI.getPred() !=4)
				dataStructure.predType = _GUI.getPred();
			
			nclock++;
			ArrayList<ReorderBufferNode> robList = dataStructure.getReorderBuffer_().getROBList();
			ArrayList<ReservationStationNode> rsLoadList = dataStructure.getReservationStation().getLoadList();
			ArrayList<ReservationStationNode> rsAddList = dataStructure.getReservationStation().getAddList();
			ArrayList<ReservationStationNode> rsMultList = dataStructure.getReservationStation().getMultList();
			
			System.out.println("");
			System.out.println("-------------------Antes-------------------------");
			System.out.println("");
			
			System.out.println("RobList");
			for(int i=0; i < robList.size(); i++){
				System.out.println(robList.get(i).instruction
						+"  "+robList.get(i).ID
						+"  "+robList.get(i).busy
						+"  "+robList.get(i).state
						+"  "+robList.get(i).destination
						+"  "+robList.get(i).value);
				//robList.get(i)._instrucao.setMudou(false);
			}
			System.out.println("rsLoadList");
			for(int i=0; i < rsLoadList.size(); i++){
				System.out.println(rsLoadList.get(i).getInstrucao().getInstrucao()
						+"  "+rsLoadList.get(i).getOp()
						+"  "+rsLoadList.get(i).getVj()
						+"  "+rsLoadList.get(i).getVk()
						+"  "+rsLoadList.get(i).getQj()
						+"  "+rsLoadList.get(i).getQk()
						+"  "+rsLoadList.get(i).getDest()
						+"  "+rsLoadList.get(i).getA());
				//rsLoadList.get(i).getInstrucao().setMudou(false);
			}
			System.out.println("rsAddList");
			for(int i=0; i < rsAddList.size(); i++){
				System.out.println(rsAddList.get(i).getInstrucao().getInstrucao()
						+"  "+rsAddList.get(i).getOp()
						+"  "+rsAddList.get(i).getVj()
						+"  "+rsAddList.get(i).getVk()
						+"  "+rsAddList.get(i).getQj()
						+"  "+rsAddList.get(i).getQk()
						+"  "+rsAddList.get(i).getDest()
						+"  "+rsAddList.get(i).getA());
				//rsAddList.get(i).getInstrucao().setMudou(false);
			}
			System.out.println("rsMultList");
			for(int i=0; i < rsMultList.size(); i++){
				System.out.println(rsMultList.get(i).getInstrucao().getInstrucao()
						+"  "+rsMultList.get(i).getOp()
						+"  "+rsMultList.get(i).getVj()
						+"  "+rsMultList.get(i).getVk()
						+"  "+rsMultList.get(i).getQj()
						+"  "+rsMultList.get(i).getQk()
						+"  "+rsMultList.get(i).getDest()
						+"  "+rsMultList.get(i).getA());
				//rsMultList.get(i).getInstrucao().setMudou(false);
			}
			System.out.println("");
			System.out.println("--------------------------------------------");
			System.out.println("");
	
			dataStructure.getReservationStation().execute();
			dataStructure.getReservationStation().write();
			
			int tamanhoROB = robList.size();
			dataStructure.getReorderBuffer_().commit();
			if(tamanhoROB != robList.size()){
				nInst++;
			}
			//Ver tamanho do rob antes do commit e depois do commit, se mudou soma uma no número de instruções concluidas
			dataStructure.getFilaDeIntrucao_().issue();
				
			dataStructure.issued = null;
			dataStructure.write = null;
			
			
			System.out.println("");
			System.out.println("--------------------------------------------");
			System.out.println("");
			
			System.out.println("RobList");
			for(int i=0; i < robList.size(); i++){
				System.out.println(robList.get(i).instruction
						+"  "+robList.get(i).ID
						+"  "+robList.get(i).busy
						+"  "+robList.get(i).state
						+"  "+robList.get(i).destination
						+"  "+robList.get(i).value);
				robList.get(i)._instrucao.setMudou(false);
			}
			System.out.println("rsLoadList");
			for(int i=0; i < rsLoadList.size(); i++){
				System.out.println(rsLoadList.get(i).getInstrucao().getInstrucao()
						+"  "+rsLoadList.get(i).getOp()
						+"  "+rsLoadList.get(i).getVj()
						+"  "+rsLoadList.get(i).getVk()
						+"  "+rsLoadList.get(i).getQj()
						+"  "+rsLoadList.get(i).getQk()
						+"  "+rsLoadList.get(i).getDest()
						+"  "+rsLoadList.get(i).getA());
				rsLoadList.get(i).getInstrucao().setMudou(false);
			}
			System.out.println("rsAddList");
			for(int i=0; i < rsAddList.size(); i++){
				System.out.println(rsAddList.get(i).getInstrucao().getInstrucao()
						+"  "+rsAddList.get(i).getOp()
						+"  "+rsAddList.get(i).getVj()
						+"  "+rsAddList.get(i).getVk()
						+"  "+rsAddList.get(i).getQj()
						+"  "+rsAddList.get(i).getQk()
						+"  "+rsAddList.get(i).getDest()
						+"  "+rsAddList.get(i).getA());
				rsAddList.get(i).getInstrucao().setMudou(false);
			}
			System.out.println("rsMultList");
			for(int i=0; i < rsMultList.size(); i++){
				System.out.println(rsMultList.get(i).getInstrucao().getInstrucao()
						+"  "+rsMultList.get(i).getOp()
						+"  "+rsMultList.get(i).getVj()
						+"  "+rsMultList.get(i).getVk()
						+"  "+rsMultList.get(i).getQj()
						+"  "+rsMultList.get(i).getQk()
						+"  "+rsMultList.get(i).getDest()
						+"  "+rsMultList.get(i).getA());
				rsMultList.get(i).getInstrucao().setMudou(false);
			}
			
			
			System.out.println("SP " + dataStructure.sPointer);
			System.out.println("Tamanho do Rob: "+ dataStructure.getReorderBuffer_().getROBList().size());
			
			System.out.println("");
			System.out.println("--------------------------------------------");
			System.out.println("");
			
			_GUI.setTableROB(dataStructure.getReorderBuffer_().getROBList());
			_GUI.setTableRS(dataStructure.getReservationStation().getLoadList(),dataStructure.getReservationStation().getAddList(),
					dataStructure.getReservationStation().getMultList());
			_GUI.setTableRegs(dataStructure.getRegisterStatus_());
			_GUI.setTableMemoria(dataStructure.getMemory_());
			_GUI.setTableClock(dataStructure.sPointer,nclock,nInst);
		}
	}
	
	/*public static void main(String[] args) {
		ProcessadorMips p = new ProcessadorMips("/home/spider/git/SimuladorMIPSSuperEscalar/SimuladorMIPSSuperEscalar/src/dataStructure/entrada");
		System.out.println(p.dataStructure.sPointer);
		System.out.println(p.dataStructure.getFilaDeIntrucao_().fila_.size());
		p.RunClock();
		System.out.println(p.dataStructure.getReservationStation().getAddList().get(0).getInstrucao().getInstrucao());
		p.RunClock();
		p.RunClock();
		System.out.println(p.dataStructure.getReservationStation().getLoadList().get(0).getInstrucao().getInstrucao());

		
	}*/
}
