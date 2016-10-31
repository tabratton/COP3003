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

  public MyEllipse(int x, int y, int width, int height) {
    super(x, y, width, height);
  }

  public void setClickedX(int x) {
    this.clickedX = x;
  }

  public void setClickedY(int y) {
    this.clickedY = y;
  }

  public int getClickedX() {
    return this.clickedX;
  }

  public int getClickedY() {
    return this.clickedY;
  }

  @Override
  public boolean isSelected(int x, int y) {
    if (!this.filled) {
      MyEllipse smaller = new MyEllipse((int) this.x + 1, (int) this.y + 1,
          (int) this.width - 2, (int) this.height - 2);
      MyEllipse larger = new MyEllipse((int) this.x - 1, (int) this.y - 1,
          (int) this.width + 2, (int) this.height + 2);
      return !smaller.contains(x, y) && larger.contains(x, y);
    } else {
      return contains(x, y);
    }
  }

  @Override
  public void draw(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;
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
  public void move(int x, int y) {
    this.x = this.x + x;
    this.y = this.y + y;
  }
}
