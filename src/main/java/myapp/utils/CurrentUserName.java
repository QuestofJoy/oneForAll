package myapp.utils;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CurrentUserName {

  LoginAndRegister lar = new LoginAndRegister();
  String DIR_USERS = lar.dirUsers();
  String FILE_TEMPORARY = lar.fileTemporary();

  public String getCurrentUserName() {
    String userName = "user";
    File file = new File(FILE_TEMPORARY);

    if (!file.exists()) {
      System.out.println(
          "it seems you are not logged in which is quiet surprising.\n You are currently using guest account UwU\n");
      TUIUtils.threadSleep(2000);
    }
    try (BufferedReader bfr = new BufferedReader(new FileReader(FILE_TEMPORARY))) {
      userName = bfr.readLine();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return userName;
  }

}
