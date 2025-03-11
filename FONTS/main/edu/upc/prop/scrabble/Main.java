package edu.upc.prop.scrabble;

import com.google.gson.Gson;

public class Main {
  public static void main(String[] args) {
    System.out.println("Hello world!");
    new Gson();

    Main divisioner = new Main();
    System.out.println("Dividing 10 by 2 is " + divisioner.division(10,2));
  }

  public float division(int a, int b) throws ArithmeticException {
    return a/b;
  }
}