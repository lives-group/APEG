package dataStructure;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

import intrucoes.FactoryDeInstrucao;
import intrucoes.Instrucao;

public class SetDeEntradaDeInstrucoes {
	
	public static ArrayList<Instrucao> getSetIntrucaoDaEntrada(String arquivo, DataStructure dataStructure ){
		String linha;
		ArrayList<Instrucao> list = new ArrayList<Instrucao>();
		try {
			FileReader arq = new FileReader(arquivo);
			BufferedReader lerArq = new BufferedReader(arq);
			while( (linha = lerArq.readLine()) != null){
				//System.out.println(linha.split(";")[0]);
				list.add(FactoryDeInstrucao.getInstrucao(linha.split(";")[0]));
				list.get(list.size()-1).setInstrucao(linha.split("; ")[1]);
				list.get(list.size()-1).setNbyte((list.size()-1)*4);
				list.get(list.size()-1).setDataStructure(dataStructure);
				list.get(list.size()-1).setInstrucaoOpCode(linha.split(";")[0]);
			}
		} catch (Exception e){
			JOptionPane.showMessageDialog(null, "Arquivo não encontrado", "Problema encontrado", JOptionPane.INFORMATION_MESSAGE);
			e.printStackTrace();
		}
		
		return list;
	}
	
	
	public static Instrucao getSetIntrucaoDaEntrada(Instrucao instrucao, DataStructure dataStructure){
		Instrucao inst = FactoryDeInstrucao.getInstrucao(instrucao.getInstrucaoOpCode());
		inst.setInstrucao(instrucao.getInstrucao());
		inst.setNbyte(instrucao.getNbyte());
		inst.setDataStructure(dataStructure);
		return inst;
	}
}
