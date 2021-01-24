package tasks;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//FileIO class - handles all I/O for files.
public class FileIO {

  private List<String> fileN;
  private String directoryPath;

  public FileIO() {
    fileN = new ArrayList<>();
    // Path to local ressources folder
    directoryPath = "C:\\Users\\Lucas\\Documents\\Visual Studio Code\\projects\\FileSystemManager\\src\\resources";
  }

  // Returns a String List with all file names
  public List<String> getFileNames() {
    File[] files = new File(directoryPath).listFiles();
    for (File file : files) {
      if (file.isFile()) {
        fileN.add(file.getName());
      }
    }
    return fileN;
  }

  // Will provide the size of a specific file.
  public int getSize(String fileName) {
    // FileInputStream reads byte by byte
    try (FileInputStream fileInputStream = new FileInputStream(directoryPath + "/" + fileName)) {
      int data = fileInputStream.read();
      int byteCount = 0;

      // -1 indicates no more data
      while (data != -1) {
        byteCount++;
        data = fileInputStream.read();
      }
      return byteCount;
    } catch (IOException ex) {
      System.out.println(ex.getMessage());
      return 0;
    } catch (Exception ex) {
      ex.printStackTrace();
      return 0;
    }
  }

  public int getNumberOfLine(String fileName) {
    // buffer to read line by line
    try (BufferedReader bufferedReader = new BufferedReader(new FileReader(directoryPath + "/" + fileName))) {
      int lines = 0;

      String line;
      // to keep iterating as long as there's line in the buffer
      while ((line = bufferedReader.readLine()) != null) {
        // process the line
        lines++;

      }
      // returns the number of counted lines
      return lines;

    } catch (IOException ex) {
      System.out.println(ex.getMessage());
    } catch (Exception ex) {
      ex.printStackTrace();

    }
    return 0;

  }

  public int getSpecificWord(String word, String fileName) {
    // Instantiate scanner
    try (Scanner scanner = new Scanner(new File(directoryPath + "/" + fileName))) {
      // Use Space delimiter
      scanner.useDelimiter(" ");
      int wordCounter = 0;
      String fWord;

      while (scanner.hasNext()) {
        // Assign next word, check if it matches user input, if yes wordCount++
        fWord = scanner.next();
        if (fWord.contains(word)) {
          wordCounter++;
        }
      }

      // Check for occurences
      if (wordCounter > 0)
        return wordCounter;

    } catch (IOException ex) {
      ex.printStackTrace();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return 0;
  }
}
