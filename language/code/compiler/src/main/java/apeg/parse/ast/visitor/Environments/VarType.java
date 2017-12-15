package apeg.parse.ast.visitor.Environments;

import apeg.parse.ast.TypeNode;

public class VarType {

	private TypeNode type;
	private AttrDirection atributo;
	
	public static enum AttrDirection{
		HERDADO, SINTETIZADO	
	}

	
    public VarType(TypeNode tipo, AttrDirection atributo){	
	   this.type = tipo;
	   this.atributo = atributo;	
    }

    public String toString(){
    	String s = " :: " + type.getName() +  (atributo == AttrDirection.HERDADO ? " H " : " S "); 
    	return s;
    }
    
	public TypeNode getType() {
		return type;
	}

	public AttrDirection getAtributo() {
		return atributo;
	}
 
}
