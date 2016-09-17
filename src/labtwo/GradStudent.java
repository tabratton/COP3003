package labtwo;

import java.util.Scanner;

/**
 * Contains information about a grad student.
 */
public class GradStudent extends Student {
  private String researchTopic;
  private String advisor;

  public GradStudent() {
    super();
    readData();
  }

  private void readData() {
    Scanner scanner = new Scanner(System.in);
    String possessive = (this.gender == 'm') ? "his" : "her";
    System.out.printf("Please input %s research topic: ", possessive);
    this.researchTopic = scanner.nextLine();
    System.out.printf("Please input %s research advisor: ", possessive);
    this.advisor = scanner.nextLine();
  }

  /**
   * Prints all information about a labtwo.GradStudent.
   */
  public void print() {
    super.print();
    String possessive = (this.gender == 'm') ? "His" : "Her";
    System.out.printf("%s research topic is %s.%n", possessive, this
        .researchTopic);
    System.out.printf("%s advisor is %s.%n", possessive, this.advisor);
  }
}
