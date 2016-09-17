package labthree;

import java.util.Scanner;

/**
 * Crate class, defines a Package that is in the form of a Crate.
 * 
 * @author  UIN: 2378
 * @version 1.0, 2016-9-16
 *
 */
public abstract class Crate implements Package {
  
  /**
   * The current weight of the crate in lbs.
   */
  private double weight;
  
  /**
   * Gets the current weight of the crate.
   * 
   * @return the weight of the crate
   */
  public double getWeight() {
    return weight;
  }
  
  /**
   * Reads input to get (lbs).
   * 
   * @param scanner A Scanner object for user input.
   */
  public void input(Scanner scanner) {
    System.out.printf("Please input the weight for the %s (lbs) : ", 
            this.getClass().getSimpleName());
    this.weight = scanner.nextDouble();
  }
}
