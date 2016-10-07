package homeworkone;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class JEightPuzzleFrame extends JFrame {
  // Adjustment to width of window because of borders on buttons and space
  // taken up by the window borders.
  private static final int WIDTH_ADJUSTMENT = 20;
  // Adjustment to height of window because of borders on buttons and space
  // taken up by the window borders.
  private static final int HEIGHT_ADJUSTMENT = 45;
  // The height of the full image used to create the puzzle.
  private int imageWidth;
  // The width of the full image used to create the puzzle.
  private int imageHeight;
  // The "emptyButton" that is used to display the empty square.
  private JPanel emptyButton = new JPanel();
  // An array to hold all of the button objects, also helps identify them by
  // their position in the array.
  private JButton[] buttons = new JButton[8];
  // The current arrangement of the buttons on the board, each number is the
  // ID number of the button, which is its index in the buttons array + 1,
  // except for the empty button, which is 0.
  private int[][] board = new int[3][3];
  // The solved state of the board, used for checking after each move.
  private int[][] solvedBoard = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};

  /**
   * Main method of the program.
   *
   * <p>Creates the frame, sets the size of the frame based on the size of
   * the image used to create the frame, and sets visibility to true.
   *
   * @param args Commandline arguments not used in this program
   */
  public static void main(String[] args) {
    String imageName;

    if (args.length != 0) {
      imageName = args[1];
      System.out.printf("Using given file: %s as source image.", imageName);
    } else {
      imageName = "FGCU_logo.png";
      System.out.printf("No commandline arguments given, using default image:"
          + " %s as source image.", imageName);
    }

    JEightPuzzleFrame buttonFrame = new JEightPuzzleFrame("Eight Puzzle Game",
        imageName);
    buttonFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    buttonFrame.setSize(buttonFrame.imageWidth + WIDTH_ADJUSTMENT,
        buttonFrame.imageHeight + HEIGHT_ADJUSTMENT);
    buttonFrame.setVisible(true);
  }

  /**
   * JEightPuzzleFrame constructor.
   *
   * <p>Sets the window title, layout, calls extractIcon to split source
   * image into 8 pieces and assign them to buttons, registers the button
   * handler as an action listener, and calls the initialPositions method to
   * set the predetermined layout of the buttons.
   *
   * @param windowTitle A String that contains the desired title of the window
   * @param imagePath   A String that is the filename of the image to use as a
   *                    source for the puzzle buttons
   */
  public JEightPuzzleFrame(String windowTitle, String imagePath) {
    super(windowTitle);
    setLayout(new GridLayout(3, 3));
    // Adds empty button first so that it is in the 0, 0 position of the board.
    add(emptyButton);
    board[0][0] = 0;
    // Creates handler object so that it can be registered as an
    // action listener for all buttons.
    ButtonHandler handler = new ButtonHandler();

    for (int i = 0; i < buttons.length; i++) {
      // Creates an icon that is a piece of the original image that is 1/3
      // the width and height of the original image.
      // The starting x, y positions of each image are the top leftmost x and
      // topmost y of the piece being taken. Imagining the starting positions
      // as a [3][3] array, the first piece [0][0] will have starting
      // positions [0][0], the second piece [0][1] will have [0][1] starting
      // positions, however the fourth piece [1][0] will have starting
      // positions [0][1], so we must always take the modulus to get the
      // starting x, and use division to get the starting y.
      Icon icon = extractIcon(imagePath, i % 3, i / 3);
      // Creates new button with the icon just extracted from the source image.
      buttons[i] = new JButton(icon);
      // Registers the button handler as the action listener.
      buttons[i].addActionListener(handler);
    }
    // Sets the predetermined initial positions.
    initialPositions();
  }

  private Icon extractIcon(String imagePath, int startX, int startY) {
    BufferedImage image = null;
    // Makes sure that the file name given is valid.
    try {
      image = ImageIO.read(new File(imagePath));
    } catch (IOException exception) {
      System.err.println("Image not found");
      System.exit(1);
    }
    this.imageHeight = image.getHeight();
    this.imageWidth = image.getWidth();

    // Each part will be 1/3 the width and height of the source image, since
    // we are making a 3x3 grid.
    int widthOfPart = imageWidth / 3;
    int heightOfPart = imageHeight / 3;

    BufferedImage part = new BufferedImage(widthOfPart, heightOfPart,
        BufferedImage.TYPE_4BYTE_ABGR);

    // Sets the values of the new image to the values in the section of the
    // source image that we want.
    for (int x = 0; x < widthOfPart; x++) {
      for (int y = 0; y < heightOfPart; y++) {
        // widthOfPart * startX and heightOfPart * startY will always provide
        // us with the starting point for that section of the image.
        part.setRGB(x, y, image.getRGB(x + (widthOfPart * startX), y
            + (heightOfPart * startY)));
      }
    }

    ImageIcon icon = new ImageIcon();
    icon.setImage(part);

    return icon;
  }

  private void initialPositions() {
    // The first number represents the original position of the piece, with
    // the - 1 adjusting it for its position inside the array. Same for all
    // add statements in this method.
    add(buttons[1 - 1]);
    // Changes the board array to make note of which button is in which space
    // of the board. Same for all statements involving board in this method.
    board[0][1] = 1;
    add(buttons[2 - 1]);
    board[0][2] = 2;
    add(buttons[5 - 1]);
    board[1][0] = 5;
    add(buttons[6 - 1]);
    board[1][1] = 6;
    add(buttons[3 - 1]);
    board[1][2] = 3;
    add(buttons[4 - 1]);
    board[2][0] = 4;
    add(buttons[7 - 1]);
    board[2][1] = 7;
    add(buttons[8 - 1]);
    board[2][2] = 8;
  }

  private boolean checkIfWon() {
    // Checks each position of the board against that position of the
    // solvedBoard to make sure the buttons in each position match.
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board.length; j++) {
        if (solvedBoard[i][j] != board[i][j]) {
          return false;
        }
      }
    }
    return true;
  }

  private boolean isNeighbor(int curRow, int curCol, int zeroRow, int
      zeroCol) {
    // Neighbors will always be either one row up and the same column, one
    // row down and the same column, the same row and ome column left, or the
    // same row and one column right.
    return zeroRow == curRow - 1 && zeroCol == curCol || zeroRow == curRow
        + 1 && zeroCol == curCol || zeroRow == curRow && zeroCol == curCol
        - 1 || zeroRow == curRow && zeroCol == curCol + 1;
  }

  private void swapZero(int buttonRow, int buttonCol, int zeroRow, int
      zeroCol) {
    // Simply switches the position of whatever button was clicked and the
    // empty button. Only called if isNeighbor returned true.
    int tempZero = board[zeroRow][zeroCol];
    board[zeroRow][zeroCol] = board[buttonRow][buttonCol];
    board[buttonRow][buttonCol] = tempZero;
    // Calls reorganizeButtons to visualize the changes to the board.
    reorganizeButtons();
  }

  private void reorganizeButtons() {
    // Removes all the buttons from the frame
    for (int i = 0; i < buttons.length; i++) {
      remove(buttons[i]);
    }

    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board.length; j++) {
        // Takes care of the case where the button number is 0, the empty
        // button, since it is not located in the buttons array.
        if (board[i][j] != 0) {
          // The button that is at [i][j] position in the board will have an
          // index of (whatever value it is) - 1 in the buttons array. This
          // allows us to loop through the board array and dynamically re-add
          // the buttons to the frame depending on their position in the board.
          add(buttons[board[i][j] - 1]);
        } else {
          add(emptyButton);
        }
      }
    }
    // Visualizes all the changes made to the frame.
    getContentPane().validate();
  }

  private void shuffle() {
    // Stores information on which places of the new array have already had
    // values moved to them.
    boolean[][] used = new boolean[3][3];
    // New array that will be a shuffled version of the original one.
    int[][] newArray = new int[3][3];
    // Because everyone loves random puzzles that could be unsolvable.
    Random random = new Random();

    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board.length; j++) {
        int newRow;
        int newCol;
        do {
          newRow = random.nextInt(Integer.MAX_VALUE) % 3;
          newCol = random.nextInt(Integer.MAX_VALUE) % 3;
        } while (used[newRow][newCol]);
        newArray[newRow][newCol] = board[i][j];
        used[newRow][newCol] = true;
      }
    }
    // Makes the board array the new array
    board = newArray;
    // Calls reorganizeButtons to visualize the random shuffle.
    reorganizeButtons();
  }

  private class ButtonHandler implements ActionListener {
    public void actionPerformed(ActionEvent event) {
      // Creates a new button so that we can compare it to the buttons array
      // to determine which one was clicked.
      JButton clickedButton = (JButton) event.getSource();
      int buttonNumber = -1;
      int currentRow = -1;
      int currentCol = -1;
      int currentZeroRow = -1;
      int currentZeroCol = -1;

      for (int i = 0; i < buttons.length; i++) {
        if (clickedButton == buttons[i]) {
          // If the buttons are the same, the ID number of the button will be
          // its array index + 1.
          buttonNumber = i + 1;
        }
      }

      // Searches the board to find the current position of the button that
      // was clicked and the current position of the empty button.
      for (int i = 0; i < board.length; i++) {
        for (int j = 0; j < board.length; j++) {
          if (board[i][j] == buttonNumber) {
            currentRow = i;
            currentCol = j;
          }
          if (board[i][j] == 0) {
            currentZeroRow = i;
            currentZeroCol = j;
          }
        }
      }

      // Calls isNeighbor to see if the empty button is a neighbor of the
      // clicked button.
      if (isNeighbor(currentRow, currentCol, currentZeroRow,
          currentZeroCol)) {
        // If they are neighbors, then their positions are swapped with
        // swapZero.
        swapZero(currentRow, currentCol, currentZeroRow, currentZeroCol);
        // Calls checkIfWon to see if the current board state is the
        // same as the winning board state.
        if (checkIfWon()) {
          // If it is the same, then a pop-up message is displayed that
          // congratulates the user.
          JOptionPane.showMessageDialog(null, "Congratulations, you solved"
              + " the puzzle!");
          // And the board is reshuffled into a random state.
          shuffle();
        }
      }
    }
  }
}
