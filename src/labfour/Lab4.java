package labfour;

import java.awt.BorderLayout;
import javax.swing.JFrame;

public class Lab4 extends JFrame {

  /**
   * Constructor for the Lab4 class.
   *
   * <p>Sets the title of the window, creates a ScreenSaver object, and adds
   * the ScreenSaver object to the JFrame.
   */
  public Lab4() {
    super("Lab4");
    ScreenSaver saver = new ScreenSaver();
    setLayout(new BorderLayout());
    add(saver, BorderLayout.CENTER);
  }

  /**
   * Main method for the program.
   *
   * <p>Creates a Lab4 object, sets its size, makes it resizable, and makes
   * it visible.
   *
   * @param args No commandline arguments for this program.
   */
  public static void main(String[] args) {
    Lab4 lab4 = new Lab4();
    lab4.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    lab4.setSize(600, 600);
    lab4.setResizable(true);
    lab4.setVisible(true);
  }
}
