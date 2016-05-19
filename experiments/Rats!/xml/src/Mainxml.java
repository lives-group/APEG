import java.io.FileReader;
import xtc.parser.Result;

	public class Mainxml {
		
		public static void main(String args[]){
			
			try{
				FileReader reader = new FileReader("./input/entrada.txt");
				xml parser = new xml(reader, "entrada");
				Result result = parser.pcompilation_unit(0);
		           if( result instanceof xtc.parser.ParseError){
		              System.err.println("Nao aceito");
		           }else{
		              System.out.println("Aceito");
		           }
		        }catch(Exception e){
		            System.err.println("Alguma coisa deu errado !");
		        }
			
		}
      
}
