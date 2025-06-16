package myapp.utils;

public class TUIUtils {

  public static void clearScreen() {
    System.out.println("\033[H\033[2J");
    System.out.flush();
  }

  public static void clearScreen(int miliseconds) {
    threadSleep(miliseconds);
    System.out.println("\033[H\033[2J");
    System.out.flush();
  }

  public static void threadSleep(int miliseconds) {
    try {
      Thread.sleep(miliseconds);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
