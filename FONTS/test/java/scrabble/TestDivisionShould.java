package java.scrabble;

import static org.junit.Assert.assertEquals;

import edu.upc.prop.scrabble.Main;
import org.junit.Test;

public class TestDivisionShould {
  @Test
  public void divideGettingCorrectResult() {
    Main divisionMachine = new Main();

    float actualResult = divisionMachine.division(10,2);
    assertEquals(5.0, actualResult, 0.0001);
  }

  @Test(expected = ArithmeticException.class)
  public void divideByZeroThrowsException() {
    Main divisionMachine = new Main();

    float actualResult = divisionMachine.division(10,0);

  }
}