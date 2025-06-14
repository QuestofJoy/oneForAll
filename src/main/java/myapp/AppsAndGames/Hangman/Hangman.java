package myapp.AppsAndGames.Hangman;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;

import myapp.AppsAndGames.AppsAndGamesInterface;
import myapp.utils.TUIUtils;

public class Hangman implements AppsAndGamesInterface {

  private List<String> words = new ArrayList<>();
  private List<String> hints = new ArrayList<>();

  private char blanks[] = new char[20];

  public Hangman(Scanner scan) {
    start();
    // Initializing words and hints
    initializeList();
    runGame(scan);
  }

  private void runGame(Scanner scan) {
    TUIUtils.clearScreen(1000);
    int tries = 5;

    // generating random number
    Random random = new Random(System.currentTimeMillis());
    int randomNumber = random.nextInt(words.size());

    // Setting up variables
    String userWord = words.get(randomNumber);
    String userHint = hints.get(randomNumber);
    int userWordLength = userWord.length();
    createBlanks(userWordLength);
    boolean wordFound = false;

    while (tries > 0 && !wordFound) {
      // Taking user input
      // I'm directly printing hints because that wont be used again after this
      head();
      System.out.println("Hint for your word is:\n" + userHint);
      System.out.println();
      System.out.print(blanks);
      System.out.print("\nEnter your guess: ");
      char userInputChar = scan.next().charAt(0);

      // checking if the userWord has the char input given by the user
      boolean charFound = inputChecker(userWord, userWordLength, userInputChar);

      // why the fuck is this if statement not working????
      // i tried equals i tried maches
      // left to try shits: string builder,
      if (new String(blanks).equalsIgnoreCase(userWord)) {
        System.out.println("Congratulations!! You have found the word!!!");
        wordFound = true;
      }

      TUIUtils.clearScreen();
      System.out.println(new String(blanks));
      System.out.println(userWord);
      if (charFound) {
        System.out.println("Guess Correct!! UwU\n");
      } else {
        System.out.println("Guess Incorrect!!! TuT\n");
        tries--;
      }

      // check if the word is found

    }
    if (tries == 0) {
      System.out.println("get shitted on looser");
    }
  }

  private void head() {
    System.out.println("----------------------------");
    System.out.println("        Hangman game");
    System.out.println("----------------------------");
  }

  private void initializeList() {

    // Add Words
    words.add("HelloWorld");
    words.add("Elephant");
    words.add("Java");
    words.add("user");

    // Add Hints
    hints.add("First words of a programmer");
    hints.add("Largest land mammal.");
    hints.add("What was this program written on?");
    hints.add("Who are you?");

  }

  private void createBlanks(int userWordLength) {
    for (int i = 0; i < userWordLength; i++) {
      blanks[i] = '_';
    }
  }

  private boolean inputChecker(String userWord, int userWordLength, char userInputChar) {
    boolean found = false;

    for (int i = 0; i < userWordLength; i++) {
      if (userInputChar == userWord.charAt(i)) {
        blanks[i] = userWord.charAt(i);
        found = true;
      }
    }
    return found;
  }

  @Override
  public void start() {
    System.out.println("Starting Hangman");
  }

}
