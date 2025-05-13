package edu.upc.prop.scrabble.presenter.swing.screens.game.board.tiles;

import edu.upc.prop.scrabble.presenter.swing.screens.game.board.BoardView;
import edu.upc.prop.scrabble.presenter.swing.screens.game.board.IBlankPieceSelector;
import edu.upc.prop.scrabble.presenter.swing.screens.game.board.IHandView;
import edu.upc.prop.scrabble.presenter.swing.screens.game.utils.Tooltip;
import edu.upc.prop.scrabble.utils.Vector2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.RoundRectangle2D;

public abstract class BoardTile extends JButton {
    private final IHandView handView;
    private final IBlankPieceSelector blankPieceSelector;
    private final Vector2 position;
    private final BoardView board;

    public Vector2 getPosition() {
        return position;
    }

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

    private void disableKeyboardInput() {
        InputMap inputMap = getInputMap(JComponent.WHEN_FOCUSED);
        inputMap.put(KeyStroke.getKeyStroke("SPACE"), "none");
        inputMap.put(KeyStroke.getKeyStroke("released SPACE"), "none");

        inputMap.put(KeyStroke.getKeyStroke("ENTER"), "none");
        inputMap.put(KeyStroke.getKeyStroke("released ENTER"), "none");
    }

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

    protected abstract void drawTile(Graphics2D g, Color bg, int radius);

    @Override
    public boolean contains(int x, int y) {
        Shape shape = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), getCornerRadius(), getCornerRadius());
        return shape.contains(x, y);
    }

    private int getCornerRadius() {
        return getHeight() * 20 / 100 * 2;
    }

    private void clicked(ActionEvent actionEvent) {
        openSelectBlankPieceLetterPopup();
//        placePiece("A", 1);
//        String piece = handView.getSelectedPiece();
//        if (piece.isBlank() || piece.equals("#")){
//            openSelectBlankPieceLetterPopup();
//            return;
//        }
//
//        int points = handView.getSelectedPiecePoints();
//        placePiece(piece, points);
    }

    private void openSelectBlankPieceLetterPopup() {
        blankPieceSelector.openSelectorPopUp(this::placeBlankPiece);
    }

    private void placeBlankPiece(String letter) {
        placePiece(letter, 0);
    }

    private void placePiece(String piece, int points) {
        board.placeTemporalPiece(piece, points, position.x, position.y);
    }

    protected final void createTooltip(String text) {
        Tooltip tooltip = new Tooltip(this, text);

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
