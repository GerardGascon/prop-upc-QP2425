package edu.upc.prop.scrabble.data.leaderboard;

import java.util.ArrayList;

/**
 * Class used to store Score ArrayList
 * @author Felipe Martínez Lassalle
 */
public class Leaderboard {
    private final ArrayList<Score> leaderBoard;

    public Leaderboard() { this.leaderBoard = new ArrayList<>(); }

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
}
