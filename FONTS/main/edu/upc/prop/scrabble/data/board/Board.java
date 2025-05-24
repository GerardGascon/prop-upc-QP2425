package edu.upc.prop.scrabble.data.board;

import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.persistence.runtime.data.PersistentDictionary;
import edu.upc.prop.scrabble.persistence.runtime.data.PersistentObject;
import edu.upc.prop.scrabble.persistence.runtime.interfaces.IPersistableObject;

/**
 * Representa el tauler del joc de Scrabble.
 * Manté les fitxes col·locades i la informació de les caselles amb bonificació de puntuació.
 * Aquesta classe proporciona funcionalitats bàsiques com col·locar fitxes, comprovar l'estat de les caselles
 * i rotar el tauler.
 *
 * @author Gerard Gascón
 */
public abstract class Board implements IPersistableObject {
    /**
     * Matriu de fitxes col·locades al tauler. Les caselles buides contenen null.
     */
    protected Piece[][] placedTiles;

    /**
     * Matriu que representa les caselles de bonificació (doble/triple paraula o lletra).
     */
    private final PremiumTileType[][] premiumTiles;

    /**
     * Indica si el tauler està buit (sense cap fitxa col·locada).
     */
    private boolean empty = true;

    /**
     * Crea un tauler amb la mida donada.
     *
     * @param size la mida (amplada i alçada) del tauler quadrat
     */
    protected Board(int size) {
        placedTiles = new Piece[size][size];
        premiumTiles = new PremiumTileType[size][size];
    }

    /**
     * Retorna la mida del tauler.
     *
     * @return la mida del tauler
     */
    public int getSize() {
        return placedTiles.length;
    }

    /**
     * Col·loca una fitxa a les coordenades indicades del tauler.
     * No fa res si la casella ja està ocupada.
     *
     * @param piece la fitxa a col·locar
     * @param x     índex de columna (comença a 0)
     * @param y     índex de fila (comença a 0)
     * @see Piece
     */
    public void placePiece(Piece piece, int x, int y) {
        if (placedTiles[y][x] != null) return;

        placedTiles[y][x] = piece;
        empty = false;
    }

    /**
     * Comprova si les coordenades donades corresponen al centre del tauler.
     * Normalment, la primera paraula s'ha de col·locar passant pel centre.
     *
     * @param x índex de columna
     * @param y índex de fila
     * @return true si la casella és el centre del tauler
     */
    public boolean isCenter(int x, int y) {
        return x == y && x == getSize() / 2;
    }

    /**
     * Comprova si una casella està buida i dins dels límits del tauler.
     *
     * @param x coordenada x
     * @param y coordenada y
     * @return true si la casella és vàlida i buida
     */
    public boolean isCellEmpty(int x, int y) {
        if (!isCellValid(x, y))
            return false;
        return placedTiles[y][x] == null;
    }

    /**
     * Retorna la fitxa a la casella especificada.
     *
     * @param x coordenada x
     * @param y coordenada y
     * @return la fitxa en la casella, o null si està buida
     */
    public Piece getCellPiece(int x, int y) {
        return placedTiles[y][x];
    }

    /**
     * Comprova si una casella és una casella de bonificació.
     *
     * @param x coordenada x
     * @param y coordenada y
     * @return true si és una casella de bonificació
     */
    public boolean isPremiumTile(int x, int y) {
        return premiumTiles[y][x] != null;
    }

    /**
     * Retorna el tipus de bonificació d'una casella concreta.
     *
     * @param x coordenada x
     * @param y coordenada y
     * @return el tipus de casella de bonificació, o null si no n'és
     * @see PremiumTileType
     */
    public PremiumTileType getPremiumTileType(int x, int y) {
        return premiumTiles[y][x];
    }

    /**
     * Assigna un tipus de casella de bonificació a una ubicació específica.
     *
     * @param x    coordenada x
     * @param y    coordenada y
     * @param type el tipus de bonificació a assignar
     */
    protected void setPremiumTile(int x, int y, PremiumTileType type) {
        premiumTiles[y][x] = type;
    }

    /**
     * Comprova si les coordenades donades estan dins dels límits del tauler.
     *
     * @param x coordenada x
     * @param y coordenada y
     * @return true si les coordenades són vàlides
     */
    public boolean isCellValid(int x, int y) {
        return x >= 0 && x < getSize() && y >= 0 && y < getSize();
    }

    /**
     * Retorna si el tauler està buit (sense cap fitxa col·locada).
     *
     * @return true si no hi ha cap fitxa, false altrament
     */
    public boolean isEmpty() {
        return empty;
    }

    /**
     * Retorna una còpia del tauler rotat 90 graus en sentit antihorari.
     *
     * @return una còpia rotada del tauler
     */
    public Board rotate() {
        Board copy = copy();
        copy.placedTiles = getRotatedPieces();
        return copy;
    }

    /**
     * Retorna una matriu de fitxes rotada 90 graus en sentit antihorari.
     *
     * @return la matriu de fitxes rotada
     */
    private Piece[][] getRotatedPieces() {
        Piece[][] rotatedPieces = new Piece[getSize()][getSize()];
        for (int row = 0; row < getSize(); row++) {
            for (int col = 0; col < getSize(); col++) {
                rotatedPieces[getSize() - col - 1][row] = placedTiles[row][col];
            }
        }
        return rotatedPieces;
    }

    /**
     * Crea i retorna una còpia del tauler.<br>
     * <b>Important</b>: No inclou les fitxes col·locades.
     *
     * @return una còpia del tauler
     */
    protected abstract Board copy();

    /**
     * Codifica l'estat del tauler en un diccionari per persistència.
     *
     * @return el diccionari amb les dades del tauler
     */
    @Override
    public PersistentDictionary encode() {
        PersistentDictionary data = new PersistentDictionary();

        data.add(new PersistentObject("placedTiles", placedTiles));

        return data;
    }

    /**
     * Decodifica les dades persistents per reconstruir l'estat del tauler.
     *
     * @param data diccionari amb les dades emmagatzemades
     */
    @Override
    public void decode(PersistentDictionary data) {
        PersistentObject placedTiles = data.get("placedTiles");
        this.placedTiles = placedTiles.parse(Piece[][].class);
        updateEmptyState();
    }

    /**
     * Actualitza l'estat de "buit" del tauler en funció de si hi ha fitxes col·locades.
     */
    private void updateEmptyState() {
        for (int row = 0; row < getSize(); row++) {
            for (int col = 0; col < getSize(); col++) {
                if (this.placedTiles[row][col] != null){
                    empty = false;
                    return;
                }
            }
        }
    }
}
