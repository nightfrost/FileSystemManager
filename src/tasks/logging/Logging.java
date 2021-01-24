package tasks.logging;

import java.io.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;

public class Logging {
  private String directoryPath;

  public Logging() {
    directoryPath = "C:\\Users\\Lucas\\Documents\\Visual Studio Code\\projects\\FileSystemManager\\src\\resources";
  }

  public void logWriter(String log) {
    String path = directoryPath + "\\log.txt";
    String dateAndTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());

    // FileWriter called with append: true will write the upcoming text at the end
    // of the file.
    try (FileWriter fileWriter = new FileWriter(path, true);) {

      // The log will be written like:
      // Filename, Date and Time, Log Message, NewLine
      fileWriter.write("log.txt(" + dateAndTime + ") " + log + "\n");

    } catch (IOException e) {
      System.out.println();

    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public List<String> logReader() {
    List<String> lines = new ArrayList<>();
    // buffer to read line by line
    try (BufferedReader bufferedReader = new BufferedReader(new FileReader(directoryPath + "\\log.txt"))) {
      String line;
      while ((line = bufferedReader.readLine()) != null) {
        lines.add(line);
      }

      return lines;

    } catch (IOException ex) {
      System.out.println(ex.getMessage());
    } catch (Exception ex) {
      ex.printStackTrace();

    }
    return null;

  }
}