package homeworkthree;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;

public class JSimplePaint extends JFrame {
  public static boolean nextObjectFilled = false;
  public static Color nextObjectColor = Color.RED;
  public static String nextObjectShape = "Rectangle";
  public static ArrayList<MyShape> shapes = new ArrayList<>();
  public static String[] colors = {"Red", "Green", "Blue", "Black"};
  public static String[] typesOfShapes = {"Rectangle", "Ellipse", "Line"};
  private static JPanel buttonPanel;
  private static JCanvas  canvas;
  private static JLabel mouseCoordinates;
  private boolean mouseIn;
  private boolean mouseOut;
  private boolean drawingShape;
  private boolean movingShape;
  private MyShape currentMovingShape;
  private int initialRightClickX;
  private int initialLeftClickY;

  /**
   * Main method of the program, creates a JSimplePaint object and displays it.
   *
   * @param args No commandline arguments used in this program.
   */
  public static void main(String[] args) {
    JSimplePaint paint = new JSimplePaint("Paint");
    paint.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    paint.setSize(600, 600);
    paint.setVisible(true);
  }

  /**
   * Constructor for JSimplePaint, creates the entire layout.
   *
   * @param windowName The name of the window.
   */
  public JSimplePaint(String windowName) {
    super(windowName);
    setLayout(new BorderLayout());

    buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

    JButton undoButton = new JButton("Undo");
    undoButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent event) {
        if (shapes.size() > 0) {
          shapes.remove(shapes.size() - 1);
          canvas.repaint();
        } else {
          shapes.clear();
          canvas.repaint();
        }
      }
    });

    JButton clearButton = new JButton("Clear");
    clearButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent event) {
        shapes.clear();
        canvas.repaint();
      }
    });

    JComboBox colorComboBox = new JComboBox(colors);
    colorComboBox.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent event) {
        switch (colorComboBox.getSelectedIndex()) {
          case 0:
            nextObjectColor = Color.RED;
            break;
          case 1:
            nextObjectColor = Color.GREEN;
            break;
          case 2:
            nextObjectColor = Color.BLUE;
            break;
          case 3:
            nextObjectColor = Color.BLACK;
            break;
          default:
            break;
        }
      }
    });

    JComboBox shapesComboBox = new JComboBox(typesOfShapes);
    shapesComboBox.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent event) {
        switch (shapesComboBox.getSelectedIndex()) {
          case 0:
            nextObjectShape = "Rectangle";
            break;
          case 1:
            nextObjectShape = "Ellipse";
            break;
          case 2:
            nextObjectShape = "Line";
            break;
          default:
            break;
        }
      }
    });

    JCheckBox filled = new JCheckBox("Filled");
    filled.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent event) {
        nextObjectFilled = filled.isSelected();
      }
    });

    buttonPanel.add(undoButton);
    buttonPanel.add(clearButton);
    buttonPanel.add(colorComboBox);
    buttonPanel.add(shapesComboBox);
    buttonPanel.add(filled);
    add(buttonPanel, BorderLayout.NORTH);

    canvas = new JCanvas();
    MouseHandler mouseHandler = new MouseHandler();
    canvas.addMouseListener(mouseHandler);
    canvas.addMouseMotionListener(mouseHandler);

    add(canvas, BorderLayout.CENTER);

    mouseCoordinates = new JLabel("N/A");
    mouseCoordinates.setHorizontalTextPosition(SwingConstants.LEFT);
    add(mouseCoordinates, BorderLayout.SOUTH);

  }

  private void setCoordinatesText(MouseEvent event) {
    if (mouseIn) {
      mouseCoordinates.setText(String.format("(%d,%d)", event.getX(), event
          .getY()));

    } else {
      mouseCoordinates.setText("Mouse is out");
    }
  }

  private class MouseHandler extends MouseInputAdapter {
    @Override
    public void mouseDragged(MouseEvent event) {
      setCoordinatesText(event);
      int newX = event.getX();
      int newY = event.getY();
      if (drawingShape) {
        MyShape newShape = shapes.get(shapes.size() - 1);
        String subclassName = newShape.getClass().getSimpleName();
        switch (newShape.getClass().getSimpleName()) {
          case "MyRectangle":
            MyRectangle changingRectangle = (MyRectangle) newShape;
            int clickedX = changingRectangle.getClickedX();
            int clickedY = changingRectangle.getClickedY();
            changingRectangle.x = findUpperLeftX(clickedX, newX);
            changingRectangle.y = findUpperLeftY(clickedY, newY);
            changingRectangle.width = findWidth(clickedX, newX);
            changingRectangle.height = findHeight(clickedY, newY);
            break;
          case "MyEllipse":
            MyEllipse changingEllipse = (MyEllipse) newShape;
            clickedX = changingEllipse.getClickedX();
            clickedY = changingEllipse.getClickedY();
            changingEllipse.x = findUpperLeftX(clickedX, newX);
            changingEllipse.y = findUpperLeftY(clickedY, newY);
            changingEllipse.width = findWidth(clickedX, newX);
            changingEllipse.height = findHeight(clickedY, newY);
            break;
          case "MyLine":
            MyLine changingLine = (MyLine) newShape;
            changingLine.setX2(newX);
            changingLine.setY2(newY);
            break;
          default:
            break;
        }
      } else if (movingShape) {
        int changeInX = newX - initialRightClickX;
        int changeInY = newY - initialLeftClickY;
        switch (currentMovingShape.getClass().getSimpleName()) {
          case "MyRectangle":
            MyRectangle changingRectangle = (MyRectangle) currentMovingShape;
            changingRectangle.move(changeInX, changeInY);
            initialRightClickX = newX;
            initialLeftClickY = newY;
            break;
          case "MyEllipse":
            MyEllipse changingEllipse = (MyEllipse) currentMovingShape;
            changingEllipse.move(changeInX, changeInY);
            initialRightClickX = newX;
            initialLeftClickY = newY;
            break;
          case "MyLine":
            MyLine changingLine = (MyLine) currentMovingShape;
            changingLine.move(changeInX, changeInY);
            initialRightClickX = newX;
            initialLeftClickY = newY;
            break;
          default:
            break;
        }
      }
      canvas.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent event) {
      setCoordinatesText(event);
    }

    @Override
    public void mouseExited(MouseEvent event) {
      mouseIn = false;
      mouseOut = true;
      setCoordinatesText(event);
    }

    @Override
    public void mouseEntered(MouseEvent event) {
      mouseIn = true;
      mouseOut = false;
      setCoordinatesText(event);
    }

    @Override
    public void mousePressed(MouseEvent event) {
      int buttonClicked = event.getButton();
      int mouseX = event.getX();
      int mouseY = event.getY();
      if (SwingUtilities.isLeftMouseButton(event)) {
        drawingShape = true;
        MyShape newShape = null;
        switch (nextObjectShape) {
          case "Rectangle":
            MyRectangle newRectangle = new MyRectangle();
            newRectangle.setClickedX(mouseX);
            newRectangle.setClickedY(mouseY);
            newShape = newRectangle;
            break;
          case "Ellipse":
            MyEllipse newEllipse = new MyEllipse();
            newEllipse.setClickedX(mouseX);
            newEllipse.setClickedY(mouseY);
            newShape = newEllipse;
            break;
          case "Line":
            MyLine newLine = new MyLine();
            newLine.setX1(mouseX);
            newLine.setY1(mouseY);
            newLine.setX2(mouseX);
            newLine.setY2(mouseY);
            newShape = newLine;
            break;
          default:
            break;
        }
        newShape.setColor(nextObjectColor);
        newShape.setFilled(nextObjectFilled);
        shapes.add(newShape);
      } else if (SwingUtilities.isRightMouseButton(event)) {
        MyShape clickedShape = null;
        for (int i = shapes.size() - 1; i >= 0; i--) {
          if (shapes.get(i).isSelected(mouseX, mouseY)) {
            clickedShape = shapes.get(i);
            break;
          }
        }
        if (clickedShape != null) {
          currentMovingShape = clickedShape;
          initialRightClickX = mouseX;
          initialLeftClickY = mouseY;
          movingShape = true;
        }
      }
      canvas.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent event) {
      movingShape = false;
      drawingShape = false;
    }
  }

  private int findUpperLeftX(int clickedX, int draggedX) {
    return clickedX < draggedX ? clickedX : draggedX;
  }

  private int findUpperLeftY(int clickedY, int draggedY) {
    return clickedY < draggedY ? clickedY : draggedY;
  }

  private int findWidth(int clickedX, int draggedX) {
    return Math.abs(clickedX - draggedX);
  }

  private int findHeight(int clickedY, int draggedY) {
    return Math.abs(clickedY - draggedY);
  }
}
