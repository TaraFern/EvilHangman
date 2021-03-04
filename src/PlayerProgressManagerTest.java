import java.util.HashMap;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class PlayerProgressManagerTest {

  @Test
  void fillInCorrectGuessTest() {
    String wordToTest = "apple";
    HashMap<Character, Boolean> guessSet = new HashMap<>();
    guessSet.put('a', true);
    guessSet.put('c', true);
    guessSet.put('d', true);

    String filledInDisplay = PlayerProgressManager.fillInCorrectGuess(wordToTest, guessSet);
    String expected = "a____";
    Assert.assertEquals(expected, filledInDisplay);
  }

  @Test
  void handleGuessTestAlreadyGuessed() {
    String userChosenLetter = "a";
    HashMap<Character, Boolean> guessSet = new HashMap<>();
    guessSet.put('a', true);
    guessSet.put('c', true);
    guessSet.put('d', true);

    PlayerProgressManager ppm = new PlayerProgressManager(
        "sea",
        DictionaryReader.parseDictionary("src/short_list.txt")
    );
    ppm.setGuessSet(guessSet);

    Boolean result = ppm.hasPlayerGuessedThisLetter(userChosenLetter);
    Assert.assertTrue(result);
  }

  @Test
  void handleGuessTestNotGuessd() {
    String userChosenLetter = "k";
    HashMap<Character, Boolean> guessSet = new HashMap<>();
    guessSet.put('a', true);
    guessSet.put('c', true);
    guessSet.put('z', true);

    PlayerProgressManager ppm =
        new PlayerProgressManager(
            "sea",
            DictionaryReader.parseDictionary("src/short_list.txt")
        );

    ppm.setGuessSet(guessSet);
    Boolean result = ppm.hasPlayerGuessedThisLetter(userChosenLetter);
    Assert.assertFalse(result);
  }

  @Test
  void checkIfUserGuessMatchesSolutionTestTrue() {
    PlayerProgressManager ppm =
        new PlayerProgressManager(
            "sea",
            DictionaryReader.parseDictionary("src/short_list.txt")
        );

    ppm.setUserGuessProgress("sea");
    Assert.assertTrue(ppm.checkIfUserGuessMatchesSolution());
  }

  @Test
  void checkIfUserGuessMatchesSolutionTestFalse() {
    PlayerProgressManager ppm =
        new PlayerProgressManager(
            "sea",
            DictionaryReader.parseDictionary("src/short_list.txt")
        );
    ppm.setUserGuessProgress("se");
    Assert.assertFalse(ppm.checkIfUserGuessMatchesSolution());
  }

}
