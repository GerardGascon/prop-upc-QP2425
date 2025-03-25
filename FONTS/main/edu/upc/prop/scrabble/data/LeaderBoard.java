package edu.upc.prop.scrabble.data;

import java.util.Arrays;

public class LeaderBoard {
    private Score[] leaderBoard;

    public LeaderBoard() {
        this.leaderBoard = new Score[0];
    }

    public LeaderBoard(Score score) {
        this.leaderBoard = new Score[]{score};
    }

    public LeaderBoard(Score[] scoreArray) {
        this.leaderBoard = java.util.Arrays.copyOf(scoreArray, scoreArray.length);
    }

    public Score[] getLeaderBoard() { return leaderBoard; }

    public void addScore(Score score) {
        this.leaderBoard = Arrays.copyOf(this.leaderBoard, this.leaderBoard.length + 1);
        this.leaderBoard[this.leaderBoard.length - 1] = score;
    }
}
