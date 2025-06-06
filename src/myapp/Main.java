package myapp;

import myapp.utils.*;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);

    TUIUtils.clearScreen();

    DirInitializer.UserDirInitializer();
    // let user choose to make new user or sign in to existing one

    // check for existing user;
    // user should be on a single text file inside userData/
    int userChoice = TUIUtils.loginOrSignupScreen(scan);
    scan.nextLine();

    TUIUtils.clearScreen();
    if (userChoice == 1)
      System.out.println("Please refrain from using white-space \" \" or any special keywords\n");
    System.out.printf("Username: ");
    String userName = scan.nextLine();
    System.out.printf("Password: ");
    String userPassword = scan.nextLine();

    // the code commented below is useless as of now
    // User user = new User(userName, userPassword);

    switch (userChoice) {
      case 1 -> DirInitializer.loadUserProfile(userName.strip(), userPassword.strip());
      case 2 -> DirInitializer.makeUserProfile(userName.strip(), userPassword.strip());
      default -> System.out.println("something along the line has gone wrong and i dont know what it is");
      // cauz there is nothing that could break
    }

    // DirInitializer.initializeUserProfile();
    // close scanner at before exiting main program
    scan.close();
  }
}
