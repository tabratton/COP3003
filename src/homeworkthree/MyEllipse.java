package homeworkthree;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class MyEllipse extends Ellipse2D.Float implements MyShape {
  private Color color;
  private boolean filled;
  private int clickedX;
  private int clickedY;

  public MyEllipse() {
    super();
  }

  public MyEllipse(int topLeftX, int topLeftY, int width, int height) {
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
    if (!this.filled) {
      MyEllipse smaller = new MyEllipse((int) this.x + 1, (int) this.y + 1,
          (int) this.width - 2, (int) this.height - 2);
      MyEllipse larger = new MyEllipse((int) this.x - 1, (int) this.y - 1,
          (int) this.width + 2, (int) this.height + 2);
      return !smaller.contains(clickedX, clickedY) && larger.contains(clickedX, clickedY);
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
    this.x = this.x + dx;
    this.y = this.y + dy;
  }
}
