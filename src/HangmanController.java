import java.util.Scanner;

public class HangmanController {

  public static void main(String[] args) {
    //takes in dictionary words to use
    WordChooser wc = DictionaryReader.parseDictionary("src/engDictionary.txt");
    PlayerProgressManager playerProgressManager =
        new PlayerProgressManager(wc.pickInitialWord(), wc);

    System.out
        .println("Welcome to *using spooky voice*  a casual game of hangman, begin if you dare."
            + "You only have 8 incorrect guesses, so choose wisely.");

    boolean gameFinished = false;
    int bad_guess_limit = 8;
    int badGuesses = 0;
    //run the game
    while (!gameFinished) {
      Display.displayGame(
          playerProgressManager.getUserGuessProgress(),
          playerProgressManager.getGuessedLetters()
      );
      //interact with player to receive their guesses
      System.out.println("Guess a letter\n");
      Scanner scnr = new Scanner(System.in);
      String playerGuess = scnr.next();

      if (playerProgressManager.hasPlayerGuessedThisLetter(playerGuess)) {
        //player has repeated their guess
        System.out.println("Letter has been guessed already, try again");
        continue;
      }
      //player has guessed incorrectly
      if (!playerProgressManager.handleLetter(playerGuess)) {
        System.out.println("Bad Guess, letter not in word");
        //count and increment the incorrect guesses a player than have
        badGuesses++;

        if (badGuesses >= bad_guess_limit) {
          //once the the player reaches the limit of guess, the game is over
          System.out.println("Game over!");
          return;
        }
        continue;
      }

      //handles the display of how much the player has guessed each round
      String guessProgress = PlayerProgressManager.fillInCorrectGuess(
          playerProgressManager.getWordToGuess(),
          playerProgressManager.getGuessSet()
      );
      playerProgressManager.setUserGuessProgress(guessProgress);

      //checks to see if player finished game by guessing all the correct letters in the word
      if (playerProgressManager.checkIfUserGuessMatchesSolution()) {
        gameFinished = true;
      }

    }

    Display.displayGame(playerProgressManager.getUserGuessProgress(),
        playerProgressManager.getGuessedLetters());
    System.out.println("*** Woo you win ***");
  }


}
