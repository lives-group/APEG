package dataStructure;

public class Memory {
	static int[] memoria = new int[2048];
	
	public int getMem(int i) {
		return memoria[i];
	}
	public void setMem(int i, int value){
		memoria[i] = value;
	}
}
