package tester;

import java.io.*;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import parsers.rats.pair.*;
import xtc.parser.Result;

public class RunPair implements Runner {

  CSVTable table;

  public RunPair(CSVTable table) {
    this.table = table;
  }

  @Override
  public void Run(List<File> lista) throws IOException {
    long beginTime, endTime, auxTimeMedicao = 0;
    Result r;
    FileReader file;
    String pth;
    int j = 0;

    Collections.shuffle(lista);
    for (int i = 0; i < lista.size(); i++) {
      pth = (lista.get(i)).getPath();
      file = new FileReader(pth);
      pair parser = new pair(file, pth);
      beginTime = System.currentTimeMillis();
      r = parser.pcompilation_unit(0);
      endTime = System.currentTimeMillis();
      auxTimeMedicao += (endTime - beginTime);
    }
    table.addLi("pair", "" + auxTimeMedicao);
  }

  public CSVTable getTable() {
    return this.table;
  }
}
