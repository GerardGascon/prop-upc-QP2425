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
}
/*public class LeaderBoard {
    private Map<Integer, List<String>> leaderBoard;
    private Map<String, Integer> playerScores;

    public LeaderBoard() { this.leaderBoard = new TreeMap<>(); }
    public LeaderBoard(Score score) {
        this.leaderBoard = new TreeMap<>();
        List<String> aux = new ArrayList<>();
        aux.add(score.getPlayerName());
        leaderBoard.put(score.getScoreValue(), aux);
        playerScores.put(score.getPlayerName(), score.getScoreValue());
    }
    public LeaderBoard(List<Score> scoreList) {
        this.leaderBoard = new TreeMap<>();
        for (Score score : scoreList) {
            if(leaderBoard.containsKey(score.getScoreValue())) {
                leaderBoard.get(score.getScoreValue()).add(score.getPlayerName());
            } else {
                List<String> aux = new ArrayList<>();
                aux.add(score.getPlayerName());
                leaderBoard.put(score.getScoreValue(), aux);
            }
            playerScores.put(score.getPlayerName(), score.getScoreValue());
        }
    }

    public void addPlayer(Score score) {
        List<String> aux = new ArrayList<>();
        aux.add(score.getPlayerName());
        leaderBoard.put(score.getScoreValue(), aux);
        playerScores.put(score.getPlayerName(), score.getScoreValue());
    }

    public void addPlayers(List<Score> scoreList) {
        for (Score score : scoreList) {
            if(leaderBoard.containsKey(score.getScoreValue())) {
                leaderBoard.get(score.getScoreValue()).add(score.getPlayerName());
            } else {
                List<String> aux = new ArrayList<>();
                aux.add(score.getPlayerName());
                leaderBoard.put(score.getScoreValue(), aux);
            }
            playerScores.put(score.getPlayerName(), score.getScoreValue());
        }
    }

    public Map<Integer, List<String>> getLeaderBoard() { return leaderBoard; }

    public int getPlayerScore(String playerName) { return  playerScores.get(playerName); }

    public List<String> getRank(int rank) {

        Iterator<Map.Entry<Integer, List<String>>> iterator = leaderBoard.entrySet().iterator();
        int i = 0;
        while (iterator.hasNext()) {
            Map.Entry<Integer, List<String>> entry = iterator.next();
            if (i == rank - 1) {
                return entry.getValue();
            }
            i++;
        }
        return new ArrayList<>();

    }
}*/
