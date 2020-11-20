import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class DictionaryReader {

  static WordChooser parseDictionary(String file) {
    File f1 = new File(file);
    ArrayList<String> availableDictionaryWords = new ArrayList<>();
    HashMap<Integer, ArrayList<String>> wordLengthToString = new HashMap<>();

    try {
      //scans in the dictionary of words and adds them to an arraylist
      Scanner fileScnr = new Scanner(f1);
      while (fileScnr.hasNext()) {
        // we have the word
        String currentWord = fileScnr.next();
        availableDictionaryWords.add(currentWord);
        // while we're adding the word to the array, we should add it to the map also

        // if we don't have this word length w/ a default array yet
        if (!wordLengthToString.containsKey(currentWord.length())) {
          wordLengthToString.put(currentWord.length(), new ArrayList<>());
        }
        wordLengthToString.get(currentWord.length()).add(currentWord);

      }
      fileScnr.close();
    } catch (FileNotFoundException e) {
      System.out.println("Error: file not found");
      e.printStackTrace();
    }

    return new WordChooser(availableDictionaryWords, wordLengthToString);
  }
}
