package edu.upc.prop.scrabble.data;

import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.properties.Language;

import java.util.ArrayList;

public class GameData {
    public class GameData {

        private int skipCounter = 0;
        private int turnNumber = 0;
        private Language language;
        private Player[] players;

        public GameData(Language lan, Player[] p) {
            language = lan;
            players = p;
        }

        public void incrementSkipCounter() {
            skipCounter = skipCounter + 1;
        }

        public void resetSkipCounter() {
            skipCounter = 0;
        }

        public void incrementTurnNumber(){
            turnNumber = turnNumber + 1;
        }

        public int getSkipCounter() {
            return skipCounter;
        }

        public void setSkipCounter(int skipCounter) {
            this.skipCounter = skipCounter;
        }

        public int getTurnNumber() {
            return turnNumber;
        }

        public void setTurnNumber(int turnNumber) {
            this.turnNumber = turnNumber;
        }

        public Language getLanguage() {
            return language;
        }

        public void setLanguage(Language language) {
            this.language = language;
        }

        public Player[] getPlayers() {
            return players;
        }

        public void setPlayers(Player[] players) {
            this.players = players;
        }

    }
}
