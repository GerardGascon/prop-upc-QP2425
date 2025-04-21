package edu.upc.prop.scrabble.domain.pieces;

import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.data.Player;

import java.util.ArrayList;

/***
 * Verifies if a player has the necessary pieces in their hand to form a given word.
 * @author Gina Escofet González
 */
public class PiecesInHandVerifier {
    private final Player player;
    private final PiecesConverter piecesConverter;

    /***
     * Constructs a verifier for the specified player and piece converter.
     * @param player The player whose hand will be verified.
     * @param piecesConverter Converts words to piece arrays.
     * @throws IllegalArgumentException if either parameter is null
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
     * Finds and returns the pieces from the player's hand that match a given word.
     * @param word The word to verify against the player's pieces.
     * @return Array of matching pieces found in the player's hand
     * @throws IllegalArgumentException if the word is null
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
