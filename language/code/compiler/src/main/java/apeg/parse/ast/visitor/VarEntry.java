package apeg.parse.ast.visitor;



public class VarEntry {

	private String nome;
	private String type;
	private AttrDirection atributo;
	
	public static enum AttrDirection{
		HERDADO, SINTETIZADO	
	}
	
    public VarEntry(String nome, String tipo, AttrDirection atributo){	
	   this.nome = nome;
	   this.type = tipo;
	   this.atributo = atributo;	
    }

    public String toString(){
    	String s = nome + " :: " + type +  (atributo == AttrDirection.HERDADO ? " H " : " S "); 
    	return s;
    }
    
    public String getNome() {
		return nome;
	}
	public String getType() {
		return type;
	}

	public AttrDirection getAtributo() {
		return atributo;
	}
}
