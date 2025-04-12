package edu.upc.prop.scrabble.presenter.terminal.movements;

import edu.upc.prop.scrabble.data.Movement;
import edu.upc.prop.scrabble.presenter.scenes.SceneManager;
import edu.upc.prop.scrabble.presenter.scenes.SceneObject;
import edu.upc.prop.scrabble.presenter.terminal.utils.Reader;

public class MovementMaker extends SceneObject {
    private final Reader reader = new Reader();

    @Override
    public void onProcess(float delta) {
        String movementRaw = reader.readLine();
        if (movementRaw == null){
            return;
        }

        Movement move = MovementParser.parse(movementRaw);
        System.out.println(move);
        SceneManager.getInstance().quit();
        //TODO: Call someone to make the move
    }
}
