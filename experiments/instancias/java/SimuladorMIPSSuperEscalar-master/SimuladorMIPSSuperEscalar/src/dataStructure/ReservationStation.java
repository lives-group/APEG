package dataStructure;

import java.util.ArrayList;

import intrucoes.Instrucao;
import intrucoes.InstrucaoJOpJmp;

public class ReservationStation {
	
	ArrayList<ReservationStationNode> loadList = new ArrayList<ReservationStationNode>(5);
	ArrayList<ReservationStationNode> addList = new ArrayList<ReservationStationNode>(3);
	ArrayList<ReservationStationNode> multList = new ArrayList<ReservationStationNode>(3);
	private DataStructure dataStructure_;
	Instrucao i1=null, i2=null;
	int a1=0, a2=0;
	
	public ArrayList<ReservationStationNode> getLoadList(){
		return loadList;
	}
	
	public ArrayList<ReservationStationNode> getAddList(){
		return addList;
	}
	
	public ArrayList<ReservationStationNode> getMultList(){
		return multList;
	}
	public void setDataStructure(DataStructure dataStructure) {
		this.dataStructure_ = dataStructure;
	}
	
	public void execute(){
		
		//"Escolher" o Load a ser executado
		if(loadList.size()!=0 && dataStructure_.loadexecute == null){
			dataStructure_.loadexecute = loadList.get(0);
		}
		
		//Escolher o Add a ser executado
		if(dataStructure_.addexecute == null ){
			for(int i = 0; i < addList.size(); i++){
				if(addList.get(i)._instrucao.isExecutable(i)){
					if(dataStructure_.issued != addList.get(i)){
						dataStructure_.addexecute = addList.get(i);
						a1 = i;
						break;
					}
				}
			}
		}
		//System.out.println("Comecou a verificar");
		//Escolher o Mult a ser executado
		if(dataStructure_.multexecute == null){
			for(int i = 0; i < multList.size(); i++){
				if(multList.get(i)._instrucao.isExecutable(i)){
					if(dataStructure_.issued != multList.get(i)){
						dataStructure_.multexecute = multList.get(i);
						a2 = i;
						break;
					}
				}
			}
		}
		
		if(dataStructure_.loadexecute != null)
			System.out.println(dataStructure_.loadexecute._instrucao.getInstrucao());
		if(dataStructure_.addexecute != null)
			System.out.println("   "+ dataStructure_.addexecute._instrucao.getInstrucao());
		if(dataStructure_.multexecute != null)
			System.out.println("   "+ dataStructure_.multexecute._instrucao.getInstrucao());
		
		//System.out.println("Comecou a verificar");
		
		//Executar o Load
		if(dataStructure_.loadexecute != null)
			dataStructure_.loadexecute.execute(0);
		//executar o Add
		if(dataStructure_.addexecute != null)
			dataStructure_.addexecute.execute(a1);
		//Executar o Mult
		if(dataStructure_.multexecute != null)
			dataStructure_.multexecute.execute(a2);
		
	}
	
	public void write(){
		if(loadList.size()!=0 && dataStructure_.loadexecute != null){
			if(dataStructure_.loadexecute._instrucao.terminouDeExecutar()){	
				loadList.get(0).write(0);
				//dataStructure_.write = loadList.get(0);
				dataStructure_.write = dataStructure_.getReorderBuffer_().getRobNodeDest(loadList.get(0)._dest);
				loadList.remove(0);
				dataStructure_.loadexecute = null;
				return;
				
			}
		}
		
		if(dataStructure_.multexecute!=null){
			if(dataStructure_.multexecute._instrucao.terminouDeExecutar()){
				multList.get(a2).write(a2);
				//dataStructure_.write = multList.get(a2);
				dataStructure_.write = dataStructure_.getReorderBuffer_().getRobNodeDest(multList.get(a2)._dest);
				multList.remove(a2);
				dataStructure_.multexecute = null;
				return;
			}
		}
		
		if(dataStructure_.addexecute!=null){
			System.out.println("Comparar com os outros");
			if(dataStructure_.addexecute._instrucao.terminouDeExecutar()){
				System.out.println("Comparar com os outros");
				addList.get(a1).write(a1);
				dataStructure_.write = dataStructure_.getReorderBuffer_().getRobNodeDest(addList.get(a1)._dest);
				addList.remove(a1);
				dataStructure_.addexecute = null;
				return;
			}
		}
			
		/*for(int i = 0; i<3 ; i++){
			if(i<multList.size()){
					if(multList.get(i).write(i)){
						//System.out.println(i+ "Probelma RS ver ind" + multList.size());
						//multList.remove(i);
						dataStructure_.multexecute = null;
						return;
					}
			}
		}
		for(int i = 0; i<3 ; i++){
			if(i < addList.size()){
				if(addList.get(i).write(i)){
					//addList.remove(i);
					dataStructure_.addexecute = null;
					return;
				}
			}
		}*/
	}
	
	public boolean isFullLoad(){
		if(loadList.size()>=5){
			return true;
		}
		
		return false;
	}
	
	
	public boolean isFullAdd(){
		if(addList.size()>=3){
			return true;
		}
		
		return false;
	}
	
	public boolean isFullMult(){
		if(multList.size()>=3){
			return true;
		}
		
		return false;
	}
	public void addAddNode(ReservationStationNode rs){
		addList.add(rs);
	}
	
	public void clearAll(){
		loadList.clear();
		addList.clear();
		multList.clear();
		dataStructure_.addexecute = null;
		dataStructure_.loadexecute = null;
		dataStructure_.multexecute = null;
	}
}
