package edu.upc.prop.scrabble.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class LeaderBoard {
    private ArrayList<Score> leaderBoard;

    public LeaderBoard() { this.leaderBoard = new ArrayList<>(); }

    public LeaderBoard(Score score) { this.leaderBoard = new ArrayList<>(Collections.singletonList(score)); }

    public LeaderBoard(Score[] scoreArray) { this.leaderBoard = (ArrayList<Score>)  Arrays.stream(scoreArray).toList(); }

    public Score[] getScoreArray() { return leaderBoard.toArray(new Score[0]); }

    public ArrayList<Score> getLeaderBoard() { return leaderBoard; }

    public void addScore(Score score) { leaderBoard.add(score); }

    public void addScores(Score[] scores) { leaderBoard.addAll(Arrays.asList(scores)); }

}
