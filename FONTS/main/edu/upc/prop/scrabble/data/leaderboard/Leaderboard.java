package edu.upc.prop.scrabble.data.leaderboard;

import edu.upc.prop.scrabble.persistence.runtime.data.PersistentArray;
import edu.upc.prop.scrabble.persistence.runtime.data.PersistentDictionary;
import edu.upc.prop.scrabble.persistence.runtime.interfaces.IPersistableObject;

import java.util.ArrayList;

/**
 * Classe encarregada d’emmagatzemar els registres {@link Score}
 * associats a les diferents partides jugades.
 * Aquesta classe permet afegir, obtenir i serialitzar la informació
 * dels resultats per a la seva persistència.
 * @author Felipe Martínez Lassalle
 * @see Score
 */

public class Leaderboard implements IPersistableObject {
    /**
     * Llista que conté tots els resultats {@link Score} registrats.
     */
    private final ArrayList<Score> leaderBoard = new ArrayList<>();

    /**
     * Crea una instància del rànquing.
     */
    public Leaderboard() {

    }

    /**
     * Retorna els resultats en forma d'array per facilitar-ne la gestió
     * i el seu ús des de capes superiors, com ara els controladors.
     * @return Un array de {@link Score} amb tots els resultats emmagatzemats.
     */
    public Score[] getScoreArray() { return leaderBoard.toArray(new Score[0]); }

    /**
     * Afegeix un nou resultat a la classificació.
     * @param score El nou {@link Score} a afegir.
     */
    public void addScore(Score score) { leaderBoard.add(score); }

    /**
     * Codifica els resultats emmagatzemats per tal de permetre la seva persistència.
     * Construeix un diccionari persistent amb la informació actual de la classificació.
     * @return Un {@link PersistentDictionary} amb les dades codificades de la classificació.
     * @see PersistentDictionary
     */
    @Override
    public PersistentDictionary encode() {
        PersistentDictionary data = new PersistentDictionary();

        PersistentArray leaderboard = new PersistentArray("leaderboard");
        for (Score score : leaderBoard)
            leaderboard.add(score);
        data.add(leaderboard);

        return data;
    }

    /**
     * Reconstrueix la classificació a partir de les dades desades en un diccionari persistent.
     * Els resultats recuperats s’afegeixen a la llista interna {@code leaderBoard}.
     * @param data El {@link PersistentDictionary} que conté les dades a deserialitzar.
     * @see PersistentDictionary
     */
    @Override
    public void decode(PersistentDictionary data) {
        PersistentArray leaderboard = (PersistentArray)data.get("leaderboard");
        for (int i = 0; i < leaderboard.getLength(); i++)
            this.leaderBoard.add(leaderboard.get(i, Score.class));
    }
}
