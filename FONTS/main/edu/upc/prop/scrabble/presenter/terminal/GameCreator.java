package edu.upc.prop.scrabble.presenter.terminal;

import edu.upc.prop.scrabble.data.properties.GameProperties;
import edu.upc.prop.scrabble.presenter.scenes.SceneManager;
import edu.upc.prop.scrabble.presenter.scenes.SceneObject;
import edu.upc.prop.scrabble.presenter.terminal.scenes.GameScene;
import edu.upc.prop.scrabble.presenter.terminal.utils.Reader;



public class GameCreator extends SceneObject {
    private final LanguageSetter languageSetter = new LanguageSetter();
    private final SizeSetter sizeSetter = new SizeSetter();
    private final PlayerSetter playerSetter = new PlayerSetter();


    public GameCreator() {
        languageSetter.print_interface();
    }

    enum State {
        Lang,
        Size,
        Player
    }
    State state = State.Lang;

    @Override
    public void onProcess(float delta) {

        String action = Reader.getInstance().readLine();
        if (action == null) {
        }
        else if (action.equals("next")) {
            next();
        }
        else if (action.equals("previous")) {
            previous();
        }
        else if (action.equals("submit")) {
            submit();
        }
        else {
            if (state == State.Player && !playerSetter.hasEnded()) {
                playerSetter.addPlayer(action);
                playerSetter.print_interface();
            }
        }
    }

    private void previous(){
        switch (state) {
            case Lang:
                languageSetter.previous();
                break;
            case Size:
                sizeSetter.previous();
                break;
            case Player:
                playerSetter.Decrease();
                break;
        }
    }

    private void next(){
        switch (state) {
            case Lang:
                languageSetter.next();
                break;
            case Size:
                sizeSetter.next();
                break;
            case Player:
                playerSetter.Increase();
                break;
        }
    }

    private void submit(){
        switch (state) {
            case Lang:
                sizeSetter.print_interface();
                state = State.Size;
                break;
            case Size:
                playerSetter.print_interface();
                state = State.Player;
                break;
            case Player:
                if (playerSetter.hasEnded()) {
                    if (playerSetter.getTotalPlayer() < 2)
                        System.out.println("\n At least 2 players are needed! \n");
                    else
                        loadGame();
                }
                else {
                    playerSetter.switchMode();
                    playerSetter.print_interface();
                }
                break;
        }
    }

    private void loadGame(){
        GameProperties gameProperties = new GameProperties(languageSetter.getLanguage(), sizeSetter.getBoardSize(),
                                                            playerSetter.getPlayersNames(),playerSetter.getCpusNames());
        SceneManager.getInstance().load(GameScene.class,gameProperties);
    }
}
