package edu.upc.prop.scrabble.presenter.swing.screens.game.pieceselector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.util.function.Consumer;

public class PieceSelector extends JPanel {
    private final JPanel parent;
    private final float POPUP_PANEL_HEIGHT_PERCENTAGE = 0.2f;
    private final float POPUP_PANEL_ASPECT_RATIO = 1.5f;

    private final PieceWriter inputField;
    private final PieceSelectorButton putBtn;
    private final PieceSelectorButton cancelBtn;
    private final JPanel popup;
    private final JLabel headerLabel;

    public PieceSelector(JPanel parent, Consumer<String> selectPieceCallback) {
        super(null);
        this.parent = parent;

        setBounds(0, 0, parent.getWidth(), parent.getHeight());
        setOpaque(false);

        popup = new PieceSelectorPanel(null);

        inputField = new PieceWriter();
        putBtn = new PieceSelectorButton("Put");
        cancelBtn = new PieceSelectorButton("Cancel");
        headerLabel = new JLabel("Enter a piece");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerLabel.setBounds(0, 0, parent.getWidth(), 30);

        popup.add(inputField);
        popup.add(putBtn);
        popup.add(cancelBtn);
        add(headerLabel);
        add(popup);

        putBtn.addActionListener(_ -> {
            parent.remove(this);
            parent.revalidate();
            parent.repaint();
            validatePieceAndPlace(selectPieceCallback);
        });

        cancelBtn.addActionListener(_ -> {
            parent.remove(this);
            parent.revalidate();
            parent.repaint();
        });

        addMouseWheelListener(InputEvent::consume);

        layoutComponents();
    }

    private void validatePieceAndPlace(Consumer<String> selectPieceCallback) {
        selectPieceCallback.accept(inputField.getText());
    }

    private void layoutComponents() {
        int panelHeight = (int)(parent.getHeight() * POPUP_PANEL_HEIGHT_PERCENTAGE);
        int panelWidth = (int)(panelHeight * POPUP_PANEL_ASPECT_RATIO);

        int x = (parent.getWidth() - panelWidth) / 2;
        int y = (parent.getHeight() - panelHeight) / 2;

        popup.setBounds(x, y, panelWidth, panelHeight);

        int margin = 10;
        int inputSize = panelHeight / 2 - 2 * margin;

        int buttonHeight = panelHeight / 8;

        int inputFieldX = (panelWidth - inputSize) / 2;
        int inputFieldY = (panelHeight - inputSize - buttonHeight - margin) / 2;

        inputField.setBounds(inputFieldX, inputFieldY, inputSize, inputSize);

        int headerHeight = 30;
        int headerY = popup.getY() + 2 * margin;
        headerLabel.setBounds(popup.getX(), headerY, popup.getWidth(), headerHeight);

        int buttonWidth = (panelWidth - 3 * margin) / 2;
        int buttonY = panelHeight - buttonHeight - margin;

        putBtn.setBounds(margin, buttonY, buttonWidth, buttonHeight);
        cancelBtn.setBounds(2 * margin + buttonWidth, buttonY, buttonWidth, buttonHeight);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        super.paintComponent(g2);
        g2.setColor(new Color(0, 0, 0, 100));
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.dispose();
    }

    @Override
    public boolean isOpaque() {
        return false;
    }
}
