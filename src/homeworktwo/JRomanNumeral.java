package homeworktwo;

import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class JRomanNumeral extends JFrame {
  private JLabel arabicLabel;
  private JLabel romanLabel;
  private JTextField arabicField;
  private JTextField romanField;
  private static int[] romanNumbers = {1000, 900, 500, 400, 100, 90, 50, 40,
      10, 9, 5, 4, 1};
  private static String[] romanLetters = {"M", "CM", "D", "CD", "C", "XC",
      "L", "XL", "X", "IX", "V", "IV", "I"};
  private String lastValidArabic = "";
  private String lastValidRoman = "";

  /**
   * Main method for the program.
   *
   * <p>Creates a JRomanNumeral object, sets its close operation, sets its
   * size with pack, and sets it to be visible.
   *
   * @param args Commandline arguments not used in this program.
   */
  public static void main(String[] args) {
    JRomanNumeral numeralFrame = new JRomanNumeral("Roman <--> Arabic");
    numeralFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    numeralFrame.pack();
    numeralFrame.setVisible(true);
  }

  /**
   * Constructor for the JRomanNumeral class.
   *
   * <p>Sets the title of the window, creates the text fields, and adds
   * the action handler to them.
   *
   * @param windowTitle The title of the window.
   */
  public JRomanNumeral(String windowTitle) {
    super(windowTitle);
    // Set number of rows and columns, as well as vertical and horizontal
    // spacing.
    setLayout(new GridLayout(2, 2, 0, 5));
    Handler handler = new Handler();

    arabicField = new JTextField(12);
    arabicField.addKeyListener(handler);
    romanField = new JTextField(12);
    romanField.addKeyListener(handler);

    arabicLabel = new JLabel("Arabic Numeral");
    romanLabel = new JLabel("Roman Numeral");

    add(arabicLabel);
    add(arabicField);
    add(romanLabel);
    add(romanField);

  }

  private static String arabicToRoman(String arabic) {
    int arabicInt = Integer.parseInt(arabic);

    // Return an empty string if 0 because there is no Roman Numeral for 0.
    if (arabicInt == 0) {
      return "";
    }

    String romanConversion = "";

    // Checks to see if the arabic number is large enough to have part of it
    // be represented by each Roman Numeral, going from largest to smallest,
    // as well as including the common subtraction combinations. After each
    // successful time it decreases the original number by the value of the
    // Roman Numeral.
    for (int i = 0; i < romanLetters.length; i++) {
      while (arabicInt >= romanNumbers[i]) {
        romanConversion += romanLetters[i];
        arabicInt -= romanNumbers[i];
      }
    }
    return romanConversion;
  }

  private static String romanToArabic(String roman) {
    if (roman.length() == 0) {
      return "";
    }

    int arabicNum = 0;
    int index = 0;

    while (index < roman.length()) {
      int current = findNumFromRoman(roman.charAt(index));
      // If it is the last numeral then it cannot be part of a subtractive
      // combination.
      if (index == roman.length() - 1) {
        arabicNum += current;
        index++;
      } else {
        int next = findNumFromRoman(roman.charAt(index + 1));
        // If the next numeral is greater than the current one, then they are
        // a subtractive combination.
        if (next > current) {
          arabicNum += (next - current);
          index += 2;
        } else {
          arabicNum += current;
          index++;
        }
      }
    }

    return Integer.toString(arabicNum);
  }

  private static int findNumFromRoman(char roman) {
    // Simple switch statement to find the arabic value for specific Roman
    // Numerals.
    switch (roman) {
      case 'I':
        return 1;
      case 'V':
        return 5;
      case 'X':
        return 10;
      case 'L':
        return 50;
      case 'C':
        return 100;
      case 'D':
        return 500;
      case 'M':
        return 1000;
      default:
        return 0;
    }
  }

  private class Handler implements KeyListener {
    public void keyReleased(KeyEvent event) {
      char pressedKey = event.getKeyChar();
      if (event.getSource() == romanField) {
        // Verifies that the key pressed is a valid Roman numeral, or it was
        // to delete a previously entered character.
        if (pressedKey == 'M' || pressedKey == 'D' || pressedKey == 'C'
            || pressedKey == 'L' || pressedKey == 'X' || pressedKey == 'V'
            || pressedKey == 'I' || pressedKey == 'm' || pressedKey == 'd'
            || pressedKey == 'c' || pressedKey == 'l' || pressedKey == 'x'
            || pressedKey == 'v' || pressedKey == 'i' || pressedKey == 8
            || pressedKey == 127) {
          // If all numerals are deleted and the result is an empty String,
          // call the romanToArabic method to make that field also empty.
          if (romanField.getText().length() == 0) {
            lastValidRoman = "";
            arabicField.setText(romanToArabic(""));
          } else {
            // Convert all input to uppercase and then update the field to
            // display proper uppercase.
            lastValidRoman = romanField.getText().toUpperCase();
            romanField.setText(lastValidRoman);
            arabicField.setText(romanToArabic(lastValidRoman));
          }
        } else {
          // If it wasn't valid input, restore the text field to the last
          // valid input.
          romanField.setText(lastValidRoman);
        }
      } else {
        // Verifies that an integer was entered or that a previously entered
        // character was deleted.
        if ((pressedKey > 47 && pressedKey < 58) || pressedKey == 8
            || pressedKey == 127) {
          // If all integers are deleted and an empty String results, call
          // the arabicToRoman method with a "0" argument to make that field
          // also empty.
          if (arabicField.getText().length() == 0) {
            lastValidArabic = "";
            romanField.setText(arabicToRoman("0"));
          } else {
            int value = Integer.parseInt(arabicField.getText());
            // Verifies that the number is in the range 1 to 3999 inclusive.
            if (value > 0 && value < 4000) {
              lastValidArabic = arabicField.getText();
              romanField.setText(arabicToRoman(lastValidArabic));
            } else {
              // Set the text to the last valid number if it isn't in range.
              arabicField.setText(lastValidArabic);
            }
          }
        } else {
          // Set the text to the last valid number if the input wasn't a number.
          arabicField.setText(lastValidArabic);
        }
      }
    }

    @Override
    // Dummy method, only need to be able to instantiate an object.
    public void keyPressed(KeyEvent event) {

    }

    @Override
    // Dummy method, only need to be able to instantiate an object.
    public void keyTyped(KeyEvent event) {

    }
  }
}
