package edu.upc.prop.scrabble;

import com.google.gson.Gson;
import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.data.board.JuniorBoard;
import edu.upc.prop.scrabble.data.board.StandardBoard;
import edu.upc.prop.scrabble.data.board.SuperBoard;

public class Main {
  public static void main(String[] args) {
    Board board = new SuperBoard();
    System.out.println(board);
  }

  public float division(int a, int b) throws ArithmeticException {
    return a/b;
  }
}