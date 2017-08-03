package apeg.parse.ast.visitor;


import java.io.IOException;
import java.util.Hashtable;



public class Entry {
	
	
private Hashtable<String,String> entry;
	
public Entry() throws IOException {
	
	entry = new Hashtable<String,String>();	
}

public void table(String name, String tipo, String atributo){
	
//	System.out.println("name "+name);
//	System.out.println("tipo "+tipo);
//	System.out.println("atributo "+ atributo);
	
	entry.put("Nome", name);
	entry.put("Tipo", tipo);
	entry.put("Atributo", atributo);
	
	System.out.println(entry);
	
	
	
	
}






	

}
