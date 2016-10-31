package homeworkthree;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Line2D;

public class MyLine extends Line2D.Float implements MyShape {
  // The color of the line.
  private Color color;
  // Not needed for lines, but included it anyway for completeness since
  // MyShape has a setFilled method.
  private boolean filled;

  @Override
  public boolean isSelected(int clickedX, int clickedY) {
    // The line was clicked if the line intersects a tiny rectangle around
    // the point that was clicked.
    return intersects(clickedX, clickedY, 1, 1);
  }

  @Override
  public void draw(Graphics graphics) {
    graphics.setColor(color);
    graphics.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
  }

  @Override
  public void setColor(Color color) {
    this.color = color;
  }

  @Override
  public void setFilled(boolean filled) {
    this.filled = filled;
  }

  @Override
  public void move(int dx, int dy) {
    this.x1 = this.x1 + dx;
    this.x2 = this.x2 + dx;
    this.y1 = this.y1 + dy;
    this.y2 = this.y2 + dy;
  }

  public void setX1(int x1) {
    this.x1 = x1;
  }

  public void setX2(int x2) {
    this.x2 = x2;
  }

  public void setY1(int y1) {
    this.y1 = y1;
  }

  public void setY2(int y2) {
    this.y2 = y2;
  }
}
