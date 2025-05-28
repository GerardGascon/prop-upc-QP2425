package edu.upc.prop.scrabble.presenter.swing.screens.menu;

import java.awt.*;
import java.util.Random;

public class FloatingTile {
    static int width, height, sidePanelWidth;
    private String letter;
    private float x, y;
    private int size;
    private float dx, dy;
    private float speed;
    private float verticalDir;
    private float horizontalDir;

    public FloatingTile(String letter, int x, int y, int size) {
        this.letter = letter;
        this.x = x;
        this.y = y;
        this.size = size;
        Random rand = new Random();

        speed = rand.nextFloat(150, 250);
        horizontalDir = rand.nextFloat(-1, 1);
        verticalDir = rand.nextFloat(-1, 1);
    }

    public void move(float delta) {
        dx = horizontalDir * speed * delta;
        dy = verticalDir * speed * delta;
        x += dx;
        y += dy;

        if (x < sidePanelWidth || x + size > width) horizontalDir *= -1;
        if (y < 0 || y + size > height) verticalDir *= -1;
    }

    private boolean intersect(float x1, float y1, float r1, float x2, float y2, float r2) {
        float dx = x2 - x1;
        float dy = y2 - y1;
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        return distance <= (r1 + r2) && distance >= Math.abs(r1 - r2);
    }

    public void checkCollision(FloatingTile other) {
        if (intersect(x, y, size / 2f, other.x, other.y, other.size / 2f)) {
            float middlePointX = (x + other.x) / 2f;
            float middlePointY = (y + other.y) / 2f;

            float speed = this.speed;
            this.speed = other.speed;
            other.speed = speed;

            if (middlePointX > x){
                horizontalDir = -Math.abs(horizontalDir);
            }else if (middlePointX < x){
                horizontalDir = Math.abs(horizontalDir);
            }else{
                horizontalDir = 0;
            }
            if (middlePointY > y){
                verticalDir = -Math.abs(verticalDir);
            }else if (middlePointY < y){
                verticalDir = Math.abs(verticalDir);
            }else{
                verticalDir = 0;
            }

            if (middlePointX > other.x){
                other.horizontalDir = -Math.abs(other.horizontalDir);
            }else if (middlePointX < other.x){
                other.horizontalDir = Math.abs(other.horizontalDir);
            }else{
                other.horizontalDir = 0;
            }
            if (middlePointY > other.y){
                other.verticalDir = -Math.abs(other.verticalDir);
            }else if (middlePointY < other.y){
                other.verticalDir = Math.abs(other.verticalDir);
            }else{
                other.verticalDir = 0;
            }
        }
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.ORANGE);
        g2.fillRoundRect((int) x, (int) y, size, size, 10, 10);
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("SansSerif", Font.BOLD, size / 2));
        FontMetrics fm = g2.getFontMetrics();
        int tx = (int) x + (size - fm.stringWidth(letter)) / 2;
        int ty = (int) y + (size + fm.getAscent()) / 2 - 4;
        g2.drawString(letter, tx, ty);
    }
}

