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

    // this is a cracked log out. it does not log out just returns to the
    // login and signup screen
    // the newly logged in user's data will be loaded
    // its like overriding... idk
    System.out.println("3. Logout");
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
        TUIUtils.clearScreen();
        while (!lar.loginAndRegister(scan)) {
        }
      }
      case 4 -> {
        TUIUtils.clearScreen();
        System.out.println("Have a nice day!!\nbyeee :)");
        TUIUtils.clearScreen(1250);
        runProgram = false;
      }
      default -> System.out.println("under production...");
    }

    return runProgram;

  }

}
