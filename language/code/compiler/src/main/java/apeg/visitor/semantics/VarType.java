package apeg.visitor.semantics;


public class VarType {

	private VType type;
	private AttrDirection atributo;
	
	public static enum AttrDirection{
		HERDADO, LOCAL	
	}

	
    public VarType(VType tipo, AttrDirection atributo){	
	   this.type = tipo;
	   this.atributo = atributo;	
    }

    public String toString(){
    	String s = " :: " + type.getName() +  (atributo == AttrDirection.HERDADO ? " H " : " L "); 
    	return s;
    }
    
	public VType getType() {
		return type;
	}

	public AttrDirection getAtributo() {
		return atributo;
	}
 
}
