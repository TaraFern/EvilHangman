import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class DictionaryReader {

  /**
   * parseDictionary is a method that takes in a file to read. After it reads in the file, it
   * creates an array list holding all the available words from the dictionary . It also creates a
   * Hashmap that maps an integer (the different lengths a word can have) to an array list of string
   * (these strings all have the same word length and are mapped to the integer.
   * @param file
   *
   */
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
        // while we're adding the word to the array, we add it to the hashmap as well.

        // if we don't have this word length w/ a default array yet then it is created.
        if (!wordLengthToString.containsKey(currentWord.length())) {
          wordLengthToString.put(currentWord.length(), new ArrayList<>());
        }
        //the word gets added to the array to its corresponding length
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
