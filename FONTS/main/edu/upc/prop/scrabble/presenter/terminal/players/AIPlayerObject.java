package edu.upc.prop.scrabble.presenter.terminal.players;

import edu.upc.prop.scrabble.data.Movement;
import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.actionmaker.DrawActionMaker;
import edu.upc.prop.scrabble.domain.actionmaker.PlaceActionMaker;
import edu.upc.prop.scrabble.domain.actionmaker.SkipActionMaker;
import edu.upc.prop.scrabble.presenter.terminal.movements.DrawParser;
import edu.upc.prop.scrabble.utils.Rand;
import edu.upc.prop.scrabble.domain.ai.AI;

public class AIPlayerObject extends PlayerObject {
    private AI ai;

    public final void configureAI(AI ai) { this.ai = ai; }

    @Override
    public void onProcess(float delta) {
        if (!isActive())
            return;

        Movement move = ai.run();
        if(move != null) {
            int previousScore = player.getScore();
            placePiece(move);
            System.out.println("Movement: " + move.word() + " " + move.y() + "," + move.x() + " " + (player.getScore() - previousScore));
        }
        else { // NOTE TO SELF: in a future this can depend on difficulty, letter rarity...
            if(new Rand().nextInt(4) == 3) skipTurn();
            else {
                Piece[] hand = player.getHand();
                String draw = "";
                for(Piece piece : hand) if(new Rand().nextInt(5) == 1) draw += piece;
                Piece[] piecesToDraw = DrawParser.parse(draw);
                drawPieces(piecesToDraw);
            }
        }
    }
}
