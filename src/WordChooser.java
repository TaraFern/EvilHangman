import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class WordChooser {

  String file;
  ArrayList<String> dictionaryWords;

  /**
   * Constructor for Wordchooser - this constructor takes in a file and creates an array list of the
   * individual words.
   *
   * @param String file , takes a file name to
   */
  WordChooser(String file) {
    this.file = "src/engDictionary.txt";
    File f1 = new File(file);
    this.dictionaryWords = new ArrayList<>();

    try {
      //scans in the dictionary of words and adds them to an arraylist
      Scanner fileScnr = new Scanner(f1);
      while (fileScnr.hasNext()) {
        this.dictionaryWords.add(fileScnr.next());
      }
      fileScnr.close();
    } catch (FileNotFoundException e) {
      System.out.println("Error: file not found");
      e.printStackTrace();
    }
  }

  /**
   * pickWord takes in an arraylist and randomly chooses a word from the list, this word is what
   * user will guess during the game of hangman.
   *
   * @return String , this is a randomly chosen word
   */
  String pickWord() {
    Random random = new Random();
    return this.dictionaryWords.get(random.nextInt(dictionaryWords.size()));
  }

//  char[] getWordAsArray(String word) {
//    String chosenWord = this.pickWord();
//    char[] chosenWordAsArray = new char[chosenWord.length()];
//    for (int i = 0; i < chosenWord.length(); i++) {
//      chosenWordAsArray[i] = chosenWord.charAt(i);
//    }
//    return chosenWordAsArray;
//  }

  public static void main(String[] args) {
    //just testing if file was read

//    WordChooser wc = new WordChooser("src/engDictionary.txt");
//    System.out.println(wc.dictionaryWords);
//    System.out.println("\n");
//    System.out.println(wc.pickWord(dictionaryWords));

  }


}


