package edu.upc.prop.scrabble.presenter.swing.objects;

import edu.upc.prop.scrabble.data.Movement;
import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.exceptions.ScrabbleException;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.actionmaker.DrawActionMaker;
import edu.upc.prop.scrabble.domain.actionmaker.PlaceActionMaker;
import edu.upc.prop.scrabble.domain.actionmaker.SkipActionMaker;
import edu.upc.prop.scrabble.domain.turns.IGamePlayer;
import edu.upc.prop.scrabble.presenter.scenes.SceneObject;
import edu.upc.prop.scrabble.domain.actionmaker.IHandView;
import edu.upc.prop.scrabble.presenter.swing.screens.game.board.sidepanel.SidePanel;
import edu.upc.prop.scrabble.presenter.swing.screens.game.turnaction.ActionButtonPanel;

/**
 * Classe abstracta que representa un objecte jugador dins de l'escena,
 * integrant les accions de joc i la interfície amb la vista de la mà.
 * Implementa la interfície IGamePlayer per a la gestió de torns.
 *
 * @author Gerard Gascón
 */
public abstract class PlayerObject extends SceneObject implements IGamePlayer {

    /**
     * Índex que identifica el jugador dins de la partida.
     */
    private int playerIndex;

    /**
     * Indica si el jugador té el torn actiu.
     */
    private boolean onTurn = false;

    /**
     * Dades del jugador (nom, mà, etc.).
     */
    protected Player player;

    /**
     * Acció encarregada de col·locar fitxes al tauler.
     */
    protected PlaceActionMaker placeActionMaker;

    /**
     * Acció encarregada de robar fitxes de la bossa.
     */
    protected DrawActionMaker drawActionMaker;

    /**
     * Acció encarregada de saltar el torn.
     */
    protected SkipActionMaker skipActionMaker;

    /**
     * Vista que mostra les fitxes a la mà del jugador.
     */
    private IHandView handView;

    protected ActionButtonPanel actionButtonPanel;
    private SidePanel sidePanel;

    /**
     * Configura el jugador amb les accions i dades necessàries.
     *
     * @param playerIndex Índex del jugador.
     * @param placeActionMaker Acció per col·locar fitxes.
     * @param player Dades del jugador.
     * @param drawActionMaker Acció per robar fitxes.
     * @param skipActionMaker Acció per saltar torn.
     * @param handView Vista de la mà del jugador.
     * @param actionButtonPanel Panell dels botons d'acció
     * @param sidePanel Panell lateral on es mostren les puntuacions dels jugadors
     */
    public final void configure(int playerIndex, PlaceActionMaker placeActionMaker, Player player, DrawActionMaker drawActionMaker, SkipActionMaker skipActionMaker, IHandView handView, ActionButtonPanel actionButtonPanel, SidePanel sidePanel) {
        this.playerIndex = playerIndex;
        this.placeActionMaker = placeActionMaker;
        this.player = player;
        this.drawActionMaker = drawActionMaker;
        this.skipActionMaker = skipActionMaker;
        this.handView = handView;
        this.actionButtonPanel = actionButtonPanel;
        this.sidePanel = sidePanel;
    }

    /**
     * Inicia el torn del jugador mostrant les fitxes a la vista.
     */
    @Override
    public void startTurn() {
        onTurn = true;
        handView.showPieces(player.getHand());
        sidePanel.setCurrentPlayer(playerIndex);
    }

    /**
     * Intenta col·locar una fitxa al tauler segons el moviment indicat.
     * En cas d'error, salta automàticament el torn.
     *
     * @param movement Moviment que indica on col·locar la fitxa.
     */
    protected final void placePiece(Movement movement) {
        try {
            placeActionMaker.run(movement);
        } catch (ScrabbleException e) {
            skipActionMaker.run();
        }
    }

    /**
     * Intenta robar les fitxes indicades.
     * En cas d'error, salta automàticament el torn.
     *
     * @param piece Array de fitxes que es volen robar.
     */
    protected final void drawPieces(Piece[] piece) {
        try {
            String[] word = new String[piece.length];
            for (int i = 0; i < piece.length; i++) {
                word[i] = piece[i].letter();
            }
            drawActionMaker.run(word);
        } catch (ScrabbleException e) {
            skipActionMaker.run();
        }
    }

    /**
     * Salta el torn actual.
     */
    protected final void skipTurn() {
        skipActionMaker.run();
    }

    /**
     * Finalitza el torn del jugador.
     */
    @Override
    public final void endTurn() {
        actionButtonPanel.hideButtons();
        onTurn = false;
    }

    /**
     * Indica si el jugador està actiu en el torn actual.
     *
     * @return true si el jugador té el torn, false en cas contrari.
     */
    @Override
    public final boolean isActive() {
        return onTurn;
    }
}
