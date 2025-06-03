package edu.upc.prop.scrabble.presenter.swing.screens.menu;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.Random;

/**
 * Classe auxiliar que gestiona les peces flotants del menú principal.
 * Aquestes peces es mouen lliurement per la pantalla i poden col·lisionar entre elles.
 * @author Felipe Martínez Lassalle
 */
public class FloatingTile {
    /**
     * Dimensions del panell on es renderitzen les peces.
     */
    static int width, height, sidePanelWidth;
    /**
     * Lletra que conté la peça.
     */
    private String letter;
    /**
     * Coordenades de la peça dins del panell.
     */
    public float x, y;
    /**
     * Mida (costat) de la peça.
     */
    public int size;
    /**
     * Desplaçament calculat en cada actualització de moviment.
     */
    private float dx, dy;
    /**
     * Velocitats de la peça.
     */
    public float speed, prevSpeed;
    /**
     * Direcció vertical del moviment (-1 a 1).
     */
    public float verticalDir;
    /**
     * Direcció horitzontal del moviment (-1 a 1).
     */
    public float horizontalDir;
    /**
     * Grau de rotació actual de la peça.
     */
    private double rotation;
    /**
     * Velocitat de rotació de la peça (graus per segon).
     */
    private final double rotationSpeed;

    /**
     * Constructor de la peça flotant.
     * Inicialitza els valors de posició, mida, direccions aleatòries, velocitat i rotació.
     *
     * @param letter Lletra que mostrarà la peça.
     * @param x      Posició inicial horitzontal.
     * @param y      Posició inicial vertical.
     * @param size   Mida de la peça.
     */
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
        double aux = rand.nextDouble(-90, 90);
        if(aux == 0) aux = 1.0;
        rotationSpeed = aux;
        rotation = rand.nextDouble(-360, 360);
    }

    /**
     * Actualitza la posició de la peça en funció del temps passat.
     *
     * @param delta Temps transcorregut en segons.
     */
    public void move(float delta) {
        if (speed > prevSpeed) speed -= 2;
        else if (speed < prevSpeed && speed != 0) speed += 2;
        dx = horizontalDir * speed * delta;
        dy = verticalDir * speed * delta;

        rotation += rotationSpeed * delta;

        if (x + dx < sidePanelWidth || x + dx + size > width) horizontalDir *= -1;
        else x += dx;

        if (y + dy < 0 || y + dy + size > height) verticalDir *= -1;
        else y += dy;
    }

    /**
     * Comprova si dues peces es creuen com a cercles (per detectar col·lisions).
     *
     * @param x1 Coordenada X del primer centre.
     * @param y1 Coordenada Y del primer centre.
     * @param r1 Radi del primer cercle.
     * @param x2 Coordenada X del segon centre.
     * @param y2 Coordenada Y del segon centre.
     * @param r2 Radi del segon cercle.
     * @return Cert si els cercles es tallen.
     */
    private boolean intersect(float x1, float y1, float r1, float x2, float y2, float r2) {
        float dx = x2 - x1;
        float dy = y2 - y1;
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        return distance <= (r1 + r2) && distance >= Math.abs(r1 - r2);
    }

    /**
     * Comprova i gestiona la col·lisió amb una altra peça.
     * Ajusta la direcció i velocitat de totes dues peces.
     *
     * @param other L'altra peça amb la qual pot col·lidir.
     */
    public void checkCollision(FloatingTile other) {
        if (intersect(x, y, size / 2f, other.x, other.y, other.size / 2f)) {
            float middlePointX = (x + other.x) / 2f;
            float middlePointY = (y + other.y) / 2f;

            float speed = (this.speed + other.speed) / 4;
            this.speed = this.speed/2 + speed;
            other.speed = other.speed/2 + speed;

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


    /**
     * Dibuixa la peça a la pantalla amb rotació i estil.
     *
     * @param g2 Objecte gràfic per al dibuix.
     */
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

    /**
     * Comprova si una peça se solapa amb qualsevol altra d'una llista.
     *
     * @param tile  Peça a comprovar.
     * @param others Llista de peces amb les quals comparar.
     * @return Cert si se solapa amb alguna altra peça.
     */
    public boolean overlapsAny(FloatingTile tile, java.util.List<FloatingTile> others) {
        Rectangle2D.Float r1 = new Rectangle2D.Float(tile.x, tile.y, tile.size, tile.size);
        for (FloatingTile other : others) {
            Rectangle2D.Float r2 = new Rectangle2D.Float(other.x, other.y, other.size, other.size);
            if (r1.intersects(r2)) return true;
        }
        return false;
    }

    /**
     * Retorna una cadena de text aleatòria d'un conjunt predefinit.
     *
     * @return Cadena de text aleatòria.
     */
    static public String getRandomString() {
        String[] names = {
                "GERARD", "BIELGINA", "ALBERT", "FELIPE",
                "LAKESCHAN", "SCRABBLE", "WORDPLAY", "SKIBIDI"
        };
        Random rand = new Random();
        int index = rand.nextInt(names.length);
        return names[index];
    }

    /**
     * Comprova si un punt es troba dins la peça.
     *
     * @param px Coordenada X del punt.
     * @param py Coordenada Y del punt.
     * @return Cert si el punt es troba dins els límits de la peça.
     */
    public boolean contains(int px, int py) {
        return px >= x && px <= x + size && py >= y && py <= y + size;
    }

    /**
     * Comprova si aquesta peça se solapa amb una altra.
     *
     * @param other L'altra peça.
     * @return Cert si hi ha solapament entre les dues peces.
     */
    public boolean overlaps(FloatingTile other) {
        return this != other &&
                this.x < other.x + other.size &&
                this.x + this.size > other.x &&
                this.y < other.y + other.size &&
                this.y + this.size > other.y;
    }

}

