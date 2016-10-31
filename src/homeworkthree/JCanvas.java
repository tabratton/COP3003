package homeworkthree;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class JCanvas extends JPanel {

  /**
   * Repaints the canvas used for drawing.
   *
   * @param graphics The graphics object used for drawing.
   */
  public void paintComponent(Graphics graphics) {
    graphics.setColor(Color.WHITE);
    graphics.fillRect(0, 0, getWidth(), getHeight());

    // Loops through all the drawn shapes, redrawing them to reflect any
    // changes made.
    for (MyShape shape : JSimplePaint.shapes) {
      shape.draw(graphics);
    }
  }
}
