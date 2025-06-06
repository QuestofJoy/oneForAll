package myapp.utils;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;

public class DirInitializer {
  private static final String DIR_CURRENT_PATH = Paths.get("").toAbsolutePath().toString();
  public static final String DIR_USER_DATA_PATH = DIR_CURRENT_PATH + File.separator + "userData";

  public static void UserDirInitializer() {
    File file = new File(DIR_USER_DATA_PATH);
    boolean created = false;
    if (file.exists()) {
      created = true;
    }

    if (!created) {
      if (file.mkdir()) {
        System.out.println("user folder created");
      } else {
        System.out.println("user folder could not be created");
      }
    }

  }

  // this was made for auto login
  // public static boolean initializeUserProfile() {
  // boolean loggedIn = false;
  // File userData = new File(DIR_USER_DATA_PATH);
  //
  // String names = userData.listFiles().toString();
  // System.out.println(names);
  // return loggedIn;
  // }
  //

  public static void makeUserProfile(String userName, String userPassword) {
    String userProfileFileName = userName + ".txt";

    File userProfile = new File(DIR_USER_DATA_PATH + File.separator + userProfileFileName);

    // this boolean is to check if the user exists before creating a new user
    boolean userExists = false;
    try {
      if (!userProfile.createNewFile()) {
        System.out.println("user exists!!");
        userExists = true;
      } else {
        System.out.println("user created");
      }
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("user could not be created");
    }

    // if the user did not orignally exist write to the newly created userProfile
    if (!userExists) {
      try (FileWriter fw = new FileWriter(userProfile, true)) {
        fw.write("userName=" + userName + System.lineSeparator());
        fw.write("userPassword=" + userPassword + System.lineSeparator());

      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  public static void loadUserProfile(String userName, String userPassword) {
    String userProfileFileName = userName + ".txt";
    File userProfile = new File(DIR_USER_DATA_PATH + File.separator + userProfileFileName);

    List<String> userInfo = new ArrayList<>();

    // checking if the user profile exists
    if (!userProfile.exists()) {
      System.out.println("user not found");
      return;
    }

    // pulling out username and password of the existing user
    try (BufferedReader bfr = new BufferedReader(new FileReader(userProfile))) {
      String line;
      while ((line = bfr.readLine()) != null) {
        line = line.substring(line.indexOf("=") + 1);
        userInfo.add(line);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

    if (userName.matches(userInfo.get(0)) && userPassword.matches(userInfo.get(1))) {
      System.out.println("successfully loaded user profile");
    } else {
      System.out.println("invalid password");
    }

  }

  // public boolean searchUserProfile() {
  // boolean found = false;
  // }
  //
  // public boolean makeUserProfile() {
  // boolean created = false;
  // File newUserProfile = new File(DIR_USER_DATA + File.separator + "User");
  // return created;
  // }
  //
}
