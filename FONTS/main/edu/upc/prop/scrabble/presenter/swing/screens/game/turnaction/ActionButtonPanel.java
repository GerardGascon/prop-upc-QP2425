package edu.upc.prop.scrabble.presenter.swing.screens.game.turnaction;

import edu.upc.prop.scrabble.domain.actionmaker.DrawActionMaker;
import edu.upc.prop.scrabble.domain.actionmaker.PlaceActionMaker;
import edu.upc.prop.scrabble.domain.actionmaker.SkipActionMaker;

import javax.swing.*;
import java.awt.*;

/**
 * Classe per gestionar el panell que conté tots els botons d'accions de torn:
 * Robar (Draw), Col·locar (Place) i Passar (Skip).
 * @author Gina Escofet González
 */
public class ActionButtonPanel extends JPanel {

    private final DrawAction drawAction;
    private final PlaceAction placeAction;
    private final SkipAction skipAction;

    /**
     * Construeix un nou ActionButtonPanel amb les accions de torn especificades.
     * Aquest panell utilitza un FlowLayout per disposar els botons.
     * @param drawAction L'objecte DrawAction que gestiona la lògica del botó de robar.
     * @param placeAction L'objecte PlaceAction que gestiona la lògica del botó de col·locar.
     * @param skipAction L'objecte SkipAction que gestiona la lògica del botó de passar torn.
     */
    public ActionButtonPanel(DrawAction drawAction, PlaceAction placeAction, SkipAction skipAction) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.drawAction = drawAction;
        this.placeAction = placeAction;
        this.skipAction = skipAction;

        setOpaque(false);

        add(drawAction);
        add(placeAction);
        add(skipAction);

        drawAction.setAlignmentX(Component.CENTER_ALIGNMENT);
        placeAction.setAlignmentX(Component.CENTER_ALIGNMENT);
        skipAction.setAlignmentX(Component.CENTER_ALIGNMENT);

        drawAction.setParent(this);
        placeAction.setParent(this);

    }
    /**
     * Mostra tots els botons d'acció al panell, eliminant qualsevol contingut previ.
     * És útil per reiniciar la visualització i assegurar-se que tots els botons apareixen.
     */
    public void showButtons() {
        removeAll();
        add(drawAction);
        add(placeAction);
        add(skipAction);
        revalidate();
        repaint();
    }
    /**
     * Amaga tots els botons del panell eliminant-los i actualitzant la interfície gràfica.
     * Útil quan no es vol mostrar cap acció disponible.
     */
    public void hideButtons() {
        removeAll();
        revalidate();
        repaint();
    }
    /**
     * Configura el panell per a l'estat "Draw" (robar peces),
     * eliminant els botons de col·locar i passar per evitar accions incompatibles.
     */
    public void startDraw() {
        remove(placeAction);
        remove(skipAction);
        revalidate();
        repaint();
    }
    /**
     * Configura el panell per a l'estat "Place" (col·locar peces),
     * eliminant els botons de robar i passar per evitar accions incompatibles.
     */
    public void startPlace() {
        remove(drawAction);
        remove(skipAction);
        revalidate();
        repaint();
    }
    /**
     * Assigna els objectes ActionMaker corresponents als botons de col·locar, robar i passar.
     * Això permet que cada botó tingui la lògica correcta associada segons l'estat actual del joc.
     *
     * @param place Objecte que gestiona la lògica d'accions de col·locar peces.
     * @param draw Objecte que gestiona la lògica d'accions de robar peces.
     * @param skip Objecte que gestiona la lògica d'accions de passar torn.
     */
    public void setActionMakers(PlaceActionMaker place, DrawActionMaker draw, SkipActionMaker skip) {
        placeAction.setActionMaker(place, skip);
        drawAction.setActionMaker(draw, skip);
        skipAction.setSkip(skip);
    }

    /**
     * S'encarrega de pintar el component amb un fons arrodonit de color negre,
     * si ho desitges per aquest panell contenidor.
     * Si no vols un fons per a aquest panell, pots eliminar o comentar aquest mètode.
     * @param g L'objecte gràfic proporcionat pel sistema Swing per pintar.
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        super.paintComponent(g2);
        // g2.setColor(new Color(0x00, 0x00, 0x00, 150));
        // g2.fillRoundRect(0, 0, getWidth(), getHeight(), 58, 58);
        g2.dispose();
    }

    /**
     * Indica que el panell no és opac per permetre transparència.
     * @return fals, per permetre transparència visual.
     */
    @Override
    public boolean isOpaque() {
        return false;
    }
}