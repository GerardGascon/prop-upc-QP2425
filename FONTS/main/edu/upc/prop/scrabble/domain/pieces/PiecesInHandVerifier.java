package edu.upc.prop.scrabble.domain.pieces;

import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.pieces.Piece;

import java.util.ArrayList;

/***
 * Verifica si un jugador té les peces necessàries a la seva mà per formar una paraula donada.
 * @author Gina Escofet González
 */
public class PiecesInHandVerifier {
    /**
     * El jugador al qual representa aquest controlador
     */
    private final Player player;
    /**
     * El convertidor de fitxes, utilitzat per convertir un string en una fitxa de la mà del jugador
     */
    private final PiecesConverter piecesConverter;

    /***
     * Construeix un verificador per al jugador i el convertidor de peces especificats.
     * @param player El jugador la mà del qual serà verificada.
     * @param piecesConverter Converteix paraules a arrays de peces.
     * @throws IllegalArgumentException si algun dels paràmetres és nul.
     */
    public PiecesInHandVerifier(Player player, PiecesConverter piecesConverter) {
        if (player == null) {
            throw new IllegalArgumentException("player cannot be null");
        }
        if (piecesConverter == null) {
            throw new IllegalArgumentException("piecesConverter cannot be null");
        }
        this.piecesConverter = piecesConverter;
        this.player = player;
    }

    /***
     * Troba i retorna les peces de la mà del jugador que coincideixen amb una paraula donada.
     * @param word La paraula a verificar amb les peces del jugador.
     * @return Array de peces coincidents trobades a la mà del jugador.
     * @throws IllegalArgumentException si la paraula és nul·la.
     */
    public Piece[] run(String word) {
        if (word == null) {
            throw new IllegalArgumentException("word cannot be null");
        }
        Piece[] hand = player.getHand();
        Piece[] piecesInWord = piecesConverter.run(word);
        ArrayList<Piece> piecesInHand = new ArrayList<>();

        for (Piece piece : piecesInWord) {
            for (Piece value : hand) {
                if (value.equals(piece)) {
                    piecesInHand.add(value);
                }
            }
        }
        return piecesInHand.toArray(new Piece[0]);
    }
}
