package edu.upc.prop.scrabble.presenter.swing.screens.game;

import edu.upc.prop.scrabble.presenter.swing.screens.game.board.BoardView;
import edu.upc.prop.scrabble.presenter.swing.screens.game.sidepanel.SidePanel;
import edu.upc.prop.scrabble.presenter.swing.screens.game.endscreen.EndScreen;
import edu.upc.prop.scrabble.presenter.swing.screens.game.hand.HandView;
import edu.upc.prop.scrabble.presenter.swing.screens.game.pieceselector.BlankPieceSelector;
import edu.upc.prop.scrabble.presenter.swing.screens.game.turnaction.ActionButtonPanel;
import edu.upc.prop.scrabble.presenter.swing.screens.game.pause.PauseMenu;

import javax.swing.*;
import java.awt.*;

/**
 * Pantalla principal del joc Scrabble. Gestiona la disposició visual del tauler,
 * la mà del jugador, el panell lateral, el selector de fitxes en blanc i el menú de pausa.
 * <p>
 * Aquesta classe s'encarrega de col·locar dinàmicament els components en funció
 * de les dimensions de la finestra.
 *
 * @author Gerard Gascón
 */
public class GameScreen extends JPanel {
    /**
     * Percentatge d'amplada reservada per al panell lateral.
     */
    private final float SIDE_PANEL_WIDTH_PERCENTAGE = 0.25f;

    /**
     * Percentatge d'alçada destinat al tauler del joc.
     */
    private final float BOARD_VERTICAL_SIZE_PERCENTAGE = 0.8f;

    /**
     * Percentatge de desplaçament horitzontal des de l'esquerra per col·locar el tauler.
     */
    private final float BOARD_HORIZONTAL_OFFSET_PERCENTAGE = 0.4f;

    /**
     * Component que representa la vista gràfica del tauler de Scrabble.
     */
    private BoardView boardView;

    /**
     * Component lateral amb informació del joc, com puntuacions o controls.
     */
    private SidePanel sidePanel;

    /**
     * Component que representa la mà del jugador amb les fitxes disponibles.
     */
    private HandView handView;

    /**
     * Component emergent que permet seleccionar una lletra per a una fitxa en blanc.
     */
    private BlankPieceSelector blankPieceSelector;

    /**
     * Component de menú de pausa que permet interrompre el joc.
     */
    private PauseMenu pauseMenu;

    /**
     * Component del panell d'accions del jugador
     */
    private ActionButtonPanel actionButtonPanel;

    /**
     * Component del panell del final de partida
     */
    private EndScreen endScreen;

    /**
     * Constructor que inicialitza la pantalla amb un disseny BorderLayout i
     * un color de fons verdós.
     */
    public GameScreen() {
        setLayout(new BorderLayout());
        setBackground(new Color(0x50, 0x84, 0x6e));
    }

    /**
     * Afegeix el tauler de joc a la pantalla.
     *
     * @param boardView la vista del tauler.
     */
    public void addBoard(BoardView boardView) {
        add(boardView);
        this.boardView = boardView;
    }

    /**
     * Afegeix el panell lateral a la pantalla.
     *
     * @param sidePanel el panell lateral amb informació addicional.
     */
    public void addSidePanel(SidePanel sidePanel) {
        add(sidePanel);
        this.sidePanel = sidePanel;
    }

    /**
     * Afegeix la vista de la mà del jugador a la pantalla.
     *
     * @param handView la vista de la mà amb les fitxes del jugador.
     */
    public void addHandView(HandView handView) {
        add(handView);
        this.handView = handView;
    }

    /**
     * Afegeix el selector de fitxes en blanc a la pantalla i el posa al davant.
     *
     * @param blankPieceSelector el selector de fitxes en blanc.
     */
    public void addBlankPieceSelector(BlankPieceSelector blankPieceSelector) {
        add(blankPieceSelector);
        setComponentZOrder(blankPieceSelector, 0);
        this.blankPieceSelector = blankPieceSelector;
    }

    /**
     * Afegeix el panell d'accions que pot fer el jugador.
     *
     * @param actionButtonPanel el panell de botons
     */
    public void addActionPanel(ActionButtonPanel actionButtonPanel) {
        add(actionButtonPanel);
        this.actionButtonPanel = actionButtonPanel;
    }

    /**
     * Afegeix el menú de pausa a la pantalla i el posa al davant.
     *
     * @param pauseMenu el menú de pausa del joc.
     */
    public void addPauseButton(PauseMenu pauseMenu) {
        add(pauseMenu);
        setComponentZOrder(pauseMenu, 0);
        this.pauseMenu = pauseMenu;
    }

    public void addEndScreen(EndScreen endScreen) {
        add(endScreen);
        setComponentZOrder(endScreen, 0);
        this.endScreen = endScreen;
    }

    /**
     * Sobreescriu el mètode de disposició dels components per organitzar-los
     * manualment a la pantalla segons les proporcions establertes.
     */
    @Override
    public void doLayout() {
        super.doLayout();

        putBoard();
        putSidePanel();
        putHandView();
        putBlankPieceSelector();
        putPauseButton();
        putActionButtonPanel();
        putEndScreen();
    }

    /**
     * Col·loca el tauler en la posició i mida adequada dins la pantalla.
     */
    private void putBoard() {
        int boardSize = (int) (getHeight() * BOARD_VERTICAL_SIZE_PERCENTAGE);
        int xOffset = (int) (getWidth() * BOARD_HORIZONTAL_OFFSET_PERCENTAGE);
        int yOffset = (getHeight() - boardSize) / 4;

        boardView.setBounds(xOffset, yOffset, boardSize, boardSize);
    }

    /**
     * Col·loca el panell lateral a l'esquerra de la pantalla.
     */
    private void putSidePanel() {
        int sidePanelWidth = (int)(getWidth() * SIDE_PANEL_WIDTH_PERCENTAGE);
        sidePanel.setBounds(0, 0, sidePanelWidth, getHeight());
    }

    /**
     * Col·loca la mà del jugador sota el tauler, centrada horitzontalment.
     */
    private void putHandView() {
        int boardSize = (int) (getHeight() * BOARD_VERTICAL_SIZE_PERCENTAGE);
        int xOffset = (int) (getWidth() * BOARD_HORIZONTAL_OFFSET_PERCENTAGE);
        int boardVerticalMargin = (getHeight() - boardSize) / 2;

        int margin = (int) (boardVerticalMargin * 0.05f);

        int extraRowY = boardVerticalMargin + boardSize + margin;
        int handPieceSize = (int) (boardVerticalMargin * 0.9f);

        int totalExtraRowWidth = (7 * handPieceSize) + (7 * HandView.HAND_PIECES_SPACING);

        int extraRowX = xOffset + (boardSize - totalExtraRowWidth) / 2;

        handView.setBounds(extraRowX, extraRowY, totalExtraRowWidth, handPieceSize);
        handView.setPieceSize(handPieceSize);
    }

    /**
     * Estableix la mida i posició del selector de fitxes en blanc (ocupa tota la pantalla).
     */
    private void putBlankPieceSelector() {
        blankPieceSelector.setBounds(0, 0, getWidth(), getHeight());
    }

    /**
     * Estableix la mida i posició del menú de pausa (ocupa tota la pantalla).
     */
    private void putPauseButton() {
        pauseMenu.setBounds(0, 0, getWidth(), getHeight());
    }

    /**
     * Col·loca el panell de botons a la vora de la mà del jugador
     */
    private void putActionButtonPanel() {
        if (actionButtonPanel != null && handView != null) {
            int handX = handView.getX();
            int handY = handView.getY();
            int handWidth = handView.getWidth();
            int handHeight = handView.getHeight();
            int margin = 100;
            Dimension preferredActionPanelSize = actionButtonPanel.getPreferredSize();

            int actionPanelWidth = preferredActionPanelSize.width;
            int actionPanelHeight = preferredActionPanelSize.height;

            int actionPanelX = handX + handWidth + margin;
            int actionPanelY = handY + handHeight - actionPanelHeight;

            actionButtonPanel.setBounds(actionPanelX, actionPanelY, actionPanelWidth, actionPanelHeight);
        }
    }

    /**
     * Estableix la mida i posició del panell de fi de partida
     */
    private void putEndScreen() {
        endScreen.setBounds(0, 0, getWidth(), getHeight());
    }
}
