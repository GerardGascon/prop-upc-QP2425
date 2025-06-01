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
import edu.upc.prop.scrabble.presenter.swing.screens.game.sidepanel.SidePanel;
import edu.upc.prop.scrabble.presenter.swing.screens.game.turnaction.ActionButtonPanel;

/**
 * Classe abstracta que representa un objecte jugador dins de l'escena de joc.
 * Integra accions de joc com col·locar, robar o passar el torn, així com la comunicació amb la vista.
 * Implementa la interfície {@link IGamePlayer} per permetre la seva gestió durant els torns de partida.
 * <p>
 * Aquesta classe serveix com a base tant per a jugadors controlats per IA com per a jugadors humans.
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
     * Objecte que conté les dades del jugador (mà de fitxes, nom, puntuació, etc.).
     */
    protected Player player;

    /**
     * Acció per col·locar fitxes al tauler.
     */
    protected PlaceActionMaker placeActionMaker;

    /**
     * Acció per robar fitxes de la bossa.
     */
    protected DrawActionMaker drawActionMaker;

    /**
     * Acció per saltar el torn.
     */
    protected SkipActionMaker skipActionMaker;

    /**
     * Vista encarregada de mostrar les fitxes a la mà del jugador.
     */
    private IHandView handView;

    /**
     * Panell amb els botons d'acció (col·locar, robar, passar).
     */
    protected ActionButtonPanel actionButtonPanel;

    /**
     * Panell lateral que mostra la informació dels jugadors (puntuació, torns, etc.).
     */
    private SidePanel sidePanel;

    /**
     * Configura l'objecte jugador amb els valors necessaris per interactuar amb el joc.
     *
     * @param playerIndex Índex del jugador dins de la partida.
     * @param placeActionMaker Acció per col·locar fitxes.
     * @param player Objecte que conté les dades del jugador.
     * @param drawActionMaker Acció per robar fitxes.
     * @param skipActionMaker Acció per saltar el torn.
     * @param handView Vista de la mà del jugador.
     * @param actionButtonPanel Panell de botons d'acció del torn.
     * @param sidePanel Panell lateral amb informació dels jugadors.
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
     * Inicia el torn del jugador.
     * Mostra les fitxes de la mà a la vista i marca el jugador com a actiu.
     */
    @Override
    public void startTurn() {
        onTurn = true;
        handView.showPieces(player.getHand());
        sidePanel.setCurrentPlayer(playerIndex);
    }

    /**
     * Intenta col·locar una fitxa al tauler segons el moviment indicat.
     * Si es produeix una excepció, es passa automàticament el torn.
     *
     * @param movement Objecte que indica la posició i fitxa a col·locar.
     */
    protected final void placePiece(Movement movement) {
        try {
            placeActionMaker.run(movement);
        } catch (ScrabbleException e) {
            skipActionMaker.run();
        }
    }

    /**
     * Intenta robar les fitxes indicades de la mà.
     * Si es produeix un error, es passa el torn.
     *
     * @param piece Array de fitxes que el jugador vol robar.
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
     * Passa el torn actual del jugador.
     * Executa l'acció de "skip" definida per a aquest jugador.
     */
    protected final void skipTurn() {
        skipActionMaker.run();
    }

    /**
     * Finalitza el torn del jugador.
     * Amaga els botons d'acció i desactiva l'estat de torn actiu.
     */
    @Override
    public final void endTurn() {
        actionButtonPanel.hideButtons();
        onTurn = false;
    }

    /**
     * Indica si el jugador està actiu en el torn actual.
     *
     * @return {@code true} si el jugador està en el seu torn, {@code false} si no ho està.
     */
    @Override
    public final boolean isActive() {
        return onTurn;
    }
}
