package homeworkfour;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class FileStats {
  private Scanner input;
  private ArrayList<String> wordList = new ArrayList<String>();
  private HashSet<String> wordSet = new HashSet<String>();
  private ArrayList<Entry<String>> entryList = new ArrayList<Entry<String>>();
  private Map<String, Character> wordToChar = new TreeMap<String, Character>();
  private Map<Character, String> charToWord = new TreeMap<Character, String>();

  private class Entry<T> implements Comparable<Entry<T>> {
    public T word;
    public int frequency;

    public Entry(T word, int frequency) {
      this.word = word;
      this.frequency = frequency;
    }

    public int compareTo(Entry<T> entry) {
      // Unlike usual compareTo methods, this returns -1 if the object the
      // method is called on is greater than the specified one, and 1 if it
      // is less than.
      // This is done to give us descending order instead of ascending order
      // when we use Collections.sort.
      if (this.frequency == entry.frequency) {
        return 0;
      } else if (this.frequency > entry.frequency) {
        return -1;
      } else {
        return 1;
      }
    }
  }

  /**
   * Reads all words in a file and adds them to the wordList and wordSet,
   * then calls the count() method.
   *
   * @param path The path of the text file to read.
   */
  public FileStats(String path) {
    try {
      input = new Scanner(new File(path));
    } catch (FileNotFoundException ex) {
      System.out.printf("Error opening %s.%n", path);
      System.exit(1);
    }

    String line;
    while (input.hasNext()) {
      line = input.nextLine();
      StringTokenizer st = new StringTokenizer(line, ".,?;:'\" \t\n\r\f");
      while (st.hasMoreTokens()) {
        String currentToken = st.nextToken().toLowerCase();
        wordList.add(currentToken);
        wordSet.add(currentToken);
      }
    }
    count();
  }

  /**
   * Creates a new entry in the entryList for every word in the wordSet,
   * using the wordList to count the frequencies of each word, then creates
   * maps to associate them with specific characters.
   */
  private void count() {
    for (String word : wordSet) {
      Entry entry = new Entry(word, Collections.frequency(wordList, word));
      entryList.add(entry);
    }
    Collections.sort(entryList);

    // Create map to use to go from Word to Character
    wordToChar.put(entryList.get(0).word, '#');
    wordToChar.put(entryList.get(1).word, '$');
    wordToChar.put(entryList.get(2).word, '*');
    wordToChar.put(entryList.get(3).word, '%');

    // Create map to use to go from Character to Word.
    charToWord.put('#', entryList.get(0).word);
    charToWord.put('$', entryList.get(1).word);
    charToWord.put('*', entryList.get(2).word);
    charToWord.put('%', entryList.get(3).word);

  }

  public Map<String, Character> getWordToChar() {
    return wordToChar;
  }

  public Map<Character, String> getCharToWord() {
    return charToWord;
  }

  /**
   * Prints out the relationship between encoded and decoded words in the
   * current file.
   */
  public void printDictionary() {
    for (String word : wordToChar.keySet()) {
      System.out.printf("Key/Value: %s/%c%n", word, wordToChar.get(word));
    }
  }
}
