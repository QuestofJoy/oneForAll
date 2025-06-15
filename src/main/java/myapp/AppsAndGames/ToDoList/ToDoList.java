package myapp.AppsAndGames.ToDoList;

import myapp.AppsAndGames.AppsAndGamesInterface;
import myapp.utils.LoginAndRegister;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

import java.io.File;
import java.nio.file.Paths;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;

public class ToDoList implements AppsAndGamesInterface {
  private String userName;
  private int count = 0;
  List<String> tasks = new ArrayList<>();

  LoginAndRegister lar = new LoginAndRegister();
  String DIR_USERS = lar.dirUsers();
  String FILE_TEMPORARY = lar.fileTemporary();

  public ToDoList(Scanner scan) {
    start();

    // loading user file of to-do-list and creating variable for it
    File userFile = loadUserToDoList();
    loadUserTask(userFile);

    System.out.println(count);
    boolean run = true;
    while (run) {
      System.out.print("enter 1 to add task 2 to exit: ");
      int userChoice = scan.nextInt();
      scan.nextLine();

      switch (userChoice) {
        case 1 -> addNewTask(userFile, scan);
        case 2 -> {
          System.out.println("exiting...");
          run = false;
        }
        default -> System.out.println("fuck you");
      }
    }

  }

  private boolean checkUser() {
    boolean userExists = false;
    File file = new File(FILE_TEMPORARY);

    if (!file.exists()) {
      System.out.println(
          "it seems you are not logged in which is quiet surprising. UWU\nplease exit the app and log in again :)");
    }
    try (BufferedReader bfr = new BufferedReader(new FileReader(FILE_TEMPORARY))) {
      userName = bfr.readLine();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return userExists;
  }

  private File loadUserToDoList() {
    File userFile = new File(DIR_USERS + File.separator + userName + File.separator + "to-do-list.txt");

    if (!userFile.exists()) {
      try {
        userFile.createNewFile();
        System.out.println(userName + " to-do-list created");
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      System.out.println(userName + "\'s to-do-list exists");
    }
    return userFile;
  }

  // reads the line count and loads user tasks
  private void loadUserTask(File userFile) {
    try (BufferedReader bfr = new BufferedReader(new FileReader(userFile))) {
      String line;
      while ((line = bfr.readLine()) != null) {
        count += 1;
        tasks.add(line);
      }
    } catch (IOException e) {
    }
  }

  private void addNewTask(File userFile, Scanner scan) {
    System.out.print("Enter new task: ");
    String line = scan.nextLine();
    tasks.add(count + line);
    try (BufferedWriter bfw = new BufferedWriter(new FileWriter(userFile, true))) {
      bfw.write(line);
      bfw.newLine();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  private void listTask() {

  }

  private void removeTask() {

  }

  // exitToMainMenu will be on utils
  private void exitToMainMenu() {
  }

  @Override
  public void start() {
    checkUser();
    System.out.println("Loading To-Do-List...");
    head();
  }

  @Override
  public void head() {
    System.out.println("----------------------------");
    System.out.println("        To-Do-List");
    System.out.println("----------------------------");
  }

}
