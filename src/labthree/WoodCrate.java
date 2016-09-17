package labthree;

/**
 * WoodCrate class, defines a Crate in the form of a wood crate.
 * 
 * @author  UIN: 2378
 * @version 1.0, 2016-9-16
 *
 */
public class WoodCrate extends Crate {
  
  /**
   * Returns the cost for a wood crate, 1.4 is the cost per lbs.
   * 
   * @return a double value that is the cost for the wood crate
   */
  public double cost() {
    return 1.4 * this.getWeight();
  }
}
