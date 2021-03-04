import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class PlayerProgressManager {

  private String wordToGuess;
  private String userGuessProgress;
  private HashMap<Character, Boolean> guessSet;
  private HashMap<Character, Boolean> wordSet;
  private WordChooser wc;

  public String getWordToGuess() {
    return wordToGuess;
  }

  public HashMap<Character, Boolean> getGuessSet() {
    return guessSet;
  }

  public void setGuessSet(HashMap<Character, Boolean> guessSet) {
    this.guessSet = guessSet;
  }

  String getUserGuessProgress() {
    return this.userGuessProgress;
  }

  public void setUserGuessProgress(String userGuessProgress) {
    this.userGuessProgress = userGuessProgress;
  }

  Set<Character> getGuessedLetters() {
    return this.guessSet.keySet();
  }

  /**
   * This constructor takes in the currently chosen word that the player is trying to guess as well
   * as an instance of WordChooser.
   *
   * @param chosenWord
   * @param wc
   */
  PlayerProgressManager(String chosenWord, WordChooser wc) {
    this.wordToGuess = chosenWord;
    this.wc = wc;

    this.userGuessProgress = "";
    for (int i = 0; i < this.wordToGuess.length(); i++) {
      userGuessProgress = userGuessProgress.concat("_");
    }
    //there is a hashmap that holds the set of letters guessed by player
    this.guessSet = new HashMap<>();
    //there is a hashmap that holds the set of letters belonging to a word that is being guessed.
    this.wordSet = new HashMap<>();
    for (Character letter : chosenWord.toCharArray()) {
      this.wordSet.put(letter, true);
    }
  }

  /**
   * The method, fillInCorrectGuess, creates the a string that shows the player their successful
   * guesses for the game.
   *
   * @param wordToGuess
   * @param guessSet
   * @return String   displayed correct guesses
   */
  static String fillInCorrectGuess(
      String wordToGuess,
      HashMap<Character, Boolean> guessSet
  ) {
    StringBuilder builder = new StringBuilder();

    for (int idx = 0; idx < wordToGuess.length(); idx++) {
      if (guessSet.containsKey(wordToGuess.charAt(idx))) {
        builder.append(wordToGuess.charAt(idx));
      } else {
        builder.append("_");
      }
    }
    return builder.toString();
  }


  /**
   * This method evaluates if the guess provided by the player is in the set containing all the
   * guesses existing for the game's entirety
   *
   * @param userChosenLetter
   * @return if guess is successful or not
   */
  Boolean hasPlayerGuessedThisLetter(String userChosenLetter) {
    // if it is already guessed return false
    if (this.guessSet.containsKey(userChosenLetter.charAt(0))) {
      return true;
    }
    this.guessSet.put(userChosenLetter.charAt(0), true);
    return false;
  }

  /**
   *
   * @param userChosenLetter
   * @return true (if       ; false (if
   */
  Boolean handleLetter(String userChosenLetter) {
    if (!this.wordSet.containsKey(userChosenLetter.charAt(0))) {
      // even though the guess didn't work, it still needs to prune the available words
      // we used this letter so if you guessed "q" and it isn't in the word
      // we still have to filter out the available word list for all words with "q"s
      HashSet<String> wordsToRemove = new HashSet<>();
      for (String word : this.wc.availableDictionaryWords) {
        if (word.contains(userChosenLetter)) {
          wordsToRemove.add(word);
        }
      }
      for (String wordToRemove : wordsToRemove) {
        this.wc.availableDictionaryWords.remove(wordToRemove);
      }

      // User guessed a letter that works but we can still dodge it completely
      // this will try to dodge, if it can, it will return false and narrow down word list
      // if it can't then it will return true and update the word list
      return false;
    }

    Boolean dodged = this.wc.dodgeLetter(userChosenLetter);
    // pick a new word from our updated set
    this.wordToGuess = wc.pickWord();

    // update our guess set
    this.wordSet = new HashMap<>();
    for (Character letter : this.wordToGuess.toCharArray()) {
      wordSet.put(letter, true);
    }

    return !dodged;
  }


  /**
   * checkIfUserGuessMatchesSolution
   *
   * @return
   */
  Boolean checkIfUserGuessMatchesSolution() {
    return this.userGuessProgress.equalsIgnoreCase(this.wordToGuess);
  }

}
