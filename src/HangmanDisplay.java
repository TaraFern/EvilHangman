import java.util.HashMap;

public class HangmanDisplay {

  String wordToGuess;
  String userGuessProgress;
  HashMap<Character, Boolean> guessSet;
  HashMap<Character, Boolean> wordSet;


  HangmanDisplay(String chosenWord) {
    this.wordToGuess = chosenWord;

    this.userGuessProgress = "";
    for (int i = 0; i < this.wordToGuess.length(); i++) {
      userGuessProgress = userGuessProgress.concat("_");
    }

    this.guessSet = new HashMap<>();

    this.wordSet = new HashMap<>();
    for (Character letter : chosenWord.toCharArray()) {
      this.wordSet.put(letter, true);
    }
  }

  /**
   * A method that will display the dashes for the user to view. Each dash represents the letter the
   * player needs to guess.
   */
  void displayGame() {
    for (Character letter : this.userGuessProgress.toCharArray()) {
      System.out.print(letter + " ");

    }
    System.out.println();
    System.out.println("Guessed letters so far: ");
    System.out.println(this.getGuessedLetters());
  }

  void fillInCorrectGuess(String letter) {
    StringBuilder builder = new StringBuilder();

    for (int idx = 0; idx < this.wordToGuess.length(); idx++) {
      if (this.guessSet.containsKey(this.wordToGuess.charAt(idx))) {
        builder.append(this.wordToGuess.charAt(idx));
      } else {
        builder.append("_");
      }
    }
    this.userGuessProgress = builder.toString();
  }


  Boolean handleGuess(String userChosenLetter) {
    if (this.guessSet.containsKey(userChosenLetter.charAt(0))) {
      return false;
    }
    this.guessSet.put(userChosenLetter.charAt(0), true);
    return true;
  }


  Boolean handleLetter(String userChosenLetter) {
    if (!this.wordSet.containsKey(userChosenLetter.charAt(0))) {
      return false;
    }
    this.wordSet.put(userChosenLetter.charAt(0), true);

    return true;
  }

  Boolean isUserWordComplete() {
    return this.userGuessProgress.equalsIgnoreCase(this.wordToGuess);
  }

  String getGuessedLetters() {
    return this.guessSet.toString();
  }
}
