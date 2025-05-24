package edu.upc.prop.scrabble.presenter.swing.screens.game.pieceselector;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;

/**
 * Camp de text personalitzat per a l'entrada de peces,
 * que limita el nombre de caràcters i converteix automàticament el text a majúscules.
 * També personalitza la pintura per tenir un fons blanc amb cantonades arrodonides.
 *
 * @author Gerard Gascón
 */
class PieceWriter extends JTextField {
    /**
     * Màxim nombre de caràcters permesos.
     */
    private static final int MAX_CHARACTERS = 3;

    /**
     * Constructor que inicialitza el camp de text amb un document personalitzat,
     * alineació centrada i estil de font.
     */
    public PieceWriter() {
        super();
        setDocument(new UppercaseDocument(MAX_CHARACTERS));
        setHorizontalAlignment(JTextField.CENTER);
        setBorder(null);
        setFont(new Font("SansSerif", Font.BOLD, 20));
        setFont(calculateFont(getHeight()));
    }

    /**
     * Estableix el text convertint-lo automàticament a majúscules.
     *
     * @param text text a establir
     */
    @Override
    public void setText(String text) {
        super.setText(text.toUpperCase());
    }

    /**
     * Indica que el component no és opac per permetre la personalització del fons.
     *
     * @return false sempre
     */
    @Override
    public boolean isOpaque() {
        return false;
    }

    /**
     * Ajusta la mida de la font en funció de l'altura del component.
     */
    @Override
    public void doLayout() {
        super.doLayout();
        setFont(calculateFont(getHeight()));
    }

    /**
     * Pinta el fons blanc amb cantonades arrodonides i després el contingut del JTextField.
     *
     * @param g context gràfic
     */
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

    /**
     * Calcula una mida de font basada en una proporció de l'altura del component.
     *
     * @param height altura del component
     * @return font ajustada a l'altura
     */
    private Font calculateFont(int height) {
        int fontSize = (int) (height * 0.4f);
        return getFont().deriveFont((float) fontSize);
    }

    /**
     * Document personalitzat que limita la longitud del text i converteix
     * totes les lletres a majúscules.
     */
    private static class UppercaseDocument extends PlainDocument {
        private final int maxChars;

        /**
         * Constructor que inicialitza el límit de caràcters.
         *
         * @param maxChars nombre màxim de caràcters
         */
        public UppercaseDocument(int maxChars) {
            this.maxChars = maxChars;
        }

        /**
         * Insereix text convertit a majúscules, sempre que no excedeixi
         * el límit màxim de caràcters.
         *
         * @param offs posició on inserir
         * @param str cadena a inserir
         * @param a atributs associats
         * @throws BadLocationException si la posició és invàlida
         */
        @Override
        public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
            if (str != null)
                str = str.toUpperCase();

            assert str != null;
            if (getLength() + str.length() <= maxChars) {
                super.insertString(offs, str, a);
            }
        }

        /**
         * Elimina una part del text.
         *
         * @param offs posició inicial d'eliminació
         * @param len longitud a eliminar
         * @throws BadLocationException si la posició és invàlida
         */
        @Override
        public void remove(int offs, int len) throws BadLocationException {
            super.remove(offs, len);
        }
    }
}
