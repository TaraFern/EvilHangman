import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class WordChooser {

  ArrayList<String> availableDictionaryWords;
  HashMap<Integer, ArrayList<String>> wordLengthToString;
  String chosenWord;

  /**
   * Constructor for WordChooser
   *
   * @param availableDictionaryWords
   * @param wordLengthToString
   */
  WordChooser(
      ArrayList<String> availableDictionaryWords,
      HashMap<Integer, ArrayList<String>> wordLengthToString
  ) {
    this.availableDictionaryWords = availableDictionaryWords;
    this.wordLengthToString = wordLengthToString;
  }

  /**
   * Evaluates whether the game can avoid using the letter guessed by the player at all to make the
   * difficulty increased
   *
   * @param letter
   */

  Boolean dodgeLetter(String letter) {
    ArrayList<String> wordsWithoutLetter = getWordsWithoutLetter(letter,
        this.availableDictionaryWords);

    // this means we can dodge it and pick a new word from the new set
    if (!wordsWithoutLetter.isEmpty()) {
      this.availableDictionaryWords = wordsWithoutLetter;
      return true;
    }

    this.absorbWordLetter(letter);
    return false;
  }

  /**
   * This function pulls out any words that have the letter guessed by the player so that the
   * player's guess cannot be applied successfully
   *
   * @param letter
   * @param availableDictionaryWords
   * @return
   */
  static ArrayList<String> getWordsWithoutLetter(
      String letter,
      List<String> availableDictionaryWords
  ) {
    ArrayList<String> wordsWithoutLetter = new ArrayList<>();
    for (String word : availableDictionaryWords) {
      if (!word.contains(letter)) {
        wordsWithoutLetter.add(word);
      }
    }
    return wordsWithoutLetter;
  }

  /**
   * This method uses the getBiggestBucketOfPossibleWords a helper function, as well as the pickWord
   * helper function to randomly chose a word for the player to guess after the player has finally
   * reached a letter option that must be placed in the evolving guess.
   *
   * @param letter
   */
  void absorbWordLetter(String letter) {
    this.availableDictionaryWords = getBiggestBucketOfPossibleWords(letter, this.chosenWord,
        this.availableDictionaryWords);

    this.chosenWord = this.pickWord();

    // walk where the letter exists and filter out our other words
    this.availableDictionaryWords =
        WordChooser.filterByChosenWordLetterPosition(letter, this.chosenWord,
            this.availableDictionaryWords);
  }

  /**
   * The method takes the letter that is guessed, and then looks through the remaining available
   * words. It loops through each word and assigns the words to an array list based on where the
   * guessed letter resides on the index. For example, words with "a" at index 0 will be assigned to
   * an array list, words with "a" at index 1 will be assigned to an arraylist ...and so on. From
   * these buckets, the method will return a list of words from the largest existing bucket (aka
   * largest amount of word possibilities).
   *
   * @param letter
   * @param chosenWord
   * @param availableDictionaryWords
   * @return currBiggestBucket;
   */

  static ArrayList<String> getBiggestBucketOfPossibleWords(
      String letter,
      String chosenWord,
      ArrayList<String> availableDictionaryWords
  ) {
    // create our letter index and fill up the buckets with the default empty array
    HashMap<Integer, ArrayList<String>> letterIndexToWordList = new HashMap<>();
    for (int i = 0; i < chosenWord.length(); i++) {
      letterIndexToWordList.put(i, new ArrayList<>());
    }

    // group words into buckets based on where the index of the letter is
    for (String word : availableDictionaryWords) {
      ArrayList<String> wordsWithLetterAtCurrIndex = letterIndexToWordList.get(word.indexOf(letter));
      wordsWithLetterAtCurrIndex.add(word);
    }

    ArrayList<String> currBiggestBucket = new ArrayList<>();
    for (ArrayList<String> currBucket : letterIndexToWordList.values()) {
      if (currBucket.size() > currBiggestBucket.size()) {
        currBiggestBucket = currBucket;
      }
    }
    return currBiggestBucket;
  }

  /**
   * Filters the available word list, ensuring that the current word's letter positions are
   * preserved for all future guessable words, and removing any that don't fit that pattern.
   * Additionally, the words going into this method are expected to be the same size because at the
   * begging of the program we only select words of a particular length.
   * <p>
   * For example if the word is poodle and the user guessed o and we have to absorb it. We then also
   * need to ensure all future guessable words have 2 Os at the 1st and 2nd position: _ o o _ _ _
   *
   * @param letter
   * @param wordWithLetter
   * @param availableDictionaryWords
   * @return filteredWords
   */
  static ArrayList<String> filterByChosenWordLetterPosition(
      String letter,
      String wordWithLetter,
      ArrayList<String> availableDictionaryWords
  ) {
    HashSet<String> wordsToRemove = new HashSet<>();
    HashSet<Integer> indexesOfLetterinChoosenWord = new HashSet<>();

    // find all indexes in chosen word where the letter i exists
    for (int idx = 0; idx < wordWithLetter.length(); idx++) {
      if (wordWithLetter.charAt(idx) == letter.charAt(0)) {
        indexesOfLetterinChoosenWord.add(idx);
      }
    }

    // walk through all available dictionary words
    for (String availableWord : availableDictionaryWords) {

      // ensure the letter the current words guessed letter positions are preserved for all
      // future words.
      for (int i = 0; i < wordWithLetter.length(); i++) {

        if (indexesOfLetterinChoosenWord.contains(i)) {
          // if this is an index that is supposed to contain the current letter
          if (availableWord.charAt(i) != letter.charAt(0)) {
            wordsToRemove.add(availableWord);
            break;
          }
        } else {
          if (availableWord.charAt(i) == letter.charAt(0)) {
            wordsToRemove.add(availableWord);
            break;
          }
        }
      }

    }

    ArrayList<String> filteredWords = new ArrayList<>();
    for (String word : availableDictionaryWords) {
      if (!wordsToRemove.contains(word)) {
        filteredWords.add(word);
      }
    }

    return filteredWords;
  }

  /**
   * pickWord takes in an arraylist and randomly chooses a word from the list, this word is what
   * user will guess during the game of hangman.
   *
   * @return String , this is a randomly chosen word
   */
  String pickInitialWord() {
    Random random = new Random();
    String chosenWord = this.availableDictionaryWords
        .get(random.nextInt(availableDictionaryWords.size()));
    this.availableDictionaryWords = this.wordLengthToString.get(chosenWord.length());
    this.chosenWord = chosenWord;
    return chosenWord;
  }


  /**
   * pickWord takes in an arraylist and randomly chooses a word from the list, this word is what
   * user will guess during the game of hangman.
   *
   * @return String , this is a randomly chosen word
   */
  String pickWord() {
    Random random = new Random();
    String chosenWord = this.availableDictionaryWords
        .get(random.nextInt(availableDictionaryWords.size()));
    this.chosenWord = chosenWord;
    return chosenWord;
  }

}


