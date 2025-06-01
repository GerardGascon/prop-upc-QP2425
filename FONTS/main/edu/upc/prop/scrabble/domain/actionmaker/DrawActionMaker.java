package edu.upc.prop.scrabble.domain.actionmaker;

import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.pieces.Bag;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.exceptions.NotEnoughPiecesInBagException;
import edu.upc.prop.scrabble.domain.game.GameStepper;
import edu.upc.prop.scrabble.domain.pieces.PieceDrawer;
import edu.upc.prop.scrabble.domain.pieces.PiecesConverter;
import edu.upc.prop.scrabble.domain.turns.TurnResult;
import edu.upc.prop.scrabble.utils.IRand;

/**
 * Classe que gestiona l'intercanvi de fitxes entre la bossa del joc i la mà d'un jugador.
 * Les fitxes a canviar es retornen a la bossa i es reparteixen noves fitxes a la mà del jugador.
 * @author Gina Escofet González
 */
public class DrawActionMaker {
    /**
     * El jugador al qual pertany aquesta classe
     */
    private final Player player;
    /**
     * El controlador per robar fitxes
     */
    private final PieceDrawer pieceDrawer;
    /**
     * La bossa on es troben les fitxes
     */
    private final Bag bag;
    /**
     * La vista on es mostren les fitxes de la mà
     */
    private final IHandView handView;
    /**
     * El controlador que s'encarrega de controlar el flux del joc
     */
    private final GameStepper stepper;
    /**
     * El controlador que converteix un String en una Piece de la mà
     */
    private final PiecesConverter piecesConverter;

    /**
     * Constructor per defecte d'un DrawActionMaker amb bag, player, rand i handView.
     * @param bag La bossa de fitxes actual del joc.
     * @param player El jugador que vol canviar fitxes.
     * @param rand Instància del generador de nombres aleatoris.
     * @param handView Interfície que representa la mà actual del jugador.
     */
    public DrawActionMaker(Bag bag, Player player, IRand rand, IHandView handView, GameStepper stepper, PiecesConverter piecesConverter) {
        this.player = player;
        this.bag = bag;
        this.handView = handView;
        this.piecesConverter = piecesConverter;
        this.pieceDrawer = new PieceDrawer(bag, player, rand);
        this.stepper = stepper;
    }

    /**
     * Procediment que gestiona l'intercanvi de fitxes entre la mà del jugador i la bossa de fitxes.
     * @param word Paraula que es canvia per noves fitxes extretes de la bossa.
     * @throws IllegalArgumentException si PiecesToSwap és nul.
     * @throws NotEnoughPiecesInBagException si no hi ha prou fitxes a la bossa per completar l'intercanvi.
     */
    public void run(String[] word) {
        Piece[] PiecesToSwap = new Piece[word.length];
        for (int i = 0; i < word.length; i++) {
            Piece[] Piece = piecesConverter.run(word[i]);
            PiecesToSwap[i] = Piece[0];
        }
        if (PiecesToSwap.length > bag.getSize()) {
            throw new NotEnoughPiecesInBagException("Not enough pieces in bag");
        }

        pieceDrawer.run(PiecesToSwap);
        handView.showPieces(player.getHand());
        stepper.run(TurnResult.Draw);
    }
}
