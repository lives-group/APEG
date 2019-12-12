package apeg.visitor.Environments;

import apeg.ast.types.Type;

public class VarType {

	private Type type;
	private AttrDirection atributo;
	
	public static enum AttrDirection{
		HERDADO, SINTETIZADO, LOCAL	
	}

	
    public VarType(Type tipo, AttrDirection atributo){	
	   this.type = tipo;
	   this.atributo = atributo;	
    }

    public String toString(){
    	String s = " :: " + type.getName() +  (atributo == AttrDirection.HERDADO ? " H " : " S "); 
    	return s;
    }
    
	public Type getType() {
		return type;
	}

	public AttrDirection getAtributo() {
		return atributo;
	}
 
}
