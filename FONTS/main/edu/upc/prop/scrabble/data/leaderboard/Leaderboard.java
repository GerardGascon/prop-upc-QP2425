package edu.upc.prop.scrabble.data.leaderboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Class used to store Score ArrayList
 * @author Felipe Martínez Lassalle
 */
public class Leaderboard {
    private ArrayList<Score> leaderBoard;

    public Leaderboard() { this.leaderBoard = new ArrayList<>(); }
    public Leaderboard(Score score) { this.leaderBoard = new ArrayList<>(Collections.singletonList(score)); }
    public Leaderboard(Score[] scoreArray) { this.leaderBoard = (ArrayList<Score>)  Arrays.stream(scoreArray).toList(); }

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
