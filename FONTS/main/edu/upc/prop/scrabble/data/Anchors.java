package edu.upc.prop.scrabble.data;

import edu.upc.prop.scrabble.data.dawg.Node;
import edu.upc.prop.scrabble.utils.Vector2;

import java.util.ArrayList;

/**
 * Classe que representa les caselles anchors actuals del board
 * Una casella anchor és una casella sobre la qual es pot iniciar un moviment.
 * @author Albert Usero
 * @author Felipe Martínez Lassalle
 */

public class Anchors {
    /**
     * Conjunt d'anchors
     */
    private final ArrayList<Vector2> anchors;

    /**
     * Crea un conjunt d'anchors buit
     */
    public Anchors() { this.anchors = new ArrayList<>(); }

    /**
     * Afegeix una casella anchor al conjunt
     * @param x Coordenada x de la casella a afegir
     * @param y Coordenada y de la casella a afegir
     */
    public void addAnchor(int x, int y) {
        anchors.add(new Vector2(x, y));
    }

    /**
     * Elimina una casella anchor al conjunt
     * @param x Coordenada x de la casella a eliminar
     * @param y Coordenada y de la casella a eliminar
     */
    public void removeAnchor(int x, int y) {
        anchors.remove(new Vector2(x, y));
    }

    /**
     * @return Quantitat de caselles anchors actuals
     */
    public int getSize() {
        return anchors.size();
    }

    /**
     * @return Vector2 de les coordenades de l'anchor a la posició de l'entrada
     * @param position Posició a l'array d'anchors de l'achor a retornar
     */
    public Vector2 getAnchor(int position) {
        return anchors.get(position);
    }

    /**
     * @return True si existeix l'anchor a comprovar, false altrament
     * @param x Coordenada x de la casella a comprovar
     * @param y Coordenada y de la casella a comprovar
     */
    public boolean exists(int x, int y) {
        return anchors.contains(new Vector2(x, y));
    }

    /**
     * Rota les anchors i retorna un nou conjunt d'anchors als quals s'ha realitzat la rotació
     * @param boardSize Mida del board actual
     * @return Nova instància d'Anchors girats 90 graus en sentit horari
     */
    public Anchors rotate(int boardSize) {
        Anchors rotated = new Anchors();
        for (Vector2 anchor : this.anchors){
            //noinspection SuspiciousNameCombination
            rotated.addAnchor(anchor.y, boardSize - anchor.x - 1);
        }
        return rotated;
    }
}