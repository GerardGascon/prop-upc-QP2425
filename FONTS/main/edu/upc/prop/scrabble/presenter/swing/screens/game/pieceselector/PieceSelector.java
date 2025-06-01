package edu.upc.prop.scrabble.presenter.swing.screens.game.pieceselector;

import edu.upc.prop.scrabble.data.properties.Language;
import edu.upc.prop.scrabble.presenter.swing.screens.game.pieceselector.alphabet.Alphabet;
import edu.upc.prop.scrabble.presenter.swing.screens.game.pieceselector.alphabet.CatalanAlphabet;
import edu.upc.prop.scrabble.presenter.swing.screens.game.pieceselector.alphabet.EnglishAlphabet;
import edu.upc.prop.scrabble.presenter.swing.screens.game.pieceselector.alphabet.SpanishAlphabet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.util.function.Consumer;

/**
 * Panell que mostra un popup per seleccionar una peça (lletra) en forma d’entrada.
 * Conté un camp d’entrada i botons per confirmar o cancel·lar.
 *
 * @author Gerard Gascón
 */
public class PieceSelector extends JPanel {
    /** Panell pare on es mostrarà aquest selector */
    private final JPanel parent;

    /** Percentatge de l’alçada del popup respecte al panell pare */
    private final float POPUP_PANEL_HEIGHT_PERCENTAGE = 0.2f;

    /** Relació d’aspecte (ample/alçada) del popup */
    private final float POPUP_PANEL_ASPECT_RATIO = 1.5f;

    /** Camp d’entrada per introduir la lletra */
    private final PieceWriter inputField;

    /** Botó per confirmar la selecció */
    private final PieceSelectorButton putBtn;

    /** Botó per cancel·lar la selecció */
    private final PieceSelectorButton cancelBtn;

    /** Panell que conté l’input i els botons (popup) */
    private final JPanel popup;

    /** Etiqueta de capçalera que indica què fer */
    private final JLabel headerLabel;

    /** Alfabet en ús durant la partida */
    private final Alphabet alphabet;

    /**
     * Crea un nou selector de peça amb botons de confirmació i cancel·lació.
     *
     * @param language Idioma en el que s'està jugant la partida
     * @param parent Panell pare on s’afegeix aquest selector
     * @param selectPieceCallback Funció que s’executa quan es valida i es confirma la lletra introduïda
     */
    public PieceSelector(Language language, JPanel parent, Consumer<String> selectPieceCallback) {
        super(null);
        this.parent = parent;

        alphabet = switch (language) {
            case English -> new EnglishAlphabet();
            case Catalan -> new CatalanAlphabet();
            case Spanish -> new SpanishAlphabet();
        };

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

        putBtn.addActionListener(_ -> validatePieceAndPlace(selectPieceCallback));

        cancelBtn.addActionListener(_ -> {
            parent.remove(this);
            parent.revalidate();
            parent.repaint();
        });

        addMouseWheelListener(InputEvent::consume);

        layoutComponents();
    }

    /**
     * Valida la peça introduïda i crida el callback per processar-la.
     * Actualment, només passa el text introduït sense fer validacions.
     *
     * @param selectPieceCallback Callback que s'executa amb la peça introduïda
     */
    private void validatePieceAndPlace(Consumer<String> selectPieceCallback) {
        if (!alphabet.isValid(inputField.getText()))
            return;

        selectPieceCallback.accept(inputField.getText().trim());
        parent.remove(this);
        parent.revalidate();
        parent.repaint();
    }

    /**
     * Calcula i estableix la posició i mida dels components del popup.
     */
    private void layoutComponents() {
        int panelHeight = (int)(parent.getHeight() * POPUP_PANEL_HEIGHT_PERCENTAGE);
        int panelWidth = (int)(panelHeight * POPUP_PANEL_ASPECT_RATIO);

        int x = (parent.getWidth() - panelWidth) / 2;
        int y = (parent.getHeight() - panelHeight) / 2;

        popup.setBounds(x, y, panelWidth, panelHeight);

        int margin = 10;
        int inputSize = panelHeight / 2 - 2 * margin;

        int buttonHeight = 48;

        int inputFieldX = (panelWidth - inputSize) / 2;
        int inputFieldY = (panelHeight - inputSize - margin) / 2;

        inputField.setBounds(inputFieldX, inputFieldY, inputSize, inputSize);

        int headerHeight = 30;
        int headerY = popup.getY() + 2 * margin;
        headerLabel.setBounds(popup.getX(), headerY, popup.getWidth(), headerHeight);

        int buttonWidth = (panelWidth - 3 * margin) / 2;
        int buttonY = panelHeight - buttonHeight - margin;

        putBtn.setBounds(margin, buttonY, buttonWidth, buttonHeight);
        cancelBtn.setBounds(2 * margin + buttonWidth, buttonY, buttonWidth, buttonHeight);
    }

    /**
     * Pinta el fons translúcid per donar un efecte de popup sobre el contingut
     * principal.
     *
     * @param g Context gràfic per pintar
     */
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

    /**
     * Indica que el panell no és completament opac.
     *
     * @return false sempre, ja que es vol un fons translúcid
     */
    @Override
    public boolean isOpaque() {
        return false;
    }
}
