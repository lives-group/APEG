package apeg.parse.ast.visitor.Environments;

import apeg.parse.ast.TypeNode;

public class VarType {

	private TypeNode type;
	private AttrDirection atributo;
	private int accessCode;
	
	public static enum AttrDirection{
		HERDADO, SINTETIZADO, LOCAL	
	}

	
    public VarType(TypeNode tipo, AttrDirection atributo, int ac){	
	   this.type = tipo;
	   this.atributo = atributo;	
	   accessCode = ac;
    }

    public String toString(){
    	String s = " :: " + type.getName() +  (atributo == AttrDirection.HERDADO ? " H " : " S ") + " a.code = " + accessCode; 
    	return s;
    }
    
	public TypeNode getType() {
		return type;
	}

	public AttrDirection getAtributo() {
		return atributo;
	}
	
	public int getAccessCode() {
		return accessCode;
	}
 
}
