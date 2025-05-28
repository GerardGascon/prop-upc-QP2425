package edu.upc.prop.scrabble.presenter.swing.screens.menu;

import java.awt.*;
import java.util.Random;

public class FloatingTile {
    static int width, height, sidePanelWidth;
    private String letter;
    private int x, y, size;
    private int dx, dy;
    private final int SPEED = 2;

    public FloatingTile(String letter, int x, int y, int size) {
        this.letter = letter;
        this.x = x;
        this.y = y;
        this.size = size;
        Random rand = new Random();
        this.dx = rand.nextBoolean() ? SPEED : -SPEED;
        this.dy = rand.nextBoolean() ? SPEED : -SPEED;
    }

    public void move() {
        x += dx;
        y += dy;

        if (x < sidePanelWidth || x + size > width) dx *= -1;
        if (y < 0 || y + size > height) dy *= -1;
    }

    public void checkCollision(FloatingTile other) {
        Rectangle r1 = new Rectangle(x, y, size, size);
        Rectangle r2 = new Rectangle(other.x, other.y, other.size, other.size);

        if (r1.intersects(r2)) {
            // Push tiles apart slightly to prevent sticking
            if (dx != 0) x -= dx;
            if (dy != 0) y -= dy;

            // More realistic collision response (conserves momentum)
            int tempDx = dx;
            int tempDy = dy;

            dx = other.dx;
            dy = other.dy;

            other.dx = tempDx;
            other.dy = tempDy;

            // Ensure tiles don't get stuck by moving them apart
            if (Math.abs(x - other.x) < size) {
                x += (dx > 0) ? 1 : -1;
                other.x += (other.dx > 0) ? 1 : -1;
            }
            if (Math.abs(y - other.y) < size) {
                y += (dy > 0) ? 1 : -1;
                other.y += (other.dy > 0) ? 1 : -1;
            }
        }
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.ORANGE);
        g2.fillRoundRect(x, y, size, size, 10, 10);
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("SansSerif", Font.BOLD, size / 2));
        FontMetrics fm = g2.getFontMetrics();
        int tx = x + (size - fm.stringWidth(letter)) / 2;
        int ty = y + (size + fm.getAscent()) / 2 - 4;
        g2.drawString(letter, tx, ty);
    }
}

