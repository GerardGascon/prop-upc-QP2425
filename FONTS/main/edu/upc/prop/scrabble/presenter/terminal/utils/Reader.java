package edu.upc.prop.scrabble.presenter.terminal.utils;

import java.util.Scanner;
import java.util.concurrent.*;

public class Reader {
    private final Scanner scanner = new Scanner(System.in);
    private final BlockingQueue<String> inputQueue = new LinkedBlockingQueue<>();

    public Reader() {
        Thread inputThread = new Thread(() -> {
            while (true) {
                String line = scanner.nextLine();
                inputQueue.offer(line);
            }
        });
        inputThread.setDaemon(true);
        inputThread.start();
    }

    public String readLine() {
        return inputQueue.poll();
    }
}
