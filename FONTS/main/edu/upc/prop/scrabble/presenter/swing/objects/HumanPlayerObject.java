package edu.upc.prop.scrabble.presenter.swing.objects;

/**
 * Representa un objecte jugador controlat per un usuari humà.
 * Estén la classe {@link PlayerObject} i permet la interacció directa de l'usuari amb la interfície.
 * Aquesta classe es limita a configurar els botons d'acció per mostrar-se quan comença el torn.
 *
 * @author Gerard Gascón
 */
public class HumanPlayerObject extends PlayerObject {

    /**
     * Inicia el torn del jugador humà.
     * Configura els creadors d'accions i mostra els botons d'acció per a la interacció de l'usuari.
     */
    @Override
    public void startTurn() {
        super.startTurn();

        actionButtonPanel.setActionMakers(placeActionMaker, drawActionMaker, skipActionMaker);
        actionButtonPanel.showButtons();
    }
}
