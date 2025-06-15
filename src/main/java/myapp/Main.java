package myapp;

import myapp.utils.*;
import myapp.AppsAndGames.*;
import myapp.AppsAndGames.Hangman.Hangman;
import myapp.AppsAndGames.ToDoList.ToDoList;

import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);

    TUIUtils.clearScreen();

    // login and regester screen
    LoginAndRegister lar = new LoginAndRegister();
    while (!lar.loginAndRegister(scan)) {
    }

    selector();

    int selection = scan.nextInt();

    switch (selection) {
      case 1 -> new ToDoList(scan);
      case 2 -> new Hangman(scan);
    }

    // close scanner at before exiting main program
    scan.close();
  }

  private static void selector() {
    System.out.println("What program do you want to use today?");
    System.out.println("--------------------------------------");
    System.out.println("1. To-Do-List");
    System.out.println("2. Hangman");
    System.out.print("Enter your selection: ");
  }
}
