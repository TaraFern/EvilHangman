import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class WordChooser {

  ArrayList<String> availableDictionaryWords;
  HashMap<Integer, ArrayList<String>> wordLengthToString;
  String chosenWord;

  /**
   * Constructor for Wordchooser - this constructor takes in a file and creates an array list of the
   * individual words.
   */
  WordChooser(
      ArrayList<String> availableDictionaryWords,
      HashMap<Integer, ArrayList<String>> wordLengthToString
  ) {
    this.availableDictionaryWords = availableDictionaryWords;
    this.wordLengthToString = wordLengthToString;

  }

  Boolean dodgeLetter(String letter) {
    ArrayList<String> wordsWithoutLetter = new ArrayList<>();
    for (String word : this.availableDictionaryWords) {
      if (!word.contains(letter)) {
        wordsWithoutLetter.add(word);
      }
    }

    // this means we can dodge it and pick a new word from the new set
    if (!wordsWithoutLetter.isEmpty()) {
      this.availableDictionaryWords = wordsWithoutLetter;
      return true;
    }

    this.absorbWordLetter(letter);
    return false;
  }

  void absorbWordLetter(String letter) {



    // create our letter index and fill up the buckets with the default empty array
    HashMap<Integer, ArrayList<String>> letterIndexToWordList = new HashMap<>();
    for (int i = 0; i < this.chosenWord.length(); i++) {
      letterIndexToWordList.put(i, new ArrayList<>());
    }

    // group words into buckets based on where the index of the letter is
    for (String word : this.availableDictionaryWords) {
      ArrayList<String> wordsWithLetterAtCurrIndex = letterIndexToWordList
          .get(word.indexOf(letter));
      wordsWithLetterAtCurrIndex.add(word);
    }

    // find our max bucket size
    //TODO: if the case is we say have 3 words left and say they are unorthodoxy, bloodhounds cloudbursts
    // and our current word is unorthodcy, we have all buckets of 1, and we happen to pick unothordxy b/c thats the first bucket
    // is that right? or should we pick any bucket besides the unorthodoxy
    ArrayList<String> currBiggestBucket = new ArrayList<>();
    for (ArrayList<String> currBucket : letterIndexToWordList.values()) {
      if (currBucket.size() > currBiggestBucket.size()) {
        currBiggestBucket = currBucket;
      }
    }

    // we want to filter the biggest bucket and ensure our already letter spots
    // are the same in each word
    // that means they got _ o o _ _ _
    // now we that we know we can't dodge, we need to ensure all available words
    // also have os in the first and second slots
    this.availableDictionaryWords = currBiggestBucket;

    this.chosenWord = this.pickWord();
    // walk where the letter exists and filter out our other words
    HashSet<String> wordsToRemove = new HashSet<>();
    HashSet<Integer> indexesOfLetterinChoosenWord = new HashSet<>();

    // find all indexes in choosen word where the letter i exists
    // TODO: also need to do the inverse and ensure the letter i doesn't exist at the other spots

    for (int idx = 0; idx < this.chosenWord.length(); idx++) {
      if (chosenWord.charAt(idx) == letter.charAt(0)) {
        indexesOfLetterinChoosenWord.add(idx);
      }
    }

    // for mimics and i indexes of letter contains 1 and 3

    for (String availableWord: this.availableDictionaryWords) {

      for (int i = 0; i <this.chosenWord.length(); i++) {

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

    for (String wordToRemove: wordsToRemove) {
      this.availableDictionaryWords.remove(wordToRemove);
    }
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


