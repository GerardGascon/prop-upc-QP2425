package edu.upc.prop.scrabble.data.leaderboard;

import edu.upc.prop.scrabble.persistence.runtime.data.PersistentArray;
import edu.upc.prop.scrabble.persistence.runtime.data.PersistentDictionary;
import edu.upc.prop.scrabble.persistence.runtime.data.PersistentObject;
import edu.upc.prop.scrabble.persistence.runtime.interfaces.IPersistableObject;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Classe utilitzada per emmagatzemar els Score
 * de les diferents partides jugades
 * @author Felipe Martínez Lassalle
 * @see Score
 */
public class Leaderboard implements IPersistableObject {
    /**
     * Estructura utilitzada per l'emmagatzematge
     */
    private ArrayList<Score> leaderBoard = new ArrayList<>();

    /**
     * Transforma l'ArrayList leaderBoard en una array regular
     * amb l'objectiu de facilitar la gestió als controladors
     * @return array que conté tots els Score emmagatzemats
     * @see Score
     */
    public Score[] getScoreArray() { return leaderBoard.toArray(new Score[0]); }

    /**
     * Afegeix un Score a l'ArrayList leaderBoard
     * @param score Score a afegir
     * @see Score
     */
    public void addScore(Score score) { leaderBoard.add(score); }

    /**
     * Transforma les dades que emmagatzema la leaderBoard per ser guardades
     * @return Dades que poden ser afegides al diccionari persistent
     * @see PersistentDictionary
     */
    @Override
    public PersistentDictionary encode() {
        PersistentDictionary data = new PersistentDictionary();
        data.add(new PersistentObject("leaderboard", leaderBoard.toArray(Score[]::new)));
        return data;
    }

    /**
     * Emmagatzema les dades relacionades guardades al diccionari persistent a la leaderBoard
     * @see PersistentDictionary
     */
    @Override
    public void decode(PersistentDictionary data) {
        PersistentObject leaderboard = data.get("leaderboard");
        leaderBoard.addAll(Arrays.stream(leaderboard.parse(Score[].class)).toList());
    }
}
