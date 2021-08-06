package tester;

import java.io.*;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import xtc.parser.Result;

public class Main {

  static final int X = 8; //quantidade de vezes a serem repetidas as mediçoes

  // metodo para separar os programas Java para a analise
  public static void coletarDiretorios(File java, CSVTable tabela) {
    File inst[];
    Queue<File> q = new LinkedList();

    inst = java.listFiles();

    for (File f : inst) {
      if (f.isDirectory()) {
        q.add(f);
      }
    }

    File aux;

    try {
      while (!q.isEmpty()) {
        aux = q.remove();
        RunJava j = new RunJava(tabela);
        j.Run(aux);
      }
    } catch (Exception e) {
      System.out.println("Deu merda " + e.getMessage());
      e.printStackTrace();
    }
  }

  public static void main(String[] args) throws IOException {
    CSVTable tabela = new CSVTable();

    List<File> lista = new LinkedList();
    List<File> listaAux = new LinkedList();

    String caminho = "./instancias"; //caminho das instancias
    File f = new File(caminho);

    File vet[] = f.listFiles();

    try { //tenta pegar os diretorios do caminho
      for (int i = 0; i < f.listFiles().length; i++) {
        if (vet[i].isDirectory()) {
          lista.add(vet[i]);
        }
      }
    } catch (Exception e) {
      System.out.println("uai : " + e.getMessage()); // "uai " puquê nois é minero
    }

    for (int i = 0; i < X; i++) {
      Collections.shuffle(lista); // embralhamento da lista de repositórios para evitar ruido de sistema operacional

      for (File file : lista) {
        // Caso adicione/remova um parser, adicionar/remover o case do diretório
        // referente neste switch
        switch (file.getName()) {
          case "closure":
            RunClosure a = new RunClosure(tabela);
            a.Run(vectorToList(file.listFiles()));
            break;
          case "closure_xml":
            RunClosure_xml b = new RunClosure_xml(tabela);
            b.Run(vectorToList(file.listFiles()));
            break;
          case "java_xml":
            RunJava_Xml c = new RunJava_Xml(tabela);
            c.Run(vectorToList(file.listFiles()));
            break;
          case "pair":
            RunPair d = new RunPair(tabela);
            d.Run(vectorToList(file.listFiles()));
            break;
          case "pair_closure":
            RunPair_Closure e = new RunPair_Closure(tabela);
            e.Run(vectorToList(file.listFiles()));
            break;
          case "pair_closure_xml":
            RunPair_Closure_Xml ff = new RunPair_Closure_Xml(tabela);
            ff.Run(vectorToList(file.listFiles()));
            break;
          case "pair_xml":
            RunPair_Xml g = new RunPair_Xml(tabela);
            g.Run(vectorToList(file.listFiles()));
            break;
          case "xml":
            RunXml h = new RunXml(tabela);
            h.Run(vectorToList(file.listFiles()));
            break;
          case "java":
            coletarDiretorios(file, tabela);
            break;
          default:
        }

        tabela.csvToFile("./reports/Saida.csv");
      }
    }
  }

  public static List<File> vectorToList(File[] v) {
    List<File> f = new LinkedList();

    for (int i = 0; i < v.length; i++) {
      f.add(v[i]);
    }
    return f;
  }
}
