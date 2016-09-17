package labthree;

import java.util.Scanner;

/**
 * Letter class, defines a Package that is in the form of a letter.
 * 
 * @author  UIN: 2378
 * @version 1.0, 2016-9-16
 *
 */
public class Letter implements Package {
  
  /**
   * The current number of pages in the letter.
   */
  private int numOfPages;
  
  /**
   * Returns the cost for a letter, 0.05 is the cost per page.
   * 
   * @return a double value that is the cost for the letter
   */
  public double cost() {
    return 0.05 * numOfPages;
  }
  
  /**
   * Reads input to get (pgs).
   * 
   * @param scanner A Scanner object for user input.
   */
  public void input(Scanner scanner) {
    System.out.printf("Please input the number of pages for the letter (pgs) :"
            + " ");
    this.numOfPages = scanner.nextInt();
  }
}
