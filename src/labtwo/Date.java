package labtwo;

import java.util.Scanner;

/**
 * Contains information about a single day.
 */
public class Date {
  private int month;
  private int day;
  private int year;

  public Date() {
    this.readData();
  }

  private void readData() {
    Scanner scanner = new Scanner(System.in);
    System.out.printf("\t\tPlease input the month: ");
    this.month = scanner.nextInt();
    System.out.printf("\t\tPlease input the day: ");
    this.day = scanner.nextInt();
    System.out.printf("\t\tPlease input the year: ");
    this.year = scanner.nextInt();
  }

  /**
   * Prints all information about a labtwo.Date.
   */
  public void print() {
    System.out.printf("%d/%d/%d%n", month, day, year);
  }
}

