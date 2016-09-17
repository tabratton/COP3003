package labtwo;

import java.util.Scanner;

/**
 * Contains information about a student.
 */
public class Student {
  private String name;
  private int ssn;
  private int numOfClasses;
  private Date birthDate;
  public char gender;

  public Student() {
    this.readData();
  }

  private void readData() {
    Scanner scanner = new Scanner(System.in);
    System.out.printf("Please input the name: ");
    this.name = scanner.nextLine();
    System.out.printf("Please input the SSN: ");
    this.ssn = scanner.nextInt();
    // Clears the scanner from anything left over from nextInt
    scanner.nextLine();
    System.out.printf("Male/Female (m/f): ");
    // Since input is a 1 character string, sets the only character to gender
    this.gender = scanner.nextLine().charAt(0);
    // Determines and sets correct pronouns
    String pronoun = (this.gender == 'm') ? "he" : "she";
    String possessive = (this.gender == 'm') ? "his" : "her";
    System.out.printf("How many courses %s is taking: ", pronoun);
    this.numOfClasses = scanner.nextInt();
    System.out.printf("Please input %s birth date: %n", possessive);
    this.birthDate = new Date();
  }

  /**
   * Prints out all the information about a labtwo.Student.
   */
  public void print() {
    System.out.println("");
    System.out.println("The information you input was");
    System.out.printf("%s's ssn is %d.%n", this.name, this.ssn);
    String pronoun = (this.gender == 'm') ? "He" : "She";
    String possessive = (this.gender == 'm') ? "His" : "Her";
    System.out.printf("%s is taking %d courses.%n", pronoun, this
        .numOfClasses);
    System.out.printf("%s birth date is ", possessive);
    this.birthDate.print();
  }
}
