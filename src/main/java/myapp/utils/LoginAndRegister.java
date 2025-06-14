package myapp.utils;

import myapp.utils.TUIUtils;
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

public class LoginAndRegister {
  // define ALL USER dir
  private final String DIR_MAIN = Paths.get("").toAbsolutePath().toString() + File.separator + "USERS";
  private String userName = "guest";

  private String userPassword = "password";

  public boolean loginAndRegister(Scanner scan) {
    boolean loggedIn = false;

    TUIUtils.clearScreen(1500);
    initilizeRequiredFolderAndFile();

    int selection = loginOrSignupSelection(scan);

    TUIUtils.clearScreen();
    if (selection == 2) {
      System.out.println("Sign-Up");
      takeUserNameAndPassword(scan, 2);
      if (createNewUser()) {
        System.out.println("User created successfully!");
        saveUserLogin();
      } else {
        System.out.println("Use was not  created");
        return loggedIn;
      }
      TUIUtils.clearScreen();
    }

    System.out.println("Log-In");
    takeUserNameAndPassword(scan, 1);
    if (checkCredentialMatch()) {
      saveUserLogin();
      loadUserDataManually();
      loggedIn = true;
    } else {
      return loggedIn;
    }
    return loggedIn;
  }

  private void initilizeRequiredFolderAndFile() {
    File USERS = new File(DIR_MAIN);
    File lastUserName = new File(DIR_MAIN + File.separator + "lastUserName.txt");

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
      System.out.print("Enter 1 to Log-In and 2 to Sign-Up: ");
      String userInput = scan.nextLine().trim();

      try {
        int choice = Integer.parseInt(userInput);
        if (choice == 1 || choice == 2) {
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

    File newUser = new File(DIR_MAIN + File.separator + userName);
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
    File user = new File(DIR_MAIN + File.separator + userName + File.separator + userName + ".txt");
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
      System.out.println("Credentials does not match!");
    }
    return matches;
  }

  private boolean loadUserDataManually() {
    File userData = new File(DIR_MAIN + File.separator + userName + File.separator + userName + "Data.txt");

    if (!userData.exists()) {
      System.out.println("No user data found!! It might have been curropted or deleted!");
      return false;
    }

    // will add user data after creating game
    // current plan is the user data will have achivements and stats saved
    System.out.println("Loading User Data...");
    System.out.println("Logged in successfully!!");
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

    if (choice == 2) {
      System.out.print("Password: ");
      this.userPassword = BCrypt.hashpw(scan.nextLine(), BCrypt.gensalt());
    } else {
      System.out.print("Password: ");
      this.userPassword = scan.nextLine();
    }
  }

  private void saveUserLogin() {
    File lastUserName = new File(DIR_MAIN + File.separator + "lastUserName.txt");

    try (FileWriter fw = new FileWriter(lastUserName);) {
      fw.write(userName);
    } catch (IOException e) {
      e.printStackTrace();
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
