package edu.upc.prop.scrabble.data;

import edu.upc.prop.scrabble.data.dawg.Node;
import edu.upc.prop.scrabble.utils.Vector2;

import java.util.ArrayList;

/**
 * Classe que representa el conjunt de caselles *anchor* actives del tauler.
 * Una casella *anchor* és una posició des de la qual es pot iniciar una jugada nova.
 * Aquest conjunt s'utilitza per determinar els punts vàlids d'inici de paraules
 * segons l'estat actual del joc.
 * @author Albert Usero
 * @author Felipe Martínez Lassalle
 */
public class Anchors {
    /**
     * Llista de coordenades de les caselles *anchor*.
     */
    private final ArrayList<Vector2> anchors;

    /**
     * Crea un conjunt buit de caselles *anchor*.
     */
    public Anchors() {
        this.anchors = new ArrayList<>();
    }

    /**
     * Afegeix una nova casella *anchor* al conjunt.
     * @param x Coordenada X de la casella a afegir.
     * @param y Coordenada Y de la casella a afegir.
     */
    public void addAnchor(int x, int y) {
        anchors.add(new Vector2(x, y));
    }

    /**
     * Elimina una casella *anchor* del conjunt.
     * @param x Coordenada X de la casella a eliminar.
     * @param y Coordenada Y de la casella a eliminar.
     */
    public void removeAnchor(int x, int y) {
        anchors.remove(new Vector2(x, y));
    }

    /**
     * Retorna el nombre total de caselles *anchor* actuals.
     * @return La mida del conjunt d'*anchors*.
     */
    public int getSize() {
        return anchors.size();
    }

    /**
     * Obté la casella *anchor* en una posició específica del conjunt.
     * @param position Índex dins la llista d'*anchors*.
     * @return L’objecte {@link Vector2} amb les coordenades de la casella corresponent.
     * @throws IndexOutOfBoundsException si la posició és invàlida.
     */
    public Vector2 getAnchor(int position) {
        return anchors.get(position);
    }

    /**
     * Comprova si una casella determinada existeix com a *anchor* al conjunt.
     * @param x Coordenada X de la casella a comprovar.
     * @param y Coordenada Y de la casella a comprovar.
     * @return {@code true} si la casella és un *anchor* vàlid, {@code false} altrament.
     */
    public boolean exists(int x, int y) {
        return anchors.contains(new Vector2(x, y));
    }

    /**
     * Genera una nova instància de {@code Anchors} amb les caselles *anchor* rotades
     * 90 graus en sentit horari, utilitzant la mida del tauler com a referència.
     * @param boardSize Mida lateral del tauler (assumint tauler quadrat).
     * @return Un nou objecte {@code Anchors} amb les coordenades rotades.
     */
    public Anchors rotate(int boardSize) {
        Anchors rotated = new Anchors();
        for (Vector2 anchor : this.anchors) {
            // Rotació horària: (x, y) -> (y, boardSize - x - 1)
            rotated.addAnchor(anchor.y, boardSize - anchor.x - 1);
        }
        return rotated;
    }
}
