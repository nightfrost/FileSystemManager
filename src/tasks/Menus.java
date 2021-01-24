package tasks;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import tasks.logging.Logging;

public class Menus {
  private String date;
  private long start, end, duration;

  public Menus() {
    this.date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
  }

  public void FsmMenu() {
    Logging log = new Logging();
    boolean isTrue = true;
    Scanner scanner = new Scanner(System.in);
    String choice;

    // Main loop
    while (isTrue) {
      System.out.println("###File System Manager###");
      System.out.println("1. Manage Files");
      System.out.println("2. Show log");
      System.out.println("3. Exit");
      System.out.println("##########################");

      choice = scanner.nextLine();
      switch (choice) {
        case "1":
          fileManagerMenu();
          break;
        case "2":
          List<String> lines = log.logReader();
          for (String line : lines) {
            System.out.println(line);
          }
          break;
        case "3":
          System.out.println("Goodbye!");
          isTrue = false;
          break;

        default:
          System.out.println("Only input between 1-3 is valid.");
      }
    }
  }

  private void fileManagerMenu() {
    Scanner scanner = new Scanner(System.in);
    boolean isTrue = true;

    // instantiate Logging
    Logging log = new Logging();
    // a list of file names
    List<String> files = new FileIO().getFileNames();

    String choice;

    while (isTrue) {
      System.out.println("###File Manager###");
      System.out.println("1. List all files");
      System.out.println("2. List specific files");
      System.out.println("3. Manage specific txt file");
      System.out.println("4. Back to main menu");
      System.out.println("##################");

      choice = scanner.nextLine();
      switch (choice) {
        // List all files
        case "1":
          // Iterate through list and print names.
          start = System.currentTimeMillis();
          for (String fileName : files) {
            System.out.println(files.indexOf(fileName) + "- " + fileName);
          }
          end = System.currentTimeMillis();
          duration = end - start;

          // Log
          log.logWriter("List of all files: " + files.toString() + ". It took " + duration + "ms");

          break;
        // List specific files.
        case "2":
          System.out.print("Please enter the file extension: ");
          String fileExtension = scanner.nextLine();
          List<String> tempFiles = new ArrayList<>();

          // Iterates through the files checking the extensions.
          // If extension exists, put into variable and compare against user input.
          // If extension matches, put into own list and give back to user.
          start = System.currentTimeMillis();
          for (String fName : files) {
            String extension = "";
            int i = fName.lastIndexOf('.');
            int p = Math.max(fName.lastIndexOf('/'), fName.lastIndexOf('\\'));

            if (i > p) {
              extension = fName.substring(i + 1);
            }

            if (extension.equalsIgnoreCase(fileExtension))
              tempFiles.add(fName);
          }
          end = System.currentTimeMillis();
          duration = end - start;

          System.out.print("List of file(s) with " + fileExtension + " as extension: " + tempFiles.toString() + "\n");

          // Log
          log.logWriter("List of all files with (" + fileExtension + ") extension, " + tempFiles.toString()
              + ". It took " + duration + "ms");

          break;
        // Manage specific text file
        case "3":
          // Ask user for input, check if input is valid, if valid call manageSpecificFile
          System.out.println("Enter the index of the targeted file between(0 - " + (files.size() - 1) + "): ");
          int fileIndex = scanner.nextInt();
          scanner.nextLine();
          if (fileIndex < files.size())
            manageSpecificFile(fileIndex);
          else
            System.out.println("Try again!");
          break;
        // Exit menu
        case "4":
          isTrue = false;
          break;

        default:
          System.out.println("Only input between 1-4 is valid.");
      }
    }
  }

  private void manageSpecificFile(int fileIndex) {
    // Instantiate Scanner, FileIO and Log
    Scanner scanner = new Scanner(System.in);
    FileIO fileHandler = new FileIO();
    Logging log = new Logging();
    boolean isTrue = true;
    List<String> files = fileHandler.getFileNames();
    String choice;

    // Get extensions of file for later condition check.
    String extension = "";
    int i = files.get(fileIndex).lastIndexOf('.');
    int p = Math.max(files.get(fileIndex).lastIndexOf('/'), files.get(fileIndex).lastIndexOf('\\'));
    if (i > p) {
      extension = files.get(fileIndex).substring(i + 1);
    }

    while (isTrue) {
      System.out.println("###Manage Specific File###");
      System.out.println("|\t1. Show File Name");
      System.out.println("|\t2. Show File Size");
      System.out.println("|\t3. Number of lines in File");
      System.out.println("|\t4. Search for sentence");
      System.out.println("|\t5. Go back");
      System.out.println("###########################");
      choice = scanner.nextLine();

      switch (choice) {
        // Show file name
        case "1":
          long start = System.currentTimeMillis();
          String fileName = files.get(fileIndex);
          long end = System.currentTimeMillis();
          duration = end - start;

          // Print File name
          System.out.println("File name: " + fileName);

          // Log
          log.logWriter("List file name: (" + fileName + "). It took " + duration + "ms");
          break;

        // Show File Size
        case "2":
          // Get number of lines
          start = System.currentTimeMillis();
          int fileSize = fileHandler.getSize(files.get(fileIndex));
          end = System.currentTimeMillis();
          duration = end - start;
          // Print Size
          System.out.println("The file size is: " + fileSize + " bytes, or  " + fileSize / 1024.0 + " kilobytes ("
              + (int) Math.ceil(fileSize / 1024.0) + "KB on disk)");

          // Log
          log.logWriter("Print size of file: " + fileSize + " bytes, or " + fileSize / 1024.0 + " kilobytes ("
              + (int) Math.ceil(fileSize / 1024.0) + "KB on disk). It took " + duration + "ms");
          break;
        // Show the amount of lines in the file
        case "3":
          // Check if .txt
          if (extension.equalsIgnoreCase("txt")) {
            // Get number of lines
            start = System.currentTimeMillis();
            int numberOfLines = fileHandler.getNumberOfLine(files.get(fileIndex));
            end = System.currentTimeMillis();
            duration = end - start;

            // Print and log
            System.out.println("The number of lines are: " + numberOfLines);
            log.logWriter("List number of lines: (" + numberOfLines + ") line. It took " + duration + "ms");

          } else {
            System.out.println("You can only do this for txt files!");
          }
          break;
        // Search for word
        case "4":
          // Check if .txt
          if (extension.equalsIgnoreCase("txt")) {
            System.out.print("Write a sentence to search for: ");
            String word = scanner.nextLine();

            // Search for specific word
            start = System.currentTimeMillis();
            int occurrences = fileHandler.getSpecificWord(word, files.get(fileIndex));
            end = System.currentTimeMillis();
            duration = end - start;

            // Check for occurrences, if any, print how many. If none, print none. Log as
            // well.
            if (occurrences > 0) {
              System.out.println("The word (" + word + ")exists and appeared: " + occurrences);
              log.logWriter(
                  "Search for word: " + word + " and it appeared: " + occurrences + ". It took " + duration + "ms");
            } else {
              System.out.println("The word (" + word + ")does not exist in the file");
              log.logWriter("Search for word: " + word + ". It doesn't exist in the file . It took " + duration + "ms");
            }
          } else {
            System.out.println("Only applicable for .txt files!");
          }
          break;

        // Exit menu.
        case "5":
          isTrue = false;
          break;

        default:
          System.out.println("Only input between 1-4 is valid.");
      }

    }
  }

}
