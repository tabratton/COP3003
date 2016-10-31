package homeworkthree;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class MyRectangle extends Rectangle implements MyShape {
  // The color of the shape.
  private Color color;
  // The filled status of the shape.
  private boolean filled;
  // Stores the initial click coordinates to use later when drawing the object.
  private int clickedX;
  private int clickedY;

  public MyRectangle() {
    super();
  }

  public MyRectangle(int topLeftX, int topLeftY, int width, int height) {
    super(topLeftX, topLeftY, width, height);
  }

  public void setClickedX(int clickedX) {
    this.clickedX = clickedX;
  }

  public void setClickedY(int clickedY) {
    this.clickedY = clickedY;
  }

  public int getClickedX() {
    return this.clickedX;
  }

  public int getClickedY() {
    return this.clickedY;
  }

  @Override
  public boolean isSelected(int clickedX, int clickedY) {
    // If it isn't filled, use inclusion/exclusion with a slightly smaller
    // rectangle and a slightly larger rectangle to determine if the border of
    // the real rectangle was clicked.
    if (!this.filled) {
      MyRectangle smaller = new MyRectangle(this.x + 1, this.y + 1, this.width
          - 2, this.height - 2);
      MyRectangle larger = new MyRectangle(this.x - 1, this.y - 1, this.width
          + 2, this.height + 2);
      return !smaller.contains(clickedX, clickedY) && larger.contains(clickedX,
          clickedY);
    } else {
      return contains(clickedX, clickedY);
    }
  }

  @Override
  public void draw(Graphics graphics) {
    Graphics2D g2d = (Graphics2D) graphics;
    g2d.setColor(color);
    if (filled) {
      g2d.fill(this);
    } else {
      g2d.draw(this);
    }
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
    // If the change was to the left or up, dx or dy will already be
    // negative, so only addition is needed.
    this.x = this.x + x;
    this.y = this.y + y;
  }
}
