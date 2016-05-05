import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import java.io.File;
import xtc.parser.Result;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Main2 {
	private ArrayList<File> fs;

	public Main2() {
		fs = new ArrayList<File>();
	}
	
	public void reset(){
	   fs = new ArrayList<File>();
	}

	public ArrayList<File> listDirs(String path) {
		File files[];

		File f = new File(path);
		files = f.listFiles();
		for (int i = 0; i < files.length; i++) {

			if (files[i].isDirectory()) {
				
				//System.out.println(files[i].toString());
				
				listDirs(files[i].toString());
							}

			else {
				String s = files[i].toString().toLowerCase();
				if (s.endsWith(                                                                                                                                                      ".java")) {
					fs.add(files[i]);
				}
			}
		}
		return fs;
	}
	
	
	public static void main(String args[]) throws Exception {
		FileOutputStream f;
		String targets[] = new String[5];

		//targets[0] = "/home/deise/Área de Trabalho/aula/iniciação/workspace/rats3/input/teste";		
		
        targets[0] = "/home/deise/workspace/rats3/input/cryptix-jce";
        targets[1] = "/home/deise/workspace/rats3/input/antlr4";
		targets[2] = "/home/deise/workspace/rats3/input/LGI";
		targets[3] = "/home/deise/workspace/rats3/input/mouse";
		targets[4] = "/home/deise/workspace/rats3/input/xtc";

		Main2 l = new Main2();
		String S = "";
		
		for (int k = 0; k < targets.length; k++) {
			long timeInicio = System.nanoTime();
			
			ArrayList<File> fl = l.listDirs(targets[k]);
			System.out.println("Processando em : " + targets[k]);
			int i;
		
			for ( i = 0; i < fl.size(); i++) {
				
				
				//System.out.println("\n");
				//System.out.println(fl.get(i).toString());
				FileReader diretorio = new FileReader(fl.get(i));
				javarats parser = new javarats(diretorio, fl.get(i).toString());
				Result result = parser.pcompilation_unit(0);
				
				if (result instanceof xtc.parser.ParseError) {
					System.out.print(fl.get(i).toString());
					System.out.println("   [ Nao aceito ]");
					
				} else {
					
					//System.err.println("Aceito");
				}
			}
			System.out.println(i);
			
			long timeFinal = System.nanoTime();
	        //Imprime o tempo de execução
	       
	        String sd = new SimpleDateFormat("mm:ss:SSS").format(new Date(timeFinal - timeInicio));
	        System.out.println(sd);
	        S = S + "\n" + sd;
	        
	        try{
	            String s = S;// coisas que vc vai querer passar pro txt
	            f =  new FileOutputStream("teste.txt");
	            f.write(s.getBytes("US-ASCII"));
	            }catch(IOException e){
	           System.err.println(e.getMessage());
	        }
	        
	        l.reset();
	       
	}
		
	}
}
