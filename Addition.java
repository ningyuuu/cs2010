import java.io.*;
import java.util.*;
import java.math.BigInteger;

public class Addition { // as the class name that contains the main method is "Addition", you have to save this file as "Addition.java", and submit "Addition.java" to Codecrunch
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    BigInteger firstInt;
    BigInteger secondInt;
    String[] nextLine;
    while (true) {
      // Here is the full solution for this super simple practice task in pseudo code
      // read two integers A and B
      nextLine = sc.nextLine().split(" ");
      firstInt = new BigInteger(nextLine[0]);
      secondInt = new BigInteger(nextLine[1]);
      // if both are -1, stop
      if (firstInt.intValue() == -1 && secondInt.intValue() == -1) {
        break;
      }
      // output A+B
      System.out.println(firstInt.add(secondInt));
    }
  }
}
