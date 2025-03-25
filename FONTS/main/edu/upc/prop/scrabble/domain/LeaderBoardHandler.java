package edu.upc.prop.scrabble.domain;

import java.util.Map;
import java.util.TreeMap;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import edu.upc.prop.scrabble.data.LeaderBoard;
import edu.upc.prop.scrabble.data.Score;

public class LeaderBoardHandler {
    private final Score[] leaderBoardH;

    public LeaderBoardHandler(){ leaderBoardH = new Score[0]; }

    public LeaderBoardHandler(LeaderBoard leaderBoard){ this.leaderBoardH = leaderBoard.getLeaderBoard(); }

}
