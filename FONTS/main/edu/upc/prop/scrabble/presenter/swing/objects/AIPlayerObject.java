package edu.upc.prop.scrabble.presenter.swing.objects;

import edu.upc.prop.scrabble.data.Movement;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.ai.AI;
import edu.upc.prop.scrabble.utils.Rand;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa un objecte jugador controlat per intel·ligència artificial (IA).
 * Estén la classe {@link PlayerObject} afegint funcionalitat específica per a jugadors IA.
 * Aquesta classe s'encarrega de gestionar les accions automàtiques durant el torn del jugador.
 *
 * @author Gerard Gascón
 */
public class AIPlayerObject extends PlayerObject {

    /**
     * Indica si és el torn actual del jugador IA.
     */
    private boolean onTurn;

    /**
     * Instància de la IA encarregada de decidir les accions del jugador.
     */
    private AI ai;

    /**
     * Crea una instància d'un jugador controlat per IA.
     */
    public AIPlayerObject() {

    }

    /**
     * Inicia el torn del jugador IA.
     * Es configura el panell d'accions i s'oculta per preparar les accions automàtiques.
     */
    @Override
    public void startTurn() {
        super.startTurn();

        actionButtonPanel.setActionMakers(placeActionMaker, drawActionMaker, skipActionMaker);
        actionButtonPanel.hideButtons();

        onTurn = true;
    }

    /**
     * Processa les accions del jugador IA durant el seu torn.
     * Aquesta funció és cridada regularment amb una actualització del temps de joc.
     * Si la IA retorna un moviment, es col·loca la peça; en cas contrari,
     * es decideix aleatòriament entre saltar el torn o robar peces.
     *
     * @param update valor flotant indicant el temps des de l'última actualització.
     */
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
     * @param ai instància de la intel·ligència artificial que gestionarà el jugador.
     */
    public void configureAI(AI ai) {
        this.ai = ai;
    }
}
