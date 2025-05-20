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

/***
 * Class that exchanges pieces between the game bag and a player's hand. Pieces to swap are returned to the bag, and
 * new drawn pieces are assigned to the player's hand.
 * @author Gina Escofet González
 */
public class DrawActionMaker {
    private final Player player;
    private final PieceDrawer pieceDrawer;
    private final Bag bag;
    private final IHandView handView;
    private final GameStepper stepper;
    private final PiecesConverter piecesConverter;
    /***
     * Default constructor of a DrawActionMaker with specific bag, player, rand and handDisplay.
     * @param bag The current piece bag of the game.
     * @param player The player who wants to draw pieces.
     * @param rand The random number generator instance
     * @param handView Interface that represents the current hand of the player.
     */
    public DrawActionMaker(Bag bag, Player player, IRand rand, IHandView handView, GameStepper stepper, PiecesConverter piecesConverter) {
        this.player = player;
        this.bag = bag;
        this.handView = handView;
        this.piecesConverter = piecesConverter;
        this.pieceDrawer = new PieceDrawer(bag, player, rand);
        this.stepper = stepper;
    }

    /***
     * Procedure that manages the exchange of pieces between the player's hand and the piece bag.
     * @param word Word to be exchanged with new drawn pieces from the bag.
     * @throws IllegalArgumentException if PiecesToSwap is null.
     * @throws NotEnoughPiecesInBagException if there are not enough pieces in the bag for completing the exchange.
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
