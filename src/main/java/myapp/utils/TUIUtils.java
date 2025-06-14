package myapp.utils;

import java.util.Scanner;

public class TUIUtils {

  public static void clearScreen() {
    System.out.println("\033[H\033[2J");
    System.out.flush();
  }

  public static void clearScreen(int seconds) {
    threadSleep(seconds);
    System.out.println("\033[H\033[2J");
    System.out.flush();
  }

  public static void threadSleep(int seconds) {
    try {
      Thread.sleep(seconds);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
