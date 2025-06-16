package myapp;

import myapp.utils.*;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);

    TUIUtils.clearScreen();

    // login and regester screen
    LoginAndRegister lar = new LoginAndRegister();

    while (!lar.loginAndRegister(scan)) {
    }

    while (ProgramMenus.programSelection(scan)) {
      TUIUtils.clearScreen();
    }

    // close scanner at before exiting main program
    scan.close();
  }

}
