package labthree;

/**
 * MetalCrate class, defines a Crate that is in the form of a metal crate.
 * 
 * @author  UIN: 2378
 * @version 1.0, 2016-9-16
 *
 */
public class MetalCrate extends Crate {
  
  /**
   * Returns the cost for a metal crate, 1.3 is the cost per lbs.
   * 
   * @return a double value that is the cost for the metal crate
   */
  public double cost() {
    return 1.3 * this.getWeight();
  }
}
