package edu.upc.prop.scrabble.data.leaderboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Leaderboard {
    private ArrayList<Score> leaderBoard;

    public Leaderboard() { this.leaderBoard = new ArrayList<>(); }

    public Leaderboard(Score score) { this.leaderBoard = new ArrayList<>(Collections.singletonList(score)); }

    public Leaderboard(Score[] scoreArray) { this.leaderBoard = (ArrayList<Score>)  Arrays.stream(scoreArray).toList(); }

    public Score[] getScoreArray() { return leaderBoard.toArray(new Score[0]); }

    public ArrayList<Score> getLeaderBoard() { return leaderBoard; }

    public void addScore(Score score) { leaderBoard.add(score); }

    public void addScores(Score[] scores) { leaderBoard.addAll(Arrays.asList(scores)); }

}
