package edu.upc.prop.scrabble.data;


import edu.upc.prop.scrabble.data.board.BoardType;
import edu.upc.prop.scrabble.data.properties.Language;
import edu.upc.prop.scrabble.persistence.runtime.data.PersistentArray;
import edu.upc.prop.scrabble.persistence.runtime.data.PersistentDictionary;
import edu.upc.prop.scrabble.persistence.runtime.data.PersistentObject;
import edu.upc.prop.scrabble.persistence.runtime.interfaces.IPersistableObject;

/**
 * Representa l'estat de la partida en curs, incloent informació sobre el tauler,
 * el llenguatge, el torn actual, els jugadors i el nombre de torns saltats.
 * Aquesta classe també permet la persistència d'aquestes dades mitjançant encode/decode.
 * @author Biel
 */
public class GameData implements IPersistableObject {
    /** Nombre de torns consecutius que s'han saltat. */
    private int skipCounter = 0;
    /** Número del torn actual. */
    private int turnNumber = 0;
    /** Tipus de tauler de joc. */
    private BoardType boardType;
    /** Llengua utilitzada durant la partida. */
    private Language language;
    /** Jugadors participants en la partida. */
    private Player[] players;

    /**
     * Crea una instància de les dades d'una partida
     */
    public GameData() {

    }

    /**
     * Estableix el comptador de salts de torn.
     * @param skipCounter nombre de torns consecutius saltats
     */
    public void setSkipCounter(int skipCounter) {
        this.skipCounter = skipCounter;
    }
    /**
     * Retorna el nombre de torns consecutius saltats.
     * @return nombre de torns saltats
     */
    public int getSkipCounter() {
        return this.skipCounter;
    }
    /**
     * Estableix el número de torn actual.
     * @param turnNumber número del torn
     */
    public void setTurnNumber(int turnNumber) {
        this.turnNumber = turnNumber;
    }
    /**
     * Retorna el número de torn actual.
     * @return número del torn
     */
    public int getTurnNumber() {
        return turnNumber;
    }
    /**
     * Estableix l'idioma de la partida.
     * @param language idioma seleccionat
     */
    public void setLanguage(Language language) {
        this.language = language;
    }
    /**
     * Retorna l'idioma actual de la partida.
     * @return idioma seleccionat
     */
    public Language getLanguage() {
        return language;
    }
    /**
     * Estableix els jugadors de la partida.
     * @param players array de jugadors
     */
    public void setPlayers(Player[] players) {
        this.players = players;
    }
    /**
     * Retorna l'array de jugadors participants.
     * @return jugadors de la partida
     */
    public Player[] getPlayers() {
        return players;
    }
    /**
     * Estableix el tipus de tauler emprat.
     * @param boardType tipus de tauler
     */
    public void setBoardType(BoardType boardType) {
        this.boardType = boardType;
    }
    /**
     * Retorna el tipus de tauler emprat.
     * @return tipus de tauler
     */
    public BoardType getBoardType() {
        return boardType;
    }
    /**
     * Serialitza totes les dades del joc en un {@link PersistentDictionary} per a poder-les guardar.
     * @return diccionari amb les dades persistents de la partida
     */
    @Override
    public PersistentDictionary encode() {
        PersistentDictionary data = new PersistentDictionary();

        data.add(new PersistentObject("skipCounter", this.skipCounter));
        data.add(new PersistentObject("turnNumber", this.turnNumber));
        data.add(new PersistentObject("boardType", this.boardType.toString()));
        data.add(new PersistentObject("language", this.language.toString()));

        PersistentArray players = new PersistentArray("players");
        for (Player player : this.players)
            players.add(player);
        data.add(players);

        return data;
    }
    /**
     * Reconstrueix l'estat intern a partir d'un {@link PersistentDictionary}.
     * Aquesta funció es fa servir per carregar una partida desada.
     * @param data diccionari amb les dades persistides
     */
    @Override
    public void decode(PersistentDictionary data) {
        PersistentObject skipCounter = data.get("skipCounter");
        this.skipCounter = skipCounter.parse(Integer.class);

        PersistentObject turnNumber = data.get("turnNumber");
        this.turnNumber = turnNumber.parse(Integer.class);

        PersistentObject boardType = data.get("boardType");
        this.boardType = BoardType.valueOf(boardType.parse(String.class));

        PersistentObject language = data.get("language");
        this.language = Language.valueOf(language.parse(String.class));

        PersistentArray players = (PersistentArray)data.get("players");
        this.players = new Player[players.getLength()];
        for (int i = 0; i < players.getLength(); i++) {
            this.players[i] = players.get(i, Player.class);
        }
    }
}

