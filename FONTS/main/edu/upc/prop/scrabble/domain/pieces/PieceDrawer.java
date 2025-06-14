package edu.upc.prop.scrabble.domain.pieces;

import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.pieces.Bag;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.exceptions.NotEnoughPiecesInBagException;
import edu.upc.prop.scrabble.utils.IRand;

import java.util.ArrayList;
import java.util.List;

/**
 * Gestiona l'intercanvi de peces entre la mà d'un jugador i la bossa del joc.
 * Retorna les peces no desitjades a la bossa i treu noves peces aleatòries de la bossa.
 * @author Gina Escofet González
 */
public class PieceDrawer {
    /**
     * La bossa on es troben les fitxes
     */
    private final Bag bag;
    /**
     * El jugador que està utilitzant aquest botó
     */
    private final Player player;
    /**
     * Generador de nombres aleatoris
     */
    private final IRand rand;

    /**
     * Crea un PieceDrawer per a la bossa de joc i el jugador especificats.
     * @param bag La bossa de peces del joc.
     * @param player El jugador que realitza l'intercanvi.
     * @param rand La interfície per a la generació de nombres aleatoris.
     */
    public PieceDrawer(Bag bag, Player player, IRand rand) {
        this.bag = bag;
        this.player = player;
        this.rand = rand;
    }

    /**
     * Intercanvia peces entre la mà del jugador i la bossa.
     * @param piecesToSwap Peces a retornar a la bossa.
     */
    public void run(Piece[] piecesToSwap) {
        if (bag.getSize() < piecesToSwap.length)
            throw new NotEnoughPiecesInBagException("Not enough pieces in bag");

        List<Piece> drawnPieces = collectPiecesFromBag(piecesToSwap);
        swapPiecesWithPlayer(piecesToSwap);
        for (Piece piece : drawnPieces)
            player.addPiece(piece);
    }
    /**
     * Intercanvia les peces indicades amb el jugador:
     * elimina cada peça de la mà del jugador i la retorna a la bossa.
     *
     * @param piecesToSwap Array de peces que el jugador vol retornar a la bossa.
     */
    private void swapPiecesWithPlayer(Piece[] piecesToSwap) {
        for (Piece piece : piecesToSwap) {
            player.removePiece(piece);
            bag.add(piece);
        }
    }
    /**
     * Extreu de la bossa un nombre de peces aleatòries igual al nombre de peces retornades.
     * Utilitza el generador aleatori proporcionat per seleccionar les peces.
     *
     * @param piecesToSwap Array de peces que el jugador ha retornat a la bossa.
     * @return Llista de noves peces extretes aleatòriament de la bossa.
     */
    private List<Piece> collectPiecesFromBag(Piece[] piecesToSwap) {
        List<Piece> drawnPieces = new ArrayList<>();
        for (int i = 0; i < piecesToSwap.length; i++) {
            Piece randomBagPiece = bag.draw(rand.nextInt(bag.getSize()));
            drawnPieces.add(randomBagPiece);
        }
        return drawnPieces;
    }
}