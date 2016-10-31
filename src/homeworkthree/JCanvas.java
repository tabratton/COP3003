package homeworkthree;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;

public class JCanvas extends JPanel {
  public void paintComponent(Graphics g) {
    g.setColor(Color.WHITE);
    g.fillRect(0, 0, getWidth(), getHeight());

    for (MyShape shape : JSimplePaint.shapes) {
      shape.draw(g);
    }
  }
}
