package apeg.rts;

class RuleInfo{
	public String rname;
	public int n;
	
	public RuleInfo(String name){ 
		 rname = name;
		 n = 0;
	}
	
	public String toString() {
		return " (" + rname +"," + n +")";
	}
}
