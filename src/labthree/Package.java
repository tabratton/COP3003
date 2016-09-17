package labthree;

import java.util.Scanner;

/**
 * Package interface, defines method headers for calculating cost and getting
 *  input.
 * 
 * @author  UIN: 2378
 * @version 1.0, 2016-9-16
 *
 */
public interface Package {
  
  /**
   * Returns the cost for a package.
   * 
   * @return a double value that is the cost for the package
   */
  double cost();
  
  /**
   * Reads input to get (lbs) or (pgs).
   * 
   * @param scanner A Scanner object for user input.
   */
  void input(Scanner scanner);
}
