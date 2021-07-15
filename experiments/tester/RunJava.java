package tester;

import java.io.*;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import parsers.rats.java.*;
import xtc.parser.Result;

public class RunJava {

  CSVTable table;

  public RunJava(CSVTable table) {
    this.table = table;
  }

  public void Run(File f) throws IOException {
    long beginTime, endTime, auxTimeMedicao = 0;
    Result r;
    FileReader file;
    String pth;
    String name = f.getName();
    int j = 0;
    File inst[];
    Queue<File> q = new LinkedList();
    List<File> lista = new LinkedList();
    q.add(f);

    try {
      while (!q.isEmpty()) {
        f = q.remove();

        inst = f.listFiles();
        for (File fi : inst) {
          if (fi.isDirectory()) {
            q.add(fi);
          }
        }

        for (int i = 0; i < inst.length; i++) {
          if (inst[i].isDirectory()) {
            q.add(inst[i]);
          } else if (
            inst[i].getPath()
              .startsWith(".java", inst[i].getPath().length() - 5)
          ) {
            lista.add(inst[i]);
          }
        }
      }
    } catch (Exception e) {
      System.out.println("Deu merda " + e.getMessage());
      e.printStackTrace();
    }

    Collections.shuffle(lista);

    for (int i = 0; i < lista.size(); i++) {
      pth = (lista.get(i)).getPath();
      file = new FileReader(pth);
      javarats parser = new javarats(file, pth);

      beginTime = System.currentTimeMillis();
      r = parser.pcompilation_unit(0);
      endTime = System.currentTimeMillis();

      auxTimeMedicao += (endTime - beginTime);
    }
    table.addLi(name, "" + auxTimeMedicao);
  }

  public CSVTable getTable() {
    return this.table;
  }
}
