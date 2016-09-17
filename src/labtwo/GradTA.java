package labtwo;

import java.util.Scanner;

/**
 * Contains information about a grad student who is also a TA.
 */
public class GradTA extends GradStudent {
  private String taInstructor;
  private String taCourse;
  private Date hireDate;

  public GradTA() {
    super();
    readData();
  }

  private void readData() {
    Scanner scanner = new Scanner(System.in);
    String possessive = (this.gender == 'm') ? "his" : "her";
    System.out.printf("Please input %s TA course: ", possessive);
    this.taCourse = scanner.nextLine();
    System.out.printf("Please input %s TA instructor: ", possessive);
    this.taInstructor = scanner.nextLine();
    System.out.printf("Please input %s hire date: %n", possessive);
    this.hireDate = new Date();
  }

  /**
   * Prints all information about a labtwo.GradTA.
   */
  public void print() {
    super.print();
    String possessive = (this.gender == 'm') ? "His" : "Her";
    System.out.printf("%s TA course is %s.%n", possessive, this.taCourse);
    System.out.printf("%s TA instructor is %s.%n", possessive, this
        .taInstructor);
    System.out.printf("%s hire date is ", possessive);
    this.hireDate.print();
  }
}
