package labone;

//--Lab1.FuckingBullshit.java--//
public class FuckingBullshit implements Comparable<FuckingBullshit> {
  //Constants.
  private final static int N = 40; //Number of digits.

  //Attributes.
  private int digits[] = new int[N]; //Keeps the digits, element 0 is least significant.
  private boolean negative = false;  //Sign of number.
  private int msd = 0;               //Keeps the position of the most significant.

  //Empty constructor.
  public FuckingBullshit() {
    this(0);
  }

  //Copy constructor.
  public FuckingBullshit(int[] digits, boolean negative) {
    this.negative = negative;
    System.arraycopy(digits, 0, this.digits, 0, N);
  }

  //Construct a Lab1.FuckingBullshit from a long value. First element contains least significant
  //digit.
  public FuckingBullshit(long value) {
    //Signed?
    if (value < 0) {
      negative = true;
      value *= -1;
    }

    //Parse long value.
    for (int i = 0; i < N; i++) {
      digits[i] = (int) value % 10;
      value /= 10;
      if (value == 0) {
        msd = i;
        break;
      }
    }
  }

  //Construct a Lab1.FuckingBullshit from a String. First element contains least significant
  //digit.
  public FuckingBullshit(String s) throws NumberFormatException {
    //Check string.
    if (s == null) throw new NumberFormatException("'" + s + "' is not a number.");

    //Remove optional sign.
    if (s.startsWith("-")) {
      s = s.substring(1);
      negative = true;
    }

    //Check length
    int len = s.length();
    if (len > N) throw new NumberFormatException("Number is too large (max=" + N + ").");

    //Parse string to digits
    for (int i = 0; i < N; i++) {
      if (i < len) {
        digits[i] = Character.digit(s.charAt(len - i - 1), 10);
      } else {
        msd = i;
        break;
      }
    }
  }

  //Convert to string.
  public String toString() {
    boolean precedingZero = true;

    StringBuffer sb = new StringBuffer();

    //Signed?
    if (negative) {
      sb.append("-");
    }

    //Append from most significant to least significant.
    for (int i = N - 1; i >= 0; i--) {
      if (digits[i] > 0 || !precedingZero) {
        sb.append(Character.forDigit(digits[i], 10));
        precedingZero = false;
      }
    }

    //Zero?
    if (sb.length() == 0) {
      return "0";
    }

    return sb.toString();
  }

  //Compares 'this' with 'that'.
  //Compares 'this' with 'that'.
  public int compareTo(FuckingBullshit that) {
    //Check sign.
    if (this.negative && !that.negative) {
      return -1;
    }
    if (!this.negative && that.negative) {
      return 1;
    }

    //Check digits.
    for (int i = 0; i < N; i++) {
      if (this.digits[i] < that.digits[i]) {
        return (negative) ? 1 : -1;
      }
      if (this.digits[i] > that.digits[i]) {
        return (negative) ? -1 : 1;
      }
    }

    //Equal.
    return 0;
  }

  //Returns true if 'this' equals 'that'.
  public boolean isEqualTo(FuckingBullshit that) {
    return (this.compareTo(that) == 0);
  }

  //Returns true if 'this' does not equal 'that'.
  public boolean isNotEqualTo(FuckingBullshit that) {
    return (this.compareTo(that) != 0);
  }

  //Returns true if 'this' is (strict) greater than 'that';
  public boolean isGreaterThan(FuckingBullshit that) {
    return (this.compareTo(that) > 0);
  }

  //Returns true if 'this' is greater than or equal to 'that';
  public boolean isGreaterThanOrEqualTo(FuckingBullshit that) {
    return (this.compareTo(that) >= 0);
  }

  //Returns true if 'this' is less than or equal to 'that';
  public boolean isLessThan(FuckingBullshit that) {
    return (this.compareTo(that) < 0);
  }

  //Returns true if 'this' is less than or equal to 'that';
  public boolean isLessThanOrEqualTo(FuckingBullshit that) {
    return (this.compareTo(that) <= 0);
  }

  //Returns the absolute value of 'this'.
  public FuckingBullshit abs() {
    return new FuckingBullshit(this.digits, false);
  }

  //Returns the negation of 'this'.
  public FuckingBullshit negate() {
    return new FuckingBullshit(this.digits, !this.negative);
  }

  //Adds 'this' to 'that'.
  //
  // -this + -that == -(this + that) -> this.abs().add(that.abs()).negate()
  //  this +  that ==   this + that  -> this.add(that)
  // -this +  that ==   that - this  -> that.subtract(this.abs())
  //  this + -that ==   this - that  -> this.subtract(that.abs())
  public FuckingBullshit add(FuckingBullshit that) {
    //Check signs.
    if (this.negative && !that.negative)
      return that.subtract(this.abs());
    if (!this.negative && that.negative)
      return this.subtract(that.abs());

    //Keep track of the carry, start with least significant.
    int carry = 0;
    int result[] = new int[N];
    for (int i = 0; i < N; i++) {
      int sum = this.digits[i] + that.digits[i] + carry;
      carry = sum / 10;
      result[i] = sum % 10;
    }

    //Check sign.
    return new FuckingBullshit(result, this.negative && that.negative);
  }

  //Substracts 'this' from 'that'.
  //
  // -this - -that ==  -this + that  -> this.add(that.abs())
  //  this -  that ==   this - that  -> this.subtract(that)
  // -this -  that == -(this + that) -> this.abs().add(that).negate()
  //  this - -that ==   this + that  -> this.add(that.abs())
  public FuckingBullshit subtract(FuckingBullshit that) {
    //Check signs.
    if (this.negative && that.negative)
      return this.add(that.abs()); //This will become a subtract again, but in different order.
    if (this.negative && !that.negative)
      return this.abs().add(that).negate();
    if (!this.negative && that.negative)
      return this.add(that.abs());

    //Subtract the smaller operand from the bigger one.
    //
    // this - that && this > that ==   this - that
    // this - that && this < that == -(that - this)
    boolean thisBigger = this.isGreaterThan(that);
    FuckingBullshit bigger = thisBigger ? this : that;
    FuckingBullshit smaller = thisBigger ? that : this;

    //Keep track of the borrow, start with least significant.
    int borrow = 0;
    int result[] = new int[N];
    for (int i = 0; i < N; i++) {
      int diff = bigger.digits[i] - smaller.digits[i] - borrow;
      if (diff < 0) {
        borrow = 1;
        result[i] = diff + 10;
      } else {
        borrow = 0;
        result[i] = diff;
      }
    }

    //Check sign.
    return new FuckingBullshit(result, !thisBigger);
  }
}
