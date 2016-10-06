package homeworkone;

//TODO Add comments like crazy.

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class JEightPuzzleFrame extends JFrame {
  private int imageWidth;
  private int imageHeight;
  private JPanel emptyButton = new JPanel();
  private JButton[] buttons = new JButton[8];
  private int[][] board = new int[3][3];
  private int[][] solvedBoard = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};

  public static void main(String args[]) {
    if (args.length != 1) {
      System.err.println("Error: Image filename not given. You must provide a" +
          " filename for an image as an argument.");
      System.exit(1);
    }
    JEightPuzzleFrame buttonFrame = new JEightPuzzleFrame("Eight Puzzle Game",
        args[0]);
    buttonFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // The addition of 22 and 43 accounts for the sizes of the borders and
    // window when using the setSize function
    buttonFrame.setSize(buttonFrame.imageWidth + 20, buttonFrame.imageHeight
        + 45);
    buttonFrame.setVisible(true);
  }

  public JEightPuzzleFrame(String windowTitle, String imagePath) {
    super(windowTitle);
    setLayout(new GridLayout(3, 3));
    add(emptyButton);
    board[0][0] = 0;
    ButtonHandler handler = new ButtonHandler();

    for (int i = 0; i < buttons.length; i++) {
      Icon icon = extractIcon(imagePath, i % 3, i / 3);
      buttons[i] = new JButton(icon);
      buttons[i].addActionListener(handler);
    }
    initialPositions();
  }

  private Icon extractIcon(String imagePath, int startX, int startY) {
    BufferedImage image = null;
    try {
      image = ImageIO.read(new File(imagePath));
    } catch (IOException e) {
      System.err.println("Image not found");
      System.exit(1);
    }
    this.imageHeight = image.getHeight();
    this.imageWidth = image.getWidth();

    int widthOfPart = imageWidth / 3;
    int heightOfPart = imageHeight / 3;

    BufferedImage part = new BufferedImage(widthOfPart, heightOfPart,
        BufferedImage.TYPE_4BYTE_ABGR);


    for (int x = 0; x < widthOfPart; x++) {
      for (int y = 0; y < heightOfPart; y++) {
        part.setRGB(x, y, image.getRGB(x + (widthOfPart * startX), y +
            (heightOfPart * startY)));
      }
    }

    ImageIcon icon = new ImageIcon();
    icon.setImage(part);

    return icon;
  }

  private void initialPositions() {
    // The first number represents the original position of the piece, with
    // the - 1 adjusting it for its position inside the array.
    add(buttons[1 - 1]);
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
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board.length; j++) {
        if (solvedBoard[i][j] != board[i][j]) {
          return false;
        }
      }
    }
    return true;
  }

  private boolean checkNeighbor(int row, int col) {
    switch (row) {
      case 0:
        if (col != 1) {
          return board[row + 1][col] == 0 || board[0][1] == 0;
        } else {
          return board[row + 1][col] == 0 || board[row][col + 1] == 0 ||
              board[0][0] == 0;
        }
      case 1:
        if (col != 1) {
          return board[row + 1][col] == 0 || board[row - 1][col] == 0 ||
              board[1][1] == 0;
        } else {
          return board[row - 1][col] == 0 || board[row + 1][col] == 0 ||
              board[row][col + 1] == 0 || board[row][col - 1] == 0;
        }
      case 2:
        if (col != 1) {
          return board[row - 1][col] == 0 || board[2][1] == 0;
        } else {
          return board[row][col - 1] == 0 || board[row - 1][col] == 0 ||
              board[row][col + 1] == 0;
        }
    }
    return false;
  }

  private void swapZero(int buttonRow, int buttonCol, int zeroRow, int
      zeroCol) {
    int tempZero = board[zeroRow][zeroCol];
    board[zeroRow][zeroCol] = board[buttonRow][buttonCol];
    board[buttonRow][buttonCol] = tempZero;
    reorganizeButtons();
  }

  private void reorganizeButtons() {
    for (int i = 0; i < buttons.length; i++) {
      remove(buttons[i]);
    }

    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board.length; j++) {
        if (board[i][j] != 0) {
          add(buttons[board[i][j] - 1]);
        } else {
          add(emptyButton);
        }
      }
    }
    getContentPane().validate();
  }

  private void shuffle() {
    boolean[][] used = new boolean[3][3];
    int[][] newArray = new int[3][3];
    Random r = new Random();

    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board.length; j++) {
        int newRow;
        int newCol;
        do {
          newRow = r.nextInt(Integer.MAX_VALUE) % 3;
          newCol = r.nextInt(Integer.MAX_VALUE) % 3;
        } while (used[newRow][newCol]);
        newArray[newRow][newCol] = board[i][j];
        used[newRow][newCol] = true;
      }
    }
    board = newArray;
    reorganizeButtons();
  }

  private class ButtonHandler implements ActionListener {
    public void actionPerformed(ActionEvent event) {
      JButton clickedButton = (JButton) event.getSource();
      int buttonID = -1;
      int currentRow = -1;
      int currentCol = -1;
      int currentZeroRow = -1;
      int currentZeroCol = -1;

      for (int i = 0; i < buttons.length; i++) {
        if (clickedButton == buttons[i]) {
          buttonID = i + 1;
        }
      }

      for (int i = 0; i < board.length; i++) {
        for (int j = 0; j < board.length; j++) {
          if (board[i][j] == buttonID) {
            currentRow = i;
            currentCol = j;
          }
          if (board[i][j] == 0) {
            currentZeroRow = i;
            currentZeroCol = j;
          }
        }
      }

      if (checkNeighbor(currentRow, currentCol)) {
        swapZero(currentRow, currentCol, currentZeroRow, currentZeroCol);
        if (checkIfWon()) {
          JOptionPane.showMessageDialog(null, "Congratulations, you solved " +
              "the puzzle!");
          shuffle();
        }
      }
    }
  }
}
