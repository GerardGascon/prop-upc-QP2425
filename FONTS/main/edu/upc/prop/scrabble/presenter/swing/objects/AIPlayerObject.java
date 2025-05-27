package edu.upc.prop.scrabble.presenter.swing.objects;

import edu.upc.prop.scrabble.data.Movement;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.ai.AI;
import edu.upc.prop.scrabble.utils.Rand;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa un objecte jugador controlat per intel·ligència artificial (IA).
 * Estén la classe PlayerObject afegint funcionalitat específica per a jugadors IA.
 *
 * @author Gerard Gascón
 */
public class AIPlayerObject extends PlayerObject {
    private boolean onTurn;
    private AI ai;

    @Override
    public void startTurn() {
        super.startTurn();

        actionButtonPanel.setActionMakers(placeActionMaker, drawActionMaker, skipActionMaker);
        actionButtonPanel.hideButtons();

        onTurn = true;
    }

    @Override
    public void onProcess(float update) {
        if (!onTurn)
            return;

        onTurn = false;
        Movement move = ai.run();
        if (move != null) {
            placePiece(move);
        } else {
            if (new Rand().nextInt(4) == 3) skipTurn();
            else {
                Piece[] hand = player.getHand();
                List<Piece> piecesToDraw = new ArrayList<>();
                for (Piece piece : hand)
                    if (new Rand().nextInt(5) == 1)
                        piecesToDraw.add(piece);
                drawPieces(piecesToDraw.toArray(Piece[]::new));
            }
        }
    }

    /**
     * Configura l'objecte jugador amb una instància d'IA per controlar-lo.
     *
     * @param ai Instància de la intel·ligència artificial que gestionarà el jugador.
     */
    public void configureAI(AI ai) {
        this.ai = ai;
    }
}
