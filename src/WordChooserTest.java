import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class WordChooserTest {

  @Test
  void dodgeLetterTestSuccess() {
    String chosenLetter = "c";
    WordChooser wordChooser = DictionaryReader.parseDictionary("src/short_list.txt");
    wordChooser.chosenWord = "copy";
    wordChooser.availableDictionaryWords = new ArrayList<>(
        Arrays.asList("copy", "coby", "ceee", "bcbb", "aaca", "hello"));

    Boolean isDodged = wordChooser.dodgeLetter(chosenLetter);

    Assert.assertTrue(isDodged);
    Assert.assertEquals(1, wordChooser.availableDictionaryWords.size());
    Assert.assertEquals("hello", wordChooser.availableDictionaryWords.get(0));
  }

  @Test
  void dodgeLetterTestFailure() {
    String chosenLetter = "c";
    WordChooser wordChooser = DictionaryReader.parseDictionary("src/short_list.txt");
    wordChooser.chosenWord = "copy";
    wordChooser.availableDictionaryWords = new ArrayList<>(
        Arrays.asList("copy", "coby", "ceee", "bcbb", "aaca"));

    Boolean isDodged = wordChooser.dodgeLetter(chosenLetter);

    Assert.assertFalse(isDodged);
  }

  @Test
  void getWordsWithoutLetterTestNonemptyResponse() {
    List<String> wordsToTest = List.of("icecream", "tacos", "boba", "cookies");
    int expectedLengthWithoutZWords = wordsToTest.size() - 1;
    ArrayList<String> wordsWithoutZ =
        WordChooser.getWordsWithoutLetter("b", wordsToTest);

    Assert.assertEquals(expectedLengthWithoutZWords, wordsWithoutZ.size());
  }

  @Test
  void getWordsWithoutLetterTestEmptyResponse() {
    List<String> wordsToTest = List.of("coil");
    ArrayList<String> wordsWithoutLetter = WordChooser
        .getWordsWithoutLetter(wordsToTest.get(0).substring(0, 1), wordsToTest);

    Assert.assertTrue(wordsWithoutLetter.isEmpty());
  }

  @Test
  void absorbWordLetterPossible() {
    String chosenLetter = "c";
    WordChooser wordChooser = DictionaryReader.parseDictionary("src/short_list.txt");
    wordChooser.chosenWord = "copy";
    wordChooser.availableDictionaryWords = new ArrayList<>(
        Arrays.asList("copy", "coby", "ceee", "bcbb", "aaca"));

    int initialSize = wordChooser.availableDictionaryWords.size();
    wordChooser.absorbWordLetter(chosenLetter);

    int sizeAfterAbsorbing = wordChooser.availableDictionaryWords.size();
    Assert.assertTrue(initialSize > sizeAfterAbsorbing);

  }

  @Test
  void filterByChosenWordLetterPositionTest() {
    String letter = "o";
    String word = "poodle";
    ArrayList<String> availableWordsToFilter = new ArrayList<>(
        Arrays.asList("noodle", "zoodle", "doodle", "orange", "barbie", "coffee", "batter"));
    ArrayList<String> expected = new ArrayList<>(Arrays.asList("noodle", "zoodle", "doodle"));

    ArrayList<String> filteredWords = WordChooser
        .filterByChosenWordLetterPosition(letter, word, availableWordsToFilter);

    Assert.assertEquals(expected, filteredWords);
  }

  @Test
  void getBiggestBucketOfPossibleWordsTest() {
    String letter = "t";
    String chosenWord = "tardy";
    ArrayList<String> availableDictionaryWords = new ArrayList<>(
        Arrays.asList("tardy", "toast", "buton", "aaataa", "bbbtbh", "cccta"));

    ArrayList<String> expected = new ArrayList<>(Arrays.asList("aaataa", "bbbtbh", "cccta"));
    ArrayList<String> currBiggestBucket = WordChooser.getBiggestBucketOfPossibleWords(letter,
        chosenWord, availableDictionaryWords);

    Assert.assertEquals(expected, currBiggestBucket);


  }

}
