import java.util.Set;

public class Display {

  /**
   * A method that will display the dashes for the user to view. Each dash represents the letter the
   * player needs to guess.
   */
  static void displayGame(String userProgressString, Set<Character> guessedLetters) {
    for (Character letter : userProgressString.toCharArray()) {
      System.out.print(letter + " ");

    }
    System.out.println();
    System.out.println("Guessed letters so far: ");
    System.out.println(guessedLetters);
  }
}
