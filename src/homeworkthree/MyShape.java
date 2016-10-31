package homeworkthree;

import java.awt.Color;
import java.awt.Graphics;

public interface MyShape {
  void draw(Graphics graphics);

  void setColor(Color color);

  void setFilled(boolean filled);

  boolean isSelected(int clickedX, int clickedY);

  void move(int dx, int dy);
}