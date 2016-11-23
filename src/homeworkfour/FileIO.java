package homeworkfour;

import java.util.Map;

public class FileIO {
  /**
   * Compresses basketball.txt, and then decompresses the previously
   * compressed file.
   *
   * @param args Not used in this program
   */
  public static void main(String[] args) {
    FileStats fs = new FileStats("basketball.txt");
    fs.printDictionary();

    Map<String, Character> m1 = fs.getWordToChar();
    FileCompressor.compress("basketball.txt", "compressed.txt", m1);

    Map<Character, String> m2 = fs.getCharToWord();
    FileCompressor.decompress("compressed.txt", "decompressed.txt", m2);
  }
}
