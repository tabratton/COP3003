package labfour;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.JPanel;
import javax.swing.Timer;

public class ScreenSaver extends JPanel implements ActionListener,
    MouseWheelListener {
  // Stores the x positions of the points of the spiral.
  private int[] x = new int[60];
  // Stores the y positions of the points of the spiral.
  private int[] y = new int[60];
  // Stores the current number of points in the spiral.
  private int numOfPoints = 0;
  // Stores the current radius of the spiral.
  private int radius = 10;
  // The initial delay in milliseconds that is used for the timer.
  private int delay = 1000;
  // The Timer object that will repeatedly invoke the actionPerformed method.
  private Timer timer = null;
  // The maximum delay for the timer in milliseconds.
  private static final int MAX_DELAY = 5000;
  // The minimum delay for the timer in milliseconds.
  private static final int MIN_DELAY = 100;
  // Constant to adjust how much the delay is changed by each mouse wheel
  // "click," since we want it to change by more than 1 each time.
  private static final int ROTATION_ADJUSTMENT_FACTOR = 100;

  /**
   * Constructor for the ScreenSaver class.
   *
   * <p>Creates a new timer object, starts the timer, and adds itself as a
   * mouseWheelListener since the mouseWheelMoved method is in this class.
   */
  public ScreenSaver() {
    timer = new Timer(delay, this);
    timer.start();
    addMouseWheelListener(this);
  }

  /**
   * Adds a point to the spiral and calls the repaint method, which
   * indirectly invokes the paintComponent method each time the timer calls
   * the method.
   *
   * @param e Not used in the method.
   */
  public void actionPerformed(ActionEvent e) {
    addAPoint();
    repaint();
  }

  /**
   * Clears the current spiral and then redraws it with the current points.
   *
   * @param g The Graphics object that is being used to display the spiral.
   */
  public void paintComponent(Graphics g) {
    g.clearRect(0, 0, (int) getSize().getWidth(), (int) getSize().getHeight());
    g.drawPolyline(x, y, numOfPoints);
  }

  /**
   * Listens for each time the mouse wheel is moved and then adjusts the
   * delay of the timer according to which direction the mouse wheel was
   * moved in.
   *
   * @param e Contains information about the event the called the method.
   */
  public void mouseWheelMoved(MouseWheelEvent e) {
    // Get the number of "clicks" and the direction of the movement.
    int rotation = e.getWheelRotation();
    // If the rotation is negative, then it was "up" or "away" from the user,
    // which means we must decrease the delay.
    if (rotation < 0) {
      //Makes sure that the delay can never go below the MIN_DELAY
      if (timer.getDelay() + rotation > MIN_DELAY) {
        timer.setDelay(timer.getDelay() + rotation
            * ROTATION_ADJUSTMENT_FACTOR);
      } else {
        timer.setDelay(MIN_DELAY);
      }
    // If the rotation is not negative, then it is positive and it was "down"
    // or "towards" the user, which means we have to increase the delay.
    } else {
      // Makes sure that the delay can never go above the MAX_DELAY
      if (timer.getDelay() + rotation < MAX_DELAY) {
        timer.setDelay(timer.getDelay() + rotation
            * ROTATION_ADJUSTMENT_FACTOR);
      } else {
        timer.setDelay(MAX_DELAY);
      }
    }
  }

  private void addAPoint() {
    // Gets the center x position of the window.
    int centerX = (int) getSize().getWidth() / 2;
    // Gets the center y position of the window.
    int centerY = (int) getSize().getHeight() / 2;
    // Calculates the x position of the next point.
    double x = centerX + Math.cos(numOfPoints * Math.PI / 3) * radius;
    // Calculates the y position of the next point.
    double y = centerY + Math.sin(numOfPoints * Math.PI / 3) * radius;
    // Puts the x position of the next point in the x array.
    this.x[numOfPoints] = (int) x;
    // Puts the y position of the next point in the y array.
    this.y[numOfPoints] = (int) y;
    // Increase the number of points by 1 because we calculated a new point.
    numOfPoints++;
    // Adds 3 to the radius so that the circle continues to grow.
    radius += 3;
    // If there are 60 (or more) points, we restart the spiral drawing by
    // going back to the original values of numOfPoints and radius.
    if (numOfPoints == 60) {
      numOfPoints = 0;
      radius = 10;
    }
  }
}
