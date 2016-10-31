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
  // Used to keep track of if the next object will be filled.
  public static boolean nextObjectFilled = false;
  // Tracks the color to be used for the next object.
  public static Color nextObjectColor = Color.RED;
  // Tracks the type of object to be drawn next.
  public static String nextObjectShape = "Rectangle";
  // Stores all drawn objects.
  public static ArrayList<MyShape> shapes = new ArrayList<>();
  // The list of colors.
  public static String[] colors = {"Red", "Green", "Blue", "Black"};
  // The list of shapes.
  public static String[] typesOfShapes = {"Rectangle", "Ellipse", "Line"};
  // The canvas object to draw shapes on.
  private static JCanvas canvas;
  // The label that displays mouse coordinates.
  private static JLabel mouseCoordinates;
  // Keeps track of the mouse being in or out of the canvas.
  private boolean mouseIn;
  // Redundant, but it was better for readability while coding.
  private boolean mouseOut;
  // Keeps track of if a shape is being drawn by the current mouse dragging.
  private boolean drawingShape;
  // Keeps track of if a shape is being moved by the current mouse dragging.
  private boolean movingShape;
  // The shape that is current being moved.
  private MyShape currentMovingShape;
  // The initial position used to calculate how far to move a shape.
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

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

    JButton undoButton = new JButton("Undo");
    undoButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent event) {
        // Remove the last drawn shape from the ArrayList.
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
        // Remove all from the ArrayList.
        shapes.clear();
        canvas.repaint();
      }
    });

    JComboBox colorComboBox = new JComboBox(colors);
    colorComboBox.addItemListener(new ItemListener() {
      @Override
      public void itemStateChanged(ItemEvent event) {
        // Sets the currently selected color.
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
        // Sets the currently selected shape.
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
        // Sets whether the Filled checkbox is checked.
        nextObjectFilled = filled.isSelected();
      }
    });

    // Add all buttons/option components to the layout.
    buttonPanel.add(undoButton);
    buttonPanel.add(clearButton);
    buttonPanel.add(colorComboBox);
    buttonPanel.add(shapesComboBox);
    buttonPanel.add(filled);
    add(buttonPanel, BorderLayout.NORTH);

    canvas = new JCanvas();
    MouseHandler mouseHandler = new MouseHandler();
    // Add the handler for both mouse actions and movements.
    canvas.addMouseListener(mouseHandler);
    canvas.addMouseMotionListener(mouseHandler);

    add(canvas, BorderLayout.CENTER);

    // Create mouse coordinates label to display where the mouse currently is.
    mouseCoordinates = new JLabel("N/A");
    mouseCoordinates.setHorizontalTextPosition(SwingConstants.LEFT);
    add(mouseCoordinates, BorderLayout.SOUTH);

  }

  private void setCoordinatesText(MouseEvent event) {
    // If the mouse is in the canvas, display the coordinates.
    if (mouseIn) {
      mouseCoordinates.setText(String.format("(%d,%d)", event.getX(), event
          .getY()));

    } else {
      // If it is out, display mouse is out.
      mouseCoordinates.setText("Mouse is out");
    }
  }

  private class MouseHandler extends MouseInputAdapter {

    @Override
    public void mouseMoved(MouseEvent event) {
      // Update coordinates (this is in every mouse movement event)
      setCoordinatesText(event);
    }

    @Override
    public void mouseExited(MouseEvent event) {
      // The mouse is currently out, set booleans accordingly.
      mouseIn = false;
      mouseOut = true;
      setCoordinatesText(event);
    }

    @Override
    public void mouseEntered(MouseEvent event) {
      // The mouse is currently in, set booleans accordingly.
      mouseIn = true;
      mouseOut = false;
      setCoordinatesText(event);
    }

    @Override
    public void mousePressed(MouseEvent event) {
      // Get the mouse button that was clicked.
      int buttonClicked = event.getButton();
      // Find the position where the mouse was clicked.
      int mouseX = event.getX();
      int mouseY = event.getY();
      // If it was a left click, we start drawing shapes.
      if (SwingUtilities.isLeftMouseButton(event)) {
        drawingShape = true;
        MyShape newShape = null;
        // Create a new shape based on current selections.
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
        // If it was a right click, we start moving shapes.
        MyShape clickedShape = null;
        // Find out which shape, if any, was clicked.
        for (int i = shapes.size() - 1; i >= 0; i--) {
          if (shapes.get(i).isSelected(mouseX, mouseY)) {
            clickedShape = shapes.get(i);
            break;
          }
        }
        // If a shape was clicked, save fields so that the drag event can
        // move the shape.
        if (clickedShape != null) {
          currentMovingShape = clickedShape;
          initialRightClickX = mouseX;
          initialLeftClickY = mouseY;
          movingShape = true;
        }
      }
      // Update changes to the canvas.
      canvas.repaint();
    }

    @Override
    public void mouseDragged(MouseEvent event) {
      setCoordinatesText(event);
      // Get current mouse position.
      int newX = event.getX();
      int newY = event.getY();
      if (drawingShape) {
        // Get the newest shape because we have to be using that one if we're
        // drawing.
        MyShape newShape = shapes.get(shapes.size() - 1);
        // Get the subclass of the shape.
        String subclassName = newShape.getClass().getSimpleName();
        switch (newShape.getClass().getSimpleName()) {
          case "MyRectangle":
            MyRectangle changingRectangle = (MyRectangle) newShape;
            int clickedX = changingRectangle.getClickedX();
            int clickedY = changingRectangle.getClickedY();
            // Find the Upper Left points and the width/height.
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
        // The change in position is needed to calculate the required
        // movement of the shape.
        int changeInX = newX - initialRightClickX;
        int changeInY = newY - initialLeftClickY;
        switch (currentMovingShape.getClass().getSimpleName()) {
          case "MyRectangle":
            MyRectangle changingRectangle = (MyRectangle) currentMovingShape;
            changingRectangle.move(changeInX, changeInY);
            // Set the x and y to the current position, so that the change in
            // x and y stays accurate to the most recent changes, and doesn't
            // get increasingly huge
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
    public void mouseReleased(MouseEvent event) {
      // When the mouse is released, we are done moving or drawing.
      movingShape = false;
      drawingShape = false;
      // Point currentMovingShape to null to make sure we don't move a shape
      // by accident.
      currentMovingShape = null;
    }
  }

  private int findUpperLeftX(int clickedX, int draggedX) {
    // The upper leftmost x will always be the smaller value.
    return clickedX < draggedX ? clickedX : draggedX;
  }

  private int findUpperLeftY(int clickedY, int draggedY) {
    // The upper leftmost y will always be the smaller value.
    return clickedY < draggedY ? clickedY : draggedY;
  }

  private int findWidth(int clickedX, int draggedX) {
    // The width is the absolute difference between the two x's
    return Math.abs(clickedX - draggedX);
  }

  private int findHeight(int clickedY, int draggedY) {
    // The height is the absolute difference between the two y's
    return Math.abs(clickedY - draggedY);
  }
}