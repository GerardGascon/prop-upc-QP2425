package edu.upc.prop.scrabble.domain;

import edu.upc.prop.scrabble.data.LeaderBoard;

public class LeaderBoardHandler {
    final LeaderBoard leaderBoard;

    public LeaderBoardHandler(){ leaderBoard = new LeaderBoard(); }

    public LeaderBoardHandler(LeaderBoard leaderBoard){ this.leaderBoard = leaderBoard; }

}
