package labone;

/**
 * A class for storing and performing basic math operations on very large
 * integers.
 *
 * @version 1.0 - 2016-09-05
 */
public class HugeInteger {
  private static final byte NUM_DIGITS = 40;
  private byte[] digits = new byte[NUM_DIGITS];
  private boolean positive;
  private byte mostSignificantDigit;

  /**
   * Converts an integer string to an array of digits.
   *
   * <p>It does not check the validity of the string.
   *
   * @param num user input string that represents an integer of 40 characters or
   *            less
   */
  public HugeInteger(String num) {
    // Removes '-' character from string if it exists and sets positive to false
    if (num.startsWith("-")) {
      num = num.substring(1);
      positive = false;
    } else {
      positive = true;
    }

    byte length = (byte) num.length();
    // Populates the array with zeroes
    for (int i = 0; i < NUM_DIGITS; i++) {
      this.digits[i] = 0;
    }
    // Populates the array with the integer values of the string
    for (int i = NUM_DIGITS - length, j = 0; i < NUM_DIGITS; i++, j++) {
      this.digits[i] = (byte) (num.charAt(j) - (int) '0');
    }

    this.findMostSignificantDigit();
  }

  /**
   * Checks to see if two HugeIntegers are equal to one another.
   *
   * @param hi an object of the Lab1.HugeInteger class to be used for comparison
   * @return whether the two HugeIntegers are equal or not
   */
  public boolean isEqualTo(HugeInteger hi) {

    // Checks to see if they are the same sign
    if (this.positive != hi.positive) {
      return false;
    }

    // Checks to see if any digits in the arrays do not match
    for (int i = 0; i < NUM_DIGITS; i++) {
      if (this.digits[i] != hi.digits[i]) {
        return false;
      }
    }
    return true;
  }

  /**
   * Checks to see if one Lab1.HugeInteger is greater than another one.
   *
   * @param hi an object of the Lab1.HugeInteger class to be used for comparison
   * @return whether the first Lab1.HugeInteger is greater than the second one
   */
  public boolean isGreaterThan(HugeInteger hi) {
    // Checks to see if the numbers are the same, or if one is positive and
    // the other isn't
    if (this.isEqualTo(hi)) {
      return false;
    } else if (this.positive && !hi.positive) {
      return true;
    } else if (!this.positive && hi.positive) {
      return false;
    }

    boolean greaterThan = false;

    // Compares digits in both integers from the left to see which are
    // larger, once it finds one,
    // it breaks the loop and assigns a value to greaterThan corresponding to
    // which was larger
    for (int i = 0; i < NUM_DIGITS; i++) {
      if (this.digits[i] > hi.digits[i]) {
        greaterThan = true;
        break;
      } else if (this.digits[i] < hi.digits[i]) {
        greaterThan = false;
        break;
      }
    }

    // Because negative numbers mess up literally everything, this negates
    // the result of the previous loop since it
    // doesn't account for negatives
    if (!this.positive && !hi.positive) {
      greaterThan = !greaterThan;
    }

    return greaterThan;
  }

  /**
   * Checks to see if two HugeIntegers are not equal to one another.
   *
   * @param hi an object of the Lab1.HugeInteger class to be used for comparison
   * @return whether the two HugeIntegers are not equal to one another
   */
  public boolean isNotEqualTo(HugeInteger hi) {
    // Simply returns the opposite of isEqualTo
    return !(this.isEqualTo(hi));
  }

  /**
   * Checks to see if one Lab1.HugeInteger is less than another one.
   *
   * @param hi an object of the Lab1.HugeInteger class to be used for comparison
   * @return whether the first Lab1.HugeInteger is less than the second one
   */
  public boolean isLessThan(HugeInteger hi) {
    // Checks to see if the numbers are the same, or if one is positive and
    // the other isn't
    if (this.isEqualTo(hi)) {
      return false;
    } else if (this.positive && !hi.positive) {
      return false;
    } else if (!this.positive && hi.positive) {
      return true;
    }

    boolean lessThan = false;

    // Compares digits in both integers from the left to see which are
    // larger, once it finds one,
    // it breaks the loop and assigns a value to lessThan corresponding to
    // which was larger
    for (int i = 0; i < NUM_DIGITS; i++) {
      if (this.digits[i] > hi.digits[i]) {
        lessThan = false;
        break;
      } else if (this.digits[i] < hi.digits[i]) {
        lessThan = true;
        break;
      }
    }

    // Because negative numbers mess up literally everything, this negates
    // the result of the previous loop since it
    // doesn't account for negatives
    if (!this.positive && !hi.positive) {
      lessThan = !lessThan;
    }

    return lessThan;
  }

  /**
   * Checks to see if one Lab1.HugeInteger is greater than or equal to another one.
   *
   * @param hi an object of the Lab1.HugeInteger class to be used for comparison
   * @return whether the first Lab1.HugeInteger is greater than or equal to the
   *         second one
   */
  public boolean isGreaterThanOrEqualTo(HugeInteger hi) {
    // Since "Greater than or equal to" is a combination of "Greater than"
    // and "equal to," it
    // calls the individual methods for those comparisons
    if (this.isEqualTo(hi)) {
      return true;
    } else {
      return isGreaterThan(hi);
    }
  }

  /**
   * Checks to see if one Lab1.HugeInteger is less than or equal to another one.
   *
   * @param hi an object of the Lab1.HugeInteger class to be used for comparison
   * @return whether the first Lab1.HugeInteger is less than or equal to the second
   *         one
   */
  public boolean isLessThanOrEqualTo(HugeInteger hi) {
    // Since "Less than or equal to" is a combination of "Less than" and
    // "equal to," it
    // calls the individual methods for those comparisons
    if (this.isEqualTo(hi)) {
      return true;
    } else {
      return isLessThan(hi);
    }
  }

  /**
   * Negates the value of positive.
   */
  private void negate() {
    this.positive = !this.positive;
  }

  /**
   * Determines if the number is zero or not.
   *
   * @return true if it is zero, false if it isn't
   */
  private boolean isZero() {
    for (int i = 0; i < NUM_DIGITS; i++) {
      if (this.digits[i] != 0) {
        return false;
      }
    }
    return true;
  }

  /**
   * Converts the digits array to a string./
   *
   * @return a string version of the digits array
   */
  public String toString() {
    String digitStr = "";
    this.findMostSignificantDigit();
    for (int i = this.mostSignificantDigit; i < NUM_DIGITS; i++) {
      digitStr += this.digits[i];
    }

    if (!this.positive) {
      digitStr = "-" + digitStr;
    }
    return digitStr;
  }

  /**
   * Finds the left most significant digit and sets the mostSignificantDigit
   * variable to that index.
   */
  private void findMostSignificantDigit() {
    for (int i = 0; i < NUM_DIGITS; i++) {
      if (this.digits[i] != 0) {
        this.mostSignificantDigit = (byte) i;
        break;
      } else {
        this.mostSignificantDigit = NUM_DIGITS - 1;
      }
    }
  }

  /**
   * Adds two HugeIntegers together.
   *
   * @param hi a Lab1.HugeInteger that is added to the original Lab1.HugeInteger
   */
  public void add(HugeInteger hi) {
    if (this.isZero()) {
      this.digits = hi.digits;
      this.positive = hi.positive;
    }

    if (!this.isZero() && !hi.isZero()) {
      if (this.positive && hi.positive) {
        // (+) + (+) is regular addition
        this.digits = HugeInteger.addArrayDigits(this.digits, hi.digits);
      } else if (this.positive && !hi.positive) {
        // (+) + (-) = (+) - (+) which is subtraction
        if (!this.absoluteGreaterThan(hi)) {
          this.digits = HugeInteger.subtractArrayDigits(hi.digits, this.digits);
          this.negate();
        } else {
          this.digits = HugeInteger.subtractArrayDigits(this.digits, hi.digits);
        }
      } else if (!this.positive && hi.positive) {
        // (-) + (+) can be thought of as subtraction in terms of absolute
        // value i.e. -(5 - 4) = -1 or if the second operand is larger
        // (10 + (-5)) = (10 - 5) = 5
        if (!this.absoluteGreaterThan(hi)) {
          this.digits = HugeInteger.subtractArrayDigits(hi.digits, this.digits);
          this.negate();
        } else {
          this.digits = HugeInteger.subtractArrayDigits(this.digits, hi.digits);
        }
      } else if (!this.positive && !hi.positive) {
        // (-) + (-) can be thought of as addition in terms of absolute value
        // i.e. |-5 + (-5)| = 10
        this.digits = HugeInteger.addArrayDigits(this.digits, hi.digits);
      }
    }
  }

  /**
   * Subtracts two HugeIntegers.
   *
   * @param hi a Lab1.HugeInteger that is to be subtracted from the original
   *           Lab1.HugeInteger
   */
  public void subtract(HugeInteger hi) {
    if (this.isZero()) {
      this.digits = hi.digits;
      this.positive = !hi.positive;
    }

    if (!this.isZero() && !hi.isZero()) {
      if (this.positive && hi.positive) {
        // (+) - (+) is subtraction
        if (!this.isGreaterThan(hi)) {
          this.digits = HugeInteger.subtractArrayDigits(hi.digits, this.digits);
          this.negate();
        } else {
          this.digits = HugeInteger.subtractArrayDigits(this.digits, hi.digits);
        }
      } else if (this.positive && !hi.positive) {
        // (+) - (-) = (+) + (+) which is addition
        this.digits = HugeInteger.addArrayDigits(this.digits, hi.digits);
      } else if (!this.positive && hi.positive) {
        // (-) - (+) can be thought of as addition i.e. -(5 + 4) = -9
        this.digits = HugeInteger.addArrayDigits(this.digits, hi.digits);
      } else if (!this.positive && !hi.positive) {
        // (-) - (-) = -[(+) - (+)] which is subtraction with the result negated
        if (!this.absoluteGreaterThan(hi)) {
          this.digits = HugeInteger.subtractArrayDigits(hi.digits, this.digits);
          this.negate();
        } else {
          this.digits = HugeInteger.subtractArrayDigits(this.digits, hi.digits);
        }
      }
    }

  }

  /**
   * Multiplies two HugeIntegers.
   *
   * @param hi a Lab1.HugeInteger that is to be multiplied by the original
   *           Lab1.HugeInteger
   */
  public void multiply(HugeInteger hi) {
    // Initializes 2D array to store arrays for addition
    byte[][] listOfArrays = new byte[NUM_DIGITS][NUM_DIGITS];
    byte[] tempArray = new byte[NUM_DIGITS];
    byte carry = 0;

    // The adjustment variable is to add trailing zeroes to simulate the
    // position of numbers moving to the left in long multiplication
    for (int i = NUM_DIGITS - 1, adjustment = 0; i >= hi
        .mostSignificantDigit; i--, adjustment++) {
      // Zeroes out the array at the start of each loop to make sure nothing
      // remains from the previous round of multiplication
      for (int k = 0; k < NUM_DIGITS; k++) {
        tempArray[k] = 0;
      }
      // Loop goes 1 past the most significant digit so that the final carry
      // value is added to the end if there is one
      for (int j = NUM_DIGITS - 1; j >= this.mostSignificantDigit - 1; j--) {
        byte product = (byte) (hi.digits[i] * this.digits[j] + carry);
        carry = (byte) (product / 10);
        tempArray[j - adjustment] = (byte) (product % 10);
      }
      listOfArrays[i] = new byte[NUM_DIGITS];
      // Adds the tempArray to the listOfArrays for addition
      System.arraycopy(tempArray, 0, listOfArrays[i], 0, NUM_DIGITS);
      carry = 0;
    }

    byte[] finalArray = new byte[NUM_DIGITS];

    // Uses the already built addition method to add all the arrays together,
    // just like in long multiplication
    for (int i = 0; i < NUM_DIGITS; i++) {
      finalArray = HugeInteger.addArrayDigits(finalArray, listOfArrays[i]);
    }

    this.digits = finalArray;

    // If one of the numbers is negative, it turns the product negative
    // Does not check the case where the first number is negative and the
    // second is positive because no change needs to be made
    if (this.positive && !hi.positive) {
      this.negate();
      // If both numbers are negative, the product will be positive
    } else if (!this.positive && !hi.positive) {
      this.negate();
    }
  }

  /**
   * Finds if the absolute value of a Lab1.HugeInteger is greater than another one or
   * not.
   *
   * @param hi an object of the Lab1.HugeInteger class to be used for comparison
   * @return boolean value for whether the absolute value is greater than the
   *         other number or not
   */
  private boolean absoluteGreaterThan(HugeInteger hi) {
    this.findMostSignificantDigit();
    hi.findMostSignificantDigit();
    if (this.mostSignificantDigit < hi.mostSignificantDigit) {
      return true;
    } else if (this.mostSignificantDigit > hi.mostSignificantDigit) {
      return false;
    } else if (this.mostSignificantDigit == hi.mostSignificantDigit) {
      for (int i = mostSignificantDigit; i < NUM_DIGITS; i++) {
        if (this.digits[i] > hi.digits[i]) {
          return true;
        } else if (this.digits[i] < hi.digits[i]) {
          return false;
        }
      }
    }
    return false;
  }

  /**
   * Is a private method and not for outside users to call. This method
   * interprets array1 and array2 as two integers whose LSB digits are
   * at array1[NUM_DIGITS-1] and array2[NUM_DIGITS-1]. Without sign
   * information, array1 and array2 can only represent positive integers.
   *
   * <p>This method adds array1 and array2, and store the result in another
   * integer array for return.
   *
   * @param passedArray1 first integer
   * @param passedArray2 second integer
   * @return addition of array1 and array2
   */
  private static byte[] addArrayDigits(byte[] passedArray1, byte[]
      passedArray2) {
    // Creates all new locations in memory to make sure the original values
    // and arrays are not changed until I want them to be changed
    byte[] tempArray = new byte[NUM_DIGITS];
    byte[] array1 = new byte[NUM_DIGITS];
    byte[] array2 = new byte[NUM_DIGITS];
    System.arraycopy(passedArray1, 0, array1, 0, NUM_DIGITS);
    System.arraycopy(passedArray2, 0, array2, 0, NUM_DIGITS);
    byte carry = 0;

    for (int i = NUM_DIGITS - 1; i >= 0; i--) {
      byte sum = (byte) (array1[i] + array2[i] + carry);
      carry = (byte) (sum / 10);
      tempArray[i] = (byte) (sum % 10);
    }

    return tempArray;
  }

  /**
   * Is a private method and not for outside users to call. This method
   * interprets array1 and array2 as two integers whose LSB digits are
   * at array1[NUM_DIGITS-1] and array2[NUM_DIGITS-1]. Without sign
   * information, array1 and array2 can only represent positive integers.
   *
   * <p>This method subtracts array2 from array1, and store the result in
   * another integer array for return.
   *
   * <p>The assumption is the integer in array1 is greater than the one in
   * array2
   *
   * @param passedArray1 first integer
   * @param passedArray2 second integer
   * @return subtraction of array2 from array1
   */
  private static byte[] subtractArrayDigits(byte[] passedArray1, byte[]
      passedArray2) {
    // Creates all new locations in memory to make sure the original values
    // and arrays are not changed until I want them to be changed
    byte[] tempArray = new byte[NUM_DIGITS];
    byte[] array1 = new byte[NUM_DIGITS];
    byte[] array2 = new byte[NUM_DIGITS];
    System.arraycopy(passedArray1, 0, array1, 0, NUM_DIGITS);
    System.arraycopy(passedArray2, 0, array2, 0, NUM_DIGITS);
    byte borrow = 0;

    for (int i = NUM_DIGITS - 1; i >= 0; i--) {
      byte diff = (byte) (array1[i] - array2[i] - borrow);
      if (diff < 0) {
        borrow = 1;
        tempArray[i] = (byte) (diff + 10);
      } else {
        borrow = 0;
        tempArray[i] = diff;
      }
    }
    return tempArray;
  }

}
