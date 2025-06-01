package edu.upc.prop.scrabble.data;

import edu.upc.prop.scrabble.data.exceptions.PlayerDoesNotHavePieceException;
import edu.upc.prop.scrabble.data.pieces.Piece;

import java.util.ArrayList;

/**
 * Classe que representa un jugador/a amb els seus atributs
 * @author Albert Usero
 */
public class Player {
    /**
     * Nom del jugador/a
     */
    private final String name;
    /**
     * Puntuació actual del jugador/a
     */
    private int score;

    /**
     * Indica si el jugador és controlat per la CPU (true) o per un humà (false)
     */
    private final boolean CPU;
    /**
     * Llista de fitxes que té el jugador/a a la mà (màxim 7)
     */
    private final ArrayList<Piece> hand = new ArrayList<>(7);

    /**
     * Crea un nou jugador/a amb score 0 per defecte
     * @param name Nom del jugador/a a crear
     * @param CPU Booleà que indica si el jugador/a a crear és controlat per un humà (false) o no (true)
     */
    public Player(String name, boolean CPU) {
        this.name = name;
        this.CPU = CPU;
        this.score = 0;
    }

    /**
     * Retorna el nom del jugador/a
     * @return Nom del jugador/a
     */
    public String getName() {
        return name;
    }

    /**
     * Retorna si es CPU o no
     * @return Cert si és CPU, fals altrament
     */
    public boolean getCPU() {
        return CPU;
    }

    /**
     * Retorna la puntuació actual
     * @return Puntuació actual del jugador/a
     */
    public int getScore() {
        return score;
    }

    /**
     * Afegeix la quantitat de puntuació de l'input a la puntuació actual del jugador/a
     * @param scoretoadd Puntuació que volem afegir
     */
    public void addScore(int scoretoadd) {
        this.score += scoretoadd;
    }

    public Piece[] getHand() {return hand.toArray(Piece[]::new);}

    /**
     * Afegeix la peça donada a la mà del jugador/a
     * @param piece Peça que serà afegida
     * @see Piece
     */
    public void addPiece(Piece piece) {
        hand.add(piece);
    }
    /**
     * Elimina la peça donada de la mà del jugador/a
     * @param piece Peça que serà eliminada
     * @see Piece
     */
    public Piece removePiece(Piece piece) {
        if (piece.isBlank()){
            for (int i = 0; i < hand.size(); i++) {
                if (hand.get(i).isBlank()) {
                    Piece p = hand.remove(i);
                    p.setLetter(piece.letter());
                    return p;
                }
            }
            throw new PlayerDoesNotHavePieceException("Player " + name + " does not have the piece " + piece.letter());
        }

        for (int i = 0; i < hand.size(); i++) {
            Piece value = hand.get(i);
            if (value.letter().equals(piece.letter())) {
                hand.remove(i);
                return value;
            }
        }

        throw new PlayerDoesNotHavePieceException("Player " + name + " does not have the piece " + piece.letter());
    }

    /**
     * Consulta si el jugador/a té certa peça a la mà i si és així la retorna.
     * Si no tenim la peça, però sí que tenim comodí, es retornarà el comodí.
     * @param piece Peça sobre la qual volem obtenir informació
     * @return Peça en cas que sí que existeixi, null altrament
     */
    public Piece hasPiece(String piece) {
        Piece blankPiece = null;
        for(Piece value : hand) {
            if(value.isBlank()) blankPiece = value;
            else if(value.letter().equals(piece)) return value;
        }
        return blankPiece;
    }
}