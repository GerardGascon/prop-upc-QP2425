package edu.upc.prop.scrabble.data;


import edu.upc.prop.scrabble.data.board.BoardType;
import edu.upc.prop.scrabble.data.properties.Language;
import edu.upc.prop.scrabble.persistence.runtime.data.PersistentDictionary;
import edu.upc.prop.scrabble.persistence.runtime.data.PersistentObject;
import edu.upc.prop.scrabble.persistence.runtime.interfaces.IPersistableObject;

// terminal... (OR MAYBE NOT!)
public class GameData implements IPersistableObject {
    private int skipCounter = 0;
    private int turnNumber = 0;
    private BoardType boardType;
    private Language language;
    private Player[] players;

    public void setSkipCounter(int skipCounter) {
        this.skipCounter = skipCounter;
    }
    public int getSkipCounter() {
        return this.skipCounter;
    }

    public void setTurnNumber(int turnNumber) {
        this.turnNumber = turnNumber;
    }
    public int getTurnNumber() {
        return turnNumber;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }
    public Language getLanguage() {
        return language;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }
    public Player[] getPlayers() {
        return players;
    }

    public void setBoardType(BoardType boardType) {
        this.boardType = boardType;
    }
    public BoardType getBoardType() {
        return boardType;
    }

    @Override
    public PersistentDictionary encode() {
        PersistentDictionary data = new PersistentDictionary();

        data.add(new PersistentObject("skipCounter", this.skipCounter));
        data.add(new PersistentObject("turnNumber", this.turnNumber));
        data.add(new PersistentObject("boardType", this.boardType));
        data.add(new PersistentObject("language", this.language));
        data.add(new PersistentObject("players", this.players));

        return data;
    }

    @Override
    public void decode(PersistentDictionary data) {
        PersistentObject skipCounter = data.get("skipCounter");
        this.skipCounter = skipCounter.parse(Integer.class);

        PersistentObject turnNumber = data.get("turnNumber");
        this.turnNumber = turnNumber.parse(Integer.class);

        PersistentObject boardType = data.get("boardType");
        this.boardType = boardType.parse(BoardType.class);

        PersistentObject language = data.get("language");
        this.language = language.parse(Language.class);

        PersistentObject players = data.get("players");
        this.players = players.parse(Player[].class);
    }
}

