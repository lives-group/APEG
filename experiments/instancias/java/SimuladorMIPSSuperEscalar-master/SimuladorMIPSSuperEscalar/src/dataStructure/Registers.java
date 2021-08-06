package dataStructure;

public class Registers {
	int[] reg = new int[32];
	public Registers(){
		reg[1] = 0;
	}
	public int getReg(int i) {
		return reg[i];
	}
	public void setReg(int i, int value){
		reg[i] = value;
	}
}
