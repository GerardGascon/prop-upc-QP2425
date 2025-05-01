package edu.upc.prop.scrabble.data.leaderboard;

import edu.upc.prop.scrabble.persistence.runtime.data.PersistentArray;
import edu.upc.prop.scrabble.persistence.runtime.data.PersistentDictionary;
import edu.upc.prop.scrabble.persistence.runtime.data.PersistentObject;
import edu.upc.prop.scrabble.persistence.runtime.interfaces.IPersistableObject;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class used to store Score ArrayList
 * @author Felipe Martínez Lassalle
 */
public class Leaderboard implements IPersistableObject {
    private ArrayList<Score> leaderBoard = new ArrayList<>();

    /**
     * Transforms the ArrayList to a regular array to ease controllers functions
     * @return array of the stored Score
     * @see Score
     */
    public Score[] getScoreArray() { return leaderBoard.toArray(new Score[0]); }

    /**
     * Adds a Score to the Leaderboard ArrayList
     * @param score Score to add to Leaderboard
     * @see Score
     */
    public void addScore(Score score) { leaderBoard.add(score); }

    @Override
    public PersistentDictionary encode() {
        PersistentDictionary data = new PersistentDictionary();
        data.add(new PersistentObject("leaderboard", leaderBoard.toArray(Score[]::new)));
        return data;
    }

    @Override
    public void decode(PersistentDictionary data) {
        PersistentObject leaderboard = data.get("leaderboard");
        leaderBoard.addAll(Arrays.stream(leaderboard.parse(Score[].class)).toList());
    }
}
