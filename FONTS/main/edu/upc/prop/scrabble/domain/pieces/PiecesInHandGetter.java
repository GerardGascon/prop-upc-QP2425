package edu.upc.prop.scrabble.domain.pieces;

import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.exceptions.PlayerDoesNotHavePieceException;
import edu.upc.prop.scrabble.data.pieces.Bag;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.utils.IRand;

import java.util.*;

/***
 * Gestiona la recuperació i verificació de peces de la mà d'un jugador.
 * @author Gina Escofet González
 */
public class PiecesInHandGetter {
    /**
     * El jugador al qual representa aquesta classe
     */
    private final Player player;
    /**
     * La bossa on s'emmagatzemen les fitxes
     */
    private final Bag bag;
    /**
     * Generador de nombres aleatoris
     */
    private final IRand rand;

    /***
     * Construeix un nou PiecesInHandGetter amb la bossa, el jugador i el "piecePrinter" especificats.
     * @param bag La bossa de peces del joc.
     * @param player El jugador objectiu.
     * @param rand La interfície per a la generació de nombres aleatoris.
     */
    public PiecesInHandGetter(Bag bag, Player player, IRand rand) {
        this.player = player;
        this.bag = bag;
        this.rand = rand;
    }

    /***
     * Verifica i intercanvia peces de la mà del jugador.
     * @param pieces Peces necessàries per a la jugada actual.
     * @return Les noves peces extretes de la bossa.
     * @throws IllegalStateException si el procés falla.
     */
    public Piece[] run(Piece[] pieces) {
        List<Piece> piecesList = new Vector<>();
        if (!hasPieces(pieces))
            throw new PlayerDoesNotHavePieceException("Player " + player.getName() + " does not have all the pieces");

        for (Piece piece : pieces) {
            piecesList.add(player.removePiece(piece));
            if (!bag.isEmpty()) {
                int r = rand.nextInt(bag.getSize());
                Piece newPiece = bag.draw(r);
                player.addPiece(newPiece);
            }
        }
        return piecesList.toArray(Piece[]::new);
    }

    /**
     * Comprova si la mà del jugador conté totes les peces indicades.
     * Es crea una còpia de la mà per evitar modificar-la durant la comprovació.
     *
     * @param pieces Array de peces que volem verificar si estan a la mà del jugador.
     * @return {@code true} si totes les peces estan presents a la mà; {@code false} altrament.
     */
    private boolean hasPieces(Piece[] pieces) {
        List<Piece> hand = new ArrayList<>(Arrays.asList(player.getHand()));

        for (Piece piece : pieces) {
            if (!hasPiece(piece, hand))
                return false;
        }

        return true;
    }
    /**
     * Comprova si una peça específica està present dins d'una llista de peces,
     * i la elimina un cop trobada per evitar comptar-la més d'una vegada.
     * Gestiona especialment les peces "blank" (comodins), que es reconeixen
     * pel seu atribut isBlank.
     *
     * @param piece Peça que es vol buscar.
     * @param pieces Llista mutable de peces on es fa la cerca.
     * @return {@code true} si la peça es troba i s'elimina de la llista; {@code false} si no.
     */
    private boolean hasPiece(Piece piece, List<Piece> pieces) {
        if (piece.isBlank()){
            for (int i = 0; i < pieces.size(); i++) {
                if (pieces.get(i).isBlank()) {
                    pieces.remove(i);
                    return true;
                }
            }
            return false;
        }

        for (int i = 0; i < pieces.size(); i++) {
            if (pieces.get(i).letter().equals(piece.letter())) {
                pieces.remove(i);
                return true;
            }
        }
        return false;
    }
}