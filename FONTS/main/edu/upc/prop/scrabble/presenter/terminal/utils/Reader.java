package edu.upc.prop.scrabble.presenter.terminal.utils;

import edu.upc.prop.scrabble.presenter.scenes.SceneManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Reader {
    private final Scanner scanner = new Scanner(System.in);

    public String readLine() {
        try {
            if (System.in.available() > 0) {
                return scanner.nextLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
}
