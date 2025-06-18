package myapp.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.List;
import java.util.ArrayList;
import org.mindrot.jbcrypt.BCrypt;
import java.io.Console;

public class LoginAndRegister {
  // define ALL USER dir
  private final String DIR_USERS = Paths.get("").toAbsolutePath().toString() + File.separator + "USERS";
  private final String FILE_TEMPORARY = Paths.get("").toAbsolutePath().toString() + File.separator + "tmp";

  public String dirUsers() {
    return DIR_USERS;
  }

  public String fileTemporary() {
    return FILE_TEMPORARY;
  }

  private String userName = "guest";

  private String userPassword = "password";

  public boolean loginAndRegister(Scanner scan) {
    boolean loggedIn = false;

    initilizeRequiredFolderAndFile();

    int selection = loginOrSignupSelection(scan);

    TUIUtils.clearScreen();
    if (selection == 0) {
      System.exit(0);
    }
    if (selection == 2) {
      System.out.println("Sign-Up");
      takeUserNameAndPassword(scan, 2);
      if (createNewUser()) {
        System.out.println("User created successfully!");
        saveUserLogin();
      } else {
        System.out.println("User was not created");
        return loggedIn;
      }
      TUIUtils.clearScreen();
    }

    System.out.println("Log-In");
    takeUserNameAndPassword(scan, 1);
    TUIUtils.clearScreen();
    if (checkCredentialMatch()) {
      saveUserLogin();
      currentlyLoggedInUser();
      loadUserDataManually();
      loggedIn = true;
    } else {
      return loggedIn;
    }
    return loggedIn;
  }

  private void initilizeRequiredFolderAndFile() {
    File USERS = new File(DIR_USERS);
    File lastUserName = new File(DIR_USERS + File.separator + "lastUserName.txt");

    // returning early if the files exists
    if (USERS.exists() && lastUserName.exists()) {
      return;
    }

    if (!USERS.mkdir()) {
      System.out.println("Folder could not be loaded.");
    }
    System.out.println("Users dir loaded");

    try {
      if (!lastUserName.createNewFile()) {
        System.out.println("lastUserName file exists.");
      }
      System.out.println("lastUserName file created");
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("initilizeRequiredFolderAndFile method: sth went wrong");
    }
  }

  private int loginOrSignupSelection(Scanner scan) {

    while (true) {
      System.out.println("(0 to exit)");
      System.out.print("Enter 1 to Log-In and 2 to Sign-Up: ");
      String userInput = scan.nextLine().trim();

      try {
        int choice = Integer.parseInt(userInput);
        if (choice == 0 || choice == 1 || choice == 2) {
          return choice;
        } else {
          wrongUserInputMessege();
        }
      } catch (NumberFormatException nfe) {
        wrongUserInputMessege();
      }
    }

  }

  private boolean createNewUser() {
    boolean created = false;

    File newUser = new File(DIR_USERS + File.separator + userName);
    if (!newUser.mkdir()) {
      System.out.println("That username is taken. Please choose something else!");
      return created;
    }

    File newUserProfile = new File(newUser + File.separator + userName + ".txt");
    File newUserData = new File(newUser + File.separator + userName + "Data.txt");

    try {
      if (newUserProfile.createNewFile() && newUserData.createNewFile()) {
        System.out.println("new user " + userName + " was created");
      } else {
        System.out.println("User could not be created");
      }
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("createNewUser");
    }

    try (
        BufferedWriter bfw = new BufferedWriter(new FileWriter(newUserProfile));) {
      bfw.write("Username=" + userName);
      bfw.newLine();
      bfw.write("Password=" + userPassword);
      bfw.newLine();

      created = true;
    } catch (IOException e) {
      e.printStackTrace();
    }

    return created;
  }

  private boolean checkCredentialMatch() {
    File user = new File(DIR_USERS + File.separator + userName + File.separator + userName + ".txt");
    String line;
    boolean matches = false;

    if (!user.exists()) {
      System.out.println("User not found! Please make an account first.");
      return matches;
    }

    System.out.println("User found! Checking credentials...");

    List<String> userInfo = new ArrayList<>();
    try (BufferedReader bfr = new BufferedReader(new FileReader(user))) {
      while ((line = bfr.readLine()) != null) {
        line = line.substring(line.indexOf("=") + 1);
        userInfo.add(line);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    if (userInfo.get(0).equals(userName) && BCrypt.checkpw(userPassword, userInfo.get(1))) {
      System.out.println("Credentials matches!!!");
      matches = true;
    } else {
      System.out.println("""
          '(＞人＜;)  Yamete!!
          Stop right there, senpai!!!
          Credentials does not match!!!""");

    }
    return matches;
  }

  private boolean loadUserDataManually() {
    File userData = new File(DIR_USERS + File.separator + userName + File.separator + userName + "Data.txt");

    if (!userData.exists()) {
      System.out.println("No user data found!! It might have been curropted or deleted!");
      return false;
    }

    System.out.println("Loading User Data...");
    // will add user data after creating game
    // current plan is the user data will have achivements and stats saved
    System.out.println("User data loaded successfully");
    return true;
  }

  // logOut option
  private void logOut() {

  }

  // log in or signup screen
  private void takeUserNameAndPassword(Scanner scan, int choice) {
    boolean b = false;
    while (!b) {
      System.out.print("Username: ");
      this.userName = scan.nextLine();

      if (userName.length() > 10) {
        System.out.println("Username should not exceed 10 characters!");
      } else if (userName.matches(".*[^a-zA-Z0-9].*")) {
        System.out.println("Please exclude special characters");
      } else {
        b = true;
      }
    }

    // console
    // Console cnsl = System.console();
    // if (cnsl == null) {
    // System.out.println(
    // "No console available");
    // }
    System.out.println("NOTE: Your password will be visible on screen! \nSorry :3");
    if (choice == 2) {
      // cnsl.readPassword("password: ");
      System.out.print("Password: ");
      this.userPassword = BCrypt.hashpw(scan.nextLine(), BCrypt.gensalt());
    } else {
      // cnsl.readPassword("password: ");
      System.out.print("Password: ");
      this.userPassword = scan.nextLine();
    }
  }

  private void saveUserLogin() {
    File lastUserName = new File(DIR_USERS + File.separator + "lastUserName.txt");

    try (FileWriter fw = new FileWriter(lastUserName);) {
      fw.write(userName);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void currentlyLoggedInUser() {
    File tmpFile = new File(FILE_TEMPORARY);

    try (FileWriter fw = new FileWriter(tmpFile)) {
      tmpFile.createNewFile();
      tmpFile.deleteOnExit();
      fw.write(userName);
    } catch (IOException e) {

    }

  }

  private void wrongUserInputMessege() {
    TUIUtils.clearScreen();
    TUIUtils.threadSleep(1000);
    System.out.printf("\"( – ⌓ – )*");
    TUIUtils.threadSleep(2000);
    TUIUtils.clearScreen();
    TUIUtils.threadSleep(1000);
    System.out.printf("IT'S");
    TUIUtils.threadSleep(800);
    System.out.printf(" EITHER");
    TUIUtils.threadSleep(800);
    System.out.printf(" 1");
    TUIUtils.threadSleep(800);
    System.out.printf(" OR");
    TUIUtils.threadSleep(800);
    System.out.printf(" 2");
    TUIUtils.threadSleep(800);
    System.out.printf(" MOTHER");
    TUIUtils.threadSleep(800);
    System.out.printf(" F***ER!!!");
    TUIUtils.threadSleep(1000);
    System.out.println("""

        ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣴⢶⣶⣶⠼⣦⣤⣼⣼⡆⠀⠀⠀⠀⠀⠀⠀⠀
        ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣠⠖⣯⠿⠟⠛⠻⢶⣿⣯⣿⣿⣃⠀⠀⠀⠀⠀⠀⠀⠀
        ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣠⣖⣺⡿⠿⠷⠶⠒⢶⣶⠖⠀⠉⡻⢻⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀
        ⠀⠀⠀⠀⠀⠀⠀⠀⣴⢻⣭⣫⣿⠁⠀⠀⠀⠀⠀⠀⠀⢀⣾⠃⢀⡏⠀⠀⠀⠀⠀⠀⠀⠀⠀
        ⠀⠀⠀⠀⢀⣖⡿⠋⢙⣿⠿⢿⠿⣿⡦⠄⠀⠀⠀⣠⣾⠟⠀⠀⣼⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
        ⠀⠀⢀⣰⣿⣴⣿⡿⠿⠿⠿⢿⣦⣄⠀⠀⠀⣠⣾⣿⠃⠀⢀⣸⡿⣳⣶⣲⡄⠀⠀⠀⠀⠀⠀
        ⠀⠀⣾⣽⡿⣛⣵⠾⠿⠿⠷⣦⣌⠻⣷⣄⢰⣿⠟⠁⠀⢠⣾⠿⢡⣯⠸⠧⢽⣄⠀⠀⠀⠀⠀
        ⠀⢸⡇⡟⣴⡿⢟⣽⣾⣿⣶⣌⠻⣧⣹⣿⡿⠋⠀⠀⠀⣾⠿⡇⣽⣿⣄⠀⠀⠉⠳⣄⢀⡀⠀
        ⠀⢸⠇⢳⣿⢳⣿⣿⣿⣿⣿⣿⡆⢹⡇⣿⡇⠀⡆⣠⣼⡏⢰⣿⣿⣿⣿⣦⠀⠀⠀⠈⠳⣅⠀
        ⠀⣸⡀⢸⣿⢸⣿⣿⣿⣿⣿⣿⡇⣸⡇⣿⡇⠀⡟⣻⢳⣷⣿⣿⣿⣿⣿⣿⠀⠀⠀⠀⠀⠘⣧
        ⢰⡟⡿⡆⠹⣧⡙⢿⣿⣿⠿⡟⢡⣿⢷⣿⣧⠾⢠⣿⣾⣿⣿⣿⣿⣿⣿⠁⠀⠀⠀⠀⠀⠀⠘
        ⠀⠻⡽⣦⠀⠈⠙⠳⢶⣦⡶⠞⢻⡟⡸⠟⠁⢠⠟⠉⠉⠙⠿⣿⣿⣿⣿⠀⠀⠀⠀⠀⠀⠀⡴
        ⠀⠀⢸⣿⡇⠀⠀⣀⣠⠀⢀⡀⠸⣹⠇⠀⣰⡟⡀⠀⠈⠛⠻⢿⣻⣿⡿⠀⠀⠀⠀⠀⠀⡠⠁
        ⠀⠀⢸⣿⣇⣴⢿⣿⣿⣿⣮⣿⣷⡟⠀⣰⣿⢰⠀⣀⠀⠀⠀⢀⣉⣿⡇⠀⠀⠀⠀⠀⣸⠃⠀
        ⠀⠀⢸⣿⡟⣯⠸⣿⣿⣿⣿⢈⣿⡇⣼⣿⠇⣸⡦⣙⣷⣦⣴⣯⠿⠛⢷⡀⠀⠀⠀⣰⡟⠀⠀
        ⠀⠀⠘⣿⣿⡸⣷⣝⠻⠟⢋⣾⣟⣰⡏⣠⣤⡟⠀⠀⠈⠉⠁⠀⠀⠀⠀⢻⣶⠀⢀⣿⠁⠀⠀
        ⠀⠀⠀⢸⡿⣿⣦⣽⣛⣛⣛⣭⣾⣷⡶⠞⠋⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣼⣿⣿⡟⠀⠀⠀⠀
        ⠀⠀⠀⠀⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⡀⠁⢸⢻⠁⠀⠀⠀⠀
        ⠀⠀⠀⠀⡿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣧⣤⣤⣀⣀⣀⣀⣀⣠⣤⠶⠛⠁⢀⣾⡟⠀⠀⠀⠀⠀
        ⠀⠀⠀⠀⢿⣻⣿⣿⣿⣿⣿⣿⣎⣿⡅⠀⠈⠉⠉⠉⠉⠉⠁⠀⠀⠀⠀⣼⣿⠁⠀⠀⠀⠀⠀
        ⠀⠀⠀⠀⠈⢻⣿⣿⣿⣿⣿⣿⣿⣿⣇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣠⡷⠟⠀⠀⠀⠀⠀⠀
        ⠀⠀⠀⠀⠀⠀⠙⢿⣿⣿⠻⢿⣿⣿⣟⣂⣀⣀⣀⣀⣀⣀⣤⠴⠋⠁⣾⠀⠀⠀⠀⠀⠀⠀⠀
        ⠀⠀⠀⠀⠀⠀⠀⠈⢻⣿⣷⣷⡄⠀⠀⠀⠉⠉⠉⠉⠉⠀⠀⠀⢀⡞⠁⠀⠀⠀⠀⠀⠀⠀⠀
        ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠻⣿⣿⡆⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣠⠟⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
        ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⣿⣷⣤⣤⣤⣤⣄⣤⣤⡤⠴⠞⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀

                       """);
    // switch end
    TUIUtils.threadSleep(1000);
  }

}
