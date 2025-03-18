package edu.upc.prop.scrabble.presenter.terminal;

import edu.upc.prop.scrabble.domain.IMovementReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MovementReader implements IMovementReader {
    @Override
    public String readMove() {
        return readMovement();
    }

    private static String readMovement() {
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));

        String s;
        try {
            s = r.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return s;
    }
}
