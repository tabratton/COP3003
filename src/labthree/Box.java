package labthree;

import java.util.Scanner;

/**
 * Box class, defines a Package that is in the form of a box
 * 
 * @author  UIN: 2378
 * @version 1.0, 2016-9-16
 *
 */
public class Box implements Package {
  
  /**
   * The current weight of the box in lbs.
   */
  private double weight;
  
  /**
   * Returns the cost for a box, 1.2 is the cost per lbs.
   * 
   * @return a double value that is the cost for the box
   */
  public double cost() {
    return 1.2 * weight;
  }
  
  /**
   * Reads input to get (lbs).
   * 
   * @param scanner A Scanner object for user input.
   */
  public void input(Scanner scanner) {
    System.out.printf("Please input the weight for the Box (lbs) : ");
    this.weight = scanner.nextDouble();
  }
}
