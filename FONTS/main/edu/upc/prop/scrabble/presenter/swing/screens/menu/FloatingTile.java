package edu.upc.prop.scrabble.presenter.swing.screens.menu;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class FloatingTile {
    static int width, height, sidePanelWidth;
    private String letter;
    public float x, y;
    public int size;
    private float dx, dy;
    public float speed, prevSpeed;
    public float verticalDir;
    public float horizontalDir;
    private double rotation;
    private final double rotationSpeed;

    public FloatingTile(String letter, int x, int y, int size) {
        this.letter = letter;
        this.x = x;
        this.y = y;
        this.size = size;
        Random rand = new Random();

        speed = rand.nextFloat(200, 300);
        prevSpeed = speed;
        horizontalDir = rand.nextFloat(-1, 1);
        verticalDir = rand.nextFloat(-1, 1);
        rotationSpeed = rand.nextDouble(-90, 90);
        rotation = rand.nextDouble(-360, 360);
    }

    public void move(float delta) {
        if (speed > prevSpeed) speed -= 2;

        dx = horizontalDir * speed * delta;
        dy = verticalDir * speed * delta;
        x += dx;
        y += dy;

        rotation += rotationSpeed * delta;

        if (x < sidePanelWidth || x + size > width) horizontalDir *= -1;
        if (y < 0 || y + size > height) verticalDir *= -1;

        if (dx > -0.5 && dx < 0.5) dx = 3;
        if (dy > -0.5 && dy < 0.5) dy = -3;
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
        AffineTransform original = g2.getTransform();

        double cx = x + size / 2.0;
        double cy = y + size / 2.0;

        g2.rotate(Math.toRadians(rotation), cx, cy);

        g2.setColor(new Color(0xff, 0xf9, 0xb5));
        int arc = (int)(size * 0.4);
        g2.fillRoundRect((int) x, (int) y, size, size, arc, arc);

        g2.setColor(Color.BLACK);
        g2.setFont(new Font("SansSerif", Font.BOLD, size / 2));
        FontMetrics fm = g2.getFontMetrics();
        int tx = (int) x + (size - fm.stringWidth(letter)) / 2;
        int ty = (int) y + (size + fm.getAscent()) / 2 - 4;
        g2.drawString(letter, tx, ty);

        g2.setTransform(original);
    }

    public boolean overlapsAny(FloatingTile tile, java.util.List<FloatingTile> others) {
        Rectangle2D.Float r1 = new Rectangle2D.Float(tile.x, tile.y, tile.size, tile.size);
        for (FloatingTile other : others) {
            Rectangle2D.Float r2 = new Rectangle2D.Float(other.x, other.y, other.size, other.size);
            if (r1.intersects(r2)) return true;
        }
        return false;
    }

    static public String getRandomString() {
        String[] names = {
                "GERARD", "BIELGINA", "ALBERT", "FELIPE",
                "LAKESCHAN", "SCRABBLE", "WORDPLAY", "SKIBIDI"
        };
        Random rand = new Random();
        int index = rand.nextInt(names.length);
        return names[index];
    }

    public boolean contains(int px, int py) {
        return px >= x && px <= x + size && py >= y && py <= y + size;
    }

    public boolean overlaps(FloatingTile other) {
        return this != other &&
                this.x < other.x + other.size &&
                this.x + this.size > other.x &&
                this.y < other.y + other.size &&
                this.y + this.size > other.y;
    }

}

