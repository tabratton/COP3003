package homeworkthree;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Line2D;

public class MyLine extends Line2D.Float implements MyShape {
  private Color color;
  private boolean filled;

  @Override
  public boolean isSelected(int x, int y) {
    return intersects(x, y, 1, 1);
  }

  @Override
  public void draw(Graphics g) {
    g.setColor(color);
    g.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
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
  public void move(int x, int y) {
    this.x1 = this.x1 + x;
    this.x2 = this.x2 + x;
    this.y1 = this.y1 + y;
    this.y2 = this.y2 + y;
  }

  public void setX1(int x) {
    this.x1 = x;
  }

  public void setX2(int x) {
    this.x2 = x;
  }

  public void setY1(int y) {
    this.y1 = y;
  }

  public void setY2(int y) {
    this.y2 = y;
  }
}
