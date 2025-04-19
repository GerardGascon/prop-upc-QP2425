package edu.upc.prop.scrabble.domain.actionmaker;

import edu.upc.prop.scrabble.domain.game.GameStepper;
import edu.upc.prop.scrabble.domain.turns.TurnResult;

public class SkipActionMaker {
    private final GameStepper stepper;

    public SkipActionMaker(GameStepper stepper) {
        this.stepper = stepper;
    }

    public void run() {
        stepper.run(TurnResult.Skip);
    }
}
