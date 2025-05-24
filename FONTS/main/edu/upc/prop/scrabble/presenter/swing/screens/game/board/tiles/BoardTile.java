package edu.upc.prop.scrabble.presenter.swing.screens.game.board.tiles;

import edu.upc.prop.scrabble.presenter.swing.screens.game.board.BoardView;
import edu.upc.prop.scrabble.presenter.swing.screens.game.board.IBlankPieceSelector;
import edu.upc.prop.scrabble.domain.actionmaker.IHandView;
import edu.upc.prop.scrabble.presenter.swing.screens.game.utils.Tooltip;
import edu.upc.prop.scrabble.utils.Vector2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.RoundRectangle2D;

/**
 * Classe abstracta que representa una fitxa del tauler.
 * <p>
 * És un botó personalitzat amb posició al tauler, que pot contenir una peça
 * i permet interaccions amb la mà i la selecció de fitxes en blanc.
 * </p>
 *
 * @author Gerard Gascón
 */
public abstract class BoardTile extends JButton {
    /**
     * Vista de la mà de fitxes de l'usuari.
     */
    private final IHandView handView;

    /**
     * Selector per fitxes en blanc (wildcards).
     */
    private final IBlankPieceSelector blankPieceSelector;

    /**
     * Posició de la fitxa al tauler.
     */
    private final Vector2 position;

    /**
     * Vista del tauler on es col·loca la fitxa.
     */
    private final BoardView board;

    /**
     * Text d'ajuda que surt en algunes caselles quan els hi passes el ratolí per sobre.
     */
    private Tooltip tooltip;

    /**
     * Retorna la posició (x,y) de la fitxa.
     *
     * @return vector de la posició
     */
    public Vector2 getPosition() {
        return position;
    }

    /**
     * Constructor amb posició, vista de la mà, tauler i selector fitxa en blanc.
     *
     * @param x coordenada x al tauler
     * @param y coordenada y al tauler
     * @param handView vista de la mà de fitxes
     * @param board vista del tauler
     * @param blankPieceSelector selector per fitxes en blanc
     */
    public BoardTile(int x, int y, IHandView handView, BoardView board, IBlankPieceSelector blankPieceSelector) {
        super();
        setOpaque(false);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);

        this.handView = handView;
        this.position = new Vector2(x, y);
        this.board = board;
        this.blankPieceSelector = blankPieceSelector;

        addActionListener(this::clicked);

        disableKeyboardInput();
    }

    /**
     * Deshabilita la resposta a les tecles SPACE i ENTER per evitar activacions accidentals.
     */
    private void disableKeyboardInput() {
        InputMap inputMap = getInputMap(JComponent.WHEN_FOCUSED);
        inputMap.put(KeyStroke.getKeyStroke("SPACE"), "none");
        inputMap.put(KeyStroke.getKeyStroke("released SPACE"), "none");

        inputMap.put(KeyStroke.getKeyStroke("ENTER"), "none");
        inputMap.put(KeyStroke.getKeyStroke("released ENTER"), "none");
    }

    /**
     * Pinta la fitxa amb un fons que canvia segons l'estat del botó (normal, ressaltat o premut).
     *
     * @param g context gràfic
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color bgColor;
        if (model.isArmed())
            bgColor = getBackground().darker();
        else if (model.isRollover())
            bgColor = getBackground().brighter();
        else
            bgColor = getBackground();

        drawTile(g2, bgColor, getCornerRadius());

        super.paintComponent(g);
        g2.dispose();
    }

    /**
     * Mètode abstracte per pintar la fitxa, ha de ser implementat per les subclasses.
     *
     * @param g context gràfic 2D
     * @param bg color de fons
     * @param radius radi de cantonades arrodonides
     */
    protected abstract void drawTile(Graphics2D g, Color bg, int radius);

    /**
     * Sobrescriu el mètode contains per fer clics dins una figura de cantonades arrodonides.
     *
     * @param x coordenada x del punt a testear
     * @param y coordenada y del punt a testear
     * @return true si el punt està dins la fitxa, false en cas contrari
     */
    @Override
    public boolean contains(int x, int y) {
        Shape shape = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), getCornerRadius(), getCornerRadius());
        return shape.contains(x, y);
    }

    /**
     * Retorna el radi de les cantonades arrodonides basat en l'alçada.
     *
     * @return radi en píxels
     */
    private int getCornerRadius() {
        return getHeight() * 20 / 100 * 2;
    }

    /**
     * Mètode invocat quan es fa clic sobre la fitxa.
     * Col·loca la peça seleccionada temporalment al tauler,
     * i si és una fitxa en blanc, obre un selector de lletra.
     *
     * @param actionEvent esdeveniment d’acció
     */
    protected void clicked(ActionEvent actionEvent) {
        String[] piece = handView.getSelectedPiece();
        if (piece == null)
            return;

        if (piece[0].isBlank() || piece[0].equals("#")) {
            openSelectBlankPieceLetterPopup();
            return;
        }

        int points = handView.getSelectedPiecePoints();
        placePiece(piece[0], points);
    }

    /**
     * Obre el popup per seleccionar la lletra d'una fitxa en blanc.
     */
    private void openSelectBlankPieceLetterPopup() {
        blankPieceSelector.openSelectorPopUp(this::placeBlankPiece);
    }

    /**
     * Col·loca la fitxa en blanc amb la lletra seleccionada i 0 punts.
     *
     * @param letter lletra seleccionada per la fitxa en blanc
     */
    private void placeBlankPiece(String letter) {
        placePiece(letter, 0);
    }

    /**
     * Col·loca temporalment una peça al tauler a la posició d'aquesta fitxa.
     *
     * @param piece lletra de la peça
     * @param points punts de la peça
     */
    private void placePiece(String piece, int points) {
        board.placeTemporalPiece(piece, points, position.x, position.y);
        handView.piecePlaced();

        if (tooltip != null)
            tooltip.setVisible(false);
    }

    /**
     * Crea un tooltip personalitzat que apareix quan es mou el cursor sobre la fitxa.
     *
     * @param text text del tooltip
     */
    protected final void createTooltip(String text) {
        tooltip = new Tooltip(this, text);

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                Point locationOnScreen = e.getLocationOnScreen();
                tooltip.showAt(locationOnScreen.x + 10, locationOnScreen.y + 10);
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                tooltip.setVisible(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                tooltip.setVisible(false);
            }
        });
    }
}
