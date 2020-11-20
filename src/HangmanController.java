import java.util.Scanner;

public class HangmanController {

  public static void main(String[] args) {
    WordChooser wc = DictionaryReader.parseDictionary("src/engDictionary.txt");
    PlayerProgressManager playerProgressManager =
        new PlayerProgressManager(wc.pickInitialWord(), wc);

    System.out.println("Welcome to *using spooky voice*  you game of hangman, begin if you dare.");

    boolean gameFinished = false;
    int bad_guess_limit = 8;
    int badGuesses = 0;

    while (!gameFinished) {
      Display.displayGame(playerProgressManager.getUserGuessProgress(),
          playerProgressManager.getGuessedLetters());

      System.out.println("Guess a letter\n");
      Scanner scnr = new Scanner(System.in);
      String playerGuess = scnr.next();

      if (!playerProgressManager.handleGuess(playerGuess)) {
        System.out.println("Letter has been guessed already, try again");
        continue;
      }

      if (!playerProgressManager.handleLetter(playerGuess)) {
        System.out.println("Bad Guess, letter not in word");
        badGuesses++;
        if (badGuesses >= bad_guess_limit) {
          System.out.println("Game over u suck bye");
          return;
        }
        continue;
      }

      playerProgressManager.fillInCorrectGuess(playerGuess);

      if (playerProgressManager.checkIfUserGuessMatchesSolution()) {
        gameFinished = true;
      }

    }

    Display.displayGame(playerProgressManager.getUserGuessProgress(),
        playerProgressManager.getGuessedLetters());
    System.out.println("*** Woo you win ***");
  }


}
