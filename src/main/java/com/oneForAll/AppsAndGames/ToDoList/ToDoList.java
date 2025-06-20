package myapp.AppsAndGames.ToDoList;

import myapp.AppsAndGames.AppsAndGamesInterface;
import myapp.utils.*;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

import java.io.File;
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

  public ToDoList(Scanner scan) {
    start();

    // loading user file of to-do-list and creating variable for it
    File userFile = loadUserToDoList();
    loadUserTask(userFile);

    boolean run = true;
    System.out.println("You currently have " + count + " tasks left to do!!");
    while (run) {
      System.out.print("""
           _________________________
          |      ***Options***      |
          |-------------------------|
          |1. Add a new task        |
          |2. List all tasks        |
          |3. List left to-do tasks |
          |4. List completed tasks  |
          |5. Delete task           |
          |6. Return to main menu   |
           -------------------------
           """);
      System.out.print("Enter your choice: ");
      int userChoice = scan.nextInt();
      scan.nextLine();
      TUIUtils.clearScreen();

      switch (userChoice) {
        case 1 -> {
          addNewTask(userFile, scan);
        }
        case 2 -> {
          listTasks(userFile, scan);
          TUIUtils.clearScreen();
          ;
        }
        case 5 -> {
          removeTask(scan, userFile);
        }
        case 6 -> {
          run = false;
        }
        default -> {
          System.out.println("under production....");
          TUIUtils.threadSleep(1250);
        }
      }

    }

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
  private void listTasks(File userFile) {
    System.out.println();
    System.out.println("Task List");
    System.out.println("------------------------");
    try (BufferedReader bfr = new BufferedReader(new FileReader(userFile))) {
      String line;
      int i = 1;

      while ((line = bfr.readLine()) != null) {
        System.out.println(i++ + ". " + line);
      }
    } catch (IOException e) {
      System.out.println("error listing tasks");
    }
    System.out.println("________________________");
    System.out.println();
    System.out.println("press enter to return");
  }

  private void listTasks(File userFile, Scanner scan) {
    System.out.println();
    System.out.println("Task List");
    System.out.println("------------------------");
    try (BufferedReader bfr = new BufferedReader(new FileReader(userFile))) {
      String line;
      int i = 1;

      while ((line = bfr.readLine()) != null) {
        System.out.println(i++ + ". " + line);
      }
    } catch (IOException e) {
      System.out.println("error listing tasks");
    }
    System.out.println("________________________");
    System.out.println();
    System.out.print("press enter to return ");
    scan.nextLine();
  }

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
      count++;
      TUIUtils.clearScreen();
      System.out.print("New task added!!!");
      TUIUtils.clearScreen(1250);
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  private void removeTask(Scanner scan, File userFile) {
    listTasks(userFile);
    int i = -1;
    boolean correctInput = false;
    while (!correctInput) {
      try {
        System.out.println("(enter 0 to return)");
        System.out.print("Enter the task number you want to delete: ");
        i = scan.nextInt();
        if (i == 0) {
          return;
        }
        if (!(i > 0 && i <= count)) {
          System.out.print("Could not find the task number\nEnter task number: ");
        } else {
          correctInput = true;
        }
      } catch (Exception e) {
        System.out.println("Please enter a valid number!");
      }
    }
    tasks.remove(i - 1);
    count--;

    try (BufferedWriter bfw = new BufferedWriter(new FileWriter(userFile))) {
      for (String task : tasks) {
        bfw.write(task);
        bfw.newLine();
        ;
      }
    } catch (IOException e) {
    }
  }

  @Override
  public void start() {
    userName = new CurrentUserName().getCurrentUserName();
    head();
  }

  @Override
  public void head() {
    System.out.println("----------------------------");
    System.out.println("        To-Do-List");
    System.out.println("----------------------------");
  }

}
