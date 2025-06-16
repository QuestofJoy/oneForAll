package myapp.utils;

import java.util.Scanner;

import myapp.AppsAndGames.Hangman.Hangman;
import myapp.AppsAndGames.ToDoList.ToDoList;

public class ProgramMenus {

  public static boolean programSelection(Scanner scan) {

    boolean runProgram = true;
    LoginAndRegister lar = new LoginAndRegister();

    System.out.println("What program do you want to use today?");
    System.out.println("--------------------------------------");
    System.out.println("1. To-Do-List");
    System.out.println("2. Hangman");
    System.out.println("3. Return to login And signup screen");
    System.out.println("4. Exit program");
    System.out.print("Enter your selection: ");

    if (!scan.hasNextInt()) {
      System.out.println("Please enter a number!");
      scan.nextLine();
      return true;
    }

    int selection = scan.nextInt();
    scan.nextLine();

    switch (selection) {
      case 1 -> new ToDoList(scan);
      case 2 -> new Hangman(scan);
      case 3 -> {
        while (!lar.loginAndRegister(scan)) {
        }
      }
      case 4 -> {
        System.out.println("Have a nice day!!\nbyeee :)");
        ;
        runProgram = false;
      }
      default -> System.out.println("under production...");
    }

    return runProgram;

  }

}
