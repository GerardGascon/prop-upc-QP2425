package edu.upc.prop.scrabble.presenter.swing.screens.game.pieceselector;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;

class PieceWriter extends JTextField {
    private static final int MAX_CHARACTERS = 3;

    public PieceWriter() {
        super();
        setDocument(new UppercaseDocument(MAX_CHARACTERS));
        setHorizontalAlignment(JTextField.CENTER);
        setBorder(null);
        setFont(new Font("SansSerif", Font.BOLD, 20));
        setFont(calculateFont(getHeight()));
    }

    @Override
    public void setText(String text) {
        super.setText(text.toUpperCase());
    }

    @Override
    public boolean isOpaque() {
        return false;
    }

    @Override
    public void doLayout() {
        super.doLayout();
        setFont(calculateFont(getHeight()));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Insets insets = getInsets();
        int width = getWidth() - insets.left - insets.right;
        int height = getHeight() - insets.top - insets.bottom;

        g2.setColor(Color.WHITE);
        g2.fillRoundRect(insets.left, insets.top, width, height, 58, 58);

        super.paintComponent(g2);
        g2.dispose();
    }

    private Font calculateFont(int height) {
        int fontSize = (int) (height * 0.4f);
        return getFont().deriveFont((float) fontSize);
    }

    private static class UppercaseDocument extends PlainDocument {
        private final int maxChars;

        public UppercaseDocument(int maxChars) {
            this.maxChars = maxChars;
        }

        @Override
        public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
            if (str != null)
                str = str.toUpperCase();

            assert str != null;
            if (getLength() + str.length() <= maxChars) {
                super.insertString(offs, str, a);
            }
        }

        @Override
        public void remove(int offs, int len) throws BadLocationException {
            super.remove(offs, len);
        }
    }
}
