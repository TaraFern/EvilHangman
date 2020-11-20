import java.util.Scanner;

public class HangmanController {

  public static void main(String[] args) {
    WordChooser wc = new WordChooser("src/engDictionary.txt");
    String pickedWord = wc.pickWord();
    HangmanDisplay display = new HangmanDisplay(pickedWord);

    System.out.println("Welcome to *using spooky voice*  EVIL hangman");

    boolean gameFinished = false;
    int bad_guess_limit = 8;
    int badGuesses = 0;

    while (!gameFinished) {
      display.displayGame();

      System.out.println("Guess a letter\n");
      Scanner scnr = new Scanner(System.in);
      String playerGuess = scnr.next();

      if (!display.handleGuess(playerGuess)) {
        System.out.println("Letter has been guessed already, try again");
        continue;
      }

      if (!display.handleLetter(playerGuess)) {
        System.out.println("Bad Guess, letter not in word");
        badGuesses++;
        if (badGuesses >= bad_guess_limit) {
          System.out.println("Game over u suck bye");
          return;
        }
        continue;
      }

      // walk and fill in for display
      display.fillInCorrectGuess(playerGuess);

      if (display.isUserWordComplete()) {
        gameFinished = true;
      }

    }

    display.displayGame();
    System.out.println("*** Woo you win ***");
  }


}
