package edu.upc.prop.scrabble.domain;

import java.util.Map;
import java.util.TreeMap;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import edu.upc.prop.scrabble.data.LeaderBoard;
import edu.upc.prop.scrabble.data.Score;

public class LeaderBoardHandler {
    private final LeaderBoard leaderBoardH;

    public LeaderBoardHandler(){ leaderBoardH = new LeaderBoard(); }

    public LeaderBoardHandler(LeaderBoard leaderBoard){ this.leaderBoardH = leaderBoard; }

    public List<String> getRank(int rank) {

        Map<Integer, List<String>> auxMap = new TreeMap<>();
        Score[] auxArray = leaderBoardH.getLeaderBoard();
        for (Score score : auxArray) {
            if(auxMap.containsKey(score.getScoreValue())) {
                auxMap.get(score.getScoreValue()).add(score.getPlayerName());
            } else {
                List<String> auxList = new ArrayList<>();
                auxList.add(score.getPlayerName());
                auxMap.put(score.getScoreValue(), auxList);
            }
        }

        Iterator<Map.Entry<Integer, List<String>>> iterator = auxMap.entrySet().iterator();
        int rank_i = 0;
        while (iterator.hasNext()) {
            Map.Entry<Integer, List<String>> entry = iterator.next();
            if (rank_i == rank - 1) {
                return entry.getValue();
            }
            rank_i++;
        }
        // Non existent rank
        return new ArrayList<>();
    }
}
