package dataStructure;

import java.util.ArrayList;

public class RegisterStatus {
	ArrayList<Integer> reorder = new ArrayList<Integer>(32);
	ArrayList<Boolean> busy = new ArrayList<Boolean>(32);
	public RegisterStatus() {
		for(int i=0; i<32;i++)
			reorder.add(0);
		for(int i=0; i<32;i++)
			busy.add(false);
	}
	public boolean isBusy (int ID){
		//System.out.println(busy.get(ID));
		return busy.get(ID);
	}
	public int getReorder(int id){
		return reorder.get(id);
	}
	public ArrayList<Integer> getReorder(){
		return reorder;
	}
	public ArrayList<Boolean> getBusy(){
		return busy;
	}
	public void clearAll(){
		reorder = new ArrayList<Integer>(32);
		busy = new ArrayList<Boolean>(32);
		for(int i=0; i<32;i++)
			reorder.add(0);
		for(int i=0; i<32;i++)
			busy.add(false);
	}
}
