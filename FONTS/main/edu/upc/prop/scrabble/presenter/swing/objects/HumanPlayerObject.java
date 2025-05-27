package edu.upc.prop.scrabble.presenter.swing.objects;

/**
 * Representa un objecte jugador controlat per un usuari humà.
 * Estén la classe PlayerObject sense funcionalitat addicional específica.
 *
 * @author Gerard Gascón
 */
public class HumanPlayerObject extends PlayerObject {
    @Override
    public void startTurn() {
        super.startTurn();

        actionButtonPanel.setActionMakers(placeActionMaker, drawActionMaker, skipActionMaker);
        actionButtonPanel.showButtons();
    }
}
