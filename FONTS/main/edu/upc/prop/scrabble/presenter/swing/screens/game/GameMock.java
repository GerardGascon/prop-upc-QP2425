package edu.upc.prop.scrabble.presenter.swing.screens.game;

import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.data.board.SuperBoard;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.board.PremiumTileTypeFiller;
import edu.upc.prop.scrabble.presenter.swing.screens.game.board.BoardView;
import edu.upc.prop.scrabble.presenter.swing.screens.game.board.IBlankPieceSelector;
import edu.upc.prop.scrabble.presenter.swing.screens.game.board.sidepanel.SidePanel;
import edu.upc.prop.scrabble.presenter.swing.screens.game.hand.HandView;
import edu.upc.prop.scrabble.presenter.swing.screens.game.pieceselector.PieceSelector;
import edu.upc.prop.scrabble.presenter.swing.screens.game.turnaction.draw.DrawAction;
import edu.upc.prop.scrabble.presenter.swing.screens.menu.PauseButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.function.Consumer;

public class GameMock extends JPanel {
    // Coses per a Pause Button
    private boolean menuActive = false;
    private JPanel settingsPanel;
    private JPanel saveConfirmPanel;
    private final JButton continueButton = new JButton("Continuar");
    private final JButton exitButton = new JButton("Salir");
    private final JButton yesSaveButton = new JButton("Sí");
    private final JButton noSaveButton = new JButton("No");

    public GameMock() {
        setLayout(null);
        setBackground(new Color(0x50, 0x84, 0x6e));

        // En aquest moment exacte no tenim acces a getWidth i Heigth.
        // Invoke later s'asegura d'executarho mes tard, quan tinguem acces
        SwingUtilities.invokeLater(() -> {
            int width = getWidth();
            int height = getHeight();
            int xcoord = (int)(width * 0.3);
            int compWidth = (int)(width * 0.065);
            int compHeight = (int)(width * 0.035);
            createTurnActionButton("PUT", xcoord, (int)(height*0.6), compWidth, compHeight);

            createTurnActionButton("DRAW", xcoord, (int)(height*0.5), compWidth, compHeight);
            createTurnActionButton("SKIP", xcoord, (int)(height*0.4), compWidth, compHeight);

        });

        // Create Pause button
        PauseButton pauseButton = new PauseButton(this);
        add(pauseButton);
    }

    @Override
    public void doLayout() {
        super.doLayout();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        super.paintComponent(g2);
        g2.dispose();
        // We don't need to call sidePanel.drawSidePanel(g) anymore because sidePanel is a proper component
    }

    private void createTurnActionButton(String text, int x, int y, int width, int height) {
        JButton putButton = new JButton(text);
        putButton.setFocusPainted(false);
        putButton.setBackground(new Color(0xbc, 0xbc, 0xbc));
        putButton.setForeground(Color.BLACK);
        putButton.setBorder(BorderFactory.createEmptyBorder());
        putButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        putButton.setBounds(x, y, width, height);

        putButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(text + " button clicked!");
                // cridar a putActionMaker
            }
        });
        add(putButton);

    }
}