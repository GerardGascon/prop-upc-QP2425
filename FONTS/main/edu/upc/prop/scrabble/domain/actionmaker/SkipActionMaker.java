package edu.upc.prop.scrabble.domain.actionmaker;

import edu.upc.prop.scrabble.domain.game.GameStepper;
import edu.upc.prop.scrabble.domain.turns.TurnResult;

/**
 * Classe que gestiona l'acció de passar el torn en el joc de Scrabble.
 * Quan s'executa, simplement avança el joc al següent torn sense realitzar cap moviment.
 *
 * @author Gerard Gascón
 */
public class SkipActionMaker {
    /**
     * Component que controla la lògica d'avançar el torn del joc.
     */
    private final GameStepper stepper;

    /**
     * Crea una instància de SkipActionMaker amb el controlador de torns.
     *
     * @param stepper Component per avançar la lògica del joc.
     */
    public SkipActionMaker(GameStepper stepper) {
        this.stepper = stepper;
    }

    /**
     * Executa l'acció de passar el torn, indicant al controlador que s'ha saltat el torn.
     */
    public void run() {
        stepper.run(TurnResult.Skip);
    }
}

