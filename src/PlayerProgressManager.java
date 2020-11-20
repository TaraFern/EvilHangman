import java.util.HashMap;
import java.util.HashSet;

public class PlayerProgressManager {

  private String wordToGuess;
  private String userGuessProgress;
  private HashMap<Character, Boolean> guessSet;
  private HashMap<Character, Boolean> wordSet;
  private WordChooser wc;


  PlayerProgressManager(String chosenWord, WordChooser wc) {
    this.wordToGuess = chosenWord;
    this.wc = wc;

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


  // Boolean handleLetter(String userChosenLetter) {
  Boolean handleLetter(String userChosenLetter) {
    if (!this.wordSet.containsKey(userChosenLetter.charAt(0))) {
      // even though the guess didn't work, it still needs to prune the avialable words
      // we used this letter so if you guessed q and it isn't in the word
      // we still have to filter out the available word list for all words with qs
      HashSet<String> wordsToRemove = new HashSet<>();
      for (String word : this.wc.availableDictionaryWords) {
        if (word.contains(userChosenLetter)) {
          wordsToRemove.add(word);
        }
      }
      for (String wordToRemove : wordsToRemove) {
        this.wc.availableDictionaryWords.remove(wordToRemove);
      }

      return false;
    }

    // okay they gussed a thing that works -- can we dodge it completely
    // this will try to dodge, if it can, it will return false and narrow down word list
    // if it can't then it will return true and update the word list
    Boolean dodged = this.wc.dodgeLetter(userChosenLetter);
    // pick a new word from our uopdated set
    this.wordToGuess = wc.pickWord();

    // update our guess set
    this.wordSet = new HashMap<>();
    for (Character letter : this.wordToGuess.toCharArray()) {
      wordSet.put(letter, true);
    }

    return !dodged;
  }

  Boolean checkIfUserGuessMatchesSolution() {
    return this.userGuessProgress.equalsIgnoreCase(this.wordToGuess);
  }

  String getUserGuessProgress() {
    return this.userGuessProgress;
  }

  String getGuessedLetters() {
    return this.guessSet.toString();
  }

}
