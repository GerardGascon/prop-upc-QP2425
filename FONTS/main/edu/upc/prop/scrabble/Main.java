package edu.upc.prop.scrabble;

import com.google.gson.Gson;
import edu.upc.prop.scrabble.data.board.StandardBoard;

public class Main {
  public static void main(String[] args) {
    StandardBoard board = new StandardBoard();
    System.out.println(board);
  }

  public float division(int a, int b) throws ArithmeticException {
    return a/b;
  }
}