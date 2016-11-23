package homeworkfour;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Scanner;

class FileCompressor {
  private static Scanner input;

  /**
   * Compresses a given text file by replacing the four most common words in
   * the file.
   *
   * @param src         The file to compress.
   * @param dest        The file that will store the decompressed version.
   * @param dictionary  The four most common words and their encoded versions.
   */
  public static void compress(String src, String dest,
                              Map<String, Character> dictionary) {
    setUpScanner(src);
    String fileString = input.next().toLowerCase();

    // [ ] in regex to prevent matching of sub strings
    // inside of words, such as the "the" in "another" (Not a perfect
    // solution since the sub word could be at the end of the word, or it may
    // be followed by punctuation instead of a space).
    for (String word : dictionary.keySet()) {
      // Have to escape $ and * due to their use in regex.
      if (dictionary.get(word).equals('$')) {
        fileString = fileString.replaceAll(word + "[ ]",
            String.format("\\%c ", dictionary.get(word)));
      } else {
        fileString = fileString.replaceAll(word + "[ ]",
            String.format("%c ", dictionary.get(word)));
      }
    }

    writeFile(dest, fileString);

  }

  /**
   * Decompresses a given file by replacing the encoded characters of the
   * four most common words with the original words.
   *
   * @param src         The file to be decompressed.
   * @param dest        The file that will store the uncompressed version.
   * @param dictionary  The four most common words and their encoded versions.
   */
  public static void decompress(String src, String dest,
                                Map<Character, String> dictionary) {
    setUpScanner(src);
    String fileString = input.next().toLowerCase();

    // [ ] in regex to prevent matching of sub strings
    // inside of words, such as the "the" in "another" (Not a perfect
    // solution since the sub word could be at the end of the word, or it may
    // be followed by punctuation instead of a space).
    for (Character character : dictionary.keySet()) {
      // Have to escape $ and * due to their use in regex.
      if (character.equals('$') || character.equals('*')) {
        fileString = fileString.replaceAll("\\" + character + "[ ]",
            String.format("%s ", dictionary.get(character)));
      } else {
        fileString = fileString.replaceAll(character + "[ ]",
            String.format("%s ", dictionary.get(character)));
      }
    }

    writeFile(dest, fileString);

  }

  private static void setUpScanner(String src) {
    try {
      input = new Scanner(new File(src));
    } catch (FileNotFoundException ex) {
      System.out.printf("Error opening %s.%n", src);
      System.exit(1);
    }

    input.useDelimiter("\\Z");
  }

  private static void writeFile(String dest, String stringToWrite) {
    try (Writer writer = new BufferedWriter(new OutputStreamWriter(new
        FileOutputStream(dest), StandardCharsets.UTF_8))) {
      writer.write(stringToWrite);
    } catch (IOException ex) {
      System.out.printf("Error creating %s.%n", dest);
      System.exit(1);
    }
  }
}
