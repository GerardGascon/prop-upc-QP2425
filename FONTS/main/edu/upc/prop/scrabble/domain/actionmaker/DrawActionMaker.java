package edu.upc.prop.scrabble.domain.actionmaker;

import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.pieces.Bag;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.exceptions.NotEnoughPiecesInBagException;
import edu.upc.prop.scrabble.domain.game.GameStepper;
import edu.upc.prop.scrabble.domain.pieces.PieceDrawer;
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
    private final IHandDisplay handDisplay;
    private final GameStepper stepper;

    /***
     * Default constructor of a DrawActionMaker with specific bag, player, rand and handDisplay.
     * @param bag The current piece bag of the game.
     * @param player The player who wants to draw pieces.
     * @param rand
     * @param handDisplay Interface that represents the current hand of the player.
     */
    public DrawActionMaker(Bag bag, Player player, IRand rand, IHandDisplay handDisplay, GameStepper stepper) {
        this.player = player;
        this.bag = bag;
        this.handDisplay = handDisplay;
        this.pieceDrawer = new PieceDrawer(bag, player, rand);
        this.stepper = stepper;
    }

    /***
     * Procedure that manages the exchange of pieces between the player's hand and the piece bag.
     * @param PiecesToSwap Array of pieces to be exchanged with new drawn pieces from the bag.
     * @throws IllegalArgumentException if PiecesToSwap is null.
     * @throws NotEnoughPiecesInBagException if there are not enough pieces in the bag for completing the exchange.
     */
    public void run(Piece[] PiecesToSwap) {
        if (PiecesToSwap == null) {
            throw new IllegalArgumentException("No pieces to swap");
        }
        if (PiecesToSwap.length > bag.getSize()) {
            throw new NotEnoughPiecesInBagException("Not enough pieces in bag");
        }
        pieceDrawer.run(PiecesToSwap);
        handDisplay.updateHand(player.getHand());
        stepper.run(TurnResult.Draw);
    }
}
