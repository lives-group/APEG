package intrucoes;

public class FactoryDeInstrucao {
	
	public static Instrucao getInstrucao(String s){		
		//Intrucao tipo R
		if(getOpCode(s).equals("000000")){
			switch (getFunct(s)){
				case "100000":
					return new InstrucaoROpAdd(getRs(s), getRt(s), getRd(s));
				case "011000":{
					return new InstrucaoROpMul(getRs(s), getRt(s), getRd(s));
					}
				case "000000":
					return new InstrucaoROpNop(getRs(s), getRt(s), getRd(s));
				case "100010":
					return new InstrucaoROpSub(getRs(s), getRt(s), getRd(s));					
			}
		}
		
		//Intrucao tipo I
		switch (getOpCode(s)){
			case "001000":
				return new InstrucaoIOpAddi(Integer.parseInt(getOpCode(s)), 
						getRs(s), getRt(s), getImmediate(s));
			case "000101":
				return new InstrucaoIOpBeq(Integer.parseInt(getOpCode(s)), 
						getRs(s), getRt(s), getImmediate(s));
			case "000111":
				return new InstrucaoIOpBle(Integer.parseInt(getOpCode(s)), 
						getRs(s), getRt(s), getImmediate(s));
				
			case "000100":
				return new InstrucaoIOpBne(Integer.parseInt(getOpCode(s)), 
						getRs(s), getRt(s), getImmediate(s));
			case "100011": 
				return new InstrucaoIOpLw(Integer.parseInt(getOpCode(s)), 
						getRs(s), getRt(s), getImmediate(s));
			case "101011":
				return new InstrucaoIOpSw(Integer.parseInt(getOpCode(s)), 
					getRs(s), getRt(s), getImmediate(s));
		}
		
		//Intrucao tipo J
		if(getFunct(s).equals("000010"))
			return new InstrucaoJOpJmp(getImmediate(s));
		
		return new InstrucaoROpAdd(1,2,3);
	}
	
	private static String getFunct(String s) {
		return s.substring(26, 32);
	}
	private static String getOpCode(String s){
		//System.out.println(s.substring(0, 6));
		return s.substring(0, 6);
	}
	private static int getRs(String s){
		return Integer.parseInt(s.substring(6,11),2);
	}
	private static int getRt(String s){
		return Integer.parseInt(s.substring(11,16),2);
	}
	private static int getRd(String s){
		return Integer.parseInt(s.substring(16,21),2);
	}
	private static int getImmediate(String s){
		return (short)Integer.parseInt(s.substring(6, 32), 2);
	}
	
}
