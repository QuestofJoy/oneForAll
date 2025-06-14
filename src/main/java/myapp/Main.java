package myapp;

import myapp.utils.*;
import myapp.AppsAndGames.*;
import myapp.AppsAndGames.Hangman.Hangman;

import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);

    TUIUtils.clearScreen();

    // login and regester screen
    LoginAndRegister lar = new LoginAndRegister();
    while (!lar.loginAndRegister(scan)) {
    }

    Hangman hangman = new Hangman(scan);

    // close scanner at before exiting main program
    scan.close();
  }
}
