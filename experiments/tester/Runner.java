package tester;

import java.io.File;
import java.io.IOException;
import java.util.List;

interface Runner {
  public void Run(List<File> lista) throws IOException;

  public CSVTable getTable();
}
